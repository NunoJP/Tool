package presentation.common.custom;

import presentation.common.GuiConstants;
import presentation.common.custom.graphs.BarChartPanel;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static presentation.common.GuiConstants.H_GAP;
import static presentation.common.GuiConstants.V_GAP;

public class KeywordHistogramPanel extends JPanel {

    private JButton previousPanelButton;
    private JButton nextPanelButton;
    private JLabel currPageLabel;
    private int numberOfPanels = 0;
    private int currentPanel = 0;
    private CardLayout cardLayout;
    private JPanel cardPanel;

    public KeywordHistogramPanel(JFrame frame) {
        this.setLayout(new BorderLayout(H_GAP, V_GAP));
        this.setBorder(new EmptyBorder(V_GAP, H_GAP, V_GAP, H_GAP));
        JLabel titleLabel = new JLabel(GuiConstants.KEYWORD_HISTOGRAM_LABEL);
        this.add(titleLabel, BorderLayout.NORTH);
        this.add(createButtonPanel(), BorderLayout.SOUTH);
        setBehaviour();
    }

    private JPanel createButtonPanel() {
        JPanel holder = new JPanel(new FlowLayout(FlowLayout.CENTER, H_GAP, V_GAP));
        previousPanelButton = new JButton("<-");
        nextPanelButton = new JButton("->");
        currPageLabel = new JLabel(getCurrPageLabel());
        holder.add(previousPanelButton);
        holder.add(currPageLabel);
        holder.add(nextPanelButton);
        return holder;
    }

    private String getCurrPageLabel() {
        return "   " + (currentPanel + 1) + "/" + numberOfPanels + "   ";
    }

    public void updateChart(HashMap<String, Integer> barChartData) {
        reset();
        Set<Map.Entry<String, Integer>> entries = barChartData.entrySet();

        int numberOfBars = 10;
        int numberOfSections = entries.size() / numberOfBars;

        List<LinkedHashMap<String, Integer>> sectionedData = new ArrayList<>(numberOfSections);
        List<Map.Entry<String, Integer>> collect =
                entries.stream().sorted((o1, o2) -> o2.getValue() - o1.getValue()).collect(Collectors.toList());

        int barCtr = 0;
        int sectionIdx = 0;
        boolean first = true;
        for (Map.Entry<String, Integer> entry : collect) {
            if(first) {
                sectionedData.add(sectionIdx, new LinkedHashMap<>());
                first = false;
            } else if(barCtr >= numberOfBars) {
                barCtr = 0;
                sectionIdx++;
                sectionedData.add(sectionIdx, new LinkedHashMap<>());
            }
            sectionedData.get(sectionIdx).put(entry.getKey(), entry.getValue());
            barCtr++;
        }



        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        for (HashMap<String, Integer> section : sectionedData) {
            BarChartPanel chartPanel = new BarChartPanel(section);
            cardPanel.add(chartPanel, numberOfPanels + "");
            numberOfPanels++;
        }

        currPageLabel.setText(getCurrPageLabel());
        this.add(cardPanel, BorderLayout.CENTER);
    }

    private void reset() {
        cardLayout = null;
        cardPanel = null;
        numberOfPanels = 0;
        currentPanel = 0;
    }


    private void setBehaviour() {
        this.nextPanelButton.addActionListener(e -> {
            if(currentPanel < numberOfPanels - 1) {
                currentPanel++;
                cardLayout.show(cardPanel, currentPanel + "");
                currPageLabel.setText(getCurrPageLabel());
            }
        });
        this.previousPanelButton.addActionListener(e -> {
            if(currentPanel > 0) {
                currentPanel--;
                cardLayout.show(cardPanel, currentPanel + "");
                currPageLabel.setText(getCurrPageLabel());
            }
        });
    }
}
