package domain.services;

import domain.entities.domainobjects.MetricsReport;

public class FileAnalysisMetricsService {


    private FileAnalysisService fileAnalysisService;

    public FileAnalysisMetricsService(FileAnalysisService fileAnalysisService) {
        this.fileAnalysisService = fileAnalysisService;
    }

    public MetricsReport getMetricsReport() {
        fileAnalysisService.getData();
        return new MetricsReport();
    }


}
