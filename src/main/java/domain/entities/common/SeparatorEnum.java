package domain.entities.common;

public enum SeparatorEnum {
    SPACE("\" \"", " ", "SPACE"),
    HIFEN("-", "-", "HIFEN"),
    COLON(":", ":", "COLON"),
    SEMI_COLON(";", ";", "SEMI_COLON"),
    TAB("\\t", "\t", "TAB"),
    COMMA(",", ",", "COMMA"),
    OPEN_BRACKET("[", "[", "OPEN_BRACKET"),
    CLOSE_BRACKET("]", "]", "CLOSE_BRACKET");


    public static String getParsingStringByName(String portion) {
        if(SPACE.getName().equalsIgnoreCase(portion)) {
            return SPACE.parsingString;
        } else if(HIFEN.getName().equalsIgnoreCase(portion)){
            return HIFEN.parsingString;
        } else if(COLON.getName().equalsIgnoreCase(portion)){
            return COLON.parsingString;
        } else if(SEMI_COLON.getName().equalsIgnoreCase(portion)){
            return SEMI_COLON.parsingString;
        } else if(TAB.getName().equalsIgnoreCase(portion)){
            return TAB.parsingString;
        } else if(COMMA.getName().equalsIgnoreCase(portion)){
            return COMMA.parsingString;
        } else if(OPEN_BRACKET.getName().equalsIgnoreCase(portion)){
            return OPEN_BRACKET.parsingString;
        } else if(CLOSE_BRACKET.getName().equalsIgnoreCase(portion)){
            return CLOSE_BRACKET.parsingString;
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

    private final String name;
    private final String symbol;
    private final String parsingString;

    SeparatorEnum(String name, String symbol, String parsingString) {
        this.name = name;
        this.symbol = symbol;
        this.parsingString = parsingString;
    }
}
