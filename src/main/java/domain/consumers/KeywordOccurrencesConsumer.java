package domain.consumers;

import domain.entities.common.Keyword;
import domain.entities.domainobjects.LogLine;
import domain.entities.domainobjects.MetricsProfile;
import general.util.Pair;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class KeywordOccurrencesConsumer implements IMetricsReportConsumer {

    private final HashMap<Keyword, Integer> wordsFromMessages;
    private final HashMap<Keyword, List<Pair<Long, Date>>> keywordsWithDate;
    private final MetricsProfile metricsProfile;

    public KeywordOccurrencesConsumer(MetricsProfile metricsProfile) {
        this.metricsProfile = metricsProfile;
        wordsFromMessages = new HashMap<>();
        keywordsWithDate =  new HashMap<>();
    }

    @Override
    public void processLine(LogLine logLine) {
        if (logLine != null && logLine.getMessage() != null) {
            int ctr = 0;
            for (Keyword extractedKwd : metricsProfile.getKeywords()) {
                String logLineMessage = extractedKwd.isCaseSensitive() ? logLine.getMessage() : logLine.getMessage().toLowerCase();
                String keywordText = extractedKwd.isCaseSensitive() ? extractedKwd.getKeywordText() : extractedKwd.getKeywordText().toLowerCase();

                // first check
                int idx = logLineMessage.indexOf(keywordText);

                // keep checking for hits
                while (idx != -1) {
                    // we only increment when there was an index found, the first increment comes from the "first check",
                    // if any, else the ctr is always 0
                    ctr++;
                    idx = logLineMessage.indexOf(keywordText, idx+1);
                }
                int count = wordsFromMessages.getOrDefault(extractedKwd, 0);
                wordsFromMessages.put(extractedKwd, count + ctr);
                List<Pair<Long, Date>> pairs = keywordsWithDate.getOrDefault(extractedKwd, new ArrayList<>());
                pairs.add(Pair.of((long) ctr, logLine.getTemporalRepresentation()));
                keywordsWithDate.put(extractedKwd, pairs);
                ctr = 0;
            }

        }
    }

    public HashMap<Keyword, Integer> getKwdOccurrences() {
        return wordsFromMessages;
    }

    public HashMap<Keyword, List<Pair<Long, Date>>> getKeywordsWithDate() {
        return keywordsWithDate;
    };

}
