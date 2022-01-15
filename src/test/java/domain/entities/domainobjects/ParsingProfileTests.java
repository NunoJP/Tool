package domain.entities.domainobjects;

import domain.entities.Converter;
import domain.entities.common.ParsingProfilePortion;
import domain.entities.common.SeparatorEnum;
import domain.entities.common.TextClassesEnum;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class ParsingProfileTests {

    @Test
    public void addPortionAndGetProfile() {
        ParsingProfile parsingProfile = new ParsingProfile();
        parsingProfile.addPortion(new ParsingProfilePortion(TextClassesEnum.DATE.getName(), TextClassesEnum.DATE.getName(), true, false, false));
        parsingProfile.addPortion(new ParsingProfilePortion(SeparatorEnum.SPACE.getName(), SeparatorEnum.SPACE.getSymbol(), false, true, false));
        parsingProfile.addPortion(new ParsingProfilePortion(TextClassesEnum.TIMESTAMP.getName(), TextClassesEnum.TIMESTAMP.getName(), false, false, false));
        ArrayList<ParsingProfilePortion> portions = parsingProfile.getPortions();
        assertEquals(3, portions.size());
        assertEquals(" Ignore<Date>  \" \"  Keep<Timestamp> ", Converter.toDisplayObject(parsingProfile).getDescription());
    }

    @Test
    public void addSpecificFormatPortionAndGetProfile() {
        ParsingProfile parsingProfile = new ParsingProfile();
        parsingProfile.addPortion(new ParsingProfilePortion(TextClassesEnum.DATE.getName(), TextClassesEnum.DATE.getName(), false, false, true, "YYYY-MM-DD"));
        parsingProfile.addPortion(new ParsingProfilePortion(SeparatorEnum.SPACE.getName(), SeparatorEnum.SPACE.getSymbol(), false, true, false));
        parsingProfile.addPortion(new ParsingProfilePortion(TextClassesEnum.TIMESTAMP.getName(), TextClassesEnum.TIMESTAMP.getName(), false, false, true, "HH-mm-ss"));
        ArrayList<ParsingProfilePortion> portions = parsingProfile.getPortions();
        assertEquals(3, portions.size());
        assertEquals(" KeepSpecific<Date YYYY-MM-DD>  \" \"  KeepSpecific<Timestamp HH-mm-ss> ", Converter.toDisplayObject(parsingProfile).getDescription());
    }

}
