package presentation.common.custom;

import domain.entities.common.Keyword;
import presentation.common.custom.graphs.BarChartPanel;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class KeywordHistogramPanel extends MultiPanelPanel<Keyword, Integer> {


    public static final int NUMBER_OF_BARS = 5;

    public KeywordHistogramPanel(JFrame frame) {
        super(frame);
    }


    public void updateChart(HashMap<Keyword, Integer> barChartData) {
        reset();
        Set<Map.Entry<Keyword, Integer>> entries = barChartData.entrySet();

        int numberOfSections = entries.size() / NUMBER_OF_BARS;

        List<LinkedHashMap<Keyword, Integer>> sectionedData = new ArrayList<>(numberOfSections);
        List<Map.Entry<Keyword, Integer>> collect =
                entries.stream().sorted((o1, o2) -> o2.getValue() - o1.getValue()).collect(Collectors.toList());

        int barCtr = 0;
        int sectionIdx = 0;
        boolean first = true;
        for (Map.Entry<Keyword, Integer> entry : collect) {
            if(first) {
                sectionedData.add(sectionIdx, new LinkedHashMap<>());
                first = false;
            } else if(barCtr >= NUMBER_OF_BARS) {
                barCtr = 0;
                sectionIdx++;
                sectionedData.add(sectionIdx, new LinkedHashMap<>());
            }
            sectionedData.get(sectionIdx).put(entry.getKey(), entry.getValue());
            barCtr++;
        }

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        for (HashMap<Keyword, Integer> section : sectionedData) {
            BarChartPanel chartPanel = new BarChartPanel(section);
            cardPanel.add(chartPanel, numberOfPanels + "");
            numberOfPanels++;
        }

        currPageLabel.setText(getCurrPageLabel());
        this.add(cardPanel, BorderLayout.CENTER);
    }


}
