package domain.services;

import data.dataaccess.reader.ParsingProfileReader;
import domain.entities.Converter;
import domain.entities.displayobjects.ParsingProfileDo;
import domain.entities.domainobjects.ParsingProfile;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ParsingProfileManagementService {

    protected ParsingProfileReader reader;
    private String DEFAULT_PARSING_PROFILE_FOLDER_NAME = "parsing_profiles";

    public ParsingProfileManagementService() {
        reader = new ParsingProfileReader(DEFAULT_PARSING_PROFILE_FOLDER_NAME);
    }

    public ParsingProfileDo[] getParsingProfiles() {
        ParsingProfile [] profiles = reader.getParsingProfiles();
        return Arrays.stream(profiles).map(Converter::toDisplayObject).collect(Collectors.toList()).toArray(ParsingProfileDo[]::new);
    }

    public boolean updateProfile(ParsingProfileDo parsingProfile) {
        return false;
    }

    public boolean deleteProfile(ParsingProfileDo parsingProfile) {
        return false;
    }
}
