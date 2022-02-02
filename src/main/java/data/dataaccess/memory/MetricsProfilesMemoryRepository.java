package data.dataaccess.memory;

import domain.entities.domainobjects.MetricsProfile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MetricsProfilesMemoryRepository {

    public static MetricsProfilesMemoryRepository instance;
    private AtomicInteger idx = new AtomicInteger(0);

    public static MetricsProfilesMemoryRepository getInstance(){
        if(instance == null) {
            instance = new MetricsProfilesMemoryRepository();
        }
        return instance;
    }

    private HashMap<Integer, MetricsProfile> metricsProfiles;
    private HashMap<String, ArrayList<MetricsProfile>> metricsProfileByFile;
    private MetricsProfilesMemoryRepository() {
        metricsProfiles = new HashMap<>();
        metricsProfileByFile = new HashMap<>();
    }

    public ArrayList<MetricsProfile> getMetricsProfiles(){
        return new ArrayList<>(metricsProfiles.values());
    }


    public boolean createProfile(MetricsProfile toDomainObject, String targetFile) {
        if(profileExists(toDomainObject)){
            return false; // profile id already exists
        }
        int i = idx.getAndIncrement();
        toDomainObject.setId(i);
        metricsProfiles.put(i, toDomainObject);
        // add to storage file map the new profile
        if(!metricsProfileByFile.containsKey(targetFile)) {
            // new listing
            metricsProfileByFile.put(targetFile, new ArrayList<>());
        }
        ArrayList<MetricsProfile> metricsProfiles = metricsProfileByFile.get(targetFile);
        metricsProfiles.add(toDomainObject);
        return true;
    }

    public boolean profileExists(MetricsProfile toDomainObject) {
        ArrayList<MetricsProfile> metricsProfiles = metricsProfileByFile.get(toDomainObject.getOriginFile());
        if (metricsProfiles == null || metricsProfiles.isEmpty()) {
            return false;
        }
        long count = metricsProfiles.stream().filter(c -> c.getName().equals(toDomainObject.getName())).count();
        return count != 0;
    }

    public MetricsProfile getProfile(int id) {
        return metricsProfiles.get(id);
    }

    public ArrayList<MetricsProfile> getMetricsProfilesByOriginFile(String originFile) {
        return metricsProfileByFile.getOrDefault(originFile, new ArrayList<>());
    }

    public void createMetricsProfiles(ArrayList<MetricsProfile> accumulator) {
        for (MetricsProfile profile : accumulator) {
            createProfile(profile, profile.getOriginFile());
        }
    }

    public boolean updateProfile(MetricsProfile metricsProfile) {
        if (profileExists(metricsProfile)) {
            metricsProfiles.put(metricsProfile.getId(), metricsProfile);
            // add to storage file map the new profile
            ArrayList<MetricsProfile> profileArrayList = metricsProfileByFile.get(metricsProfile.getOriginFile());
            for (int i = 0; i < profileArrayList.size(); i++) {
                if (profileArrayList.get(i).getName().equals(metricsProfile.getName())){
                    profileArrayList.remove(i);
                    profileArrayList.add(metricsProfile);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean deleteProfile(MetricsProfile metricsProfile) {
        if(profileExists(metricsProfile)) {
            metricsProfiles.remove(metricsProfile.getId());
            ArrayList<MetricsProfile> profileArrayList = metricsProfileByFile.get(metricsProfile.getOriginFile());
            for (int i = 0; i < profileArrayList.size(); i++) {
                if (profileArrayList.get(i).getName().equals(metricsProfile.getName())){
                    profileArrayList.remove(i);
                    return true;
                }
            }
        }
        return false;
    }

    public void fullReset() {
        metricsProfiles = new HashMap<>();
        metricsProfileByFile = new HashMap<>();
        idx = new AtomicInteger(0);
    }


}
