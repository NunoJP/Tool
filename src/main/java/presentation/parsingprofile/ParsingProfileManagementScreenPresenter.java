package presentation.parsingprofile;

import presentation.common.ITabPresenter;

import javax.swing.JPanel;

public class ParsingProfileManagementScreenPresenter implements ITabPresenter {

    private final ParsingProfileManagementScreen view;

    public ParsingProfileManagementScreenPresenter() {
        view = new ParsingProfileManagementScreen();
        defineViewBehavior();
    }

    private void defineViewBehavior() {

    }

    @Override
    public void execute() {

    }

    @Override
    public JPanel getView() {
        return view;
    }
}
