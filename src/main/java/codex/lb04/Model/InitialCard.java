package codex.lb04.Model;

import codex.lb04.Model.Enumerations.Color;

/**
 * This class represents initial cards
 */
public class InitialCard extends Card {
    private Face front;
    private Face back;


    /**
     * Default constructor
     *
     * @param front the face of the card
     * @param back  the face of the card
     */
    public InitialCard(Face front, Face back) {
        super(Color.YELLOW, front, back);
    }


}
