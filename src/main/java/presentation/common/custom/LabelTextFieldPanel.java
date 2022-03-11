package presentation.common.custom;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.BorderLayout;

import static presentation.common.GuiConstants.H_GAP;
import static presentation.common.GuiConstants.V_GAP;

public class LabelTextFieldPanel extends JPanel {

    private final JLabel staticLabel;
    private final JTextField variableTextField;

    public LabelTextFieldPanel(String staticText) {
        this(staticText, 0);
    }

    public LabelTextFieldPanel(String staticText, int fieldSize) {
        this.setLayout(new BorderLayout(H_GAP, V_GAP));
        staticLabel = new JLabel(staticText);
        variableTextField = new JTextField(fieldSize);
        this.add(staticLabel, BorderLayout.WEST);
        this.add(variableTextField, BorderLayout.CENTER);
    }

    public void setVariableLabelText(String text){
        this.variableTextField.setText(text);
    }

    public String getVariableLabelText(){
        return this.variableTextField.getText();
    }

    public void setMaxSizeAsPreferredSize(){
        variableTextField.setMaximumSize( variableTextField.getPreferredSize() );
    }

    public void setTextFieldWidth(int numberOfColumns) {
        variableTextField.setColumns(numberOfColumns);
    }

    public JTextField getVariableTextField() {
        return variableTextField;
    }

    public boolean isEmpty() {
        return variableTextField.getText() == null || variableTextField.getText().isEmpty();
    }
}
