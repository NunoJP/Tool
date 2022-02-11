package presentation.fileanalysis;

import presentation.common.GuiConstants;
import presentation.common.custom.DateComponent;
import presentation.common.custom.GeneralTablePanel;
import presentation.common.custom.LabelTextFieldPanel;
import presentation.common.custom.ScrollableTextArea;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import static presentation.common.GuiConstants.H_GAP;
import static presentation.common.GuiConstants.V_GAP;

public class FileAnalysisScreen extends JPanel {

    private GeneralTablePanel fileContentsTable;
    private LabelTextFieldPanel origin;
    private LabelTextFieldPanel level;
    private DateComponent fromDateComponent;
    private DateComponent toDateComponent;
    private LabelTextFieldPanel message;
    private JButton searchButton;
    private JButton filterButton;
    private JButton clearButton;
    private JButton exportButton;
    private ScrollableTextArea messageDetailsTextArea;

    public FileAnalysisScreen() {
        this.setLayout(new BorderLayout(H_GAP, V_GAP));
        createComponents();
    }

    private void createComponents() {
        this.add(createNorthPanel(), BorderLayout.NORTH);
        this.add(createCenterPanel(), BorderLayout.CENTER);
    }

    private JPanel createNorthPanel() {
        return createFilterPanel();
    }

    private JPanel createCenterPanel() {
        JPanel holder = new JPanel(new BorderLayout());
        messageDetailsTextArea = new ScrollableTextArea();
        fileContentsTable = new GeneralTablePanel(
                new String[]{GuiConstants.DATE_COLUMN, GuiConstants.TIME_COLUMN,
                        GuiConstants.ORIGIN_COLUMN, GuiConstants.LEVEL_COLUMN, GuiConstants.MESSAGE_COLUMN}, false
        );
        resizeTableToFitContents();

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, messageDetailsTextArea, fileContentsTable);
        holder.add(splitPane, BorderLayout.NORTH);
        return holder;
    }


    private JPanel createFilterPanel() {
        JPanel filterPanel = new JPanel(new GridLayout(3, 1, H_GAP, 0));
        // From Date/Time, Origin, Level
        JPanel firstRow = new JPanel(new FlowLayout(FlowLayout.LEFT, H_GAP, 0));
        fromDateComponent = new DateComponent(GuiConstants.FROM_LABEL);
        origin = new LabelTextFieldPanel(GuiConstants.ORIGIN_LABEL, GuiConstants.METHOD_FIELD_SIZE);
        level = new LabelTextFieldPanel(GuiConstants.LEVEL_LABEL, GuiConstants.LEVEL_FIELD_SIZE);
        firstRow.add(fromDateComponent);
        firstRow.add(origin);
        firstRow.add(level);

        // To Date/Time, Message, Search button
        JPanel secondRow = new JPanel(new FlowLayout(FlowLayout.LEFT, H_GAP, 0));
        toDateComponent = new DateComponent(GuiConstants.TO_LABEL);
        message = new LabelTextFieldPanel(GuiConstants.MESSAGE_LABEL, GuiConstants.MESSAGE_FIELD_SIZE);
        searchButton = new JButton(GuiConstants.SEARCH_BUTTON);
        secondRow.add(toDateComponent);
        secondRow.add(message);
        secondRow.add(searchButton);

        // Filter, Clear, Export buttons
        JPanel thirdRow = new JPanel(new FlowLayout(FlowLayout.LEFT, H_GAP, 0));
        JPanel spacer = new JPanel(new FlowLayout(FlowLayout.LEFT, H_GAP, 0));
        filterButton = new JButton(GuiConstants.FILTER_BUTTON);
        clearButton = new JButton(GuiConstants.CLEAR_BUTTON);
        exportButton = new JButton(GuiConstants.EXPORT_BUTTON);
        spacer.add(filterButton);
        spacer.add(clearButton);
        spacer.add(exportButton);
        thirdRow.add(spacer);

        // adding panels
        filterPanel.add(firstRow);
        filterPanel.add(secondRow);
        filterPanel.add(thirdRow);
        return filterPanel;
    }


    public void resizeTableToFitContents() {
        fileContentsTable.resizeToMatchContents();
    }

    public GeneralTablePanel getFileContentsTable() {
        return fileContentsTable;
    }

    public LabelTextFieldPanel getOrigin() {
        return origin;
    }

    public LabelTextFieldPanel getLevel() {
        return level;
    }

    public DateComponent getFromDateComponent() {
        return fromDateComponent;
    }

    public DateComponent getToDateComponent() {
        return toDateComponent;
    }

    public LabelTextFieldPanel getMessage() {
        return message;
    }

    public JButton getSearchButton() {
        return searchButton;
    }

    public JButton getFilterButton() {
        return filterButton;
    }

    public JButton getClearButton() {
        return clearButton;
    }

    public JButton getExportButton() {
        return exportButton;
    }

    public ScrollableTextArea getMessageDetailsTextArea() {
        return messageDetailsTextArea;
    }
}
