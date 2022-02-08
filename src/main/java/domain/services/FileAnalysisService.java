package domain.services;

import data.dataaccess.reader.LogFileReader;
import domain.entities.domainobjects.LogLine;
import domain.entities.domainobjects.MetricsProfile;
import domain.entities.domainobjects.ParsingProfile;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.function.Consumer;

public class FileAnalysisService {
    private final File selectedFile;
    private final ParsingProfile parsingProfile;
    private final MetricsProfile metricsProfile;
    private final LogFileReader logFileReader;
    private boolean hasLoadedData;
    private Calendar calendar = Calendar.getInstance();
    private Date initDate = calendar.getTime();
    private LogLine[] data;
    private Consumer<String> logMessageConsumer = s -> {};

    public FileAnalysisService(File selectedFile, ParsingProfile parsingProfile, MetricsProfile metricsProfile) {
        this.selectedFile = selectedFile;
        this.parsingProfile = parsingProfile;
        this.metricsProfile = metricsProfile;
        this.logFileReader = new LogFileReader(selectedFile, parsingProfile);
        hasLoadedData = false;
    }

    public LogLine[] getData() {
        if(hasLoadedData) {
            return data;
        }
        hasLoadedData = true;
        data = logFileReader.read(logMessageConsumer);
        return data;
    }

    public void setLogMessageConsumer(Consumer<String> logMessageConsumer) {
        this.logMessageConsumer = logMessageConsumer;
    }
}
