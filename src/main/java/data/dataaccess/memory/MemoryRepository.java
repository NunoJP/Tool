package data.dataaccess.memory;

import domain.entities.common.SeparatorEnum;
import domain.entities.common.TextClassesEnum;
import domain.entities.displayobjects.ParsingProfileDo;
import domain.entities.domainobjects.ParsingProfile;

import java.util.ArrayList;

public class MemoryRepository {

    public static MemoryRepository instance;

    public static MemoryRepository getInstance(){
        if(instance == null) {
            instance = new MemoryRepository();
        }
        return instance;
    }

    private ArrayList<ParsingProfile> parsingProfiles;
    private MemoryRepository() {
        parsingProfiles = new ArrayList<>();
        ParsingProfileDo profile = new ParsingProfileDo();
        profile.addPortionAndGetProfile(TextClassesEnum.DATE.getName(), true, false, false);
        profile.addPortionAndGetProfile(SeparatorEnum.SPACE.getName(), false, true, false);
        profile.addPortionAndGetProfile(TextClassesEnum.TIMESTAMP.getName(), false, false, false);

        ParsingProfile p1 = new ParsingProfile(1, "Test 1", profile.getPortions());
        ParsingProfile p2 = new ParsingProfile(2, "Test 2", profile.getPortions());
        parsingProfiles.add(p1);
        parsingProfiles.add(p2);
    }

    public ArrayList<ParsingProfile> getParsingProfiles(){
        return parsingProfiles;
    }


    public boolean createProfile(ParsingProfile toDomainObject) {
        return parsingProfiles.add(toDomainObject);
    }
}
