package domain.entities.common;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SeparatorEnumTests {

    @Test
    public void testGetParsingStringBySymbol(){
        assertEquals(SeparatorEnum.SPACE.getParsingString(), SeparatorEnum.getParsingStringBySymbol(SeparatorEnum.SPACE.getSymbol()));
        assertEquals(SeparatorEnum.HIFEN.getParsingString(), SeparatorEnum.getParsingStringBySymbol(SeparatorEnum.HIFEN.getSymbol()));
        assertEquals(SeparatorEnum.COLON.getParsingString(), SeparatorEnum.getParsingStringBySymbol(SeparatorEnum.COLON.getSymbol()));
        assertEquals(SeparatorEnum.SEMI_COLON.getParsingString(), SeparatorEnum.getParsingStringBySymbol(SeparatorEnum.SEMI_COLON.getSymbol()));
        assertEquals(SeparatorEnum.TAB.getParsingString(), SeparatorEnum.getParsingStringBySymbol(SeparatorEnum.TAB.getSymbol()));
        assertEquals(SeparatorEnum.COMMA.getParsingString(), SeparatorEnum.getParsingStringBySymbol(SeparatorEnum.COMMA.getSymbol()));
        assertEquals(SeparatorEnum.OPEN_BRACKET.getParsingString(), SeparatorEnum.getParsingStringBySymbol(SeparatorEnum.OPEN_BRACKET.getSymbol()));
        assertEquals(SeparatorEnum.CLOSE_BRACKET.getParsingString(), SeparatorEnum.getParsingStringBySymbol(SeparatorEnum.CLOSE_BRACKET.getSymbol()));
    }
}
