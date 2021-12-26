package presentation.common.custom;

import general.util.Pair;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.util.HashMap;

import static presentation.common.GuiConstants.H_GAP;
import static presentation.common.GuiConstants.V_GAP;

public class GeneralTablePanel extends JPanel {

    private final JTable table;
    private HashMap<String, Pair<Color,Color>> colorRenderMap;

    public GeneralTablePanel(String [] columns) {
        this(null, columns);
    }

    public GeneralTablePanel(String title, String [] columns) {
        this.setLayout(new BorderLayout(H_GAP, V_GAP));
        this.setBorder(new EmptyBorder(V_GAP, H_GAP, V_GAP, H_GAP));
        if(title != null) {
            JLabel titleLabel = new JLabel(title);
            this.add(titleLabel, BorderLayout.NORTH);
        }
        table = new JTable(new DefaultTableModel(columns, 25));
        JScrollPane scrollPane = new JScrollPane(table);
        this.add(scrollPane, BorderLayout.CENTER);
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>((DefaultTableModel) table.getModel());
        table.setRowSorter(sorter);
    }

    public GeneralTablePanel setGeneralSelection(boolean selectable){
        table.setRowSelectionAllowed(selectable);
        table.setColumnSelectionAllowed(selectable);
        table.setCellSelectionEnabled(selectable);
        return this;
    }

    public void setData(Object [][] data) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        for (Object[] datum : data) {
            model.addRow(datum);
        }
    }

    public void setStringColorRenderMap(HashMap<String, Pair<Color,Color>> colorRenderMap){
        this.colorRenderMap = colorRenderMap;
        table.setDefaultRenderer(Object.class, new LevelRenderer(table, colorRenderMap));
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
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            int indexToModel = table.getRowSorter().convertRowIndexToModel(row);
            String level = (String) table.getModel().getValueAt(indexToModel, LEVEL_COL_INDEX);
            Pair<Color,Color> color = colorRenderMap.getOrDefault(level, Pair.of(defaultBackground, defaultForeground));
            setBackground(color.getLeft());
            setForeground(color.getRight());
            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
    }
}
