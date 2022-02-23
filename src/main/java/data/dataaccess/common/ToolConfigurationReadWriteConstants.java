package data.dataaccess.common;

import java.io.File;

public class ToolConfigurationReadWriteConstants {
    public static final String PORTION_SEPARATOR = ";";
    public static final String STOP_WORDS_TOKEN = "STOP_WORDS";
    public static final String EXPECTED_FIRST_LINE = "Configurations File";


    public static final String DEFAULT_TOOL_CONFIGURATION_FOLDER = "Configuration";
    public static final String DEFAULT_TOOL_CONFIGURATION_FILE_NAME = "configurations.txt";
    public static final String DEFAULT_TOOL_CONFIGURATION_PROFILE_PATH =
            DEFAULT_TOOL_CONFIGURATION_FOLDER + File.separator + DEFAULT_TOOL_CONFIGURATION_FILE_NAME;

}
