package presentation.metricsmonitoring;

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
import java.util.ArrayList;
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
    private MetricsProfileDo metricsProfile;
    private LabelLabelPanel namePanel;
    private JButton stopButton;
    private JFrame motherFrame;
    private KeywordHistogramPanel keywordHistogram;
    private JTabbedPane tabbedPane;
    private JPanel keywordHistogramPanelHolder;
    private PointPlotChartPanel fileSizePanel;

    public MetricsMonitoringScreen(Frame owner, String title, MetricsProfileDo metricsProfile) {
        super(owner, title);
        this.motherFrame = (JFrame) owner;
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
        tabbedPane = new JTabbedPane();
        this.add(tabbedPane, BorderLayout.CENTER);

        createTablesPanel(tabbedPane);

        // File size chart
        if(metricsProfile.isHasFileSize()) {
            List<Pair<Long, Date>> values = new ArrayList<>();
            values.add(Pair.of(1L, new Date()));
            values.add(Pair.of(30L, new Date()));
            values.add(Pair.of(50L, new Date()));
            values.add(Pair.of(60L, new Date()));
            values.add(Pair.of(100L, new Date()));
            values.add(Pair.of(150L, new Date()));
            values.add(Pair.of(190L, new Date()));
            values.add(Pair.of(290L, new Date()));
            values.add(Pair.of(390L, new Date()));
            values.add(Pair.of(490L, new Date()));
            values.add(Pair.of(590L, new Date()));
            values.add(Pair.of(690L, new Date()));
            values.add(Pair.of(890L, new Date()));
            values.add(Pair.of(1190L, new Date()));
            values.add(Pair.of(1290L, new Date()));
            values.add(Pair.of(1390L, new Date()));
            values.add(Pair.of(1590L, new Date()));
            values.add(Pair.of(1690L, new Date()));
            values.add(Pair.of(2190L, new Date()));
            values.add(Pair.of(2290L, new Date()));
            values.add(Pair.of(2390L, new Date()));
            values.add(Pair.of(2590L, new Date()));
            values.add(Pair.of(2690L, new Date()));
            values.add(Pair.of(3490L, new Date()));
            values.add(Pair.of(4650L, new Date()));
            values.add(Pair.of(5590L, new Date()));
            values.add(Pair.of(5520L, new Date()));
            values.add(Pair.of(4490L, new Date()));
            values.add(Pair.of(4390L, new Date()));
            values.add(Pair.of(3390L, new Date()));
            values.add(Pair.of(2290L, new Date()));
            values.add(Pair.of(2590L, new Date()));
            values.add(Pair.of(1290L, new Date()));
            values.add(Pair.of(1590L, new Date()));
            values.add(Pair.of(1690L, new Date()));
            values.add(Pair.of(1790L, new Date()));
            fileSizePanel = new PointPlotChartPanel(values);
            tabbedPane.addTab(GuiConstants.FILE_SIZE_TAB, fileSizePanel);
        }

        // Keyword Histogram
        if(metricsProfile.isHasKeywordHistogram()) {
            keywordHistogram = new KeywordHistogramPanel(motherFrame);
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

    public void setKeywordHistogramData(HashMap<String, Integer> barChartData) {
        keywordHistogram = null;
        keywordHistogram = new KeywordHistogramPanel(motherFrame);
        keywordHistogram.updateChart(barChartData);
        tabbedPane.setComponentAt(2, keywordHistogram);
    }
}
