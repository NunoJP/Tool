package presentation.common.custom.graphs;

import general.util.Pair;

import javax.swing.BorderFactory;
import javax.swing.border.BevelBorder;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PointPlotChartPanel extends BaseGraphPanel {

    public static final int POINT_SIZE = 4;
    public static final int MIN_POINT_SPACING = 4;
    private final List<Pair<Long, Date>> mapping;

    public PointPlotChartPanel(List<Pair<Long, Date>> mapping) {
        this.mapping = new ArrayList<>(mapping);
        setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
    }

    public PointPlotChartPanel() {
        this(new ArrayList<>());
    }

    @Override
    protected void drawContents(Graphics2D graphics2D) {
        calculateGraphSize();
        int numberOfPoints = mapping.size();

        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        double maxValue = mapping.stream().map(Pair::getLeft).max(Long::compareTo).orElse(0L);

        writeYaxisLabels(graphics2D, numberOfPoints, maxValue);

        // We can only have as many points as we can properly draw.
        int totalAllowedPoints = (chartWidth / POINT_SIZE) / MIN_POINT_SPACING;
        int cutOffForEntries = new BigDecimal(numberOfPoints).divide(new BigDecimal(totalAllowedPoints), RoundingMode.CEILING).intValue();
        boolean shouldSkipEntries = numberOfPoints > (totalAllowedPoints * 2);
        int pointSpacing;
//        if(shouldSkipEntries) {
//            pointSpacing = MIN_POINT_SPACING;
//        } else {
//            pointSpacing = chartWidth / POINT_SIZE / (Math.min(numberOfPoints, totalAllowedPoints));
            pointSpacing = chartWidth / (Math.min(numberOfPoints, totalAllowedPoints));
//        }

//        System.out.println("=======================");
        int counter = 0;
        int skeCounter = 0;
        for (Pair<Long, Date> pair : mapping) {
//            System.out.println("OCCs: " + pair.getLeft() + " Date: " + pair.getRight());
            if(shouldSkipEntries) { // to avoid over crowding the screen
                if(counter % cutOffForEntries == 0){
                    createPoint(graphics2D, maxValue, pointSpacing, skeCounter, pair);
                    // the number of points drawn in the skip mode
                    skeCounter++;
                }
            } else {
                createPoint(graphics2D, maxValue, pointSpacing, counter, pair);
            }
            // general counter, needed for regular charts and for calculating which points to skip
            counter++;
        }



    }

    private void createPoint(Graphics2D graphics2D, double maxValue, int pointSpacing, int counter, Pair<Long, Date> pair) {
        int xLeft;
        int yTopLeft;
        // calculate the height of the current point based on the relative difference
        // between the max value and the height of the canvas
        double height = (pair.getLeft() / maxValue) * chartHeight;
        // calculate the x by shifting it counter number of times points have been drawn the spacing of the point and the width of the point
        xLeft = chartZeroX + counter * (pointSpacing + POINT_SIZE);
        // the Y position is the diff from the y axis "zero" and the calculated height
        yTopLeft = chartZeroY - (int) height;

        // draw point
        graphics2D.fill(new Ellipse2D.Double(xLeft, yTopLeft - POINT_SIZE, POINT_SIZE, POINT_SIZE));
    }



}
