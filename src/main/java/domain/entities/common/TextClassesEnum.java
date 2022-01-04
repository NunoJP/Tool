package domain.entities.common;

public enum TextClassesEnum {
    TIMESTAMP("Timestamp", "TIMESTAMP"),
    DATE("Date", "DATE"),
    TIME("Time", "TIME"),
    LEVEL("Level", "LEVEL"),
    MESSAGE("Message", "MESSAGE"),
    METHOD("Method", "METHOD"),
    ID("ID", "ID");

    public String getName() {
        return name;
    }

    public String getParsingString() {
        return parsingString;
    }

    private final String name;
    private String parsingString;

    TextClassesEnum(String name, String parsingString ) {
        this.name = name;
        this.parsingString = parsingString;
    }
}
