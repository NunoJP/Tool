package presentation.fileanalysis;

import domain.entities.displayobjects.MetricsProfileDo;
import domain.entities.displayobjects.ParsingProfileDo;
import presentation.common.ITabPresenter;

import javax.swing.JPanel;
import java.io.File;

public class FileAnalysisScreenPresenter implements ITabPresenter {
    private final File selectedFile;
    private final ParsingProfileDo parsingProfile;
    private final MetricsProfileDo metricsProfile;
    private final FileAnalysisScreen view;

    public FileAnalysisScreenPresenter(File selectedFile, ParsingProfileDo parsingProfile, MetricsProfileDo metricsProfile) {
        view = new FileAnalysisScreen();
        this.selectedFile = selectedFile;
        this.parsingProfile = parsingProfile;
        this.metricsProfile = metricsProfile;
    }

    @Override
    public JPanel getView() {
        return view;
    }

    @Override
    public void execute() {

    }
}
