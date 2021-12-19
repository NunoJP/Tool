package presentation.common.custom;

import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;

public class LabelLabelPanel extends JPanel {

    private final JLabel staticLabel;
    private final JLabel variableLabel;

    public LabelLabelPanel(String staticText) {
        this.setLayout(new BorderLayout());
        staticLabel = new JLabel(staticText);
        variableLabel = new JLabel();
        this.add(staticLabel, BorderLayout.WEST);
        this.add(variableLabel, BorderLayout.CENTER);
    }

    public void setVariableLabelText(String text){
        this.variableLabel.setText(text);
    }
}
