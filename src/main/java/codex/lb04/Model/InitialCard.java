package codex.lb04.Model;

import codex.lb04.Model.Enumerations.Color;

import java.io.Serializable;

/**
 * This class represents initial cards
 */
public class InitialCard extends Card implements Serializable {

    private Integer ID;

    // No-argument constructor
    public InitialCard() {
        super();
    }


    /**
     * Default constructor
     *
     * @param front the face of the card
     * @param back  the face of the card
     */
    public InitialCard(Face front, Face back, Integer ID) {
        super(Color.YELLOW, front, back);
        this.ID = ID;
    }


}
