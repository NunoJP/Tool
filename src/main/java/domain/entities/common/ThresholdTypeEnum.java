package domain.entities.common;

public enum ThresholdTypeEnum {
    NOT_APPLICABLE("NA", "none", "NOT_APPLICABLE"),
    EQUAL_TO("=", "equalTo", "EQUAL_TO"),
    BIGGER_THAN(">", "biggerThan", "BIGGER_THAN"),
    BIGGER_OR_EQUAL_THAN(">=", "biggerOrEqualThan", "BIGGER_OR_EQUAL_THAN"),
    SMALLER_THAN("<", "smallerThan", "SMALLER_THAN"),
    SMALLER_OR_EQUAL_THAN("<=", "smallerOrEqualThan", "SMALLER_OR_EQUAL_THAN"),
    ;

    private final String name;
    private final String symbol;
    private final String parsingString;

    ThresholdTypeEnum(String name, String symbol, String parsingString) {
        this.name = name;
        this.symbol = symbol;
        this.parsingString = parsingString;
    }

    public static String getParsingStringByName(String portion) {
        if(NOT_APPLICABLE.getName().equalsIgnoreCase(portion)) {
            return NOT_APPLICABLE.parsingString;
        } else if(EQUAL_TO.getName().equalsIgnoreCase(portion)){
            return EQUAL_TO.parsingString;
        } else if(BIGGER_THAN.getName().equalsIgnoreCase(portion)){
            return BIGGER_THAN.parsingString;
        } else if(BIGGER_OR_EQUAL_THAN.getName().equalsIgnoreCase(portion)){
            return BIGGER_OR_EQUAL_THAN.parsingString;
        } else if(SMALLER_THAN.getName().equalsIgnoreCase(portion)){
            return SMALLER_THAN.parsingString;
        } else if(SMALLER_OR_EQUAL_THAN.getName().equalsIgnoreCase(portion)){
            return SMALLER_OR_EQUAL_THAN.parsingString;
        }
        return "";
    }

    public String getName() {
        return name;
    }

    public String getParsingString() {
        return parsingString;
    }

    public String getSymbol() {
        return symbol;
    }
}
