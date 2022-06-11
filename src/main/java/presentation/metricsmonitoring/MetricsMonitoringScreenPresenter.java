package presentation.metricsmonitoring;

import data.dataaccess.reader.LogFileReader;
import domain.entities.Converter;
import domain.entities.displayobjects.MetricsProfileDo;
import domain.entities.displayobjects.ParsingProfileDo;
import domain.entities.domainobjects.MetricsReport;
import domain.services.MetricsMonitoringService;
import domain.services.MetricsMonitoringServiceWrapper;
import presentation.common.GuiConstants;
import presentation.common.GuiMessages;
import presentation.common.IPresenter;
import presentation.common.PresentationUtils;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;

public class MetricsMonitoringScreenPresenter implements IPresenter {

    private final MetricsMonitoringScreen view;
    private final File selectedFile;
    private final ParsingProfileDo parsingProfile;
    private final MetricsProfileDo metricsProfile;
    private final MetricsMonitoringService metricsMonitoringService;
    private static final Logger LOGGER = Logger.getLogger(MetricsMonitoringScreenPresenter.class.getName());
    private final LogFileReader logFileReader;
    private AtomicBoolean stop;
    private JFrame motherFrame;

    public MetricsMonitoringScreenPresenter(JFrame motherFrame, File selectedFile, ParsingProfileDo parsingProfile, MetricsProfileDo metricsProfile) {
        this.motherFrame = motherFrame;
        this.view = new MetricsMonitoringScreen(motherFrame, GuiConstants.METRICS_MONITORING_SCREEN_TITLE,
                metricsProfile, () -> stopThreadAndReader());
        this.selectedFile = selectedFile;
        this.parsingProfile = parsingProfile;
        this.metricsProfile = metricsProfile;
        this.logFileReader = new LogFileReader(selectedFile, Converter.toDomainObject(parsingProfile));
        metricsMonitoringService = new MetricsMonitoringService(this::updateView, logFileReader,
                Converter.toDomainObject(parsingProfile), Converter.toDomainObject(metricsProfile));
        stop = new AtomicBoolean(false);
        defineViewBehavior();
    }

    private void defineViewBehavior() {
        view.getNamePanel().setVariableLabelText(selectedFile.getName());

        view.getStopButton().addActionListener(actionEvent ->  {
            // Stop the thread
            stopThreadAndReader();
        });
    }

    private void stopThreadAndReader() {
        stop.set(true);
        metricsMonitoringService.stop();
    }

    @Override
    public void execute() {
        // launch thread for the analysis, which then calls updateView
        (new Thread(new MetricsMonitoringServiceWrapper(metricsMonitoringService))).start();
        view.setLocationRelativeTo(motherFrame);
        view.setVisible(true);
    }

    public boolean updateView(MetricsReport metricsReport) {
        // File size chart
        if(metricsProfile.isHasFileSize()) {
            view.setFileSizeData(metricsReport.getFileSizeData());
        }

        // Keyword Threshold + Warnings
        if(metricsProfile.isHasKeywordThreshold()) {
            String[][] kwdThresholdData = metricsReport.getKwdThresholdData();
            view.getKwdThTable().setData(kwdThresholdData);
            view.getKwdThTable().setCellSelectionOnly();
            String[][] warningsData = PresentationUtils.getMessages(metricsReport.getWarningsData());
            view.getWarningsTable().setData(warningsData);
            view.getWarningsTable().setCellSelectionOnly();
            view.getWarningsTable().setStringColorRenderMap(PresentationUtils.generateDefaultColorMap());
        }
        // Keyword Histogram
        if(metricsProfile.isHasKeywordHistogram()) {
            view.setKeywordHistogramData(metricsReport.getKwdOccurrences());
        }

        // Most common words
        if(metricsProfile.isHasMostCommonWords()) {
            String[][] mostCommonWordsData = metricsReport.getMostCommonWordsData();
            view.getMostCommonWordsTable().setData(mostCommonWordsData);
            view.getMostCommonWordsTable().setCellSelectionOnly();
        }

        // Keywords over time

        return stop.get();
    }

    private void messagePopup(String message) {
        JOptionPane.showMessageDialog(view,
                message,
                GuiMessages.MESSAGE_TITLE,
                JOptionPane.INFORMATION_MESSAGE);
    }
}
