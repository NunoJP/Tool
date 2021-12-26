package presentation.common.custom;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.BorderLayout;

import static presentation.common.GuiConstants.H_GAP;
import static presentation.common.GuiConstants.V_GAP;

public class LabelTextFieldPanel extends JPanel {

    private final JLabel staticLabel;
    private final JTextField variableLabel;

    public LabelTextFieldPanel(String staticText) {
        this(staticText, 0);
    }

    public LabelTextFieldPanel(String staticText, int fieldSize) {
        this.setLayout(new BorderLayout(H_GAP, V_GAP));
        staticLabel = new JLabel(staticText);
        variableLabel = new JTextField(fieldSize);
        this.add(staticLabel, BorderLayout.WEST);
        this.add(variableLabel, BorderLayout.CENTER);
    }

    public void setVariableLabelText(String text){
        this.variableLabel.setText(text);
    }
}
