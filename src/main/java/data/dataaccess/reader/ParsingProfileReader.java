package data.dataaccess.reader;

import data.dataaccess.memory.ParsingProfilesMemoryRepository;
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

import static data.dataaccess.common.ParsingProfileReadWriteConstants.DEFAULT_PARSING_PROFILE_FOLDER_NAME;
import static presentation.common.GuiMessages.ERROR_FOLDER_DOES_NOT_EXIST;
import static presentation.common.GuiMessages.LOG_ERROR_ERROR_OPENING_FILE;
import static presentation.common.GuiMessages.LOG_ERROR_ERROR_READING_FILE;

public class ParsingProfileReader {

    private static final Logger LOGGER = Logger.getLogger(ParsingProfileReader.class.getName());
    private final ParsingProfilesMemoryRepository instance;

    private final ParsingProfileConsumer consumer = new ParsingProfileConsumer();
    private final String parsingProfileFolderName;

    public ParsingProfileReader(String parsingProfileFolderName) {
        this.parsingProfileFolderName = parsingProfileFolderName;
        instance = ParsingProfilesMemoryRepository.getInstance();
    }

    public ParsingProfileReader() {
        this.parsingProfileFolderName = DEFAULT_PARSING_PROFILE_FOLDER_NAME;
        instance = ParsingProfilesMemoryRepository.getInstance();
    }

    public ParsingProfile[] getParsingProfiles() {
        Path currentRelativePath = Paths.get(parsingProfileFolderName);
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
            String line;
            while ((line = br.readLine()) != null) {
                consumer.accept(line);
            }
            LOGGER.log(Level.INFO, GuiMessages.LOG_INFO_READER_FINISHED_PROCESSING + file.getName());

            // As the users may add their own files, we need to know where each profile comes from
            ParsingProfile[] profiles = consumer.getProfiles();
            for (ParsingProfile profile : profiles) {
                profile.setOriginFile(file.getName());
            }
            return profiles;
        } catch (FileNotFoundException e) {
            LOGGER.log(Level.SEVERE, LOG_ERROR_ERROR_OPENING_FILE + file.getName(), e);
        } catch (IOException | IndexOutOfBoundsException e) {
            LOGGER.log(Level.SEVERE, LOG_ERROR_ERROR_READING_FILE + file.getName(), e);
        }
        return new ParsingProfile[0];
    }

}
