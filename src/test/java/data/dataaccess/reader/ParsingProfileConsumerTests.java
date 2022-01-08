package data.dataaccess.reader;

import data.dataaccess.common.ParsingProfileReadWriteConstants;
import domain.entities.common.ParsingProfilePortion;
import domain.entities.common.SeparatorEnum;
import domain.entities.common.TextClassesEnum;
import domain.entities.domainobjects.ParsingProfile;
import org.junit.Test;
import presentation.common.GuiConstants;

import java.util.ArrayList;

import static data.dataaccess.common.ParsingProfileReadWriteConstants.IGNORE_TOKEN;
import static data.dataaccess.common.ParsingProfileReadWriteConstants.KEEP_SPECIFIC_TOKEN;
import static data.dataaccess.common.ParsingProfileReadWriteConstants.KEEP_TOKEN;
import static data.dataaccess.common.ParsingProfileReadWriteConstants.NO_NAME_PROVIDED;
import static data.dataaccess.common.ParsingProfileReadWriteConstants.PORTION_SEPARATOR;
import static org.junit.Assert.assertEquals;

public class ParsingProfileConsumerTests {

    public static final String VALUE = "Value";
    private static final String GARBAGE = "assfsadaskh bdakbdkajsbdv";
    private final String EXPECTED_FIRST_LINE = "Parsing Profile";

    @Test
    public void simpleTest() {
        ParsingProfileConsumer consumer = getConsumer();
        ParsingProfile[] profiles;

        // setup
        consumer.accept(EXPECTED_FIRST_LINE);
        consumer.accept(ParsingProfileReadWriteConstants.NAME_TOKEN + PORTION_SEPARATOR + VALUE);
        consumer.accept(ParsingProfileReadWriteConstants.START_PROFILE_TOKEN);
        consumer.accept(TextClassesEnum.LEVEL + PORTION_SEPARATOR + KEEP_TOKEN);
        consumer.accept(ParsingProfileReadWriteConstants.END_PROFILE_TOKEN);

        // validations
        profiles = consumer.getProfiles();
        assertEquals(1, profiles.length);
        ParsingProfile profile = profiles[0];
        assertEquals(VALUE, profile.getName());
        ArrayList<ParsingProfilePortion> portions = profile.getPortions();
        assertEquals(1, portions.size());
        validatePortion(portions.get(0), false, false, false, TextClassesEnum.LEVEL.getName());
    }

    @Test
    public void simpleTestTwoProfiles() {
        ParsingProfileConsumer consumer = getConsumer();
        ParsingProfile[] profiles;

        // setup
        // first profile
        consumer.accept(EXPECTED_FIRST_LINE);
        consumer.accept(ParsingProfileReadWriteConstants.NAME_TOKEN + PORTION_SEPARATOR + VALUE + 1);
        consumer.accept(ParsingProfileReadWriteConstants.START_PROFILE_TOKEN);
        consumer.accept(TextClassesEnum.LEVEL + PORTION_SEPARATOR + KEEP_TOKEN);
        consumer.accept(ParsingProfileReadWriteConstants.END_PROFILE_TOKEN);
        // second profile
        consumer.accept(ParsingProfileReadWriteConstants.NAME_TOKEN + PORTION_SEPARATOR + VALUE + 2);
        consumer.accept(ParsingProfileReadWriteConstants.START_PROFILE_TOKEN);
        consumer.accept(TextClassesEnum.LEVEL + PORTION_SEPARATOR + KEEP_TOKEN);
        consumer.accept(ParsingProfileReadWriteConstants.END_PROFILE_TOKEN);

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
        ParsingProfileConsumer consumer = getConsumer();
        ParsingProfile[] profiles;

        // setup
        consumer.accept(EXPECTED_FIRST_LINE);
        consumer.accept(ParsingProfileReadWriteConstants.NAME_TOKEN + PORTION_SEPARATOR + VALUE);
        consumer.accept(ParsingProfileReadWriteConstants.START_PROFILE_TOKEN);
        consumer.accept(TextClassesEnum.LEVEL + PORTION_SEPARATOR + KEEP_TOKEN);
        consumer.accept(SeparatorEnum.COLON.getParsingString());
        consumer.accept(TextClassesEnum.TIME + PORTION_SEPARATOR + IGNORE_TOKEN);
        consumer.accept(SeparatorEnum.HIFEN.getParsingString());
        consumer.accept(ParsingProfileReadWriteConstants.END_PROFILE_TOKEN);

        // validations
        profiles = consumer.getProfiles();
        assertEquals(1, profiles.length);
        ParsingProfile profile = profiles[0];
        assertEquals(VALUE, profile.getName());
        ArrayList<ParsingProfilePortion> portions = profile.getPortions();
        assertEquals(4, portions.size());
        validatePortion(portions.get(0), false, false, false, TextClassesEnum.LEVEL.getName());
        validatePortion(portions.get(1), true, false, false, SeparatorEnum.COLON.getName());
        validatePortion(portions.get(2), false, true, false, TextClassesEnum.TIME.getName());
        validatePortion(portions.get(3), true, false, false, SeparatorEnum.HIFEN.getName());
    }


    @Test
    public void testWithSeparatorsWithGarbageInFront() {
        ParsingProfileConsumer consumer = getConsumer();
        ParsingProfile[] profiles;

        // setup
        consumer.accept(EXPECTED_FIRST_LINE);
        consumer.accept(ParsingProfileReadWriteConstants.NAME_TOKEN + PORTION_SEPARATOR + VALUE);
        consumer.accept(ParsingProfileReadWriteConstants.START_PROFILE_TOKEN);
        consumer.accept(TextClassesEnum.LEVEL + PORTION_SEPARATOR + KEEP_TOKEN);
        consumer.accept(SeparatorEnum.COLON.getParsingString() + GARBAGE);
        consumer.accept(TextClassesEnum.TIME + PORTION_SEPARATOR + IGNORE_TOKEN);
        consumer.accept(SeparatorEnum.HIFEN.getParsingString() + GARBAGE);
        consumer.accept(ParsingProfileReadWriteConstants.END_PROFILE_TOKEN);

        // validations
        profiles = consumer.getProfiles();
        assertEquals(1, profiles.length);
        ParsingProfile profile = profiles[0];
        assertEquals(VALUE, profile.getName());
        ArrayList<ParsingProfilePortion> portions = profile.getPortions();
        assertEquals(4, portions.size());
        validatePortion(portions.get(0), false, false, false, TextClassesEnum.LEVEL.getName());
        validatePortion(portions.get(1), true, false, false, SeparatorEnum.COLON.getName());
        validatePortion(portions.get(2), false, true, false, TextClassesEnum.TIME.getName());
        validatePortion(portions.get(3), true, false, false, SeparatorEnum.HIFEN.getName());
    }


    @Test
    public void testWithSpecificFormat() {
        ParsingProfileConsumer consumer = getConsumer();
        ParsingProfile[] profiles;

        // setup
        consumer.accept(EXPECTED_FIRST_LINE);
        consumer.accept(ParsingProfileReadWriteConstants.NAME_TOKEN + PORTION_SEPARATOR + VALUE);
        consumer.accept(ParsingProfileReadWriteConstants.START_PROFILE_TOKEN);
        consumer.accept(TextClassesEnum.DATE + PORTION_SEPARATOR + KEEP_SPECIFIC_TOKEN + PORTION_SEPARATOR + GuiConstants.DATE_FORMATTER);
        consumer.accept(SeparatorEnum.COLON.getParsingString() + GARBAGE);
        consumer.accept(TextClassesEnum.TIME + PORTION_SEPARATOR + KEEP_SPECIFIC_TOKEN + PORTION_SEPARATOR + GuiConstants.DATE_TIME_FORMATTER);
        consumer.accept(SeparatorEnum.HIFEN.getParsingString() + GARBAGE);
        consumer.accept(ParsingProfileReadWriteConstants.END_PROFILE_TOKEN);

        // validations
        profiles = consumer.getProfiles();
        assertEquals(1, profiles.length);
        ParsingProfile profile = profiles[0];
        assertEquals(VALUE, profile.getName());
        ArrayList<ParsingProfilePortion> portions = profile.getPortions();
        assertEquals(4, portions.size());
        validatePortion(portions.get(0), false, false, true, TextClassesEnum.DATE.getName() + " " + portions.get(0).getSpecificFormat());
        assertEquals(GuiConstants.DATE_FORMATTER, portions.get(0).getSpecificFormat());
        validatePortion(portions.get(1), true, false, false, SeparatorEnum.COLON.getName());
        validatePortion(portions.get(2), false, false, true, TextClassesEnum.TIME.getName() + " " + portions.get(2).getSpecificFormat());
        assertEquals(GuiConstants.DATE_TIME_FORMATTER, portions.get(2).getSpecificFormat());
        validatePortion(portions.get(3), true, false, false, SeparatorEnum.HIFEN.getName());
    }

    @Test
    public void testJustSeparators() {
        ParsingProfileConsumer consumer = getConsumer();
        ParsingProfile[] profiles;

        // setup
        consumer.accept(EXPECTED_FIRST_LINE);
        consumer.accept(ParsingProfileReadWriteConstants.NAME_TOKEN + PORTION_SEPARATOR + VALUE);
        consumer.accept(ParsingProfileReadWriteConstants.START_PROFILE_TOKEN);
        consumer.accept(SeparatorEnum.COLON.getParsingString());
        consumer.accept(SeparatorEnum.HIFEN.getParsingString());
        consumer.accept(ParsingProfileReadWriteConstants.END_PROFILE_TOKEN);

        // validations
        profiles = consumer.getProfiles();
        assertEquals(1, profiles.length);
        ParsingProfile profile = profiles[0];
        assertEquals(VALUE, profile.getName());
        ArrayList<ParsingProfilePortion> portions = profile.getPortions();
        assertEquals(2, portions.size());
        validatePortion(portions.get(0), true, false, false, SeparatorEnum.COLON.getName());
        validatePortion(portions.get(1), true, false, false, SeparatorEnum.HIFEN.getName());
    }

    @Test
    public void testJustTextClasses() {
        ParsingProfileConsumer consumer = getConsumer();
        ParsingProfile[] profiles;

        // setup
        consumer.accept(EXPECTED_FIRST_LINE);
        consumer.accept(ParsingProfileReadWriteConstants.NAME_TOKEN + PORTION_SEPARATOR + VALUE);
        consumer.accept(ParsingProfileReadWriteConstants.START_PROFILE_TOKEN);
        consumer.accept(TextClassesEnum.LEVEL + PORTION_SEPARATOR + KEEP_TOKEN);
        consumer.accept(TextClassesEnum.TIME + PORTION_SEPARATOR + IGNORE_TOKEN);
        consumer.accept(ParsingProfileReadWriteConstants.END_PROFILE_TOKEN);

        // validations
        profiles = consumer.getProfiles();
        assertEquals(1, profiles.length);
        ParsingProfile profile = profiles[0];
        assertEquals(VALUE, profile.getName());
        ArrayList<ParsingProfilePortion> portions = profile.getPortions();
        assertEquals(2, portions.size());
        validatePortion(portions.get(0), false, false, false, TextClassesEnum.LEVEL.getName());
        validatePortion(portions.get(1), false, true, false, TextClassesEnum.TIME.getName());
    }

    @Test
    public void testJustHeader() {
        ParsingProfileConsumer consumer = getConsumer();
        ParsingProfile[] profiles;

        // setup
        consumer.accept(EXPECTED_FIRST_LINE);
        consumer.accept(ParsingProfileReadWriteConstants.NAME_TOKEN + PORTION_SEPARATOR + VALUE);

        // validations
        profiles = consumer.getProfiles();
        assertEquals(1, profiles.length);
        ParsingProfile profile = profiles[0];
        assertEquals(VALUE, profile.getName());
        ArrayList<ParsingProfilePortion> portions = profile.getPortions();
        assertEquals(0, portions.size());
    }

    @Test
    public void testNoName() {
        // We will still get a profile, but with no contents
        ParsingProfileConsumer consumer = getConsumer();
        ParsingProfile[] profiles;

        // setup
        consumer.accept(EXPECTED_FIRST_LINE);

        // validations
        profiles = consumer.getProfiles();
        assertEquals(0, profiles.length);
    }

    @Test
    public void testInvalidName() {
        // if the invalid name is supplied, a default name is given
        ParsingProfileConsumer consumer = getConsumer();
        ParsingProfile[] profiles;

        // setup
        consumer.accept(EXPECTED_FIRST_LINE);
        consumer.accept(ParsingProfileReadWriteConstants.NAME_TOKEN + PORTION_SEPARATOR);

        // validations
        profiles = consumer.getProfiles();
        assertEquals(1, profiles.length);
        ParsingProfile profile = profiles[0];
        assertEquals(NO_NAME_PROVIDED, profile.getName());
        ArrayList<ParsingProfilePortion> portions = profile.getPortions();
        assertEquals(0, portions.size());
    }

    @Test
    public void testNoHeader() {
        // if there is no header, there is no way to know what type of file it is, so we ignore it
        ParsingProfileConsumer consumer = getConsumer();
        ParsingProfile[] profiles;

        // setup
        consumer.accept(ParsingProfileReadWriteConstants.NAME_TOKEN + PORTION_SEPARATOR + VALUE);
        consumer.accept(ParsingProfileReadWriteConstants.START_PROFILE_TOKEN);
        consumer.accept(TextClassesEnum.LEVEL + PORTION_SEPARATOR + KEEP_TOKEN);
        consumer.accept(TextClassesEnum.TIME + PORTION_SEPARATOR + IGNORE_TOKEN);
        consumer.accept(ParsingProfileReadWriteConstants.END_PROFILE_TOKEN);

        // validations
        profiles = consumer.getProfiles();
        assertEquals(0, profiles.length);
    }


    @Test
    public void testNoStartProfile() {
        // if there is no start profile, there is no way to know if the profile started so we do not process it
        ParsingProfileConsumer consumer = getConsumer();
        ParsingProfile[] profiles;

        // setup
        consumer.accept(EXPECTED_FIRST_LINE);
        consumer.accept(ParsingProfileReadWriteConstants.NAME_TOKEN + PORTION_SEPARATOR + VALUE);
        consumer.accept(TextClassesEnum.LEVEL + PORTION_SEPARATOR + KEEP_TOKEN);
        consumer.accept(TextClassesEnum.TIME + PORTION_SEPARATOR + IGNORE_TOKEN);
        consumer.accept(ParsingProfileReadWriteConstants.END_PROFILE_TOKEN);

        // validations
        profiles = consumer.getProfiles();
        assertEquals(1, profiles.length);
        ParsingProfile profile = profiles[0];
        assertEquals(VALUE, profile.getName());
        ArrayList<ParsingProfilePortion> portions = profile.getPortions();
        assertEquals(0, portions.size());
    }


    @Test
    public void testNoEndProfile() {
        // if there is no end profile, there is no way to know if the profile ended so we continue to process the file as
        // if there was no break, so two profiles are aggregated into one
        ParsingProfileConsumer consumer = getConsumer();
        ParsingProfile[] profiles;

        // setup
        // first profile
        consumer.accept(EXPECTED_FIRST_LINE);
        consumer.accept(ParsingProfileReadWriteConstants.NAME_TOKEN + PORTION_SEPARATOR + VALUE + 1);
        consumer.accept(ParsingProfileReadWriteConstants.START_PROFILE_TOKEN);
        consumer.accept(TextClassesEnum.LEVEL + PORTION_SEPARATOR + KEEP_TOKEN);
        // second profile
        consumer.accept(ParsingProfileReadWriteConstants.NAME_TOKEN + PORTION_SEPARATOR + VALUE + 2);
        consumer.accept(ParsingProfileReadWriteConstants.START_PROFILE_TOKEN);
        consumer.accept(TextClassesEnum.LEVEL + PORTION_SEPARATOR + KEEP_TOKEN);
        consumer.accept(ParsingProfileReadWriteConstants.END_PROFILE_TOKEN);

        // validations
        profiles = consumer.getProfiles();
        assertEquals(1, profiles.length);

        // profiles
        ParsingProfile profile = profiles[0];
        assertEquals(VALUE + 1, profile.getName());
        ArrayList<ParsingProfilePortion> portions = profile.getPortions();
        assertEquals(2, portions.size());
        validatePortion(portions.get(0), false, false, false, TextClassesEnum.LEVEL.getName());
    }


    @Test
    public void testNoEndProfileMultipleEntries() {
        // if there is no end profile, there is no way to know if the profile ended so we continue to process the file as
        // if there was no break, so two profiles are aggregated into one additionally, if there were more profiles it would go on
        ParsingProfileConsumer consumer = getConsumer();
        ParsingProfile[] profiles;

        // setup
        // first profile
        consumer.accept(EXPECTED_FIRST_LINE);
        consumer.accept(ParsingProfileReadWriteConstants.NAME_TOKEN + PORTION_SEPARATOR + VALUE + 1);
        consumer.accept(ParsingProfileReadWriteConstants.START_PROFILE_TOKEN);
        consumer.accept(TextClassesEnum.LEVEL + PORTION_SEPARATOR + KEEP_TOKEN);
        // second profile
        consumer.accept(ParsingProfileReadWriteConstants.NAME_TOKEN + PORTION_SEPARATOR + VALUE + 2);
        consumer.accept(ParsingProfileReadWriteConstants.START_PROFILE_TOKEN);
        consumer.accept(TextClassesEnum.LEVEL + PORTION_SEPARATOR + KEEP_TOKEN);

        // validations
        profiles = consumer.getProfiles();
        assertEquals(1, profiles.length);

        // profiles
        ParsingProfile profile = profiles[0];
        assertEquals(VALUE + 1, profile.getName());
        ArrayList<ParsingProfilePortion> portions = profile.getPortions();
        assertEquals(2, portions.size());
        validatePortion(portions.get(0), false, false, false, TextClassesEnum.LEVEL.getName());
    }


    @Test
    public void testSpecificFormatWithNoFormat() {
        // if there is no specific profile there the line is ignored
        ParsingProfileConsumer consumer = getConsumer();
        ParsingProfile[] profiles;

        // setup
        consumer.accept(EXPECTED_FIRST_LINE);
        consumer.accept(ParsingProfileReadWriteConstants.NAME_TOKEN + PORTION_SEPARATOR + VALUE);
        consumer.accept(ParsingProfileReadWriteConstants.START_PROFILE_TOKEN);
        consumer.accept(TextClassesEnum.DATE + PORTION_SEPARATOR + KEEP_SPECIFIC_TOKEN + PORTION_SEPARATOR);
        consumer.accept(SeparatorEnum.COLON.getParsingString() + GARBAGE);
        consumer.accept(TextClassesEnum.TIME + PORTION_SEPARATOR + KEEP_SPECIFIC_TOKEN + PORTION_SEPARATOR);
        consumer.accept(SeparatorEnum.HIFEN.getParsingString() + GARBAGE);
        consumer.accept(ParsingProfileReadWriteConstants.END_PROFILE_TOKEN);

        // validations
        profiles = consumer.getProfiles();
        assertEquals(1, profiles.length);
        ParsingProfile profile = profiles[0];
        assertEquals(VALUE, profile.getName());
        ArrayList<ParsingProfilePortion> portions = profile.getPortions();
        assertEquals(2, portions.size());
        validatePortion(portions.get(0), true, false, false, SeparatorEnum.COLON.getName());
        validatePortion(portions.get(1), true, false, false, SeparatorEnum.HIFEN.getName());
    }


    @Test
    public void testTextClassWithNoModifier() {
        // if no modifiers are given, the Text Classes are ignored
        ParsingProfileConsumer consumer = getConsumer();
        ParsingProfile[] profiles;

        // setup
        consumer.accept(EXPECTED_FIRST_LINE);
        consumer.accept(ParsingProfileReadWriteConstants.NAME_TOKEN + PORTION_SEPARATOR + VALUE);
        consumer.accept(ParsingProfileReadWriteConstants.START_PROFILE_TOKEN);
        consumer.accept(TextClassesEnum.LEVEL + PORTION_SEPARATOR);
        consumer.accept(TextClassesEnum.TIME + PORTION_SEPARATOR);
        consumer.accept(ParsingProfileReadWriteConstants.END_PROFILE_TOKEN);

        // validations
        profiles = consumer.getProfiles();
        assertEquals(1, profiles.length);
        ParsingProfile profile = profiles[0];
        assertEquals(VALUE, profile.getName());
        ArrayList<ParsingProfilePortion> portions = profile.getPortions();
        assertEquals(0, portions.size());
    }


    @Test
    public void testNonExistingTextClass() {
        // if the class does not exist it is ignored
        ParsingProfileConsumer consumer = getConsumer();
        ParsingProfile[] profiles;

        // setup
        consumer.accept(EXPECTED_FIRST_LINE);
        consumer.accept(ParsingProfileReadWriteConstants.NAME_TOKEN + PORTION_SEPARATOR + VALUE);
        consumer.accept(ParsingProfileReadWriteConstants.START_PROFILE_TOKEN);
        consumer.accept("NON_EXITING_CLASS" + PORTION_SEPARATOR);
        consumer.accept("NON_EXITING_CLASS_2" + PORTION_SEPARATOR);
        consumer.accept(ParsingProfileReadWriteConstants.END_PROFILE_TOKEN);

        // validations
        profiles = consumer.getProfiles();
        assertEquals(1, profiles.length);
        ParsingProfile profile = profiles[0];
        assertEquals(VALUE, profile.getName());
        ArrayList<ParsingProfilePortion> portions = profile.getPortions();
        assertEquals(0, portions.size());
    }


    @Test
    public void testNonExistingSeparator() {
        ParsingProfileConsumer consumer = getConsumer();
        ParsingProfile[] profiles;

        // setup
        consumer.accept(EXPECTED_FIRST_LINE);
        consumer.accept(ParsingProfileReadWriteConstants.NAME_TOKEN + PORTION_SEPARATOR + VALUE);
        consumer.accept(ParsingProfileReadWriteConstants.START_PROFILE_TOKEN);
        consumer.accept("NON_EXITING_SEPARATOR");
        consumer.accept("NON_EXITING_SEPARATOR_2");
        consumer.accept(ParsingProfileReadWriteConstants.END_PROFILE_TOKEN);

        // validations
        profiles = consumer.getProfiles();
        assertEquals(1, profiles.length);
        ParsingProfile profile = profiles[0];
        assertEquals(VALUE, profile.getName());
        ArrayList<ParsingProfilePortion> portions = profile.getPortions();
        assertEquals(0, portions.size());
    }

    private ParsingProfileConsumer getConsumer() {
        ParsingProfileConsumer consumer = new ParsingProfileConsumer();
        // baseline
        ParsingProfile[] profiles = consumer.getProfiles();
        assertEquals(0, profiles.length);
        return consumer;
    }


    private void validatePortion(ParsingProfilePortion portion, boolean isSeparator, boolean isIgnore, boolean isSpecificFormat, String name) {
        assertEquals(isSeparator, portion.isSeparator());
        assertEquals(isIgnore, portion.isIgnore());
        assertEquals(isSpecificFormat, portion.isSpecificFormat());
        assertEquals(name, portion.getPortionRepresentation());
    }
}
