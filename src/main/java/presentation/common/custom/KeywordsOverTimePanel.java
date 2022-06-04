package presentation.common.custom;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;

import static presentation.common.GuiConstants.H_GAP;
import static presentation.common.GuiConstants.V_GAP;

public class KeywordsOverTimePanel extends JPanel {

    public KeywordsOverTimePanel() {
        this.setLayout(new BorderLayout(H_GAP, V_GAP));
        this.setBorder(new EmptyBorder(V_GAP, H_GAP, V_GAP, H_GAP));
    }

}
