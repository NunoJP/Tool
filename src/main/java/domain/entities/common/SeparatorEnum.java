package domain.entities.common;

public enum SeparatorEnum {
    SPACE("\" \"", " ", "SPACE"),
    HIFEN("-", "-", "HIFEN"),
    COLON(":", ":", "COLON"),
    SEMI_COLON(";", ";", "SEMI_COLON"),
    TAB("\t", "\t", "TAB"),
    COMMA(",", ",", "COMMA"),
    OPEN_BRACKET("[", "[", "OPEN_BRACKET"),
    CLOSE_BRACKET("]", "]", "CLOSE_BRACKET");

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
