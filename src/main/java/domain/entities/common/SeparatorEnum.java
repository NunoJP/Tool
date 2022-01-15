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


    public static String getParsingStringBySymbol(String portion) {
        if(SPACE.getSymbol().equalsIgnoreCase(portion)) {
            return SPACE.parsingString;
        } else if(HIFEN.getSymbol().equalsIgnoreCase(portion)){
            return HIFEN.parsingString;
        } else if(COLON.getSymbol().equalsIgnoreCase(portion)){
            return COLON.parsingString;
        } else if(SEMI_COLON.getSymbol().equalsIgnoreCase(portion)){
            return SEMI_COLON.parsingString;
        } else if(TAB.getSymbol().equalsIgnoreCase(portion)){
            return TAB.parsingString;
        } else if(COMMA.getSymbol().equalsIgnoreCase(portion)){
            return COMMA.parsingString;
        } else if(OPEN_BRACKET.getSymbol().equalsIgnoreCase(portion)){
            return OPEN_BRACKET.parsingString;
        } else if(CLOSE_BRACKET.getSymbol().equalsIgnoreCase(portion)){
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
