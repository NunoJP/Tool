package presentation.organization;

import domain.entities.displayobjects.MetricsProfileDo;
import domain.entities.displayobjects.ParsingProfileDo;
import presentation.common.GuiConstants;
import presentation.common.custom.GeneralTablePanel;
import presentation.common.custom.LabelLabelPanel;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import static presentation.common.GuiConstants.H_GAP;
import static presentation.common.GuiConstants.V_GAP;

public class OrganizationScreen extends JPanel {

    private JButton chooseSourceFolderButton;
    private JButton chooseTargetFolderButton;
    private GeneralTablePanel sourceFolderTable;
    private GeneralTablePanel targetFolderTable;
    private LabelLabelPanel sourceFolderName;
    private LabelLabelPanel targetFolderName;

    private JComboBox<ParsingProfileDo> parsingProfileDropdown;
    private JComboBox<MetricsProfileDo> metricsProfileDropdown;
    private JButton copyButton;
    private JButton moveButton;

    public OrganizationScreen() {
        this.setLayout(new GridLayout(1, 3, H_GAP, V_GAP));

        JPanel westPanel = createWestPanel();
        JPanel centerPanel = createCenterPanel();
        JPanel eastPanel = createEastPanel();

        this.add(westPanel, BorderLayout.WEST);
        this.add(centerPanel, BorderLayout.CENTER);
        this.add(eastPanel, BorderLayout.EAST);
    }

    private JPanel createWestPanel() {
        JPanel panel = new JPanel(new BorderLayout(H_GAP, V_GAP));
        JPanel northPanel = new JPanel();
        northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.Y_AXIS));

        chooseSourceFolderButton = new JButton(GuiConstants.CHOOSE_SOURCE_FOLDER_LABEL);
        JPanel chooseSourceButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, H_GAP, V_GAP));
        chooseSourceButtonPanel.add(chooseSourceFolderButton, BorderLayout.CENTER);

        sourceFolderName = new LabelLabelPanel(GuiConstants.FOLDER_LABEL + ": ");
        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, H_GAP, V_GAP));
        namePanel.add(sourceFolderName);

        northPanel.add(chooseSourceButtonPanel);
        northPanel.add(namePanel);
        panel.add(northPanel, BorderLayout.NORTH);

        sourceFolderTable = new GeneralTablePanel(new String[]{GuiConstants.FILE_COLUMN}, false, false);
        sourceFolderTable.setMultiLineSelection();
        panel.add(sourceFolderTable, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createEastPanel() {
        JPanel panel = new JPanel(new BorderLayout(H_GAP, V_GAP));
        JPanel northPanel = new JPanel();
        northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.Y_AXIS));

        chooseTargetFolderButton = new JButton(GuiConstants.CHOOSE_TARGET_FOLDER_LABEL);
        JPanel chooseTargetButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, H_GAP, V_GAP));
        chooseTargetButtonPanel.add(chooseTargetFolderButton, BorderLayout.CENTER);

        targetFolderName = new LabelLabelPanel(GuiConstants.FOLDER_LABEL + ": ");
        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, H_GAP, V_GAP));
        namePanel.add(targetFolderName);

        northPanel.add(chooseTargetButtonPanel);
        northPanel.add(namePanel);
        panel.add(northPanel, BorderLayout.NORTH);

        targetFolderTable = new GeneralTablePanel(new String[]{GuiConstants.FILE_COLUMN}, false, false);
        panel.add(targetFolderTable, BorderLayout.CENTER);

        return panel;
    }


    private JPanel createCenterPanel() {
        JPanel holder = new JPanel(new BorderLayout(H_GAP, V_GAP));
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(createParsingProfilePanel());
        panel.add(createMetricsProfilePanel());

        copyButton = new JButton(GuiConstants.COPY_BUTTON);
        JPanel copyButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, H_GAP, V_GAP));
        copyButtonPanel.add(copyButton, BorderLayout.CENTER);

        moveButton = new JButton(GuiConstants.MOVE_BUTTON);
        JPanel moveButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, H_GAP, V_GAP));
        moveButtonPanel.add(moveButton, BorderLayout.CENTER);


        panel.add(copyButtonPanel);
        panel.add(moveButtonPanel);
        holder.add(panel, BorderLayout.NORTH);
        return holder;
    }


    private JPanel createParsingProfilePanel() {
        JPanel profilePanel = new JPanel(new BorderLayout(H_GAP, V_GAP));
        profilePanel.add(new JLabel(GuiConstants.PARSING_PROFILE_LABEL, SwingConstants.RIGHT), BorderLayout.WEST);
        parsingProfileDropdown = new JComboBox<>();
        profilePanel.setBorder(new EmptyBorder(V_GAP * 2, H_GAP, V_GAP * 2, H_GAP));
        profilePanel.add(parsingProfileDropdown, BorderLayout.CENTER);
        profilePanel.add(new JPanel(), BorderLayout.EAST);
        return profilePanel;
    }

    private JPanel createMetricsProfilePanel() {
        JPanel metricsPanel = new JPanel(new BorderLayout(H_GAP, V_GAP));
        metricsPanel.add(new JLabel(GuiConstants.METRICS_LABEL, SwingConstants.RIGHT), BorderLayout.WEST);
        metricsProfileDropdown = new JComboBox<>();
        metricsPanel.setBorder(new EmptyBorder(V_GAP * 2, H_GAP, V_GAP * 2, H_GAP));
        metricsPanel.add(metricsProfileDropdown, BorderLayout.CENTER);
        metricsPanel.add(new JPanel(), BorderLayout.EAST);
        return metricsPanel;
    }

    public JButton getChooseSourceFolderButton() {
        return chooseSourceFolderButton;
    }

    public JButton getChooseTargetFolderButton() {
        return chooseTargetFolderButton;
    }

    public GeneralTablePanel getSourceFolderTable() {
        return sourceFolderTable;
    }

    public GeneralTablePanel getTargetFolderTable() {
        return targetFolderTable;
    }

    public LabelLabelPanel getSourceFolderName() {
        return sourceFolderName;
    }

    public LabelLabelPanel getTargetFolderName() {
        return targetFolderName;
    }

    public JComboBox<ParsingProfileDo> getParsingProfileDropdown() {
        return parsingProfileDropdown;
    }

    public JComboBox<MetricsProfileDo> getMetricsProfileDropdown() {
        return metricsProfileDropdown;
    }

    public JButton getCopyButton() {
        return copyButton;
    }

    public JButton getMoveButton() {
        return moveButton;
    }
}
