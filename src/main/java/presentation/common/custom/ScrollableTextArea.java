package presentation.common.custom;

import presentation.common.GuiConstants;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.BorderLayout;

public class ScrollableTextArea extends JPanel {

    private final JTextArea textArea;

    public ScrollableTextArea() {
        this.setLayout(new BorderLayout());
        this.textArea = new JTextArea();
        textArea.setColumns(GuiConstants.MESSAGE_DETAILS_TEXT_AREA_ROWS);
        textArea.setRows(GuiConstants.MESSAGE_DETAILS_TEXT_AREA_COLS);
        JScrollPane scrollPane = new JScrollPane(textArea);
        this.add(scrollPane, BorderLayout.CENTER);
        textArea.setEditable(false);
    }

    public void setTextAreaText(String text) {
        this.textArea.setText(text);
    }

    public void clearTextAreaText() {
        this.textArea.setText("");
    }
}
