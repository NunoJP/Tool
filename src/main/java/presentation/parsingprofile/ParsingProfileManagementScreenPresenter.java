package presentation.parsingprofile;

import domain.entities.displayobjects.ParsingProfileDo;
import domain.services.ParsingProfileManagementService;
import presentation.common.IViewPresenter;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

public class ParsingProfileManagementScreenPresenter implements IViewPresenter {

    private final ParsingProfileManagementScreen view;
    private final ParsingProfileManagementService service;
    private int selectedItem = -1;
    private ParsingProfileDo[] parsingProfiles;

    public ParsingProfileManagementScreenPresenter() {
        view = new ParsingProfileManagementScreen();
        service = new ParsingProfileManagementService();
        defineViewBehavior();
    }

    private void defineViewBehavior() {
        view.getParsingProfilesPanel().setLineSelectionOnly();
        view.getUpdateSelectedButton().setEnabled(false);
        view.getDeleteSelectedButton().setEnabled(false);
        view.getParsingProfilesPanel().addRowSelectionEvent( (event) -> {
            ListSelectionModel listSelectionModel = (ListSelectionModel) event.getSource();
            if(listSelectionModel.isSelectionEmpty()) {
                view.getUpdateSelectedButton().setEnabled(false);
                view.getDeleteSelectedButton().setEnabled(false);
                selectedItem = -1;
            } else {
                view.getUpdateSelectedButton().setEnabled(true);
                view.getDeleteSelectedButton().setEnabled(true);
                selectedItem = listSelectionModel.getSelectedIndices()[0];
            }
        });

        view.getNewProfileButton().addActionListener( (e -> {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(view);
            ParsingProfileEditorScreenPresenter editorScreenPresenter = new ParsingProfileEditorScreenPresenter(frame);
            editorScreenPresenter.execute();
        }));

        view.getDeleteSelectedButton().addActionListener( (e -> {
            if(userConfirmedOperation()) {
                boolean success = service.deleteProfile(parsingProfiles[selectedItem]);
                if(success) {
                    // show popup
                } else {
                    // show popup
                }
            }
        }));

        view.getUpdateSelectedButton().addActionListener( (e -> {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(view);
            ParsingProfileEditorScreenPresenter editorScreenPresenter
                    = new ParsingProfileEditorScreenPresenter(frame, parsingProfiles[selectedItem]);
            editorScreenPresenter.execute();
        }));
    }



    @Override
    public void execute() {
        parsingProfiles = service.getParsingProfiles();
        view.getParsingProfilesPanel().setData(convertDataForTable(parsingProfiles));
    }

    @Override
    public JPanel getView() {
        return view;
    }

    private boolean userConfirmedOperation() {
        return false;
    }


    private Object[][] convertDataForTable(ParsingProfileDo[] data) {
        Object[][] objects = new Object[data.length][];
        for (int i = 0; i <data.length; i++) {
            objects[i] = new Object[] { data[i].getName(), data[i].getDescription() };
        }
        return objects;
    }
}
