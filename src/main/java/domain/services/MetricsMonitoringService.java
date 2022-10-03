package domain.services;

import data.dataaccess.reader.DynamicLogFileReaderConsumer;
import data.dataaccess.reader.LogFileReader;
import data.dataaccess.reader.LogFileReaderWrapper;
import domain.entities.domainobjects.MetricsProfile;
import domain.entities.domainobjects.MetricsReport;
import domain.entities.domainobjects.ParsingProfile;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MetricsMonitoringService {
    private Consumer<MetricsReport> reportConsumer;
    private LogFileReader logFileReader;
    private ParsingProfile parsingProfile;
    private MetricsProfile metricsProfile;
    private DynamicLogFileReaderConsumer logLineConsumer;
    private MetricsReport previousReport;
    private MetricsReport currentReport;
    private boolean wasChanged = true;
    private AtomicBoolean keepGoing = new AtomicBoolean(true);
    private static final Logger LOGGER = Logger.getLogger(MetricsMonitoringService.class.getName());

    public MetricsMonitoringService(Consumer<MetricsReport> reportConsumer, LogFileReader logFileReader,
                                    ParsingProfile parsingProfile, MetricsProfile metricsProfile) {
        this.reportConsumer = reportConsumer;
        this.logFileReader = logFileReader;
        this.parsingProfile = parsingProfile;
        this.metricsProfile = metricsProfile;
    }

    public void start() {
        logLineConsumer = new DynamicLogFileReaderConsumer((this::updateLocalReport), parsingProfile, metricsProfile);
        LogFileReaderWrapper logFileReaderWrapper = new LogFileReaderWrapper(logFileReader, logLineConsumer);
        (new Thread(logFileReaderWrapper)).start();

        while (keepGoing.get()) {
            try {
                Thread.sleep(600);
            } catch (InterruptedException e) {
                LOGGER.log(Level.SEVERE, "Error in thread sleep at MetricsMonitoringService", e);
            }
            if (wasChanged) {
                System.out.println("changed");
                reportConsumer.accept(getCurrentReportAndMarkUnchanged());
            }
        }
    }

    public void stop() {
        keepGoing.set(false);
        logFileReader.stop();
    }

    private void updateLocalReport(MetricsReport report){
        this.previousReport = currentReport;
        this.currentReport = report;
        this.wasChanged = true;
    }

    private MetricsReport getCurrentReportAndMarkUnchanged() {
        this.wasChanged = false;
        return currentReport;
    }
}
