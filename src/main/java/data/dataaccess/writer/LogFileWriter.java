package data.dataaccess.writer;

import domain.entities.domainobjects.LogLine;
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

public class LogFileWriter {


    private static final Logger LOGGER = Logger.getLogger(LogFileWriter.class.getName());

    public boolean write(LogLine[] data, File exportFile) {
        try {
            exportFile.createNewFile(); // if the file exists we don't need to add the first line
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, LOG_ERROR_ERROR_CREATING_FILE + exportFile.getName(), e);
        }

        try (BufferedWriter wr = new BufferedWriter(new FileWriter(exportFile, false))) {
            for (LogLine datum : data) { // TODO: Change this to match the parsing profile
                StringBuilder line = new StringBuilder();
                line.append(datum.getDate()).append(";")
                        .append(datum.getTime()).append(";")
                        .append(datum.getIdentifier()).append(";")
                        .append(datum.getLevel()).append(";")
                        .append(datum.getOrigin()).append(";")
                        .append(datum.getMessage()).append(";");
                wr.append(line);
            }
            // TODO: Change the messages to match the class
            LOGGER.log(Level.INFO, GuiMessages.LOG_INFO_WRITER_FINISHED_PROCESSING + exportFile.getName());
            return true;
        } catch (FileNotFoundException e) {
            LOGGER.log(Level.SEVERE, LOG_ERROR_ERROR_OPENING_FILE + exportFile.getName(), e);
        } catch (IOException | IndexOutOfBoundsException e) {
            LOGGER.log(Level.SEVERE, LOG_ERROR_ERROR_READING_FILE + exportFile.getName(), e);
        }
        return false;
    }


}
