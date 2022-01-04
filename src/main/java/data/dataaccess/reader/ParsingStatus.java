package data.dataaccess.reader;

public enum ParsingStatus {
    OK(0),
    FAILURE(1),
    ONGOING(2);

    private int status;

    public int getStatus() {
        return status;
    }

    ParsingStatus(int status) {
        this.status = status;
    }
}
