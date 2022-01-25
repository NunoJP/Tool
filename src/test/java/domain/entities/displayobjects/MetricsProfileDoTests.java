package domain.entities.displayobjects;
import domain.entities.common.Keyword;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MetricsProfileDoTests {

    @Test
    public void simpleTest() {
        MetricsProfileDo profileDo = new MetricsProfileDo();
        profileDo.setName("Name");
        profileDo.setHasMostCommonWords(true);
        profileDo.setHasFileSize(true);
        profileDo.setHasKeywordHistogram(true);
        profileDo.setHasKeywordOverTime(true);
        profileDo.setHasKeywordThreshold(true);

        assertEquals("Name", profileDo.getName());
        assertTrue(profileDo.isHasMostCommonWords());
        assertTrue(profileDo.isHasFileSize());
        assertTrue(profileDo.isHasKeywordHistogram());
        assertTrue(profileDo.isHasKeywordOverTime());
        assertTrue(profileDo.isHasKeywordThreshold());
    }

    @Test
    public void simpleTestWithKeywords() {
        MetricsProfileDo profileDo = new MetricsProfileDo();
        assertTrue(profileDo.getKeywords().isEmpty());
        profileDo.addKeyword(new Keyword("KWD", false));
        profileDo.addKeyword(new Keyword("KWD1", true));
        assertEquals(2, profileDo.getKeywords().size());
        assertEquals("KWD", profileDo.getKeywords().get(0).getKeywordText());
        assertFalse(profileDo.getKeywords().get(0).isCaseSensitive());
        assertEquals("KWD1", profileDo.getKeywords().get(1).getKeywordText());
        assertTrue(profileDo.getKeywords().get(1).isCaseSensitive());
    }


}
