package presentation.parsingprofile;

import domain.entities.displayobjects.ParsingProfileDo;
import domain.services.ParsingProfileManagementService;
import presentation.common.IViewPresenter;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ParsingProfileEditorScreenPresenter implements IViewPresenter {

    private final JFrame motherFrame;
    private final ParsingProfileEditorScreen dialogView;
    private final ParsingProfileManagementService service;

    public ParsingProfileEditorScreenPresenter(JFrame motherFrame, ParsingProfileDo existingProfile) {
        this.motherFrame = motherFrame;
        dialogView = new ParsingProfileEditorScreen(motherFrame);
        service = new ParsingProfileManagementService();
        defineViewBehavior();
    }

    public ParsingProfileEditorScreenPresenter(JFrame motherFrame) {
        this(motherFrame, null);
    }

    private void defineViewBehavior() {

    }

    @Override
    public void execute() {
        dialogView.setLocationRelativeTo(motherFrame);
        dialogView.setVisible(true);
    }

    @Override
    public JPanel getView() {
        return dialogView.getPanel();
    }
}
