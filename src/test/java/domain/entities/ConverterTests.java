package domain.entities;

import domain.entities.displayobjects.MetricsProfileDo;
import domain.entities.displayobjects.ParsingProfileDo;
import domain.entities.domainobjects.MetricsProfile;
import domain.entities.domainobjects.ParsingProfile;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ConverterTests {

    @Test
    public void testParsingProfileToDomainObject(){
        ParsingProfileDo displayObject = new ParsingProfileDo(1, "Name");
        ParsingProfile domainObject = Converter.toDomainObject(displayObject);
        assertEquals(displayObject.getId(), domainObject.getId());
        assertEquals(displayObject.getName(), domainObject.getName());
    }

    @Test
    public void testParsingProfileToDisplayObject(){
        ParsingProfile domainObject = new ParsingProfile(1, "Name");
        ParsingProfileDo displayObject = Converter.toDisplayObject(domainObject);
        assertEquals(domainObject.getId(), displayObject.getId());
        assertEquals(domainObject.getName(), displayObject.getName());
    }

    @Test
    public void testMetricsProfileToDomainObject(){
        MetricsProfileDo displayObject = new MetricsProfileDo(1, "Name");
        MetricsProfile domainObject = Converter.toDomainObject(displayObject);
        assertEquals(displayObject.getId(), domainObject.getId());
        assertEquals(displayObject.getName(), domainObject.getName());
    }

    @Test
    public void testMetricsProfileToDisplayObject(){
        MetricsProfile domainObject = new MetricsProfile(1, "Name");
        MetricsProfileDo displayObject = Converter.toDisplayObject(domainObject);
        assertEquals(domainObject.getId(), displayObject.getId());
        assertEquals(domainObject.getName(), displayObject.getName());
    }
}
