package presentation.common.custom.graphs;

import general.util.Pair;
import presentation.common.GuiConstants;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LegendPanel extends JPanel {

    private final List<Pair<Long, Date>> mapping;
    private int labelYPlacement;
    protected final int HEAD_SPACE = 0;
    protected final int AXIS_OFFSET = 20;

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        drawContents(g2);
        g2.dispose();
    }

    public LegendPanel(List<Pair<Long, Date>> mapping) {
        setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        this.mapping = new ArrayList<>(mapping);
    }


    protected void drawContents(Graphics2D graphics2D) {

        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int chartWidth = this.getWidth() - 4 * AXIS_OFFSET;
        int chartZeroX = AXIS_OFFSET * 3;
        int chartZeroY = this.getHeight() - AXIS_OFFSET;
        int endOfAxisX = chartZeroX + chartWidth;

        // draw X label
        labelYPlacement = chartZeroY + AXIS_OFFSET / 2 + AXIS_OFFSET / 3;
        graphics2D.drawString(convertToString(mapping.get(0).getRight()), chartZeroX, labelYPlacement);

        String s = convertToString(mapping.get(mapping.size() / 2).getRight());
        graphics2D.drawString(s, endOfAxisX / 2 - graphics2D.getFontMetrics().stringWidth(s) / 2,
                labelYPlacement);

        s = convertToString(mapping.get(mapping.size()-1).getRight());
        graphics2D.drawString(s, endOfAxisX - graphics2D.getFontMetrics().stringWidth(s),
                labelYPlacement);

    }

    private String convertToString(Date instant) {
        DateFormat timeStampFormat = new SimpleDateFormat(GuiConstants.DATE_TIME_FORMATTER);
        return timeStampFormat.format(instant);
    }


}
