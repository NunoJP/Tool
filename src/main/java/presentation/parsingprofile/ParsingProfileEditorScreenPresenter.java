package presentation.parsingprofile;

import data.dataaccess.reader.LogFileReaderConsumer;
import domain.entities.Converter;
import domain.entities.Validator;
import domain.entities.common.ParsingProfilePortion;
import domain.entities.common.SeparatorEnum;
import domain.entities.common.TextClassesEnum;
import domain.entities.displayobjects.ParsingProfileDo;
import domain.entities.domainobjects.LogLine;
import domain.services.ParsingProfileManagementService;
import presentation.common.GuiMessages;
import presentation.common.IViewPresenter;
import presentation.fileanalysis.FileAnalysisScreenPresenter;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class ParsingProfileEditorScreenPresenter implements IViewPresenter {

    private final JFrame motherFrame;
    private final ParsingProfileEditorScreen dialogView;
    private final ParsingProfileManagementService managementService;
    private ParsingProfileDo parsingProfileDo;
    private final ParsingProfileDo existingProfile;
    private final ParsingProfileManagementScreenPresenter callerPresenter;
    private boolean wasLastPortionTextClass;

    public ParsingProfileEditorScreenPresenter(JFrame motherFrame, ParsingProfileDo existingProfile, ParsingProfileManagementScreenPresenter parsingProfileManagementScreenPresenter) {
        this.motherFrame = motherFrame;
        this.existingProfile = existingProfile;
        this.callerPresenter = parsingProfileManagementScreenPresenter;
        dialogView = new ParsingProfileEditorScreen(motherFrame);
        managementService = new ParsingProfileManagementService();
        parsingProfileDo = existingProfile == null ? new ParsingProfileDo() : new ParsingProfileDo(existingProfile);
        populateViewWithExistingProfile();
        defineViewBehavior();
    }

    private void populateViewWithExistingProfile() {
        if(existingProfile != null) {
            dialogView.setProfileNameText(parsingProfileDo.getName());
            dialogView.setResultPanelText(parsingProfileDo.getGuiRepresentation());
            ArrayList<ParsingProfilePortion> portions = parsingProfileDo.getPortions();
            if(!portions.isEmpty()) {
                changeTextClassSeparatorStates(!portions.get(portions.size() - 1).isSeparator());
            }
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

        // Text class Add button behavior
        dialogView.getTextClassAddButton().addActionListener( actionEvent -> {
            // if there is a specific format chosen by the user
            if(dialogView.getSpecificFormatButton().isSelected()){
                TextClassesEnum selectedItem = (TextClassesEnum) dialogView.getTextClassComboBox().getSelectedItem();
                if(selectedItem != null) {
                    updateDoAndResultPanel(selectedItem.getName(), selectedItem.getName(), dialogView.getIgnoreButton().isSelected(),
                            dialogView.getSpecificFormatButton().isSelected(), dialogView.getSpecificFormatText());
                }
            } else {
                TextClassesEnum selectedItem = (TextClassesEnum) dialogView.getTextClassComboBox().getSelectedItem();
                if(selectedItem != null) {
                    updateDoAndResultPanel(selectedItem.getName(), selectedItem.getName(), dialogView.getIgnoreButton().isSelected(),
                            false, "");
                }
            }
            // Added a Text Class so now it's a Separator
            changeTextClassSeparatorStates(true);
        });

        // Separator Add button behavior
        dialogView.getSeparatorAddButton().addActionListener(actionEvent -> {
            SeparatorEnum selectedItem = (SeparatorEnum) dialogView.getSeparatorClassComboBox().getSelectedItem();
            if(selectedItem != null) {
                updateDoAndResultPanel(selectedItem.getName(), selectedItem.getSymbol(),
                        (Integer) dialogView.getNumberOfSkipsInput().getValue());
            }
            // Added a Separator so now it's a Text Class
            changeTextClassSeparatorStates(false);
        });

        // Remove last portion button behavior
        dialogView.getRemoveLastButton().addActionListener(actionEvent -> {
            String profileText = parsingProfileDo.removeLastPortionAndGetProfile();
            dialogView.setResultPanelText(profileText);
            if(profileText.isEmpty() || wasLastPortionTextClass) {
                // if there is no more text, then it's the empty configuration, and we start in the Text Class
                changeTextClassSeparatorStates(false);
            } else {
                // What was removed was a Separator so now the "index" is on a Text Class
                changeTextClassSeparatorStates(true);
            }
        });

        // Save profile button behavior
        dialogView.getSaveProfileButton().addActionListener(actionEvent -> {
            String profileName = dialogView.getProfileNameText();
            if(!Validator.validateProfileName(profileName)){
                showMessageDialog(GuiMessages.PROFILE_NAME_INVALID_OR_EMPTY, GuiMessages.PROFILE_NAME_INVALID_OR_EMPTY_TITLE, JOptionPane.WARNING_MESSAGE);
            } else {
                parsingProfileDo.setName(profileName);
                parsingProfileDo.finishProfile();
                if(existingProfile != null) {
                    int confirmation = JOptionPane.showConfirmDialog(dialogView,
                            GuiMessages.CONFIRM_OVERWRITE_PARSING_PROFILE,
                            GuiMessages.PLEASE_CONFIRM_DIALOG_TITLE,
                            JOptionPane.YES_NO_OPTION);
                    if(confirmation == JOptionPane.YES_OPTION) {
                        boolean updateSuccess = managementService.updateProfile(parsingProfileDo);
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
                    managementService.createProfile(parsingProfileDo);
                }

                callerPresenter.updateViewTable();
                fullReset();
            }
        });

        // Specific format button behavior
        dialogView.getSpecificFormatButton().addActionListener(actionEvent -> {
            if(dialogView.getSpecificFormatButton().isSelected()){
                dialogView.getIgnoreButton().setSelected(false);
            }
        });

        // Ignore portion button behavior
        dialogView.getIgnoreButton().addActionListener(actionEvent -> {
            if(dialogView.getIgnoreButton().isSelected()){
                dialogView.getSpecificFormatButton().setSelected(false);
            }
        });

        dialogView.getTestButton().addActionListener(actionEvent -> {
            if(dialogView.getSampleTextPanel().isEmpty()) {
                showMessageDialog(GuiMessages.SAMPLE_TEXT_FIELD_EMPTY, GuiMessages.WARNING_TITLE, JOptionPane.INFORMATION_MESSAGE);
            } else {
                this.parsingProfileDo.finishProfile();
                LogFileReaderConsumer logFileConsumer = new LogFileReaderConsumer(Converter.toDomainObject(this.parsingProfileDo));
                logFileConsumer.accept(dialogView.getSampleTextPanel().getVariableLabelText());
                if(!logFileConsumer.getWarningMessages().isEmpty()) {
                    StringBuilder msgs = new StringBuilder();
                    for (String warningMessage : logFileConsumer.getWarningMessages()) {
                        msgs.append(warningMessage).append(System.lineSeparator());
                    }
                    showMessageDialog(msgs.toString(), GuiMessages.WARNING_TITLE, JOptionPane.INFORMATION_MESSAGE);
                }
                fillTestLabels(logFileConsumer.getLines());
            }
        });

    }


    private void showMessageDialog(String message, String title, int warningMessage) {
        JOptionPane.showMessageDialog(dialogView, message, title, warningMessage);
    }

    private void updateDoAndResultPanel(String name, String symbol, Integer numberOfSkips) {
        dialogView.setResultPanelText(parsingProfileDo.addPortionAndGetProfile(
                name, symbol, false, true, false, "", numberOfSkips
        ));

    }

    private void updateDoAndResultPanel(String name, String symbol, boolean isIgnore,
                                        boolean isSpecificFormat, String specificFormatText) {
        if(isSpecificFormat) {
            dialogView.setResultPanelText(parsingProfileDo.addPortionAndGetProfile(
                    name, symbol, isIgnore, false, true, specificFormatText
            ));
        } else {
            dialogView.setResultPanelText(parsingProfileDo.addPortionAndGetProfile(
                    name, symbol, isIgnore, false, false, ""
            ));
        }

    }

    private void changeTextClassSeparatorStates(boolean lastPortionTextClass) {
        wasLastPortionTextClass = lastPortionTextClass;
        dialogView.enableTextClass(!lastPortionTextClass);
        dialogView.enableSeparator(lastPortionTextClass);
    }

    private void fullReset() {
        dialogView.setResultPanelText(parsingProfileDo.clearPortions());
        dialogView.setSpecificFormatText("");
        dialogView.setProfileNameText("");
        dialogView.enableSeparator(false);
        dialogView.enableTextClass(true);
        dialogView.getIgnoreButton().setSelected(false);
        dialogView.getSpecificFormatButton().setSelected(false);
        wasLastPortionTextClass = false;
    }

    private void windowClosedOperations() {
        callerPresenter.dialogWindowClosed();

        // if the window was closed, we need to reset the status of the object
        parsingProfileDo = existingProfile == null ? new ParsingProfileDo() : new ParsingProfileDo(existingProfile);
    }


    private void fillTestLabels(LogLine[] lines) {
        if (lines.length < 1) {
            // no lines produced
            showMessageDialog(GuiMessages.NO_LINES_PRODUCED_MESSAGE, GuiMessages.ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
        } else if (lines.length > 1) {
            // more lines than expected
            showMessageDialog(GuiMessages.MORE_LINES_PRODUCED_MESSAGE, GuiMessages.ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
        } else {
            LogLine line = lines[0];
            dialogView.getDatePanel().setVariableLabelText(FileAnalysisScreenPresenter.getDate(line));
            dialogView.getTimePanel().setVariableLabelText(FileAnalysisScreenPresenter.getTime(line));
            dialogView.getIdPanel().setVariableLabelText(line.getIdentifier());
            dialogView.getLevelPanel().setVariableLabelText(line.getLevel());
            dialogView.getOriginPanel().setVariableLabelText(line.getOrigin());
            dialogView.getMessagePanel().setVariableLabelText(line.getMessage());
        }
    }

    @Override
    public void execute() {
        dialogView.setLocationRelativeTo(motherFrame);
        dialogView.setVisible(true);
    }

    @Override
    public JPanel getView() {
        return dialogView.getPanel();
    }
}
