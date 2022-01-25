package domain.entities.domainobjects;

import domain.entities.common.Keyword;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MetricsProfileTests {

    @Test
    public void simpleTest() {
        MetricsProfile profile = new MetricsProfile();
        profile.setName("Name");
        profile.setHasMostCommonWords(true);
        profile.setHasFileSize(true);
        profile.setHasKeywordHistogram(true);
        profile.setHasKeywordOverTime(true);
        profile.setHasKeywordThreshold(true);

        assertEquals("Name", profile.getName());
        assertTrue(profile.isHasMostCommonWords());
        assertTrue(profile.isHasFileSize());
        assertTrue(profile.isHasKeywordHistogram());
        assertTrue(profile.isHasKeywordOverTime());
        assertTrue(profile.isHasKeywordThreshold());
    }

    @Test
    public void simpleTestWithKeywords() {
        MetricsProfile profile = new MetricsProfile();
        assertTrue(profile.getKeywords().isEmpty());
        profile.addKeyword(new Keyword("KWD", false));
        profile.addKeyword(new Keyword("KWD1", true));
        assertEquals(2, profile.getKeywords().size());
        assertEquals("KWD", profile.getKeywords().get(0).getKeywordText());
        assertFalse(profile.getKeywords().get(0).isCaseSensitive());
        assertEquals("KWD1", profile.getKeywords().get(1).getKeywordText());
        assertTrue(profile.getKeywords().get(1).isCaseSensitive());
    }


}
