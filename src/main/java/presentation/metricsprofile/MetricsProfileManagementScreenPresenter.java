package presentation.metricsprofile;

import presentation.common.ITabPresenter;

import javax.swing.JPanel;

public class MetricsProfileManagementScreenPresenter implements ITabPresenter {


    private final MetricsProfileManagementScreen view;

    public MetricsProfileManagementScreenPresenter() {
        view = new MetricsProfileManagementScreen();
    }

    @Override
    public JPanel getView() {
        return view;
    }

    @Override
    public void execute() {

    }
}
