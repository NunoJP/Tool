package data.dataaccess.reader;

import domain.entities.domainobjects.LogLine;
import domain.entities.domainobjects.ParsingProfile;
import general.util.Pair;
import presentation.common.GuiMessages;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

import static presentation.common.GuiMessages.LOG_ERROR_ERROR_OPENING_FILE;
import static presentation.common.GuiMessages.LOG_ERROR_ERROR_READING_FILE;

public class LogFileReader {
    private static final Logger LOGGER = Logger.getLogger(LogFileReader.class.getName());
    private final File selectedFile;
    private final LogFileReaderConsumer consumer;
    private AtomicBoolean keepReading = new AtomicBoolean(true);

    public LogFileReader(File selectedFile, ParsingProfile parsingProfile) {
        this.selectedFile = selectedFile;
        this.consumer = new LogFileReaderConsumer(parsingProfile);
    }

    public LogLine[] read(Consumer<String> logMessageConsumer) {
        try (BufferedReader br = new BufferedReader(new FileReader(selectedFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                consumer.accept(line);
            }
            LOGGER.log(Level.INFO, GuiMessages.LOG_INFO_READER_FINISHED_PROCESSING + selectedFile.getName());
            return consumer.getLines();
        } catch (FileNotFoundException e) {
            LOGGER.log(Level.SEVERE, LOG_ERROR_ERROR_OPENING_FILE + selectedFile.getName(), e);
            logMessageConsumer.accept(LOG_ERROR_ERROR_OPENING_FILE + selectedFile.getName());
        } catch (IOException | IndexOutOfBoundsException e) {
            LOGGER.log(Level.SEVERE, LOG_ERROR_ERROR_READING_FILE + selectedFile.getName(), e);
            logMessageConsumer.accept(LOG_ERROR_ERROR_READING_FILE + selectedFile.getName());
        }
        return new LogLine[0];
    }

    public void readDynamic(Consumer<String> logMessageConsumer, DynamicLogFileReaderConsumer logLineConsumer) {
        try (BufferedReader br = new BufferedReader(new FileReader(selectedFile))) {
            String line = null;
            while (keepReading.get() && (line = br.readLine()) != null) {
                if(!line.isEmpty()) {
                    logLineConsumer.accept(Pair.of(line, selectedFile.length()));
                }
            }
            LOGGER.log(Level.INFO, GuiMessages.LOG_INFO_READER_FINISHED_PROCESSING + selectedFile.getName());

        } catch (FileNotFoundException e) {
            LOGGER.log(Level.SEVERE, LOG_ERROR_ERROR_OPENING_FILE + selectedFile.getName(), e);
            logMessageConsumer.accept(LOG_ERROR_ERROR_OPENING_FILE + selectedFile.getName());
        } catch (IOException | IndexOutOfBoundsException e) {
            LOGGER.log(Level.SEVERE, LOG_ERROR_ERROR_READING_FILE + selectedFile.getName(), e);
            logMessageConsumer.accept(LOG_ERROR_ERROR_READING_FILE + selectedFile.getName());
        }
    }


    public void stop() {
        keepReading.set(false);
    }
}
