package domain.entities.common;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TextClassesEnumTests {

    @Test
    public void testGetParsingStringByName() {
        assertEquals(TextClassesEnum.TIMESTAMP.getParsingString(), TextClassesEnum.getParsingStringByName(TextClassesEnum.TIMESTAMP.getName()));
        assertEquals(TextClassesEnum.TIMESTAMP.getParsingString(), TextClassesEnum.getParsingStringByName(TextClassesEnum.TIMESTAMP.getName()));
        assertEquals(TextClassesEnum.DATE.getParsingString(), TextClassesEnum.getParsingStringByName(TextClassesEnum.DATE.getName()));
        assertEquals(TextClassesEnum.TIME.getParsingString(), TextClassesEnum.getParsingStringByName(TextClassesEnum.TIME.getName()));
        assertEquals(TextClassesEnum.LEVEL.getParsingString(), TextClassesEnum.getParsingStringByName(TextClassesEnum.LEVEL.getName()));
        assertEquals(TextClassesEnum.MESSAGE.getParsingString(), TextClassesEnum.getParsingStringByName(TextClassesEnum.MESSAGE.getName()));
        assertEquals(TextClassesEnum.METHOD.getParsingString(), TextClassesEnum.getParsingStringByName(TextClassesEnum.METHOD.getName()));
        assertEquals(TextClassesEnum.ID.getParsingString(), TextClassesEnum.getParsingStringByName(TextClassesEnum.ID.getName()));
    }
}
