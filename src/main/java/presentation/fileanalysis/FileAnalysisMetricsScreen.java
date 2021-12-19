package presentation.fileanalysis;

import presentation.common.GuiConstants;
import presentation.common.custom.GeneralTablePanel;
import presentation.common.custom.KeywordHistogramPanel;
import presentation.common.custom.KeywordsOverTimePanel;
import presentation.common.custom.LabelLabelPanel;

import javax.swing.JPanel;
import java.awt.GridLayout;

public class FileAnalysisMetricsScreen extends JPanel {

    public FileAnalysisMetricsScreen(){
        this.setLayout(new GridLayout(2, 3));
        createComponents();
    }

    private void createComponents() {
        JPanel holder = new JPanel( new GridLayout(2, 1));
        JPanel statisticsPanel = createStatisticsPanel();
        GeneralTablePanel logLevelTable = new GeneralTablePanel(GuiConstants.LOG_LEVEL_DISTRIBUTION_LABEL);
        holder.add(statisticsPanel);
        holder.add(logLevelTable);
        GeneralTablePanel kwdThTable = new GeneralTablePanel(GuiConstants.KEYWORD_THRESHOLD_LABEL);
        GeneralTablePanel warningsTable = new GeneralTablePanel(GuiConstants.WARNINGS_LABEL);
        KeywordHistogramPanel keywordHistogram = new KeywordHistogramPanel();
        GeneralTablePanel mostCommonWordsTable = new GeneralTablePanel(GuiConstants.MOST_COMMON_WORDS_LABEL);
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
        statsPanel.add(new LabelLabelPanel(GuiConstants.FILE_INDICATION_LABEL));
        statsPanel.add(new LabelLabelPanel(GuiConstants.START_DATE_LABEL));
        statsPanel.add(new LabelLabelPanel(GuiConstants.END_DATE_LABEL));
        return statsPanel;
    }
}
