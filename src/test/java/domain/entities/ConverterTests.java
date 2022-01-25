package domain.entities;

import domain.entities.common.Keyword;
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

    private ArrayList<ParsingProfilePortion> setupParsingProfilePortions() {
        ParsingProfileDo profile = new ParsingProfileDo();
        profile.addPortionAndGetProfile(TextClassesEnum.DATE.getName(), TextClassesEnum.DATE.getName(), true, false, false);
        profile.addPortionAndGetProfile(SeparatorEnum.SPACE.getName(), SeparatorEnum.SPACE.getSymbol(), false, true, false);
        profile.addPortionAndGetProfile(TextClassesEnum.TIMESTAMP.getName(), TextClassesEnum.TIMESTAMP.getName(), false, false, false);
        String sProfile = profile.getGuiRepresentation();
        assertEquals(" Ignore<Date>  \" \"  Keep<Timestamp> ", sProfile);
        return profile.getPortions();
    }

    @Test
    public void testMetricsProfileToDomainObject(){
        MetricsProfileDo displayObject = new MetricsProfileDo(1, "Name");
        displayObject.setHasMostCommonWords(true);
        displayObject.setHasFileSize(true);
        displayObject.setHasKeywordHistogram(true);
        displayObject.setHasKeywordOverTime(true);
        displayObject.setHasKeywordThreshold(true);
        MetricsProfile domainObject = Converter.toDomainObject(displayObject);
        assertEquals(displayObject.getId(), domainObject.getId());
        assertEquals(displayObject.getName(), domainObject.getName());
        assertEquals(displayObject.isHasMostCommonWords(), domainObject.isHasMostCommonWords());
        assertEquals(displayObject.isHasFileSize(), domainObject.isHasFileSize());
        assertEquals(displayObject.isHasKeywordHistogram(), domainObject.isHasKeywordHistogram());
        assertEquals(displayObject.isHasKeywordOverTime(), domainObject.isHasKeywordOverTime());
        assertEquals(displayObject.isHasKeywordThreshold(), domainObject.isHasKeywordThreshold());
    }

    @Test
    public void testMetricsProfileToDisplayObject(){
        MetricsProfile domainObject = new MetricsProfile(1, "Name");
        domainObject.setHasMostCommonWords(true);
        domainObject.setHasFileSize(true);
        domainObject.setHasKeywordHistogram(true);
        domainObject.setHasKeywordOverTime(true);
        domainObject.setHasKeywordThreshold(true);
        domainObject.addKeyword(new Keyword("KWD", false));
        domainObject.addKeyword(new Keyword("KWD1", true));

        MetricsProfileDo displayObject = Converter.toDisplayObject(domainObject);
        assertEquals(domainObject.getId(), displayObject.getId());
        assertEquals(domainObject.getName(), displayObject.getName());
        assertEquals(domainObject.isHasMostCommonWords(), displayObject.isHasMostCommonWords());
        assertEquals(domainObject.isHasFileSize(), displayObject.isHasFileSize());
        assertEquals(domainObject.isHasKeywordHistogram(), displayObject.isHasKeywordHistogram());
        assertEquals(domainObject.isHasKeywordOverTime(), displayObject.isHasKeywordOverTime());
        assertEquals(domainObject.isHasKeywordThreshold(), displayObject.isHasKeywordThreshold());


        assertEquals(2, domainObject.getKeywords().size());

        for (int i = 0; i < domainObject.getKeywords().size(); i++) {
            assertEquals(domainObject.getKeywords().get(i), displayObject.getKeywords().get(i));
        }
    }



}
