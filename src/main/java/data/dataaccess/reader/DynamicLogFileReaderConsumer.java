package data.dataaccess.reader;

import domain.entities.common.ToolConfiguration;
import domain.entities.domainobjects.LogLine;
import domain.entities.domainobjects.MetricsProfile;
import domain.entities.domainobjects.MetricsReport;
import domain.entities.domainobjects.ParsingProfile;
import domain.services.ToolConfigurationService;

import java.util.function.Consumer;

public class DynamicLogFileReaderConsumer extends LogFileReaderConsumer {
    private final ToolConfiguration toolConfiguration;
    private Consumer<MetricsReport> reportConsumer;
    private MetricsProfile metricsProfile;

    public DynamicLogFileReaderConsumer(Consumer<MetricsReport> reportConsumer, ParsingProfile parsingProfile, MetricsProfile metricsProfile) {
        super(parsingProfile);
        this.reportConsumer = reportConsumer;
        this.metricsProfile = metricsProfile;
        this.toolConfiguration = ToolConfigurationService.getInstance().getToolConfiguration();
    }

    @Override
    public void accept(String s) {
        super.accept(s);
        MetricsReport report = new MetricsReport(metricsProfile, logLines.toArray(LogLine[]::new), toolConfiguration.getStopWords());
        reportConsumer.accept(report);
    }
}
