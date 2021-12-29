package domain.services;

import data.dataaccess.reader.ParsingProfileReader;
import data.dataaccess.writer.ParsingProfileWriter;
import domain.entities.Converter;
import domain.entities.displayobjects.ParsingProfileDo;
import domain.entities.domainobjects.ParsingProfile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;

public class ParsingProfileManagementService {

    private final ParsingProfileWriter writer;
    protected ParsingProfileReader reader;
    private String DEFAULT_PARSING_PROFILE_FOLDER_NAME = "parsing_profiles";

    public ParsingProfileManagementService() {
        reader = new ParsingProfileReader(DEFAULT_PARSING_PROFILE_FOLDER_NAME);
        writer = new ParsingProfileWriter(DEFAULT_PARSING_PROFILE_FOLDER_NAME);
    }

    public ParsingProfileDo[] getParsingProfiles() {
        ParsingProfile[] parsingProfiles = reader.getParsingProfiles();
        ArrayList<ParsingProfile> profiles = new ArrayList<>(Arrays.asList(parsingProfiles));
        profiles = profiles.stream().sorted(Comparator.comparingInt(ParsingProfile::getId)).collect(Collectors.toCollection(ArrayList::new));
        return Arrays.stream(parsingProfiles).map(Converter::toDisplayObject).collect(Collectors.toList()).toArray(ParsingProfileDo[]::new);
    }

    public boolean updateProfile(ParsingProfileDo parsingProfile) {
        return false;
    }

    public boolean deleteProfile(ParsingProfileDo parsingProfile) {
        return false;
    }

    public boolean createProfile(ParsingProfileDo newObject) {
        return writer.createProfile(Converter.toDomainObject(newObject));
    }
}
