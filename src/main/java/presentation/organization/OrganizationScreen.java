package presentation.organization;

import domain.entities.displayobjects.MetricsProfileDo;
import domain.entities.displayobjects.ParsingProfileDo;
import presentation.common.GuiConstants;
import presentation.common.custom.GeneralTablePanel;
import presentation.common.custom.LabelLabelPanel;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
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
        JPanel northPanel = new JPanel(new BorderLayout(H_GAP, V_GAP));

        chooseSourceFolderButton = new JButton(GuiConstants.CHOOSE_SOURCE_FOLDER_LABEL);
        sourceFolderName = new LabelLabelPanel(GuiConstants.FOLDER_LABEL + ": ");
        northPanel.add(chooseSourceFolderButton, BorderLayout.NORTH);
        northPanel.add(sourceFolderName);
        panel.add(northPanel, BorderLayout.NORTH);

        sourceFolderTable = new GeneralTablePanel(new String[]{GuiConstants.FILE_COLUMN}, false);
        panel.add(sourceFolderTable, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createEastPanel() {
        JPanel panel = new JPanel(new BorderLayout(H_GAP, V_GAP));
        JPanel northPanel = new JPanel(new BorderLayout(H_GAP, V_GAP));

        chooseTargetFolderButton = new JButton(GuiConstants.CHOOSE_TARGET_FOLDER_LABEL);
        targetFolderName = new LabelLabelPanel(GuiConstants.FOLDER_LABEL + ": ");
        northPanel.add(chooseTargetFolderButton, BorderLayout.NORTH);
        northPanel.add(targetFolderName);
        panel.add(northPanel, BorderLayout.NORTH);

        targetFolderTable = new GeneralTablePanel(new String[]{GuiConstants.FILE_COLUMN}, false);
        panel.add(targetFolderTable, BorderLayout.CENTER);

        return panel;
    }


    private JPanel createCenterPanel() {
        JPanel holder = new JPanel(new BorderLayout(H_GAP, V_GAP));

        JPanel panel = new JPanel(new GridLayout(4, 1, H_GAP, V_GAP));
//        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, H_GAP, V_GAP));

        panel.add(createParsingProfilePanel());
        panel.add(createMetricsProfilePanel());
        copyButton = new JButton(GuiConstants.COPY_BUTTON);
        moveButton = new JButton(GuiConstants.MOVE_BUTTON);
        panel.add(copyButton);
        panel.add(moveButton);
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

}
