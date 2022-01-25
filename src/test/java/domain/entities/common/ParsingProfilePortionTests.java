package domain.entities.common;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
public class ParsingProfilePortionTests {

    private static final String NAME = "NAME";
    private static final TextClassesEnum TEXT_CLASS_PORTION = TextClassesEnum.LEVEL;
    private static final SeparatorEnum SEPARATOR_PORTION = SeparatorEnum.COLON;

    @Test
    public void testCreateTextClassNotIgnore() {
        ParsingProfilePortion portion = new ParsingProfilePortion(NAME, TEXT_CLASS_PORTION.getName(), false, false);
        assertEquals(NAME, portion.getPortionName());
        assertEquals(TEXT_CLASS_PORTION.getName(), portion.getPortionSymbol());
        assertFalse(portion.isIgnore());
        assertFalse(portion.isSeparator());
        assertFalse(portion.isSpecificFormat());
        assertFalse(portion.isLast());
    }

    @Test
    public void testCreateTextClassIgnore() {
        ParsingProfilePortion portion = new ParsingProfilePortion(NAME, TEXT_CLASS_PORTION.getName(), true, false);
        assertEquals(NAME, portion.getPortionName());
        assertEquals(TEXT_CLASS_PORTION.getName(), portion.getPortionSymbol());
        assertTrue(portion.isIgnore());
        assertFalse(portion.isSeparator());
        assertFalse(portion.isSpecificFormat());
        assertFalse(portion.isLast());
    }

    @Test
    public void testCreateSeparator() {
        ParsingProfilePortion portion = new ParsingProfilePortion(SEPARATOR_PORTION.getName(), SEPARATOR_PORTION.getSymbol(), false, true);
        assertEquals(SEPARATOR_PORTION.getName(), portion.getPortionName());
        assertEquals(SEPARATOR_PORTION.getSymbol(), portion.getPortionSymbol());
        assertFalse(portion.isIgnore());
        assertTrue(portion.isSeparator());
        assertFalse(portion.isSpecificFormat());
        assertFalse(portion.isLast());
    }
}
