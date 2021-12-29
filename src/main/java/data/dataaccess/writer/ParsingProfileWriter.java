package data.dataaccess.writer;

import data.dataaccess.memory.MemoryRepository;
import domain.entities.domainobjects.ParsingProfile;

public class ParsingProfileWriter {
    private final MemoryRepository instance;

    public ParsingProfileWriter(String default_parsing_profile_folder_name) {
        instance = MemoryRepository.getInstance();
    }

    public boolean createProfile(ParsingProfile toDomainObject) {
        return instance.createProfile(toDomainObject);
    }
}
