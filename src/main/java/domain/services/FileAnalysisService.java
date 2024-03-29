package domain.services;

import data.dataaccess.reader.LogFileReader;
import data.dataaccess.writer.LogFileWriter;
import domain.entities.common.ParsingProfilePortion;
import domain.entities.common.SearchResultLine;
import domain.entities.common.TextClassesEnum;
import domain.entities.displayobjects.FileAnalysisFilterDo;
import domain.entities.domainobjects.LogLine;
import domain.entities.domainobjects.MetricsProfile;
import domain.entities.domainobjects.ParsingProfile;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class FileAnalysisService {
    protected final File selectedFile;
    protected final ParsingProfile parsingProfile;
    protected final MetricsProfile metricsProfile;
    protected LogFileReader logFileReader;
    private boolean hasLoadedData;
    private LogLine[] data;
    private Consumer<String> logMessageConsumer = s -> {};
    private static final Logger LOGGER = Logger.getLogger(FileAnalysisService.class.getName());

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
        generateSearchStructure(data);
        return data;
    }

    private void generateSearchStructure(LogLine[] data) {
        if(!isMessageIgnored()) {
            Arrays.stream(data).parallel().forEach(LogLine::calculateSearchStructure);
        }
    }

    private boolean isMessageIgnored() {
        if(parsingProfile == null) {
            LOGGER.log(Level.WARNING, "Parsing profile was null");
            return true;
        }

        for (ParsingProfilePortion portion : parsingProfile.getPortions()) {
            if(!portion.isSeparator()) {
                if(portion.getPortionName().equals(TextClassesEnum.MESSAGE.getName())) {
                    return portion.isIgnore();
                }
            }
        }
        return true;
    }

    public List<SearchResultLine> getStringPositionMatches(LogLine[] source, String toFind) {
        return Arrays.stream(source).parallel().map(logLine -> {
            Integer[] indexes = logLine.getSearchStructure().searchStringIndexes(toFind, true).toArray(Integer[]::new);
            if(indexes.length > 0) {
                return new SearchResultLine(logLine.getPosition(), indexes);
            }
            return null;
        }).filter(Objects::nonNull).collect(Collectors.toList());
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

    public boolean exportData(LogLine[] data, File exportFile, ParsingProfile parsingProfile) {
        LogFileWriter writer = new LogFileWriter();
        return writer.write(data, exportFile, parsingProfile);
    }

    public void stopReader() {
        logFileReader.stop();
    }
}
