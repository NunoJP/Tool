package data.dataaccess.reader;

import domain.entities.domainobjects.LogLine;
import domain.entities.domainobjects.ParsingProfile;
import presentation.common.GuiMessages;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static presentation.common.GuiMessages.LOG_ERROR_ERROR_OPENING_FILE;
import static presentation.common.GuiMessages.LOG_ERROR_ERROR_READING_FILE;

public class LogFileReader {
    private static final Logger LOGGER = Logger.getLogger(LogFileReader.class.getName());
    private final File selectedFile;
    private final ParsingProfile parsingProfile;
    private final LogFileReaderConsumer consumer;


    public LogFileReader(File selectedFile, ParsingProfile parsingProfile) {
        this.selectedFile = selectedFile;
        this.parsingProfile = parsingProfile;
        this.consumer = new LogFileReaderConsumer(parsingProfile);
    }

    public LogLine[] read() {
        try (BufferedReader br = new BufferedReader(new FileReader(selectedFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                consumer.accept(line);
            }
            LOGGER.log(Level.INFO, GuiMessages.LOG_INFO_READER_FINISHED_PROCESSING + selectedFile.getName());
            return consumer.getLines();
        } catch (FileNotFoundException e) {
            LOGGER.log(Level.SEVERE, LOG_ERROR_ERROR_OPENING_FILE + selectedFile.getName(), e);
        } catch (IOException | IndexOutOfBoundsException e) {
            LOGGER.log(Level.SEVERE, LOG_ERROR_ERROR_READING_FILE + selectedFile.getName(), e);
        }
        return new LogLine[0];
    }

}
