package domain.entities.domainobjects;

import general.util.DateTimeUtils;
import presentation.common.GuiConstants;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

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

        HashMap<String, Integer> occs = new HashMap<>();

        for (LogLine datum : data) {
            if (datum.getLevel() != null) {
                int count = occs.getOrDefault(datum.getLevel(), 0);
                occs.put(datum.getLevel(), count + 1);
            }
        }

        BigDecimal size = new BigDecimal(data.length);
        String [][] res = new String [occs.size()][2];
        int idx = 0;
        for (String s : occs.keySet()) {
            res[idx][0] = s;
            BigDecimal val = new BigDecimal(occs.get(s));
            res[idx][1] = new DecimalFormat("#,##0.00 %").format(val.divide(size, 4, RoundingMode.HALF_EVEN).doubleValue());
            idx++;
        }
        return res;
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
