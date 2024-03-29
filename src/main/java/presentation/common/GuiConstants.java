package presentation.common;

public class GuiConstants {
    // Numeric constants
    public static final int H_GAP = 5;
    public static final int V_GAP = 5;
    public static final int MAIN_FRAME_WIDTH = 800;
    public static final int MAIN_FRAME_HEIGHT = 500;
    public static final int H_FILE_ANALYSIS_SCREEN_SIZE = 1200;
    public static final int V_FILE_ANALYSIS_SCREEN_SIZE = 740;
    public static final int H_FILE_MONITORING_SCREEN_SIZE = 1200;
    public static final int V_FILE_MONITORING_SCREEN_SIZE = 700;
    public static final int H_PARSING_PROFILES_EDITOR_SCREEN_SIZE = 650;
    public static final int V_PARSING_PROFILES_EDITOR_SCREEN_SIZE = 280;
    public static final int H_METRIC_PROFILES_EDITOR_SCREEN_SIZE = 650;
    public static final int V_METRIC_PROFILES_EDITOR_SCREEN_SIZE = 650;
    public static final int FILE_NAME_FIELD_SIZE = 100/3;
    public static final int RESULT_FIELD_SIZE = 100/2;
    public static final int KEYWORD_FIELD_SIZE = 50/2;
    public static final int SPECIFIC_FORMAT_FIELD_SIZE = 20;
    public static final int ORIGIN_FIELD_SIZE = 100/10;
    public static final int LEVEL_FIELD_SIZE = 10;
    public static final int MESSAGE_FIELD_SIZE = 255/10;
    public static final int MESSAGE_DETAILS_TEXT_AREA_ROWS = 100;
    public static final int MESSAGE_DETAILS_TEXT_AREA_COLS = 6;
    public static final int MIN_COLUMN_WIDTH = 20;
    public static final int MAX_COLUMN_WIDTH = 400;
    public static final int MIN_TEXT_AREA_HEIGHT = 20;

    // Formatters
    public static final String DATE_TIME_FORMATTER = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String DATE_FORMATTER = "yyyy-MM-dd";
    public static final String TIME_FORMATTER = "HH:mm:ss.SSS";
    public static final String TIME_FORMATTER_NO_MILLIS = "HH:mm:ss";

    // Screen names
    public static final String HOME_SCREEN_TITLE = "Simplify software log analysis";
    public static final String FILE_ANALYSIS_SETUP_SCREEN_TITLE = "File analysis setup";
    public static final String FILE_MONITORING_SETUP_SCREEN_TITLE = "File monitoring setup";
    public static final String PARSING_PROFILE_MANAGEMENT_SCREEN_TITLE = "Parsing profile management";
    public static final String METRICS_PROFILE_MANAGEMENT_SCREEN_TITLE = "Metrics profile management";
    public static final String PARSING_PROFILE_EDITOR_SCREEN_TITLE = "Parsing profile editor";
    public static final String METRICS_PROFILE_EDITOR_SCREEN_TITLE = "Metrics profile editor";
    public static final String METRICS_MONITORING_SCREEN_TITLE = "Metrics monitoring";
    public static final String FILE_ANALYSIS_METRICS_SCREEN_TITLE = "File analysis metrics";
    public static final String FILE_ANALYSIS_SCREEN_TITLE = "File analysis screen for:";
    public static final String ORGANIZATION_SCREEN_TITLE = "Organization";

    // Tabs
    public static final String ANALYSIS_TAB = "Analysis";
    public static final String MONITORING_TAB = "Monitoring";
    public static final String PARSING_PROFILES_TAB = "Parsing profiles";
    public static final String METRICS_PROFILES_TAB = "Metrics profiles";
    public static final String ORGANIZATION_TAB = "Organization";
    public static final String FILE_METRICS_TAB = "File metrics";
    public static final String FILE_OPERATIONS_TAB = "File operations";
    public static final String EDIT_PROFILE_TAB = "Edit profile";
    public static final String TEST_PROFILE_TAB = "Test profile";
    public static final String TABLES_TAB = "Tables";
    public static final String MOST_COMMON_WORDS_TAB = "Most common words";
    public static final String KEYWORD_OVER_TIME_TAB = "Keyword over time";
    public static final String FILE_SIZE_TAB = "File size evolution";
    public static final String KEYWORD_HISTOGRAM_TAB = "Keyword histogram";

    // Buttons
    public static final String ANALYSIS_BUTTON = "Analysis";
    public static final String MONITORING_BUTTON = "Monitoring";
    public static final String PARSING_PROFILES_BUTTON = "Parsing profiles";
    public static final String METRICS_PROFILES_BUTTON = "Metrics profiles";
    public static final String ORGANIZATION_BUTTON = "Organization";
    public static final String CHOOSE_FILE_BUTTON = "Choose file";
    public static final String START_BUTTON = "Start";
    public static final String STOP_BUTTON = "Stop";
    public static final String NEXT_BUTTON = "Next";
    public static final String PREVIOUS_BUTTON = "Previous";
    public static final String NEW_PROFILE_BUTTON = "New profile";
    public static final String UPDATE_SELECTED_BUTTON = "Update selected";
    public static final String DELETE_SELECTED_BUTTON = "Delete selected";
    public static final String ADD_BUTTON = "Add";
    public static final String REMOVE_LAST_BUTTON = "Remove last";
    public static final String CLEAR_BUTTON = "Clear";
    public static final String SAVE_PROFILE_BUTTON = "Save profile";
    public static final String UPDATE_BUTTON = "Update";
    public static final String DELETE_BUTTON = "Delete";
    public static final String ANALYSE_BUTTON = "Analyse";
    public static final String SEARCH_BUTTON = "Search";
    public static final String FILTER_BUTTON = "Filter";
    public static final String EXPORT_BUTTON = "Export";
    public static final String COPY_BUTTON = "Copy";
    public static final String MOVE_BUTTON = "Move";
    public static final String CHOOSE_BUTTON = "Choose";
    public static final String CLEAR_KEYWORDS_TABLE_BUTTON = "Clear table";
    public static final String CANCEL_BUTTON = "Cancel";
    public static final String CHOOSE_COLOR_BUTTON = "Color";
    public static final String TEST_BUTTON = "Test";

    // Labels
    public static final String FILE_INDICATION_LABEL = "File: ";
    public static final String PARSING_PROFILE_LABEL = "Parsing profile";
    public static final String METRICS_LABEL = "Metrics";
    public static final String NAME_LABEL = "Name";
    public static final String RESULT_LABEL = "Result";
    public static final String TEXT_CLASS_LABEL = "Text class";
    public static final String SEPARATOR_LABEL = "Separator";
    public static final String IGNORE_THIS_PORTION_LABEL = "Ignore this portion?";
    public static final String SPECIFIC_FORMAT_LABEL = "Specific format?";
    public static final String MOST_COMMON_WORDS_LABEL = "Most common words";
    public static final String FILE_SIZE_LABEL = "File size";
    public static final String KEYWORD_HISTOGRAM_LABEL = "Keyword histogram";
    public static final String KEYWORD_OVER_TIME_LABEL = "Keyword over time";
    public static final String KEYWORD_THRESHOLD_LABEL = "Keyword threshold";
    public static final String KEYWORD_LABEL = "Keyword";
    public static final String CASE_SENSITIVE_LABEL = "Case sensitive";
    public static final String THRESHOLD_LABEL = "Threshold";
    public static final String START_DATE_LABEL = "Start date: ";
    public static final String END_DATE_LABEL = "End date: ";
    public static final String LOG_LEVEL_DISTRIBUTION_LABEL = "Log level distribution";
    public static final String WARNINGS_LABEL = "Warnings";
    public static final String FROM_LABEL = "From:";
    public static final String TO_LABEL = "To:";
    public static final String DATE_LABEL = "Date";
    public static final String TIME_LABEL = "Time";
    public static final String ORIGIN_LABEL = "Origin";
    public static final String LEVEL_LABEL = "Level";
    public static final String MESSAGE_LABEL = "Message";
    public static final String TARGET_FOLDER_LABEL = "Target folder";
    public static final String SOURCE_FOLDER_LABEL = "Source folder";
    public static final String CHOOSE_SOURCE_FOLDER_LABEL = "Choose source folder";
    public static final String CHOOSE_TARGET_FOLDER_LABEL = "Choose target folder";
    public static final String FOLDER_LABEL = "Folder";
    public static final String DESCRIPTION_LABEL = "Description";
    public static final String WORD_LABEL = "Word";
    public static final String SIZE_LABEL = "Size";
    public static final String IDENTIFIER_LABEL = "Id:";
    public static final String WARNING_CONFIG_LABEL = "Warning:";


    // Columns
    public static final String LEVEL_COLUMN = "Level";
    public static final String PERCENTAGE_COLUMN = "%";
    public static final String KEYWORD_COLUMN = "Keyword";
    public static final String MESSAGE_COLUMN = "Message";
    public static final String SIZE_COLUMN = "Size";
    public static final String FILE_COLUMN = "File";
    public static final String VALUE_COLUMN = "Value";
    public static final String WORD_COLUMN = "Word";
    public static final String DATE_COLUMN = "Date";
    public static final String TIME_COLUMN = "Time";
    public static final String ORIGIN_COLUMN = "Origin";
    public static final String DESCRIPTION_COLUMN = "Description";
    public static final String NAME_COLUMN = "Name";
    public static final String CASE_SENSITIVE_COLUMN = "Case sensitive";
    public static final String THRESHOLD_COLUMN = "Threshold";
    public static final String IDENTIFIER_COLUMN = "Id";
    public static final String WARNING_LEVEL_COLUMN = "Warning";

    public static final String NO_NAME_PROVIDED = "No name provided";

}
