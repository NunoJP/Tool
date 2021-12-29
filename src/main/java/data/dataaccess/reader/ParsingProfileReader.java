package data.dataaccess.reader;

import data.dataaccess.memory.MemoryRepository;
import domain.entities.domainobjects.ParsingProfile;

public class ParsingProfileReader {

    private MemoryRepository instance;

    public ParsingProfileReader(String default_parsing_profile_folder_name) {
        instance = MemoryRepository.getInstance();
    }

    public ParsingProfileReader() {
        instance = MemoryRepository.getInstance();
    }

    public ParsingProfile[] getParsingProfiles() {
        return instance.getParsingProfiles().toArray(ParsingProfile[]::new);
    }
}
