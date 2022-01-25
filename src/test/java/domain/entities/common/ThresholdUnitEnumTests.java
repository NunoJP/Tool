package domain.entities.common;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
public class ThresholdUnitEnumTests {

    @Test
    public void testGetParsingStringBySymbol(){
        assertEquals(ThresholdUnitEnum.NONE.getParsingString(), ThresholdUnitEnum.getParsingStringByName(ThresholdUnitEnum.NONE.getName()));
        assertEquals(ThresholdUnitEnum.OCCURRENCES.getParsingString(), ThresholdUnitEnum.getParsingStringByName(ThresholdUnitEnum.OCCURRENCES.getName()));
        assertEquals(ThresholdUnitEnum.PERCENTAGE.getParsingString(), ThresholdUnitEnum.getParsingStringByName(ThresholdUnitEnum.PERCENTAGE.getName()));
    }
}
