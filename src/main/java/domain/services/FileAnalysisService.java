package domain.services;

import data.dataaccess.reader.LogFileReader;
import data.dataaccess.writer.LogFileWriter;
import domain.entities.displayobjects.FileAnalysisFilterDo;
import domain.entities.domainobjects.LogLine;
import domain.entities.domainobjects.MetricsProfile;
import domain.entities.domainobjects.ParsingProfile;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.function.Consumer;

public class FileAnalysisService {
    protected final File selectedFile;
    protected final ParsingProfile parsingProfile;
    protected final MetricsProfile metricsProfile;
    protected LogFileReader logFileReader;
    private boolean hasLoadedData;
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

    public LogLine[] getFilteredData(FileAnalysisFilterDo filterDo) {
        ArrayList<LogLine> filteredLines = new ArrayList<>();
        if(!hasLoadedData) {
            data = getData();
        }
        for (LogLine logLine : data) {
            if (lineMatchesFilter(logLine, filterDo)) {
                filteredLines.add(logLine);
            }
        }
        return filteredLines.toArray(new LogLine[0]);
    }

    public void setLogMessageConsumer(Consumer<String> logMessageConsumer) {
        this.logMessageConsumer = logMessageConsumer;
    }

    protected boolean lineMatchesFilter(LogLine line, FileAnalysisFilterDo filterDo) {
        if(!checkStringMatches(filterDo.getIdentifier(), line.getIdentifier())) {
            return false;
        }
        if(!checkStringMatches(filterDo.getLevel(), line.getLevel())) {
            return false;
        }
        if(!checkStringMatches(filterDo.getOrigin(), line.getOrigin())) {
            return false;
        }
        if(!checkStringMatches(filterDo.getMessage(), line.getMessage())) {
            return false;
        }
        if(!checkDateTimeIsBefore(filterDo.getStartDate(), line.getDate())) {
            return false;
        }
        if(!checkDateTimeIsBefore(filterDo.getStartTime(), line.getTime())) {
            return false;
        }
        if(!checkDateTimeIsAfter(filterDo.getEndDate(), line.getDate())) {
            return false;
        }
        if(!checkDateTimeIsAfter(filterDo.getEndTime(), line.getTime())) {
            return false;
        }
        return true;
    }

    protected boolean checkStringMatches(String filter, String value) {
        if(filter != null && !filter.isEmpty() && value != null && !value.isEmpty()) {
            return value.contains(filter);
        }
        return true;
    }

    protected boolean checkDateTimeIsBefore(Date filter, Date value) {
        if(filter != null && value != null) {
            return filter.toInstant().isBefore(value.toInstant()) || filter.toInstant().equals(value.toInstant());
        }
        return true;
    }

    protected boolean checkDateTimeIsAfter(Date filter, Date value) {
        if(filter != null && value != null) {
            return filter.toInstant().isAfter(value.toInstant()) || filter.toInstant().equals(value.toInstant());
        }
        return true;
    }

    public boolean exportData(LogLine[] data, File exportFile) {
        LogFileWriter writer = new LogFileWriter();
        return writer.write(data, exportFile);
    }
}
