/**
 * File: NoteRectangle.java
 * @author Victoria Chistolini
 * @author Tiffany Lam
 * @author Joseph Malionek
 * @author Vivek Sah
 * Class: CS361
 * Project: 4
 * Date: October 11, 2016
 */

package proj7ZhouRinkerSahChistolini.Views;

import javafx.beans.property.BooleanProperty;
import proj7ZhouRinkerSahChistolini.Models.Instrument;

/**
 * Represents a musical note in the gui interface
 */
public class NoteRectangle extends SelectableRectangle {

    /**int value for the instrument**/
    private Instrument instrument;

    /**
     * The constructor of the NoteRectangle
     * @param x
     * @param y
     * @param width
     * @param height
     * @param instr
     */
    public NoteRectangle(double x, double y,
                         double width, double height,
                         Instrument instr ) {
        super(x, y, width, height);
        this.getStyleClass().add("note");
        this.getStyleClass().add(instr.getName().toLowerCase().replace(" ", "-"));
        this.instrument = instr;
    }

    /**
     * sets the selection of the rectangle
     * @param selected
     */
    public void setSelected(boolean selected){
        if(selected){
            this.getStyleClass().removeAll("note");
            this.getStyleClass().add("selected-note");
        }
        else{
            this.getStyleClass().removeAll("selected-note");
            this.getStyleClass().add("note");
        }
        this.selected.set(selected);
    }

    /**
     * getter for selectedProperty
     * @return selectedProperty
     */
    public BooleanProperty selectedProperty() {
        return this.selected;
    }

    @Override
    /**
     * x,y,width, name, channel, integer representing MIDI instrucment
     * @return
     */
    public String toString() {
        String noteString = this.xProperty().intValue() +" "+
                this.yProperty().intValue() +" "+
                this.widthProperty().getValue() +" " +
                this.instrument.getName() + " " +
                this.instrument.getChannel() + " " +
                this.instrument.getValue() +"\n";
        return noteString;
    }
}
