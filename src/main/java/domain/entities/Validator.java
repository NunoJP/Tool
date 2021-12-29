package domain.entities;

public class Validator {

    public static final int FILE_NAME_SIZE = 100;
    public static final int RESULT_SIZE = 100;
    public static final int KEYWORD_SIZE = 50;
    public static final int SPECIFIC_FORMAT_SIZE = 20;
    public static final int METHOD_SIZE = 100;
    public static final int LEVEL_SIZE = 10;
    public static final int MESSAGE_SIZE = 255;

    public static boolean validateProfileName(String profileName) {
        if(profileName == null || profileName.isEmpty() || profileName.length() > FILE_NAME_SIZE) {
            return false;
        }
        return true;
    }
}
