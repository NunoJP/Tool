package presentation.metricsprofile;

import domain.entities.Validator;
import domain.entities.common.Keyword;
import domain.entities.common.ThresholdTypeEnum;
import domain.entities.common.ThresholdUnitEnum;
import domain.entities.displayobjects.MetricsProfileDo;
import presentation.common.GuiMessages;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
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
    private int selectedKwdTableItemIndex = -1;

    public MetricsProfileEditorScreenPresenter(JFrame motherFrame, MetricsProfileDo existingProfile,
                                               MetricsProfileManagementScreenPresenter metricsProfileManagementScreenPresenter) {
        this.motherFrame = motherFrame;
        this.existingProfile = existingProfile == null ? new MetricsProfileDo() : existingProfile;
        this.callerPresenter = metricsProfileManagementScreenPresenter;
        this.dialogView = new MetricsProfileEditorScreen(motherFrame);
        populateViewWithExistingProfile();
        defineViewBehavior();
    }

    private void populateViewWithExistingProfile() {
        // A.1.15
    }


    private void defineViewBehavior() {
        dialogView.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
                callerPresenter.dialogWindowClosed();
            }
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

        // A.1.14.1
        dialogView.getDeleteKwdButton().addActionListener( actionEvent -> {
            if(selectedKwdTableItemIndex != -1) {
                // Not adding a confirmation dialog, if the user mistakenly deletes it, it is simple enough to add again
                keywords.remove(selectedKwdTableItemIndex);
                selectedKwdTableItemIndex = -1;
            }
        });

        // A.1.13.1
        dialogView.getSaveProfileButton().addActionListener( actionEvent -> {

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
//                else { // selected the same row twice
//                    listSelectionModel.clearSelection();
//                    dialogView.getUpdateKwdButton().setEnabled(false);
//                    dialogView.getDeleteKwdButton().setEnabled(false);
//                    selectedKwdTableItemIndex = -1;
//                }
            }
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
        dialogView.getCaseSensitiveButton().setSelected(false);
        dialogView.getUpdateKwdButton().setEnabled(false);
        dialogView.getDeleteKwdButton().setEnabled(false);
        dialogView.getThresholdComboBox().setSelectedIndex(0);
        dialogView.getThresholdUnitComboBox().setSelectedIndex(0);
        dialogView.getThresholdValueInput().setValue(0);
        dialogView.resetKwdTable();
    }

    private Object[][] convertDataForTable(ArrayList<Keyword> data) {
        Object[][] objects = new Object[data.size()][];
        for (int i = 0; i <data.size(); i++) {
            objects[i] = new Object[] {data.get(i).getKeywordText(), data.get(i).isCaseSensitive(),
                    data.get(i).getThresholdType().getSymbol() + " " + data.get(i).getThresholdValue()
                            + " " + data.get(i).getThresholdUnit().getSymbol() };
        }
        return objects;
    }
}
