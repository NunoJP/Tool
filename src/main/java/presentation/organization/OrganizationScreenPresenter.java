package presentation.organization;

import domain.entities.displayobjects.MetricsProfileDo;
import domain.entities.displayobjects.ParsingProfileDo;
import domain.services.MetricsProfileManagementService;
import domain.services.OrganizationService;
import domain.services.ParsingProfileManagementService;
import presentation.common.GuiMessages;
import presentation.common.IViewPresenter;
import presentation.common.custom.CellRenderer;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static javax.swing.JFileChooser.APPROVE_OPTION;
import static javax.swing.JFileChooser.DIRECTORIES_ONLY;

public class OrganizationScreenPresenter implements IViewPresenter {

    private final OrganizationScreen view;
    private final ParsingProfileManagementService parsingService;
    private final MetricsProfileManagementService metricsService;
    private final OrganizationService organizationService;
    private ParsingProfileDo[] parsingProfiles;
    private MetricsProfileDo[] metricsProfiles;
    private File sourceFolder;
    private File targetFolder;
    private List<File> sourceFiles = new ArrayList<>();
    private int[] selectedIndexes = new int[0];

    public OrganizationScreenPresenter() {
        view = new OrganizationScreen();
        parsingService = new ParsingProfileManagementService();
        metricsService = new MetricsProfileManagementService();
        organizationService = new OrganizationService();
        defineViewBehavior();
    }

    private void defineViewBehavior() {
        refreshComboBoxes();

        view.getChooseSourceFolderButton().addActionListener(actionEvent -> {
            final JFileChooser fc = new JFileChooser();
            // set Fc to open for the current directory
            fc.setCurrentDirectory(Paths.get("").toAbsolutePath().toFile());
            // allow for file or directory selection
            fc.setFileSelectionMode(DIRECTORIES_ONLY);

            int returnVal = fc.showOpenDialog(view);
            if (APPROVE_OPTION == returnVal) {
                sourceFolder = fc.getSelectedFile();
                if (sourceFolder.isDirectory()) {
                    view.getSourceFolderName().setVariableLabelText(sourceFolder.getName());
                    File[] array = sourceFolder.listFiles();
                    if(array != null) {
                        view.getSourceFolderTable().setData(formatFileNames(array));
                        sourceFiles = Arrays.stream(array).filter(file -> !file.isDirectory()).collect(Collectors.toList());
                    }
                }
            }
        });

        view.getChooseTargetFolderButton().addActionListener(actionEvent -> {
            final JFileChooser fc = new JFileChooser();
            // set Fc to open for the current directory
            fc.setCurrentDirectory(Paths.get("").toAbsolutePath().toFile());
            // allow for file or directory selection
            fc.setFileSelectionMode(DIRECTORIES_ONLY);

            int returnVal = fc.showOpenDialog(view);
            if (APPROVE_OPTION == returnVal) {
                targetFolder = fc.getSelectedFile();
                if (targetFolder.isDirectory()) {
                    view.getTargetFolderName().setVariableLabelText(targetFolder.getName());
                    view.getTargetFolderTable().setData(formatFileNames(targetFolder.listFiles()));
                }
            }
        });

        view.getCopyButton().addActionListener(actionEvent -> {
            ParsingProfileDo parsingProfile = (ParsingProfileDo) view.getParsingProfileDropdown().getSelectedItem();
            MetricsProfileDo metricsProfile = (MetricsProfileDo) view.getMetricsProfileDropdown().getSelectedItem();
            view.getCopyButton().setEnabled(false);
            view.getMoveButton().setEnabled(false);
            ArrayList<File> selectedFiles = getSelectedFiles();
            if(selectedFiles.isEmpty()) {
                messagePopup(GuiMessages.NO_FILES_SELECTED, JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            boolean success = organizationService.copyFilesThatMatch(selectedFiles, targetFolder, parsingProfile, metricsProfile);
            if(success) {
                messagePopup(GuiMessages.FILE_COPY_WAS_SUCCESSFUL, JOptionPane.INFORMATION_MESSAGE);
                view.getTargetFolderTable().setData(formatFileNames(targetFolder.listFiles()));
            } else {
                messagePopup(GuiMessages.FILE_COPY_FAILED, JOptionPane.ERROR_MESSAGE);
            }
        });

        view.getMoveButton().addActionListener(actionEvent -> {
            ParsingProfileDo parsingProfile = (ParsingProfileDo) view.getParsingProfileDropdown().getSelectedItem();
            MetricsProfileDo metricsProfile = (MetricsProfileDo) view.getMetricsProfileDropdown().getSelectedItem();
            view.getCopyButton().setEnabled(false);
            view.getMoveButton().setEnabled(false);

            ArrayList<File> selectedFiles = getSelectedFiles();
            if(selectedFiles.isEmpty()) {
                messagePopup(GuiMessages.NO_FILES_SELECTED, JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            boolean success = organizationService.moveFilesThatMatch(selectedFiles, targetFolder, parsingProfile, metricsProfile);
            if(success) {
                messagePopup(GuiMessages.FILE_MOVE_WAS_SUCCESSFUL, JOptionPane.INFORMATION_MESSAGE);
                view.getTargetFolderTable().setData(formatFileNames(targetFolder.listFiles()));
                view.getSourceFolderTable().setData(formatFileNames(sourceFolder.listFiles()));
            } else {
                messagePopup(GuiMessages.FILE_MOVE_FAILED, JOptionPane.ERROR_MESSAGE);
            }
        });

        view.getSourceFolderTable().addRowSelectionEvent( (event) -> {
            ListSelectionModel listSelectionModel = (ListSelectionModel) event.getSource();
            if(listSelectionModel.isSelectionEmpty()) {
                view.getCopyButton().setEnabled(false);
                view.getMoveButton().setEnabled(false);
            } else {
                selectedIndexes = listSelectionModel.getSelectedIndices();
                view.getCopyButton().setEnabled(true);
                view.getMoveButton().setEnabled(true);
            }
        });
    }


    @Override
    public void execute() {

    }

    @Override
    public JPanel getView() {
        return view;
    }

    public void refreshComboBoxes() {
        view.getParsingProfileDropdown().setRenderer(new CellRenderer());
        parsingProfiles = parsingService.getParsingProfiles();
        DefaultComboBoxModel<ParsingProfileDo> parsingProfileModel = new DefaultComboBoxModel<>(parsingProfiles);
        view.getParsingProfileDropdown().setModel(parsingProfileModel);

        view.getMetricsProfileDropdown().setRenderer(new CellRenderer());
        metricsProfiles = metricsService.getMetricsProfiles();
        DefaultComboBoxModel<MetricsProfileDo> metricsProfileModel = new DefaultComboBoxModel<>(metricsProfiles);
        view.getMetricsProfileDropdown().setModel(metricsProfileModel);
    }


    private ArrayList<File> getSelectedFiles() {
        ArrayList<File> selectedFiles = new ArrayList<>(selectedIndexes.length);
        if(selectedIndexes.length > sourceFiles.size()) {
            return selectedFiles;
        }

        for (int selectedIndex : selectedIndexes) {
            selectedFiles.add(sourceFiles.get(selectedIndex));
        }
        return selectedFiles;
    }

    private Object[][] formatFileNames(File[] files) {

        List<File> filteredFiles = Arrays.stream(files).filter(file -> !file.isDirectory()).collect(Collectors.toList());
        Object[][] toRet = new Object[filteredFiles.size()][1];
        int idx = 0;
        for (File filteredFile : filteredFiles) {
            toRet[idx++][0] = filteredFile.getName();
        }
        return toRet;
    }

    private void messagePopup(String message, int messageType) {
        JOptionPane.showMessageDialog(view,
                message,
                GuiMessages.MESSAGE_TITLE,
                messageType);
    }

}
