package presentation.metricsmonitoring;

import domain.entities.displayobjects.MetricsProfileDo;
import domain.entities.displayobjects.ParsingProfileDo;
import presentation.common.IPresenter;

import javax.swing.JFrame;
import java.io.File;

public class MetricsMonitoringScreenPresenter implements IPresenter {

    private final MetricsMonitoringScreen view;

    public MetricsMonitoringScreenPresenter(JFrame motherFrame, File selectedFile, ParsingProfileDo parsingProfile, MetricsProfileDo metricsProfile) {
        view = new MetricsMonitoringScreen();
        defineViewBehavior();
    }

    private void defineViewBehavior() {

    }

    @Override
    public void execute() {

    }

}
