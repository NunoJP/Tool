package domain.consumers;

import domain.entities.common.Keyword;
import domain.entities.domainobjects.LogLine;
import domain.entities.domainobjects.MetricsProfile;
import general.strutures.SuffixTree;

import java.util.HashMap;

public class KeywordOccurrencesConsumer implements IMetricsReportConsumer {

    private HashMap<Keyword, Integer> wordsFromMessages;
    private int totalNumberOfNonStopWords = 0;
    private MetricsProfile metricsProfile;

    public KeywordOccurrencesConsumer(MetricsProfile metricsProfile) {
        this.metricsProfile = metricsProfile;
        wordsFromMessages = new HashMap<>();
    }

    @Override
    public void processLine(LogLine logLine) {
        if (logLine != null && logLine.getMessage() != null) {
            SuffixTree st = new SuffixTree(logLine.getMessage(), true);
            for (Keyword extractedKwd : metricsProfile.getKeywords()) {
                int size = st.getIndexes(extractedKwd.getKeywordText(), extractedKwd.isCaseSensitive()).size();
                int count = wordsFromMessages.getOrDefault(extractedKwd, 0);
                wordsFromMessages.put(extractedKwd, count + size);
            }
            this.totalNumberOfNonStopWords++;
        }
    }

    public HashMap<Keyword, Integer> getKwdOccurrences() {
        return wordsFromMessages;
    }

    public int getTotalNumberOfNonStopWords() {
        return totalNumberOfNonStopWords;
    }
}
