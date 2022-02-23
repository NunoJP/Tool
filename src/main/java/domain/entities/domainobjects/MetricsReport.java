package domain.entities.domainobjects;

import general.util.DateTimeUtils;
import presentation.common.GuiConstants;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

public class MetricsReport {

    private DateFormat dateFormat = new SimpleDateFormat(GuiConstants.DATE_FORMATTER);
    private DateFormat timeFormat = new SimpleDateFormat(GuiConstants.TIME_FORMATTER);
    private DateFormat timeStampFormat = new SimpleDateFormat(GuiConstants.DATE_TIME_FORMATTER);
    private MetricsProfile metricsProfile;
    private LogLine[] data;
    private String[] stopWords;

    public MetricsReport(MetricsProfile metricsProfile, LogLine[] data, String [] stopWords) {
        this.metricsProfile = metricsProfile;
        this.data = data;
        this.stopWords = stopWords;
    }

    public MetricsReport(MetricsProfile metricsProfile, LogLine[] data) {
        this(metricsProfile, data, new String[0]);
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
            if (datum != null && datum.getLevel() != null) {
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
        HashMap<String, Integer> occs = new HashMap<>();

        for (LogLine datum : data) {
            if (datum != null && datum.getMessage() != null) {
                String[] split = datum.getMessage().split("\\s+");
                for (String s : split) {
                    if(isNotStopWord(s)) {
                        int count = occs.getOrDefault(s, 0);
                        occs.put(s, count + 1);
                    }
                }
            }
        }

        String [][] res = new String [occs.size()][2];
        int idx = 0;
        for (String s : occs.keySet()) {
            res[idx][0] = s;
            res[idx][1] = String.valueOf(occs.get(s));
            idx++;
        }

        // sort by descending order
        return Arrays.stream(res).sorted((arr1, arr2) -> {
            int i2 = Integer.parseInt(arr2[1]);
            int i1 = Integer.parseInt(arr1[1]);
            return i2 - i1;
        }).toArray(String[][]::new);
    }

    private boolean isNotStopWord(String s) {
        for (String stopWord : stopWords) {
            if(stopWord.equalsIgnoreCase(s)){
                return false;
            }
        }
        return true;
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
        return metricsProfile.getOriginFile();
    }

}
