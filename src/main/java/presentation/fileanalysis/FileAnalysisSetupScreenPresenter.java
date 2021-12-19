package presentation.fileanalysis;

import domain.entities.displayobjects.MetricsProfileDo;
import domain.entities.displayobjects.ParsingProfileDo;
import domain.services.MetricsProfileManagementService;
import domain.services.ParsingProfileManagementService;
import presentation.common.ITabPresenter;
import presentation.common.custom.CellRenderer;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import java.io.File;
import java.nio.file.Paths;

import static javax.swing.JFileChooser.APPROVE_OPTION;
import static javax.swing.JFileChooser.FILES_ONLY;

public class FileAnalysisSetupScreenPresenter implements ITabPresenter {

    private final FileAnalysisSetupScreen view;
    private final ParsingProfileManagementService parsingService;
    private final MetricsProfileManagementService metricsService;
    private ParsingProfileDo[] parsingProfiles;
    private MetricsProfileDo[] metricsProfiles;
    private File selectedFile;


    public FileAnalysisSetupScreenPresenter() {
        view = new FileAnalysisSetupScreen();
        parsingService = new ParsingProfileManagementService();
        metricsService = new MetricsProfileManagementService();
        defineViewBehavior();
    }

    private void defineViewBehavior() {
        view.getParsingProfileDropdown().setRenderer(new CellRenderer());
        parsingProfiles = parsingService.getParsingProfiles();
        DefaultComboBoxModel<ParsingProfileDo> parsingProfileModel = new DefaultComboBoxModel<>(parsingProfiles);
        view.getParsingProfileDropdown().setModel(parsingProfileModel);

        view.getMetricsProfileDropdown().setRenderer(new CellRenderer());
        metricsProfiles = metricsService.getMetricsProfiles();
        DefaultComboBoxModel<MetricsProfileDo> metricsProfileModel = new DefaultComboBoxModel<>(metricsProfiles);
        view.getMetricsProfileDropdown().setModel(metricsProfileModel);

        view.getChooseFileButton().addActionListener(actionEvent -> {
            final JFileChooser fc = new JFileChooser();
            // set Fc to open for the current directory
            fc.setCurrentDirectory(Paths.get("").toAbsolutePath().toFile());
            // allow for file or directory selection
            fc.setFileSelectionMode(FILES_ONLY);

            int returnVal = fc.showOpenDialog(view);
            if (APPROVE_OPTION == returnVal) {
                selectedFile = fc.getSelectedFile();
                if (!selectedFile.isDirectory()) {
                    view.getNameField().setText(selectedFile.getPath());
                }
            }
        });

        view.getStartButton().addActionListener(
            e -> {
                ParsingProfileDo parsingProfile = (ParsingProfileDo) view.getParsingProfileDropdown().getSelectedItem();
                MetricsProfileDo metricsProfile = (MetricsProfileDo) view.getMetricsProfileDropdown().getSelectedItem();
                var metricsScreenPresenter = new FileAnalysisMetricsScreenPresenter(
                        selectedFile, parsingProfile, metricsProfile
                );
            }
        );
    }

    @Override
    public void execute() {

    }

    @Override
    public JPanel getView() {
        return view;
    }
}
