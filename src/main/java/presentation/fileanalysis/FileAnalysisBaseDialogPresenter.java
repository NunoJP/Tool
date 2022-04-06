package presentation.fileanalysis;

import domain.entities.Converter;
import domain.entities.displayobjects.MetricsProfileDo;
import domain.entities.displayobjects.ParsingProfileDo;
import domain.services.FileAnalysisService;
import presentation.common.GuiConstants;

import javax.swing.JFrame;
import java.io.File;

public class FileAnalysisBaseDialogPresenter {

    private final JFrame motherFrame;
    private final File selectedFile;
    private final ParsingProfileDo parsingProfile;
    private final MetricsProfileDo metricsProfile;
    private FileAnalysisMetricsScreenPresenter fileAnalysisMetricsScreenPresenter;
    private FileAnalysisScreenPresenter fileAnalysisScreenPresenter;
    private FileAnalysisBaseDialog fileAnalysisBaseDialog;
    private FileAnalysisService fileAnalysisService;

    public FileAnalysisBaseDialogPresenter(JFrame motherFrame, File selectedFile,
                                           ParsingProfileDo parsingProfile, MetricsProfileDo metricsProfile) {
        this.motherFrame = motherFrame;
        this.selectedFile = selectedFile;
        this.parsingProfile = parsingProfile;
        this.metricsProfile = metricsProfile;
        setupPresenters();
        setupComponents();
    }

    /**
     * Create the presenters for the Analysis Metrics screen and for the Analysis screen
     * Creates a FileAnalysisService which is passed to both presenters
     */
    private void setupPresenters() {
        fileAnalysisService = new FileAnalysisService(selectedFile, Converter.toDomainObject(parsingProfile),
                Converter.toDomainObject(metricsProfile));
        fileAnalysisMetricsScreenPresenter = new FileAnalysisMetricsScreenPresenter(metricsProfile, fileAnalysisService);
        fileAnalysisScreenPresenter = new FileAnalysisScreenPresenter(selectedFile, parsingProfile,
                metricsProfile, fileAnalysisService);
    }


    private void setupComponents() {
        fileAnalysisBaseDialog = new FileAnalysisBaseDialog(motherFrame,
                GuiConstants.FILE_ANALYSIS_SCREEN_TITLE + selectedFile.getName());
        fileAnalysisBaseDialog.addTab(GuiConstants.FILE_METRICS_TAB, fileAnalysisMetricsScreenPresenter.getView());
        fileAnalysisBaseDialog.addTab(GuiConstants.FILE_OPERATIONS_TAB, fileAnalysisScreenPresenter.getView());
    }

    public void execute(){
        fileAnalysisBaseDialog.setLocationRelativeTo(motherFrame);
        fileAnalysisBaseDialog.setVisible(true);
        fileAnalysisMetricsScreenPresenter.execute();
        fileAnalysisScreenPresenter.execute();
    }


}
