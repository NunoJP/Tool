package data.dataaccess.writer;

import data.dataaccess.MetricsProfilesCommon;
import data.dataaccess.reader.MetricsProfileConsumer;
import domain.entities.common.Keyword;
import domain.entities.common.ThresholdTypeEnum;
import domain.entities.common.ThresholdUnitEnum;
import domain.entities.domainobjects.MetricsProfile;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;

import static data.dataaccess.common.MetricsProfileReadWriteConstants.NO_NAME_PROVIDED;
import static domain.entities.common.ThresholdTypeEnum.BIGGER_THAN;
import static domain.entities.common.ThresholdTypeEnum.NOT_APPLICABLE;
import static domain.entities.common.ThresholdTypeEnum.SMALLER_OR_EQUAL_THAN;
import static domain.entities.common.ThresholdTypeEnum.SMALLER_THAN;
import static domain.entities.common.ThresholdUnitEnum.NONE;
import static domain.entities.common.ThresholdUnitEnum.OCCURRENCES;
import static domain.entities.common.ThresholdUnitEnum.PERCENTAGE;
import static org.junit.Assert.assertEquals;

public class MetricsProfileFunctionTests extends MetricsProfilesCommon {

    public static final String VALUE = "Value";
    private MetricsProfileFunction function;
    private MetricsProfileConsumer consumer;
    private MetricsProfile[] profiles;

    @Before
    public void setUp() {
        function = new MetricsProfileFunction(true);
        consumer = getConsumer();
        profiles = null;
    }

    @Test
    public void simpleTest() {
        MetricsProfile profile = new MetricsProfile();
        profile.setName(VALUE);
        profile.addKeyword(new Keyword("kwd", true));

        // Process
        simulateWriteAndRead(function, consumer, profile);

        // Validate
        MetricsProfile[] profiles = consumer.getProfiles();
        assertEquals(1, profiles.length);
        MetricsProfile parsedProfile = profiles[0];
        assertEquals(profile.getName(), parsedProfile.getName());
        ArrayList<Keyword> keywords = parsedProfile.getKeywords();
        assertEquals(1, keywords.size());
        validateEnabledProfiles(parsedProfile, false, false, false, false, false);
        validateKeyword(keywords.get(0), "kwd", true, NOT_APPLICABLE, new BigDecimal("0"), NONE);
    }

    @Test
    public void simpleTestTwoProfiles() {
        MetricsProfile profile1 = new MetricsProfile();
        profile1.setName(VALUE);
        profile1.addKeyword(new Keyword("kwd", true));
        MetricsProfile profile2 = new MetricsProfile();
        profile2.setName(VALUE);
        profile2.addKeyword(new Keyword("kwd", true));

        // Process
        simulateWriteAndRead(function, consumer, profile1);
        simulateWriteAndRead(function, consumer, profile2);

        // Validate
        MetricsProfile[] profiles = consumer.getProfiles();
        assertEquals(2, profiles.length);
        MetricsProfile parsedProfile = profiles[0];
        assertEquals(profile1.getName(), parsedProfile.getName());
        ArrayList<Keyword> keywords = parsedProfile.getKeywords();
        assertEquals(1, keywords.size());
        validateEnabledProfiles(parsedProfile, false, false, false, false, false);
        validateKeyword(keywords.get(0), "kwd", true, NOT_APPLICABLE, new BigDecimal("0"), NONE);

        parsedProfile = profiles[1];
        assertEquals(profile2.getName(), parsedProfile.getName());
        keywords = parsedProfile.getKeywords();
        assertEquals(1, keywords.size());
        validateEnabledProfiles(parsedProfile, false, false, false, false, false);
        validateKeyword(keywords.get(0), "kwd", true, NOT_APPLICABLE, new BigDecimal("0"), NONE);
    }

    @Test
    public void testJustHeader() {
        MetricsProfile profile = new MetricsProfile();
        profile.setName(VALUE);

        // Process
        simulateWriteAndRead(function, consumer, profile);

        // Validate
        MetricsProfile[] profiles = consumer.getProfiles();
        assertEquals(1, profiles.length);
        MetricsProfile parsedProfile = profiles[0];
        assertEquals(profile.getName(), parsedProfile.getName());
        ArrayList<Keyword> keywords = parsedProfile.getKeywords();
        assertEquals(0, keywords.size());
    }

    @Test
    public void testEmptyName() {
        MetricsProfile profile = new MetricsProfile();
        profile.setName("");

        // Process
        simulateWriteAndRead(function, consumer, profile);

        // Validate
        MetricsProfile[] profiles = consumer.getProfiles();
        assertEquals(1, profiles.length);
        MetricsProfile parsedProfile = profiles[0];
        assertEquals(NO_NAME_PROVIDED, parsedProfile.getName());
    }


    @Test
    public void normalTestAllModesEnabled() {
        MetricsProfile profile = new MetricsProfile();
        profile.setName(VALUE);
        profile.setHasKeywordThreshold(true);
        profile.setHasFileSize(true);
        profile.setHasKeywordOverTime(true);
        profile.setHasKeywordHistogram(true);
        profile.setHasMostCommonWords(true);
        profile.addKeyword(new Keyword("kwd", true));

        // Process
        simulateWriteAndRead(function, consumer, profile);

        // Validate
        MetricsProfile[] profiles = consumer.getProfiles();
        assertEquals(1, profiles.length);
        MetricsProfile parsedProfile = profiles[0];
        assertEquals(profile.getName(), parsedProfile.getName());
        ArrayList<Keyword> keywords = parsedProfile.getKeywords();
        assertEquals(1, keywords.size());
        validateEnabledProfiles(parsedProfile, true, true, true, true, true);
        validateKeyword(keywords.get(0), "kwd", true, NOT_APPLICABLE, new BigDecimal("0"), NONE);
    }

    @Test
    public void normalTestSomeModesDisabled() {
        MetricsProfile profile = new MetricsProfile();
        profile.setName(VALUE);
        profile.setHasMostCommonWords(false);
        profile.setHasFileSize(true);
        profile.setHasKeywordHistogram(false);
        profile.setHasKeywordOverTime(true);
        profile.setHasKeywordThreshold(false);

        profile.addKeyword(new Keyword("kwd", true));

        // Process
        simulateWriteAndRead(function, consumer, profile);

        // Validate
        MetricsProfile[] profiles = consumer.getProfiles();
        assertEquals(1, profiles.length);
        MetricsProfile parsedProfile = profiles[0];
        assertEquals(profile.getName(), parsedProfile.getName());
        ArrayList<Keyword> keywords = parsedProfile.getKeywords();
        assertEquals(1, keywords.size());
        validateEnabledProfiles(parsedProfile, false, true, false, true, false);
        validateKeyword(keywords.get(0), "kwd", true, NOT_APPLICABLE, new BigDecimal("0"), NONE);
    }

    @Test
    public void testKeywordCombinations() {
        MetricsProfile profile = new MetricsProfile();
        profile.setName(VALUE);
        profile.setHasMostCommonWords(false);
        profile.setHasFileSize(true);
        profile.setHasKeywordHistogram(false);
        profile.setHasKeywordOverTime(true);
        profile.setHasKeywordThreshold(false);

        String [] kwds = new String[] { "kwd0", "kwd1", "kwd2", "kwd3" };

        BigDecimal [] values = new BigDecimal[] {
                new BigDecimal("0"), new BigDecimal("0.1111"), new BigDecimal("1213"), new BigDecimal("-12"),
        };

        ThresholdTypeEnum [] types = new ThresholdTypeEnum[] { NOT_APPLICABLE, BIGGER_THAN, SMALLER_THAN, SMALLER_OR_EQUAL_THAN };
        ThresholdUnitEnum [] units = new ThresholdUnitEnum[] { NONE, OCCURRENCES, OCCURRENCES, PERCENTAGE };

        for (int i = 0; i < 4; i++) {
            Keyword kwd = new Keyword(kwds[i], i % 2 == 1);
            kwd.setThresholdTrio(types[i], units[i], values[i]);
            profile.addKeyword(kwd);
        }
        // Process
        simulateWriteAndRead(function, consumer, profile);

        // Validate
        MetricsProfile[] profiles = consumer.getProfiles();
        assertEquals(1, profiles.length);
        MetricsProfile parsedProfile = profiles[0];
        assertEquals(profile.getName(), parsedProfile.getName());
        ArrayList<Keyword> keywords = parsedProfile.getKeywords();
        validateEnabledProfiles(parsedProfile, false, true, false, true, false);
        assertEquals(4, keywords.size());
        for (int i = 0; i < 4; i++) {
            validateKeyword(keywords.get(i), kwds[i], i % 2 == 1, types[i], values[i], units[i]);
        }

    }

}
