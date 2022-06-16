package presentation.common.custom;

import domain.entities.common.Keyword;
import general.util.Pair;
import presentation.common.custom.graphs.PointPlotChartPanel;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class KeywordsOverTimePanel extends MultiPanelPanel<Keyword, List<Pair<Long, Date>>> {


    public KeywordsOverTimePanel(JFrame frame) {
        super(frame);
    }

    @Override
    public void updateChart(HashMap<Keyword, List<Pair<Long, Date>>> pointChartData) {
        reset();
        Set<Map.Entry<Keyword, List<Pair<Long, Date>>>> entries = pointChartData.entrySet();

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        for (Map.Entry<Keyword, List<Pair<Long, Date>>> section : entries) {
            PointPlotChartPanel chartPanel = new PointPlotChartPanel(section.getValue());
            cardPanel.add(chartPanel, numberOfPanels + "");
            numberOfPanels++;
        }

        currPageLabel.setText(getCurrPageLabel());
        this.add(cardPanel, BorderLayout.CENTER);
    }


}
