package domain.entities.common;

public class ParsingProfilePortion {
    public static final String NO_SPECIFIC_FORMAT = "NoFormat";
    private static final int DEFAULT_NUMBER_OF_SKIPS = 0;

    private String portionName;
    private String portionSymbol;
    private boolean ignore;
    private boolean isSeparator;
    private String specificFormat;
    private final int numberOfSkips;
    private boolean isSpecificFormat;
    private boolean isLast = false;

    public ParsingProfilePortion(String portionName, String portionSymbol, boolean ignore, boolean isSeparator) {
        this(portionName, portionSymbol, ignore, isSeparator, false, NO_SPECIFIC_FORMAT, DEFAULT_NUMBER_OF_SKIPS);
    }

    public ParsingProfilePortion(String portionName, String portionSymbol, boolean ignore, boolean isSeparator, boolean specificFormat) {
        this(portionName, portionSymbol, ignore, isSeparator, specificFormat, NO_SPECIFIC_FORMAT);
    }

    public ParsingProfilePortion(String portionName, String portionSymbol, boolean isIgnore, boolean isSeparator, boolean isSpecificFormat, String specificFormat) {
        this(portionName, portionSymbol, isIgnore, isSeparator, isSpecificFormat, specificFormat, DEFAULT_NUMBER_OF_SKIPS);
    }

    public ParsingProfilePortion(String portionName, String portionSymbol, boolean isIgnore, boolean isSeparator, boolean isSpecificFormat, String specificFormat, int numberOfSkips) {
        this.portionName = portionName;
        this.portionSymbol = portionSymbol;
        this.ignore = isIgnore;
        this.isSeparator = isSeparator;
        this.isSpecificFormat = isSpecificFormat;
        this.specificFormat = specificFormat;
        this.numberOfSkips = numberOfSkips;
    }

    public String getPortionRepresentation() {
        if(isSeparator) {
          return portionName + " skips:" + numberOfSkips;
        }
        return isSpecificFormat ? portionName + " " + specificFormat : portionName;
    }

    public void setPortionName(String portionName) {
        this.portionName = portionName;
    }

    public String getPortionName() {
        return portionName;
    }

    public String getPortionSymbol() {
        return portionSymbol;
    }

    public void setPortionSymbol(String portionSymbol) {
        this.portionSymbol = portionSymbol;
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

    public int getNumberOfSkips() {
        return numberOfSkips;
    }
}