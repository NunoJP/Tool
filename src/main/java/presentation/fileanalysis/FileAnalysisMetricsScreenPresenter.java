package presentation.fileanalysis;

import domain.entities.displayobjects.MetricsProfileDo;
import domain.entities.displayobjects.ParsingProfileDo;
import domain.entities.domainobjects.MetricsReport;
import domain.services.FileAnalysisMetricsService;
import general.util.Pair;
import presentation.common.IViewPresenter;

import javax.swing.JPanel;
import java.awt.Color;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import static presentation.common.GuiConstants.DATE_TIME_FORMATTER;

public class FileAnalysisMetricsScreenPresenter implements IViewPresenter {

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
        String [][] kwdThresholdData = metricsReport.getKwdThresholdData();
        view.getKwdThTable().setData(kwdThresholdData);
        view.getKwdThTable().setStringColorRenderMap(generateDefaultColorMap());
        String [][] logLevelData = metricsReport.getLogLevelData();
        view.getLogLevelTable().setData(logLevelData);
        view.getLogLevelTable().setStringColorRenderMap(generateDefaultColorMap());
        String [][] mostCommonWordsData = metricsReport.getMostCommonWordsData();
        view.getMostCommonWordsTable().setData(mostCommonWordsData);
        view.getMostCommonWordsTable().setStringColorRenderMap(generateDefaultColorMap());
        String [][] warningsData = metricsReport.getWarningsData();
        view.getWarningsTable().setData(warningsData);
        view.getWarningsTable().setStringColorRenderMap(generateDefaultColorMap());

        // File name and dates
        view.getFileNamePanel().setVariableLabelText(metricsReport.getFileName());
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMATTER);
        view.getStartDatePanel().setVariableLabelText(sdf.format(metricsReport.getStartDate()));
        view.getEndDatePanel().setVariableLabelText(sdf.format(metricsReport.getEndDate()));
    }

    private HashMap<String, Pair<Color, Color>> generateDefaultColorMap() {
        HashMap<String, Pair<Color, Color>> colorMap = new HashMap<>();
        colorMap.put("Error", Pair.of(new Color(255, 102, 102), Color.BLACK));
        colorMap.put("Warning", Pair.of(new Color(255, 255, 179), Color.BLACK));
        return colorMap;
    }

}
