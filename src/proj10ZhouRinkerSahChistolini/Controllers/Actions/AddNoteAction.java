/**
 * File: AddNoteAction.java
 * @author Victoria Chistolini
 * @author Edward (osan) Zhou
 * @author Alex Rinker
 * @author Vivek Sah
 * Class: CS361
 * Project: 5
 * Date: Nov 1, 2016
 */
package proj10ZhouRinkerSahChistolini.Controllers.Actions;

import proj10ZhouRinkerSahChistolini.Controllers.CompositionPanelController;
import proj10ZhouRinkerSahChistolini.Models.Playable;
import proj10ZhouRinkerSahChistolini.Views.NoteRectangle;
import proj10ZhouRinkerSahChistolini.Models.Note;

import java.util.Collection;
import javafx.scene.Node;
import proj10ZhouRinkerSahChistolini.Views.SelectableRectangle;

/**
 * holds information necessary to redo and undo adding a note
 */
public class AddNoteAction implements Actionable {

    /**N ote rectangle to be added */
    private NoteRectangle graphicalNote;
    /** note to be added */
    private Note modelNote;
    /** boolean representing whether or not meta was down */
    private boolean isMetaDown;
    /** collection of all rectangles */
    private Collection<Node> recs;
    /** collection of all notes */
    private Collection<Playable> notes;
    /** collection of all selectable rectangles */
    private Collection<SelectableRectangle> selected;
    /** collection of all */
    private CompositionPanelController compController;

    /**
     * constructor method
     * @param graphicalNote The NoteRectangle to be added
     * @param modelNote the associated note to be added
     * @param selected collection of selectable rectangles
     * @param metaDown whether or not meta was down
     * @param comp the associated composition controller
     */
    public AddNoteAction(
            NoteRectangle graphicalNote,
            Note modelNote, Collection<SelectableRectangle> selected,
            boolean metaDown,
            CompositionPanelController comp
    ){
        this.isMetaDown = metaDown;
        this.selected = selected;
        this.graphicalNote = graphicalNote;
        this.modelNote = modelNote;
        this.recs = comp.getCompositionPane().getChildren();
        this.notes = comp.getNotesfromComposition();
        this.compController = comp;
    }

    /**
     * re-adds the note/rectangle to the composition
     */
    @Override
    public void reDoIt() {
        if (this.isMetaDown){
            for (SelectableRectangle  rec : this.selected) {
                rec.setSelected(false);
            }
        }else{
            for (SelectableRectangle  rec : this.compController.getRectangles()) {
                rec.setSelected(false);
            }
        }
        this.graphicalNote.setSelected(true);
        compController.addNoteRectangle(this.graphicalNote, true);
        notes.add(this.modelNote);
    }

    /**
     * removes the note/rectangle from the composition
     */
    @Override
    public void unDoIt() {
        recs.remove(this.graphicalNote);
        recs.remove(this.graphicalNote.getTransparency());
        notes.remove(this.modelNote);
        for (SelectableRectangle  rec : this.selected) {
            rec.setSelected(true);
        }
    }
}

