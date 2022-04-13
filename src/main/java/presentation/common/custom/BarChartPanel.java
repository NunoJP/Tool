package presentation.common.custom;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

public class BarChartPanel extends JPanel {

    private static final int ELLIPSIS_SIZE = 3;
    private final int HEAD_SPACE = 0;
    private final int AXIS_OFFSET = 20;
    private final int MAX_STRING_SIZE = 10;
    private final Set<String> keySet;
    private final Collection<Integer> values;
    private int chartHeight;
    private int chartWidth;
    private int chartZeroX;
    private int chartZeroY;

    public BarChartPanel(HashMap<String, Integer> occs) {
        this.keySet = occs.keySet();
        this.values = occs.values();
    }


    @Override
    public void paintComponent(Graphics g) {
        calculateGraphSize();

        Graphics2D g2 = (Graphics2D) g;
        drawBars(g2);
        drawAxis(g2);
        drawLabels(g2);
    }


    private void calculateGraphSize() {

        // area size
        chartWidth = this.getWidth() - 2 * AXIS_OFFSET;
        chartHeight = this.getHeight() - 2 * AXIS_OFFSET - HEAD_SPACE;

        // origin coordinates taking into consideration that the top left corner is the actual 0, 0 we need the
        // relativized 0, 0 coordinates
        chartZeroX = AXIS_OFFSET * 3;
        chartZeroY = this.getHeight() - AXIS_OFFSET;
    }

    private void drawBars(Graphics2D graphics2D) {
        int numberOfBars = keySet.size();
        String[] strings = keySet.toArray(String[]::new);

        // the max has to be a double in order to have a floating point division later on
        double max = values.stream().max(Integer::compareTo).orElse(0);

        graphics2D.setColor(new Color(13, 25, 80));
        graphics2D.drawString("Max:" + ((int) max ), 0, this.getHeight() - chartZeroY);

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

    private void drawAxis(Graphics2D graphics2D) {
        // calculate the end points for the axis
        int endOfAxisX = chartZeroX + chartWidth;
        int endOfAxisY = chartZeroY - chartHeight;

        graphics2D.drawLine(chartZeroX, chartZeroY, endOfAxisX, chartZeroY);
        graphics2D.drawLine(chartZeroX, chartZeroY, chartZeroX, endOfAxisY);
    }


    private void drawLabels(Graphics2D g2) {
    }

    private String shortenString(String original) {
        if(original.length() > MAX_STRING_SIZE + ELLIPSIS_SIZE) {
            String shortened = original.substring(0, MAX_STRING_SIZE);
            return shortened + "...";
        } else if(original.equals("")) {
            return "\"\"";
        } else if(original.equals("\t")) {
            return "\\t";
        }
        return original;
    }

}
