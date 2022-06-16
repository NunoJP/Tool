package presentation.metricsprofile;

import domain.entities.Validator;
import domain.entities.common.Keyword;
import domain.entities.common.ThresholdTypeEnum;
import domain.entities.common.ThresholdUnitEnum;
import domain.entities.common.WarningLevel;
import domain.entities.displayobjects.MetricsProfileDo;
import domain.services.MetricsProfileManagementService;
import presentation.common.GuiMessages;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;
import java.util.ArrayList;

public class MetricsProfileEditorScreenPresenter {

    private ArrayList<Keyword> keywords = new ArrayList<>();
    private final JFrame motherFrame;
    private final MetricsProfileDo existingProfile;
    private final MetricsProfileManagementScreenPresenter callerPresenter;
    private final MetricsProfileEditorScreen dialogView;
    private MetricsProfileDo metricsProfileDo;
    private final MetricsProfileManagementService service;
    private int selectedKwdTableItemIndex = -1;

    public MetricsProfileEditorScreenPresenter(JFrame motherFrame, MetricsProfileDo existingProfile,
                                               MetricsProfileManagementScreenPresenter metricsProfileManagementScreenPresenter) {
        this.motherFrame = motherFrame;
        this.existingProfile = existingProfile;
        this.metricsProfileDo = existingProfile == null ? new MetricsProfileDo() : new MetricsProfileDo(existingProfile);
        this.callerPresenter = metricsProfileManagementScreenPresenter;
        this.dialogView = new MetricsProfileEditorScreen(motherFrame);
        this.service = new MetricsProfileManagementService();
        fullReset();
        populateViewWithExistingProfile();
        defineViewBehavior();
    }

    private void populateViewWithExistingProfile() {
        if(existingProfile != null) {
            dialogView.getMcwButton().setSelected(metricsProfileDo.hasMostCommonWords());
            dialogView.getFileSizeButton().setSelected(metricsProfileDo.hasFileSize());
            dialogView.getKwdHistButton().setSelected(metricsProfileDo.hasKeywordHistogram());
            dialogView.getKwdOverTimeButton().setSelected(metricsProfileDo.hasKeywordOverTime());
            dialogView.getKwdThresholdButton().setSelected(metricsProfileDo.hasKeywordThreshold());
            dialogView.getCaseSensitiveButton().setSelected(false);
            dialogView.getUpdateKwdButton().setEnabled(false);
            dialogView.getDeleteKwdButton().setEnabled(false);
            dialogView.getThresholdComboBox().setSelectedIndex(0);
            dialogView.getThresholdUnitComboBox().setSelectedIndex(0);
            dialogView.getThresholdValueInput().setValue(0);
            dialogView.getNamePanel().setVariableLabelText(metricsProfileDo.getName());
            dialogView.getKeywordPanel().setVariableLabelText("");
            keywords = metricsProfileDo.getKeywords();
            dialogView.getKeywordTable().setData(convertDataForTable(keywords));
        }
    }


    private void defineViewBehavior() {
        dialogView.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
                windowClosedOperations();
            }
        });

        dialogView.getCancelButton().addActionListener(actionEvent -> {
            windowClosedOperations();
            dialogView.dispose();
        });

        // Clear button behavior
        dialogView.getClearButton().addActionListener(actionEvent ->  {
            fullReset();
        });

        dialogView.getAddKwdButton().addActionListener( actionEvent -> {
            addOrUpdateKeyword(true);
        });

        dialogView.getUpdateKwdButton().addActionListener( actionEvent -> {
            addOrUpdateKeyword(false);
        });

        dialogView.getDeleteKwdButton().addActionListener( actionEvent -> {
            if(selectedKwdTableItemIndex != -1) {
                // Not adding a confirmation dialog, if the user mistakenly deletes it, it is simple enough to add again
                keywords.remove(selectedKwdTableItemIndex);
                selectedKwdTableItemIndex = -1;
                dialogView.getKeywordTable().setData(convertDataForTable(keywords));
            }
        });

        dialogView.getClearKwdsButton().addActionListener( actionEvent -> {
            keywords = new ArrayList<>();
            dialogView.getKeywordTable().setData(convertDataForTable(keywords));
        });

        dialogView.getSaveProfileButton().addActionListener( actionEvent -> {
            String profileName = dialogView.getProfileNameText();
            if(!Validator.validateProfileName(profileName)){
                showMessageDialog(GuiMessages.PROFILE_NAME_INVALID_OR_EMPTY, GuiMessages.PROFILE_NAME_INVALID_OR_EMPTY_TITLE, JOptionPane.WARNING_MESSAGE);
            } else {
                setValuesInDo(metricsProfileDo);
                metricsProfileDo.setName(profileName);
                if(existingProfile != null) {
                    int confirmation = JOptionPane.showConfirmDialog(dialogView,
                            GuiMessages.CONFIRM_OVERWRITE_PARSING_PROFILE,
                            GuiMessages.PLEASE_CONFIRM_DIALOG_TITLE,
                            JOptionPane.YES_NO_OPTION);
                    if(confirmation == JOptionPane.YES_OPTION) {
                        boolean updateSuccess = service.updateProfile(metricsProfileDo);
                        if(updateSuccess) {
                            showMessageDialog(GuiMessages.UPDATE_SUCCESSFUL, GuiMessages.SUCCESS_TITLE, JOptionPane.INFORMATION_MESSAGE);
                            callerPresenter.updateViewTable();
                        } else {
                            showMessageDialog(GuiMessages.UPDATE_FAILED, GuiMessages.FAILURE_TITLE, JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                    // Stop the save procedure
                    return;
                } else {
                    service.createProfile(metricsProfileDo);
                }

                callerPresenter.updateViewTable();
                fullReset();
            }
        });

        dialogView.getKeywordTable().addRowSelectionEvent( event -> {
            ListSelectionModel listSelectionModel = (ListSelectionModel) event.getSource();
            if(listSelectionModel.isSelectionEmpty()) {
                dialogView.getUpdateKwdButton().setEnabled(false);
                dialogView.getDeleteKwdButton().setEnabled(false);
                selectedKwdTableItemIndex = -1;
            } else {
                int selectedIndex = listSelectionModel.getSelectedIndices()[0];
                if(selectedIndex != selectedKwdTableItemIndex) {
                    dialogView.getUpdateKwdButton().setEnabled(true);
                    dialogView.getDeleteKwdButton().setEnabled(true);
                    selectedKwdTableItemIndex = selectedIndex;
                    fillKeywordElementsForUpdate();
                }
            }
        });

        dialogView.getThresholdUnitComboBox().addItemListener( event -> {
            JSpinner valueInput = dialogView.getThresholdValueInput();
            if(event.getItem().equals(ThresholdUnitEnum.PERCENTAGE)) {
                valueInput.setEnabled(true);
                valueInput.setModel(new SpinnerNumberModel(0.0, 0.0, 1, .1));
            } else if(event.getItem().equals(ThresholdUnitEnum.OCCURRENCES)) {
                valueInput.setEnabled(true);
                valueInput.setModel(new SpinnerNumberModel(0, 0, 100000.0, 1));
            } else {
                valueInput.setEnabled(false);
            }
            JSpinner.DefaultEditor editor = (JSpinner.DefaultEditor) (valueInput.getEditor());
            editor.getTextField().setColumns(4);
        });

    }

    /**
     * As the process for creation and update of the keyword is the same, we only need to implement the code once
     * the only difference is, if there is a selected element, then we need to replace the item.
     */
    private void addOrUpdateKeyword(boolean isAdd) {
        String keywordText = dialogView.getKeywordPanel().getVariableLabelText();
        if(!Validator.validateKeyword(keywordText)){
            showMessageDialog(GuiMessages.KEYWORD_TEXT_INVALID_OR_EMPTY, GuiMessages.KEYWORD_TEXT_INVALID_OR_EMPTY_TITLE, JOptionPane.WARNING_MESSAGE);
        } else {
            Keyword keyword = new Keyword(keywordText, dialogView.getCaseSensitiveButton().isSelected());
            keyword.setThresholdTrio((ThresholdTypeEnum) dialogView.getThresholdComboBox().getSelectedItem(),
                    (ThresholdUnitEnum) dialogView.getThresholdUnitComboBox().getSelectedItem(),
                    new BigDecimal(String.valueOf(dialogView.getThresholdValueInput().getValue())));
            keyword.setWarningLevel((WarningLevel) dialogView.getWarningLevelComboBox().getSelectedItem());
            if(!isAdd && selectedKwdTableItemIndex != -1) {
                keywords.set(selectedKwdTableItemIndex, keyword);
                selectedKwdTableItemIndex = -1;
            } else {
                keywords.add(keyword);
            }
            dialogView.getKeywordTable().setData(convertDataForTable(keywords));
        }
    }

    private void fillKeywordElementsForUpdate() {
        if(keywords.isEmpty()) {
            return;
        }
        Keyword keyword = keywords.get(selectedKwdTableItemIndex);
        dialogView.getKeywordPanel().setVariableLabelText(keyword.getKeywordText());
        ThresholdTypeEnum[] thTypeValues = ThresholdTypeEnum.values();
        ThresholdUnitEnum[] thUnitValues = ThresholdUnitEnum.values();
        WarningLevel[] warningLevels = WarningLevel.values();
        for (int i = 0; i < thTypeValues.length; i++) {
            if(thTypeValues[i].getName().equalsIgnoreCase(keyword.getThresholdType().getName())){
                dialogView.getThresholdComboBox().setSelectedIndex(i);
                break;
            }
        }
        for (int i = 0; i < thUnitValues.length; i++) {
            if(thUnitValues[i].getName().equalsIgnoreCase(keyword.getThresholdUnit().getName())){
                dialogView.getThresholdUnitComboBox().setSelectedIndex(i);
                break;
            }
        }
        for (int i = 0; i < warningLevels.length; i++) {
            if(warningLevels[i].getName().equalsIgnoreCase(keyword.getWarningLevel().getName())){
                dialogView.getWarningLevelComboBox().setSelectedIndex(i);
                break;
            }
        }
        dialogView.getThresholdValueInput().setValue(keyword.getThresholdValue());
        dialogView.getCaseSensitiveButton().setSelected(keyword.isCaseSensitive());
    }

    private void showMessageDialog(String message, String title, int warningMessage) {
        JOptionPane.showMessageDialog(dialogView, message, title, warningMessage);
    }

    public void execute() {
        dialogView.setLocationRelativeTo(motherFrame);
        dialogView.setVisible(true);
    }

    private void fullReset() {
        dialogView.getMcwButton().setSelected(false);
        dialogView.getFileSizeButton().setSelected(false);
        dialogView.getKwdHistButton().setSelected(false);
        dialogView.getKwdOverTimeButton().setSelected(false);
        dialogView.getKwdThresholdButton().setSelected(false);
        dialogView.getCaseSensitiveButton().setSelected(false);
        dialogView.getUpdateKwdButton().setEnabled(false);
        dialogView.getDeleteKwdButton().setEnabled(false);
        dialogView.getThresholdComboBox().setSelectedIndex(0);
        dialogView.getThresholdUnitComboBox().setSelectedIndex(0);
        dialogView.getThresholdValueInput().setValue(0);
        dialogView.getNamePanel().setVariableLabelText("");
        dialogView.getKeywordPanel().setVariableLabelText("");
        keywords = new ArrayList<>();
        dialogView.resetKwdTable();
    }

    private Object[][] convertDataForTable(ArrayList<Keyword> data) {
        Object[][] objects = new Object[data.size()][];
        for (int i = 0; i < data.size(); i++) {
            Keyword keyword = data.get(i);
            objects[i] = new Object[]{keyword.getKeywordText(), keyword.isCaseSensitive(),
                    keyword.getThresholdType().getSymbol()
                            + " " + keyword.getThresholdValue()
                            + " " + keyword.getThresholdUnit().getSymbol(),
                    keyword.getWarningLevel().getSymbol()};
        }
        return objects;
    }

    private void setValuesInDo(MetricsProfileDo metricsProfile) {
        metricsProfile.setHasMostCommonWords(dialogView.getMcwButton().isSelected());
        metricsProfile.setHasFileSize(dialogView.getFileSizeButton().isSelected());
        metricsProfile.setHasKeywordHistogram(dialogView.getKwdHistButton().isSelected());
        metricsProfile.setHasKeywordOverTime(dialogView.getKwdOverTimeButton().isSelected());
        metricsProfile.setHasKeywordThreshold(dialogView.getKwdThresholdButton().isSelected());
        metricsProfile.setKeywords(keywords);
    }


    private void windowClosedOperations() {
        callerPresenter.dialogWindowClosed();

        // if the window was closed, we need to reset the status of the object
        metricsProfileDo = existingProfile == null ? new MetricsProfileDo() : new MetricsProfileDo(existingProfile);
    }
}
