package domain.entities.common;

public enum WarningLevel {
    NONE("none", "None", "NONE"),
    CRITICAL("critical", "Critical", "CRITICAL"),
    HIGH("high", "High", "HIGH"),
    MEDIUM("medium", "Medium", "MEDIUM"),
    LOW("low", "Low", "LOW"),
    INFO("info", "Info", "INFO"),
    ;

    private final String name;
    private final String symbol;
    private final String parsingString;

    WarningLevel(String name, String symbol, String parsingString) {
        this.name = name;
        this.symbol = symbol;
        this.parsingString = parsingString;
    }

    public static String getParsingStringByName(String name) {
        if(NONE.getName().equalsIgnoreCase(name)) {
            return NONE.getParsingString();
        }
        return "";
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getParsingString() {
        return parsingString;
    }

}
