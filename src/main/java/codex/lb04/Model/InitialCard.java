package codex.lb04.Model;

import codex.lb04.Model.Enumerations.Color;

import java.io.Serial;

/**
 * This class represents initial cards
 */
public class InitialCard extends Card {

    @Serial
    private static final long serialVersionUID = 5499613247401499083L;

    /**
     * Default constructor
     *
     * @param front the face of the card
     * @param back  the face of the card
     * @param ID    the id of the card
     */
    public InitialCard(Face front, Face back, Integer ID) {
        super(Color.YELLOW, front, back, 0, ID);
    }

    /**
     * Constructor for cards with coordinates
     *
     * @param front                       the face of the card
     * @param back                        the face of the card
     * @param ID                          the id of the card
     * @param x                           the x coordinate of the card
     * @param y                           the y coordinate of the card
     * @param isShowingFront              true if the card is showing the front face, false otherwise
     * @param usedForPositionalObjectives true if the card is used for positional objectives, false otherwise
     */
    public InitialCard(Face front, Face back, Integer ID, Integer x, Integer y, boolean isShowingFront, boolean usedForPositionalObjectives) {
        super(Color.YELLOW, front, back, 0, ID, x, y, isShowingFront, usedForPositionalObjectives);
    }

    /**
     * override of the clone method to create a deep copy of the card to avoid problems in serialization
     *
     * @return a copy of the card
     */
    @Override
    public InitialCard clone() {
        return new InitialCard(this.getFront(), this.getBack(), this.getID(), this.getX(), this.getY(), this.isShowingFront(), this.isUsedForPositionalObjectives());
    }

}