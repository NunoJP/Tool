package presentation.common.tables;

import presentation.common.custom.GeneralTablePanel;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import java.awt.Color;
import java.awt.Component;
import java.awt.Rectangle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static presentation.fileanalysis.FileAnalysisScreenPresenter.MESSAGE_COLUMN_IDX;

public class HighlightAbleTable extends GeneralTablePanel {

    private static final Logger LOGGER = Logger.getLogger(HighlightAbleTable.class.getName());

    private String textToHighlight = "";

    private int currentRow;
    private int currentIndex;


    public HighlightAbleTable(String title, String[] columns, boolean editable, int numberOfRows, boolean isSortable) {
        super(title, columns, editable, numberOfRows, isSortable);
        table.setDefaultRenderer(Object.class, getTableCellRenderer());
    }

    public HighlightAbleTable(String [] columns, boolean editable, boolean isSortable) {
        this(null, columns, editable, 25, isSortable);
    }

    public void scrollRectToVisible(Rectangle targetRect) {
        table.scrollRectToVisible(targetRect);
    }

    public void updateHighlight(String textToHighlight) {
        this.textToHighlight = textToHighlight;
        scrollToRow(0, 0);
    }

    public void scrollToRow(int row, int index) {
        this.currentRow = row;
        this.currentIndex = index;
        Rectangle currentRect = table.getCellRect(row, MESSAGE_COLUMN_IDX, true);
        table.scrollRectToVisible(currentRect);
        table.repaint();
    }


    private TableCellRenderer getTableCellRenderer() {
        return new TableCellRenderer() {
            final JTextField textField = new JTextField();


            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = new JLabel();
                textField.setBorder(label.getBorder());

                if (value != null) {
                    textField.setText(value.toString());
                    if(column == MESSAGE_COLUMN_IDX && !textToHighlight.equals("")) {
                        String text = value.toString();
                        try {
                            highlightByIndexes(text, row);
                        } catch (BadLocationException e) {
                            LOGGER.log(Level.WARNING, e.getMessage());
                        }
                    }
                } else {
                    textField.getHighlighter().removeAllHighlights();
                }

                return textField;
            }

            private void highlightByIndexes(String text, int row) throws BadLocationException {
                int idx, idxAcc = 0;
                int ctr = 0;
                textField.getHighlighter().removeAllHighlights();
                while((idx = text.indexOf(textToHighlight, idxAcc)) != -1) {

                    idxAcc = idx + textToHighlight.length();
                    if (row == currentRow && ctr == currentIndex) {
                        textField.getHighlighter().addHighlight(idx, idx + textToHighlight.length(),
                                new DefaultHighlighter.DefaultHighlightPainter(Color.decode("#03adfc")));
                    } else {
                        textField.getHighlighter().addHighlight(idx, idx + textToHighlight.length(),
                                new DefaultHighlighter.DefaultHighlightPainter(Color.CYAN));
                    }
                    ctr++;
                }
            }


        };
    }

}
