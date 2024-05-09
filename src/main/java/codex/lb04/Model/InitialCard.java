package codex.lb04.Model;

import codex.lb04.Model.Enumerations.Color;

import java.io.Serial;
import java.io.Serializable;

/**
 * This class represents initial cards
 */
public class InitialCard extends Card implements Serializable {
    @Serial
    private static final long serialVersionUID = 63400;

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
        super(Color.YELLOW, front, back ,0 , ID);
        this.ID = ID;
    }

    public Integer getID() {
        return ID;
    }

}
