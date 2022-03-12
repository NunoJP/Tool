package data.dataaccess.reader;

import domain.entities.common.ParsingProfilePortion;
import domain.entities.common.SeparatorEnum;
import domain.entities.common.TextClassesEnum;
import domain.entities.domainobjects.ParsingProfile;

import java.util.ArrayList;
import java.util.function.Consumer;

import static data.dataaccess.common.ParsingProfileReadWriteConstants.END_PROFILE_TOKEN;
import static data.dataaccess.common.ParsingProfileReadWriteConstants.EXPECTED_FIRST_LINE;
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
    private static final int SKIPS_POSITION = 2;

    private ArrayList<ParsingProfile> profiles;
    boolean firstLine = true;
    boolean hasProfileStarted = false;
    boolean hasProfileEnded = false;
    private int currentProfileIdx = -1;


    public ParsingProfileConsumer() {
        this.profiles = new ArrayList<ParsingProfile>();
    }

    @Override
    public void accept(String s) {
        // identify if the file contains parsing profiles
        if(firstLine) {
            if(!EXPECTED_FIRST_LINE.equalsIgnoreCase(s)){
                return;
            }
            firstLine = false;
        } else {
            if(hasProfileStarted) {
                if(s.startsWith(END_PROFILE_TOKEN)){
                    // The profile contents have now ended
                    hasProfileEnded = true;
                    hasProfileStarted = false;
                    if(getCurrentProfile() != null) {
                        getCurrentProfile().finishProfile();
                    }
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
            currentProfile.addPortion(createSeparatorPortion(SeparatorEnum.SPACE, s));
        } else if(s.startsWith(SeparatorEnum.HIFEN.getParsingString())) {
            currentProfile.addPortion(createSeparatorPortion(SeparatorEnum.HIFEN, s));
        } else if(s.startsWith(SeparatorEnum.COLON.getParsingString())) {
            currentProfile.addPortion(createSeparatorPortion(SeparatorEnum.COLON, s));
        } else if(s.startsWith(SeparatorEnum.SEMI_COLON.getParsingString())) {
            currentProfile.addPortion(createSeparatorPortion(SeparatorEnum.SEMI_COLON, s));
        } else if(s.startsWith(SeparatorEnum.TAB.getParsingString())) {
            currentProfile.addPortion(createSeparatorPortion(SeparatorEnum.TAB, s));
        } else if(s.startsWith(SeparatorEnum.COMMA.getParsingString())) {
            currentProfile.addPortion(createSeparatorPortion(SeparatorEnum.COMMA, s));
        } else if(s.startsWith(SeparatorEnum.OPEN_BRACKET.getParsingString())) {
            currentProfile.addPortion(createSeparatorPortion(SeparatorEnum.OPEN_BRACKET, s));
        } else if(s.startsWith(SeparatorEnum.CLOSE_BRACKET.getParsingString())) {
            currentProfile.addPortion(createSeparatorPortion(SeparatorEnum.CLOSE_BRACKET, s));
        }
    }

    private ParsingProfilePortion createSeparatorPortion(SeparatorEnum separatorEnum, String s) {
        String[] parts = s.split(PORTION_SEPARATOR);
        int skips = Integer.parseInt(parts[SKIPS_POSITION]);
        return new ParsingProfilePortion(separatorEnum.getName(),
                separatorEnum.getSymbol(), false, true, false,
                ParsingProfilePortion.NO_SPECIFIC_FORMAT, skips);
    }

    private void parseSpecifically(String s, TextClassesEnum textClass, ParsingProfile currentProfile) {
        String[] parts = s.split(PORTION_SEPARATOR);
        if(parts.length < MINIMUM_SIZE_FOR_TEXT_CLASSES) {
            return;
        }
        if(IGNORE_TOKEN.equalsIgnoreCase(parts[1])){
            currentProfile.addPortion(new ParsingProfilePortion(textClass.getName(), textClass.getName(), true, false));
        } else if(KEEP_TOKEN.equalsIgnoreCase(parts[1])){
            currentProfile.addPortion(new ParsingProfilePortion(textClass.getName(), textClass.getName(), false, false));
        } else if(KEEP_SPECIFIC_TOKEN.equalsIgnoreCase(parts[1]) && parts.length >= SPECIFIC_FORMAT_INDEX + 1){
            ParsingProfilePortion portion = new ParsingProfilePortion(textClass.getName(), textClass.getName(), false, false, true);
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

    public ParsingProfile[] getProfiles() {
        return profiles.toArray(new ParsingProfile[0]);
    }
}
