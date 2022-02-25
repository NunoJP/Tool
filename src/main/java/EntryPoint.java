import data.dataaccess.common.MetricsProfileReadWriteConstants;
import data.dataaccess.common.ParsingProfileReadWriteConstants;
import data.dataaccess.common.ToolConfigurationReadWriteConstants;
import domain.services.ToolConfigurationService;
import presentation.frame.HomeScreenPresenter;

import java.io.File;

public class EntryPoint {

    public static void main(String[] args) {
        System.out.println("Opening");
        setupEnvironment();
        HomeScreenPresenter presenter = new HomeScreenPresenter();
        presenter.execute();
        System.out.println("Opened");
    }

    private static void setupEnvironment() {
        new File(ToolConfigurationReadWriteConstants.DEFAULT_TOOL_CONFIGURATION_FOLDER).mkdir();
        new File(ParsingProfileReadWriteConstants.DEFAULT_PARSING_PROFILE_FOLDER_NAME).mkdir();
        new File(MetricsProfileReadWriteConstants.DEFAULT_METRICS_PROFILE_FOLDER_NAME).mkdir();

        ToolConfigurationService.getInstance().createConfigFile();
    }

}
