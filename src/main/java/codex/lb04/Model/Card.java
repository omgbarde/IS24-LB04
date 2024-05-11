package codex.lb04.Model;

import codex.lb04.Model.Enumerations.Color;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;

/**
 * This class represents a generic card
 */
public class Card implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;



    private boolean isShowingFront;
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

    public boolean isShowingFront() {
        return isShowingFront;
    }

    public boolean isUsedForPositionalObjectives() {
        return usedForPositionalObjectives;
    }

    public Card clone(){
        return new Card(this.color, this.front, this.back, this.points, this.ID);
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

        this.color = color;
        this.back = back;
        this.front = front;
        this.points = null;

        this.x = null;
        this.y = null;
    }

    public void setShowingFront(boolean showingFront) {
        this.isShowingFront = showingFront;
    }

    /**
     * Default constructor for cards with points
     *
     * @param front the face of the card
     * @param back  the face of the card
     * @param color the color of the card
     */
    public Card(Color color, Face front, Face back, Integer points , Integer ID) {
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
        if(isShowingFront){
            isShowingFront = false;
        }else {
            isShowingFront = true;
        }
    }

    /**
     * returns the shown face of the card
     *
     * @return the shown face of the card
     */
    public Face getShownFace() {
        if(isShowingFront) {
            return front;
        }else {
            return back;
        }
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

    public Integer getPoints() {return points;}

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