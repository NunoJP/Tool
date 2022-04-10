package presentation.fileanalysis;

import domain.entities.displayobjects.MetricsProfileDo;
import presentation.common.GuiConstants;
import presentation.common.custom.GeneralTablePanel;
import presentation.common.custom.KeywordHistogramPanel;
import presentation.common.custom.KeywordsOverTimePanel;
import presentation.common.custom.LabelLabelPanel;

import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import static presentation.common.GuiConstants.H_FILE_MONITORING_SCREEN_SIZE;
import static presentation.common.GuiConstants.H_GAP;
import static presentation.common.GuiConstants.V_FILE_MONITORING_SCREEN_SIZE;
import static presentation.common.GuiConstants.V_GAP;

public class FileAnalysisMetricsScreen extends JPanel {

    private GeneralTablePanel logLevelTable;
    private GeneralTablePanel kwdThTable;
    private GeneralTablePanel warningsTable;
    private GeneralTablePanel mostCommonWordsTable;
    private LabelLabelPanel fileNamePanel;
    private LabelLabelPanel startDatePanel;
    private LabelLabelPanel endDatePanel;
    private MetricsProfileDo metricsProfile;
    private int numberOfItems;

    public FileAnalysisMetricsScreen(MetricsProfileDo metricsProfile){
        this.metricsProfile = metricsProfile;
        this.setLayout(new BorderLayout(H_GAP, V_GAP));
        createComponents();
    }

    private int[] calculateRowsAndCols() {
        numberOfItems = 1;
        if(metricsProfile.isHasFileSize()) {
            numberOfItems++;
        }
        if(metricsProfile.isHasKeywordThreshold()) {
            numberOfItems++;
        }
        if(metricsProfile.isHasMostCommonWords()) {
            numberOfItems++;
        }

        if(numberOfItems >= 3) {
            return new int[] { 2, 2 };
        } else {
            return new int[]{ numberOfItems, 1 };
        }
    }

    private void createComponents() {
        JTabbedPane tabbedPane = new JTabbedPane();
        this.add(tabbedPane, BorderLayout.CENTER);

        createTablesPanel(tabbedPane);


        // MCW - Tab
        if(metricsProfile.isHasKeywordHistogram()) {
            KeywordHistogramPanel keywordHistogram = new KeywordHistogramPanel();
            tabbedPane.addTab(GuiConstants.KEYWORD_HISTOGRAM_TAB, keywordHistogram);
        }


        // KwdOT - Tab
        if(metricsProfile.isHasKeywordOverTime()) {
            KeywordsOverTimePanel keywordOverTime = new KeywordsOverTimePanel();
            tabbedPane.addTab(GuiConstants.KEYWORD_OVER_TIME_TAB, keywordOverTime);
        }
    }

    private void createTablesPanel(JTabbedPane tabbedPane) {
        JPanel tablesPanel = new JPanel(new BorderLayout());
        tabbedPane.addTab(GuiConstants.TABLES_TAB, tablesPanel);

        int height = V_FILE_MONITORING_SCREEN_SIZE / 4;
        int width = H_FILE_MONITORING_SCREEN_SIZE / 4;

        // Log level tables + statistics
        JPanel statisticsPanel = createStatisticsPanel();
        logLevelTable = new GeneralTablePanel(GuiConstants.LOG_LEVEL_DISTRIBUTION_LABEL,
                new String[]{GuiConstants.LEVEL_COLUMN, GuiConstants.PERCENTAGE_COLUMN}, false);
        logLevelTable.setGeneralSelection(false);
        JPanel logLevelTable = new JPanel( new BorderLayout(H_GAP, V_GAP) );
        logLevelTable.add(statisticsPanel, BorderLayout.NORTH);
        logLevelTable.add(this.logLevelTable, BorderLayout.CENTER);
        logLevelTable.setMinimumSize(new Dimension(width, height));

        // Most common words
        if(metricsProfile.isHasMostCommonWords()) {
            mostCommonWordsTable = new GeneralTablePanel(GuiConstants.MOST_COMMON_WORDS_LABEL,
                    new String[]{GuiConstants.WORD_COLUMN, GuiConstants.VALUE_COLUMN}, false);
            mostCommonWordsTable.setGeneralSelection(false);
            mostCommonWordsTable.setIntegerColumnsSort(new int[] { 1 } );
            mostCommonWordsTable.setMinimumSize(new Dimension(width, height));
        }

        // Keyword Threshold + Warnings
        if(metricsProfile.isHasKeywordThreshold()) {
            kwdThTable = new GeneralTablePanel(GuiConstants.KEYWORD_THRESHOLD_LABEL,
                    new String[]{GuiConstants.KEYWORD_COLUMN, GuiConstants.VALUE_COLUMN}, false);
            kwdThTable.setGeneralSelection(false);
            kwdThTable.setMinimumSize(new Dimension(width, height));
            warningsTable = new GeneralTablePanel(GuiConstants.WARNINGS_LABEL,
                    new String[]{GuiConstants.LEVEL_COLUMN, GuiConstants.MESSAGE_COLUMN}, false);
            warningsTable.setGeneralSelection(false);
            warningsTable.setMinimumSize(new Dimension(width, height));
        }


        if(metricsProfile.isHasMostCommonWords() && metricsProfile.isHasKeywordThreshold()) {
            // two top slots are log level and MCW
            JSplitPane topSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, logLevelTable, mostCommonWordsTable);
            topSplitPane.setMinimumSize(new Dimension(width, height));
            // Bottom slot is for Keyword Threshold + Warnings
            JSplitPane thSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, kwdThTable, warningsTable);
            thSplitPane.setMinimumSize(new Dimension(width, height));
            // Join them
            JSplitPane verticalSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, topSplitPane, thSplitPane);
            tablesPanel.add(verticalSplit);
        } else {
            if(metricsProfile.isHasMostCommonWords()) {
                // top row is log level, bottom row is MCW
                JSplitPane verticalSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, logLevelTable, mostCommonWordsTable);
                tablesPanel.add(verticalSplit);
            } else if(metricsProfile.isHasKeywordThreshold()) {
                // top row is log level
                // bottom row is KwdTh + Warnings
                JSplitPane thSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, kwdThTable, warningsTable);
                JSplitPane verticalSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, logLevelTable, thSplitPane);
                tablesPanel.add(verticalSplit);
            }
        }
    }

    private JPanel createStatisticsPanel() {
        JPanel statsPanel = new JPanel(new GridLayout(3, 1));
        statsPanel.setBorder(new EmptyBorder(V_GAP, H_GAP, V_GAP, H_GAP));
        fileNamePanel = new LabelLabelPanel(GuiConstants.FILE_INDICATION_LABEL);
        statsPanel.add(fileNamePanel);
        startDatePanel = new LabelLabelPanel(GuiConstants.START_DATE_LABEL);
        statsPanel.add(startDatePanel);
        endDatePanel = new LabelLabelPanel(GuiConstants.END_DATE_LABEL);
        statsPanel.add(endDatePanel);
        return statsPanel;
    }

    public GeneralTablePanel getLogLevelTable() {
        return logLevelTable;
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

    public LabelLabelPanel getFileNamePanel() {
        return fileNamePanel;
    }

    public LabelLabelPanel getStartDatePanel() {
        return startDatePanel;
    }

    public LabelLabelPanel getEndDatePanel() {
        return endDatePanel;
    }
}
