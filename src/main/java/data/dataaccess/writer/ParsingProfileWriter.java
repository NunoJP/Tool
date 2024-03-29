package data.dataaccess.writer;

import data.dataaccess.memory.ParsingProfilesMemoryRepository;
import domain.entities.domainobjects.ParsingProfile;
import presentation.common.GuiMessages;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import static data.dataaccess.common.ParsingProfileReadWriteConstants.DEFAULT_PARSING_PROFILE_FILE_NAME;
import static data.dataaccess.common.ParsingProfileReadWriteConstants.DEFAULT_PARSING_PROFILE_FOLDER_NAME;
import static presentation.common.GuiMessages.LOG_ERROR_ERROR_CREATING_FILE;
import static presentation.common.GuiMessages.LOG_ERROR_ERROR_OPENING_FILE;
import static presentation.common.GuiMessages.LOG_ERROR_ERROR_READING_FILE;

public class ParsingProfileWriter {
    private final ParsingProfilesMemoryRepository instance;
    private final String parsingProfileFolderName;
    private ParsingProfileFunction function;

    private static final Logger LOGGER = Logger.getLogger(ParsingProfileWriter.class.getName());

    public ParsingProfileWriter(String parsingProfileFolderName) {
        this.parsingProfileFolderName = parsingProfileFolderName;
        instance = ParsingProfilesMemoryRepository.getInstance();
    }

    public ParsingProfileWriter() {
        this.parsingProfileFolderName = DEFAULT_PARSING_PROFILE_FOLDER_NAME;
        instance = ParsingProfilesMemoryRepository.getInstance();
    }

    public boolean createProfile(ParsingProfile parsingProfile) {
        if(instance.createProfile(parsingProfile, DEFAULT_PARSING_PROFILE_FILE_NAME)) {
            return writeProfile(parsingProfile,
                    parsingProfile.getOriginFile() == null ? DEFAULT_PARSING_PROFILE_FILE_NAME : parsingProfile.getOriginFile());
        }
        return false;
    }

    public boolean updateProfile(ParsingProfile parsingProfile) {
        String originFile = parsingProfile.getOriginFile();
        if(instance.updateProfile(parsingProfile)) {
            return writeProfiles(instance.getParsingProfilesByOriginFile(originFile), originFile);
        }
        return false;
    }

    public boolean deleteProfile(ParsingProfile parsingProfile) {
        String originFile = parsingProfile.getOriginFile();
        if(instance.deleteProfile(parsingProfile)) {
            return writeProfiles(instance.getParsingProfilesByOriginFile(originFile), originFile);
        }
        return false;
    }

    private boolean writeProfiles(ArrayList<ParsingProfile> parsingProfilesByOriginFile, String originFile) {
        Path currentRelativePath = Paths.get(parsingProfileFolderName + File.separator + originFile);
        File file = currentRelativePath.toAbsolutePath().toFile();
        function = new ParsingProfileFunction(true); // if the file exists we don't need to add the first line

        try (BufferedWriter wr = new BufferedWriter(new FileWriter(file))) {
            for (ParsingProfile parsingProfile : parsingProfilesByOriginFile) {
                String line = function.apply(parsingProfile);
                wr.append(line);
                function.setIsNewFile(false);
            }
            LOGGER.log(Level.INFO, GuiMessages.LOG_INFO_WRITER_FINISHED_PROCESSING + file.getName());
            return true;
        } catch (FileNotFoundException e) {
            LOGGER.log(Level.SEVERE, LOG_ERROR_ERROR_OPENING_FILE + file.getName(), e);
        } catch (IOException | IndexOutOfBoundsException e) {
            LOGGER.log(Level.SEVERE, LOG_ERROR_ERROR_READING_FILE + file.getName(), e);
        }
        return false;
    }

    private boolean writeProfile(ParsingProfile parsingProfile, String defaultParsingProfileFileName) {
        Path currentRelativePath = Paths.get(parsingProfileFolderName + File.separator + defaultParsingProfileFileName);
        File file = currentRelativePath.toAbsolutePath().toFile();
        try {
            function = new ParsingProfileFunction(file.createNewFile()); // if the file exists we don't need to add the first line
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, LOG_ERROR_ERROR_CREATING_FILE + file.getName(), e);
        }

        try (BufferedWriter wr = new BufferedWriter(new FileWriter(file, true))) {
            String line = function.apply(parsingProfile);
            wr.append(line);
            LOGGER.log(Level.INFO, GuiMessages.LOG_INFO_WRITER_FINISHED_PROCESSING + file.getName());
            return true;
        } catch (FileNotFoundException e) {
            LOGGER.log(Level.SEVERE, LOG_ERROR_ERROR_OPENING_FILE + file.getName(), e);
        } catch (IOException | IndexOutOfBoundsException e) {
            LOGGER.log(Level.SEVERE, LOG_ERROR_ERROR_READING_FILE + file.getName(), e);
        }
        return false;
    }

}
