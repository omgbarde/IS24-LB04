package codex.lb04.Model;

import codex.lb04.Model.Enumerations.Color;

/**
 * This class represents a resource card
 */
public class ResourceCard extends Card {

    private int points;
    private Integer ID;

    /**
     * Default constructor
     *
     * @param color  the color of the card
     * @param front  the face of the card
     * @param back   the face of the card
     * @param points the points of the card
     */
    public ResourceCard(Color color, Face front, Face back, int points, Integer ID) {
        super(color, front, back, points);
        this.points = points;
        this.ID = ID;
    }
}
