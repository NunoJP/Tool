package data.dataaccess.reader;

import domain.entities.common.ToolConfiguration;
import domain.entities.domainobjects.LogLine;
import domain.entities.domainobjects.MetricsProfile;
import domain.entities.domainobjects.MetricsReport;
import domain.entities.domainobjects.ParsingProfile;
import domain.services.ToolConfigurationService;
import general.util.Pair;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

public class DynamicLogFileReaderConsumer implements Consumer<Pair<String, Long>> {
    private final ToolConfiguration toolConfiguration;
    private final LogFileReaderConsumer logFileReaderConsumer;
    private Consumer<MetricsReport> reportConsumer;
    private MetricsProfile metricsProfile;
    private List<Pair<Long, Date>> fileSizeData;

    public DynamicLogFileReaderConsumer(Consumer<MetricsReport> reportConsumer, ParsingProfile parsingProfile, MetricsProfile metricsProfile) {
        logFileReaderConsumer = new LogFileReaderConsumer(parsingProfile);
        this.reportConsumer = reportConsumer;
        this.metricsProfile = metricsProfile;
        this.toolConfiguration = ToolConfigurationService.getInstance().getToolConfiguration();
        this.fileSizeData = new ArrayList<>();
    }

    @Override
    public void accept(Pair<String, Long> stringLongPair) {
        logFileReaderConsumer.accept(stringLongPair.getLeft());
        fileSizeData.add(Pair.of(stringLongPair.getRight(), new Date()));
        MetricsReport report = new MetricsReport(metricsProfile, logFileReaderConsumer.getLines(), toolConfiguration.getStopWords(), fileSizeData);
        reportConsumer.accept(report);
    }


    public LogLine[] getLines() {
        return logFileReaderConsumer.getLines();
    }
}
