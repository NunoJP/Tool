package presentation.metricsprofile;

import presentation.common.IViewPresenter;

import javax.swing.JPanel;

public class MetricsProfileManagementScreenPresenter implements IViewPresenter {


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
