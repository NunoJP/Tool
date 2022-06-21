package domain.entities.domainobjects;

import general.strutures.SuffixTree;

import java.util.Calendar;
import java.util.Date;

public class LogLine {
    private Date date;
    private Date time;
    private Date timestamp;
    private String level;
    private String origin;
    private String message;
    private String identifier;
    private SuffixTree suffixTree;
    private int position;

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

    /**
     * The position field is the absolute position of the log line within the source file
     * excluding special lines which are aggregated in the message field.
     */
    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

//    public void calculateSuffixTree() {
//        this.suffixTree = new SuffixTree(this.message);
//    }

    public void calculateSuffixTreeNonCaseSensitive() {
        if (suffixTree == null) {
            this.suffixTree = new SuffixTree(this.message, true);
        } else {
            this.suffixTree.createNonCaseSensitiveTree();
        }
    }

    public SuffixTree getSuffixTree() {
        return suffixTree;
    }

    public Date getTemporalRepresentation() {

        if (timestamp != null) {
            return timestamp;
        }

        if (date != null && time != null) {
            Calendar d = Calendar.getInstance();
            d.setTime(date);
            Calendar t = Calendar.getInstance();
            t.setTime(time);

            d.set(Calendar.HOUR_OF_DAY, t.get(Calendar.HOUR_OF_DAY));
            d.set(Calendar.MINUTE, t.get(Calendar.MINUTE));
            d.set(Calendar.SECOND, t.get(Calendar.SECOND));
            d.set(Calendar.MILLISECOND, t.get(Calendar.MILLISECOND));

            return d.getTime();
        }

        if (date != null) {
            return date;
        }

        if (time != null) {
            return time;
        }

        return null;
    }
}
