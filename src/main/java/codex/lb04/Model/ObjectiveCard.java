package codex.lb04.Model;

import codex.lb04.Model.Enumerations.Color;

import java.io.Serial;

/**
 * This class represents an objective card, both secret and non
 */
public class ObjectiveCard extends Card {

    @Serial
    private static final long serialVersionUID = -4160177644632182402L;

    /**
     * Default constructor
     *
     * @param color  the color of the card
     * @param front  the face of the card
     * @param back   the face of the card
     * @param points the points of the card
     * @param ID     the ID of the card
     */
    public ObjectiveCard(Color color, Face front, Face back, int points, int ID) {
        super(color, front, back, points, ID);
    }

    /**
     * override of the clone method to create a deep copy of the card to avoid problems in serialization
     *
     * @return a deep copy of the card
     */
    @Override
    public ObjectiveCard clone() {
        return new ObjectiveCard(this.getColor(), this.getFront(), this.getBack(), this.getPoints(), this.getID());
    }
}