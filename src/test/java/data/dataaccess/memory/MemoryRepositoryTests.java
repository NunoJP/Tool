package data.dataaccess.memory;

import domain.entities.common.ParsingProfilePortion;
import domain.entities.domainobjects.ParsingProfile;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MemoryRepositoryTests {

    @Before
    public void setup() {
        MemoryRepository repository = MemoryRepository.getInstance();
        repository.fullReset();
    }

    @Test
    public void testCreateProfileSimple(){
        MemoryRepository repository = MemoryRepository.getInstance();
        ParsingProfile profile = new ParsingProfile("Name");
        assertTrue(repository.createProfile(profile));
        ParsingProfile mapProfile = repository.getProfile(0);
        assertEquals(profile.getName(), mapProfile.getName());
    }

    @Test
    public void testCreateProfileCollision(){
        MemoryRepository repository = MemoryRepository.getInstance();
        ArrayList<ParsingProfilePortion> parsingProfilePortions = new ArrayList<>();
        parsingProfilePortions.add(new ParsingProfilePortion("Test", true, true));

        // create two profiles with the same name but one has Portions, the one with portions (the first to be created)
        // should be the one remaining
        ParsingProfile profile = new ParsingProfile( "Name", parsingProfilePortions);
        ParsingProfile profile2 = new ParsingProfile( "Name");

        assertTrue(repository.createProfile(profile));
        assertFalse(repository.createProfile(profile2));

        ParsingProfile mapProfile = repository.getProfile(0);
        assertEquals(profile.getName(), mapProfile.getName());
        assertEquals(profile.getPortions().size(), mapProfile.getPortions().size());
    }

    @Test
    public void testCreateMultipleProfiles(){
        MemoryRepository repository = MemoryRepository.getInstance();
        ArrayList<ParsingProfilePortion> parsingProfilePortions = new ArrayList<>();
        parsingProfilePortions.add(new ParsingProfilePortion("Test", true, true));

        // create two profiles with the same name but one has Portions, there should be two profiles in the end
        ParsingProfile profile = new ParsingProfile( "Name", parsingProfilePortions);
        ParsingProfile profile2 = new ParsingProfile( "Name2");

        assertTrue(repository.createProfile(profile));
        assertTrue(repository.createProfile(profile2));

        ParsingProfile mapProfile = repository.getProfile(0);
        assertEquals(profile.getName(), mapProfile.getName());
        assertEquals(profile.getPortions().size(), mapProfile.getPortions().size());

        ParsingProfile mapProfile2 = repository.getProfile(1);
        assertEquals(profile2.getName(), mapProfile2.getName());
    }

    @Test
    public void testUpdateProfileSimple(){

    }

    @Test
    public void testUpdateProfileCollision(){

    }



}
