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

    public InitialCard clone(){
        return new InitialCard(this.getFront(), this.getBack(), this.getID() , this.getX(), this.getY() , this.isShowingFront() , this.isUsedForPositionalObjectives());
    }

    public InitialCard(Face front, Face back, Integer ID , Integer x, Integer y , boolean isShowingFront , boolean usedForPositionalObjectives) {
        super(Color.YELLOW, front, back ,0 , ID , x, y , isShowingFront , usedForPositionalObjectives);
        this.ID = ID;
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
