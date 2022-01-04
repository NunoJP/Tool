package data.dataaccess.reader;

import domain.entities.common.ParsingProfilePortion;
import domain.entities.common.SeparatorEnum;
import domain.entities.common.TextClassesEnum;
import domain.entities.domainobjects.ParsingProfile;

import java.util.ArrayList;
import java.util.function.Consumer;

import static data.dataaccess.common.ParsingProfileReadWriteConstants.END_PROFILE_TOKEN;
import static data.dataaccess.common.ParsingProfileReadWriteConstants.IGNORE_TOKEN;
import static data.dataaccess.common.ParsingProfileReadWriteConstants.KEEP_SPECIFIC_TOKEN;
import static data.dataaccess.common.ParsingProfileReadWriteConstants.KEEP_TOKEN;
import static data.dataaccess.common.ParsingProfileReadWriteConstants.NAME_TOKEN;
import static data.dataaccess.common.ParsingProfileReadWriteConstants.NO_NAME_PROVIDED;
import static data.dataaccess.common.ParsingProfileReadWriteConstants.PORTION_SEPARATOR;
import static data.dataaccess.common.ParsingProfileReadWriteConstants.START_PROFILE_TOKEN;

public class ParsingProfileConsumer implements Consumer<String> {
    private static final int MINIMUM_SIZE_FOR_TEXT_CLASSES = 2;
    public static final int SPECIFIC_FORMAT_INDEX = 2;
    public static final int NAME_POSITION = 1;

    private ArrayList<ParsingProfile> profiles;
    boolean firstLine = true;
    boolean hasProfileStarted = false;
    boolean hasProfileEnded = false;
    private int currentProfileIdx = -1;
    private ParsingStatus status = ParsingStatus.OK;
    private final String expectedFirstLine = "Parsing Profile";

    public ParsingProfileConsumer() {
        this.profiles = new ArrayList<ParsingProfile>();
    }

    @Override
    public void accept(String s) {
        // identify if the file contains parsing profiles
        if(firstLine) {
            if(!expectedFirstLine.equalsIgnoreCase(s)){
                status = ParsingStatus.FAILURE;
                return;
            }
            firstLine = false;
        } else {
            if(hasProfileStarted) {
                if(s.startsWith(END_PROFILE_TOKEN)){
                    // The profile contents have now ended
                    hasProfileEnded = true;
                    hasProfileStarted = false;
                } else {
                    parseSpecifically(s, getCurrentProfile());
                }
            } else {
                // Deal with the profile's name
                if(s.startsWith(NAME_TOKEN)) {
                    addNewProfile();
                    ParsingProfile currentProfile = getCurrentProfile();

                    String[] split = s.split(PORTION_SEPARATOR);
                    if(split.length > NAME_POSITION) {
                        if(!split[NAME_POSITION].isEmpty()) {
                            currentProfile.setName(split[NAME_POSITION]);
                        } else {
                            currentProfile.setName(NO_NAME_PROVIDED);
                        }
                    } else {
                        currentProfile.setName(NO_NAME_PROVIDED);
                    }
                } else if(s.startsWith(START_PROFILE_TOKEN)) {
                    // The profile contents start now
                    hasProfileStarted = true;
                    hasProfileEnded = false;
                }
            }

        }
    }

    private void addNewProfile() {
        profiles.add(new ParsingProfile());
        currentProfileIdx++;
    }


    private void parseSpecifically(String s, ParsingProfile currentProfile) {
        if(s.startsWith(TextClassesEnum.TIMESTAMP.getParsingString())) {
            parseSpecifically(s, TextClassesEnum.TIMESTAMP, currentProfile);
        } else if(s.startsWith(TextClassesEnum.DATE.getParsingString())) {
            parseSpecifically(s, TextClassesEnum.DATE, currentProfile);
        } else if(s.startsWith(TextClassesEnum.TIME.getParsingString())) {
            parseSpecifically(s, TextClassesEnum.TIME, currentProfile);
        } else if(s.startsWith(TextClassesEnum.LEVEL.getParsingString())) {
            parseSpecifically(s, TextClassesEnum.LEVEL, currentProfile);
        } else if(s.startsWith(TextClassesEnum.MESSAGE.getParsingString())) {
            parseSpecifically(s, TextClassesEnum.MESSAGE, currentProfile);
        } else if(s.startsWith(TextClassesEnum.METHOD.getParsingString())) {
            parseSpecifically(s, TextClassesEnum.METHOD, currentProfile);
        } else if(s.startsWith(TextClassesEnum.ID.getParsingString())) {
            parseSpecifically(s, TextClassesEnum.ID, currentProfile);
        } else if(s.startsWith(SeparatorEnum.SPACE.getParsingString())) {
            currentProfile.addPortion(new ParsingProfilePortion(SeparatorEnum.SPACE.getSymbol(), false, true));
        } else if(s.startsWith(SeparatorEnum.HIFEN.getParsingString())) {
            currentProfile.addPortion(new ParsingProfilePortion(SeparatorEnum.HIFEN.getSymbol(), false, true));
        } else if(s.startsWith(SeparatorEnum.COLON.getParsingString())) {
            currentProfile.addPortion(new ParsingProfilePortion(SeparatorEnum.COLON.getSymbol(), false, true));
        } else if(s.startsWith(SeparatorEnum.SEMI_COLON.getParsingString())) {
            currentProfile.addPortion(new ParsingProfilePortion(SeparatorEnum.SEMI_COLON.getSymbol(), false, true));
        } else if(s.startsWith(SeparatorEnum.TAB.getParsingString())) {
            currentProfile.addPortion(new ParsingProfilePortion(SeparatorEnum.TAB.getSymbol(), false, true));
        } else if(s.startsWith(SeparatorEnum.COMMA.getParsingString())) {
            currentProfile.addPortion(new ParsingProfilePortion(SeparatorEnum.COMMA.getSymbol(), false, true));
        } else if(s.startsWith(SeparatorEnum.OPEN_BRACKET.getParsingString())) {
            currentProfile.addPortion(new ParsingProfilePortion(SeparatorEnum.OPEN_BRACKET.getSymbol(), false, true));
        } else if(s.startsWith(SeparatorEnum.CLOSE_BRACKET.getParsingString())) {
            currentProfile.addPortion(new ParsingProfilePortion(SeparatorEnum.CLOSE_BRACKET.getSymbol(), false, true));
        }
    }

    private void parseSpecifically(String s, TextClassesEnum textClass, ParsingProfile currentProfile) {
        String[] parts = s.split(PORTION_SEPARATOR);
        if(parts.length < MINIMUM_SIZE_FOR_TEXT_CLASSES) {
            return;
        }
        if(IGNORE_TOKEN.equalsIgnoreCase(parts[1])){
            currentProfile.addPortion(new ParsingProfilePortion(textClass.getName(), true, false));
        } else if(KEEP_TOKEN.equalsIgnoreCase(parts[1])){
            currentProfile.addPortion(new ParsingProfilePortion(textClass.getName(), false, false));
        } else if(KEEP_SPECIFIC_TOKEN.equalsIgnoreCase(parts[1]) && parts.length >= SPECIFIC_FORMAT_INDEX + 1){
            ParsingProfilePortion portion = new ParsingProfilePortion(textClass.getName(), false, false, true);
            portion.setSpecificFormat(parts[SPECIFIC_FORMAT_INDEX]);
            currentProfile.addPortion(portion);
        }
    }

    private ParsingProfile getCurrentProfile() {
        if(profiles.isEmpty()) {
            profiles.add(new ParsingProfile());
        }
        if(currentProfileIdx < 0) {
            return null;
        }
        return profiles.get(currentProfileIdx);
    }

    public void clearList() {
        profiles = new ArrayList<>();
        currentProfileIdx = -1;
    }

    public boolean keepGoing() {
        return status.getStatus() != ParsingStatus.FAILURE.getStatus();
    }

    public ParsingProfile[] getProfiles() {
        return profiles.toArray(new ParsingProfile[0]);
    }
}
