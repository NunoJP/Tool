package presentation.fileanalysis;

import domain.entities.displayobjects.MetricsProfileDo;
import presentation.common.GuiConstants;
import presentation.common.custom.GeneralTablePanel;
import presentation.common.custom.KeywordHistogramPanel;
import presentation.common.custom.KeywordsOverTimePanel;
import presentation.common.custom.LabelLabelPanel;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.GridLayout;

import static presentation.common.GuiConstants.H_GAP;
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

        int[] rowsAndCols = calculateRowsAndCols();
        JPanel tablesPanel = new JPanel(new GridLayout(rowsAndCols[0], rowsAndCols[1]));
        tabbedPane.addTab(GuiConstants.TABLES_TAB, tablesPanel);

        JPanel statisticsPanel = createStatisticsPanel();

        // Log level tables
        logLevelTable = new GeneralTablePanel(GuiConstants.LOG_LEVEL_DISTRIBUTION_LABEL,
                new String[]{GuiConstants.LEVEL_COLUMN, GuiConstants.PERCENTAGE_COLUMN}, false);
        logLevelTable.setGeneralSelection(false);
        JPanel holder = new JPanel( new BorderLayout(H_GAP, V_GAP) );
        holder.add(statisticsPanel, BorderLayout.NORTH);
        holder.add(logLevelTable, BorderLayout.CENTER);
        tablesPanel.add(holder);

        // Most common words
        if(metricsProfile.isHasMostCommonWords()) {
            mostCommonWordsTable = new GeneralTablePanel(GuiConstants.MOST_COMMON_WORDS_LABEL,
                    new String[]{GuiConstants.WORD_COLUMN, GuiConstants.VALUE_COLUMN}, false);
            mostCommonWordsTable.setGeneralSelection(false);
            mostCommonWordsTable.setIntegerColumnsSort(new int[] { 1 } );
            tablesPanel.add(mostCommonWordsTable);
        }

        // Keyword Threshold + Warnings
        if(metricsProfile.isHasKeywordThreshold()) {
            kwdThTable = new GeneralTablePanel(GuiConstants.KEYWORD_THRESHOLD_LABEL,
                    new String[]{GuiConstants.KEYWORD_COLUMN, GuiConstants.VALUE_COLUMN}, false);
            kwdThTable.setGeneralSelection(false);
            warningsTable = new GeneralTablePanel(GuiConstants.WARNINGS_LABEL,
                    new String[]{GuiConstants.LEVEL_COLUMN, GuiConstants.MESSAGE_COLUMN}, false);
            warningsTable.setGeneralSelection(false);
            tablesPanel.add(kwdThTable);
            tablesPanel.add(warningsTable);
        }


        // MCW - Tab
        if(metricsProfile.isHasKeywordHistogram()) {
            KeywordHistogramPanel keywordHistogram = new KeywordHistogramPanel();
            tabbedPane.addTab(GuiConstants.MOST_COMMON_WORDS_TAB, keywordHistogram);
        }


        // KwdOT - Tab
        if(metricsProfile.isHasKeywordOverTime()) {
            KeywordsOverTimePanel keywordOverTime = new KeywordsOverTimePanel();
            tabbedPane.addTab(GuiConstants.KEYWORD_OVER_TIME_TAB, keywordOverTime);
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
