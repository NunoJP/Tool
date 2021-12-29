package domain.entities.common;

public enum TextClassesEnum {
    TIMESTAMP("Timestamp"),
    DATE("Date"),
    TIME("Time"),
    LEVEL("Level"),
    MESSAGE("Message"),
    METHOD("Method"),
    ID("ID");

    public String getName() {
        return name;
    }

    private final String name;

    TextClassesEnum(String name) {
        this.name = name;
    }
}
