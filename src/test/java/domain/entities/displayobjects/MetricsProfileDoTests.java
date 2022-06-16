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
        profileDo.setOriginFile("Origin");

        assertEquals("Name", profileDo.getName());
        assertTrue(profileDo.hasMostCommonWords());
        assertTrue(profileDo.hasFileSize());
        assertTrue(profileDo.hasKeywordHistogram());
        assertTrue(profileDo.hasKeywordOverTime());
        assertTrue(profileDo.hasKeywordThreshold());
        assertEquals("Origin", profileDo.getOriginFile());
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

    @Test
    public void testCopyByConstructor() {
        MetricsProfileDo profileDo = new MetricsProfileDo();
        assertTrue(profileDo.getKeywords().isEmpty());
        profileDo.setName("Name");
        profileDo.setHasMostCommonWords(true);
        profileDo.setHasFileSize(true);
        profileDo.setHasKeywordHistogram(true);
        profileDo.setHasKeywordOverTime(true);
        profileDo.setHasKeywordThreshold(true);
        profileDo.setOriginFile("Origin");
        profileDo.addKeyword(new Keyword("KWD", false));
        profileDo.addKeyword(new Keyword("KWD1", true));

        // Validate first object
        validateOriginalObject(profileDo);


        MetricsProfileDo copyProfileDo = new MetricsProfileDo(profileDo);
        // should be equal
        validateOriginalObject(copyProfileDo);

        // make changes to new object
        copyProfileDo.setName("Name1");
        copyProfileDo.setHasMostCommonWords(false);
        copyProfileDo.setHasFileSize(false);
        copyProfileDo.setHasKeywordHistogram(false);
        copyProfileDo.setHasKeywordOverTime(false);
        copyProfileDo.setHasKeywordThreshold(false);
        copyProfileDo.setOriginFile("Origin1");
        copyProfileDo.addKeyword(new Keyword("KWD2", false));
        copyProfileDo.addKeyword(new Keyword("KWD3", true));

        // old object should not have changed
        validateOriginalObject(profileDo);
    }

    private void validateOriginalObject(MetricsProfileDo profileDo) {
        assertEquals("Name", profileDo.getName());
        assertTrue(profileDo.hasMostCommonWords());
        assertTrue(profileDo.hasFileSize());
        assertTrue(profileDo.hasKeywordHistogram());
        assertTrue(profileDo.hasKeywordOverTime());
        assertTrue(profileDo.hasKeywordThreshold());
        assertEquals("Origin", profileDo.getOriginFile());
        assertEquals(2, profileDo.getKeywords().size());
        assertEquals("KWD", profileDo.getKeywords().get(0).getKeywordText());
        assertFalse(profileDo.getKeywords().get(0).isCaseSensitive());
        assertEquals("KWD1", profileDo.getKeywords().get(1).getKeywordText());
        assertTrue(profileDo.getKeywords().get(1).isCaseSensitive());
    }


}
