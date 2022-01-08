package domain.entities.common;

public class ParsingProfilePortion {
    public static final String NO_SPECIFIC_FORMAT = "NoFormat";

    private String portion;
    private boolean ignore;
    private boolean isSeparator;
    private String specificFormat;
    private boolean isSpecificFormat;
    private boolean isLast = false;

    public ParsingProfilePortion(String portion, boolean ignore, boolean isSeparator) {
        this(portion, ignore, isSeparator, false);
    }

    public ParsingProfilePortion(String portion, boolean ignore, boolean isSeparator, boolean specificFormat) {
        this(portion, ignore, isSeparator, specificFormat, NO_SPECIFIC_FORMAT);
    }

    public ParsingProfilePortion(String portion, boolean isIgnore, boolean isSeparator, boolean isSpecificFormat, String specificFormat) {
        this.portion = portion;
        this.ignore = isIgnore;
        this.isSeparator = isSeparator;
        this.isSpecificFormat = isSpecificFormat;
        this.specificFormat = specificFormat;
    }

    public String getPortionRepresentation() {
        return isSpecificFormat ? portion + " " + specificFormat : portion;
    }

    public void setPortion(String portion) {
        this.portion = portion;
    }

    public String getPortion() {
        return portion;
    }

    public boolean isIgnore() {
        return ignore;
    }

    public void setIgnore(boolean ignore) {
        this.ignore = ignore;
    }

    public boolean isSeparator() {
        return isSeparator;
    }

    public void setSeparator(boolean separator) {
        isSeparator = separator;
    }

    public boolean isSpecificFormat() {
        return isSpecificFormat;
    }

    public void setSpecificFormat(boolean isSpecificFormat) {
        this.isSpecificFormat = isSpecificFormat;
    }

    public String getSpecificFormat() {
        return specificFormat;
    }

    public void setSpecificFormat(String specificFormat) {
        this.specificFormat = specificFormat;
    }

    public boolean isLast() {
        return isLast;
    }

    public void setLast(boolean last) {
        isLast = last;
    }
}