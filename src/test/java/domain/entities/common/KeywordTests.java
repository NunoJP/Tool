package domain.entities.common;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class KeywordTests {

    private static final String KWD = "KWD";

    @Test
    public void testSimpleKeywordDo() {
        Keyword keyword = new Keyword(KWD, false);
        assertEquals(KWD, keyword.getKeywordText());
        assertFalse(keyword.isCaseSensitive());
        assertEquals(ThresholdTypeEnum.NOT_APPLICABLE, keyword.getThresholdType());
        assertEquals(ThresholdUnitEnum.NONE, keyword.getThresholdUnit());
        assertEquals(0, keyword.getThresholdValue().intValue());
    }

    @Test
    public void testSimpleKeywordDoWithThreshold() {
        Keyword keyword = new Keyword(KWD, false);
        assertEquals(KWD, keyword.getKeywordText());
        assertFalse(keyword.isCaseSensitive());
        assertEquals(ThresholdTypeEnum.NOT_APPLICABLE, keyword.getThresholdType());
        assertEquals(ThresholdUnitEnum.NONE, keyword.getThresholdUnit());
        assertEquals(0, keyword.getThresholdValue().intValue());

        keyword.setThresholdTrio(ThresholdTypeEnum.BIGGER_OR_EQUAL_THAN, ThresholdUnitEnum.OCCURRENCES, new BigDecimal(1));
        assertEquals(ThresholdTypeEnum.BIGGER_OR_EQUAL_THAN, keyword.getThresholdType());
        assertEquals(ThresholdUnitEnum.OCCURRENCES, keyword.getThresholdUnit());
        assertEquals(1, keyword.getThresholdValue().intValue());
    }

}
