package presentation.common.custom;

import presentation.common.GuiConstants;

import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;

import static presentation.common.GuiConstants.H_GAP;
import static presentation.common.GuiConstants.V_GAP;

public class KeywordsOverTimePanel extends JPanel {

    public KeywordsOverTimePanel() {
        this.setLayout(new BorderLayout(H_GAP, V_GAP));
        JLabel titleLabel = new JLabel(GuiConstants.KEYWORD_OVER_TIME_LABEL);
        this.add(titleLabel, BorderLayout.NORTH);
    }

}
