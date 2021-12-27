package data.dataaccess.reader;

import domain.entities.domainobjects.ParsingProfile;

public class ParsingProfileReader {

    public ParsingProfileReader(String default_parsing_profile_folder_name) {

    }

    public ParsingProfileReader() {

    }

    public ParsingProfile[] getParsingProfiles() {
        ParsingProfile p1 = new ParsingProfile(1, "Test 1", "Profiles for tests 1");
        ParsingProfile p2 = new ParsingProfile(2, "Test 2", "Profiles for tests 2");
        return new ParsingProfile[] { p1, p2 };
    }
}
