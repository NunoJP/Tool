package data.dataaccess.writer;

import data.dataaccess.ParsingProfilesCommon;
import data.dataaccess.reader.ParsingProfileConsumer;
import domain.entities.common.ParsingProfilePortion;
import domain.entities.common.SeparatorEnum;
import domain.entities.common.TextClassesEnum;
import domain.entities.domainobjects.ParsingProfile;
import org.junit.Before;
import org.junit.Test;
import presentation.common.GuiConstants;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class ParsingProfileFunctionTests  extends ParsingProfilesCommon {

    private ParsingProfileFunction function;
    private ParsingProfileConsumer consumer;
    private ParsingProfile[] profiles;

    @Before
    public void setUp() {
        function = new ParsingProfileFunction(true);
        consumer = getConsumer();
        profiles = null;
    }


    @Test
    public void simpleTest() {
        ParsingProfile profile = new ParsingProfile();
        profile.setName(VALUE);
        profile.addPortion(new ParsingProfilePortion(TextClassesEnum.LEVEL.getName(), TextClassesEnum.LEVEL.getName(), false, false));

        // Process
        simulateWriteAndRead(function, consumer, profile);

        // validations
        ParsingProfile[] profiles = consumer.getProfiles();
        assertEquals(1, profiles.length);
        ParsingProfile parsedProfile = profiles[0];
        assertEquals(profile.getName(), parsedProfile.getName());
        ArrayList<ParsingProfilePortion> portions = parsedProfile.getPortions();
        assertEquals(1, portions.size());
        validatePortion(portions.get(0), false, false, false, TextClassesEnum.LEVEL.getName());
    }


    @Test
    public void simpleTestTwoProfiles() {
        // first profile
        ParsingProfile profile1 = new ParsingProfile();
        profile1.setName(VALUE + 1);
        profile1.addPortion(new ParsingProfilePortion(TextClassesEnum.LEVEL.getName(), TextClassesEnum.LEVEL.getName(), false, false));
        // second profile
        ParsingProfile profile2 = new ParsingProfile();
        profile2.setName(VALUE + 2);
        profile2.addPortion(new ParsingProfilePortion(TextClassesEnum.LEVEL.getName(), TextClassesEnum.LEVEL.getName(), false, false));

        // Process
        simulateWriteAndRead(function, consumer, profile1);
        simulateWriteAndRead(function, consumer, profile2);

        // validations
        profiles = consumer.getProfiles();
        assertEquals(2, profiles.length);

        // profiles
        for(int i = 0; i < 2; i++){
            ParsingProfile profile = profiles[i];
            assertEquals(VALUE + (i + 1), profile.getName());
            ArrayList<ParsingProfilePortion> portions = profile.getPortions();
            assertEquals(1, portions.size());
            validatePortion(portions.get(0), false, false, false, TextClassesEnum.LEVEL.getName());
        }
    }

    @Test
    public void testWithSeparatorsAndTextClasses() {
        // profile
        ParsingProfile profile = new ParsingProfile();
        profile.setName(VALUE);
        profile.addPortion(new ParsingProfilePortion(TextClassesEnum.LEVEL.getName(), TextClassesEnum.LEVEL.getName(), false, false));
        ParsingProfilePortion colonPortion = new ParsingProfilePortion(SeparatorEnum.COLON.getName(), SeparatorEnum.COLON.getSymbol(), false, true);
        profile.addPortion(colonPortion);
        profile.addPortion(new ParsingProfilePortion(TextClassesEnum.TIME.getName(), TextClassesEnum.TIME.getName(), true, false));
        ParsingProfilePortion hifenPortion = new ParsingProfilePortion(SeparatorEnum.HIFEN.getName(), SeparatorEnum.HIFEN.getSymbol(), false, true);
        profile.addPortion(hifenPortion);

        // Process
        simulateWriteAndRead(function, consumer, profile);

        // validations
        profiles = consumer.getProfiles();
        assertEquals(1, profiles.length);
        ParsingProfile parsedProfile = profiles[0];
        assertEquals(profile.getName(), parsedProfile.getName());
        ArrayList<ParsingProfilePortion> portions = parsedProfile.getPortions();
        assertEquals(4, portions.size());
        validatePortion(portions.get(0), false, false, false, TextClassesEnum.LEVEL.getName());
        validatePortion(portions.get(1), true, false, false, colonPortion.getPortionRepresentation());
        validatePortion(portions.get(2), false, true, false, TextClassesEnum.TIME.getName());
        validatePortion(portions.get(3), true, false, false, hifenPortion.getPortionRepresentation());
    }


    @Test
    public void testWithSpecificFormat() {
        // setup
        ParsingProfile profile = new ParsingProfile();
        profile.setName(VALUE);
        ParsingProfilePortion parsingProfilePortion = new ParsingProfilePortion(TextClassesEnum.DATE.getName(), TextClassesEnum.DATE.getName(), false, false, true);
        parsingProfilePortion.setSpecificFormat(GuiConstants.DATE_FORMATTER);
        profile.addPortion(parsingProfilePortion);

        ParsingProfilePortion colonPortion = new ParsingProfilePortion(SeparatorEnum.COLON.getName(), SeparatorEnum.COLON.getSymbol(), false, true);
        profile.addPortion(colonPortion);

        ParsingProfilePortion parsingProfilePortion2 = new ParsingProfilePortion(TextClassesEnum.TIME.getName(), TextClassesEnum.TIME.getName(), false, false, true);
        parsingProfilePortion2.setSpecificFormat(GuiConstants.DATE_TIME_FORMATTER);
        profile.addPortion(parsingProfilePortion2);

        ParsingProfilePortion hyphenPortion = new ParsingProfilePortion(SeparatorEnum.HIFEN.getName(), SeparatorEnum.HIFEN.getSymbol(), false, true);
        profile.addPortion(hyphenPortion);

        // Process
        simulateWriteAndRead(function, consumer, profile);

        // validations
        profiles = consumer.getProfiles();
        assertEquals(1, profiles.length);
        ParsingProfile parsedProfile = profiles[0];
        assertEquals(profile.getName(), parsedProfile.getName());
        ArrayList<ParsingProfilePortion> portions = parsedProfile.getPortions();
        assertEquals(4, portions.size());
        validatePortion(portions.get(0), false, false, true, TextClassesEnum.DATE.getName() + " " + portions.get(0).getSpecificFormat());
        assertEquals(GuiConstants.DATE_FORMATTER, portions.get(0).getSpecificFormat());
        validatePortion(portions.get(1), true, false, false, colonPortion.getPortionRepresentation());
        validatePortion(portions.get(2), false, false, true, TextClassesEnum.TIME.getName() + " " + portions.get(2).getSpecificFormat());
        assertEquals(GuiConstants.DATE_TIME_FORMATTER, portions.get(2).getSpecificFormat());
        validatePortion(portions.get(3), true, false, false, hyphenPortion.getPortionRepresentation());
    }

    @Test
    public void testJustSeparators() {
        // setup
        ParsingProfile profile = new ParsingProfile();
        profile.setName(VALUE);
        ParsingProfilePortion colonPortion = new ParsingProfilePortion(SeparatorEnum.COLON.getName(), SeparatorEnum.COLON.getSymbol(), false, true);
        profile.addPortion(colonPortion);
        ParsingProfilePortion hyphenPortion = new ParsingProfilePortion(SeparatorEnum.HIFEN.getName(), SeparatorEnum.HIFEN.getSymbol(), false, true);
        profile.addPortion(hyphenPortion);

        // Process
        simulateWriteAndRead(function, consumer, profile);

        // validations
        profiles = consumer.getProfiles();
        assertEquals(1, profiles.length);
        ParsingProfile parsedProfile = profiles[0];
        assertEquals(profile.getName(), parsedProfile.getName());
        ArrayList<ParsingProfilePortion> portions = parsedProfile.getPortions();
        assertEquals(2, portions.size());
        validatePortion(portions.get(0), true, false, false, colonPortion.getPortionRepresentation());
        validatePortion(portions.get(1), true, false, false, hyphenPortion.getPortionRepresentation());
    }

    @Test
    public void testJustTextClasses() {
        // setup
        ParsingProfile profile = new ParsingProfile();
        profile.setName(VALUE);
        profile.addPortion(new ParsingProfilePortion(TextClassesEnum.LEVEL.getName(), TextClassesEnum.LEVEL.getName(), false, false));
        profile.addPortion(new ParsingProfilePortion(TextClassesEnum.TIME.getName(), TextClassesEnum.TIME.getName(), true, false));

        // Process
        simulateWriteAndRead(function, consumer, profile);

        // validations
        profiles = consumer.getProfiles();
        assertEquals(1, profiles.length);
        ParsingProfile parsedProfile = profiles[0];
        assertEquals(profile.getName(), parsedProfile.getName());
        ArrayList<ParsingProfilePortion> portions = parsedProfile.getPortions();
        assertEquals(2, portions.size());
        validatePortion(portions.get(0), false, false, false, TextClassesEnum.LEVEL.getName());
        validatePortion(portions.get(1), false, true, false, TextClassesEnum.TIME.getName());
    }

    @Test
    public void testJustHeader() {
        // setup
        ParsingProfile profile = new ParsingProfile();
        profile.setName(VALUE);

        // Process
        simulateWriteAndRead(function, consumer, profile);

        // validations
        profiles = consumer.getProfiles();
        assertEquals(1, profiles.length);
        ParsingProfile parsedProfile = profiles[0];
        assertEquals(profile.getName(), parsedProfile.getName());
        ArrayList<ParsingProfilePortion> portions = parsedProfile.getPortions();
        assertEquals(0, portions.size());
    }

    @Test
    public void testSpecificFormatWithNoFormat() {
        // setup
        ParsingProfile profile = new ParsingProfile();
        profile.setName(VALUE);
        ParsingProfilePortion parsingProfilePortion = new ParsingProfilePortion(TextClassesEnum.DATE.getName(), TextClassesEnum.DATE.getName(), false, false, true);
        parsingProfilePortion.setSpecificFormat("");
        profile.addPortion(parsingProfilePortion);

        ParsingProfilePortion colonPortion = new ParsingProfilePortion(SeparatorEnum.COLON.getName(), SeparatorEnum.COLON.getSymbol(), false, true);
        profile.addPortion(colonPortion);

        ParsingProfilePortion parsingProfilePortion2 = new ParsingProfilePortion(TextClassesEnum.TIME.getName(), TextClassesEnum.TIME.getName(), false, false, true);
        parsingProfilePortion2.setSpecificFormat("");
        profile.addPortion(parsingProfilePortion2);

        ParsingProfilePortion hyphenPortion = new ParsingProfilePortion(SeparatorEnum.HIFEN.getName(), SeparatorEnum.HIFEN.getSymbol(), false, true);
        profile.addPortion(hyphenPortion);

        // Process
        simulateWriteAndRead(function, consumer, profile);

        // validations
        profiles = consumer.getProfiles();
        assertEquals(1, profiles.length);
        ParsingProfile parsedProfile = profiles[0];
        assertEquals(profile.getName(), parsedProfile.getName());
        ArrayList<ParsingProfilePortion> portions = parsedProfile.getPortions();
        assertEquals(2, portions.size());
        validatePortion(portions.get(0), true, false, false, colonPortion.getPortionRepresentation());
        validatePortion(portions.get(1), true, false, false, hyphenPortion.getPortionRepresentation());
    }



    @Test
    public void testNonExistingTextClass() {
        // setup
        ParsingProfile profile = new ParsingProfile();
        profile.setName(VALUE);
        profile.addPortion(new ParsingProfilePortion("NON_EXITING_CLASS", "NON_EXITING_CLASS", false, false));
        profile.addPortion(new ParsingProfilePortion("NON_EXITING_CLASS_2", "NON_EXITING_CLASS_2", true, false));

        // Process
        simulateWriteAndRead(function, consumer, profile);

        // validations
        profiles = consumer.getProfiles();
        assertEquals(1, profiles.length);
        ParsingProfile parsedProfile = profiles[0];
        assertEquals(profile.getName(), parsedProfile.getName());
        ArrayList<ParsingProfilePortion> portions = parsedProfile.getPortions();
        assertEquals(0, portions.size());
    }


    @Test
    public void testNonExistingSeparator() {
        // setup
        ParsingProfile profile = new ParsingProfile();
        profile.setName(VALUE);
        profile.addPortion(new ParsingProfilePortion("NON_EXITING_SEPARATOR", "NON_EXITING_SEPARATOR", false, true));
        profile.addPortion(new ParsingProfilePortion("NON_EXITING_SEPARATOR_2", "NON_EXITING_SEPARATOR_2", false, true));

        // Process
        simulateWriteAndRead(function, consumer, profile);

        // validations
        profiles = consumer.getProfiles();
        assertEquals(1, profiles.length);
        ParsingProfile parsedProfile = profiles[0];
        assertEquals(profile.getName(), parsedProfile.getName());
        ArrayList<ParsingProfilePortion> portions = parsedProfile.getPortions();
        assertEquals(0, portions.size());
    }


    @Test
    public void testIsIgnoreSeparator() {
        // setup
        ParsingProfile profile = new ParsingProfile();
        profile.setName(VALUE);
        profile.addPortion(new ParsingProfilePortion("NON_EXITING_SEPARATOR", "NON_EXITING_SEPARATOR", true, true));

        // Process
        simulateWriteAndRead(function, consumer, profile);

        // validations
        profiles = consumer.getProfiles();
        assertEquals(1, profiles.length);
        ParsingProfile parsedProfile = profiles[0];
        assertEquals(profile.getName(), parsedProfile.getName());
        ArrayList<ParsingProfilePortion> portions = parsedProfile.getPortions();
        assertEquals(0, portions.size());
    }

    @Test
    public void testIsIgnoreSpecificFormatSeparator() {
        // setup
        ParsingProfile profile = new ParsingProfile();
        profile.setName(VALUE);
        ParsingProfilePortion parsingProfilePortion = new ParsingProfilePortion(TextClassesEnum.DATE.getName(), TextClassesEnum.DATE.getName(), true, false, true);
        parsingProfilePortion.setSpecificFormat(GuiConstants.DATE_FORMATTER);
        profile.addPortion(parsingProfilePortion);

        ParsingProfilePortion portion = new ParsingProfilePortion(SeparatorEnum.COLON.getName(), SeparatorEnum.COLON.getSymbol(), false, true);
        profile.addPortion(portion);

        // Process
        simulateWriteAndRead(function, consumer, profile);

        // validations
        profiles = consumer.getProfiles();
        assertEquals(1, profiles.length);
        ParsingProfile parsedProfile = profiles[0];
        assertEquals(profile.getName(), parsedProfile.getName());
        ArrayList<ParsingProfilePortion> portions = parsedProfile.getPortions();
        assertEquals(2, portions.size());
        validatePortion(portions.get(0), false, true, false, TextClassesEnum.DATE.getName());
        assertEquals(ParsingProfilePortion.NO_SPECIFIC_FORMAT, portions.get(0).getSpecificFormat());
        validatePortion(portions.get(1), true, false, false, portion.getPortionRepresentation());
    }

}
