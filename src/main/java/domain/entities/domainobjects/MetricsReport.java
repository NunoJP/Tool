package domain.entities.domainobjects;

import general.util.DateTimeUtils;
import presentation.common.GuiConstants;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MetricsReport {

    private DateFormat dateFormat = new SimpleDateFormat(GuiConstants.DATE_FORMATTER);
    private DateFormat timeFormat = new SimpleDateFormat(GuiConstants.TIME_FORMATTER);
    private DateFormat timeStampFormat = new SimpleDateFormat(GuiConstants.DATE_TIME_FORMATTER);
    private MetricsProfile metricsProfile;
    private LogLine[] data;

    public MetricsReport(MetricsProfile metricsProfile, LogLine[] data) {
        this.metricsProfile = metricsProfile;
        this.data = data;
    }

    public String[][] getKwdThresholdData() {
        if(metricsProfile.isHasKeywordThreshold()){
            return new String [][] {
                    {"Error", "1"},
                    {"Warning", "2"},
                    {"Info", "3"},
                    {"Debug", "12"},
            };
        } else {
            return new String [][] {};
        }
    }

    public String[][] getLogLevelData() {
        return new String [][] {
                {"Error", "10%"},
                {"Warning", "20%"},
                {"Info", "30%"},
                {"Debug", "10%"},
        };
    }

    public String[][] getMostCommonWordsData() {
        return new String [][] {
                {"Error", "1"},
                {"Warning", "2"},
                {"Info", "3"},
                {"Debug", "12"},
        };
    }

    public String[][] getWarningsData() {
        return new String [][] {
                {"Error", "Problem"},
                {"Warning", "Medium issue"},
                {"Info", "Doing things"},
                {"Debug", "Doing things Verbosely"},
                {"Error", "Problem"},
                {"Warning", "Medium issue"},
                {"Info", "Doing things"},
                {"Debug", "Doing things Verbosely"},
                {"Error", "Problem"},
                {"Warning", "Medium issue"},
                {"Info", "Doing things"},
                {"Debug", "Doing things Verbosely"},
                {"Error", "Problem"},
                {"Warning", "Medium issue"},
                {"Info", "Doing things"},
                {"Debug", "Doing things Verbosely"},
                {"Error", "Problem"},
                {"Warning", "Medium issue"},
                {"Info", "Doing things"},
                {"Debug", "Doing things Verbosely"},
                {"Error", "Problem"},
                {"Warning", "Medium issue"},
                {"Info", "Doing things"},
                {"Debug", "Doing things Verbosely"},
                {"Error", "Problem"},
                {"Warning", "Medium issue"},
                {"Info", "Doing things"},
                {"Debug", "Doing things Verbosely"},
        };
    }

    public Date getStartDate() throws ParseException {

        // As the log lines come ordered the first element is the most recent one
        return DateTimeUtils.getDateTimeFromLogLine(data[0]);
    }


    public Date getEndDate() throws ParseException {

        // As the log lines come ordered the last element is the most recent one
        return DateTimeUtils.getDateTimeFromLogLine(data[data.length - 1]);
    }

    public String getFileName(){
        return "LogFile";
    }

}
