package presentation.frame;

import presentation.common.GuiConstants;
import presentation.common.IPresenter;
import presentation.fileanalysis.FileAnalysisSetupScreenPresenter;
import presentation.metricsmonitoring.MonitoringSetupScreenPresenter;
import presentation.metricsprofile.MetricsProfileManagementScreenPresenter;
import presentation.organization.OrganizationScreenPresenter;
import presentation.parsingprofile.ParsingProfileManagementScreenPresenter;

public class HomeScreenPresenter implements IPresenter {

    private final HomeScreen view;

    public HomeScreenPresenter() {
        view = new HomeScreen();
        defineViewBehavior();
    }

    private void defineViewBehavior() {
//        basePanel.addTab(GuiConstants.ANALYSIS_TAB, new );
        FileAnalysisSetupScreenPresenter fileAnalysisSetupScreenPresenter = new FileAnalysisSetupScreenPresenter(view.getFrame());
        view.getBasePanel().addTab(GuiConstants.ANALYSIS_TAB, fileAnalysisSetupScreenPresenter.getView());
        MonitoringSetupScreenPresenter monitoringSetupScreenPresenter = new MonitoringSetupScreenPresenter();
        view.getBasePanel().addTab(GuiConstants.MONITORING_TAB, monitoringSetupScreenPresenter.getView());
        ParsingProfileManagementScreenPresenter parsingProfileManagementScreenPresenter = new ParsingProfileManagementScreenPresenter();
        view.getBasePanel().addTab(GuiConstants.PARSING_PROFILES_TAB, parsingProfileManagementScreenPresenter.getView());
        MetricsProfileManagementScreenPresenter metricsProfileManagementScreenPresenter = new MetricsProfileManagementScreenPresenter();
        view.getBasePanel().addTab(GuiConstants.METRICS_PROFILES_TAB, metricsProfileManagementScreenPresenter.getView());
        OrganizationScreenPresenter organizationScreenPresenter = new OrganizationScreenPresenter();
        view.getBasePanel().addTab(GuiConstants.ORGANIZATION_TAB, organizationScreenPresenter.getView());

    }


    @Override
    public void execute() {
        view.setVisible();
    }
}
