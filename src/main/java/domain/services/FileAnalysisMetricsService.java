package domain.services;

import domain.entities.domainobjects.LogLine;
import domain.entities.domainobjects.MetricsProfile;
import domain.entities.domainobjects.MetricsReport;

public class FileAnalysisMetricsService {


    private FileAnalysisService fileAnalysisService;
    private MetricsProfile metricsProfile;

    public FileAnalysisMetricsService(FileAnalysisService fileAnalysisService, MetricsProfile metricsProfile) {
        this.fileAnalysisService = fileAnalysisService;
        this.metricsProfile = metricsProfile;
    }

    public MetricsReport getMetricsReport() {
        LogLine[] data = fileAnalysisService.getData();
        return new MetricsReport(metricsProfile, data);
    }


}
