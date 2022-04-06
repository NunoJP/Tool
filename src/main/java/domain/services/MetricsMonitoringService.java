package domain.services;

import data.dataaccess.reader.DynamicLogFileReaderConsumer;
import data.dataaccess.reader.LogFileReader;
import domain.entities.domainobjects.MetricsProfile;
import domain.entities.domainobjects.MetricsReport;
import domain.entities.domainobjects.ParsingProfile;

import java.util.function.Consumer;

public class MetricsMonitoringService {
    private Consumer<MetricsReport> reportConsumer;
    private LogFileReader logFileReader;
    private ParsingProfile parsingProfile;
    private MetricsProfile metricsProfile;
    private DynamicLogFileReaderConsumer logLineConsumer;

    public MetricsMonitoringService(Consumer<MetricsReport> reportConsumer, LogFileReader logFileReader,
                                    ParsingProfile parsingProfile, MetricsProfile metricsProfile) {
        this.reportConsumer = reportConsumer;
        this.logFileReader = logFileReader;
        this.parsingProfile = parsingProfile;
        this.metricsProfile = metricsProfile;
    }

    public void start() {
        logLineConsumer = new DynamicLogFileReaderConsumer(reportConsumer, parsingProfile, metricsProfile);
        logFileReader.readDynamic((a) -> {}, logLineConsumer);
    }

    public void stop() {
        logFileReader.stop();
    }
}
