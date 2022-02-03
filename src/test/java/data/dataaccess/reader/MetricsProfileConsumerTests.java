package data.dataaccess.reader;

import data.dataaccess.MetricsProfilesCommon;
import domain.entities.common.Keyword;
import domain.entities.domainobjects.MetricsProfile;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;

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
import static domain.entities.common.ThresholdTypeEnum.EQUAL_TO;
import static domain.entities.common.ThresholdTypeEnum.NOT_APPLICABLE;
import static domain.entities.common.ThresholdUnitEnum.NONE;
import static domain.entities.common.ThresholdUnitEnum.OCCURRENCES;
import static org.junit.Assert.assertEquals;

public class MetricsProfileConsumerTests extends MetricsProfilesCommon {

    @Test
    public void simpleTest() {
        MetricsProfileConsumer consumer = getConsumer();
        MetricsProfile[] profiles;

        // setup
        consumer.accept(EXPECTED_FIRST_LINE);
        consumer.accept(NAME_TOKEN + SEPARATOR + VALUE);
        consumer.accept(START_PROFILE_TOKEN);
        consumer.accept(MOST_COMMON_WORDS_TOKEN + SEPARATOR + true);
        consumer.accept(FILE_SIZE_TOKEN + SEPARATOR + false);
        consumer.accept(KEYWORD_HISTOGRAM_TOKEN + SEPARATOR + true);
        consumer.accept(KEYWORD_OVER_TIME_TOKEN + SEPARATOR + false);
        consumer.accept(KEYWORD_THRESHOLD_TOKEN + SEPARATOR + true);
        consumer.accept(START_KEYWORDS_TOKEN);
        consumer.accept("Kws1" + SEPARATOR + false + SEPARATOR + NOT_APPLICABLE + SEPARATOR + 0 + SEPARATOR + NONE);
        consumer.accept("Kws2" + SEPARATOR + true + SEPARATOR + NOT_APPLICABLE + SEPARATOR + 0 + SEPARATOR + NONE);
        consumer.accept("Kws3" + SEPARATOR + true + SEPARATOR + EQUAL_TO + SEPARATOR + 1 + SEPARATOR + OCCURRENCES);
        consumer.accept(END_KEYWORDS_TOKEN);
        consumer.accept(END_PROFILE_TOKEN);

        // validations
        profiles = consumer.getProfiles();
        assertEquals(1, profiles.length);
        MetricsProfile profile = profiles[0];
        assertEquals(VALUE, profile.getName());
        validateEnabledProfiles(profile, true, false, true, false, true);
        ArrayList<Keyword> keywords = profile.getKeywords();
        assertEquals(3, keywords.size());
        validateKeyword(keywords.get(0), "Kws1", false, NOT_APPLICABLE, new BigDecimal(0), NONE);
        validateKeyword(keywords.get(1), "Kws2", true, NOT_APPLICABLE, new BigDecimal(0), NONE);
        validateKeyword(keywords.get(2), "Kws3", true, EQUAL_TO, new BigDecimal(1), OCCURRENCES);
    }


    @Test
    public void simpleTestWithTwoProfiles() {
        MetricsProfileConsumer consumer = getConsumer();
        MetricsProfile[] profiles;

        // setup
        consumer.accept(EXPECTED_FIRST_LINE);
        consumer.accept(NAME_TOKEN + SEPARATOR + VALUE);
        consumer.accept(START_PROFILE_TOKEN);
        consumer.accept(MOST_COMMON_WORDS_TOKEN + SEPARATOR + false);
        consumer.accept(FILE_SIZE_TOKEN + SEPARATOR + false);
        consumer.accept(KEYWORD_HISTOGRAM_TOKEN + SEPARATOR + false);
        consumer.accept(KEYWORD_OVER_TIME_TOKEN + SEPARATOR + false);
        consumer.accept(KEYWORD_THRESHOLD_TOKEN + SEPARATOR + false);
        consumer.accept(START_KEYWORDS_TOKEN);
        consumer.accept("Kws1" + SEPARATOR + false + SEPARATOR + NOT_APPLICABLE + SEPARATOR + 0 + SEPARATOR + NONE);
        consumer.accept("Kws2" + SEPARATOR + false + SEPARATOR + NOT_APPLICABLE + SEPARATOR + 0 + SEPARATOR + NONE);
        consumer.accept("Kws3" + SEPARATOR + false + SEPARATOR + NOT_APPLICABLE + SEPARATOR + 0 + SEPARATOR + NONE);
        consumer.accept(END_KEYWORDS_TOKEN);
        consumer.accept(END_PROFILE_TOKEN);
        consumer.accept(NAME_TOKEN + SEPARATOR + VALUE);
        consumer.accept(START_PROFILE_TOKEN);
        consumer.accept(MOST_COMMON_WORDS_TOKEN + SEPARATOR + true);
        consumer.accept(FILE_SIZE_TOKEN + SEPARATOR + true);
        consumer.accept(KEYWORD_HISTOGRAM_TOKEN + SEPARATOR + true);
        consumer.accept(KEYWORD_OVER_TIME_TOKEN + SEPARATOR + true);
        consumer.accept(KEYWORD_THRESHOLD_TOKEN + SEPARATOR + true);
        consumer.accept(START_KEYWORDS_TOKEN);
        consumer.accept("Kws1" + SEPARATOR + true + SEPARATOR + EQUAL_TO + SEPARATOR + 1 + SEPARATOR + OCCURRENCES);
        consumer.accept("Kws2" + SEPARATOR + true + SEPARATOR + EQUAL_TO + SEPARATOR + 1 + SEPARATOR + OCCURRENCES);
        consumer.accept("Kws3" + SEPARATOR + true + SEPARATOR + EQUAL_TO + SEPARATOR + 1 + SEPARATOR + OCCURRENCES);
        consumer.accept(END_KEYWORDS_TOKEN);
        consumer.accept(END_PROFILE_TOKEN);

        // validations
        profiles = consumer.getProfiles();
        assertEquals(2, profiles.length);
        MetricsProfile profile = profiles[0];
        assertEquals(VALUE, profile.getName());
        validateEnabledProfiles(profile, false, false, false, false, false);
        ArrayList<Keyword> keywords = profile.getKeywords();
        assertEquals(3, keywords.size());
        validateKeyword(keywords.get(0), "Kws1", false, NOT_APPLICABLE, new BigDecimal(0), NONE);
        validateKeyword(keywords.get(1), "Kws2", false, NOT_APPLICABLE, new BigDecimal(0), NONE);
        validateKeyword(keywords.get(2), "Kws3", false, NOT_APPLICABLE, new BigDecimal(0), NONE);

        profile = profiles[1];
        assertEquals(VALUE, profile.getName());
        validateEnabledProfiles(profile, true, true, true, true, true);
        keywords = profile.getKeywords();
        assertEquals(3, keywords.size());
        validateKeyword(keywords.get(0), "Kws1", true, EQUAL_TO, new BigDecimal(1), OCCURRENCES);
        validateKeyword(keywords.get(1), "Kws2", true, EQUAL_TO, new BigDecimal(1), OCCURRENCES);
        validateKeyword(keywords.get(2), "Kws3", true, EQUAL_TO, new BigDecimal(1), OCCURRENCES);
    }

    @Test
    public void simpleTestNoKeywords() {
        MetricsProfileConsumer consumer = getConsumer();
        MetricsProfile[] profiles;

        // setup
        consumer.accept(EXPECTED_FIRST_LINE);
        consumer.accept(NAME_TOKEN + SEPARATOR + VALUE);
        consumer.accept(START_PROFILE_TOKEN);
        consumer.accept(MOST_COMMON_WORDS_TOKEN + SEPARATOR + true);
        consumer.accept(FILE_SIZE_TOKEN + SEPARATOR + false);
        consumer.accept(KEYWORD_HISTOGRAM_TOKEN + SEPARATOR + true);
        consumer.accept(KEYWORD_OVER_TIME_TOKEN + SEPARATOR + false);
        consumer.accept(KEYWORD_THRESHOLD_TOKEN + SEPARATOR + true);
        consumer.accept(START_KEYWORDS_TOKEN);
        consumer.accept(END_KEYWORDS_TOKEN);
        consumer.accept(END_PROFILE_TOKEN);

        // validations
        profiles = consumer.getProfiles();
        assertEquals(1, profiles.length);
        MetricsProfile profile = profiles[0];
        assertEquals(VALUE, profile.getName());
        validateEnabledProfiles(profile, true, false, true, false, true);
        ArrayList<Keyword> keywords = profile.getKeywords();
        assertEquals(0, keywords.size());
    }

    @Test
    public void simpleTestNoKeywordsNoMarkers() {
        MetricsProfileConsumer consumer = getConsumer();
        MetricsProfile[] profiles;

        // setup
        consumer.accept(EXPECTED_FIRST_LINE);
        consumer.accept(NAME_TOKEN + SEPARATOR + VALUE);
        consumer.accept(START_PROFILE_TOKEN);
        consumer.accept(MOST_COMMON_WORDS_TOKEN + SEPARATOR + true);
        consumer.accept(FILE_SIZE_TOKEN + SEPARATOR + false);
        consumer.accept(KEYWORD_HISTOGRAM_TOKEN + SEPARATOR + true);
        consumer.accept(KEYWORD_OVER_TIME_TOKEN + SEPARATOR + false);
        consumer.accept(KEYWORD_THRESHOLD_TOKEN + SEPARATOR + true);
        consumer.accept(END_PROFILE_TOKEN);

        // validations
        profiles = consumer.getProfiles();
        assertEquals(1, profiles.length);
        MetricsProfile profile = profiles[0];
        assertEquals(VALUE, profile.getName());
        validateEnabledProfiles(profile, true, false, true, false, true);
        ArrayList<Keyword> keywords = profile.getKeywords();
        assertEquals(0, keywords.size());
    }

    @Test
    public void testJustHeader() {
        MetricsProfileConsumer consumer = getConsumer();
        MetricsProfile[] profiles;

        // setup
        consumer.accept(EXPECTED_FIRST_LINE);
        consumer.accept(NAME_TOKEN + SEPARATOR + VALUE);

        // validations
        profiles = consumer.getProfiles();
        assertEquals(1, profiles.length);
        MetricsProfile profile = profiles[0];
        assertEquals(VALUE, profile.getName());
        validateEnabledProfiles(profile, false, false, false, false, false);
        ArrayList<Keyword> keywords = profile.getKeywords();
        assertEquals(0, keywords.size());
    }


    @Test
    public void testJustHeaderNoName() {
        MetricsProfileConsumer consumer = getConsumer();
        MetricsProfile[] profiles;

        // setup
        consumer.accept(EXPECTED_FIRST_LINE);

        // validations
        profiles = consumer.getProfiles();
        assertEquals(0, profiles.length);
    }

    @Test
    public void testInvalidName() {
        MetricsProfileConsumer consumer = getConsumer();
        MetricsProfile[] profiles;

        // setup
        consumer.accept(EXPECTED_FIRST_LINE);
        consumer.accept(NAME_TOKEN + SEPARATOR);
        consumer.accept(START_PROFILE_TOKEN);
        consumer.accept(MOST_COMMON_WORDS_TOKEN + SEPARATOR + true);
        consumer.accept(FILE_SIZE_TOKEN + SEPARATOR + false);
        consumer.accept(KEYWORD_HISTOGRAM_TOKEN + SEPARATOR + true);
        consumer.accept(KEYWORD_OVER_TIME_TOKEN + SEPARATOR + false);
        consumer.accept(KEYWORD_THRESHOLD_TOKEN + SEPARATOR + true);
        consumer.accept(START_KEYWORDS_TOKEN);
        consumer.accept("Kws1" + SEPARATOR + false + SEPARATOR + NOT_APPLICABLE + SEPARATOR + 0 + SEPARATOR + NONE);
        consumer.accept("Kws2" + SEPARATOR + true + SEPARATOR + NOT_APPLICABLE + SEPARATOR + 0 + SEPARATOR + NONE);
        consumer.accept("Kws3" + SEPARATOR + true + SEPARATOR + EQUAL_TO + SEPARATOR + 1 + SEPARATOR + OCCURRENCES);
        consumer.accept(END_KEYWORDS_TOKEN);
        consumer.accept(END_PROFILE_TOKEN);

        // validations
        profiles = consumer.getProfiles();
        assertEquals(1, profiles.length);
        MetricsProfile profile = profiles[0];
        assertEquals(NO_NAME_PROVIDED, profile.getName());
        validateEnabledProfiles(profile, true, false, true, false, true);
        ArrayList<Keyword> keywords = profile.getKeywords();
        assertEquals(3, keywords.size());
        validateKeyword(keywords.get(0), "Kws1", false, NOT_APPLICABLE, new BigDecimal(0), NONE);
        validateKeyword(keywords.get(1), "Kws2", true, NOT_APPLICABLE, new BigDecimal(0), NONE);
        validateKeyword(keywords.get(2), "Kws3", true, EQUAL_TO, new BigDecimal(1), OCCURRENCES);
    }

    @Test
    public void testNoHeader() {
        MetricsProfileConsumer consumer = getConsumer();
        MetricsProfile[] profiles;

        // setup
        consumer.accept(START_PROFILE_TOKEN);
        consumer.accept(MOST_COMMON_WORDS_TOKEN + SEPARATOR + true);
        consumer.accept(FILE_SIZE_TOKEN + SEPARATOR + false);
        consumer.accept(KEYWORD_HISTOGRAM_TOKEN + SEPARATOR + true);
        consumer.accept(KEYWORD_OVER_TIME_TOKEN + SEPARATOR + false);
        consumer.accept(KEYWORD_THRESHOLD_TOKEN + SEPARATOR + true);
        consumer.accept(START_KEYWORDS_TOKEN);
        consumer.accept("Kws1" + SEPARATOR + false + SEPARATOR + NOT_APPLICABLE + SEPARATOR + 0 + SEPARATOR + NONE);
        consumer.accept("Kws2" + SEPARATOR + true + SEPARATOR + NOT_APPLICABLE + SEPARATOR + 0 + SEPARATOR + NONE);
        consumer.accept("Kws3" + SEPARATOR + true + SEPARATOR + EQUAL_TO + SEPARATOR + 1 + SEPARATOR + OCCURRENCES);
        consumer.accept(END_KEYWORDS_TOKEN);
        consumer.accept(END_PROFILE_TOKEN);

        // validations
        profiles = consumer.getProfiles();
        assertEquals(0, profiles.length);
    }

    @Test
    public void testNoStartProfile() {
        MetricsProfileConsumer consumer = getConsumer();
        MetricsProfile[] profiles;

        // setup
        consumer.accept(EXPECTED_FIRST_LINE);
        consumer.accept(NAME_TOKEN + SEPARATOR + VALUE);
        consumer.accept(MOST_COMMON_WORDS_TOKEN + SEPARATOR + true);
        consumer.accept(FILE_SIZE_TOKEN + SEPARATOR + false);
        consumer.accept(KEYWORD_HISTOGRAM_TOKEN + SEPARATOR + true);
        consumer.accept(KEYWORD_OVER_TIME_TOKEN + SEPARATOR + false);
        consumer.accept(KEYWORD_THRESHOLD_TOKEN + SEPARATOR + true);
        consumer.accept(START_KEYWORDS_TOKEN);
        consumer.accept("Kws1" + SEPARATOR + false + SEPARATOR + NOT_APPLICABLE + SEPARATOR + 0 + SEPARATOR + NONE);
        consumer.accept("Kws2" + SEPARATOR + true + SEPARATOR + NOT_APPLICABLE + SEPARATOR + 0 + SEPARATOR + NONE);
        consumer.accept("Kws3" + SEPARATOR + true + SEPARATOR + EQUAL_TO + SEPARATOR + 1 + SEPARATOR + OCCURRENCES);
        consumer.accept(END_KEYWORDS_TOKEN);
        consumer.accept(END_PROFILE_TOKEN);

        // validations
        profiles = consumer.getProfiles();
        assertEquals(1, profiles.length);
        MetricsProfile profile = profiles[0];
        assertEquals(VALUE, profile.getName());
        validateEnabledProfiles(profile, false, false, false, false, false);
        ArrayList<Keyword> keywords = profile.getKeywords();
        assertEquals(0, keywords.size());
    }

    @Test
    public void testNoEndProfile() {
        MetricsProfileConsumer consumer = getConsumer();
        MetricsProfile[] profiles;

        // setup
        consumer.accept(EXPECTED_FIRST_LINE);
        consumer.accept(NAME_TOKEN + SEPARATOR + VALUE);
        consumer.accept(START_PROFILE_TOKEN);
        consumer.accept(MOST_COMMON_WORDS_TOKEN + SEPARATOR + true);
        consumer.accept(FILE_SIZE_TOKEN + SEPARATOR + false);
        consumer.accept(KEYWORD_HISTOGRAM_TOKEN + SEPARATOR + true);
        consumer.accept(KEYWORD_OVER_TIME_TOKEN + SEPARATOR + false);
        consumer.accept(KEYWORD_THRESHOLD_TOKEN + SEPARATOR + true);
        consumer.accept(START_KEYWORDS_TOKEN);
        consumer.accept("Kws1" + SEPARATOR + false + SEPARATOR + NOT_APPLICABLE + SEPARATOR + 0 + SEPARATOR + NONE);
        consumer.accept("Kws2" + SEPARATOR + true + SEPARATOR + NOT_APPLICABLE + SEPARATOR + 0 + SEPARATOR + NONE);
        consumer.accept("Kws3" + SEPARATOR + true + SEPARATOR + EQUAL_TO + SEPARATOR + 1 + SEPARATOR + OCCURRENCES);
        consumer.accept(END_KEYWORDS_TOKEN);

        // validations
        profiles = consumer.getProfiles();
        assertEquals(1, profiles.length);
        MetricsProfile profile = profiles[0];
        assertEquals(VALUE, profile.getName());
        validateEnabledProfiles(profile, true, false, true, false, true);
        ArrayList<Keyword> keywords = profile.getKeywords();
        assertEquals(3, keywords.size());
        validateKeyword(keywords.get(0), "Kws1", false, NOT_APPLICABLE, new BigDecimal(0), NONE);
        validateKeyword(keywords.get(1), "Kws2", true, NOT_APPLICABLE, new BigDecimal(0), NONE);
        validateKeyword(keywords.get(2), "Kws3", true, EQUAL_TO, new BigDecimal(1), OCCURRENCES);
    }

    @Test
    public void testNoEndProfileBetweenProfiles() {
        MetricsProfileConsumer consumer = getConsumer();
        MetricsProfile[] profiles;

        // setup
        consumer.accept(EXPECTED_FIRST_LINE);
        consumer.accept(NAME_TOKEN + SEPARATOR + VALUE);
        consumer.accept(START_PROFILE_TOKEN);
        consumer.accept(MOST_COMMON_WORDS_TOKEN + SEPARATOR + false);
        consumer.accept(FILE_SIZE_TOKEN + SEPARATOR + false);
        consumer.accept(KEYWORD_HISTOGRAM_TOKEN + SEPARATOR + false);
        consumer.accept(KEYWORD_OVER_TIME_TOKEN + SEPARATOR + false);
        consumer.accept(KEYWORD_THRESHOLD_TOKEN + SEPARATOR + false);
        consumer.accept(START_KEYWORDS_TOKEN);
        consumer.accept("Kws1" + SEPARATOR + false + SEPARATOR + NOT_APPLICABLE + SEPARATOR + 0 + SEPARATOR + NONE);
        consumer.accept("Kws2" + SEPARATOR + false + SEPARATOR + NOT_APPLICABLE + SEPARATOR + 0 + SEPARATOR + NONE);
        consumer.accept("Kws3" + SEPARATOR + false + SEPARATOR + NOT_APPLICABLE + SEPARATOR + 0 + SEPARATOR + NONE);
        consumer.accept(END_KEYWORDS_TOKEN);
        consumer.accept(NAME_TOKEN + SEPARATOR + VALUE);
        consumer.accept(START_PROFILE_TOKEN);
        consumer.accept(MOST_COMMON_WORDS_TOKEN + SEPARATOR + true);
        consumer.accept(FILE_SIZE_TOKEN + SEPARATOR + true);
        consumer.accept(KEYWORD_HISTOGRAM_TOKEN + SEPARATOR + true);
        consumer.accept(KEYWORD_OVER_TIME_TOKEN + SEPARATOR + true);
        consumer.accept(KEYWORD_THRESHOLD_TOKEN + SEPARATOR + true);
        consumer.accept(START_KEYWORDS_TOKEN);
        consumer.accept("Kws1" + SEPARATOR + true + SEPARATOR + EQUAL_TO + SEPARATOR + 1 + SEPARATOR + OCCURRENCES);
        consumer.accept("Kws2" + SEPARATOR + true + SEPARATOR + EQUAL_TO + SEPARATOR + 1 + SEPARATOR + OCCURRENCES);
        consumer.accept("Kws3" + SEPARATOR + true + SEPARATOR + EQUAL_TO + SEPARATOR + 1 + SEPARATOR + OCCURRENCES);
        consumer.accept(END_KEYWORDS_TOKEN);
        consumer.accept(END_PROFILE_TOKEN);

        // validations
        profiles = consumer.getProfiles();
        assertEquals(1, profiles.length);
        MetricsProfile profile = profiles[0];
        assertEquals(VALUE, profile.getName());
        // as the START PROFILE appears again, the Enables are set with the second profile's values.
        validateEnabledProfiles(profile, true, true, true, true, true);
        ArrayList<Keyword> keywords = profile.getKeywords();
        assertEquals(6, keywords.size());
        validateKeyword(keywords.get(0), "Kws1", false, NOT_APPLICABLE, new BigDecimal(0), NONE);
        validateKeyword(keywords.get(1), "Kws2", false, NOT_APPLICABLE, new BigDecimal(0), NONE);
        validateKeyword(keywords.get(2), "Kws3", false, NOT_APPLICABLE, new BigDecimal(0), NONE);
        validateKeyword(keywords.get(3), "Kws1", true, EQUAL_TO, new BigDecimal(1), OCCURRENCES);
        validateKeyword(keywords.get(4), "Kws2", true, EQUAL_TO, new BigDecimal(1), OCCURRENCES);
        validateKeyword(keywords.get(5), "Kws3", true, EQUAL_TO, new BigDecimal(1), OCCURRENCES);
    }

    @Test
    public void testNoEndKeywordsWithTwoProfiles() {
        MetricsProfileConsumer consumer = getConsumer();
        MetricsProfile[] profiles;

        // setup
        consumer.accept(EXPECTED_FIRST_LINE);
        consumer.accept(NAME_TOKEN + SEPARATOR + VALUE);
        consumer.accept(START_PROFILE_TOKEN);
        consumer.accept(MOST_COMMON_WORDS_TOKEN + SEPARATOR + false);
        consumer.accept(FILE_SIZE_TOKEN + SEPARATOR + false);
        consumer.accept(KEYWORD_HISTOGRAM_TOKEN + SEPARATOR + false);
        consumer.accept(KEYWORD_OVER_TIME_TOKEN + SEPARATOR + false);
        consumer.accept(KEYWORD_THRESHOLD_TOKEN + SEPARATOR + false);
        consumer.accept(START_KEYWORDS_TOKEN);
        consumer.accept("Kws1" + SEPARATOR + false + SEPARATOR + NOT_APPLICABLE + SEPARATOR + 0 + SEPARATOR + NONE);
        consumer.accept("Kws2" + SEPARATOR + false + SEPARATOR + NOT_APPLICABLE + SEPARATOR + 0 + SEPARATOR + NONE);
        consumer.accept("Kws3" + SEPARATOR + false + SEPARATOR + NOT_APPLICABLE + SEPARATOR + 0 + SEPARATOR + NONE);
        consumer.accept(END_PROFILE_TOKEN);
        consumer.accept(NAME_TOKEN + SEPARATOR + VALUE);
        consumer.accept(START_PROFILE_TOKEN);
        consumer.accept(MOST_COMMON_WORDS_TOKEN + SEPARATOR + true);
        consumer.accept(FILE_SIZE_TOKEN + SEPARATOR + true);
        consumer.accept(KEYWORD_HISTOGRAM_TOKEN + SEPARATOR + true);
        consumer.accept(KEYWORD_OVER_TIME_TOKEN + SEPARATOR + true);
        consumer.accept(KEYWORD_THRESHOLD_TOKEN + SEPARATOR + true);
        consumer.accept(START_KEYWORDS_TOKEN);
        consumer.accept("Kws1" + SEPARATOR + true + SEPARATOR + EQUAL_TO + SEPARATOR + 1 + SEPARATOR + OCCURRENCES);
        consumer.accept("Kws2" + SEPARATOR + true + SEPARATOR + EQUAL_TO + SEPARATOR + 1 + SEPARATOR + OCCURRENCES);
        consumer.accept("Kws3" + SEPARATOR + true + SEPARATOR + EQUAL_TO + SEPARATOR + 1 + SEPARATOR + OCCURRENCES);
        consumer.accept(END_KEYWORDS_TOKEN);
        consumer.accept(END_PROFILE_TOKEN);

        // validations
        profiles = consumer.getProfiles();
        assertEquals(2, profiles.length);
        MetricsProfile profile = profiles[0];
        assertEquals(VALUE, profile.getName());
        validateEnabledProfiles(profile, false, false, false, false, false);
        ArrayList<Keyword> keywords = profile.getKeywords();
        assertEquals(3, keywords.size());
        validateKeyword(keywords.get(0), "Kws1", false, NOT_APPLICABLE, new BigDecimal(0), NONE);
        validateKeyword(keywords.get(1), "Kws2", false, NOT_APPLICABLE, new BigDecimal(0), NONE);
        validateKeyword(keywords.get(2), "Kws3", false, NOT_APPLICABLE, new BigDecimal(0), NONE);

        profile = profiles[1];
        assertEquals(VALUE, profile.getName());
        validateEnabledProfiles(profile, true, true, true, true, true);
        keywords = profile.getKeywords();
        assertEquals(3, keywords.size());
        validateKeyword(keywords.get(0), "Kws1", true, EQUAL_TO, new BigDecimal(1), OCCURRENCES);
        validateKeyword(keywords.get(1), "Kws2", true, EQUAL_TO, new BigDecimal(1), OCCURRENCES);
        validateKeyword(keywords.get(2), "Kws3", true, EQUAL_TO, new BigDecimal(1), OCCURRENCES);
    }


    @Test
    public void simpleTestKeywordFloatingPoint() {
        MetricsProfileConsumer consumer = getConsumer();
        MetricsProfile[] profiles;

        // setup
        consumer.accept(EXPECTED_FIRST_LINE);
        consumer.accept(NAME_TOKEN + SEPARATOR + VALUE);
        consumer.accept(START_PROFILE_TOKEN);
        consumer.accept(START_KEYWORDS_TOKEN);
        consumer.accept("Kws1" + SEPARATOR + false + SEPARATOR + NOT_APPLICABLE + SEPARATOR + 0.1 + SEPARATOR + NONE);
        consumer.accept("Kws1" + SEPARATOR + false + SEPARATOR + NOT_APPLICABLE + SEPARATOR + 1.1221 + SEPARATOR + NONE);
        consumer.accept(END_KEYWORDS_TOKEN);
        consumer.accept(END_PROFILE_TOKEN);

        // validations
        profiles = consumer.getProfiles();
        assertEquals(1, profiles.length);
        MetricsProfile profile = profiles[0];
        assertEquals(VALUE, profile.getName());
        validateEnabledProfiles(profile, false, false, false, false, false);
        ArrayList<Keyword> keywords = profile.getKeywords();
        assertEquals(2, keywords.size());
        validateKeyword(keywords.get(0), "Kws1", false, NOT_APPLICABLE, new BigDecimal("0.1"), NONE);
        validateKeyword(keywords.get(1), "Kws1", false, NOT_APPLICABLE, new BigDecimal("1.1221"), NONE);
    }

    @Test
    public void testKeywordWithoutKeywordText() {
        // no string but with separators "" + SEPARATOR + false + SEPARATOR + NOT_APPLICABLE + SEPARATOR + 0 + SEPARATOR + NONE
        // no string but without the separators false + SEPARATOR + NOT_APPLICABLE + SEPARATOR + 0 + SEPARATOR + NONE)

        MetricsProfileConsumer consumer = getConsumer();
        MetricsProfile[] profiles;

        // setup
        consumer.accept(EXPECTED_FIRST_LINE);
        consumer.accept(NAME_TOKEN + SEPARATOR + VALUE);
        consumer.accept(START_PROFILE_TOKEN);
        consumer.accept(START_KEYWORDS_TOKEN);
        consumer.accept("" + SEPARATOR + false + SEPARATOR + NOT_APPLICABLE + SEPARATOR + 0.1 + SEPARATOR + NONE);
        consumer.accept(false + SEPARATOR + NOT_APPLICABLE + SEPARATOR + 1.1221 + SEPARATOR + NONE);
        consumer.accept(END_KEYWORDS_TOKEN);
        consumer.accept(END_PROFILE_TOKEN);

        // validations
        profiles = consumer.getProfiles();
        assertEquals(1, profiles.length);
        MetricsProfile profile = profiles[0];
        assertEquals(VALUE, profile.getName());
        validateEnabledProfiles(profile, false, false, false, false, false);
        ArrayList<Keyword> keywords = profile.getKeywords();
        assertEquals(1, keywords.size());
        validateKeyword(keywords.get(0), "", false, NOT_APPLICABLE, new BigDecimal("0.1"), NONE);
    }

    @Test
    public void testKeywordWithoutCaseSensitive() {
        // no string but with separators "Kws1" + SEPARATOR + false + SEPARATOR + NOT_APPLICABLE + SEPARATOR + 0 + SEPARATOR + NONE
        // no string but without the separators  "Kws1" + SEPARATOR + false + SEPARATOR + NOT_APPLICABLE + SEPARATOR + 0 + SEPARATOR + NONE)
        MetricsProfileConsumer consumer = getConsumer();
        MetricsProfile[] profiles;

        // setup
        consumer.accept(EXPECTED_FIRST_LINE);
        consumer.accept(NAME_TOKEN + SEPARATOR + VALUE);
        consumer.accept(START_PROFILE_TOKEN);
        consumer.accept(START_KEYWORDS_TOKEN);
        consumer.accept("Kws1" + SEPARATOR + SEPARATOR + NOT_APPLICABLE + SEPARATOR + 0.1 + SEPARATOR + NONE);
        consumer.accept("Kws1" + SEPARATOR + NOT_APPLICABLE + SEPARATOR + 1.1221 + SEPARATOR + NONE);
        consumer.accept(END_KEYWORDS_TOKEN);
        consumer.accept(END_PROFILE_TOKEN);

        // validations
        profiles = consumer.getProfiles();
        assertEquals(1, profiles.length);
        MetricsProfile profile = profiles[0];
        assertEquals(VALUE, profile.getName());
        validateEnabledProfiles(profile, false, false, false, false, false);
        ArrayList<Keyword> keywords = profile.getKeywords();
        assertEquals(1, keywords.size());
        validateKeyword(keywords.get(0), "Kws1", false, NOT_APPLICABLE, new BigDecimal("0.1"), NONE);
    }

    @Test
    public void testKeywordWithoutType() {
        MetricsProfileConsumer consumer = getConsumer();
        MetricsProfile[] profiles;

        // setup
        consumer.accept(EXPECTED_FIRST_LINE);
        consumer.accept(NAME_TOKEN + SEPARATOR + VALUE);
        consumer.accept(START_PROFILE_TOKEN);
        consumer.accept(START_KEYWORDS_TOKEN);
        consumer.accept("Kws1" + SEPARATOR + false + SEPARATOR + SEPARATOR + 0.1 + SEPARATOR + NONE);
        consumer.accept("Kws1" + SEPARATOR + false + SEPARATOR + 1.1221 + SEPARATOR + NONE);
        consumer.accept(END_KEYWORDS_TOKEN);
        consumer.accept(END_PROFILE_TOKEN);

        // validations
        profiles = consumer.getProfiles();
        assertEquals(1, profiles.length);
        MetricsProfile profile = profiles[0];
        assertEquals(VALUE, profile.getName());
        validateEnabledProfiles(profile, false, false, false, false, false);
        ArrayList<Keyword> keywords = profile.getKeywords();
        assertEquals(1, keywords.size());
        validateKeyword(keywords.get(0), "Kws1", false, NOT_APPLICABLE, new BigDecimal("0.1"), NONE);
    }

    @Test
    public void testKeywordWithoutValue() {
        MetricsProfileConsumer consumer = getConsumer();
        MetricsProfile[] profiles;

        // setup
        consumer.accept(EXPECTED_FIRST_LINE);
        consumer.accept(NAME_TOKEN + SEPARATOR + VALUE);
        consumer.accept(START_PROFILE_TOKEN);
        consumer.accept(START_KEYWORDS_TOKEN);
        consumer.accept("Kws1" + SEPARATOR + false + SEPARATOR + NOT_APPLICABLE + SEPARATOR + SEPARATOR + NONE);
        consumer.accept("Kws1" + SEPARATOR + false + SEPARATOR + NOT_APPLICABLE + SEPARATOR + NONE);
        consumer.accept(END_KEYWORDS_TOKEN);
        consumer.accept(END_PROFILE_TOKEN);

        // validations
        profiles = consumer.getProfiles();
        assertEquals(1, profiles.length);
        MetricsProfile profile = profiles[0];
        assertEquals(VALUE, profile.getName());
        validateEnabledProfiles(profile, false, false, false, false, false);
        ArrayList<Keyword> keywords = profile.getKeywords();
        assertEquals(1, keywords.size());
        validateKeyword(keywords.get(0), "Kws1", false, NOT_APPLICABLE, new BigDecimal("0"), NONE);
    }

    @Test
    public void testKeywordWithoutUnit() {
        MetricsProfileConsumer consumer = getConsumer();
        MetricsProfile[] profiles;

        // setup
        consumer.accept(EXPECTED_FIRST_LINE);
        consumer.accept(NAME_TOKEN + SEPARATOR + VALUE);
        consumer.accept(START_PROFILE_TOKEN);
        consumer.accept(START_KEYWORDS_TOKEN);
        consumer.accept("Kws1" + SEPARATOR + false + SEPARATOR + NOT_APPLICABLE + SEPARATOR + 0.1 + SEPARATOR + NONE);
        consumer.accept("Kws1" + SEPARATOR + false + SEPARATOR + NOT_APPLICABLE + SEPARATOR + 1.1221 + SEPARATOR + NONE);
        consumer.accept(END_KEYWORDS_TOKEN);
        consumer.accept(END_PROFILE_TOKEN);

        // validations
        profiles = consumer.getProfiles();
        assertEquals(1, profiles.length);
        MetricsProfile profile = profiles[0];
        assertEquals(VALUE, profile.getName());
        validateEnabledProfiles(profile, false, false, false, false, false);
        ArrayList<Keyword> keywords = profile.getKeywords();
        assertEquals(2, keywords.size());
        validateKeyword(keywords.get(0), "Kws1", false, NOT_APPLICABLE, new BigDecimal("0.1"), NONE);
        validateKeyword(keywords.get(1), "Kws1", false, NOT_APPLICABLE, new BigDecimal("1.1221"), NONE);
    }

    @Test
    public void testKeywordWithInvalidCaseSensitive() {
        // not a boolean
        MetricsProfileConsumer consumer = getConsumer();
        MetricsProfile[] profiles;

        // setup
        consumer.accept(EXPECTED_FIRST_LINE);
        consumer.accept(NAME_TOKEN + SEPARATOR + VALUE);
        consumer.accept(START_PROFILE_TOKEN);
        consumer.accept(START_KEYWORDS_TOKEN);
        // invalid line
        consumer.accept("Kws1" + SEPARATOR + "not a boolean" + SEPARATOR + NOT_APPLICABLE + SEPARATOR + 0.1 + SEPARATOR + NONE);
        // valid line
        consumer.accept("Kws1" + SEPARATOR + true + SEPARATOR + NOT_APPLICABLE + SEPARATOR + 1.1221 + SEPARATOR + NONE);
        consumer.accept(END_KEYWORDS_TOKEN);
        consumer.accept(END_PROFILE_TOKEN);

        // validations
        profiles = consumer.getProfiles();
        assertEquals(1, profiles.length);
        MetricsProfile profile = profiles[0];
        assertEquals(VALUE, profile.getName());
        validateEnabledProfiles(profile, false, false, false, false, false);
        ArrayList<Keyword> keywords = profile.getKeywords();
        assertEquals(2, keywords.size());
        // Parse Boolean returns false if the value is not parsable, that's convenient!
        validateKeyword(keywords.get(0), "Kws1", false, NOT_APPLICABLE, new BigDecimal("0.1"), NONE);
        validateKeyword(keywords.get(1), "Kws1", true, NOT_APPLICABLE, new BigDecimal("1.1221"), NONE);
    }

    @Test
    public void testKeywordWithInvalidType() {
        // not parsable into type
        MetricsProfileConsumer consumer = getConsumer();
        MetricsProfile[] profiles;

        // setup
        consumer.accept(EXPECTED_FIRST_LINE);
        consumer.accept(NAME_TOKEN + SEPARATOR + VALUE);
        consumer.accept(START_PROFILE_TOKEN);
        consumer.accept(START_KEYWORDS_TOKEN);
        consumer.accept("Kws1" + SEPARATOR + false + SEPARATOR + "not a valid thing" + SEPARATOR + 0.1 + SEPARATOR + NONE);
        consumer.accept("Kws1" + SEPARATOR + false + SEPARATOR + NOT_APPLICABLE + SEPARATOR + 1.1221 + SEPARATOR + NONE);
        consumer.accept(END_KEYWORDS_TOKEN);
        consumer.accept(END_PROFILE_TOKEN);

        // validations
        profiles = consumer.getProfiles();
        assertEquals(1, profiles.length);
        MetricsProfile profile = profiles[0];
        assertEquals(VALUE, profile.getName());
        validateEnabledProfiles(profile, false, false, false, false, false);
        ArrayList<Keyword> keywords = profile.getKeywords();
        assertEquals(2, keywords.size());
        // By default if the Type is invalid, returns the NOT_APPLICABLE
        validateKeyword(keywords.get(0), "Kws1", false, NOT_APPLICABLE, new BigDecimal("0.1"), NONE);
        validateKeyword(keywords.get(1), "Kws1", false, NOT_APPLICABLE, new BigDecimal("1.1221"), NONE);
    }

    @Test
    public void testKeywordWithInvalidValue() {
        // not parsable into number
        MetricsProfileConsumer consumer = getConsumer();
        MetricsProfile[] profiles;

        // setup
        consumer.accept(EXPECTED_FIRST_LINE);
        consumer.accept(NAME_TOKEN + SEPARATOR + VALUE);
        consumer.accept(START_PROFILE_TOKEN);
        consumer.accept(START_KEYWORDS_TOKEN);
        consumer.accept("Kws1" + SEPARATOR + false + SEPARATOR + NOT_APPLICABLE + SEPARATOR + "not a valid thing" + SEPARATOR + NONE);
        consumer.accept("Kws1" + SEPARATOR + false + SEPARATOR + NOT_APPLICABLE + SEPARATOR + 1.1221 + SEPARATOR + NONE);
        consumer.accept(END_KEYWORDS_TOKEN);
        consumer.accept(END_PROFILE_TOKEN);

        // validations
        profiles = consumer.getProfiles();
        assertEquals(1, profiles.length);
        MetricsProfile profile = profiles[0];
        assertEquals(VALUE, profile.getName());
        validateEnabledProfiles(profile, false, false, false, false, false);
        ArrayList<Keyword> keywords = profile.getKeywords();
        assertEquals(1, keywords.size());
        validateKeyword(keywords.get(0), "Kws1", false, NOT_APPLICABLE, new BigDecimal("1.1221"), NONE);
    }

    @Test
    public void testKeywordWithInvalidUnit() {
        // not parsable into unit
        MetricsProfileConsumer consumer = getConsumer();
        MetricsProfile[] profiles;

        // setup
        consumer.accept(EXPECTED_FIRST_LINE);
        consumer.accept(NAME_TOKEN + SEPARATOR + VALUE);
        consumer.accept(START_PROFILE_TOKEN);
        consumer.accept(START_KEYWORDS_TOKEN);
        consumer.accept("Kws1" + SEPARATOR + false + SEPARATOR + NOT_APPLICABLE + SEPARATOR + 0.1 + SEPARATOR + "not a valid unit");
        consumer.accept("Kws1" + SEPARATOR + false + SEPARATOR + NOT_APPLICABLE + SEPARATOR + 1.1221 + SEPARATOR + NONE);
        consumer.accept(END_KEYWORDS_TOKEN);
        consumer.accept(END_PROFILE_TOKEN);

        // validations
        profiles = consumer.getProfiles();
        assertEquals(1, profiles.length);
        MetricsProfile profile = profiles[0];
        assertEquals(VALUE, profile.getName());
        validateEnabledProfiles(profile, false, false, false, false, false);
        ArrayList<Keyword> keywords = profile.getKeywords();
        assertEquals(2, keywords.size());
        validateKeyword(keywords.get(0), "Kws1", false, NOT_APPLICABLE, new BigDecimal("0.1"), NONE);
        validateKeyword(keywords.get(1), "Kws1", false, NOT_APPLICABLE, new BigDecimal("1.1221"), NONE);
    }




}
