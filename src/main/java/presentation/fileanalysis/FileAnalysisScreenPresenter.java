package presentation.fileanalysis;

import domain.entities.Converter;
import domain.entities.displayobjects.FileAnalysisFilterDo;
import domain.entities.displayobjects.MetricsProfileDo;
import domain.entities.displayobjects.ParsingProfileDo;
import domain.entities.domainobjects.LogLine;
import domain.services.FileAnalysisService;
import general.util.DateTimeUtils;
import presentation.common.GuiConstants;
import presentation.common.GuiMessages;
import presentation.common.IViewPresenter;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import java.io.File;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import static javax.swing.JFileChooser.APPROVE_OPTION;
import static javax.swing.JFileChooser.FILES_ONLY;

public class FileAnalysisScreenPresenter implements IViewPresenter {
    private final File selectedFile;
    private final ParsingProfileDo parsingProfile;
    private final MetricsProfileDo metricsProfile;
    private final FileAnalysisScreen view;
    private final FileAnalysisService fileAnalysisService;
    private LogLine[] data;
    private Date fileStartDate;
    private Date fileStartTime;
    private Date fileEndDate;
    private Date fileEndTime;
    private LogLine[] filteredData;

    private static final Logger LOGGER = Logger.getLogger(FileAnalysisScreenPresenter.class.getName());

    public FileAnalysisScreenPresenter(File selectedFile, ParsingProfileDo parsingProfile,
                                       MetricsProfileDo metricsProfile,
                                       FileAnalysisService fileAnalysisService) {
        view = new FileAnalysisScreen();
        this.fileAnalysisService = fileAnalysisService;
        fileAnalysisService.setLogMessageConsumer(this::messagePopup);
        this.selectedFile = selectedFile;
        this.parsingProfile = parsingProfile;
        this.metricsProfile = metricsProfile;
        defineViewBehavior();
    }

    @Override
    public JPanel getView() {
        return view;
    }

    @Override
    public void execute() {
        data = fileAnalysisService.getData();
        updateFileContentsTableData(data);
        try {
            calculateFirstAndLastDateTime();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, GuiMessages.ERROR_PARSING_DATE, e);
        }

        setDefaultDateTimeFilters();
    }


    private void defineViewBehavior() {
        addMessageCellSelectionEvent();

        view.getSearchButton().addActionListener(actionEvent ->  {
//            fileAnalysisService.getStringPositionMatches(data, view.getMessage().getVariableLabelText());
        });

        view.getClearButton().addActionListener(actionEvent ->  {
            view.getLevel().setVariableLabelText("");
            view.getOrigin().setVariableLabelText("");
            view.getMessage().setVariableLabelText("");
            setDefaultDateTimeFilters();
            updateFileContentsTableData(data);
            try {
                calculateFirstAndLastDateTime();
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, GuiMessages.ERROR_PARSING_DATE, e);
            }
        });

        view.getFilterButton().addActionListener(actionEvent ->  {
            FileAnalysisFilterDo filterDo = new FileAnalysisFilterDo();
            filterDo.setLevel(view.getLevel().getVariableLabelText());
            filterDo.setIdentifier(view.getIdentifier().getVariableLabelText());
            filterDo.setOrigin(view.getOrigin().getVariableLabelText());
            filterDo.setMessage(view.getMessage().getVariableLabelText());
            filterDo.setStartDate(view.getFromDateComponent().getDate());
            filterDo.setStartTime(view.getFromDateComponent().getTime());
            filterDo.setEndDate(view.getToDateComponent().getDate());
            filterDo.setEndTime(view.getToDateComponent().getTime());
            filteredData = fileAnalysisService.getFilteredData(filterDo);
            updateFileContentsTableData(filteredData);
        });

        view.getExportButton().addActionListener(actionEvent ->  {
            final JFileChooser fc = new JFileChooser();
            // set Fc to open for the current directory
            fc.setCurrentDirectory(Paths.get("").toAbsolutePath().toFile());
            // allow for file or directory selection
            fc.setFileSelectionMode(FILES_ONLY);

            int returnVal = fc.showOpenDialog(view);
            if (APPROVE_OPTION == returnVal) {
                File exportFile = fc.getSelectedFile();
                if (!selectedFile.isDirectory()) {
                    if(filteredData == null || filteredData.length == 0) {
                        exportMessagePopup(fileAnalysisService.exportData(data, exportFile, Converter.toDomainObject(parsingProfile)));
                    } else {
                        exportMessagePopup(fileAnalysisService.exportData(filteredData, exportFile, Converter.toDomainObject(parsingProfile)));
                    }
                }
            }
        });
    }

    private void setDefaultDateTimeFilters() {
        if(fileStartDate != null) {
            view.getFromDateComponent().setDate(fileStartDate);
        }
        if(fileStartTime != null) {
            view.getFromDateComponent().setTime(fileStartTime);
        }
        if(fileEndDate != null) {
            view.getToDateComponent().setDate(fileEndDate);
        }
        if(fileEndTime != null) {
            view.getToDateComponent().setTime(fileEndTime);
        }
    }

    private void calculateFirstAndLastDateTime() throws ParseException {
        fileStartDate = DateTimeUtils.getDateFromLogLine(data[0]);
        fileStartTime = DateTimeUtils.getTimeFromLogLine(data[0]);
        fileEndDate = DateTimeUtils.getDateFromLogLine(data[data.length-1]);
        fileEndTime = DateTimeUtils.getTimeFromLogLine(data[data.length-1]);
    }


    private void updateFileContentsTableData(LogLine[] data) {
        view.getFileContentsTable().setData(convertDataForTable(data));
        view.resizeTableToFitContents();
    }

    private Object[][] convertDataForTable(LogLine[] data) {
        Object[][] objects = new Object[data.length][];
        for (int i = 0; i <data.length; i++) {
            objects[i] = new Object[] {
                    getDate(data[i]), getTime(data[i]), data[i].getIdentifier(),
                    data[i].getOrigin(), data[i].getLevel(), data[i].getMessage()
            };
        }
        return objects;
    }

    public static String getTime(LogLine datum) {
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

    public static String getDate(LogLine datum) {
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

    private void exportMessagePopup(boolean success) {
        if (success) {
            JOptionPane.showMessageDialog(view, GuiMessages.EXPORT_SUCCESSFUL, GuiMessages.MESSAGE_TITLE, JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(view, GuiMessages.EXPORT_FAILED, GuiMessages.MESSAGE_TITLE, JOptionPane.ERROR_MESSAGE);
        }
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
