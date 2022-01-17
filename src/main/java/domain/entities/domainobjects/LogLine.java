package domain.entities.domainobjects;

import java.util.Date;

public class LogLine {
    private Date date;
    private Date time;
    private Date timestamp;
    private String level;
    private String origin;
    private String message;
    private String identifier;

    public LogLine() {
    }

    public Date getDate() {
        return date;
    }

    public Date getTime() {
        return time;
    }

    public Date getTimestamp() {
        return timestamp;
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

    public void setTime(Date time) {
        this.time = time;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
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

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
}
