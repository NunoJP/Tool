package domain.entities.common;

public enum TextClassesEnum {
    TIMESTAMP("Timestamp", "TIMESTAMP"),
    DATE("Date", "DATE"),
    TIME("Time", "TIME"),
    LEVEL("Level", "LEVEL"),
    MESSAGE("Message", "MESSAGE"),
    ORIGIN("Origin", "ORIGIN"),
    ID("ID", "ID");

    public static String getParsingStringByName(String portion) {
        if(TIMESTAMP.getName().equalsIgnoreCase(portion)) {
            return TIMESTAMP.parsingString;
        } else if(DATE.getName().equalsIgnoreCase(portion)){
            return DATE.parsingString;
        } else if(TIME.getName().equalsIgnoreCase(portion)){
            return TIME.parsingString;
        } else if(LEVEL.getName().equalsIgnoreCase(portion)){
            return LEVEL.parsingString;
        } else if(MESSAGE.getName().equalsIgnoreCase(portion)){
            return MESSAGE.parsingString;
        } else if(ORIGIN.getName().equalsIgnoreCase(portion)){
            return ORIGIN.parsingString;
        } else if(ID.getName().equalsIgnoreCase(portion)){
            return ID.parsingString;
        }
        return "";
    }

    public String getName() {
        return name;
    }

    public String getParsingString() {
        return parsingString;
    }

    private final String name;
    private final String parsingString;

    TextClassesEnum(String name, String parsingString ) {
        this.name = name;
        this.parsingString = parsingString;
    }
}
