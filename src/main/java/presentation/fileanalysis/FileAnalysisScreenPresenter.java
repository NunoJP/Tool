package presentation.fileanalysis;

import domain.entities.displayobjects.MetricsProfileDo;
import domain.entities.displayobjects.ParsingProfileDo;
import domain.entities.domainobjects.LogLine;
import domain.services.FileAnalysisService;
import presentation.common.GuiConstants;
import presentation.common.IViewPresenter;

import javax.swing.JPanel;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class FileAnalysisScreenPresenter implements IViewPresenter {
    private final File selectedFile;
    private final ParsingProfileDo parsingProfile;
    private final MetricsProfileDo metricsProfile;
    private final FileAnalysisScreen view;
    private final FileAnalysisService fileAnalysisService;

    public FileAnalysisScreenPresenter(File selectedFile, ParsingProfileDo parsingProfile,
                                       MetricsProfileDo metricsProfile,
                                       FileAnalysisService fileAnalysisService) {
        view = new FileAnalysisScreen();
        this.fileAnalysisService = fileAnalysisService;
        this.selectedFile = selectedFile;
        this.parsingProfile = parsingProfile;
        this.metricsProfile = metricsProfile;
    }

    @Override
    public JPanel getView() {
        return view;
    }

    @Override
    public void execute() {
        LogLine[] data = fileAnalysisService.getData();
        view.getFileContentsTable().setData(convertDataForTable(data));
    }

    private Object[][] convertDataForTable(LogLine[] data) {
        DateFormat dateFormat = new SimpleDateFormat(GuiConstants.DATE_FORMATTER);
        DateFormat timeFormat = new SimpleDateFormat(GuiConstants.TIME_FORMATTER);
        Object[][] objects = new Object[data.length][];
        for (int i = 0; i <data.length; i++) {
            objects[i] = new Object[] {
                    getDate(dateFormat, data[i]), getDate(timeFormat, data[i]), data[i].getOrigin(),
                    data[i].getLevel(), data[i].getMessage()
            };
        }
        return objects;
    }

    private String getDate(DateFormat dateFormat, LogLine datum) {
        if(datum.getDate() == null) {
            return "";
        }
        return dateFormat.format(datum.getDate());
    }


}
