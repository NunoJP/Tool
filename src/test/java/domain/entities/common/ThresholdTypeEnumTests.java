package domain.entities.common;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
public class ThresholdTypeEnumTests {

    @Test
    public void testGetParsingStringBySymbol(){
        assertEquals(ThresholdTypeEnum.NOT_APPLICABLE.getParsingString(), ThresholdTypeEnum.getParsingStringByName(ThresholdTypeEnum.NOT_APPLICABLE.getName()));
        assertEquals(ThresholdTypeEnum.EQUAL_TO.getParsingString(), ThresholdTypeEnum.getParsingStringByName(ThresholdTypeEnum.EQUAL_TO.getName()));
        assertEquals(ThresholdTypeEnum.BIGGER_THAN.getParsingString(), ThresholdTypeEnum.getParsingStringByName(ThresholdTypeEnum.BIGGER_THAN.getName()));
        assertEquals(ThresholdTypeEnum.BIGGER_OR_EQUAL_THAN.getParsingString(), ThresholdTypeEnum.getParsingStringByName(ThresholdTypeEnum.BIGGER_OR_EQUAL_THAN.getName()));
        assertEquals(ThresholdTypeEnum.SMALLER_THAN.getParsingString(), ThresholdTypeEnum.getParsingStringByName(ThresholdTypeEnum.SMALLER_THAN.getName()));
        assertEquals(ThresholdTypeEnum.SMALLER_OR_EQUAL_THAN.getParsingString(), ThresholdTypeEnum.getParsingStringByName(ThresholdTypeEnum.SMALLER_OR_EQUAL_THAN.getName()));
    }
}
