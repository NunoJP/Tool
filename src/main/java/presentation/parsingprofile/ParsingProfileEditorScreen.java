package presentation.parsingprofile;

import domain.entities.common.SeparatorEnum;
import domain.entities.common.TextClassesEnum;
import presentation.common.GuiConstants;
import presentation.common.custom.CellRenderer;
import presentation.common.custom.LabelTextFieldPanel;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;

import static presentation.common.GuiConstants.H_GAP;
import static presentation.common.GuiConstants.H_PARSING_PROFILES_EDITOR_SCREEN_SIZE;
import static presentation.common.GuiConstants.V_GAP;
import static presentation.common.GuiConstants.V_PARSING_PROFILES_EDITOR_SCREEN_SIZE;

public class ParsingProfileEditorScreen extends JDialog {

    private JPanel basePanel;
    private LabelTextFieldPanel namePanel;
    private LabelTextFieldPanel resultPanel;
    private JComboBox<TextClassesEnum> textClassComboBox;
    private JComboBox<SeparatorEnum> separatorClassComboBox;
    private JRadioButton ignoreButton;
    private JButton textClassAddButton;
    private JRadioButton specificFormatButton;
    private LabelTextFieldPanel specificFormat;
    private JButton separatorAddButton;
    private JButton removeLastButton;
    private JButton clearButton;
    private JButton saveProfileButton;

    public ParsingProfileEditorScreen(Frame owner) {
        super(owner, GuiConstants.PARSING_PROFILE_EDITOR_SCREEN_TITLE);
        setWindowClosingBehavior();
        createMainPanel();
        this.pack();
    }

    private void createMainPanel() {
        this.rootPane.setLayout(new BorderLayout(H_GAP, V_GAP));
        basePanel = new JPanel(new GridLayout(6, 1, H_GAP, V_GAP));
        this.setPreferredSize(new Dimension(H_PARSING_PROFILES_EDITOR_SCREEN_SIZE, V_PARSING_PROFILES_EDITOR_SCREEN_SIZE));
        this.rootPane.add(basePanel, BorderLayout.CENTER);
        basePanel.add(createNamePanel());
        basePanel.add(createResultPanel());
        basePanel.add(createTextClassLine());
        basePanel.add(createSpecificFormatLine());
        basePanel.add(createSeparatorLine());
        basePanel.add(createButtonPanel());
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

    private JPanel createTextClassLine() {
        JPanel textClassPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, H_GAP, V_GAP));
        textClassPanel.add(new JLabel(GuiConstants.TEXT_CLASS_LABEL));
        textClassComboBox = new JComboBox<>(TextClassesEnum.values());
        textClassComboBox.setRenderer(new CellRenderer());
        textClassComboBox.setSelectedIndex(0);
        textClassPanel.add(textClassComboBox);
        textClassPanel.add(new JLabel(GuiConstants.IGNORE_THIS_PORTION_LABEL));
        ignoreButton = new JRadioButton();
        textClassPanel.add(ignoreButton);
        textClassAddButton = new JButton(GuiConstants.ADD_BUTTON);
        textClassPanel.add(textClassAddButton);
        return textClassPanel;
    }

    private JPanel createSpecificFormatLine() {
        JPanel specificPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, H_GAP, V_GAP));
        specificFormat = new LabelTextFieldPanel("                  ");
        specificFormat.setTextFieldWidth(GuiConstants.FILE_NAME_FIELD_SIZE);
        specificPanel.add(specificFormat);
        specificPanel.add(new JLabel(GuiConstants.SPECIFIC_FORMAT_LABEL));
        specificFormatButton = new JRadioButton();
        specificPanel.add(specificFormatButton);
        return specificPanel;
    }

    private JPanel createSeparatorLine() {
        JPanel separatorPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, H_GAP, V_GAP));
        separatorPanel.add(new JLabel(GuiConstants.SEPARATOR_LABEL));
        separatorClassComboBox = new JComboBox<>(SeparatorEnum.values());
        separatorClassComboBox.setRenderer(new CellRenderer());
        separatorClassComboBox.setSelectedIndex(0);
        separatorPanel.add(separatorClassComboBox);
        separatorAddButton = new JButton(GuiConstants.ADD_BUTTON);
        separatorPanel.add(separatorAddButton);
        enableSeparator(false);
        return separatorPanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, H_GAP, V_GAP));
        removeLastButton = new JButton(GuiConstants.REMOVE_LAST_BUTTON);
        clearButton = new JButton(GuiConstants.CLEAR_BUTTON);
        saveProfileButton = new JButton(GuiConstants.SAVE_PROFILE_BUTTON);
        buttonPanel.add(removeLastButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(saveProfileButton);
        return buttonPanel;
    }

    private void setWindowClosingBehavior() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }


    public JPanel getPanel() {
        return basePanel;
    }

    public void enableSeparator(boolean enable) {
        separatorClassComboBox.setEnabled(enable);
        separatorAddButton.setEnabled(enable);
    }

    public void enableTextClass(boolean enable) {
        textClassComboBox.setEnabled(enable);
        ignoreButton.setEnabled(enable);
        textClassAddButton.setEnabled(enable);
    }

    public void setResultPanelText(String text) {
        resultPanel.getVariableTextField().setText(text);
    }

    public String getSpecificFormatText() {
        return specificFormat.getVariableTextField().getText();
    }

    public void setSpecificFormatText(String text) {
        specificFormat.getVariableTextField().setText(text);
    }

    public String getProfileNameText() {
        return namePanel.getVariableTextField().getText();
    }

    public void setProfileNameText(String text) {
        namePanel.getVariableTextField().setText(text);
    }

    // Getters

    public JComboBox<TextClassesEnum> getTextClassComboBox() {
        return textClassComboBox;
    }

    public JComboBox<SeparatorEnum> getSeparatorClassComboBox() {
        return separatorClassComboBox;
    }

    public JRadioButton getIgnoreButton() {
        return ignoreButton;
    }

    public JButton getTextClassAddButton() {
        return textClassAddButton;
    }

    public JRadioButton getSpecificFormatButton() {
        return specificFormatButton;
    }

    public JButton getSeparatorAddButton() {
        return separatorAddButton;
    }

    public JButton getRemoveLastButton() {
        return removeLastButton;
    }

    public JButton getClearButton() {
        return clearButton;
    }

    public JButton getSaveProfileButton() {
        return saveProfileButton;
    }


}
