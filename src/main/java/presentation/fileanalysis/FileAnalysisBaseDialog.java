package presentation.fileanalysis;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static presentation.common.GuiConstants.H_FILE_ANALYSIS_SCREEN_SIZE;
import static presentation.common.GuiConstants.H_GAP;
import static presentation.common.GuiConstants.V_FILE_ANALYSIS_SCREEN_SIZE;
import static presentation.common.GuiConstants.V_GAP;

public class FileAnalysisBaseDialog extends JDialog {

    private JTabbedPane tabbedPane;
    private Runnable stopReaderAndThread;

    public FileAnalysisBaseDialog(Frame owner, String title, Runnable stopReaderAndThread) {
        super(owner, title);
        this.stopReaderAndThread = stopReaderAndThread;
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
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                stopReaderAndThread.run();
            }
        });
    }

    public void addTab(String name, JPanel panel){
        tabbedPane.addTab(name, panel);
    }
}
