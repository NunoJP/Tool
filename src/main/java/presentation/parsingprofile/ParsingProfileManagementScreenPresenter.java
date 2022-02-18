package presentation.parsingprofile;

import domain.entities.displayobjects.ParsingProfileDo;
import domain.services.ParsingProfileManagementService;
import presentation.common.GuiMessages;
import presentation.common.IViewPresenter;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

public class ParsingProfileManagementScreenPresenter implements IViewPresenter {

    private final ParsingProfileManagementScreen view;
    private final ParsingProfileManagementService service;
    private int selectedItem = -1;
    private ParsingProfileDo[] parsingProfiles;
    private final Runnable parentNotification;

    public ParsingProfileManagementScreenPresenter(Runnable parentNotification) {
        this.parentNotification = parentNotification;
        view = new ParsingProfileManagementScreen();
        service = new ParsingProfileManagementService();
        defineViewBehavior();
    }

    private void defineViewBehavior() {
        view.getParsingProfilesPanel().setLineSelectionOnly();
        view.getUpdateSelectedButton().setEnabled(false);
        view.getDeleteSelectedButton().setEnabled(false);
        view.getParsingProfilesPanel().addRowSelectionEvent( (event) -> {
            ListSelectionModel listSelectionModel = (ListSelectionModel) event.getSource();
            if(listSelectionModel.isSelectionEmpty()) {
                view.getUpdateSelectedButton().setEnabled(false);
                view.getDeleteSelectedButton().setEnabled(false);
                selectedItem = -1;
            } else {
                view.getUpdateSelectedButton().setEnabled(true);
                view.getDeleteSelectedButton().setEnabled(true);
                selectedItem = listSelectionModel.getSelectedIndices()[0];
            }
        });

        view.getNewProfileButton().addActionListener( (e -> {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(view);
            ParsingProfileEditorScreenPresenter editorScreenPresenter = new ParsingProfileEditorScreenPresenter(frame, null, this);
            editorScreenPresenter.execute();
            setButtonsEnabled(false);
        }));

        view.getDeleteSelectedButton().addActionListener( (e -> {
            if(userConfirmedOperation()) {
                boolean success = service.deleteProfile(parsingProfiles[selectedItem]);
                if(success) {
                    showMessageDialog(GuiMessages.DELETE_SUCCESSFUL, GuiMessages.SUCCESS_TITLE);
                } else {
                    showMessageDialog(GuiMessages.DELETE_FAILED, GuiMessages.FAILURE_TITLE);
                }
                updateViewTable();
            }
        }));

        view.getUpdateSelectedButton().addActionListener( (e -> {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(view);
            ParsingProfileEditorScreenPresenter editorScreenPresenter
                    = new ParsingProfileEditorScreenPresenter(frame, parsingProfiles[selectedItem], this);
            editorScreenPresenter.execute();
            setButtonsEnabled(false);
        }));
    }

    private void showMessageDialog(String deleteSuccessful, String successTitle) {
        JOptionPane.showMessageDialog(view,
                deleteSuccessful,
                successTitle,
                JOptionPane.INFORMATION_MESSAGE);
    }


    @Override
    public void execute() {
        fillTableData();
    }

    @Override
    public JPanel getView() {
        return view;
    }

    private boolean userConfirmedOperation() {
        int confirmation = JOptionPane.showConfirmDialog(view,
                GuiMessages.CONFIRM_DELETE_PARSING_PROFILE,
                GuiMessages.PLEASE_CONFIRM_DIALOG_TITLE,
                JOptionPane.YES_NO_OPTION);
        return confirmation == JOptionPane.YES_OPTION;
    }


    private Object[][] convertDataForTable(ParsingProfileDo[] data) {
        Object[][] objects = new Object[data.length][];
        for (int i = 0; i <data.length; i++) {
            objects[i] = new Object[] { data[i].getName(), data[i].getDescription() };
        }
        return objects;
    }

    public void updateViewTable() {
        fillTableData();

        // notify the parent that there have been changes to the data model
        parentNotification.run();
    }

    private void fillTableData() {
        parsingProfiles = service.getParsingProfiles();
        view.getParsingProfilesPanel().setData(convertDataForTable(parsingProfiles));
    }

    public void dialogWindowClosed() {
        setButtonsEnabled(true);
    }

    private void setButtonsEnabled(boolean enabled) {
        view.getNewProfileButton().setEnabled(enabled);
        view.getUpdateSelectedButton().setEnabled(enabled);
        view.getDeleteSelectedButton().setEnabled(enabled);
    }
}
