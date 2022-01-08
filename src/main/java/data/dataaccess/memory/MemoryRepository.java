package data.dataaccess.memory;

import domain.entities.domainobjects.ParsingProfile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MemoryRepository {

    public static MemoryRepository instance;
    private AtomicInteger idx = new AtomicInteger(0);

    public static MemoryRepository getInstance(){
        if(instance == null) {
            instance = new MemoryRepository();
        }
        return instance;
    }

    private HashMap<Integer, ParsingProfile> parsingProfiles;
    private HashMap<Integer, ProfileNameFileNamePair> parsingProfileNames;
    private MemoryRepository() {
        parsingProfiles = new HashMap<>();
        parsingProfileNames = new HashMap<>();
    }

    public ArrayList<ParsingProfile> getParsingProfiles(){
        return new ArrayList<>(parsingProfiles.values());
    }


    public boolean createProfile(ParsingProfile toDomainObject, String targetFile) {
        if(profileExists(toDomainObject)){
            return false; // profile id already exists
        }
        int i = idx.getAndIncrement();
        toDomainObject.setId(i);
        parsingProfiles.put(i, toDomainObject);
        parsingProfileNames.put(i, new ProfileNameFileNamePair(toDomainObject.getName(), targetFile));
        return true;
    }

    public boolean profileExists(ParsingProfile toDomainObject) {
        long count = parsingProfileNames.values().stream().filter(c -> c.getProfileName().equals(toDomainObject.getName())).count();
        return count != 0;
    }

    public ParsingProfile getProfile(int id) {
        return parsingProfiles.get(id);
    }

    public void createParsingProfiles(ArrayList<ParsingProfile> accumulator) {
        for (ParsingProfile profile : accumulator) {
            createProfile(profile, profile.getOriginFile());
        }
    }

    public void fullReset() {
        parsingProfiles = new HashMap<>();
        parsingProfileNames = new HashMap<>();
        idx = new AtomicInteger(0);
    }
}
