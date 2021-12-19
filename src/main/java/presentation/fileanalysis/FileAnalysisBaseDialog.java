package presentation.fileanalysis;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;

import static presentation.common.GuiConstants.H_FILE_ANALYSIS_SCREEN_SIZE;
import static presentation.common.GuiConstants.H_GAP;
import static presentation.common.GuiConstants.V_FILE_ANALYSIS_SCREEN_SIZE;
import static presentation.common.GuiConstants.V_GAP;

public class FileAnalysisBaseDialog extends JDialog {

    private JTabbedPane tabbedPane;

    public FileAnalysisBaseDialog(Frame owner, String title) {
        super(owner, title);
        setWindowClosingBehavior();
        createTabbedPane();
        this.pack();
    }

    private void createTabbedPane() {
        this.rootPane.setLayout(new BorderLayout());
        JPanel basePanel = new JPanel(new BorderLayout(H_GAP, V_GAP));
        this.setPreferredSize(new Dimension(H_FILE_ANALYSIS_SCREEN_SIZE, V_FILE_ANALYSIS_SCREEN_SIZE));
        this.rootPane.add(basePanel, BorderLayout.CENTER);
        tabbedPane = new JTabbedPane();
        basePanel.add(tabbedPane, BorderLayout.CENTER);
    }

    private void setWindowClosingBehavior() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    public void addTab(String name, JPanel panel){
        tabbedPane.addTab(name, panel);
    }
}
