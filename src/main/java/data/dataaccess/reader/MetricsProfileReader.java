package data.dataaccess.reader;

import data.dataaccess.memory.MetricsProfilesMemoryRepository;
import domain.entities.domainobjects.MetricsProfile;
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

import static data.dataaccess.common.MetricsProfileReadWriteConstants.DEFAULT_METRICS_PROFILE_FOLDER_NAME;
import static presentation.common.GuiMessages.ERROR_FOLDER_DOES_NOT_EXIST;
import static presentation.common.GuiMessages.LOG_ERROR_ERROR_OPENING_FILE;
import static presentation.common.GuiMessages.LOG_ERROR_ERROR_READING_FILE;

public class MetricsProfileReader {

    private static final Logger LOGGER = Logger.getLogger(MetricsProfileReader.class.getName());
    private final MetricsProfilesMemoryRepository instance;

    private final MetricsProfileConsumer consumer = new MetricsProfileConsumer();
    private final String metricsProfileFolderName;

    public MetricsProfileReader(String metricsProfileFolderName) {
        this.metricsProfileFolderName = metricsProfileFolderName;
        this.instance = MetricsProfilesMemoryRepository.getInstance();
    }

    public MetricsProfileReader() {
        this.metricsProfileFolderName = DEFAULT_METRICS_PROFILE_FOLDER_NAME;
        this.instance = MetricsProfilesMemoryRepository.getInstance();
    }

    public MetricsProfile[] getProfiles() {
        Path currentRelativePath = Paths.get(metricsProfileFolderName);
        File f = currentRelativePath.toAbsolutePath().toFile();
        ArrayList<MetricsProfile> accumulator = new ArrayList<>();
        if (f.exists() && f.isDirectory()) {
            File [] files = f.listFiles();
            if (files == null) {
                return new MetricsProfile[0];
            } else {
                for (File file : files) {
                    accumulator.addAll(Arrays.asList(readProfiles(file)));
                }
            }
        } else {
            throw new InvalidParameterException(ERROR_FOLDER_DOES_NOT_EXIST);
        }
        instance.createMetricsProfiles(accumulator);
        return instance.getMetricsProfiles().toArray(new MetricsProfile[0]);
    }

    private MetricsProfile[] readProfiles(File file) {
        consumer.clearList();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                consumer.accept(line);
            }
            LOGGER.log(Level.INFO, GuiMessages.LOG_INFO_READER_FINISHED_PROCESSING + file.getName());

            // As the users may add their own files, we need to know where each profile comes from
            MetricsProfile[] profiles = consumer.getProfiles();
            for (MetricsProfile profile : profiles) {
                profile.setOriginFile(file.getName());
            }
            return profiles;
        } catch (FileNotFoundException e) {
            LOGGER.log(Level.SEVERE, LOG_ERROR_ERROR_OPENING_FILE + file.getName(), e);
        } catch (IOException | IndexOutOfBoundsException e) {
            LOGGER.log(Level.SEVERE, LOG_ERROR_ERROR_READING_FILE + file.getName(), e);
        }
        return new MetricsProfile[0];
    }


}
