package domain.consumers;

import domain.entities.domainobjects.LogLine;

import java.util.Arrays;
import java.util.HashMap;

public class MostCommonWordsConsumer implements IMetricsReportConsumer{

    private final String[] stopWords;
    private final HashMap<String, Integer> occs = new HashMap<>();

    public MostCommonWordsConsumer(String [] stopWords) {
        this.stopWords = stopWords;
    }

    @Override
    public void processLine(LogLine logLine) {
        if (logLine != null && logLine.getMessage() != null) {
            String[] split = logLine.getMessage().split("\\s+");
            for (String s : split) {
                if(isNotStopWord(s)) {
                    int count = occs.getOrDefault(s, 0);
                    occs.put(s, count + 1);
                }
            }
        }
    }

    private boolean isNotStopWord(String s) {
        for (String stopWord : stopWords) {
            if(stopWord.equalsIgnoreCase(s)){
                return false;
            }
        }
        return true;
    }

    public String[][] getMostCommonWordsData() {
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
}
