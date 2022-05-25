package presentation.common.custom.graphs;

import general.util.Pair;
import presentation.common.GuiConstants;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PointPlotChartPanel extends BaseGraphPanel {

    public static final int POINT_SIZE = 4;
    private final List<Pair<Long, Date>> mapping;

    public PointPlotChartPanel(List<Pair<Long, Date>> mapping) {
        this.mapping = mapping;
    }

    public PointPlotChartPanel() {
        this.mapping = new ArrayList<>();
    }

    @Override
    protected void drawContents(Graphics2D graphics2D) {
        int numberOfPoints = mapping.size();

        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        double maxValue = mapping.stream().map(Pair::getLeft).max(Long::compareTo).orElse(0L);

        writeYaxisLabels(graphics2D, numberOfPoints, maxValue);

        int totalAllowedPoints = chartWidth / POINT_SIZE;
        int cutOffForEntries = numberOfPoints / totalAllowedPoints;
        boolean shouldSkipEntries = numberOfPoints > (totalAllowedPoints * 2);
        int pointSpacing = chartWidth / (Math.min(numberOfPoints, totalAllowedPoints));

        int counter = 0;
        for (Pair<Long, Date> pair : mapping) {

            if(shouldSkipEntries) { // to avoid over crowding the screen
                if(counter % cutOffForEntries == 0){
                    createPoint(graphics2D, maxValue, pointSpacing, counter, pair);
                }
            } else {
                createPoint(graphics2D, maxValue, pointSpacing, counter, pair);
            }
            counter++;
        }

    }

    private void createPoint(Graphics2D graphics2D, double maxValue, int pointSpacing, int counter, Pair<Long, Date> pair) {
        int xLeft;
        int yTopLeft;
        // calculate the height of the current bar based on the relative difference
        // between the max value and the height of the canvas
        double height = (pair.getLeft() / maxValue) * chartHeight;
        // calculate the x by shifting it counter number of times a bar has been drawn the width of the bar
        xLeft = chartZeroX + counter * pointSpacing;
        // the top of the bar is the diff from the y axis "zero" and the calculated height
        yTopLeft = chartZeroY - (int) height;

        // draw point
        graphics2D.fill(new Ellipse2D.Double(xLeft, yTopLeft - POINT_SIZE, POINT_SIZE, POINT_SIZE));

        // draw X label
        if (counter == 0) {
            graphics2D.drawString(convertToString(pair.getRight()), xLeft, chartZeroY + AXIS_OFFSET / 2 + AXIS_OFFSET / 3);
        } else if (counter == mapping.size() / 2) {
            graphics2D.drawString(convertToString(pair.getRight()), xLeft - pointSpacing / 2, chartZeroY + AXIS_OFFSET / 2 + AXIS_OFFSET / 3);
        } else if (counter == mapping.size() - 1) {
            String s = convertToString(pair.getRight());
            graphics2D.drawString(s, xLeft - graphics2D.getFontMetrics().stringWidth(s),
                    chartZeroY + AXIS_OFFSET / 2 + AXIS_OFFSET / 3);
        }
    }

    private String convertToString(Date instant) {
        DateFormat timeStampFormat = new SimpleDateFormat(GuiConstants.DATE_TIME_FORMATTER);
        return timeStampFormat.format(instant);
    }


}
