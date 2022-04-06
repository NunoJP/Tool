package presentation.fileanalysis;

import domain.entities.Converter;
import domain.entities.displayobjects.MetricsProfileDo;
import domain.entities.domainobjects.MetricsReport;
import domain.services.FileAnalysisMetricsService;
import domain.services.FileAnalysisService;
import presentation.common.GuiMessages;
import presentation.common.IViewPresenter;
import presentation.common.PresentationUtils;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

import static presentation.common.GuiConstants.DATE_TIME_FORMATTER;

public class FileAnalysisMetricsScreenPresenter implements IViewPresenter {

    private final MetricsProfileDo metricsProfile;
    private final FileAnalysisMetricsScreen view;
    private final FileAnalysisMetricsService fileAnalysisMetricsService;
    private static final Logger LOGGER = Logger.getLogger(FileAnalysisMetricsScreenPresenter.class.getName());

    public FileAnalysisMetricsScreenPresenter(MetricsProfileDo metricsProfile,
                                              FileAnalysisService fileAnalysisService) {
        view = new FileAnalysisMetricsScreen(metricsProfile);
        fileAnalysisService.setLogMessageConsumer(this::messagePopup);
        this.fileAnalysisMetricsService = new FileAnalysisMetricsService(fileAnalysisService, Converter.toDomainObject(metricsProfile));
        this.metricsProfile = metricsProfile;
    }

    @Override
    public JPanel getView() {
        return view;
    }

    public void execute(){
        MetricsReport metricsReport = fileAnalysisMetricsService.getMetricsReport();
        if(metricsProfile.isHasKeywordThreshold()) {
            String[][] kwdThresholdData = metricsReport.getKwdThresholdData();
            view.getKwdThTable().setData(kwdThresholdData);
            view.getKwdThTable().setCellSelectionOnly();
        }
        String [][] logLevelData = metricsReport.getLogLevelData();
        view.getLogLevelTable().setData(logLevelData);
        view.getLogLevelTable().setCellSelectionOnly();
        if(metricsProfile.isHasMostCommonWords()) {
            String[][] mostCommonWordsData = metricsReport.getMostCommonWordsData();
            view.getMostCommonWordsTable().setData(mostCommonWordsData);
            view.getMostCommonWordsTable().setCellSelectionOnly();
            String[][] warningsData = PresentationUtils.getMessages(metricsReport.getWarningsData());
            view.getWarningsTable().setData(warningsData);
            view.getWarningsTable().setCellSelectionOnly();
            view.getWarningsTable().setStringColorRenderMap(PresentationUtils.generateDefaultColorMap());
        }
        // File name and dates
        view.getFileNamePanel().setVariableLabelText(metricsReport.getFileName());
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMATTER);
        try {
            view.getStartDatePanel().setVariableLabelText(sdf.format(metricsReport.getStartDate()));
        } catch (ParseException pe) {
            LOGGER.log(Level.WARNING, pe.getMessage());
            view.getStartDatePanel().setVariableLabelText(GuiMessages.ERROR_PARSING_DATE);
        } catch (RuntimeException re) {
            LOGGER.log(Level.SEVERE, re.getMessage());
            view.getStartDatePanel().setVariableLabelText(GuiMessages.ERROR_TIMESTAMP_DATE_TIME_MISSING);
        }
        try {
            view.getEndDatePanel().setVariableLabelText(sdf.format(metricsReport.getEndDate()));
        } catch (ParseException pe) {
            LOGGER.log(Level.WARNING, pe.getMessage());
            view.getEndDatePanel().setVariableLabelText(GuiMessages.ERROR_PARSING_DATE);
        } catch (RuntimeException re) {
            LOGGER.log(Level.SEVERE, re.getMessage());
            view.getEndDatePanel().setVariableLabelText(GuiMessages.ERROR_TIMESTAMP_DATE_TIME_MISSING);
        }
    }


    private void messagePopup(String message) {
        JOptionPane.showMessageDialog(view,
                message,
                GuiMessages.MESSAGE_TITLE,
                JOptionPane.INFORMATION_MESSAGE);
    }

}
