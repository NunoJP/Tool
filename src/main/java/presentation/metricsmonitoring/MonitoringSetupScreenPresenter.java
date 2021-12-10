package presentation.metricsmonitoring;

import presentation.common.ITabPresenter;

import javax.swing.JPanel;

public class MonitoringSetupScreenPresenter implements ITabPresenter {

    private final MonitoringSetupScreen view;

    public MonitoringSetupScreenPresenter() {
        view = new MonitoringSetupScreen();
        defineViewBehavior();
    }

    private void defineViewBehavior() {

    }

    @Override
    public void execute() {

    }

    @Override
    public JPanel getView() {
        return view;
    }
}
