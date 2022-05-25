package presentation.common.custom.graphs;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

public class BarChartPanel extends BaseGraphPanel {
    private final Set<String> keySet;
    private final Collection<Integer> values;


    public BarChartPanel(HashMap<String, Integer> occs) {
        this.keySet = occs.keySet();
        this.values = occs.values();
    }


    protected void drawContents(Graphics2D graphics2D) {
        int numberOfBars = keySet.size();
        String[] strings = keySet.toArray(String[]::new);

        // the max has to be a double in order to have a floating point division later on
        double max = values.stream().max(Integer::compareTo).orElse(0);

        writeYaxisLabels(graphics2D, numberOfBars, max);

        int barWidth = chartWidth / numberOfBars;
        int xLeft;
        int yTopLeft;
        int counter = 0;
        for (Integer value : values) {
            // calculate the height of the current bar based on the relative difference
            // between the max value and the height of the canvas
            double height = (value / max) * chartHeight;
            // calculate the x by shifting it counter number of times a bar has been drawn the width of the bar
            xLeft = chartZeroX + counter * barWidth;
            // the top of the bar is the diff from the y axis "zero" and the calculated height
            yTopLeft = chartZeroY - (int) height;

            // draw the rectangle
            Rectangle rectangle = new Rectangle(xLeft, yTopLeft, barWidth / 2, (int) height);
            graphics2D.setColor(new Color(91, 115, 222));
            graphics2D.fill(rectangle);
            graphics2D.setColor(new Color(13, 25, 80));
            graphics2D.drawString(shortenString(strings[counter]), xLeft, chartZeroY + AXIS_OFFSET / 2 + AXIS_OFFSET / 3);
            counter++;
        }
    }



}
