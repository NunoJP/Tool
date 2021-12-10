package presentation.organization;

import presentation.common.ITabPresenter;

import javax.swing.JPanel;

public class OrganizationScreenPresenter implements ITabPresenter {

    private final OrganizationScreen view;

    public OrganizationScreenPresenter() {
        view = new OrganizationScreen();
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
