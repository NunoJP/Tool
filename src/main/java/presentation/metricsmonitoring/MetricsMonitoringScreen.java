package presentation.metricsmonitoring;

import domain.entities.displayobjects.MetricsProfileDo;
import presentation.common.GuiConstants;
import presentation.common.custom.GeneralTablePanel;
import presentation.common.custom.KeywordHistogramPanel;
import presentation.common.custom.KeywordsOverTimePanel;
import presentation.common.custom.LabelLabelPanel;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;

import static presentation.common.GuiConstants.H_FILE_MONITORING_SCREEN_SIZE;
import static presentation.common.GuiConstants.H_GAP;
import static presentation.common.GuiConstants.V_FILE_MONITORING_SCREEN_SIZE;
import static presentation.common.GuiConstants.V_GAP;

public class MetricsMonitoringScreen extends JDialog {

    private GeneralTablePanel kwdThTable;
    private GeneralTablePanel warningsTable;
    private GeneralTablePanel mostCommonWordsTable;
    private MetricsProfileDo metricsProfile;
    private LabelLabelPanel namePanel;
    private JButton stopButton;

    public MetricsMonitoringScreen(Frame owner, String title, MetricsProfileDo metricsProfile) {
        super(owner, title);
        setWindowClosingBehavior();
        this.setPreferredSize(new Dimension(H_FILE_MONITORING_SCREEN_SIZE, V_FILE_MONITORING_SCREEN_SIZE));
        this.metricsProfile = metricsProfile;
        this.setLayout(new BorderLayout(H_GAP, V_GAP));
        createComponents();
        pack();
    }

    private void createComponents() {

        // NORTH
        namePanel = new LabelLabelPanel(GuiConstants.NAME_LABEL);
        JPanel nameSpacer = new JPanel(new BorderLayout(H_GAP, V_GAP));
        nameSpacer.add(namePanel, BorderLayout.CENTER);
        this.add(nameSpacer, BorderLayout.NORTH);

        // SOUTH
        stopButton = new JButton(GuiConstants.STOP_BUTTON);
        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, H_GAP, V_GAP));
        southPanel.add(stopButton);
        this.add(southPanel, BorderLayout.SOUTH);


        // CENTER
        JTabbedPane tabbedPane = new JTabbedPane();
        this.add(tabbedPane, BorderLayout.CENTER);

        createTablesPanel(tabbedPane);

        // File size chart
        if(metricsProfile.isHasFileSize()) {
            JPanel jPanel = new JPanel();
            tabbedPane.addTab(GuiConstants.FILE_SIZE_TAB, jPanel);
        }


        // Keyword Histogram
        if(metricsProfile.isHasKeywordHistogram()) {
            KeywordHistogramPanel keywordHistogram = new KeywordHistogramPanel();
            tabbedPane.addTab(GuiConstants.KEYWORD_HISTOGRAM_TAB, keywordHistogram);
        }

        // Keywords over time
        if(metricsProfile.isHasKeywordOverTime()) {
            KeywordsOverTimePanel keywordOverTime = new KeywordsOverTimePanel();
            tabbedPane.addTab(GuiConstants.KEYWORD_OVER_TIME_TAB, keywordOverTime);
        }

    }

    private void createTablesPanel(JTabbedPane tabbedPane) {

        // neither metric is enabled, so return
        if(!metricsProfile.isHasKeywordThreshold() && !metricsProfile.isHasMostCommonWords()) {
            return;
        }

        // create tables for Keyword Threshold + Warnings
        if(metricsProfile.isHasKeywordThreshold()) {
            kwdThTable = new GeneralTablePanel(GuiConstants.KEYWORD_THRESHOLD_LABEL,
                    new String[]{GuiConstants.KEYWORD_COLUMN, GuiConstants.VALUE_COLUMN}, false);
            kwdThTable.setGeneralSelection(false);
            warningsTable = new GeneralTablePanel(GuiConstants.WARNINGS_LABEL,
                    new String[]{GuiConstants.LEVEL_COLUMN, GuiConstants.MESSAGE_COLUMN}, false);
            warningsTable.setGeneralSelection(false);
        }

        // create table for Most common words
        if(metricsProfile.isHasMostCommonWords()) {
            mostCommonWordsTable = new GeneralTablePanel(GuiConstants.MOST_COMMON_WORDS_LABEL,
                    new String[]{GuiConstants.WORD_COLUMN, GuiConstants.VALUE_COLUMN}, false);
            mostCommonWordsTable.setGeneralSelection(false);
            mostCommonWordsTable.setIntegerColumnsSort(new int[] { 1 } );
        }


        JPanel holder = new JPanel(new BorderLayout(H_GAP, V_GAP));

        // Both metrics are enabled
        if(metricsProfile.isHasKeywordThreshold() && metricsProfile.isHasMostCommonWords()) {

            int height = V_FILE_MONITORING_SCREEN_SIZE / 5;
            int width = H_FILE_MONITORING_SCREEN_SIZE / 5;

            // Keyword Threshold + Warnings
            JSplitPane thSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, kwdThTable, warningsTable);
            thSplitPane.setMinimumSize(new Dimension(width, height));

            // Join Keyword Threshold + Warnings and Most common words with vertical split
            mostCommonWordsTable.setMinimumSize(new Dimension(H_FILE_MONITORING_SCREEN_SIZE, height));
            holder.add(new JSplitPane(JSplitPane.VERTICAL_SPLIT, mostCommonWordsTable, thSplitPane));

        } else {
            // Only one of them is enabled
            if(metricsProfile.isHasKeywordThreshold()) {
                // Keyword Threshold + Warnings
                holder.add(new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, kwdThTable, warningsTable));
            } else if(metricsProfile.isHasMostCommonWords()) {
                // Most common words
                holder.add(mostCommonWordsTable);
            }
        }

        tabbedPane.addTab(GuiConstants.TABLES_TAB, holder);

    }


    private void setWindowClosingBehavior() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    public GeneralTablePanel getKwdThTable() {
        return kwdThTable;
    }

    public GeneralTablePanel getWarningsTable() {
        return warningsTable;
    }

    public GeneralTablePanel getMostCommonWordsTable() {
        return mostCommonWordsTable;
    }

    public MetricsProfileDo getMetricsProfile() {
        return metricsProfile;
    }

    public LabelLabelPanel getNamePanel() {
        return namePanel;
    }

    public JButton getStopButton() {
        return stopButton;
    }
}
