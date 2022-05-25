package domain.entities.domainobjects;

import domain.entities.common.Keyword;
import domain.entities.common.ThresholdUnitEnum;
import domain.entities.common.Warning;
import general.util.DateTimeUtils;
import general.util.Pair;
import presentation.common.GuiConstants;
import presentation.common.GuiMessages;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class MetricsReport {

    private DateFormat dateFormat = new SimpleDateFormat(GuiConstants.DATE_FORMATTER);
    private DateFormat timeFormat = new SimpleDateFormat(GuiConstants.TIME_FORMATTER);
    private DateFormat timeStampFormat = new SimpleDateFormat(GuiConstants.DATE_TIME_FORMATTER);
    private MetricsProfile metricsProfile;
    private LogLine[] data;
    private String[] stopWords;
    private ArrayList<Warning> warningMessages = new ArrayList<>();
    private HashMap<String, Integer> kwdOccs;
    private int totalNumberOfOccs = 0;
    private List<Pair<Long, Date>> fileSizeData;

    public MetricsReport(MetricsProfile metricsProfile, LogLine[] data, String [] stopWords) {
        this.metricsProfile = metricsProfile;
        this.data = data;
        this.stopWords = stopWords;
    }

    public MetricsReport(MetricsProfile metricsProfile, LogLine[] data, String [] stopWords,  List<Pair<Long, Date>> fileSizeData) {
        this(metricsProfile, data, stopWords);
        this.fileSizeData = fileSizeData;
    }

    public MetricsReport(MetricsProfile metricsProfile, LogLine[] data) {
        this(metricsProfile, data, new String[0]);
    }

    public LogLine[] getData() {
        return data;
    }


    public List<Pair<Long, Date>> getFileSizeData() {
        return fileSizeData;
    }

    public HashMap<String, Integer> getKwdData() {
        if(kwdOccs != null) {
            return kwdOccs;
        }

        kwdOccs = new HashMap<>();
        this.totalNumberOfOccs = 0;
        for (LogLine datum : data) {
            if (datum != null && datum.getMessage() != null) {
                String[] split = datum.getMessage().split("\\s+|,|\\)|\\(|:");
                for (String s : split) {
                    if(isNotStopWord(s)) {
                        int count = kwdOccs.getOrDefault(s, 0);
                        kwdOccs.put(s, count + 1);
                        this.totalNumberOfOccs++;
                    }
                }
            }
        }
        return kwdOccs;
    }

    public String[][] getKwdThresholdData() {
        getKwdData();

        ArrayList<String[]> res = new ArrayList<>();
        int idx = 0;
        for (String s : kwdOccs.keySet()) {
            // do the evaluation only if there is a matching keyword
            ArrayList<Keyword> kwds = wordMatchesKeyword(s);
            if(!kwds.isEmpty()) {
                for (Keyword kwd : kwds) {
                    Optional<String[]> strings = processResult(evaluate(kwd, kwdOccs.get(s), totalNumberOfOccs));
                    strings.ifPresent(res::add);
                }
            }
            idx++;
        }

        return res.toArray(String[][]::new);
    }

    private Optional<String[]> processResult(Optional<EvaluationResult> evaluation) {
        if (evaluation.isPresent()) {
            EvaluationResult result = evaluation.get();
            if(result.getWarning().isPresent()) {
                warningMessages.add(result.getWarning().get());
            }
            String[] toRet = new String[] { result.getStandard().getKeywordText(), makeThresholdMessage(result) };
            return Optional.of(toRet);
        }
        return Optional.empty();
    }

    private String makeThresholdMessage(EvaluationResult result) {
        Keyword standard = result.getStandard();
        return String.format(GuiMessages.THRESHOLD_VALUE_BASE, standard.getKeywordText(),
                standard.getThresholdValue(), result.actualValue());
    }

    private ArrayList<Keyword> wordMatchesKeyword(String s) {
        ArrayList<Keyword> matches = new ArrayList<>();
        for (Keyword keyword : metricsProfile.getKeywords()) {
            if(keyword.isCaseSensitive()) {
                if(keyword.getKeywordText().equals(s)) {
                    matches.add(keyword);
                }
            } else {
                if(keyword.getKeywordText().equalsIgnoreCase(s)) {
                    matches.add(keyword);
                }
            }
        }
        return matches;
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

    public ArrayList<Warning> getWarningsData() {
        return warningMessages;
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


    protected Optional<EvaluationResult> evaluate(Keyword standard, Integer currValue, Integer total) {
        EvaluationResult evaluationResult = new EvaluationResult(standard, String.valueOf(currValue));
        if(ThresholdUnitEnum.PERCENTAGE.equals(standard.getThresholdUnit())) {
            BigDecimal curr = new BigDecimal(currValue);
            BigDecimal tot = new BigDecimal(total);
            BigDecimal percentage = curr.divide(tot, 4, RoundingMode.HALF_EVEN);
            switch (standard.getThresholdType()) {
                case EQUAL_TO:
                    if(standard.getThresholdValue().compareTo(percentage) == 0) {
                        evaluationResult.setThresholdMet();
                    }
                    break;
                case BIGGER_THAN:
                    if(standard.getThresholdValue().compareTo(percentage) > 0) {
                        evaluationResult.setThresholdMet();
                    }
                    break;
                case BIGGER_OR_EQUAL_THAN:
                    if(standard.getThresholdValue().compareTo(percentage) >= 0) {
                        evaluationResult.setThresholdMet();
                    }
                    break;
                case SMALLER_OR_EQUAL_THAN:
                    if(standard.getThresholdValue().compareTo(percentage) <= 0) {
                        evaluationResult.setThresholdMet();
                    }
                    break;
                case SMALLER_THAN:
                    if(standard.getThresholdValue().compareTo(percentage) < 0) {
                        evaluationResult.setThresholdMet();
                    }
                    break;
            }
            return Optional.of(evaluationResult);
        } else if(ThresholdUnitEnum.OCCURRENCES.equals(standard.getThresholdUnit())) {
            switch (standard.getThresholdType()) {
                case EQUAL_TO:
                    if(standard.getThresholdValue().intValue() == currValue) {
                        evaluationResult.setThresholdMet();
                    }
                    break;
                case BIGGER_THAN:
                    if(standard.getThresholdValue().intValue() < currValue) {
                        evaluationResult.setThresholdMet();
                    }
                    break;
                case BIGGER_OR_EQUAL_THAN:
                    if(standard.getThresholdValue().intValue() <= currValue) {
                        evaluationResult.setThresholdMet();
                    }
                    break;
                case SMALLER_OR_EQUAL_THAN:
                    if(standard.getThresholdValue().intValue() >= currValue) {
                        evaluationResult.setThresholdMet();
                    }
                    break;
                case SMALLER_THAN:
                    if(standard.getThresholdValue().intValue() > currValue) {
                        evaluationResult.setThresholdMet();
                    }
                    break;
            }
            return Optional.of(evaluationResult);
        }
        return Optional.empty();
    }


}
