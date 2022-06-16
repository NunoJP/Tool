package presentation.metricsmonitoring;

import domain.entities.common.Keyword;
import domain.entities.displayobjects.MetricsProfileDo;
import general.util.Pair;
import presentation.common.GuiConstants;
import presentation.common.custom.GeneralTablePanel;
import presentation.common.custom.KeywordHistogramPanel;
import presentation.common.custom.KeywordsOverTimePanel;
import presentation.common.custom.LabelLabelPanel;
import presentation.common.custom.graphs.PointPlotChartPanel;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static presentation.common.GuiConstants.H_FILE_MONITORING_SCREEN_SIZE;
import static presentation.common.GuiConstants.H_GAP;
import static presentation.common.GuiConstants.V_FILE_MONITORING_SCREEN_SIZE;
import static presentation.common.GuiConstants.V_GAP;

public class MetricsMonitoringScreen extends JDialog {

    private GeneralTablePanel kwdThTable;
    private GeneralTablePanel warningsTable;
    private GeneralTablePanel mostCommonWordsTable;
    private final MetricsProfileDo metricsProfile;
    private LabelLabelPanel namePanel;
    private JButton stopButton;
    private final JFrame motherFrame;
    private final Runnable stopReaderAndThread;
    private KeywordHistogramPanel keywordHistogram;
    private JTabbedPane tabbedPane;
    private JPanel keywordHistogramPanelHolder;
    private PointPlotChartPanel fileSizePanel;
    private int fileSizeTabPosition = 1;
    private int keywordHistogramTabPosition = 2;
    private int keywordsOverTimeTabPosition = 3;
    private KeywordsOverTimePanel keywordOverTime;


    public MetricsMonitoringScreen(Frame owner, String title, MetricsProfileDo metricsProfile, Runnable stopReaderAndThread) {
        super(owner, title);
        this.motherFrame = (JFrame) owner;
        this.stopReaderAndThread = stopReaderAndThread;
        setWindowClosingBehavior();
        this.setPreferredSize(new Dimension(H_FILE_MONITORING_SCREEN_SIZE, V_FILE_MONITORING_SCREEN_SIZE));
        this.metricsProfile = metricsProfile;
        this.setLayout(new BorderLayout(H_GAP, V_GAP));
        createComponents();
        pack();
    }

    private void createComponents() {
        int panelIndex = 0;
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
        tabbedPane = new JTabbedPane();
        this.add(tabbedPane, BorderLayout.CENTER);

        createTablesPanel(tabbedPane);

        // File size chart
        if(metricsProfile.hasFileSize()) {
            fileSizePanel = new PointPlotChartPanel();
            tabbedPane.addTab(GuiConstants.FILE_SIZE_TAB, fileSizePanel);
            panelIndex++;
            fileSizeTabPosition = panelIndex;
        }

        // Keyword Histogram
        if(metricsProfile.hasKeywordHistogram()) {
            keywordHistogram = new KeywordHistogramPanel(motherFrame);
            tabbedPane.addTab(GuiConstants.KEYWORD_HISTOGRAM_TAB, keywordHistogram);
            panelIndex++;
            keywordHistogramTabPosition = panelIndex;
        }

        // Keywords over time
        if(metricsProfile.hasKeywordOverTime()) {
            keywordOverTime = new KeywordsOverTimePanel(motherFrame);
            tabbedPane.addTab(GuiConstants.KEYWORD_OVER_TIME_TAB, keywordOverTime);
            panelIndex++;
            keywordsOverTimeTabPosition = panelIndex;
        }

    }

    private void createTablesPanel(JTabbedPane tabbedPane) {

        // neither metric is enabled, so return
        if(!metricsProfile.hasKeywordThreshold() && !metricsProfile.hasMostCommonWords()) {
            return;
        }

        // create tables for Keyword Threshold + Warnings
        if(metricsProfile.hasKeywordThreshold()) {
            kwdThTable = new GeneralTablePanel(GuiConstants.KEYWORD_THRESHOLD_LABEL,
                    new String[]{GuiConstants.KEYWORD_COLUMN, GuiConstants.VALUE_COLUMN}, false);
            kwdThTable.setGeneralSelection(false);
            warningsTable = new GeneralTablePanel(GuiConstants.WARNINGS_LABEL,
                    new String[]{GuiConstants.LEVEL_COLUMN, GuiConstants.MESSAGE_COLUMN}, false);
            warningsTable.setGeneralSelection(false);
        }

        // create table for Most common words
        if(metricsProfile.hasMostCommonWords()) {
            mostCommonWordsTable = new GeneralTablePanel(GuiConstants.MOST_COMMON_WORDS_LABEL,
                    new String[]{GuiConstants.WORD_COLUMN, GuiConstants.VALUE_COLUMN}, false);
            mostCommonWordsTable.setGeneralSelection(false);
            mostCommonWordsTable.setIntegerColumnsSort(new int[] { 1 } );
        }


        JPanel holder = new JPanel(new BorderLayout(H_GAP, V_GAP));

        // Both metrics are enabled
        if(metricsProfile.hasKeywordThreshold() && metricsProfile.hasMostCommonWords()) {

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
            if(metricsProfile.hasKeywordThreshold()) {
                // Keyword Threshold + Warnings
                holder.add(new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, kwdThTable, warningsTable));
            } else if(metricsProfile.hasMostCommonWords()) {
                // Most common words
                holder.add(mostCommonWordsTable);
            }
        }

        tabbedPane.addTab(GuiConstants.TABLES_TAB, holder);

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

    public void setKeywordHistogramData(HashMap<Keyword, Integer> barChartData) {
        keywordHistogram = null;
        keywordHistogram = new KeywordHistogramPanel(motherFrame);
        keywordHistogram.updateChart(barChartData);
        tabbedPane.setComponentAt(keywordHistogramTabPosition, keywordHistogram);
    }

    public void setFileSizeData(List<Pair<Long, Date>> fileSizeData) {
        fileSizePanel = null;
        fileSizePanel = new PointPlotChartPanel(fileSizeData);
        tabbedPane.setComponentAt(fileSizeTabPosition, fileSizePanel);
    }

    public void setKeywordOverTimeData(HashMap<Keyword, List<Pair<Long, Date>>> chartData) {
        keywordOverTime = null;
        keywordOverTime = new KeywordsOverTimePanel(motherFrame);
        keywordOverTime.updateChart(chartData);
        tabbedPane.setComponentAt(keywordsOverTimeTabPosition, keywordOverTime);
    }
}
