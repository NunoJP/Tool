package common;

import domain.entities.domainobjects.LogLine;
import presentation.common.GuiConstants;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public abstract class LogLineTests {
    protected final DateFormat dateFormat = new SimpleDateFormat(GuiConstants.DATE_FORMATTER);
    protected final DateFormat timeFormat = new SimpleDateFormat(GuiConstants.TIME_FORMATTER);
    protected final DateFormat timestampFormat = new SimpleDateFormat(GuiConstants.DATE_TIME_FORMATTER);
    protected LogLine[] data;
    protected static final String LEVEL_BASE = "Level";
    protected static final String ORIGIN_BASE = "Origin";
    protected static final String MESSAGE_BASE = "Message";
    protected static final String IDENTIFIER_BASE = "Identifier";
    protected static final String DATE_BASE = "2021-01-0"; // missing the day
    protected static final String TIME_BASE = "12:10:10.00"; // missing the last millisecond


    public void setupLogLineData() throws ParseException {
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
}
