package presentation.fileanalysis;

import presentation.common.ITabPresenter;

import javax.swing.JPanel;

public class FileAnalysisSetupScreenPresenter implements ITabPresenter {

    private final FileAnalysisSetupScreen view;

    public FileAnalysisSetupScreenPresenter() {
        view = new FileAnalysisSetupScreen();
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
