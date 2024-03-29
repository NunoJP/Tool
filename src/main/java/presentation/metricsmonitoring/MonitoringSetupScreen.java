package presentation.metricsmonitoring;

import domain.entities.displayobjects.MetricsProfileDo;
import domain.entities.displayobjects.ParsingProfileDo;
import presentation.common.GuiConstants;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import static presentation.common.GuiConstants.H_GAP;
import static presentation.common.GuiConstants.V_GAP;

public class MonitoringSetupScreen extends JPanel {

    private JTextField nameField;
    private JButton chooseFileButton;
    private JButton startButton;
    private JComboBox<ParsingProfileDo> parsingProfileDropdown;
    private JComboBox<MetricsProfileDo> metricsProfileDropdown;
    private final JFrame motherFrame;

    public MonitoringSetupScreen(final JFrame motherFrame) {
        this.motherFrame = motherFrame;
        this.setLayout(new BorderLayout());
        JPanel holder = new JPanel(new GridLayout(2, 1, H_GAP, V_GAP));
        holder.add(createParsingProfilePanel());
        holder.add(createMetricsProfilePanel());
        JPanel northPanel = new JPanel(new BorderLayout(H_GAP, V_GAP));
        northPanel.add(createFilePanel(), BorderLayout.NORTH);
        northPanel.add(holder, BorderLayout.CENTER);
        northPanel.add(createStartPanel(), BorderLayout.SOUTH);
        this.add(northPanel, BorderLayout.NORTH);
    }

    private JPanel createFilePanel() {
        JPanel filePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, H_GAP, V_GAP));
        filePanel.add(new JLabel(GuiConstants.FILE_INDICATION_LABEL));
        nameField = new JTextField(GuiConstants.FILE_NAME_FIELD_SIZE);
        nameField.setEditable(false);
        filePanel.add(nameField);
        chooseFileButton = new JButton(GuiConstants.CHOOSE_FILE_BUTTON);
        filePanel.add(chooseFileButton);
        return filePanel;
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

    private JPanel createStartPanel() {
        JPanel startButtonPanel = new JPanel(new GridLayout(1, 3, H_GAP, V_GAP));
        startButton = new JButton(GuiConstants.START_BUTTON);
        startButtonPanel.add(new JPanel());
        startButtonPanel.add(startButton);
        startButtonPanel.add(new JPanel());
        return startButtonPanel;
    }

    public JTextField getNameField() {
        return nameField;
    }

    public JButton getChooseFileButton() {
        return chooseFileButton;
    }

    public JButton getStartButton() {
        return startButton;
    }

    public JFrame getMotherFrame() {
        return motherFrame;
    }

    public JComboBox<ParsingProfileDo> getParsingProfileDropdown() {
        return parsingProfileDropdown;
    }

    public JComboBox<MetricsProfileDo> getMetricsProfileDropdown() {
        return metricsProfileDropdown;
    }
}
