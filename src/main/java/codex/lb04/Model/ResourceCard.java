package codex.lb04.Model;

import codex.lb04.Model.Enumerations.Color;

import java.io.Serial;
import java.io.Serializable;

/**
 * This class represents a resource card
 */
public class ResourceCard extends Card  implements Serializable{

    @Serial
    private static final long serialVersionUID = 63540;

    private int points;
    private Integer ID;

    public ResourceCard(){
        super();
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
        super(color, front, back , points , ID);
        this.points = points;
        this.ID = ID;
    }

    public Integer getID() {
        return ID;
    }
}
