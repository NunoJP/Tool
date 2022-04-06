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
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;

import static presentation.common.GuiConstants.H_GAP;
import static presentation.common.GuiConstants.V_GAP;

public class MetricsMonitoringScreen extends JDialog {

    private GeneralTablePanel kwdThTable;
    private GeneralTablePanel warningsTable;
    private GeneralTablePanel mostCommonWordsTable;
    private MetricsProfileDo metricsProfile;
    private int numberOfItems;
    private LabelLabelPanel namePanel;
    private JPanel holder;
    private JButton stopButton;

    public MetricsMonitoringScreen(Frame owner, String title, MetricsProfileDo metricsProfile) {
        super(owner, title);
        setWindowClosingBehavior();
        this.metricsProfile = metricsProfile;
        this.setLayout(new BorderLayout(H_GAP, V_GAP));
        createComponents();
        pack();
    }

    private int[] calculateRowsAndCols() {
        numberOfItems = 1;
        if(metricsProfile.isHasFileSize()) {
            numberOfItems++;
        }
        if(metricsProfile.isHasKeywordHistogram()) {
            numberOfItems++;
        }
        if(metricsProfile.isHasKeywordOverTime()) {
            numberOfItems++;
        }
        if(metricsProfile.isHasKeywordThreshold()) {
            numberOfItems++;
        }
        if(metricsProfile.isHasMostCommonWords()) {
            numberOfItems++;
        }
        if(numberOfItems >= 5) {
            return new int[] { 2, 3 };
        } else if(numberOfItems >= 3) {
            return new int[] { 2, 2 };
        } else {
            return new int[]{ 1, numberOfItems};
        }
    }

    private void createComponents() {
        namePanel = new LabelLabelPanel(GuiConstants.NAME_LABEL);
        this.add(namePanel, BorderLayout.NORTH);
        stopButton = new JButton(GuiConstants.STOP_BUTTON);
        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, H_GAP, V_GAP));
        southPanel.add(stopButton);
        this.add(southPanel, BorderLayout.SOUTH);

        int [] rc = calculateRowsAndCols();
        holder = new JPanel(new GridLayout(rc[0], rc[1]));

        // File size chart
        if(metricsProfile.isHasFileSize()) {
            holder.add(new JPanel());
        }

        // Keyword Threshold + Warnings
        if(metricsProfile.isHasKeywordThreshold()) {
            kwdThTable = new GeneralTablePanel(GuiConstants.KEYWORD_THRESHOLD_LABEL,
                    new String[]{GuiConstants.KEYWORD_COLUMN, GuiConstants.VALUE_COLUMN}, false);
            kwdThTable.setGeneralSelection(false);
            warningsTable = new GeneralTablePanel(GuiConstants.WARNINGS_LABEL,
                    new String[]{GuiConstants.LEVEL_COLUMN, GuiConstants.MESSAGE_COLUMN}, false);
            warningsTable.setGeneralSelection(false);
            holder.add(kwdThTable);
            holder.add(warningsTable);
        }

        // Keyword Histogram
        if(metricsProfile.isHasKeywordHistogram()) {
            KeywordHistogramPanel keywordHistogram = new KeywordHistogramPanel();
            holder.add(keywordHistogram);
        }

        // Most common words
        if(metricsProfile.isHasMostCommonWords()) {
            mostCommonWordsTable = new GeneralTablePanel(GuiConstants.MOST_COMMON_WORDS_LABEL,
                    new String[]{GuiConstants.WORD_COLUMN, GuiConstants.VALUE_COLUMN}, false);
            mostCommonWordsTable.setGeneralSelection(false);
            holder.add(mostCommonWordsTable);
        }

        // Keywords over time
        if(metricsProfile.isHasKeywordOverTime()) {
            KeywordsOverTimePanel keywordOverTime = new KeywordsOverTimePanel();
            holder.add(keywordOverTime);
        }

        this.add(holder, BorderLayout.CENTER);
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
