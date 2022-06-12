package domain.entities.domainobjects;

import domain.consumers.IMetricsReportConsumer;
import domain.consumers.KeywordOccurrencesConsumer;
import domain.consumers.LogLevelConsumer;
import domain.consumers.MostCommonWordsConsumer;
import domain.consumers.WordsInMessageOccurrencesConsumer;
import domain.entities.common.Keyword;
import domain.entities.common.ThresholdUnitEnum;
import domain.entities.common.Warning;
import general.util.DateTimeUtils;
import general.util.Pair;
import presentation.common.GuiMessages;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MetricsReport {

    private final MetricsProfile metricsProfile;
    private final LogLine[] data;
    private final ArrayList<Warning> warningMessages = new ArrayList<>();
    private ArrayList<IMetricsReportConsumer> consumers;
    private List<Pair<Long, Date>> fileSizeData;
    private LogLevelConsumer logLevelConsumer;
    private MostCommonWordsConsumer mostCommonWordsConsumer;
    private WordsInMessageOccurrencesConsumer wordsInMessageOccurrencesConsumer;
    private KeywordOccurrencesConsumer keywordOccurrencesConsumer;

    public MetricsReport(MetricsProfile metricsProfile, LogLine[] data, String [] stopWords) {
        this.metricsProfile = metricsProfile;
        this.data = data;
        setupConsumers(metricsProfile, stopWords);
        processData();
    }


    // Sets up the consumers for the class, avoiding unnecessary if checks per line
    private void setupConsumers(MetricsProfile metricsProfile, String[] stopWords) {
        consumers = new ArrayList<>();
        logLevelConsumer = new LogLevelConsumer();
        wordsInMessageOccurrencesConsumer = new WordsInMessageOccurrencesConsumer(stopWords);

        consumers.add(logLevelConsumer);
        consumers.add(wordsInMessageOccurrencesConsumer);
        if(metricsProfile.hasKeywordHistogram() ||
                metricsProfile.hasKeywordOverTime() ||
                metricsProfile.hasKeywordThreshold()) {
            keywordOccurrencesConsumer = new KeywordOccurrencesConsumer(metricsProfile);
            consumers.add(keywordOccurrencesConsumer);
        }
        if(metricsProfile.hasMostCommonWords()) {
            mostCommonWordsConsumer = new MostCommonWordsConsumer(stopWords);
            consumers.add(mostCommonWordsConsumer);
        }
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

    private void processData() {
        for (LogLine logLine : data) {
            consumers.parallelStream().forEach(consumer -> consumer.processLine(logLine));
        }
    }

    public String[][] getKwdThresholdData() {
        HashMap<Keyword, Integer> kwdOccurrences = keywordOccurrencesConsumer.getKwdOccurrences();

        ArrayList<String[]> res = new ArrayList<>();

        for (Map.Entry<Keyword, Integer> keywordIntegerEntry : kwdOccurrences.entrySet()) {
            Optional<String[]> strings = processResult(evaluate(keywordIntegerEntry.getKey(),
                    keywordIntegerEntry.getValue(), wordsInMessageOccurrencesConsumer.getTotalNumberOfNonStopWords()));
            strings.ifPresent(res::add);
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


    public String[][] getLogLevelData() {
        return logLevelConsumer.getLogLevelData();
    }

    public String[][] getMostCommonWordsData() {
        return mostCommonWordsConsumer.getMostCommonWordsData();
    }

    public List<Pair<Long, Date>> getFileSizeData() {
        return fileSizeData;
    }

    public HashMap<Keyword, Integer> getKwdOccurrences() {
        return keywordOccurrencesConsumer.getKwdOccurrences();
    }

    public ArrayList<Warning> getWarningsData() {
        return warningMessages;
    }

    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // Utils
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++


    private String makeThresholdMessage(EvaluationResult result) {
        Keyword standard = result.getStandard();
        return String.format(GuiMessages.THRESHOLD_VALUE_BASE, standard.getKeywordText(),
                standard.getThresholdValue(), result.actualValue());
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
