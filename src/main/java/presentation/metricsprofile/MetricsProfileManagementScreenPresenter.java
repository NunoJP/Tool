package presentation.metricsprofile;

import domain.entities.displayobjects.MetricsProfileDo;
import domain.services.MetricsProfileManagementService;
import presentation.common.GuiMessages;
import presentation.common.IViewPresenter;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

public class MetricsProfileManagementScreenPresenter implements IViewPresenter {


    private final MetricsProfileManagementScreen view;
    private final MetricsProfileManagementService service;
    private int selectedItem = -1;
    private MetricsProfileDo[] metricsProfiles;
    private final Runnable parentNotification;

    public MetricsProfileManagementScreenPresenter(Runnable parentNotification) {
        this.parentNotification = parentNotification;
        view = new MetricsProfileManagementScreen();
        service = new MetricsProfileManagementService();
        defineViewBehavior();
        view.getMetricProfilesPanel().setData(new Object[0][]);
    }

    private void defineViewBehavior() {
        view.getMetricProfilesPanel().setLineSelectionOnly();
        view.getUpdateSelectedButton().setEnabled(false);
        view.getDeleteSelectedButton().setEnabled(false);
        view.getMetricProfilesPanel(). addRowSelectionEvent( (event) -> {
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
            MetricsProfileEditorScreenPresenter editorScreenPresenter = new MetricsProfileEditorScreenPresenter(frame, null, this);
            editorScreenPresenter.execute();
            setButtonsEnabled(false);
        }));

        view.getDeleteSelectedButton().addActionListener( (e -> {
            if(userConfirmedOperation()) {
                boolean success = service.deleteProfile(metricsProfiles[selectedItem]);
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
            MetricsProfileEditorScreenPresenter editorScreenPresenter
                    = new MetricsProfileEditorScreenPresenter(frame, metricsProfiles[selectedItem], this);
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

    private boolean userConfirmedOperation() {
        int confirmation = JOptionPane.showConfirmDialog(view,
                GuiMessages.CONFIRM_DELETE_METRIC_PROFILE,
                GuiMessages.PLEASE_CONFIRM_DIALOG_TITLE,
                JOptionPane.YES_NO_OPTION);
        return confirmation == JOptionPane.YES_OPTION;
    }



    @Override
    public JPanel getView() {
        return view;
    }

    @Override
    public void execute() {
        fillTableData();
    }

    private Object[][] convertDataForTable(MetricsProfileDo[] data) {
        Object[][] objects = new Object[data.length][];
        for (int i = 0; i <data.length; i++) {
            objects[i] = new Object[] { data[i].getName(), data[i].getDescription() };
        }
        return objects;
    }

    private void fillTableData() {
        metricsProfiles = service.getMetricsProfiles();
        view.getMetricProfilesPanel().setData(convertDataForTable(metricsProfiles));
    }


    public void updateViewTable() {
        fillTableData();

        // notify the parent that there have been changes to the data model
        parentNotification.run();
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
