package data.dataaccess.memory;

public class ProfileNameFileNamePair {

    private String profileName;
    private String fileName;

    public ProfileNameFileNamePair(String profileName, String fileName) {
        this.profileName = profileName;
        this.fileName = fileName;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
