package presentation.metricsprofile;

import domain.entities.common.ThresholdTypeEnum;
import domain.entities.common.ThresholdUnitEnum;
import domain.entities.common.WarningLevel;
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
import static presentation.common.GuiConstants.WARNING_CONFIG_LABEL;


public class MetricsProfileEditorScreen extends JDialog {

    private JPanel basePanel;
    private LabelTextFieldPanel namePanel;
    private JRadioButton mcwButton;
    private JRadioButton fileSizeButton;
    private JRadioButton kwdHistButton;
    private JRadioButton kwdOverTimeButton;
    private JRadioButton kwdThresholdButton;
    private JRadioButton caseSensitiveButton;
    private JButton addKwdButton;
    private JButton updateKwdButton;
    private JButton deleteKwdButton;
    private JButton clearKwdsButton;
    private JComboBox<ThresholdTypeEnum> thresholdComboBox;
    private JComboBox<ThresholdUnitEnum> thresholdUnitComboBox;
    private JComboBox<WarningLevel> warningLevelComboBox;
    private JSpinner valueInput;
    private GeneralTablePanel keywordTable;
    private JButton saveProfileButton;
    private JButton clearButton;
    private JButton cancelButton;
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

        JPanel northPanel = new JPanel(new GridLayout(1, 1));
        northPanel.add(createNamePanel());
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

        thresholdUnitComboBox = new JComboBox<>(ThresholdUnitEnum.values());
        thresholdUnitComboBox.setRenderer(new CellRenderer());
        thresholdUnitComboBox.setSelectedIndex(0);
        thresholdUnitComboBox.setLightWeightPopupEnabled(false);
        thresholdPanel.add(thresholdUnitComboBox);

        valueInput = new JSpinner();
        valueInput.setEnabled(false);
        JSpinner.DefaultEditor editor = (JSpinner.DefaultEditor) (valueInput.getEditor());
        editor.getTextField().setColumns(4);
        thresholdPanel.add(valueInput);

        thresholdPanel.add(new JLabel(WARNING_CONFIG_LABEL));

        warningLevelComboBox = new JComboBox<>(WarningLevel.values());
        warningLevelComboBox.setRenderer(new CellRenderer());
        warningLevelComboBox.setSelectedIndex(0);
        warningLevelComboBox.setLightWeightPopupEnabled(false);
        thresholdPanel.add(warningLevelComboBox);

        northPanel.add(thresholdPanel);

        JPanel buttonHolder = new JPanel(new FlowLayout(FlowLayout.LEFT, H_GAP, V_GAP));
        addKwdButton = new JButton(GuiConstants.ADD_BUTTON);
        updateKwdButton = new JButton(GuiConstants.UPDATE_SELECTED_BUTTON);
        deleteKwdButton = new JButton(GuiConstants.DELETE_SELECTED_BUTTON);
        clearKwdsButton = new JButton(GuiConstants.CLEAR_KEYWORDS_TABLE_BUTTON);
        buttonHolder.add(addKwdButton);
        buttonHolder.add(updateKwdButton);
        buttonHolder.add(deleteKwdButton);
        buttonHolder.add(clearKwdsButton);

        northPanel.add(buttonHolder);

        keywordTable = new GeneralTablePanel(
                new String[]{GuiConstants.KEYWORD_COLUMN, GuiConstants.CASE_SENSITIVE_COLUMN,
                        GuiConstants.THRESHOLD_COLUMN, GuiConstants.WARNING_LEVEL_COLUMN}, false, true
        );

        holder.add(northPanel, BorderLayout.NORTH);
        holder.add(keywordTable, BorderLayout.CENTER);

        return holder;
    }


    private JPanel createSouthButtonsPanel() {
        JPanel buttonHolder = new JPanel(new FlowLayout(FlowLayout.CENTER, H_GAP, V_GAP));
        saveProfileButton = new JButton(GuiConstants.SAVE_PROFILE_BUTTON);
        clearButton = new JButton(GuiConstants.CLEAR_BUTTON);
        cancelButton = new JButton(GuiConstants.CANCEL_BUTTON);
        buttonHolder.add(saveProfileButton);
        buttonHolder.add(clearButton);
        buttonHolder.add(cancelButton);
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

    public JButton getClearKwdsButton() {
        return clearKwdsButton;
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

    public JButton getCancelButton() {
        return cancelButton;
    }

    public LabelTextFieldPanel getKeywordPanel() {
        return keywordPanel;
    }

    public void setKeywordPanel(LabelTextFieldPanel keywordPanel) {
        this.keywordPanel = keywordPanel;
    }

    public void resetKwdTable() {
        keywordTable.setData(new Object[0][]);
    }

    public String getProfileNameText() {
        return namePanel.getVariableTextField().getText();
    }

    public JComboBox<WarningLevel> getWarningLevelComboBox() {
        return warningLevelComboBox;
    }

}
