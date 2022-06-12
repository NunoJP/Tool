package domain.consumers;

import domain.entities.domainobjects.LogLine;

import java.util.HashMap;

public class WordsInMessageOccurrencesConsumer implements IMetricsReportConsumer{

    private final String[] stopWords;
    private final HashMap<String, Integer> wordsOccs = new HashMap<>();
    private int totalNumberOfOccs = 0;

    public WordsInMessageOccurrencesConsumer(String [] stopWords) {
        this.stopWords = stopWords;
    }

    @Override
    public void processLine(LogLine logLine) {
        if (logLine != null && logLine.getMessage() != null) {
            String[] split = logLine.getMessage().split("\\s+|,|\\)|\\(|:");
            for (String s : split) {
                if(isNotStopWord(s)) {
                    int count = wordsOccs.getOrDefault(s, 0);
                    wordsOccs.put(s, count + 1);
                    this.totalNumberOfOccs++;
                }
            }
        }
    }

    public HashMap<String, Integer> getKwdOccurrences() {
        return wordsOccs;
    }

    private boolean isNotStopWord(String s) {
        for (String stopWord : stopWords) {
            if(stopWord.equalsIgnoreCase(s)){
                return false;
            }
        }
        return true;
    }

    public int getTotalNumberOfNonStopWords() {
        return totalNumberOfOccs;
    }
}
