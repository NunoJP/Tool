package data.dataaccess.writer;

import common.LogLineTests;
import domain.entities.common.ParsingProfilePortion;
import domain.entities.common.SeparatorEnum;
import domain.entities.common.TextClassesEnum;
import domain.entities.domainobjects.LogLine;
import domain.entities.domainobjects.ParsingProfile;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;

import static org.junit.Assert.assertEquals;


public class LogFileWriterFunctionTests extends LogLineTests {

    @Before
    public void setup() throws ParseException {
        super.setupLogLineData();
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
