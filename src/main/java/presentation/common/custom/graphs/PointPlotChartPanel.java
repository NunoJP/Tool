package presentation.common.custom.graphs;

import general.util.Pair;
import presentation.common.GuiConstants;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PointPlotChartPanel extends BaseGraphPanel {


    public static final int POINT_SIZE = 4;
    private List<Pair<Long, Date>> mapping;
    private final int MAX_Y_LABELS = 20;

    public PointPlotChartPanel(List<Pair<Long, Date>> mapping) {
        this.mapping = mapping;
    }

    @Override
    protected void drawContents(Graphics2D graphics2D) {
        int numberOfPoints = mapping.size();

        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        double maxValue = mapping.stream().map(Pair::getLeft).max(Long::compareTo).orElse(0L);

        int pointSpacing = chartWidth / numberOfPoints;

        writeYaxisLabels(graphics2D, numberOfPoints, maxValue);

        int xLeft;
        int yTopLeft;
        int counter = 0;
        for (Pair<Long, Date> pair : mapping) {
            // calculate the height of the current bar based on the relative difference
            // between the max value and the height of the canvas
            double height = (pair.getLeft() / maxValue) * chartHeight;
            // calculate the x by shifting it counter number of times a bar has been drawn the width of the bar
            xLeft = chartZeroX + counter * pointSpacing;
            // the top of the bar is the diff from the y axis "zero" and the calculated height
            yTopLeft = chartZeroY - (int) height;

            // draw point
            graphics2D.fill(new Ellipse2D.Double(xLeft, yTopLeft-POINT_SIZE, POINT_SIZE, POINT_SIZE));

            // draw X label
            if(counter == 0) {
                graphics2D.drawString(convertToString(pair.getRight()), xLeft, chartZeroY + AXIS_OFFSET / 2 + AXIS_OFFSET / 3);
            } else if(counter == mapping.size()/2) {
                graphics2D.drawString(convertToString(pair.getRight()), xLeft - pointSpacing/2, chartZeroY + AXIS_OFFSET / 2 + AXIS_OFFSET / 3);
            } else if(counter == mapping.size()-1) {
                String s = convertToString(pair.getRight());
                graphics2D.drawString(s, xLeft - graphics2D.getFontMetrics().stringWidth(s),
                        chartZeroY + AXIS_OFFSET / 2 + AXIS_OFFSET / 3);
            }

            counter++;
        }

    }

    private String convertToString(Date instant) {
        DateFormat timeStampFormat = new SimpleDateFormat(GuiConstants.DATE_TIME_FORMATTER);
        return timeStampFormat.format(instant);
    }

    private void writeYaxisLabels(Graphics2D graphics2D, int numberOfPoints, double max) {
        BigDecimal maxBigDec = new BigDecimal((int)max);

        int numberOfLabels = Math.min(numberOfPoints, MAX_Y_LABELS);
        BigDecimal maxFraction = maxBigDec.divide(new BigDecimal(numberOfLabels), RoundingMode.DOWN);

        graphics2D.setColor(new Color(13, 25, 80));
        int fractionAccumulator = 0;

        for (int i = 0; i < numberOfLabels +1; i++) {
            double height = (fractionAccumulator / max) * chartHeight;
            int yTopLeft = chartZeroY - (int) height;
            graphics2D.drawString("" + fractionAccumulator  , 0, yTopLeft);
            fractionAccumulator += maxFraction.intValue();
        }
    }


}
