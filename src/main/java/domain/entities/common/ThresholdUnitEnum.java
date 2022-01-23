package domain.entities.common;

public enum ThresholdUnitEnum {

    OCCURRENCES("Occ", "occurrences", "OCCURRENCES"),
    PERCENTAGE("%", "percentage", "PERCENTAGE")
    ;

    private final String name;
    private final String symbol;
    private final String parsingString;

    ThresholdUnitEnum(String name, String symbol, String parsingString) {
        this.name = name;
        this.symbol = symbol;
        this.parsingString = parsingString;
    }


    public static String getParsingStringByName(String portion) {
        if(OCCURRENCES.getName().equalsIgnoreCase(portion)) {
            return OCCURRENCES.parsingString;
        } else if(PERCENTAGE.getName().equalsIgnoreCase(portion)){
            return PERCENTAGE.parsingString;
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
