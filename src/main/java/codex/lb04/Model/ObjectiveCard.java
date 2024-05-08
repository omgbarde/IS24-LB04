package codex.lb04.Model;

import codex.lb04.Model.Enumerations.Color;
import codex.lb04.Model.Enumerations.ResourceType;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class represents an objective card, both secret and non
 */
public class ObjectiveCard extends Card implements Serializable {
    private  int points;
    private  ArrayList<ResourceType> resourceNeeded = new ArrayList<>();
    private boolean inGame;
    private  Integer ID;

    public ObjectiveCard(){
        super();
    }

    /**
     * Default constructor
     *
     * @param color the color of the card
     * @param front the face of the card
     * @param back  the face of the card
     */
    public ObjectiveCard(Color color, Face front, Face back, int points, int ID) {
        super(color, front, back , points , ID);
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
     * this method sets the objective card as in game
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
