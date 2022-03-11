package presentation.common;

public class GuiMessages {

    // Log Messages
    // Info
    public static final String LOG_INFO_READER_FINISHED_PROCESSING = "Reader finished processing: ";
    public static final String LOG_INFO_WRITER_FINISHED_PROCESSING = "Writer finished processing: ";

    // Error
    public static final String LOG_ERROR_ERROR_OPENING_FILE = "Error opening file: ";
    public static final String LOG_ERROR_ERROR_READING_FILE = "Error reading file: ";
    public static final String LOG_ERROR_ERROR_CREATING_FILE = "Error creating file: ";
    public static final String TIMESTAMP_DATE_TIME_MISSING = "Neither Timestamp or Date+Time were available";
    public static final String PROFILE_NAME_INVALID_OR_EMPTY = "The profile name is either invalid or empty, please resolve.";
    public static final String PROFILE_NAME_INVALID_OR_EMPTY_TITLE = "Profile name invalid or empty";
    public static final String KEYWORD_TEXT_INVALID_OR_EMPTY = "The keyword is either invalid or empty, please resolve.";
    public static final String KEYWORD_TEXT_INVALID_OR_EMPTY_TITLE = "Keyword name invalid or empty";

    // Dialog messages
    // Error messages
    public static final String ERROR_FOLDER_DOES_NOT_EXIST = "Folder does not exist";
    public static final String ERROR_PARSING_DATE = "Error parsing date";
    public static final String ERROR_TIMESTAMP_DATE_TIME_MISSING = "Timestamp or Date+Time were not available";
    public static final String SAMPLE_TEXT_FIELD_EMPTY = "The sample text field is empty";

    // Confirmations
    public static final String CONFIRM_OVERWRITE_PARSING_PROFILE = "You will overwrite the existing profile, are you sure?";
    public static final String CONFIRM_DELETE_PARSING_PROFILE = "You will delete the existing profile, are you sure?";
    public static final String PLEASE_CONFIRM_DIALOG_TITLE = "Please confirm";
    public static final String CONFIRM_DELETE_METRIC_PROFILE = "You will delete the existing profile, are you sure?";

    // Statements
    public static final String UPDATE_SUCCESSFUL = "The update was successful";
    public static final String DELETE_SUCCESSFUL = "The delete was successful";
    public static final String UPDATE_FAILED = "Update failed";
    public static final String DELETE_FAILED = "Delete failed";
    public static final String EXPORT_SUCCESSFUL = "Export done successfully";
    public static final String EXPORT_FAILED = "Export failed";

    // Window titles
    public static final String SUCCESS_TITLE = "Success";
    public static final String FAILURE_TITLE = "Failed";
    public static final String MESSAGE_TITLE = "Message";
    public static final String WARNING_TITLE = "Warning";
    public static final String ERROR_TITLE = "Error";


    public static final String WARNING_MESSAGE_BASE = "Warning: threshold %s of keyword %s was %s";
    public static final String WARNING_MESSAGE_PARTICLE_MET = "met";
    public static final String WARNING_MESSAGE_PARTICLE_SURPASSED = "surpassed";
    public static final String WARNING_MESSAGE_PARTICLE_MET_OR_SURPASSED = "met/surpassed";

    public static final String THRESHOLD_VALUE_BASE = "%s Th: %s Current: %s";

    // Parsing Messages
    public static final String ERROR_PARSING_TEXT_CLASS = "Error parsing %s, for Text Class: %s";
    public static final String ERROR_PARSING_TEXT_CLASS_SPECIFIC_FORMAT = "Error parsing %s, for Text Class: %s and Specific format: %s";
    public static final String NO_LINES_PRODUCED_MESSAGE = "No lines were produced by parsing the sample text";
    public static final String MORE_LINES_PRODUCED_MESSAGE = "More than one line was produced by parsing the sample text";

}
