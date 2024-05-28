package codex.lb04.Model;

import codex.lb04.Model.Enumerations.Color;

import java.io.Serial;

/**
 * This class represents a resource card
 */
public class ResourceCard extends Card {

    @Serial
    private static final long serialVersionUID = -105622119513609467L;

    /**
     * Default constructor
     *
     * @param color  the color of the card
     * @param front  the face of the card
     * @param back   the face of the card
     * @param points the points of the card
     * @param ID     the ID of the card
     */
    public ResourceCard(Color color, Face front, Face back, int points, Integer ID) {
        super(color, front, back, points, ID);
    }

    /**
     * Constructor for cards with coordinates
     *
     * @param color                       the color of the card
     * @param front                       the face of the card
     * @param back                        the face of the card
     * @param points                      the points of the card
     * @param ID                          the ID of the card
     * @param x                           the x coordinate of the card
     * @param y                           the y coordinate of the card
     * @param isShowingFront              true if the card is showing the front face, false otherwise
     * @param usedForPositionalObjectives true if the card is used for positional objectives, false otherwise
     */
    public ResourceCard(Color color, Face front, Face back, int points, Integer ID, Integer x, Integer y, boolean isShowingFront, boolean usedForPositionalObjectives) {
        super(color, front, back, points, ID, x, y, isShowingFront, usedForPositionalObjectives);
    }

    /**
     * override of the clone method to create a deep copy of the card to avoid problems in serialization
     *
     * @return a deep copy of the card
     */
    @Override
    public ResourceCard clone() {
        return new ResourceCard(this.getColor(), this.getFront(), this.getBack(), this.getPoints(), this.getID(), this.getX(), this.getY(), this.isShowingFront(), this.isUsedForPositionalObjectives());
    }
}