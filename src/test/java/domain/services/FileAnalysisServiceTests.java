package domain.services;

import data.dataaccess.reader.LogFileReader;
import domain.entities.common.SearchResultLine;
import domain.entities.displayobjects.FileAnalysisFilterDo;
import domain.entities.domainobjects.LogLine;
import domain.entities.domainobjects.MetricsProfile;
import domain.entities.domainobjects.ParsingProfile;
import org.junit.Before;
import org.junit.Test;
import presentation.common.GuiConstants;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FileAnalysisServiceTests {

    private final DateFormat dateFormat = new SimpleDateFormat(GuiConstants.DATE_FORMATTER);
    private final DateFormat timeFormat = new SimpleDateFormat(GuiConstants.TIME_FORMATTER);
    private LogLine [] data;
    private static final String LEVEL_BASE = "Level";
    private static final String ORIGIN_BASE = "Origin";
    private static final String MESSAGE_BASE = "Message";
    private static final String IDENTIFIER_BASE = "Identifier";
    private static final String DATE_BASE = "2021-01-0"; // missing the day
    private static final String TIME_BASE = "12:10:10.00"; // missing the last millisecond


    @Before
    public void setup() throws ParseException {
        data = new LogLine[5];
        for (int i = 0; i < data.length; i++) {
            LogLine line = new LogLine();
            line.setLevel(LEVEL_BASE + i);
            line.setOrigin(ORIGIN_BASE + i);
            line.setMessage(MESSAGE_BASE + i);
            line.setIdentifier(IDENTIFIER_BASE + i);
            line.setDate(dateFormat.parse(DATE_BASE + i));
            line.setTime(timeFormat.parse(TIME_BASE + i));
            line.setPosition(i);
            data[i] = line;
        }
    }

    @Test
    public void getDataSimpleTest() {
        FileAnalysisServiceWrapper serviceWrapper = new FileAnalysisServiceWrapper(null, null, null, data);
        LogLine[] readData = serviceWrapper.getData();
        assertEquals(data.length, readData.length);
        compareLogLines(readData);
    }

    @Test
    public void getFilteredDataSimpleTest() {
        FileAnalysisServiceWrapper serviceWrapper = new FileAnalysisServiceWrapper(null, null, null, data);
        FileAnalysisFilterDo filterDo = new FileAnalysisFilterDo();

        filterDo.setOrigin(ORIGIN_BASE + 0);

        LogLine[] readData = serviceWrapper.getFilteredData(filterDo);
        assertEquals(1, readData.length);
        compareLogLines(readData);
    }

    @Test
    public void getFilteredDataByMessage() {
        FileAnalysisServiceWrapper serviceWrapper = new FileAnalysisServiceWrapper(null, null, null, data);
        FileAnalysisFilterDo filterDo = new FileAnalysisFilterDo();

        filterDo.setMessage(MESSAGE_BASE);

        LogLine[] readData = serviceWrapper.getFilteredData(filterDo);
        assertEquals(data.length, readData.length);
        compareLogLines(readData);
    }

    @Test
    public void getFilteredDataByLevelTest() {
        FileAnalysisServiceWrapper serviceWrapper = new FileAnalysisServiceWrapper(null, null, null, data);
        FileAnalysisFilterDo filterDo = new FileAnalysisFilterDo();

        filterDo.setLevel(LEVEL_BASE + 0);

        LogLine[] readData = serviceWrapper.getFilteredData(filterDo);
        assertEquals(1, readData.length);
        compareLogLines(readData);
    }

    @Test
    public void getFilteredDataByIdTest() {
        FileAnalysisServiceWrapper serviceWrapper = new FileAnalysisServiceWrapper(null, null, null, data);
        FileAnalysisFilterDo filterDo = new FileAnalysisFilterDo();

        filterDo.setIdentifier(IDENTIFIER_BASE + 0);

        LogLine[] readData = serviceWrapper.getFilteredData(filterDo);
        assertEquals(1, readData.length);
        compareLogLines(readData);
    }

    @Test
    public void getFilteredDataByDateTest() throws ParseException {
        FileAnalysisServiceWrapper serviceWrapper = new FileAnalysisServiceWrapper(null, null, null, data);
        FileAnalysisFilterDo filterDo = new FileAnalysisFilterDo();

        filterDo.setStartDate(dateFormat.parse(DATE_BASE + 0));
        filterDo.setStartTime(timeFormat.parse(TIME_BASE + 0));
        filterDo.setEndDate(dateFormat.parse(DATE_BASE + 2));
        filterDo.setEndTime(timeFormat.parse(TIME_BASE + 2));

        LogLine[] readData = serviceWrapper.getFilteredData(filterDo);
        assertEquals(3, readData.length);
        compareLogLines(readData);
    }

    @Test
    public void getFilteredDataEmptyFilterTest() {
        FileAnalysisServiceWrapper serviceWrapper = new FileAnalysisServiceWrapper(null, null, null, data);
        FileAnalysisFilterDo filterDo = new FileAnalysisFilterDo();
        LogLine[] readData = serviceWrapper.getFilteredData(filterDo);
        assertEquals(data.length, readData.length);
        compareLogLines(readData);
    }

    private void compareLogLines(LogLine[] readData) {
        for (int i = 0; i < readData.length; i++) {
            assertEquals(data[i].getLevel(), readData[i].getLevel());
            assertEquals(data[i].getOrigin(), readData[i].getOrigin());
            assertEquals(data[i].getMessage(), readData[i].getMessage());
            assertEquals(data[i].getIdentifier(), readData[i].getIdentifier());
            assertEquals(data[i].getDate(), readData[i].getDate());
            assertEquals(data[i].getTime(), readData[i].getTime());
        }
    }


    @Test
    public void lineMatchesFilterSimpleTest() throws ParseException {
        LogLine line = new LogLine();
        line.setLevel(LEVEL_BASE);
        line.setOrigin(ORIGIN_BASE);
        line.setMessage(MESSAGE_BASE);
        line.setIdentifier(IDENTIFIER_BASE);
        line.setDate(dateFormat.parse(DATE_BASE + 1));
        line.setTime(timeFormat.parse(TIME_BASE + 1));

        FileAnalysisFilterDo filterDo = new FileAnalysisFilterDo();

        filterDo.setLevel(LEVEL_BASE);
        filterDo.setOrigin(ORIGIN_BASE);
        filterDo.setMessage(MESSAGE_BASE);
        filterDo.setIdentifier(IDENTIFIER_BASE);
        filterDo.setStartDate(dateFormat.parse(DATE_BASE + 1));
        filterDo.setStartTime(timeFormat.parse(TIME_BASE + 1));
        filterDo.setEndDate(dateFormat.parse(DATE_BASE + 2));
        filterDo.setEndTime(timeFormat.parse(TIME_BASE + 2));

        FileAnalysisServiceWrapper serviceWrapper = new FileAnalysisServiceWrapper(null, null, null, data);
        assertTrue(serviceWrapper.lineMatchesFilter(line, filterDo));
    }


    @Test
    public void checkStringMatchesSimpleTest() {
        FileAnalysisServiceWrapper serviceWrapper = new FileAnalysisServiceWrapper(null, null, null, data);
        assertTrue(serviceWrapper.checkStringMatches(LEVEL_BASE, LEVEL_BASE));
        assertTrue(serviceWrapper.checkStringMatches(ORIGIN_BASE, ORIGIN_BASE));
        assertTrue(serviceWrapper.checkStringMatches(MESSAGE_BASE, MESSAGE_BASE));
        assertTrue(serviceWrapper.checkStringMatches(IDENTIFIER_BASE, IDENTIFIER_BASE));
    }

    @Test
    public void checkStringDoesNotMatchTest() {
        FileAnalysisServiceWrapper serviceWrapper = new FileAnalysisServiceWrapper(null, null, null, data);
        assertFalse(serviceWrapper.checkStringMatches(LEVEL_BASE, IDENTIFIER_BASE));
        assertFalse(serviceWrapper.checkStringMatches(ORIGIN_BASE, LEVEL_BASE));
        assertFalse(serviceWrapper.checkStringMatches(MESSAGE_BASE, ORIGIN_BASE));
        assertFalse(serviceWrapper.checkStringMatches(IDENTIFIER_BASE, MESSAGE_BASE));
    }

    @Test
    public void checkEmptyFilterMatchesTest() {
        FileAnalysisServiceWrapper serviceWrapper = new FileAnalysisServiceWrapper(null, null, null, data);
        assertTrue(serviceWrapper.checkStringMatches("", IDENTIFIER_BASE));
        assertTrue(serviceWrapper.checkStringMatches("", LEVEL_BASE));
        assertTrue(serviceWrapper.checkStringMatches("", ORIGIN_BASE));
        assertTrue(serviceWrapper.checkStringMatches("", MESSAGE_BASE));
    }

    @Test
    public void checkNullFilterMatchesTest() {
        FileAnalysisServiceWrapper serviceWrapper = new FileAnalysisServiceWrapper(null, null, null, data);
        assertTrue(serviceWrapper.checkStringMatches(null, IDENTIFIER_BASE));
        assertTrue(serviceWrapper.checkStringMatches(null, LEVEL_BASE));
        assertTrue(serviceWrapper.checkStringMatches(null, ORIGIN_BASE));
        assertTrue(serviceWrapper.checkStringMatches(null, MESSAGE_BASE));
    }

    @Test
    public void checkEmptyValueMatchesTest() {
        FileAnalysisServiceWrapper serviceWrapper = new FileAnalysisServiceWrapper(null, null, null, data);
        assertTrue(serviceWrapper.checkStringMatches(IDENTIFIER_BASE, ""));
        assertTrue(serviceWrapper.checkStringMatches(LEVEL_BASE, ""));
        assertTrue(serviceWrapper.checkStringMatches(ORIGIN_BASE, ""));
        assertTrue(serviceWrapper.checkStringMatches(MESSAGE_BASE, ""));
    }

    @Test
    public void checkNullValueMatchesTest() {
        FileAnalysisServiceWrapper serviceWrapper = new FileAnalysisServiceWrapper(null, null, null, data);
        assertTrue(serviceWrapper.checkStringMatches(IDENTIFIER_BASE, null));
        assertTrue(serviceWrapper.checkStringMatches(LEVEL_BASE, null));
        assertTrue(serviceWrapper.checkStringMatches(ORIGIN_BASE, null));
        assertTrue(serviceWrapper.checkStringMatches(MESSAGE_BASE, null));
    }

    @Test
    public void checkDateTimeIsBeforeSimpleTest() throws ParseException {
        FileAnalysisServiceWrapper serviceWrapper = new FileAnalysisServiceWrapper(null, null, null, data);
        Date date1 = dateFormat.parse(DATE_BASE + 1);
        Date time1 = timeFormat.parse(TIME_BASE + 1);
        Date date2 = dateFormat.parse(DATE_BASE + 2);
        Date time2 = timeFormat.parse(TIME_BASE + 2);
        assertTrue(serviceWrapper.checkDateTimeIsBefore(date1, date2));
        assertTrue(serviceWrapper.checkDateTimeIsBefore(time1, time2));
    }

    @Test
    public void checkDateTimeShouldNotBeBeforeTest() throws ParseException {
        FileAnalysisServiceWrapper serviceWrapper = new FileAnalysisServiceWrapper(null, null, null, data);
        Date date1 = dateFormat.parse(DATE_BASE + 1);
        Date time1 = timeFormat.parse(TIME_BASE + 1);
        Date date2 = dateFormat.parse(DATE_BASE + 2);
        Date time2 = timeFormat.parse(TIME_BASE + 2);
        assertFalse(serviceWrapper.checkDateTimeIsBefore(date2, date1));
        assertFalse(serviceWrapper.checkDateTimeIsBefore(time2, time1));
    }

    @Test
    public void checkDateTimeIsAfterSimpleTest() throws ParseException {
        FileAnalysisServiceWrapper serviceWrapper = new FileAnalysisServiceWrapper(null, null, null, data);
        Date date1 = dateFormat.parse(DATE_BASE + 1);
        Date time1 = timeFormat.parse(TIME_BASE + 1);
        Date date2 = dateFormat.parse(DATE_BASE + 2);
        Date time2 = timeFormat.parse(TIME_BASE + 2);
        assertTrue(serviceWrapper.checkDateTimeIsAfter(date2, date1));
        assertTrue(serviceWrapper.checkDateTimeIsAfter(time2, time1));
    }

    @Test
    public void checkDateTimeShouldNotBeAfterTest() throws ParseException {
        FileAnalysisServiceWrapper serviceWrapper = new FileAnalysisServiceWrapper(null, null, null, data);
        Date date1 = dateFormat.parse(DATE_BASE + 1);
        Date time1 = timeFormat.parse(TIME_BASE + 1);
        Date date2 = dateFormat.parse(DATE_BASE + 2);
        Date time2 = timeFormat.parse(TIME_BASE + 2);
        assertFalse(serviceWrapper.checkDateTimeIsAfter(date1, date2));
        assertFalse(serviceWrapper.checkDateTimeIsAfter(time1, time2));
    }

    @Test
    public void testStringPositionMatches() throws ParseException {
        String [] messages = new String[] {
                "Test at position 0",
                "01234Test at position 5",
                "01234Test at position 5 and 31 Test",
                "No match",
                "TestTestTest"
        };


        data = new LogLine[5];
        for (int i = 0; i < data.length; i++) {
            LogLine line = new LogLine();
            line.setLevel(LEVEL_BASE + i);
            line.setOrigin(ORIGIN_BASE + i);
            line.setIdentifier(IDENTIFIER_BASE + i);
            line.setDate(dateFormat.parse(DATE_BASE + i));
            line.setTime(timeFormat.parse(TIME_BASE + i));
            line.setMessage(messages[i]);
            line.setPosition(i);
            line.calculateSearchStructure();
            data[i] = line;
        }


        FileAnalysisServiceWrapper serviceWrapper = new FileAnalysisServiceWrapper(null, null, null, data);
        List<SearchResultLine> positionMatches = serviceWrapper.getStringPositionMatches(data, "Test");
        assertEquals(4, positionMatches.size());

        assertEquals(0, positionMatches.get(0).getSearchIdx()[0].intValue());
        assertEquals(0, positionMatches.get(0).getLogLineIdx());
        assertEquals(1, positionMatches.get(0).numberOfResults());

        assertEquals(5, positionMatches.get(1).getSearchIdx()[0].intValue());
        assertEquals(1, positionMatches.get(1).getLogLineIdx());
        assertEquals(1, positionMatches.get(1).numberOfResults());

        assertEquals(5, positionMatches.get(2).getSearchIdx()[0].intValue());
        assertEquals(31, positionMatches.get(2).getSearchIdx()[1].intValue());
        assertEquals(2, positionMatches.get(2).getLogLineIdx());
        assertEquals(2, positionMatches.get(2).numberOfResults());

        assertEquals(0, positionMatches.get(3).getSearchIdx()[0].intValue());
        assertEquals(4, positionMatches.get(3).getSearchIdx()[1].intValue());
        assertEquals(8, positionMatches.get(3).getSearchIdx()[2].intValue());
        assertEquals(4, positionMatches.get(3).getLogLineIdx());
        assertEquals(3, positionMatches.get(3).numberOfResults());
    }


    @Test
    public void testStringPositionMatchesNoMatches() throws ParseException {
        String [] messages = new String[] {
                "Will find nothing",
                "because I want to tes-t that",
                "t est te st tes t",
                "No match",
                ""
        };


        data = new LogLine[5];
        for (int i = 0; i < data.length; i++) {
            LogLine line = new LogLine();
            line.setLevel(LEVEL_BASE + i);
            line.setOrigin(ORIGIN_BASE + i);
            line.setIdentifier(IDENTIFIER_BASE + i);
            line.setDate(dateFormat.parse(DATE_BASE + i));
            line.setTime(timeFormat.parse(TIME_BASE + i));
            line.setMessage(messages[i]);
            line.setPosition(i);
            line.calculateSearchStructure();
            data[i] = line;
        }


        FileAnalysisServiceWrapper serviceWrapper = new FileAnalysisServiceWrapper(null, null, null, data);
        List<SearchResultLine> positionMatches = serviceWrapper.getStringPositionMatches(data, "Test");
        assertEquals(0, positionMatches.size());
    }


    @Test
    public void testStringPositionMatchesNoMatchesCaseSensitive() throws ParseException {
        String [] messages = new String[] {
                "Test at position 0",
                "01234Test at position 5",
                "01234Test at position 5 and 31 Test",
                "No match",
                "TestTestTest"
        };


        data = new LogLine[5];
        for (int i = 0; i < data.length; i++) {
            LogLine line = new LogLine();
            line.setLevel(LEVEL_BASE + i);
            line.setOrigin(ORIGIN_BASE + i);
            line.setIdentifier(IDENTIFIER_BASE + i);
            line.setDate(dateFormat.parse(DATE_BASE + i));
            line.setTime(timeFormat.parse(TIME_BASE + i));
            line.setMessage(messages[i]);
            line.setPosition(i);
            line.calculateSearchStructure();
            data[i] = line;
        }


        FileAnalysisServiceWrapper serviceWrapper = new FileAnalysisServiceWrapper(null, null, null, data);
        List<SearchResultLine> positionMatches = serviceWrapper.getStringPositionMatches(data, "test");
        assertEquals(0, positionMatches.size());
    }


    private static class FileAnalysisServiceWrapper extends FileAnalysisService {

        public FileAnalysisServiceWrapper(File selectedFile, ParsingProfile parsingProfile, MetricsProfile metricsProfile,
                                          LogLine [] data) {
            super(selectedFile, parsingProfile, metricsProfile);
            this.logFileReader = new LogFileReaderWrapper(null, null, data);
        }

        @Override
        protected boolean lineMatchesFilter(LogLine line, FileAnalysisFilterDo filterDo) {
            return super.lineMatchesFilter(line, filterDo);
        }

        @Override
        protected boolean checkStringMatches(String filter, String value) {
            return super.checkStringMatches(filter, value);
        }

        @Override
        protected boolean checkDateTimeIsBefore(Date filter, Date value) {
            return super.checkDateTimeIsBefore(filter, value);
        }

        @Override
        protected boolean checkDateTimeIsAfter(Date filter, Date value) {
            return super.checkDateTimeIsAfter(filter, value);
        }
    }

    private static class LogFileReaderWrapper extends LogFileReader {
        private final LogLine[] data;

        public LogFileReaderWrapper(File selectedFile, ParsingProfile parsingProfile, LogLine [] data) {
            super(selectedFile, parsingProfile);
            this.data = data;
        }

        @Override
        public LogLine[] read(Consumer<String> logMessageConsumer) {
            return data;
        }
    }
}
