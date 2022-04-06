package data.dataaccess.reader;

import domain.entities.common.ToolConfiguration;
import domain.entities.domainobjects.LogLine;
import domain.entities.domainobjects.MetricsProfile;
import domain.entities.domainobjects.MetricsReport;
import domain.entities.domainobjects.ParsingProfile;
import domain.services.ToolConfigurationService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class DynamicLogFileReaderConsumer implements Consumer<String> {
    private final ToolConfiguration toolConfiguration;
    private final LogFileReaderConsumer logFileReaderConsumer;
    private Consumer<MetricsReport> reportConsumer;
    private ParsingProfile parsingProfile;
    private MetricsProfile metricsProfile;
    private ArrayList<LogLine> existingData = new ArrayList<>();

    public DynamicLogFileReaderConsumer(Consumer<MetricsReport> reportConsumer, ParsingProfile parsingProfile, MetricsProfile metricsProfile) {
        this.reportConsumer = reportConsumer;
        this.parsingProfile = parsingProfile;
        this.metricsProfile = metricsProfile;
        this.toolConfiguration = ToolConfigurationService.getInstance().getToolConfiguration();
        this.logFileReaderConsumer = new LogFileReaderConsumer(this.parsingProfile);
    }

    @Override
    public void accept(String s) {
        logFileReaderConsumer.accept(s);
        existingData.addAll(Arrays.stream(logFileReaderConsumer.getLines()).collect(Collectors.toList()));
        MetricsReport report = new MetricsReport(metricsProfile, existingData.toArray(LogLine[]::new), toolConfiguration.getStopWords());
        reportConsumer.accept(report);
        logFileReaderConsumer.clearLines();
    }
}
