package presentation.common.custom;

import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;

import static presentation.common.GuiConstants.H_GAP;
import static presentation.common.GuiConstants.V_GAP;

public class GeneralTablePanel extends JPanel {

    public GeneralTablePanel(String title) {
        this.setLayout(new BorderLayout(H_GAP, V_GAP));
        JLabel titleLabel = new JLabel(title);
        this.add(titleLabel, BorderLayout.NORTH);
    }
}
