package presentation.parsingprofile;

import presentation.common.GuiConstants;

import javax.swing.JDialog;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;

import static presentation.common.GuiConstants.H_FILE_ANALYSIS_SCREEN_SIZE;
import static presentation.common.GuiConstants.H_GAP;
import static presentation.common.GuiConstants.V_FILE_ANALYSIS_SCREEN_SIZE;
import static presentation.common.GuiConstants.V_GAP;

public class ParsingProfileEditorScreen extends JDialog {

    private JPanel basePanel;

    public ParsingProfileEditorScreen(Frame owner) {
        super(owner, GuiConstants.PARSING_PROFILE_EDITOR_SCREEN_TITLE);
        setWindowClosingBehavior();
        createTabbedPane();
        this.pack();
    }

    private void createTabbedPane() {
        this.rootPane.setLayout(new BorderLayout());
        basePanel = new JPanel(new BorderLayout(H_GAP, V_GAP));
        this.setPreferredSize(new Dimension(H_FILE_ANALYSIS_SCREEN_SIZE, V_FILE_ANALYSIS_SCREEN_SIZE));
        this.rootPane.add(basePanel, BorderLayout.CENTER);
    }

    private void setWindowClosingBehavior() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }


    public JPanel getPanel() {
        return basePanel;
    }
}
