package domain.consumers;

import domain.entities.domainobjects.LogLine;

public interface IMetricsReportConsumer {

    void processLine(LogLine logLine);

}
