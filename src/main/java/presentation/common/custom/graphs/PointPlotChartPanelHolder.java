package presentation.common.custom.graphs;

import general.util.Pair;

import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.Date;
import java.util.List;

public class PointPlotChartPanelHolder extends JPanel {
    private LegendPanel legendPanel;
    private PointPlotChartPanel pointPlotChartPanel;

    public PointPlotChartPanelHolder(List<Pair<Long, Date>> value, String keyword) {
        JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        labelPanel.add(new JLabel(keyword));
        this.pointPlotChartPanel = new PointPlotChartPanel(value);
        this.legendPanel = new LegendPanel(value);
        this.setLayout(new BorderLayout());
        this.add(labelPanel, BorderLayout.NORTH);
        this.add(pointPlotChartPanel, BorderLayout.CENTER);
        this.add(legendPanel, BorderLayout.SOUTH);
    }
}
