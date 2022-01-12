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
    private HashMap<String, ArrayList<ParsingProfile>> parsingProfileByFile;
    private MemoryRepository() {
        parsingProfiles = new HashMap<>();
        parsingProfileByFile = new HashMap<>();
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
        // add to storage file map the new profile
        if(!parsingProfileByFile.containsKey(targetFile)) {
            // new listing
            parsingProfileByFile.put(targetFile, new ArrayList<>());
        }
        ArrayList<ParsingProfile> parsingProfiles = parsingProfileByFile.get(targetFile);
        parsingProfiles.add(toDomainObject);
        return true;
    }

    public boolean profileExists(ParsingProfile toDomainObject) {
        ArrayList<ParsingProfile> parsingProfiles = parsingProfileByFile.get(toDomainObject.getOriginFile());
        if (parsingProfiles == null || parsingProfiles.isEmpty()) {
            return false;
        }
        long count = parsingProfiles.stream().filter(c -> c.getName().equals(toDomainObject.getName())).count();
        return count != 0;
    }

    public ParsingProfile getProfile(int id) {
        return parsingProfiles.get(id);
    }

    public ArrayList<ParsingProfile> getParsingProfilesByOriginFile(String originFile) {
        return parsingProfileByFile.getOrDefault(originFile, new ArrayList<>());
    }

    public void createParsingProfiles(ArrayList<ParsingProfile> accumulator) {
        for (ParsingProfile profile : accumulator) {
            createProfile(profile, profile.getOriginFile());
        }
    }

    public boolean updateProfile(ParsingProfile parsingProfile) {
        if (profileExists(parsingProfile)) {
            parsingProfiles.put(parsingProfile.getId(), parsingProfile);
            // add to storage file map the new profile
            ArrayList<ParsingProfile> profileArrayList = parsingProfileByFile.get(parsingProfile.getOriginFile());
            for (int i = 0; i < profileArrayList.size(); i++) {
                if (profileArrayList.get(i).getName().equals(parsingProfile.getName())){
                    profileArrayList.remove(i);
                    profileArrayList.add(parsingProfile);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean deleteProfile(ParsingProfile parsingProfile) {
        if(profileExists(parsingProfile)) {
            parsingProfiles.remove(parsingProfile.getId());
            ArrayList<ParsingProfile> profileArrayList = parsingProfileByFile.get(parsingProfile.getOriginFile());
            for (int i = 0; i < profileArrayList.size(); i++) {
                if (profileArrayList.get(i).getName().equals(parsingProfile.getName())){
                    profileArrayList.remove(i);
                    return true;
                }
            }
        }
        return false;
    }

    public void fullReset() {
        parsingProfiles = new HashMap<>();
        parsingProfileByFile = new HashMap<>();
        idx = new AtomicInteger(0);
    }


}
