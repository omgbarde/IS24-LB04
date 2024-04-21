package codex.lb04.Model;

import codex.lb04.Model.Enumerations.Color;

/**
 * This class represents initial cards
 */
public class InitialCard extends Card {
    private Face front;
    private Face back;
    private Integer ID;


    /**
     * Default constructor
     *
     * @param front the face of the card
     * @param back  the face of the card
     */
    public InitialCard(Face front, Face back , Integer ID) {
        super(Color.YELLOW, front, back);
        this.ID = ID;
    }


}
