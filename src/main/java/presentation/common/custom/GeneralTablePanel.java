package presentation.common.custom;

import general.util.Pair;
import presentation.common.GuiConstants;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Rectangle;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.function.ToIntFunction;

import static presentation.common.GuiConstants.H_GAP;
import static presentation.common.GuiConstants.V_GAP;
import static presentation.fileanalysis.FileAnalysisScreenPresenter.MESSAGE_COLUMN_IDX;

public class GeneralTablePanel extends JPanel {

    private final JTable table;

    public GeneralTablePanel(String title, String [] columns, boolean editable, boolean isSortable) {
        this(title, columns, editable, 25, isSortable);
    }
    public GeneralTablePanel(String [] columns, boolean editable, boolean isSortable) {
        this(null, columns, editable, 25, isSortable);
    }

    public GeneralTablePanel(String title, String[] columns, boolean editable, int numberOfRows, boolean isSortable) {
        this.setLayout(new BorderLayout(H_GAP, V_GAP));
        this.setBorder(new EmptyBorder(V_GAP, H_GAP, V_GAP, H_GAP));
        if(title != null) {
            JLabel titleLabel = new JLabel(title);
            this.add(titleLabel, BorderLayout.NORTH);
        }
        table = new JTable(new DefaultTableModel(columns, numberOfRows) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return editable;
            }
        });
        JScrollPane scrollPane = new JScrollPane(table,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.add(scrollPane, BorderLayout.CENTER);
        if(isSortable) {
            TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>((DefaultTableModel) table.getModel());
            table.setRowSorter(sorter);
        }
    }

    public void setGeneralSelection(boolean selectable){
        table.setRowSelectionAllowed(selectable);
        table.setColumnSelectionAllowed(selectable);
        table.setCellSelectionEnabled(selectable);
    }

    public void setLineSelectionOnly() {
        table.setRowSelectionAllowed(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    public void setCellSelectionOnly() {
        table.setCellSelectionEnabled(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    public void setMultiLineSelection() {
        table.setRowSelectionAllowed(true);
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    }

    public void addRowSelectionEvent(ListSelectionListener listSelectionListener) {
        table.getSelectionModel().addListSelectionListener(listSelectionListener);
    }

    public void setData(Object [][] data) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        for (Object[] datum : data) {
            model.addRow(datum);
        }
    }

    public void changeColumnWidths(int [] columnSizes) {
        TableColumn column = null;
        for (int i = 0; i < columnSizes.length; i++) {
            column = table.getColumnModel().getColumn(i);
            column.setPreferredWidth(columnSizes[i]);
            column.setMinWidth(columnSizes[i]);
        }
        table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
    }
    public void setIntegerColumnsSort(int [] cols) {
        TableRowSorter<DefaultTableModel> rowSorter = (TableRowSorter<DefaultTableModel>) table.getRowSorter();

        for (int col : cols) {
            rowSorter.setComparator(col, Comparator.comparingInt((ToIntFunction<String>) Integer::parseInt));
        }
    }

    public void setDoubleColumnsSort(int [] cols) {
        TableRowSorter<DefaultTableModel> rowSorter = (TableRowSorter<DefaultTableModel>) table.getRowSorter();

        for (int col : cols) {
            rowSorter.setComparator(col, Comparator.comparingDouble(Double::parseDouble));
        }

    }

    public void resizeToMatchContents() {
        table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        final TableColumnModel columnModel = table.getColumnModel();
        for (int col = 0; col < table.getColumnCount(); col++) {
            int width = GuiConstants.MIN_COLUMN_WIDTH;

            // for each row calculate the width necessary for the contents, if it is larger that the largest one
            // replace the current largest one.
            for (int row = 0; row < table.getRowCount(); row++) {
                TableCellRenderer cellRenderer = table.getCellRenderer(row, col);
                Component comp = table.prepareRenderer(cellRenderer, row, col);
                width = Math.max(comp.getPreferredSize().width + 1, width);
            }

            // check if the header is larger that the smallest of the column's contents
            width = Math.max(width, table.getColumnModel().getColumn(col).getPreferredWidth());

            // if the width is too large, cap it at a max value
            if(width > GuiConstants.MAX_COLUMN_WIDTH) {
                width = GuiConstants.MAX_COLUMN_WIDTH;
            }
            columnModel.getColumn(col).setPreferredWidth(width);
            columnModel.getColumn(col).setMinWidth(width);
            if(col != table.getColumnCount() - 1) {
                columnModel.getColumn(col).setMaxWidth(width);
            }

        }

    }

    public void setStringColorRenderMap(HashMap<String, Pair<Color,Color>> colorRenderMap){
        table.setDefaultRenderer(Object.class, new LevelRenderer(table, colorRenderMap));
    }

    public void setNumberOfRows(int i) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(i);
    }

    public Rectangle getCellRect(int currentSearchIndex, int column, boolean includeSpacing) {
        return table.getCellRect(currentSearchIndex, column, includeSpacing);
    }

    public void scrollRectToVisible(Rectangle targetRect) {
        table.scrollRectToVisible(targetRect);
    }

    public void scrollToRow(Pair<Integer, ? extends List> pair) {
        Rectangle currentRect = table.getCellRect(pair.getLeft(), MESSAGE_COLUMN_IDX, true);
//        Rectangle pt = table.getVisibleRect();
//        currentRect.setLocation(currentRect.x - pt.x, currentRect.y - pt.y);
        table.scrollRectToVisible(currentRect);
    }

    static class LevelRenderer extends DefaultTableCellRenderer {
        private static final int LEVEL_COL_INDEX = 0;
        Color defaultBackground;
        Color defaultForeground;
        private HashMap<String, Pair<Color,Color>> colorRenderMap;

        public LevelRenderer(JTable table, HashMap<String, Pair<Color,Color>> colorRenderMap) {
            super();
            this.defaultBackground = table.getBackground();
            this.defaultForeground = table.getForeground();
            this.colorRenderMap = colorRenderMap;
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            int indexToModel = table.getRowSorter().convertRowIndexToModel(row);
            String level = (String) table.getModel().getValueAt(indexToModel, LEVEL_COL_INDEX);
            Pair<Color,Color> color = colorRenderMap.getOrDefault(level, Pair.of(defaultBackground, defaultForeground));
            setBackground(color.getLeft());
            setForeground(color.getRight());
            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, indexToModel, column);
        }

    }
}
