package data.dataaccess.reader;

import domain.entities.common.Keyword;
import domain.entities.common.ThresholdTypeEnum;
import domain.entities.common.ThresholdUnitEnum;
import domain.entities.common.WarningLevel;
import domain.entities.domainobjects.MetricsProfile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

import static data.dataaccess.common.MetricsProfileReadWriteConstants.END_KEYWORDS_TOKEN;
import static data.dataaccess.common.MetricsProfileReadWriteConstants.END_PROFILE_TOKEN;
import static data.dataaccess.common.MetricsProfileReadWriteConstants.EXPECTED_FIRST_LINE;
import static data.dataaccess.common.MetricsProfileReadWriteConstants.FILE_SIZE_TOKEN;
import static data.dataaccess.common.MetricsProfileReadWriteConstants.KEYWORD_HISTOGRAM_TOKEN;
import static data.dataaccess.common.MetricsProfileReadWriteConstants.KEYWORD_OVER_TIME_TOKEN;
import static data.dataaccess.common.MetricsProfileReadWriteConstants.KEYWORD_THRESHOLD_TOKEN;
import static data.dataaccess.common.MetricsProfileReadWriteConstants.MOST_COMMON_WORDS_TOKEN;
import static data.dataaccess.common.MetricsProfileReadWriteConstants.NAME_TOKEN;
import static data.dataaccess.common.MetricsProfileReadWriteConstants.NO_NAME_PROVIDED;
import static data.dataaccess.common.MetricsProfileReadWriteConstants.SEPARATOR;
import static data.dataaccess.common.MetricsProfileReadWriteConstants.START_KEYWORDS_TOKEN;
import static data.dataaccess.common.MetricsProfileReadWriteConstants.START_PROFILE_TOKEN;
import static domain.entities.common.ThresholdTypeEnum.BIGGER_OR_EQUAL_THAN;
import static domain.entities.common.ThresholdTypeEnum.BIGGER_THAN;
import static domain.entities.common.ThresholdTypeEnum.EQUAL_TO;
import static domain.entities.common.ThresholdTypeEnum.NOT_APPLICABLE;
import static domain.entities.common.ThresholdTypeEnum.SMALLER_OR_EQUAL_THAN;
import static domain.entities.common.ThresholdTypeEnum.SMALLER_THAN;
import static domain.entities.common.ThresholdUnitEnum.NONE;
import static domain.entities.common.ThresholdUnitEnum.OCCURRENCES;
import static domain.entities.common.ThresholdUnitEnum.PERCENTAGE;

public class MetricsProfileConsumer implements Consumer<String> {

    public static final int NAME_POSITION = 1;
    public static final int PAIRED_MINIMUM_SIZE = 2;
    public static final int KEYWORD_EXPECTED_SIZE = 6;
    public static final int KEYWORD_POSITION = 0;
    public static final int IS_CASE_SENSITIVE_POSITION = 1;
    public static final int THRESHOLD_TYPE_POSITION = 2;
    public static final int THRESHOLD_VALUE_POSITION = 3;
    public static final int THRESHOLD_UNIT_POSITION = 4;
    public static final int WARNING_LEVEL_POSITION = 5;
    private ArrayList<MetricsProfile> profiles;
    boolean firstLine = true;
    boolean hasProfileStarted = false;
    boolean hasProfileEnded = false;
    boolean hasKeywordsStarted = false;
    boolean hasKeywordsEnded = false;
    private int currentProfileIdx = -1;

    private static final Logger LOGGER = Logger.getLogger(MetricsProfileConsumer.class.getName());

    public MetricsProfileConsumer() {
        this.profiles = new ArrayList<>();
    }

    @Override
    public void accept(String line) {
        // Check if the file is for Metrics Profiles
        if(firstLine) {
            if(!EXPECTED_FIRST_LINE.equalsIgnoreCase(line)) {
                return;
            }
            firstLine = false;
        } else {
            if(hasProfileStarted) {
                if(line.startsWith(END_PROFILE_TOKEN)) {
                    // The current profile's contents have now ended
                    hasProfileEnded = true;
                    hasProfileStarted = false;
                    hasKeywordsStarted = false;
                    hasKeywordsEnded = true;
                } else {
                    parseSpecifically(line, getCurrentProfile());
                }
            } else {
                // deal with the profile's name
                if(line.startsWith(NAME_TOKEN)) {
                    addNewProfile();
                    MetricsProfile currentProfile = getCurrentProfile();

                    String [] split = line.split(SEPARATOR);
                    if(split.length > NAME_POSITION) {
                        if(!split[NAME_POSITION].isEmpty()) {
                            currentProfile.setName(split[NAME_POSITION]);
                        } else {
                            currentProfile.setName(NO_NAME_PROVIDED);
                        }
                    } else {
                        currentProfile.setName(NO_NAME_PROVIDED);
                    }
                } else if(line.startsWith(START_PROFILE_TOKEN)) {
                    // The profile contents started
                    hasProfileStarted = true;
                    hasProfileEnded = false;
                }
            }
        }
    }

    private void addNewProfile() {
        profiles.add(new MetricsProfile());
        currentProfileIdx++;
    }

    private void parseSpecifically(String s, MetricsProfile currentProfile) {
        if(hasKeywordsStarted) {
            if(s.startsWith(END_KEYWORDS_TOKEN)) {
                // Keywords contents started
                hasKeywordsStarted = false;
                hasKeywordsEnded = true;
            } else {
                parseKeyword(s, currentProfile);
            }
        } else if(s.startsWith(START_KEYWORDS_TOKEN)) {
            // Keywords contents started
            hasKeywordsStarted = true;
            hasKeywordsEnded = false;
        } else {
            String[] split = s.split(SEPARATOR);
            if(split.length != PAIRED_MINIMUM_SIZE) {
                return;
            }
            boolean bool = Boolean.parseBoolean(split[1]);
            if(split[0].startsWith(MOST_COMMON_WORDS_TOKEN)) {
                currentProfile.setHasMostCommonWords(bool);
            } else if(split[0].startsWith(FILE_SIZE_TOKEN)) {
                currentProfile.setHasFileSize(bool);
            } else if(split[0].startsWith(KEYWORD_HISTOGRAM_TOKEN)) {
                currentProfile.setHasKeywordHistogram(bool);
            } else if(split[0].startsWith(KEYWORD_OVER_TIME_TOKEN)) {
                currentProfile.setHasKeywordOverTime(bool);
            } else if(split[0].startsWith(KEYWORD_THRESHOLD_TOKEN)) {
                currentProfile.setHasKeywordThreshold(bool);
            }
        }
    }

    private void parseKeyword(String s, MetricsProfile currentProfile) {
        String[] split = s.split(SEPARATOR);
        if(split.length != KEYWORD_EXPECTED_SIZE) {
            return;
        }
        Keyword keyword = new Keyword(split[KEYWORD_POSITION], Boolean.parseBoolean(split[IS_CASE_SENSITIVE_POSITION]));
        keyword.setThresholdType(getThresholdType(split[THRESHOLD_TYPE_POSITION]));
        String valueString = split[THRESHOLD_VALUE_POSITION];
        try {
            keyword.setThresholdValue(new BigDecimal(valueString.isEmpty() ? "0" : valueString));
        } catch (NumberFormatException exception) {
            LOGGER.log(Level.INFO, "Value in keyword" + keyword.getKeywordText() + " was invalid: " + valueString);
            return;
        }
        keyword.setThresholdUnit(getThresholdUnit(split[THRESHOLD_UNIT_POSITION]));
        keyword.setWarningLevel(getWarningLevel(split[WARNING_LEVEL_POSITION]));
        currentProfile.addKeyword(keyword);
    }

    private ThresholdTypeEnum getThresholdType(String s) {
        if(NOT_APPLICABLE.getParsingString().equalsIgnoreCase(s)) {
            return NOT_APPLICABLE;
        }
        if(EQUAL_TO.getParsingString().equalsIgnoreCase(s)) {
            return EQUAL_TO;
        }
        if(BIGGER_THAN.getParsingString().equalsIgnoreCase(s)) {
            return BIGGER_THAN;
        }
        if(BIGGER_OR_EQUAL_THAN.getParsingString().equalsIgnoreCase(s)) {
            return BIGGER_OR_EQUAL_THAN;
        }
        if(SMALLER_THAN.getParsingString().equalsIgnoreCase(s)) {
            return SMALLER_THAN;
        }
        if(SMALLER_OR_EQUAL_THAN.getParsingString().equalsIgnoreCase(s)) {
            return SMALLER_OR_EQUAL_THAN;
        }
        return NOT_APPLICABLE;
    }

    private ThresholdUnitEnum getThresholdUnit(String s) {
        if(NONE.getParsingString().equalsIgnoreCase(s)) {
            return NONE;
        }
         if(OCCURRENCES.getParsingString().equalsIgnoreCase(s)) {
            return OCCURRENCES;
        }
         if(PERCENTAGE.getParsingString().equalsIgnoreCase(s)) {
            return PERCENTAGE;
        }
        return NONE;
    }

    private WarningLevel getWarningLevel(String s) {
        if(WarningLevel.NONE.getParsingString().equalsIgnoreCase(s)) {
            return WarningLevel.NONE;
        }
        if(WarningLevel.CRITICAL.getParsingString().equalsIgnoreCase(s)) {
            return WarningLevel.CRITICAL;
        }
        if(WarningLevel.HIGH.getParsingString().equalsIgnoreCase(s)) {
            return WarningLevel.HIGH;
        }
        if(WarningLevel.MEDIUM.getParsingString().equalsIgnoreCase(s)) {
            return WarningLevel.MEDIUM;
        }
        if(WarningLevel.LOW.getParsingString().equalsIgnoreCase(s)) {
            return WarningLevel.LOW;
        }
        if(WarningLevel.INFO.getParsingString().equalsIgnoreCase(s)) {
            return WarningLevel.INFO;
        }
        return WarningLevel.NONE;
    }

    private MetricsProfile getCurrentProfile() {
        if(profiles.isEmpty()) {
            profiles.add(new MetricsProfile());
        }
        if(currentProfileIdx < 0) {
            return null;
        }
        return profiles.get(currentProfileIdx);
    }

    public void clearList() {
        profiles = new ArrayList<>();
        currentProfileIdx= -1;
    }

    public MetricsProfile[] getProfiles() {
        return profiles.toArray(new MetricsProfile[0]);
    }
}
