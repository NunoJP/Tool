package presentation.common.custom;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import java.awt.FlowLayout;
import java.util.Calendar;
import java.util.Date;

import static presentation.common.GuiConstants.DATE_TIME_FORMATTER;

public class DateComponent extends JPanel {

//    private final JSpinner year;
//    private final JSpinner month;
//    private final JSpinner day;
    private final JSpinner date;
    private final JLabel staticLabel;

    public DateComponent(String staticText) {
        this.setLayout(new FlowLayout());
        staticLabel = new JLabel(staticText);
        this.add(staticLabel);
//        year = new JSpinner();
//        month = new JSpinner();
//        day = new JSpinner();
//        Calendar calendar = Calendar.getInstance();
//        int currentYear = calendar.get(Calendar.YEAR);
//        SpinnerModel yearModel = new SpinnerNumberModel(currentYear, currentYear - 100, currentYear + 100, 1);
//        SpinnerModel monthModel = new SpinnerNumberModel(1,  1, 12, 1);
//        SpinnerModel dayModel = new SpinnerNumberModel(1, 1, 31,1); //step
//        year.setModel(yearModel);
//        month.setModel(monthModel);
//        day.setModel(dayModel);
//        this.add(year);
//        this.add(month);
//        this.add(day);

        Calendar calendar = Calendar.getInstance();
        Date initDate = calendar.getTime();
        calendar.add(Calendar.YEAR, -100);
        Date earliestDate = calendar.getTime();
        calendar.add(Calendar.YEAR, 200);
        Date latestDate = calendar.getTime();
        SpinnerDateModel dateModel = new SpinnerDateModel(initDate,
                earliestDate,
                latestDate,
                Calendar.YEAR);
        date = new JSpinner(dateModel);
        date.setEditor(new JSpinner.DateEditor(date, DATE_TIME_FORMATTER));
        this.add(date);
    }
}
