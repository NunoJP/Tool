package presentation.metricsprofile;

import domain.entities.displayobjects.MetricsProfileDo;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MetricsProfileEditorScreenPresenter {


    private final JFrame motherFrame;
    private final MetricsProfileDo existingProfile;
    private final MetricsProfileManagementScreenPresenter callerPresenter;
    private final MetricsProfileEditorScreen dialogView;

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

        // A.1.14.1
        dialogView.getAddKwdButton().addActionListener( actionEvent -> {

        });

        // A.1.14.1
        dialogView.getUpdateKwdButton().addActionListener( actionEvent -> {

        });

        // A.1.14.1
        dialogView.getDeleteKwdButton().addActionListener( actionEvent -> {

        });

        // A.1.13.1
        dialogView.getSaveProfileButton().addActionListener( actionEvent -> {

        });

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
        dialogView.getUnitComboBox().setSelectedIndex(0);
        dialogView.getValueInput().setValue(0);
        dialogView.resetKwdTable();
    }
}
