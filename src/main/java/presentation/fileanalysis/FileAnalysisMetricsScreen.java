package presentation.fileanalysis;

import presentation.common.GuiConstants;
import presentation.common.custom.GeneralTablePanel;
import presentation.common.custom.KeywordHistogramPanel;
import presentation.common.custom.KeywordsOverTimePanel;
import presentation.common.custom.LabelLabelPanel;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
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

    public FileAnalysisMetricsScreen(){
        this.setLayout(new GridLayout(2, 3));
        createComponents();
    }

    private void createComponents() {
        JPanel holder = new JPanel( new GridLayout(2, 1, H_GAP, V_GAP) );
        JPanel statisticsPanel = createStatisticsPanel();
        logLevelTable = new GeneralTablePanel(GuiConstants.LOG_LEVEL_DISTRIBUTION_LABEL,
                new String[]{GuiConstants.LEVEL_COLUMN, GuiConstants.PERCENTAGE_COLUMN}, false);
        logLevelTable.setGeneralSelection(false);
        holder.add(statisticsPanel);
        holder.add(logLevelTable);
        kwdThTable = new GeneralTablePanel(GuiConstants.KEYWORD_THRESHOLD_LABEL,
                new String[]{GuiConstants.KEYWORD_COLUMN, GuiConstants.VALUE_COLUMN}, false);
        kwdThTable.setGeneralSelection(false);
        warningsTable = new GeneralTablePanel(GuiConstants.WARNINGS_LABEL,
                new String[]{GuiConstants.LEVEL_COLUMN, GuiConstants.MESSAGE_COLUMN}, false);
        warningsTable.setGeneralSelection(false);
        KeywordHistogramPanel keywordHistogram = new KeywordHistogramPanel();
        mostCommonWordsTable = new GeneralTablePanel(GuiConstants.MOST_COMMON_WORDS_LABEL,
                new String[]{GuiConstants.WORD_COLUMN, GuiConstants.VALUE_COLUMN}, false);
        mostCommonWordsTable.setGeneralSelection(false);
        KeywordsOverTimePanel keywordOverTime = new KeywordsOverTimePanel();
        this.add(holder);
        this.add(kwdThTable);
        this.add(warningsTable);
        this.add(keywordHistogram);
        this.add(mostCommonWordsTable);
        this.add(keywordOverTime);
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
