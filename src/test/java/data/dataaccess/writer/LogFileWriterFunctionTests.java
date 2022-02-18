package data.dataaccess.writer;

import domain.entities.common.ParsingProfilePortion;
import domain.entities.common.SeparatorEnum;
import domain.entities.common.TextClassesEnum;
import domain.entities.domainobjects.LogLine;
import domain.entities.domainobjects.ParsingProfile;
import org.junit.Before;
import org.junit.Test;
import presentation.common.GuiConstants;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.Assert.assertEquals;


public class LogFileWriterFunctionTests {
    private final DateFormat dateFormat = new SimpleDateFormat(GuiConstants.DATE_FORMATTER);
    private final DateFormat timeFormat = new SimpleDateFormat(GuiConstants.TIME_FORMATTER);
    private final DateFormat timestampFormat = new SimpleDateFormat(GuiConstants.DATE_TIME_FORMATTER);
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
            line.setDate(dateFormat.parse(DATE_BASE + (i + 1)));
            line.setTime(timeFormat.parse(TIME_BASE + (i + 1)));
            line.setTimestamp(timestampFormat.parse(DATE_BASE + (i + 1) + " "+ TIME_BASE + (i + 1)));
            data[i] = line;
        }
    }

    @Test
    public void simpleTest() {
        ParsingProfile profile = new ParsingProfile();
        profile.addPortion(new ParsingProfilePortion(TextClassesEnum.DATE.getName(), TextClassesEnum.DATE.getName(), false, false));
        profile.addPortion(new ParsingProfilePortion(SeparatorEnum.HIFEN.getName(), SeparatorEnum.HIFEN.getSymbol(), false, true));
        profile.addPortion(new ParsingProfilePortion(TextClassesEnum.TIME.getName(), TextClassesEnum.TIME.getName(), false, false));
        profile.addPortion(new ParsingProfilePortion(SeparatorEnum.HIFEN.getName(), SeparatorEnum.HIFEN.getSymbol(), false, true));
        profile.addPortion(new ParsingProfilePortion(TextClassesEnum.LEVEL.getName(), TextClassesEnum.LEVEL.getName(), false, false));
        profile.addPortion(new ParsingProfilePortion(SeparatorEnum.COLON.getName(), SeparatorEnum.COLON.getSymbol(), false, true));
        profile.addPortion(new ParsingProfilePortion(TextClassesEnum.METHOD.getName(), TextClassesEnum.METHOD.getName(), false, false));
        profile.addPortion(new ParsingProfilePortion(SeparatorEnum.COLON.getName(), SeparatorEnum.COLON.getSymbol(), false, true));
        profile.addPortion(new ParsingProfilePortion(TextClassesEnum.ID.getName(), TextClassesEnum.ID.getName(), false, false));
        profile.addPortion(new ParsingProfilePortion(SeparatorEnum.COLON.getName(), SeparatorEnum.COLON.getSymbol(), false, true));
        profile.addPortion(new ParsingProfilePortion(TextClassesEnum.MESSAGE.getName(), TextClassesEnum.MESSAGE.getName(), false, false));


        LogFileFunction function = new LogFileFunction(profile);
        for (int i = 0; i < data.length; i++) {
            String result = function.apply(data[i]);

            StringBuilder sb = new StringBuilder();
            sb.append(DATE_BASE).append(i + 1).append(SeparatorEnum.HIFEN.getSymbol())
                    .append(TIME_BASE).append(i + 1).append(SeparatorEnum.HIFEN.getSymbol())
                    .append(LEVEL_BASE).append(i).append(SeparatorEnum.COLON.getSymbol())
                    .append(ORIGIN_BASE).append(i).append(SeparatorEnum.COLON.getSymbol())
                    .append(IDENTIFIER_BASE).append(i).append(SeparatorEnum.COLON.getSymbol())
                    .append(MESSAGE_BASE).append(i).append(System.lineSeparator());
            assertEquals(sb.toString(), result);
        }

    }


    @Test
    public void simpleTestWithTimestamp() {
        ParsingProfile profile = new ParsingProfile();
        profile.addPortion(new ParsingProfilePortion(TextClassesEnum.TIMESTAMP.getName(), TextClassesEnum.TIMESTAMP.getName(), false, false));
        profile.addPortion(new ParsingProfilePortion(SeparatorEnum.HIFEN.getName(), SeparatorEnum.HIFEN.getSymbol(), false, true));
        profile.addPortion(new ParsingProfilePortion(TextClassesEnum.LEVEL.getName(), TextClassesEnum.LEVEL.getName(), false, false));
        profile.addPortion(new ParsingProfilePortion(SeparatorEnum.COLON.getName(), SeparatorEnum.COLON.getSymbol(), false, true));
        profile.addPortion(new ParsingProfilePortion(TextClassesEnum.METHOD.getName(), TextClassesEnum.METHOD.getName(), false, false));
        profile.addPortion(new ParsingProfilePortion(SeparatorEnum.COLON.getName(), SeparatorEnum.COLON.getSymbol(), false, true));
        profile.addPortion(new ParsingProfilePortion(TextClassesEnum.ID.getName(), TextClassesEnum.ID.getName(), false, false));
        profile.addPortion(new ParsingProfilePortion(SeparatorEnum.COLON.getName(), SeparatorEnum.COLON.getSymbol(), false, true));
        profile.addPortion(new ParsingProfilePortion(TextClassesEnum.MESSAGE.getName(), TextClassesEnum.MESSAGE.getName(), false, false));


        LogFileFunction function = new LogFileFunction(profile);
        for (int i = 0; i < data.length; i++) {
            String result = function.apply(data[i]);

            StringBuilder sb = new StringBuilder();
            sb.append(DATE_BASE).append(i + 1).append(" ").append(TIME_BASE).append(i + 1).append(SeparatorEnum.HIFEN.getSymbol())
                    .append(LEVEL_BASE).append(i).append(SeparatorEnum.COLON.getSymbol())
                    .append(ORIGIN_BASE).append(i).append(SeparatorEnum.COLON.getSymbol())
                    .append(IDENTIFIER_BASE).append(i).append(SeparatorEnum.COLON.getSymbol())
                    .append(MESSAGE_BASE).append(i).append(System.lineSeparator());
            assertEquals(sb.toString(), result);
        }

    }

    @Test
    public void testIgnoreAll() {
        ParsingProfile profile = new ParsingProfile();
        profile.addPortion(new ParsingProfilePortion(TextClassesEnum.DATE.getName(), TextClassesEnum.DATE.getName(), true, false));
        profile.addPortion(new ParsingProfilePortion(SeparatorEnum.HIFEN.getName(), SeparatorEnum.HIFEN.getSymbol(), true, true));
        profile.addPortion(new ParsingProfilePortion(TextClassesEnum.TIME.getName(), TextClassesEnum.TIME.getName(), true, false));
        profile.addPortion(new ParsingProfilePortion(SeparatorEnum.HIFEN.getName(), SeparatorEnum.HIFEN.getSymbol(), true, true));
        profile.addPortion(new ParsingProfilePortion(TextClassesEnum.LEVEL.getName(), TextClassesEnum.LEVEL.getName(), true, false));
        profile.addPortion(new ParsingProfilePortion(SeparatorEnum.COLON.getName(), SeparatorEnum.COLON.getSymbol(), true, true));
        profile.addPortion(new ParsingProfilePortion(TextClassesEnum.METHOD.getName(), TextClassesEnum.METHOD.getName(), true, false));
        profile.addPortion(new ParsingProfilePortion(SeparatorEnum.COLON.getName(), SeparatorEnum.COLON.getSymbol(), true, true));
        profile.addPortion(new ParsingProfilePortion(TextClassesEnum.ID.getName(), TextClassesEnum.ID.getName(), true, false));
        profile.addPortion(new ParsingProfilePortion(SeparatorEnum.COLON.getName(), SeparatorEnum.COLON.getSymbol(), true, true));
        profile.addPortion(new ParsingProfilePortion(TextClassesEnum.MESSAGE.getName(), TextClassesEnum.MESSAGE.getName(), true, false));


        LogFileFunction function = new LogFileFunction(profile);
        for (LogLine datum : data) {
            String result = function.apply(datum);

            StringBuilder sb = new StringBuilder();
            sb.append(SeparatorEnum.HIFEN.getSymbol())
                    .append(SeparatorEnum.HIFEN.getSymbol())
                    .append(SeparatorEnum.COLON.getSymbol())
                    .append(SeparatorEnum.COLON.getSymbol())
                    .append(SeparatorEnum.COLON.getSymbol())
                    .append(System.lineSeparator());
            assertEquals(sb.toString(), result);
        }
    }

    @Test
    public void testEmptyParsingProfile() {
        ParsingProfile profile = new ParsingProfile();

        LogFileFunction function = new LogFileFunction(profile);
        for (LogLine datum : data) {
            String result = function.apply(datum);

            StringBuilder sb = new StringBuilder(System.lineSeparator());
            assertEquals(sb.toString(), result);
        }
    }
}
