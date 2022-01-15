package domain.entities;

import domain.entities.common.ParsingProfilePortion;
import domain.entities.common.SeparatorEnum;
import domain.entities.common.TextClassesEnum;
import domain.entities.displayobjects.MetricsProfileDo;
import domain.entities.displayobjects.ParsingProfileDo;
import domain.entities.domainobjects.MetricsProfile;
import domain.entities.domainobjects.ParsingProfile;
import org.junit.Test;

import java.util.ArrayList;

import static data.dataaccess.common.ParsingProfileReadWriteConstants.DEFAULT_PARSING_PROFILE_FILE_NAME;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ConverterTests {

    @Test
    public void testParsingProfileToDomainObject(){
        ParsingProfileDo displayObject = new ParsingProfileDo(1, "Name", setupParsingProfilePortions());
        ParsingProfile domainObject = Converter.toDomainObject(displayObject);
        assertEquals(displayObject.getId(), domainObject.getId());
        assertEquals(displayObject.getName(), domainObject.getName());
        assertEquals(displayObject.getOriginFile(), domainObject.getOriginFile());

        // validate portions
        ArrayList<ParsingProfilePortion> domainObjectPortions = domainObject.getPortions();
        assertNotNull(domainObjectPortions);
        ArrayList<ParsingProfilePortion> displayObjectPortions = displayObject.getPortions();
        assertEquals(displayObjectPortions.size(), domainObjectPortions.size());
        for (int i = 0; i < displayObjectPortions.size(); i++) {
            assertEquals(displayObjectPortions.get(i), domainObjectPortions.get(i));
        }
    }

    @Test
    public void testParsingProfileToDisplayObject(){
        ParsingProfile domainObject = new ParsingProfile(1, "Name", setupParsingProfilePortions());
        ParsingProfileDo displayObject = Converter.toDisplayObject(domainObject);
        assertEquals(domainObject.getId(), displayObject.getId());
        assertEquals(domainObject.getName(), displayObject.getName());
        assertEquals(domainObject.getOriginFile(), displayObject.getOriginFile());
        assertEquals(DEFAULT_PARSING_PROFILE_FILE_NAME, displayObject.getOriginFile());

        // validate portions
        ArrayList<ParsingProfilePortion> displayObjectPortions = displayObject.getPortions();
        assertNotNull(displayObjectPortions);
        ArrayList<ParsingProfilePortion> domainObjectPortions = domainObject.getPortions();
        assertEquals(domainObjectPortions.size(), displayObjectPortions.size());
        for (int i = 0; i < domainObjectPortions.size(); i++) {
            assertEquals(domainObjectPortions.get(i), displayObjectPortions.get(i));
        }
    }

    @Test
    public void testParsingProfileToDomainObjectWithOriginFile(){
        ParsingProfileDo displayObject = new ParsingProfileDo(1, "Name", setupParsingProfilePortions(), "originFile");
        ParsingProfile domainObject = Converter.toDomainObject(displayObject);
        assertEquals(displayObject.getId(), domainObject.getId());
        assertEquals(displayObject.getName(), domainObject.getName());
        assertEquals(displayObject.getOriginFile(), domainObject.getOriginFile());

        // validate portions
        ArrayList<ParsingProfilePortion> domainObjectPortions = domainObject.getPortions();
        assertNotNull(domainObjectPortions);
        ArrayList<ParsingProfilePortion> displayObjectPortions = displayObject.getPortions();
        assertEquals(displayObjectPortions.size(), domainObjectPortions.size());
        for (int i = 0; i < displayObjectPortions.size(); i++) {
            assertEquals(displayObjectPortions.get(i), domainObjectPortions.get(i));
        }
    }

    @Test
    public void testParsingProfileToDisplayObjectWithOriginFile(){
        ParsingProfile domainObject = new ParsingProfile(1, "Name", setupParsingProfilePortions(), "originFile");
        ParsingProfileDo displayObject = Converter.toDisplayObject(domainObject);
        assertEquals(domainObject.getId(), displayObject.getId());
        assertEquals(domainObject.getName(), displayObject.getName());
        assertEquals(domainObject.getOriginFile(), displayObject.getOriginFile());

        // validate portions
        ArrayList<ParsingProfilePortion> displayObjectPortions = displayObject.getPortions();
        assertNotNull(displayObjectPortions);
        ArrayList<ParsingProfilePortion> domainObjectPortions = domainObject.getPortions();
        assertEquals(domainObjectPortions.size(), displayObjectPortions.size());
        for (int i = 0; i < domainObjectPortions.size(); i++) {
            assertEquals(domainObjectPortions.get(i), displayObjectPortions.get(i));
        }
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


    private ArrayList<ParsingProfilePortion> setupParsingProfilePortions() {
        ParsingProfileDo profile = new ParsingProfileDo();
        profile.addPortionAndGetProfile(TextClassesEnum.DATE.getName(), TextClassesEnum.DATE.getName(), true, false, false);
        profile.addPortionAndGetProfile(SeparatorEnum.SPACE.getName(), SeparatorEnum.SPACE.getSymbol(), false, true, false);
        profile.addPortionAndGetProfile(TextClassesEnum.TIMESTAMP.getName(), TextClassesEnum.TIMESTAMP.getName(), false, false, false);
        String sProfile = profile.getGuiRepresentation();
        assertEquals(" Ignore<Date>  \" \"  Keep<Timestamp> ", sProfile);
        return profile.getPortions();
    }
}
