package data.dataaccess.memory;

import domain.entities.common.Keyword;
import domain.entities.domainobjects.MetricsProfile;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class MetricsProfilesMemoryRepositoryTests {

    @Before
    public void setup() {
        MetricsProfilesMemoryRepository repository = MetricsProfilesMemoryRepository.getInstance();
        repository.fullReset();
    }

    @Test
    public void testCreateProfileSimple(){
        MetricsProfilesMemoryRepository repository = MetricsProfilesMemoryRepository.getInstance();
        MetricsProfile profile = new MetricsProfile("Name");
        assertTrue(repository.createProfile(profile, profile.getOriginFile()));
        MetricsProfile mapProfile = repository.getProfile(0);
        assertEquals(profile.getName(), mapProfile.getName());
    }

    @Test
    public void testCreateProfileCollision(){
        MetricsProfilesMemoryRepository repository = MetricsProfilesMemoryRepository.getInstance();
        // create two profiles with the same name but one has Keywords Over Time option as true
        // the one with this enabled should be the one remaining
        MetricsProfile profile = new MetricsProfile( "Name");
        profile.setHasKeywordOverTime(true);
        MetricsProfile profile2 = new MetricsProfile( "Name");

        assertTrue(repository.createProfile(profile, profile.getOriginFile()));
        assertFalse(repository.createProfile(profile2, profile2.getOriginFile()));

        MetricsProfile mapProfile = repository.getProfile(0);
        assertEquals(profile.getName(), mapProfile.getName());
        assertEquals(profile.hasKeywordOverTime(), mapProfile.hasKeywordOverTime());
    }

    @Test
    public void testCreateMultipleProfiles(){
        MetricsProfilesMemoryRepository repository = MetricsProfilesMemoryRepository.getInstance();

        // create two profiles with different names and everything else is the same, there should be two profiles in the end
        MetricsProfile profile = new MetricsProfile( "Name");
        MetricsProfile profile2 = new MetricsProfile( "Name2");

        assertTrue(repository.createProfile(profile, profile.getOriginFile()));
        assertTrue(repository.createProfile(profile2, profile2.getOriginFile()));

        MetricsProfile mapProfile = repository.getProfile(0);
        assertEquals(profile.getName(), mapProfile.getName());

        MetricsProfile mapProfile2 = repository.getProfile(1);
        assertEquals(profile2.getName(), mapProfile2.getName());
    }

    @Test
    public void testUpdateProfileSimple(){
        // Basic create for setup
        MetricsProfilesMemoryRepository repository = MetricsProfilesMemoryRepository.getInstance();
        MetricsProfile profile = new MetricsProfile("Name");
        assertTrue(repository.createProfile(profile, profile.getOriginFile()));
        MetricsProfile mapProfile = repository.getProfile(0);
        assertEquals(profile.getName(), mapProfile.getName());
        // Update and validation
        MetricsProfile profileUp = new MetricsProfile("Name");
        profileUp.addKeyword(new Keyword("Text", true));
        assertTrue(repository.updateProfile(profileUp));
        mapProfile = repository.getProfile(0);
        assertEquals(profileUp.getName(), mapProfile.getName());
        assertEquals(profile.getName(), profileUp.getName());
        assertFalse(profileUp.getKeywords().isEmpty());
        Keyword keyword = profileUp.getKeywords().get(0);
        assertEquals("Text", keyword.getKeywordText());
        assertTrue(keyword.isCaseSensitive());
    }

    @Test
    public void testUpdateProfileWithoutExistingProfile(){
        MetricsProfilesMemoryRepository repository = MetricsProfilesMemoryRepository.getInstance();
        MetricsProfile profileUp = new MetricsProfile("Name");
        assertFalse(repository.updateProfile(profileUp));
    }

    @Test
    public void removeProfileSimple() {
        // Basic create for setup
        MetricsProfilesMemoryRepository repository = MetricsProfilesMemoryRepository.getInstance();
        MetricsProfile profile = new MetricsProfile("Name");
        assertTrue(repository.createProfile(profile, profile.getOriginFile()));
        MetricsProfile mapProfile = repository.getProfile(0);
        assertEquals(profile.getName(), mapProfile.getName());
        // Remove and validation
        assertTrue(repository.deleteProfile(profile));
        assertNull(repository.getProfile(0));
    }

    @Test
    public void removeProfileWithoutExistingProfile() {
        // Basic create for setup
        MetricsProfilesMemoryRepository repository = MetricsProfilesMemoryRepository.getInstance();
        MetricsProfile profile = new MetricsProfile("Name");
        // empty repository
        assertNull(repository.getProfile(0));
        // Remove and validation
        assertFalse(repository.deleteProfile(profile));
    }


}
