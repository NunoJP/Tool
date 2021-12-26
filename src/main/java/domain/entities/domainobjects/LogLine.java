package domain.entities.domainobjects;

import java.util.Date;

public class LogLine {
    private final Date date;
    private final String level;
    private final String origin;
    private final String message;

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
}
