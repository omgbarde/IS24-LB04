package codex.lb04.Model;

import codex.lb04.Model.Enumerations.Color;

import java.io.Serializable;

/**
 * This class represents a generic card
 */
public class Card implements Serializable {
    private Face ShownFace;
    private Face front;
    private Face back;
    private Color color;
    private Integer points;
    private Integer x, y;
    private Integer ID;
    private boolean usedForPositionalObjectives = false;

    public void setUsedForPositionalObjectives(boolean usedForPositionalObjectives) {
        this.usedForPositionalObjectives = usedForPositionalObjectives;
    }

    public boolean isUsedForPositionalObjectives() {
        return usedForPositionalObjectives;
    }

    public Card(){
    }

    /**
     * Default constructor
     *
     * @param front the face of the card
     * @param back  the face of the card
     * @param color the color of the card
     */
    public Card(Color color, Face front, Face back) {
        this.ShownFace = back;
        this.color = color;
        this.back = back;
        this.front = front;
        this.points = null;

        this.x = null;
        this.y = null;
    }

    /**
     * Default constructor for cards with points
     *
     * @param front the face of the card
     * @param back  the face of the card
     * @param color the color of the card
     */
    public Card(Color color, Face front, Face back, Integer points , Integer ID) {
        this.ShownFace = back;
        this.color = color;
        this.back = back;
        this.front = front;
        this.points = points;
        this.ID = ID;
        this.x = null;
        this.y = null;
    }

    /**
     * This method flips the card
     */
    public void flip() {
        if (ShownFace == front) {
            ShownFace = back;
        } else {
            ShownFace = front;
        }
    }

    /**
     * returns the shown face of the card
     *
     * @return the shown face of the card
     */
    public Face getShownFace() {
        return ShownFace;
    }

    public boolean iShowingFront(){
        return this.ShownFace == this.front;
    }

    /**
     * Set the coordinates of the card
     */
    public void setCoordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Face getFront() {
        return front;
    }

    public Face getBack() {
        return back;
    }

    public Color getColor() {
        return color;
    }

    public Integer getPoints() {
        return points;
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    public Integer getID() {
        return ID;
    }

}