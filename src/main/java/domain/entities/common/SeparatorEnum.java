package domain.entities.common;

public enum SeparatorEnum {
    SPACE("\" \"", " "),
    HIFEN("-", "-"),
    COLON(":", ":"),
    SEMI_COLON(";", ";"),
    TAB("\t", "\t"),
    COMMA(",", ","),
    OPEN_BRACKET("[", "["),
    CLOSE_BRACKET("]", "]");

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    private final String name;
    private final String symbol;

    SeparatorEnum(String name, String symbol) {
        this.name = name;
        this.symbol = symbol;
    }
}
