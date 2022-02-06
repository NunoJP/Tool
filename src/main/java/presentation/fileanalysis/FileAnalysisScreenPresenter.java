package presentation.fileanalysis;

import domain.entities.displayobjects.MetricsProfileDo;
import domain.entities.displayobjects.ParsingProfileDo;
import domain.entities.domainobjects.LogLine;
import domain.services.FileAnalysisService;
import presentation.common.GuiConstants;
import presentation.common.GuiMessages;
import presentation.common.IViewPresenter;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class FileAnalysisScreenPresenter implements IViewPresenter {
    private final File selectedFile;
    private final ParsingProfileDo parsingProfile;
    private final MetricsProfileDo metricsProfile;
    private final FileAnalysisScreen view;
    private final FileAnalysisService fileAnalysisService;
    private LogLine[] data;

    public FileAnalysisScreenPresenter(File selectedFile, ParsingProfileDo parsingProfile,
                                       MetricsProfileDo metricsProfile,
                                       FileAnalysisService fileAnalysisService) {
        view = new FileAnalysisScreen();
        this.fileAnalysisService = fileAnalysisService;
        fileAnalysisService.setLogMessageConsumer(this::messagePopup);
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
        data = fileAnalysisService.getData();
        view.getFileContentsTable().setData(convertDataForTable(data));
        addMessageCellSelectionEvent();
    }

    private Object[][] convertDataForTable(LogLine[] data) {
        Object[][] objects = new Object[data.length][];
        for (int i = 0; i <data.length; i++) {
            objects[i] = new Object[] {
                    getDate(data[i]), getTime(data[i]), data[i].getOrigin(),
                    data[i].getLevel(), data[i].getMessage()
            };
        }
        return objects;
    }

    private String getTime(LogLine datum) {
        DateFormat timeFormat = new SimpleDateFormat(GuiConstants.TIME_FORMATTER);
        if(datum.getTimestamp() == null) {
            if(datum.getTime() == null) {
                return "";
            } else {
                return timeFormat.format(datum.getTime());
            }
        }
        return timeFormat.format(datum.getTimestamp());
    }

    private String getDate(LogLine datum) {
        DateFormat dateFormat = new SimpleDateFormat(GuiConstants.DATE_FORMATTER);
        if(datum.getTimestamp() == null) {
            if (datum.getDate() == null) {
                return "";
            } else {
                return dateFormat.format(datum.getDate());
            }
        }
        return dateFormat.format(datum.getTimestamp());
    }

    private void messagePopup(String message) {
        JOptionPane.showMessageDialog(view,
                message,
                GuiMessages.MESSAGE_TITLE,
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void addMessageCellSelectionEvent() {
        view.getFileContentsTable().setLineSelectionOnly();
        view.getFileContentsTable().addRowSelectionEvent( (event) -> {
            ListSelectionModel listSelectionModel = (ListSelectionModel) event.getSource();
            if(listSelectionModel.isSelectionEmpty()) {
                view.getMessageDetailsTextArea().clearTextAreaText();
            } else {
                int selectedIndex = listSelectionModel.getSelectedIndices()[0];
                view.getMessageDetailsTextArea().setTextAreaText(data[selectedIndex].getMessage());
            }
        });
    }
}
