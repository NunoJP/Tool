package presentation.organization;

import presentation.common.IViewPresenter;

import javax.swing.JPanel;

public class OrganizationScreenPresenter implements IViewPresenter {

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
