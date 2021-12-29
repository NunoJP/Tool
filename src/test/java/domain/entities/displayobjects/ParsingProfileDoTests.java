package domain.entities.displayobjects;

import domain.entities.common.ParsingProfilePortion;
import domain.entities.common.SeparatorEnum;
import domain.entities.common.TextClassesEnum;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class ParsingProfileDoTests {

    private int setupSize = -1;

    @Test
    public void addPortionAndGetProfile() {
        ParsingProfileDo profile = new ParsingProfileDo();
        String sProfile = profile.addPortionAndGetProfile(TextClassesEnum.DATE.getName(), true, false, false);
        assertEquals(" Ignore<Date> ", sProfile);
        sProfile = profile.addPortionAndGetProfile(SeparatorEnum.SPACE.getName(), false, true, false);
        assertEquals(" Ignore<Date>  \" \" ", sProfile);
        sProfile = profile.addPortionAndGetProfile(TextClassesEnum.TIMESTAMP.getName(), false, false, false);
        assertEquals(" Ignore<Date>  \" \"  Keep<Timestamp> ", sProfile);
    }

    @Test
    public void addSpecificFormatPortionAndGetProfile() {
        ParsingProfileDo profile = new ParsingProfileDo();
        profile.addPortionAndGetProfile(TextClassesEnum.DATE.getName(), false, false, true, "YYYY-MM-DD");
        profile.addPortionAndGetProfile(SeparatorEnum.SPACE.getName(), false, true, false);
        profile.addPortionAndGetProfile(TextClassesEnum.TIMESTAMP.getName(), false, false, true, "HH-mm-ss");
        String sProfile = profile.getGuiRepresentation();
        assertEquals(" KeepSpecific<Date YYYY-MM-DD>  \" \"  KeepSpecific<Timestamp HH-mm-ss> ", sProfile);
    }

    @Test
    public void removeLastPortionAndGetProfile() {
        ParsingProfileDo profile = setup();
        String sProfile = profile.removeLastPortionAndGetProfile();
        assertEquals(" Ignore<Date>  \" \" ", sProfile);
        sProfile = profile.removeLastPortionAndGetProfile();
        assertEquals(" Ignore<Date> ", sProfile);
    }

    @Test
    public void clearPortions() {
        ParsingProfileDo profile = setup();
        String sProfile = profile.clearPortions();
        assertEquals("", sProfile);
    }


    @Test
    public void getPortions() {
        ParsingProfileDo profile = setup();
        ArrayList<ParsingProfilePortion> portions = profile.getPortions();
        assertEquals(setupSize, portions.size());
    }

    @Test
    public void getGuiRepresentation() {
        ParsingProfileDo profile = new ParsingProfileDo();
        profile.addPortionAndGetProfile(TextClassesEnum.DATE.getName(), true, false, false);
        profile.addPortionAndGetProfile(SeparatorEnum.SPACE.getName(), false, true, false);
        profile.addPortionAndGetProfile(TextClassesEnum.TIMESTAMP.getName(), false, false, false);
        String sProfile = profile.getGuiRepresentation();
        assertEquals(" Ignore<Date>  \" \"  Keep<Timestamp> ", sProfile);
    }


    private ParsingProfileDo setup() {
        ParsingProfileDo profile = new ParsingProfileDo();
        profile.addPortionAndGetProfile(TextClassesEnum.DATE.getName(), true, false, false);
        setupSize = 1;
        profile.addPortionAndGetProfile(SeparatorEnum.SPACE.getName(), false, true, false);
        setupSize++;
        profile.addPortionAndGetProfile(TextClassesEnum.TIMESTAMP.getName(), false, false, false);
        setupSize++;
        String sProfile = profile.getGuiRepresentation();
        assertEquals(" Ignore<Date>  \" \"  Keep<Timestamp> ", sProfile);
        return profile;
    }



}
