package codex.lb04.Model;

import codex.lb04.Model.Enumerations.Color;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;

/**
 * This class represents initial cards
 */
public class InitialCard extends Card  {


    private Integer ID;

    // No-argument constructor
    public InitialCard() {
        super();
    }


    public void readObjectSub(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        // print the value of ShownFace after deserialization
        in.defaultReadObject();
    }
    public void writeObjectSub(java.io.ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
    }


    /**
     * Default constructor
     *
     * @param front the face of the card
     * @param back  the face of the card
     */
    public InitialCard(Face front, Face back, Integer ID) {
        super(Color.YELLOW, front, back ,0 , ID);
        this.ID = ID;
    }

    public Integer getID() {
        return ID;
    }

}
