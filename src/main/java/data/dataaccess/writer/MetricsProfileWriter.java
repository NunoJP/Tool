package data.dataaccess.writer;

import data.dataaccess.memory.MetricsProfilesMemoryRepository;
import domain.entities.domainobjects.MetricsProfile;
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

import static data.dataaccess.common.MetricsProfileReadWriteConstants.DEFAULT_METRICS_PROFILE_FILE_NAME;
import static data.dataaccess.common.MetricsProfileReadWriteConstants.DEFAULT_METRICS_PROFILE_FOLDER_NAME;
import static presentation.common.GuiMessages.LOG_ERROR_ERROR_CREATING_FILE;
import static presentation.common.GuiMessages.LOG_ERROR_ERROR_OPENING_FILE;
import static presentation.common.GuiMessages.LOG_ERROR_ERROR_READING_FILE;

public class MetricsProfileWriter {
    private final MetricsProfilesMemoryRepository instance;
    private final String parsingProfileFolderName;
    private MetricsProfileFunction function;

    private static final Logger LOGGER = Logger.getLogger(MetricsProfileWriter.class.getName());


    public MetricsProfileWriter(String parsingProfileFolderName) {
        this.parsingProfileFolderName = parsingProfileFolderName;
        this.instance = MetricsProfilesMemoryRepository.getInstance();
    }

    public MetricsProfileWriter() {
        this(DEFAULT_METRICS_PROFILE_FOLDER_NAME);
    }

    public boolean createProfile(MetricsProfile metricsProfile) {
        if(instance.createProfile(metricsProfile, DEFAULT_METRICS_PROFILE_FILE_NAME)) {
            return writeProfile(metricsProfile,
                    metricsProfile.getOriginFile() == null ? DEFAULT_METRICS_PROFILE_FILE_NAME :
                    metricsProfile.getOriginFile());
        }
        return false;
    }

    public boolean updateProfile(MetricsProfile metricsProfile) {
        String originFile = metricsProfile.getOriginFile();
        if(instance.updateProfile(metricsProfile)) {
            return writeProfiles(instance.getMetricsProfilesByOriginFile(originFile), originFile);
        }
        return false;
    }

    public boolean deleteProfile(MetricsProfile metricsProfile) {
        String originFile = metricsProfile.getOriginFile();
        if(instance.deleteProfile(metricsProfile)) {
            return writeProfiles(instance.getMetricsProfilesByOriginFile(originFile), originFile);
        }
        return false;
    }

    private boolean writeProfile(MetricsProfile metricsProfile, String originFile) {
        Path currentRelativePath = Paths.get(parsingProfileFolderName + File.separator + originFile);
        File file = currentRelativePath.toAbsolutePath().toFile();
        try {
            function = new MetricsProfileFunction(file.createNewFile()); // if the file exists we don't need to add the first line
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, LOG_ERROR_ERROR_CREATING_FILE + file.getName(), e);
        }

        try (BufferedWriter wr = new BufferedWriter(new FileWriter(file, true))) {
            String line = function.apply(metricsProfile);
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

    private boolean writeProfiles(ArrayList<MetricsProfile> metricsProfiles, String originFile) {
        Path currentRelativePath = Paths.get(parsingProfileFolderName + File.separator + originFile);
        File file = currentRelativePath.toAbsolutePath().toFile();
        function = new MetricsProfileFunction(true); // if the file exists we don't need to add the first line

        try (BufferedWriter wr = new BufferedWriter(new FileWriter(file))) {
            for (MetricsProfile metricsProfile : metricsProfiles) {
                String line = function.apply(metricsProfile);
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
}
