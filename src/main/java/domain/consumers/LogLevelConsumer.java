package domain.consumers;

import domain.entities.domainobjects.LogLine;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.HashMap;

public class LogLevelConsumer implements IMetricsReportConsumer{

    HashMap<String, Integer> occs = new HashMap<>();
    private long totalNumberOfLines;

    public LogLevelConsumer() {
        totalNumberOfLines = 0;
    }

    @Override
    public void processLine(LogLine logLine) {
        if (logLine != null && logLine.getLevel() != null) {
            int count = occs.getOrDefault(logLine.getLevel(), 0);
            occs.put(logLine.getLevel(), count + 1);
            totalNumberOfLines++;
        }
    }

    public String[][] getLogLevelData() {
        BigDecimal size = new BigDecimal(totalNumberOfLines);
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
}
