package codex.lb04.Model;

import codex.lb04.Model.Enumerations.Color;
import codex.lb04.Model.Enumerations.ResourceType;

import java.util.ArrayList;

/**
 * This class represents an objective card, both secret and non
 */
public class ObjectiveCard extends Card {
    private final int points;
    private final ArrayList<ResourceType> resourceNeeded = new ArrayList<>();
    private boolean inGame;
    private final Integer ID;

    /**
     * Default constructor
     *
     * @param color the color of the card
     * @param front the face of the card
     * @param back  the face of the card
     */
    public ObjectiveCard(Color color, Face front, Face back, int points, int ID) {
        super(color, front, back);
        this.points = points;
        this.inGame = false;
        this.ID = ID;
    }

    /**
     * this method returns the points of the objective card
     *
     * @return the points of the objective card
     */
    public Integer getPoints() {
        return points;
    }

    /**
     * this method sets if the objective card is in game
     *
     * @return true if the objective card is in game, false otherwise
     */
    public void setInGame() {
        this.inGame = true;
    }

    /**
     * this method returns the ID of the objective card
     *
     * @return the ID of the objective card
     */
    public Integer getID() {
        return ID;
    }

}
