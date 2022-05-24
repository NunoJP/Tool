package data.dataaccess.reader;

import domain.entities.common.ToolConfiguration;
import domain.entities.domainobjects.LogLine;
import domain.entities.domainobjects.MetricsProfile;
import domain.entities.domainobjects.MetricsReport;
import domain.entities.domainobjects.ParsingProfile;
import domain.services.ToolConfigurationService;
import general.util.Pair;

import java.time.Instant;
import java.util.Date;
import java.util.function.Consumer;

import static java.time.temporal.ChronoUnit.MILLIS;

public class DynamicLogFileReaderConsumer implements Consumer<Pair<String, Long>> {
    private final ToolConfiguration toolConfiguration;
    private final LogFileReaderConsumer logFileReaderConsumer;
    private Consumer<MetricsReport> reportConsumer;
    private MetricsProfile metricsProfile;
    private Pair<Long, Date> latestFileSizeAndTime;
    int numberOfLines = 20000;
    int numberOfMilliseconds = 1000;
    int currentNumberOfLines = 0;
    Instant startTime;

    public DynamicLogFileReaderConsumer(Consumer<MetricsReport> reportConsumer, ParsingProfile parsingProfile, MetricsProfile metricsProfile) {
        logFileReaderConsumer = new LogFileReaderConsumer(parsingProfile);
        this.reportConsumer = reportConsumer;
        this.metricsProfile = metricsProfile;
        this.toolConfiguration = ToolConfigurationService.getInstance().getToolConfiguration();
        this.startTime = Instant.now();
    }

    @Override
    public void accept(Pair<String, Long> stringLongPair) {
        logFileReaderConsumer.accept(stringLongPair.getLeft());
        currentNumberOfLines++;
        System.out.println("currentNumberOfLines: " + currentNumberOfLines);
        if(currentNumberOfLines >= numberOfLines || Instant.now().isAfter(startTime.plus(numberOfMilliseconds, MILLIS))) {
            // TODO only call this after a number of messages or a set time - probably get the values from the toolConfiguration
            // TODO add file size information
            MetricsReport report = new MetricsReport(metricsProfile, logFileReaderConsumer.getLines(), toolConfiguration.getStopWords());
            latestFileSizeAndTime = Pair.of(stringLongPair.getRight(), new Date());
            reportConsumer.accept(report);
            currentNumberOfLines = 0;
            startTime = Instant.now();
        }
    }


    public LogLine[] getLines() {
        return logFileReaderConsumer.getLines();
    }
}
