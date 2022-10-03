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
        System.out.println("MetricsMonitoringService 1");
        logLineConsumer = new DynamicLogFileReaderConsumer((this::updateLocalReport), parsingProfile, metricsProfile);
        System.out.println("MetricsMonitoringService 2");
        LogFileReaderWrapper logFileReaderWrapper = new LogFileReaderWrapper(logFileReader, logLineConsumer);
        (new Thread(logFileReaderWrapper)).start();


        System.out.println("MetricsMonitoringService 3");
        while (keepGoing.get()) {
            System.out.println("MetricsMonitoringService 4");
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                LOGGER.log(Level.SEVERE, "Error in thread sleep at MetricsMonitoringService", e);
            }
            System.out.println("MetricsMonitoringService 5");
            if (wasChanged) {
                System.out.println("changed");
                reportConsumer.accept(getCurrentReportAndMarkUnchanged());
            }
            System.out.println("MetricsMonitoringService 6");
        }
    }

    public void stop() {
        keepGoing.set(false);
        logFileReader.stop();
    }

    private void updateLocalReport(MetricsReport report){
//        System.out.println("MetricsMonitoringService 7");
        this.previousReport = currentReport;
        this.currentReport = report;
        this.wasChanged = true;
    }

    private MetricsReport getCurrentReportAndMarkUnchanged() {
        this.wasChanged = false;
        return currentReport;
    }
}
