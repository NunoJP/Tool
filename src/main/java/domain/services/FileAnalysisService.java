package domain.services;

import domain.entities.displayobjects.MetricsProfileDo;
import domain.entities.displayobjects.ParsingProfileDo;
import domain.entities.domainobjects.LogLine;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

public class FileAnalysisService {
    private final File selectedFile;
    private final ParsingProfileDo parsingProfile;
    private final MetricsProfileDo metricsProfile;
    Calendar calendar = Calendar.getInstance();
    Date initDate = calendar.getTime();

    public FileAnalysisService(File selectedFile, ParsingProfileDo parsingProfile, MetricsProfileDo metricsProfile) {
        this.selectedFile = selectedFile;
        this.parsingProfile = parsingProfile;
        this.metricsProfile = metricsProfile;

    }

    public LogLine[] getData() {
        LogLine line = new LogLine(initDate, "Error", "Origin", "MEsages as dasdasd asd asd");
        return new LogLine[] { line };
    }
}
