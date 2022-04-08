package data.dataaccess.reader;

import domain.entities.common.ParsingProfilePortion;
import domain.entities.common.SeparatorEnum;
import domain.entities.common.TextClassesEnum;
import domain.entities.domainobjects.LogLine;
import domain.entities.domainobjects.MetricsProfile;
import domain.entities.domainobjects.MetricsReport;
import domain.entities.domainobjects.ParsingProfile;
import org.junit.Test;
import presentation.common.GuiConstants;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.function.Consumer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class DynamicLogFileReaderConsumerTests {
    public static final String VALUE = "Value";
    private final DateFormat dateFormat = new SimpleDateFormat(GuiConstants.DATE_FORMATTER);
    private final DateFormat timeFormat = new SimpleDateFormat(GuiConstants.TIME_FORMATTER);
    private final DateFormat timeStampFormat = new SimpleDateFormat(GuiConstants.DATE_TIME_FORMATTER);

    @Test
    public void simpleTestWithDateTime() {
        // 2021-01-01 12:10:10.0000 LEVEL METHOD MESSAGE
        DynamicLogFileReaderConsumer consumer = setupSimpleParser(metricsReport -> {
            LogLine[] lines = metricsReport.getData();
            assertEquals(1, lines.length);
            LogLine line = lines[0];

            assertEquals("2021-01-01", dateFormat.format(line.getDate()));
            assertEquals("12:10:10.001", timeFormat.format(line.getTime()));
            assertEquals("LEVEL", line.getLevel());
            assertEquals("METHOD", line.getOrigin());
            assertEquals("12", line.getIdentifier());
            assertEquals("MESSAGE MESSAGE MESSAGE", line.getMessage());

        });

        consumer.accept("2021-01-01 12:10:10.001 LEVEL METHOD 12 MESSAGE MESSAGE MESSAGE");
    }


    @Test
    public void simpleTestWithDateTimeNoMillis() {
        DynamicLogFileReaderConsumer consumer = setupSimpleParser(metricsReport -> {
            LogLine[] lines = metricsReport.getData();
            assertEquals(1, lines.length);
            LogLine line = lines[0];

            assertEquals("2021-01-01", dateFormat.format(line.getDate()));
            assertEquals("12:10:10.000", timeFormat.format(line.getTime()));
            assertEquals("LEVEL", line.getLevel());
            assertEquals("METHOD", line.getOrigin());
            assertEquals("12", line.getIdentifier());
            assertEquals("MESSAGE MESSAGE MESSAGE", line.getMessage());
        });
        consumer.accept("2021-01-01 12:10:10 LEVEL METHOD 12 MESSAGE MESSAGE MESSAGE");
    }

    @Test
    public void simpleTestWithTimestamp() {
        // 2021-01-01 12:10:10.0000 - LEVEL METHOD MESSAGE

        ParsingProfile profile = new ParsingProfile();
        profile.setName(VALUE);
        profile.addPortion(createSeparator(SeparatorEnum.OPEN_BRACKET));
        profile.addPortion(new ParsingProfilePortion(TextClassesEnum.TIMESTAMP.getName(), TextClassesEnum.TIMESTAMP.getName(), false, false));
        profile.addPortion(createSeparator(SeparatorEnum.CLOSE_BRACKET));
        profile.addPortion(new ParsingProfilePortion(TextClassesEnum.LEVEL.getName(), TextClassesEnum.LEVEL.getName(), false, false));
        profile.addPortion(createSeparator(SeparatorEnum.SPACE));
        profile.addPortion(new ParsingProfilePortion(TextClassesEnum.METHOD.getName(), TextClassesEnum.METHOD.getName(), false, false));
        profile.addPortion(createSeparator(SeparatorEnum.SPACE));
        profile.addPortion(new ParsingProfilePortion(TextClassesEnum.ID.getName(), TextClassesEnum.ID.getName(), false, false));
        profile.addPortion(createSeparator(SeparatorEnum.SPACE));
        profile.addPortion(new ParsingProfilePortion(TextClassesEnum.MESSAGE.getName(), TextClassesEnum.MESSAGE.getName(), false, false));
        profile.finishProfile();
        DynamicLogFileReaderConsumer consumer =  new DynamicLogFileReaderConsumer(metricsReport -> {
            LogLine[] lines = metricsReport.getData();
            assertEquals(1, lines.length);
            LogLine line = lines[0];

            assertEquals("2021-01-01 12:10:10.001", timeStampFormat.format(line.getTimestamp()));
            assertEquals("LEVEL", line.getLevel());
            assertEquals("METHOD", line.getOrigin());
            assertEquals("12", line.getIdentifier());
            assertEquals("MESSAGE MESSAGE MESSAGE", line.getMessage());
        }, profile, new MetricsProfile());

        consumer.accept("[2021-01-01 12:10:10.001] LEVEL METHOD 12 MESSAGE MESSAGE MESSAGE");
    }

    @Test
    public void simpleTestIgnoreSome() {
        // 2021-01-01 12:10:10.0000 LEVEL METHOD MESSAGE
        ParsingProfile profile = new ParsingProfile();
        profile.setName(VALUE);
        profile.addPortion(new ParsingProfilePortion(TextClassesEnum.DATE.getName(), TextClassesEnum.DATE.getName(), false, false));
        profile.addPortion(createSeparator(SeparatorEnum.SPACE));
        profile.addPortion(new ParsingProfilePortion(TextClassesEnum.TIME.getName(), TextClassesEnum.TIME.getName(), false, false));
        profile.addPortion(createSeparator(SeparatorEnum.SPACE));
        profile.addPortion(new ParsingProfilePortion(TextClassesEnum.LEVEL.getName(), TextClassesEnum.LEVEL.getName(), false, false));
        profile.addPortion(createSeparator(SeparatorEnum.SPACE));
        profile.addPortion(new ParsingProfilePortion(TextClassesEnum.METHOD.getName(), TextClassesEnum.METHOD.getName(), true, false));
        profile.addPortion(createSeparator(SeparatorEnum.SPACE));
        profile.addPortion(new ParsingProfilePortion(TextClassesEnum.MESSAGE.getName(), TextClassesEnum.MESSAGE.getName(), true, false));
        profile.finishProfile();

        DynamicLogFileReaderConsumer consumer =  new DynamicLogFileReaderConsumer(metricsReport -> {
            LogLine[] lines = metricsReport.getData();
            assertEquals(1, lines.length);
            LogLine line = lines[0];

            assertEquals("2021-01-01", dateFormat.format(line.getDate()));
            assertEquals("12:10:10.001", timeFormat.format(line.getTime()));
            assertEquals("LEVEL", line.getLevel());
            assertNull(line.getOrigin());
            assertNull(line.getMessage());
        }, profile, new MetricsProfile());

        consumer.accept("2021-01-01 12:10:10.001 LEVEL METHOD MESSAGE MESSAGE MESSAGE");
    }


    @Test
    public void simpleTestMultipleLines() {
        // 2021-01-01 12:10:10.0000 LEVEL METHOD MESSAGE
        ParsingProfile profile = new ParsingProfile();
        profile.setName(VALUE);
        profile.addPortion(new ParsingProfilePortion(TextClassesEnum.DATE.getName(), TextClassesEnum.DATE.getName(), false, false));
        profile.addPortion(createSeparator(SeparatorEnum.SPACE));
        profile.addPortion(new ParsingProfilePortion(TextClassesEnum.TIME.getName(), TextClassesEnum.TIME.getName(), false, false));
        profile.addPortion(createSeparator(SeparatorEnum.SPACE));
        profile.addPortion(new ParsingProfilePortion(TextClassesEnum.LEVEL.getName(), TextClassesEnum.LEVEL.getName(), false, false));
        profile.addPortion(createSeparator(SeparatorEnum.SPACE));
        profile.addPortion(new ParsingProfilePortion(TextClassesEnum.METHOD.getName(), TextClassesEnum.METHOD.getName(), false, false));
        profile.addPortion(createSeparator(SeparatorEnum.SPACE));
        profile.addPortion(new ParsingProfilePortion(TextClassesEnum.MESSAGE.getName(), TextClassesEnum.MESSAGE.getName(), false, false));
        profile.finishProfile();
        String [] inputs = {
                "2021-01-01 12:10:10.001 LEVEL METHOD MESSAGE MESSAGE MESSAGE",
                "2021-01-01 12:10:10.011 LEVEL2 METHOD MESSAGE MESSAGE",
                "2021-01-01 12:10:10.111 LEVEL3 METHOD MESSAGE "
        };

        String [][] expectedOutput = {
                {"2021-01-01","12:10:10.001", "LEVEL", "METHOD", "MESSAGE MESSAGE MESSAGE"},
                {"2021-01-01","12:10:10.011", "LEVEL2", "METHOD", "MESSAGE MESSAGE"},
                {"2021-01-01","12:10:10.111", "LEVEL3", "METHOD", "MESSAGE"}
        };

        DynamicLogFileReaderConsumer consumer =  new DynamicLogFileReaderConsumer(metricsReport -> {
            LogLine[] lines = metricsReport.getData();
            for (int i = 0; i < lines.length; i++) {
                LogLine line = lines[i];
                assertEquals(expectedOutput[i][0], dateFormat.format(line.getDate()));
                assertEquals(expectedOutput[i][1], timeFormat.format(line.getTime()));
                assertEquals(expectedOutput[i][2], line.getLevel());
                assertEquals(expectedOutput[i][3], line.getOrigin());
                assertEquals(expectedOutput[i][4], line.getMessage());
            }
        }, profile, new MetricsProfile());

        consumer.accept(inputs[0]);
        consumer.accept(inputs[1]);
        consumer.accept(inputs[2]);
    }



    @Test
    public void simpleTestIgnoreAll() {
        // 2021-01-01 12:10:10.0000 LEVEL METHOD MESSAGE
        ParsingProfile profile = new ParsingProfile();
        profile.setName(VALUE);
        profile.addPortion(new ParsingProfilePortion(TextClassesEnum.DATE.getName(), TextClassesEnum.DATE.getName(), true, false));
        profile.addPortion(createSeparator(SeparatorEnum.SPACE));
        profile.addPortion(new ParsingProfilePortion(TextClassesEnum.TIME.getName(), TextClassesEnum.TIME.getName(), true, false));
        profile.addPortion(createSeparator(SeparatorEnum.SPACE));
        profile.addPortion(new ParsingProfilePortion(TextClassesEnum.LEVEL.getName(), TextClassesEnum.LEVEL.getName(), true, false));
        profile.addPortion(createSeparator(SeparatorEnum.SPACE));
        profile.addPortion(new ParsingProfilePortion(TextClassesEnum.METHOD.getName(), TextClassesEnum.METHOD.getName(), true, false));
        profile.addPortion(createSeparator(SeparatorEnum.SPACE));
        profile.addPortion(new ParsingProfilePortion(TextClassesEnum.MESSAGE.getName(), TextClassesEnum.MESSAGE.getName(), true, false));
        profile.finishProfile();


        DynamicLogFileReaderConsumer consumer =  new DynamicLogFileReaderConsumer(metricsReport -> {
            LogLine[] lines = metricsReport.getData();
            assertEquals(1, lines.length);
            LogLine line = lines[0];

            assertNull(line.getDate());
            assertNull(line.getTime());
            assertNull(line.getLevel());
            assertNull(line.getOrigin());
            assertNull(line.getMessage());
        }, profile, new MetricsProfile());

        consumer.accept("2021-01-01 12:10:10.001 LEVEL METHOD MESSAGE MESSAGE MESSAGE");
    }


    @Test
    public void simpleTestIgnoreAllSeparators() {
        // 2021-01-01 12:10:10.0000 LEVEL METHOD MESSAGE
        ParsingProfile profile = new ParsingProfile();
        profile.setName(VALUE);
        profile.addPortion(new ParsingProfilePortion(TextClassesEnum.DATE.getName(), TextClassesEnum.DATE.getName(), false, false));
        profile.addPortion(createSeparator(SeparatorEnum.SPACE));
        profile.addPortion(new ParsingProfilePortion(TextClassesEnum.TIME.getName(), TextClassesEnum.TIME.getName(), false, false));
        profile.addPortion(createSeparator(SeparatorEnum.SPACE));
        profile.addPortion(new ParsingProfilePortion(TextClassesEnum.LEVEL.getName(), TextClassesEnum.LEVEL.getName(), false, false));
        profile.addPortion(createSeparator(SeparatorEnum.SPACE));
        profile.addPortion(new ParsingProfilePortion(TextClassesEnum.METHOD.getName(), TextClassesEnum.METHOD.getName(), false, false));
        profile.addPortion(createSeparator(SeparatorEnum.SPACE));
        profile.addPortion(new ParsingProfilePortion(TextClassesEnum.MESSAGE.getName(), TextClassesEnum.MESSAGE.getName(), false, false));
        profile.finishProfile();

        DynamicLogFileReaderConsumer consumer =  new DynamicLogFileReaderConsumer(metricsReport -> {
            LogLine[] lines = metricsReport.getData();
            assertEquals(1, lines.length);
            LogLine line = lines[0];

            assertEquals("2021-01-01", dateFormat.format(line.getDate()));
            assertEquals("12:10:10.001", timeFormat.format(line.getTime()));
            assertEquals("LEVEL", line.getLevel());
            assertEquals("METHOD", line.getOrigin());
            assertEquals("MESSAGE MESSAGE MESSAGE", line.getMessage());
        }, profile, new MetricsProfile());

        consumer.accept("2021-01-01 12:10:10.001 LEVEL METHOD MESSAGE MESSAGE MESSAGE");
    }


    @Test
    public void simpleTestIgnoreAllTextClasses() {
        // 2021-01-01 12:10:10.0000 LEVEL METHOD MESSAGE
        ParsingProfile profile = new ParsingProfile();
        profile.setName(VALUE);
        profile.addPortion(new ParsingProfilePortion(TextClassesEnum.DATE.getName(), TextClassesEnum.DATE.getName(), true, false));
        profile.addPortion(createSeparator(SeparatorEnum.SPACE));
        profile.addPortion(new ParsingProfilePortion(TextClassesEnum.TIME.getName(), TextClassesEnum.TIME.getName(), true, false));
        profile.addPortion(createSeparator(SeparatorEnum.SPACE));
        profile.addPortion(new ParsingProfilePortion(TextClassesEnum.LEVEL.getName(), TextClassesEnum.LEVEL.getName(), true, false));
        profile.addPortion(createSeparator(SeparatorEnum.SPACE));
        profile.addPortion(new ParsingProfilePortion(TextClassesEnum.METHOD.getName(), TextClassesEnum.METHOD.getName(), true, false));
        profile.addPortion(createSeparator(SeparatorEnum.SPACE));
        profile.addPortion(new ParsingProfilePortion(TextClassesEnum.MESSAGE.getName(), TextClassesEnum.MESSAGE.getName(), true, false));
        profile.finishProfile();

        DynamicLogFileReaderConsumer consumer =  new DynamicLogFileReaderConsumer(metricsReport -> {
            LogLine[] lines = metricsReport.getData();
            assertEquals(1, lines.length);
            LogLine line = lines[0];

            assertNull(line.getDate());
            assertNull(line.getTime());
            assertNull(line.getLevel());
            assertNull(line.getOrigin());
            assertNull(line.getMessage());
        }, profile, new MetricsProfile());

        consumer.accept("2021-01-01 12:10:10.001 LEVEL METHOD MESSAGE MESSAGE MESSAGE");
    }

    @Test
    public void emptyProfileTest() {
        // 2021-01-01 12:10:10.0000 LEVEL METHOD MESSAGE
        ParsingProfile profile = new ParsingProfile();
        profile.setName(VALUE);
        profile.finishProfile();

        DynamicLogFileReaderConsumer consumer =  new DynamicLogFileReaderConsumer(metricsReport -> {
            LogLine[] lines = metricsReport.getData();
            assertEquals(1, lines.length);
            LogLine line = lines[0];

            assertNull(line.getDate());
            assertNull(line.getTime());
            assertNull(line.getLevel());
            assertNull(line.getOrigin());
            assertNull(line.getIdentifier());
            assertNull(line.getMessage());
        }, profile, new MetricsProfile());

        consumer.accept("2021-01-01 12:10:10.001 LEVEL METHOD 12 MESSAGE MESSAGE MESSAGE");
    }


    @Test
    public void missingValuesInInputTest() {
        // 2021-01-01 12:10:10.0000 LEVEL METHOD MESSAGE
        DynamicLogFileReaderConsumer consumer = setupSimpleParser(metricsReport -> {
                LogLine[] lines = metricsReport.getData();
                if(lines.length != 0) {
                    LogLine line = lines[0];

                    // first line was ignored
                    assertEquals("2021-01-01", dateFormat.format(line.getDate()));
                    assertEquals("12:10:10.001", timeFormat.format(line.getTime()));
                    assertEquals("LEVEL", line.getLevel());
                    assertEquals("METHOD", line.getOrigin());
                    assertEquals("12", line.getIdentifier());
                    assertEquals("MESSAGE MESSAGE MESSAGE", line.getMessage());
                }
            }
        );

        consumer.accept("2021-01-01 LEVEL METHOD MESSAGE MESSAGE MESSAGE");
        consumer.accept("2021-01-01 12:10:10.001 LEVEL METHOD 12 MESSAGE MESSAGE MESSAGE");
    }

    @Test
    public void mismatchedParsingProfileForInputTest() {
        // test when there is no latestValidLine to append the non standard lines
        DynamicLogFileReaderConsumer consumer = setupSimpleParser(metricsReport -> {
                    LogLine[] lines = metricsReport.getData();
                    assertEquals(0, lines.length);
                }
        );

        consumer.accept("WARNING :place() Test Message");
    }

    @Test
    public void simpleTestWithNonStandardLines() {
        // in the inputs add lines which are not parsable by the pattern
        // 2021-01-01 12:10:10.0000 LEVEL METHOD MESSAGE
        DynamicLogFileReaderConsumer consumer = setupSimpleParser(metricsReport -> {});

        consumer.accept("2021-01-01 12:10:10.001 LEVEL METHOD 12 MESSAGE MESSAGE MESSAGE");
        consumer.accept("NON STANDARD MESSAGE");

        LogLine[] lines = consumer.getLines();
        assertEquals(1, lines.length);
        LogLine line = lines[0];

        assertEquals("2021-01-01", dateFormat.format(line.getDate()));
        assertEquals("12:10:10.001", timeFormat.format(line.getTime()));
        assertEquals("LEVEL", line.getLevel());
        assertEquals("METHOD", line.getOrigin());
        assertEquals("12", line.getIdentifier());
        assertEquals("MESSAGE MESSAGE MESSAGE" + System.lineSeparator() + "NON STANDARD MESSAGE", line.getMessage());
    }

    @Test
    public void simpleTestWithMultipleNonStandardLines() {
        // in the inputs add lines which are not parsable by the pattern
        // 2021-01-01 12:10:10.0000 LEVEL METHOD MESSAGE
        DynamicLogFileReaderConsumer consumer = setupSimpleParser(metricsReport -> {});

        consumer.accept("2021-01-01 12:10:10.001 LEVEL METHOD 12 MESSAGE MESSAGE MESSAGE");
        consumer.accept("NON STANDARD MESSAGE");
        consumer.accept("NON STANDARD MESSAGE");
        consumer.accept("NON STANDARD MESSAGE");
        consumer.accept("NON STANDARD MESSAGE");


        LogLine[] lines = consumer.getLines();
        assertEquals(1, lines.length);
        LogLine line = lines[0];

        assertEquals("2021-01-01", dateFormat.format(line.getDate()));
        assertEquals("12:10:10.001", timeFormat.format(line.getTime()));
        assertEquals("LEVEL", line.getLevel());
        assertEquals("METHOD", line.getOrigin());
        assertEquals("12", line.getIdentifier());
        assertEquals("MESSAGE MESSAGE MESSAGE" + System.lineSeparator() + "NON STANDARD MESSAGE"
                        + System.lineSeparator() + "NON STANDARD MESSAGE"
                        + System.lineSeparator() + "NON STANDARD MESSAGE"
                        + System.lineSeparator() + "NON STANDARD MESSAGE",
                line.getMessage());
    }

    @Test
    public void firstLineInvalidTest() {
        // test when there is no latestValidLine to append the non standard lines
        DynamicLogFileReaderConsumer consumer = setupSimpleParser(metricsReport -> {
            LogLine[] lines = metricsReport.getData();
            assertEquals(0, lines.length);
        });
        consumer.accept("NON STANDARD MESSAGE");
    }


    private DynamicLogFileReaderConsumer setupSimpleParser(Consumer<MetricsReport> consumer) {
        ParsingProfile profile = new ParsingProfile();
        profile.setName(VALUE);
        profile.addPortion(new ParsingProfilePortion(TextClassesEnum.DATE.getName(), TextClassesEnum.DATE.getName(), false, false));
        profile.addPortion(createSeparator(SeparatorEnum.SPACE));
        profile.addPortion(new ParsingProfilePortion(TextClassesEnum.TIME.getName(), TextClassesEnum.TIME.getName(), false, false));
        profile.addPortion(createSeparator(SeparatorEnum.SPACE));
        profile.addPortion(new ParsingProfilePortion(TextClassesEnum.LEVEL.getName(), TextClassesEnum.LEVEL.getName(), false, false));
        profile.addPortion(createSeparator(SeparatorEnum.SPACE));
        profile.addPortion(new ParsingProfilePortion(TextClassesEnum.METHOD.getName(), TextClassesEnum.METHOD.getName(), false, false));
        profile.addPortion(createSeparator(SeparatorEnum.SPACE));
        profile.addPortion(new ParsingProfilePortion(TextClassesEnum.ID.getName(), TextClassesEnum.ID.getName(), false, false));
        profile.addPortion(createSeparator(SeparatorEnum.SPACE));
        profile.addPortion(new ParsingProfilePortion(TextClassesEnum.MESSAGE.getName(), TextClassesEnum.MESSAGE.getName(), false, false));
        profile.finishProfile();
        return new DynamicLogFileReaderConsumer(consumer, profile, new MetricsProfile());
    }


    private ParsingProfilePortion createSeparator(SeparatorEnum separatorEnum) {
        return ParsingProfilePortion.createSeparator(separatorEnum.getName(), separatorEnum.getSymbol());
    }


    private ParsingProfilePortion createSeparator(int numberOfSkips, SeparatorEnum separatorEnum) {
        return ParsingProfilePortion.createSeparator(separatorEnum.getName(), SeparatorEnum.SPACE.getSymbol(), numberOfSkips);
    }

}
