package presentation.metricsprofile;

import domain.entities.common.ThresholdTypeEnum;
import domain.entities.common.ThresholdUnitEnum;
import presentation.common.GuiConstants;
import presentation.common.custom.CellRenderer;
import presentation.common.custom.GeneralTablePanel;
import presentation.common.custom.LabelTextFieldPanel;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;

import static presentation.common.GuiConstants.H_GAP;
import static presentation.common.GuiConstants.H_METRIC_PROFILES_EDITOR_SCREEN_SIZE;
import static presentation.common.GuiConstants.V_GAP;
import static presentation.common.GuiConstants.V_METRIC_PROFILES_EDITOR_SCREEN_SIZE;


public class MetricsProfileEditorScreen extends JDialog {

    private JPanel basePanel;
    private LabelTextFieldPanel namePanel;
    private LabelTextFieldPanel resultPanel;
    private JRadioButton mcwButton;
    private JRadioButton fileSizeButton;
    private JRadioButton kwdHistButton;
    private JRadioButton kwdOverTimeButton;
    private JRadioButton kwdThresholdButton;
    private JRadioButton caseSensitiveButton;
    private JButton addKwdButton;
    private JButton updateKwdButton;
    private JButton deleteKwdButton;
    private JComboBox<ThresholdTypeEnum> thresholdComboBox;
    private JComboBox<ThresholdUnitEnum> thresholdUnitComboBox;
    private JSpinner valueInput;
    private GeneralTablePanel keywordTable;
    private JButton saveProfileButton;
    private JButton clearButton;
    private LabelTextFieldPanel keywordPanel;

    public MetricsProfileEditorScreen(Frame owner) {
        super(owner, GuiConstants.METRICS_PROFILE_EDITOR_SCREEN_TITLE);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        createMainPanel();
        this.pack();
    }

    private void createMainPanel() {
        this.rootPane.setLayout(new BorderLayout(H_GAP, V_GAP));
        basePanel = new JPanel(new BorderLayout(H_GAP, V_GAP));
        this.setPreferredSize(new Dimension(H_METRIC_PROFILES_EDITOR_SCREEN_SIZE, V_METRIC_PROFILES_EDITOR_SCREEN_SIZE));
        this.rootPane.add(basePanel, BorderLayout.CENTER);

        JPanel northPanel = new JPanel(new GridLayout(2, 1));
        northPanel.add(createNamePanel());
        northPanel.add(createResultPanel());
        basePanel.add(northPanel, BorderLayout.NORTH);
        basePanel.add(createWestOptionsPanel(), BorderLayout.WEST);
        basePanel.add(createEastOptionsPanel(), BorderLayout.CENTER);
        basePanel.add(createSouthButtonsPanel(), BorderLayout.SOUTH);
    }

    private JPanel createNamePanel() {
        JPanel holder = new JPanel(new FlowLayout(FlowLayout.LEFT, H_GAP, V_GAP));
        namePanel = new LabelTextFieldPanel(GuiConstants.NAME_LABEL);
        namePanel.setTextFieldWidth(GuiConstants.FILE_NAME_FIELD_SIZE);
        holder.add(namePanel);
        return holder;
    }

    private JPanel createResultPanel() {
        JPanel holder = new JPanel(new FlowLayout(FlowLayout.LEFT, H_GAP, V_GAP));
        resultPanel = new LabelTextFieldPanel(GuiConstants.RESULT_LABEL);
        resultPanel.setTextFieldWidth(GuiConstants.RESULT_FIELD_SIZE);
        resultPanel.getVariableTextField().setEditable(false);
        holder.add(resultPanel);
        return holder;
    }

    private JPanel createWestOptionsPanel() {
        JPanel holder = new JPanel(new BorderLayout(H_GAP, V_GAP));
        JPanel north = new JPanel();
        north.setLayout(new BoxLayout(north, BoxLayout.Y_AXIS));
        holder.add(north, BorderLayout.NORTH);
        mcwButton = new JRadioButton();
        fileSizeButton = new JRadioButton();
        kwdHistButton = new JRadioButton();
        kwdOverTimeButton = new JRadioButton();
        kwdThresholdButton = new JRadioButton();

        north.add(createRadioButtonPanel(GuiConstants.MOST_COMMON_WORDS_LABEL, mcwButton));
        north.add(createRadioButtonPanel(GuiConstants.FILE_SIZE_LABEL, fileSizeButton));
        north.add(createRadioButtonPanel(GuiConstants.KEYWORD_HISTOGRAM_LABEL, kwdHistButton));
        north.add(createRadioButtonPanel(GuiConstants.KEYWORD_OVER_TIME_LABEL, kwdOverTimeButton));
        north.add(createRadioButtonPanel(GuiConstants.KEYWORD_THRESHOLD_LABEL, kwdThresholdButton));
        return holder;
    }

    private JPanel createEastOptionsPanel() {
        JPanel holder = new JPanel(new BorderLayout(H_GAP, V_GAP));

        JPanel northPanel = new JPanel();
        northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.Y_AXIS));
        keywordPanel = new LabelTextFieldPanel(GuiConstants.KEYWORD_LABEL);
        keywordPanel.setTextFieldWidth(GuiConstants.KEYWORD_FIELD_SIZE);
        JPanel spacer = new JPanel(new FlowLayout(FlowLayout.LEFT, H_GAP, 0));
        spacer.add(keywordPanel);
        northPanel.add(spacer);

        caseSensitiveButton = new JRadioButton();
        northPanel.add(createRadioButtonPanel(GuiConstants.CASE_SENSITIVE_LABEL, caseSensitiveButton));

        JPanel thresholdPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, H_GAP, V_GAP));
        thresholdPanel.add(new JLabel(GuiConstants.THRESHOLD_LABEL));
        thresholdComboBox = new JComboBox<>(ThresholdTypeEnum.values());
        thresholdComboBox.setRenderer(new CellRenderer());
        thresholdComboBox.setSelectedIndex(0);
        thresholdComboBox.setLightWeightPopupEnabled(false);
        thresholdPanel.add(thresholdComboBox);
        valueInput = new JSpinner();
        thresholdPanel.add(valueInput);
        thresholdUnitComboBox = new JComboBox<>(ThresholdUnitEnum.values());
        thresholdUnitComboBox.setRenderer(new CellRenderer());
        thresholdUnitComboBox.setSelectedIndex(0);
        thresholdUnitComboBox.setLightWeightPopupEnabled(false);
        thresholdPanel.add(thresholdUnitComboBox);
        northPanel.add(thresholdPanel);

        JPanel buttonHolder = new JPanel(new FlowLayout(FlowLayout.LEFT, H_GAP, V_GAP));
        addKwdButton = new JButton(GuiConstants.ADD_BUTTON);
        updateKwdButton = new JButton(GuiConstants.UPDATE_SELECTED_BUTTON);
        deleteKwdButton = new JButton(GuiConstants.DELETE_SELECTED_BUTTON);
        buttonHolder.add(addKwdButton);
        buttonHolder.add(updateKwdButton);
        buttonHolder.add(deleteKwdButton);

        northPanel.add(buttonHolder);

        keywordTable = new GeneralTablePanel(
                new String[]{GuiConstants.KEYWORD_COLUMN, GuiConstants.CASE_SENSITIVE_COLUMN, GuiConstants.THRESHOLD_COLUMN}, false
        );

        holder.add(northPanel, BorderLayout.NORTH);
        holder.add(keywordTable, BorderLayout.CENTER);

        return holder;
    }


    private JPanel createSouthButtonsPanel() {
        JPanel buttonHolder = new JPanel(new FlowLayout(FlowLayout.CENTER, H_GAP, V_GAP));
        saveProfileButton = new JButton(GuiConstants.SAVE_PROFILE_BUTTON);
        clearButton = new JButton(GuiConstants.CLEAR_BUTTON);
        buttonHolder.add(saveProfileButton);
        buttonHolder.add(clearButton);
        return buttonHolder;
    }


    private JPanel createRadioButtonPanel(String labelText, JRadioButton button) {
        JPanel radioButtonHolderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, H_GAP, 0));
        JLabel label = new JLabel(labelText);
        radioButtonHolderPanel.add(label);
        radioButtonHolderPanel.add(button);
        return radioButtonHolderPanel;
    }

    public JPanel getPanel() {
        return basePanel;
    }

    public JPanel getBasePanel() {
        return basePanel;
    }

    public LabelTextFieldPanel getNamePanel() {
        return namePanel;
    }

    public LabelTextFieldPanel getResultPanel() {
        return resultPanel;
    }

    public JRadioButton getMcwButton() {
        return mcwButton;
    }

    public JRadioButton getFileSizeButton() {
        return fileSizeButton;
    }

    public JRadioButton getKwdHistButton() {
        return kwdHistButton;
    }

    public JRadioButton getKwdOverTimeButton() {
        return kwdOverTimeButton;
    }

    public JRadioButton getKwdThresholdButton() {
        return kwdThresholdButton;
    }

    public JRadioButton getCaseSensitiveButton() {
        return caseSensitiveButton;
    }

    public JButton getAddKwdButton() {
        return addKwdButton;
    }

    public JButton getUpdateKwdButton() {
        return updateKwdButton;
    }

    public JButton getDeleteKwdButton() {
        return deleteKwdButton;
    }

    public JComboBox<ThresholdTypeEnum> getThresholdComboBox() {
        return thresholdComboBox;
    }

    public JComboBox<ThresholdUnitEnum> getThresholdUnitComboBox() {
        return thresholdUnitComboBox;
    }

    public JSpinner getThresholdValueInput() {
        return valueInput;
    }

    public GeneralTablePanel getKeywordTable() {
        return keywordTable;
    }

    public JButton getSaveProfileButton() {
        return saveProfileButton;
    }

    public JButton getClearButton() {
        return clearButton;
    }

    public LabelTextFieldPanel getKeywordPanel() {
        return keywordPanel;
    }

    public void setKeywordPanel(LabelTextFieldPanel keywordPanel) {
        this.keywordPanel = keywordPanel;
    }

    public void resetKwdTable() {
        keywordTable = new GeneralTablePanel(
                new String[]{GuiConstants.KEYWORD_COLUMN, GuiConstants.CASE_SENSITIVE_COLUMN, GuiConstants.THRESHOLD_COLUMN}, false
        );
    }
}
