package general.util;

import domain.entities.domainobjects.LogLine;
import presentation.common.GuiConstants;
import presentation.common.GuiMessages;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtils {

    public static final DateFormat dateFormat = new SimpleDateFormat(GuiConstants.DATE_FORMATTER);
    public static final DateFormat timeFormat = new SimpleDateFormat(GuiConstants.TIME_FORMATTER);
    public static final DateFormat timeStampFormat = new SimpleDateFormat(GuiConstants.DATE_TIME_FORMATTER);


    public static Date getDateTimeFromLogLine(LogLine logLine) throws ParseException {
        if (logLine.getTimestamp() != null) {
            return logLine.getTimestamp();
        }

        String date = null;
        String time = null;
        if (logLine.getDate() != null) {
            date = dateFormat.format(logLine.getDate());
        }
        if (logLine.getTime() != null) {
            time = timeFormat.format(logLine.getTime());
        }

        if (date != null && time != null) {
            return timeStampFormat.parse(date + " " + time);
        } else if(date != null) {
            return dateFormat.parse(date);
        } else if(time != null) {
            return timeFormat.parse(time);
        }
        throw new RuntimeException(GuiMessages.TIMESTAMP_DATE_TIME_MISSING);
    }

    public static Date getDateFromLogLine(LogLine logLine) throws ParseException {
        if (logLine.getTimestamp() != null) {
            return dateFormat.parse(dateFormat.format(logLine.getTimestamp()));
        }

        if (logLine.getDate() != null) {
            return logLine.getDate();
        }

        throw new RuntimeException(GuiMessages.TIMESTAMP_DATE_TIME_MISSING);
    }

    public static Date getTimeFromLogLine(LogLine logLine) throws ParseException {
        if (logLine.getTimestamp() != null) {
            return timeFormat.parse(timeFormat.format(logLine.getTimestamp()));
        }

        if (logLine.getTime() != null) {
            return logLine.getTime();
        }

        throw new RuntimeException(GuiMessages.TIMESTAMP_DATE_TIME_MISSING);
    }


}
