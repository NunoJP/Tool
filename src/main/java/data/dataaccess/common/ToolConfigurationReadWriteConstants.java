package data.dataaccess.common;

import java.io.File;

public class ToolConfigurationReadWriteConstants {
    public static final String PORTION_SEPARATOR = ";";
    public static final String STOP_WORDS_TOKEN = "STOP_WORDS";
    public static final String WARNING_COLORS_TOKEN = "WARNING_COLORS";
    public static final String STOP_WORDS_DEFAULT_VALUES = "a;that;the;and;at";
    public static final String WARNING_COLORS_DEFAULT_VALUES = "NONE;FFFDF7;CRITICAL;C21510;HIGH;DE4909;MEDIUM;DE9009;LOW;E6CC0E;INFO;17ABE6";

    public static final String EXPECTED_FIRST_LINE = "Configurations File";


    public static final String DEFAULT_TOOL_CONFIGURATION_FOLDER = "Configuration";
    public static final String DEFAULT_TOOL_CONFIGURATION_FILE_NAME = "configurations.txt";
    public static final String DEFAULT_TOOL_CONFIGURATION_PROFILE_PATH =
            DEFAULT_TOOL_CONFIGURATION_FOLDER + File.separator + DEFAULT_TOOL_CONFIGURATION_FILE_NAME;

}
