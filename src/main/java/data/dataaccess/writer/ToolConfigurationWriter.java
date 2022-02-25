package data.dataaccess.writer;

import presentation.common.GuiMessages;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static presentation.common.GuiMessages.LOG_ERROR_ERROR_CREATING_FILE;
import static presentation.common.GuiMessages.LOG_ERROR_ERROR_OPENING_FILE;
import static presentation.common.GuiMessages.LOG_ERROR_ERROR_READING_FILE;

public class ToolConfigurationWriter {

    private static final Logger LOGGER = Logger.getLogger(ToolConfigurationWriter.class.getName());

    public boolean write(File configFile) {
        ToolConfigurationFileSupplier supplier = new ToolConfigurationFileSupplier();
        try {
            configFile.createNewFile(); // if the file exists we don't need to add the first line
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, LOG_ERROR_ERROR_CREATING_FILE + configFile.getName(), e);
        }

        try (BufferedWriter wr = new BufferedWriter(new FileWriter(configFile, false))) {
            wr.append(supplier.get());
            LOGGER.log(Level.INFO, GuiMessages.LOG_INFO_WRITER_FINISHED_PROCESSING + configFile.getName());
            return true;
        } catch (FileNotFoundException e) {
            LOGGER.log(Level.SEVERE, LOG_ERROR_ERROR_OPENING_FILE + configFile.getName(), e);
        } catch (IOException | IndexOutOfBoundsException e) {
            LOGGER.log(Level.SEVERE, LOG_ERROR_ERROR_READING_FILE + configFile.getName(), e);
        }
        return false;
    }
}
