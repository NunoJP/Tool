package presentation.parsingprofile;

import presentation.common.GuiConstants;
import presentation.common.custom.GeneralTablePanel;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

import static presentation.common.GuiConstants.H_GAP;
import static presentation.common.GuiConstants.V_GAP;

public class ParsingProfileManagementScreen extends JPanel {

    private GeneralTablePanel parsingProfilesPanel;
    private JButton newProfileButton;
    private JButton updateSelectedButton;
    private JButton deleteSelectedButton;

    public ParsingProfileManagementScreen() {
        this.setLayout(new BorderLayout(H_GAP, V_GAP));
        createComponents();
    }

    private void createComponents() {
        this.add(createButtonPanel(), BorderLayout.NORTH);
        this.add(createParsingProfilesPanel(), BorderLayout.CENTER);
    }

    private JPanel createParsingProfilesPanel() {
        parsingProfilesPanel = new GeneralTablePanel(
                new String[]{GuiConstants.NAME_COLUMN, GuiConstants.DESCRIPTION_COLUMN}, false
        );
        parsingProfilesPanel.changeColumnWidths(new int[] { 100, 500 });
        return parsingProfilesPanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, H_GAP, V_GAP));
        newProfileButton = new JButton(GuiConstants.NEW_PROFILE_BUTTON);
        updateSelectedButton = new JButton(GuiConstants.UPDATE_SELECTED_BUTTON);
        deleteSelectedButton = new JButton(GuiConstants.DELETE_SELECTED_BUTTON);
        buttonPanel.add(newProfileButton);
        buttonPanel.add(updateSelectedButton);
        buttonPanel.add(deleteSelectedButton);
        return buttonPanel;
    }

    public GeneralTablePanel getParsingProfilesPanel() {
        return parsingProfilesPanel;
    }

    public JButton getNewProfileButton() {
        return newProfileButton;
    }

    public JButton getUpdateSelectedButton() {
        return updateSelectedButton;
    }

    public JButton getDeleteSelectedButton() {
        return deleteSelectedButton;
    }
}
