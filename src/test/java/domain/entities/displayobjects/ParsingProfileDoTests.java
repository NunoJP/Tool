package domain.entities.displayobjects;

import domain.entities.common.ParsingProfilePortion;
import domain.entities.common.SeparatorEnum;
import domain.entities.common.TextClassesEnum;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ParsingProfileDoTests {

    private int setupSize = -1;

    @Test
    public void addPortionAndGetProfile() {
        ParsingProfileDo profile = new ParsingProfileDo();
        String sProfile = profile.addPortionAndGetProfile(TextClassesEnum.DATE.getName(), TextClassesEnum.DATE.getName(), true, false, false);
        assertEquals(" Ignore<Date> ", sProfile);
        sProfile = profile.addPortionAndGetProfile(SeparatorEnum.SPACE.getName(), SeparatorEnum.SPACE.getSymbol(), false, true, false);
        assertEquals(" Ignore<Date>  \" \" ", sProfile);
        sProfile = profile.addPortionAndGetProfile(TextClassesEnum.TIMESTAMP.getName(), TextClassesEnum.TIMESTAMP.getName(), false, false, false);
        assertEquals(" Ignore<Date>  \" \"  Keep<Timestamp> ", sProfile);
    }

    @Test
    public void addSpecificFormatPortionAndGetProfile() {
        ParsingProfileDo profile = new ParsingProfileDo();
        profile.addPortionAndGetProfile(TextClassesEnum.DATE.getName(), TextClassesEnum.DATE.getName(), false, false, true, "YYYY-MM-DD");
        profile.addPortionAndGetProfile(SeparatorEnum.SPACE.getName(), SeparatorEnum.SPACE.getSymbol(), false, true, false);
        profile.addPortionAndGetProfile(TextClassesEnum.TIMESTAMP.getName(), TextClassesEnum.TIMESTAMP.getName(), false, false, true, "HH-mm-ss");
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
        profile.addPortionAndGetProfile(TextClassesEnum.DATE.getName(), TextClassesEnum.DATE.getName(), true, false, false);
        profile.addPortionAndGetProfile(SeparatorEnum.SPACE.getName(), SeparatorEnum.SPACE.getSymbol(), false, true, false);
        profile.addPortionAndGetProfile(TextClassesEnum.TIMESTAMP.getName(), TextClassesEnum.TIMESTAMP.getName(), false, false, false);
        String sProfile = profile.getGuiRepresentation();
        assertEquals(" Ignore<Date>  \" \"  Keep<Timestamp> ", sProfile);
    }

    @Test
    public void testFinish(){
        ParsingProfileDo profile = new ParsingProfileDo();
        profile.addPortionAndGetProfile(TextClassesEnum.DATE.getName(), TextClassesEnum.DATE.getName(), true, false, false);
        profile.addPortionAndGetProfile(SeparatorEnum.SPACE.getName(), SeparatorEnum.SPACE.getSymbol(), false, true, false);
        profile.addPortionAndGetProfile(TextClassesEnum.TIMESTAMP.getName(), TextClassesEnum.TIMESTAMP.getName(), false, false, false);
        String sProfile = profile.getGuiRepresentation();
        assertEquals(" Ignore<Date>  \" \"  Keep<Timestamp> ", sProfile);
        ParsingProfilePortion lastPortion = profile.getPortions().get(2); // get the last portion
        assertFalse(lastPortion.isLast());
        // finish the profile
        profile.finishProfile();
        assertTrue(lastPortion.isLast());
    }


    @Test
    public void testAddElementsAfterFinishAndThenFinish(){
        ParsingProfileDo profile = new ParsingProfileDo();
        profile.addPortionAndGetProfile(TextClassesEnum.DATE.getName(), TextClassesEnum.DATE.getName(), true, false, false);
        profile.addPortionAndGetProfile(SeparatorEnum.SPACE.getName(), SeparatorEnum.SPACE.getSymbol(), false, true, false);
        profile.addPortionAndGetProfile(TextClassesEnum.TIMESTAMP.getName(), TextClassesEnum.TIMESTAMP.getName(), false, false, false);
        String sProfile = profile.getGuiRepresentation();
        assertEquals(" Ignore<Date>  \" \"  Keep<Timestamp> ", sProfile);
        ParsingProfilePortion lastPortion = profile.getPortions().get(2); // get the last portion
        assertFalse(lastPortion.isLast());
        // finish the profile
        profile.finishProfile();
        assertTrue(lastPortion.isLast());
        // add new portion after doing the finish (simulating an update)
        profile.addPortionAndGetProfile(TextClassesEnum.TIMESTAMP.getName(), TextClassesEnum.TIMESTAMP.getName(), false, false, false);
        ParsingProfilePortion newLastPortion = profile.getPortions().get(3); // get the last portion
        // before a finish the states should not change
        assertTrue(lastPortion.isLast());
        assertFalse(newLastPortion.isLast());
        // finish the profile once again
        profile.finishProfile();
        assertFalse(lastPortion.isLast());
        assertTrue(newLastPortion.isLast());
    }


    @Test
    public void testCopyByConstructor() {
        ParsingProfileDo setup = setup();
        setup.setId(1);
        setup.setName("name");
        setup.setOriginFile("origin");

        ParsingProfileDo copy = new ParsingProfileDo(setup);
        // check copy of profileDo
        String sProfile = copy.getGuiRepresentation();
        assertEquals(" Ignore<Date>  \" \"  Keep<Timestamp> ", sProfile);
        assertEquals(1, copy.getId().intValue());
        assertEquals("name", copy.getName());
        assertEquals("origin", copy.getOriginFile());


        // make changes to copy
        copy.addPortionAndGetProfile(TextClassesEnum.DATE.getName(), TextClassesEnum.DATE.getName(), true, false, false);
        copy.setId(2);
        copy.setName("name2");
        copy.setOriginFile("origin2");

        // check original profileDo
        sProfile = setup.getGuiRepresentation();
        assertEquals(" Ignore<Date>  \" \"  Keep<Timestamp> ", sProfile);
        assertEquals(1, setup.getId().intValue());
        assertEquals("name", setup.getName());
        assertEquals("origin", setup.getOriginFile());
    }


    private ParsingProfileDo setup() {
        ParsingProfileDo profile = new ParsingProfileDo();
        profile.addPortionAndGetProfile(TextClassesEnum.DATE.getName(), TextClassesEnum.DATE.getName(), true, false, false);
        setupSize = 1;
        profile.addPortionAndGetProfile(SeparatorEnum.SPACE.getName(), SeparatorEnum.SPACE.getSymbol(), false, true, false);
        setupSize++;
        profile.addPortionAndGetProfile(TextClassesEnum.TIMESTAMP.getName(), TextClassesEnum.TIMESTAMP.getName(), false, false, false);
        setupSize++;
        String sProfile = profile.getGuiRepresentation();
        assertEquals(" Ignore<Date>  \" \"  Keep<Timestamp> ", sProfile);
        return profile;
    }



}
