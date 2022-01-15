package domain.entities.domainobjects;

import java.util.Date;

public class LogLine {
    private Date date;
    private String level;
    private String origin;
    private String message;

    public LogLine() {
    }

    public LogLine(Date date, String level, String origin, String message) {
        this.date = date;
        this.level = level;
        this.origin = origin;
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public String getLevel() {
        return level;
    }

    public String getOrigin() {
        return origin;
    }

    public String getMessage() {
        return message;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
