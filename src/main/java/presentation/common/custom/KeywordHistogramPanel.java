package presentation.common.custom;

import presentation.common.GuiConstants;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;

import static presentation.common.GuiConstants.H_GAP;
import static presentation.common.GuiConstants.V_GAP;

public class KeywordHistogramPanel extends JPanel {

    public KeywordHistogramPanel() {
        this.setLayout(new BorderLayout(H_GAP, V_GAP));
        this.setBorder(new EmptyBorder(V_GAP, H_GAP, V_GAP, H_GAP));
        JLabel titleLabel = new JLabel(GuiConstants.KEYWORD_HISTOGRAM_LABEL);
        this.add(titleLabel, BorderLayout.NORTH);
    }

}
