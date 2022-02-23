package data.dataaccess.reader;

import data.dataaccess.common.ToolConfigurationReadWriteConstants;
import domain.entities.common.ToolConfiguration;
import presentation.common.GuiMessages;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidParameterException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static presentation.common.GuiMessages.ERROR_FOLDER_DOES_NOT_EXIST;
import static presentation.common.GuiMessages.LOG_ERROR_ERROR_OPENING_FILE;
import static presentation.common.GuiMessages.LOG_ERROR_ERROR_READING_FILE;

public class ToolConfigurationReader {

    private static final Logger LOGGER = Logger.getLogger(ToolConfigurationReader.class.getName());

    public ToolConfigurationReader() {
        // do nothing
    }

    public ToolConfiguration read() {
        Path currentRelativePath = Paths.get(ToolConfigurationReadWriteConstants.DEFAULT_TOOL_CONFIGURATION_PROFILE_PATH);
        File file = currentRelativePath.toAbsolutePath().toFile();
        ToolConfigurationConsumer consumer = new ToolConfigurationConsumer();

        if (file.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    consumer.accept(line);
                }
                LOGGER.log(Level.INFO, GuiMessages.LOG_INFO_READER_FINISHED_PROCESSING + file.getName());
                return consumer.getToolConfiguration();
            } catch (FileNotFoundException e) {
                LOGGER.log(Level.SEVERE, LOG_ERROR_ERROR_OPENING_FILE + file.getName(), e);
            } catch (IOException | IndexOutOfBoundsException e) {
                LOGGER.log(Level.SEVERE, LOG_ERROR_ERROR_READING_FILE + file.getName(), e);
            }
        } else {
            throw new InvalidParameterException(ERROR_FOLDER_DOES_NOT_EXIST);
        }

        return new ToolConfiguration();
    }

}
