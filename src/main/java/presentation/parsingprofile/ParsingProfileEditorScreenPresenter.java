package presentation.parsingprofile;

import domain.entities.Converter;
import domain.entities.Validator;
import domain.entities.common.SeparatorEnum;
import domain.entities.common.TextClassesEnum;
import domain.entities.displayobjects.ParsingProfileDo;
import domain.services.ParsingProfileManagementService;
import presentation.common.GuiMessages;
import presentation.common.IViewPresenter;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ParsingProfileEditorScreenPresenter implements IViewPresenter {

    private final JFrame motherFrame;
    private final ParsingProfileEditorScreen dialogView;
    private final ParsingProfileManagementService service;
    private final ParsingProfileDo parsingProfileDo;
    private final ParsingProfileManagementScreenPresenter callerPresenter;
    private boolean wasLastPortionTextClass;

    public ParsingProfileEditorScreenPresenter(JFrame motherFrame, ParsingProfileDo existingProfile, ParsingProfileManagementScreenPresenter parsingProfileManagementScreenPresenter) {
        this.motherFrame = motherFrame;
        this.callerPresenter = parsingProfileManagementScreenPresenter;
        dialogView = new ParsingProfileEditorScreen(motherFrame);
        service = new ParsingProfileManagementService();
        parsingProfileDo = new ParsingProfileDo();
        defineViewBehavior();

    }

    private void defineViewBehavior() {

        // Clear button behavior
        dialogView.getClearButton().addActionListener(actionEvent ->  {
            fullReset();
        });

        // Text class Add button behavior
        dialogView.getTextClassAddButton().addActionListener( actionEvent -> {
            // if there is a specific format chosen by the user
            if(dialogView.getSpecificFormatButton().isSelected()){
                dialogView.setResultPanelText(parsingProfileDo.addPortionAndGetProfile(
                        ((TextClassesEnum) dialogView.getTextClassComboBox().getSelectedItem()).getName(),
                        dialogView.getIgnoreButton().isSelected(),
                        false,
                        dialogView.getSpecificFormatButton().isSelected(),
                        dialogView.getSpecificFormatText()
                ));
            } else {
                dialogView.setResultPanelText(parsingProfileDo.addPortionAndGetProfile(
                        ((TextClassesEnum) dialogView.getTextClassComboBox().getSelectedItem()).getName(),
                        dialogView.getIgnoreButton().isSelected(),
                        false,
                        false
                ));
            }
            // Added a Text Class so now it's a Separator
            changeTextClassSeparatorStates(true);
        });

        // Separator Add button behavior
        dialogView.getSeparatorAddButton().addActionListener(actionEvent -> {
            dialogView.setResultPanelText(parsingProfileDo.addPortionAndGetProfile(
                    ((SeparatorEnum) dialogView.getSeparatorClassComboBox().getSelectedItem()).getName(),
                    false,
                    true,
                    false
            ));
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
                JOptionPane.showMessageDialog(dialogView,
                        GuiMessages.PROFILE_NAME_INVALID_OR_EMPTY,
                        GuiMessages.PROFILE_NAME_INVALID_OR_EMPTY_TITLE,
                        JOptionPane.WARNING_MESSAGE);
            } else {
                parsingProfileDo.setName(profileName);
                parsingProfileDo.finishProfile();
                service.createProfile(parsingProfileDo);
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
