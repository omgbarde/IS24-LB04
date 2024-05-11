package codex.lb04.Model;

import codex.lb04.Model.Enumerations.Color;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;

/**
 * This class represents a resource card
 */
public class ResourceCard extends Card {


    private int points;
    private Integer ID;


    public ResourceCard() {
        super();

    }

    public ResourceCard clone() {
        return new ResourceCard(this.getColor(), this.getFront(), this.getBack(), this.points, this.ID, this.getX(), this.getY(), this.isShowingFront(), this.isUsedForPositionalObjectives());
    }

    public ResourceCard(Color color, Face front, Face back, int points, Integer ID, Integer x, Integer y, boolean isShowingFront, boolean usedForPositionalObjectives) {
        super(color, front, back, points, ID, x, y, isShowingFront, usedForPositionalObjectives);
        this.points = points;
        this.ID = ID;
    }

    /**
     * Default constructor
     *
     * @param color  the color of the card
     * @param front  the face of the card
     * @param back   the face of the card
     * @param points the points of the card
     */
    public ResourceCard(Color color, Face front, Face back, int points, Integer ID) {
        super(color, front, back, points, ID);
        this.points = points;
        this.ID = ID;
    }

    public Integer getID() {
        return ID;
    }
}
