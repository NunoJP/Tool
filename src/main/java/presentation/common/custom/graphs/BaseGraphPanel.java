package presentation.common.custom.graphs;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;

public abstract class BaseGraphPanel extends JPanel {
    protected int chartHeight;
    protected int chartWidth;
    protected int chartZeroX;
    protected int chartZeroY;
    protected final int HEAD_SPACE = 0;
    protected final int AXIS_OFFSET = 20;
    protected final int MAX_STRING_SIZE = 10;
    protected static final int ELLIPSIS_SIZE = 3;

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        calculateGraphSize();

        Graphics2D g2 = (Graphics2D) g;
        drawContents(g2);
        drawAxis(g2);
        g2.dispose();
    }

    protected abstract void drawContents(Graphics2D g2);
    
    private void calculateGraphSize() {

        // area size
        chartWidth = this.getWidth() - 4 * AXIS_OFFSET;
        chartHeight = this.getHeight() - 2 * AXIS_OFFSET - HEAD_SPACE;

        // origin coordinates taking into consideration that the top left corner is the actual 0, 0 we need the
        // relativized 0, 0 coordinates
        chartZeroX = AXIS_OFFSET * 3;
        chartZeroY = this.getHeight() - AXIS_OFFSET;
    }


    protected void drawAxis(Graphics2D graphics2D) {
        // calculate the end points for the axis
        int endOfAxisX = chartZeroX + chartWidth;
        int endOfAxisY = chartZeroY - chartHeight;

        graphics2D.drawLine(chartZeroX, chartZeroY, endOfAxisX, chartZeroY);
        graphics2D.drawLine(chartZeroX, chartZeroY, chartZeroX, endOfAxisY);
    }



    protected String shortenString(String original) {
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
