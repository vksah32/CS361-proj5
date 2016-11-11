/**
 * File: Controller.java
 * @author Victoria Chistolini
 * @author Edward (osan) Zhou
 * @author Alex Rinker
 * @author Vivek Sah
 * Class: CS361
 * Project: 5
 * Date: Nov 1, 2016
 */

package proj7ZhouRinkerSahChistolini.Controllers;

import javafx.fxml.FXML;
import javafx.application.Platform;
import javafx.scene.control.MenuItem;
import proj7ZhouRinkerSahChistolini.Controllers.Actions.DeleteNoteAction;
import proj7ZhouRinkerSahChistolini.Controllers.Actions.PasteAction;
import proj7ZhouRinkerSahChistolini.Controllers.Actions.SelectAction;
import proj7ZhouRinkerSahChistolini.Models.Instrument;
import proj7ZhouRinkerSahChistolini.Views.SelectableRectangle;


import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.Collection;

/**
 * Handles menu GUI interactions with other controllers
 */
public class Controller {

    /** The compositionPanel object that is modified */
    @FXML
    public CompositionPanelController compositionPanelController;

    /** an instrumentController field which allows us to talk to the InstrumentPanel */
    @FXML
    private InstrumentPanelController instrumentPaneController;

    /** a controller to assist in bindings between the menus and controllers*/
    private BindingController bindingController;

    private ClipBoardController clipboardController;


    /**
     * All of our MenuItem are put into fields
     * Each of the following FXML elements are MenuItems defined in
     * our fxml. They are injected here into our controller so that
     * we can bind their disable properties
     */
    @FXML
    private MenuItem stopButton;
    @FXML
    public MenuItem startButton;
    @FXML
    public MenuItem selectAllButton;
    @FXML
    private MenuItem deleteButton;
    @FXML
    private MenuItem groupButton;
    @FXML
    private MenuItem ungroupButton;
    @FXML
    private MenuItem undoButton;
    @FXML
    private MenuItem redoButton;
    @FXML
    private MenuItem cutButton;
    @FXML
    private MenuItem copyButton;
    @FXML
    private MenuItem pasteButton;

    /** Initializes the controllers so they can communicate properly */
    @FXML
    public void initialize() {
        this.compositionPanelController.init(this);
        this.clipboardController = new ClipBoardController(
                this.compositionPanelController,
                this.compositionPanelController.getActionController());
        this.bindingController = new BindingController(
                this,
                this.compositionPanelController,
                this.clipboardController);
        bindMenuItems();
    }

    /** Returns the currently selected instrument */
    public Instrument getSelectedInstrument() {
        return this.instrumentPaneController.getSelectedInstrument();
    }

    /**
     * Ensures that all processes are killed on the
     * destruction of the window.
     */
    @FXML
    public void cleanUpOnExit() {
        Platform.exit();
        System.exit(0);
    }

    @FXML
    public void playComposition() { this.compositionPanelController.playComposition(); }

    @FXML
    public void stopComposition() { this.compositionPanelController.stopComposition(); }

    @FXML
    /**
     * deletes the selected notes
     */
    public void deleteSelectedNotes() {
        DeleteNoteAction deletedNotes = new DeleteNoteAction(
            this.compositionPanelController.getSelectedRectangles(),
            this.compositionPanelController.getSelectedNotes(),
            this.compositionPanelController
        );
        this.compositionPanelController.deleteSelectedNotes();
        this.compositionPanelController.addAction(deletedNotes);
    }

    @FXML
    /**
     * selects all the notes
     */
    public void selectAllNotes() {
        //add
        Collection<SelectableRectangle> before = (
            this.compositionPanelController.getSelectedRectangles()
        );
        this.compositionPanelController.selectAllNotes();
        this.compositionPanelController.addAction(
                new SelectAction(
                        before,
                        this.compositionPanelController.getRectangles(),
                        this.compositionPanelController
                )
        );
    }

    @FXML
    /**
     * group the selected notes
     */
    public void groupSelected() { this.compositionPanelController.groupSelected(this.compositionPanelController.getSelectedRectangles()); }

    @FXML
    /**
     * copies the selected rectangles
     */
    public void copySelected() {
        this.clipboardController.copySelected();
    }

    @FXML
    /**
     * cut the selected rectangles
     */
    public void cutSelected() {
        this.clipboardController.copySelected();
        this.deleteSelectedNotes();
    }

    @FXML
    /**
     * paste the copied rectangles
     */
    public void pasteSelected() throws IOException, UnsupportedFlavorException {
        this.clipboardController.pasteSelected();
        PasteAction pastedNotes = new PasteAction(this.compositionPanelController.getSelectedRectangles(),
                this.compositionPanelController.getSelectedNotes(), this.compositionPanelController);
        this.compositionPanelController.addAction(pastedNotes);
    }

    @FXML
    /**
     * ungroup the selected notes
     */
    public void ungroupSelected() { this.compositionPanelController.ungroupSelected(this.compositionPanelController.getSelectedRectangles()); }

    @FXML
    /**
     * undoes the latest action
     */
    public void undo() {
        this.compositionPanelController.getActionController().undo();
    }

    @FXML
    /**
     * redoes the latest undo action
     */
    public void redo() {
        this.compositionPanelController.getActionController().redo();
    }

    /**
     * returns whether or not the composition is playing
     */
    public void bindMenuItems() {
        //stopButton
        this.stopButton.disableProperty().bind(
                this.compositionPanelController.getTempoLine().isPlayingProperty().not()
        );
        //startButton
        this.startButton.disableProperty().bind(
                this.bindingController.getChildrenProperty().sizeProperty().isEqualTo(0)
        );
        //selectAllButton
        this.selectAllButton.disableProperty().bind(
                this.bindingController.getChildrenProperty().sizeProperty().isEqualTo(0)
        );
        //deleteButton
        this.deleteButton.disableProperty().bind(
                this.bindingController.getAreNotesSelectedBinding()
        );
        //groupButton
        this.groupButton.disableProperty().bind(
                this.bindingController.getMultipleSelectedBinding()
        );
        //UngroupButton
        this.ungroupButton.disableProperty().bind(
                this.bindingController.getGroupSelectedBinding()
        );
        //redoButton
        this.redoButton.disableProperty().bind(
                this.bindingController.getRedoEmptyBinding()
        );
        //undoButton
        this.undoButton.disableProperty().bind(
                this.bindingController.getUndoEmptyBinding()
        );
        //cutButton
        this.cutButton.disableProperty().bind(
                this.bindingController.getAreNotesSelectedBinding()
        );
        //copyButton
        this.copyButton.disableProperty().bind(
                this.bindingController.getAreNotesSelectedBinding()
        );
        //pasteButton
        this.pasteButton.disableProperty().bind(
                this.bindingController.getClipBoardEmptyBinding()
        );
    }
}
