package presentation.fileanalysis;

import domain.entities.displayobjects.MetricsProfileDo;
import domain.entities.displayobjects.ParsingProfileDo;
import domain.entities.domainobjects.MetricsReport;
import domain.services.FileAnalysisMetricsService;
import presentation.common.ITabPresenter;

import javax.swing.JPanel;
import java.io.File;

public class FileAnalysisMetricsScreenPresenter implements ITabPresenter {

    private final File selectedFile;
    private final ParsingProfileDo parsingProfile;
    private final MetricsProfileDo metricsProfile;
    private final FileAnalysisMetricsScreen view;
    private final FileAnalysisMetricsService fileAnalysisMetricsService;

    public FileAnalysisMetricsScreenPresenter(File selectedFile,
                                              ParsingProfileDo parsingProfile, MetricsProfileDo metricsProfile) {
        view = new FileAnalysisMetricsScreen();
        fileAnalysisMetricsService = new FileAnalysisMetricsService();
        this.selectedFile = selectedFile;
        this.parsingProfile = parsingProfile;
        this.metricsProfile = metricsProfile;
    }

    @Override
    public JPanel getView() {
        return view;
    }

    public void execute(){
        MetricsReport metricsReport = fileAnalysisMetricsService.getMetricsReport();

    }

}
