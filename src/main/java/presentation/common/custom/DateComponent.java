package presentation.common.custom;

import presentation.common.GuiConstants;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import java.awt.FlowLayout;
import java.util.Calendar;
import java.util.Date;

public class DateComponent extends JPanel {

    private final JSpinner date;
    private final JSpinner time;
    private final JLabel staticLabel;

    public DateComponent(String staticText) {
        this.setLayout(new FlowLayout());
        staticLabel = new JLabel(staticText);
        this.add(staticLabel);
        Calendar calendar = Calendar.getInstance();
        Date initDate = calendar.getTime();
        calendar.add(Calendar.YEAR, -100);
        Date earliestDate = calendar.getTime();
        calendar.add(Calendar.YEAR, 200);
        Date latestDate = calendar.getTime();
        SpinnerDateModel dateModel = new SpinnerDateModel(initDate,
                earliestDate,
                latestDate,
                Calendar.DAY_OF_MONTH);
        date = new JSpinner(dateModel);
        date.setEditor(new JSpinner.DateEditor(date, GuiConstants.DATE_FORMATTER));
        this.add(date);
        SpinnerDateModel timeModel = new SpinnerDateModel(initDate,
                null,
                null,
                Calendar.MILLISECOND);
        time = new JSpinner(timeModel);
        time.setEditor(new JSpinner.DateEditor(time, GuiConstants.TIME_FORMATTER));
        this.add(time);
    }

    public Date getDate() {
        return (Date) date.getValue();
    }

    public Date getTime() {
        return (Date) time.getValue();
    }

    public void setDate(Date newDate) {
        SpinnerDateModel dateModel = new SpinnerDateModel(newDate, null, null, Calendar.DAY_OF_MONTH);
        date.setValue(newDate);
    }

    public void setTime(Date newTime) {
        SpinnerDateModel timeModel = new SpinnerDateModel(newTime, null, null, Calendar.MILLISECOND);
        time.setValue(newTime);
    }
}
