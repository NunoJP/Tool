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
    private FileAnalysisSetupScreenPresenter fileAnalysisSetupScreenPresenter;
    private MonitoringSetupScreenPresenter monitoringSetupScreenPresenter;
    private ParsingProfileManagementScreenPresenter parsingProfileManagementScreenPresenter;
    private MetricsProfileManagementScreenPresenter metricsProfileManagementScreenPresenter;
    private OrganizationScreenPresenter organizationScreenPresenter;

    public HomeScreenPresenter() {
        view = new HomeScreen();
        defineViewBehavior();
    }

    private void defineViewBehavior() {
        fileAnalysisSetupScreenPresenter = new FileAnalysisSetupScreenPresenter(view.getFrame());
        view.getBasePanel().addTab(GuiConstants.ANALYSIS_TAB, fileAnalysisSetupScreenPresenter.getView());
        monitoringSetupScreenPresenter = new MonitoringSetupScreenPresenter();
        view.getBasePanel().addTab(GuiConstants.MONITORING_TAB, monitoringSetupScreenPresenter.getView());
        parsingProfileManagementScreenPresenter = new ParsingProfileManagementScreenPresenter(this::applyCrossScreenChanges);
        view.getBasePanel().addTab(GuiConstants.PARSING_PROFILES_TAB, parsingProfileManagementScreenPresenter.getView());
        metricsProfileManagementScreenPresenter = new MetricsProfileManagementScreenPresenter(this::applyCrossScreenChanges);
        view.getBasePanel().addTab(GuiConstants.METRICS_PROFILES_TAB, metricsProfileManagementScreenPresenter.getView());
        organizationScreenPresenter = new OrganizationScreenPresenter();
        view.getBasePanel().addTab(GuiConstants.ORGANIZATION_TAB, organizationScreenPresenter.getView());
    }


    @Override
    public void execute() {
        view.setVisible();
        fileAnalysisSetupScreenPresenter.execute();
        monitoringSetupScreenPresenter.execute();
        parsingProfileManagementScreenPresenter.execute();
        metricsProfileManagementScreenPresenter.execute();
        organizationScreenPresenter.execute();
    }

    private void applyCrossScreenChanges() {
        fileAnalysisSetupScreenPresenter.refreshComboBoxes();
    }
}
