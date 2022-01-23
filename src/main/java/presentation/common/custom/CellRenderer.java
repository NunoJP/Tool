package presentation.common.custom;

import domain.entities.common.SeparatorEnum;
import domain.entities.common.TextClassesEnum;
import domain.entities.common.ThresholdTypeEnum;
import domain.entities.common.ThresholdUnitEnum;
import domain.entities.displayobjects.MetricsProfileDo;
import domain.entities.displayobjects.ParsingProfileDo;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import java.awt.Component;

public class CellRenderer extends DefaultListCellRenderer {

    public Component getListCellRendererComponent(
            JList list,
            Object value,
            int index,
            boolean isSelected,
            boolean cellHasFocus) {
        if (value instanceof ParsingProfileDo) {
            value = ((ParsingProfileDo) value).getName();
        } else if(value instanceof MetricsProfileDo) {
            value = ((MetricsProfileDo) value).getName();
        } else if(value instanceof TextClassesEnum) {
            value = ((TextClassesEnum) value).getName();
        } else if(value instanceof SeparatorEnum) {
            value = ((SeparatorEnum) value).getName();
        } else if(value instanceof ThresholdTypeEnum) {
            value = ((ThresholdTypeEnum) value).getName();
        } else if(value instanceof ThresholdUnitEnum) {
            value = ((ThresholdUnitEnum) value).getName();
        }
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        return this;
    }
}

