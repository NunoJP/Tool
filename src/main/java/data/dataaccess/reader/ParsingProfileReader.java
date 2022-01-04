package data.dataaccess.reader;

import data.dataaccess.memory.MemoryRepository;
import domain.entities.domainobjects.ParsingProfile;
import presentation.common.GuiMessages;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import static presentation.common.GuiMessages.ERROR_FOLDER_DOES_NOT_EXIST;
import static presentation.common.GuiMessages.LOG_ERROR_ERROR_OPENING_FILE;
import static presentation.common.GuiMessages.LOG_ERROR_ERROR_READING_FILE;

public class ParsingProfileReader {

    private static final Logger LOGGER = Logger.getLogger(ParsingProfileReader.class.getName());
    private final MemoryRepository instance;
    private String default_parsing_profile_folder_name = "ParsingProfiles";
    private final ParsingProfileConsumer consumer = new ParsingProfileConsumer();

    public ParsingProfileReader(String default_parsing_profile_folder_name) {
        this.default_parsing_profile_folder_name = default_parsing_profile_folder_name;
        instance = MemoryRepository.getInstance();
    }

    public ParsingProfileReader() {
        instance = MemoryRepository.getInstance();
    }

    public ParsingProfile[] getParsingProfiles() {
        Path currentRelativePath = Paths.get(default_parsing_profile_folder_name);
        File f = currentRelativePath.toAbsolutePath().toFile();
        ArrayList<ParsingProfile> accumulator = new ArrayList<>();
        if (f.exists() && f.isDirectory()) {
            File[] files = f.listFiles();
            if (files == null) {
                return new ParsingProfile[0];
            } else {
                for (File file : files) {
                    accumulator.addAll(Arrays.asList(readProfiles(file)));
                }
            }
        } else {
            throw new InvalidParameterException(ERROR_FOLDER_DOES_NOT_EXIST);
        }

        instance.createParsingProfiles(accumulator);
        return instance.getParsingProfiles().toArray(ParsingProfile[]::new);
    }

    private ParsingProfile[] readProfiles(File file) { // TODO add a warnings consumer ??
        consumer.clearList();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = null;
            while ((line = br.readLine()) != null) {
                consumer.accept(line);
            }
            LOGGER.log(Level.INFO, GuiMessages.LOG_INFO_READER_FINISHED_PROCESSING + file.getName());
            return consumer.getProfiles();
        } catch (FileNotFoundException e) {
            LOGGER.log(Level.SEVERE, LOG_ERROR_ERROR_OPENING_FILE + file.getName(), e);
        } catch (IOException | IndexOutOfBoundsException e) {
            LOGGER.log(Level.SEVERE, LOG_ERROR_ERROR_READING_FILE + file.getName(), e);
        }
        return new ParsingProfile[0];
    }

}
