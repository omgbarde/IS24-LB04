package codex.lb04.Model;

import codex.lb04.Model.Enumerations.Color;

/**
 * This class represents a generic card
 */
public class Card {
    public Face ShownFace;
    private final Face front;
    private final Face back;
    private Color color;
    private Integer points;
    private Integer x , y;
    /**
     * Default constructor
     * @param front the face of the card
     * @param back the face of the card
     * @param color the color of the card
     */
    public Card(Color color , Face front , Face back){
        this.ShownFace = front;
        this.color = color;
        this.back = back;
        this.front = front;
        this.points = null;
        this.x = null;
        this.y = null;
    }
    /**
     * Default constructor for cards with points
     * @param front the face of the card
     * @param back the face of the card
     * @param color the color of the card
     */
    public Card(Color color , Face front , Face back , Integer points){
        this.ShownFace = front;
        this.color = color;
        this.back = back;
        this.front = front;
        this.points = points;
        this.x = null;
        this.y = null;
    }
    /**
     *  This method flips the card
     */
    public void flip (Card card){
        if (ShownFace == front){
            ShownFace = back;
        }else{
            ShownFace = front;
        }
    }
    /**
     * returns the shown face of the card
     * @return the shown face of the card
     */
    public Face getShownFace() {
        return ShownFace;
    }
    /**
     * Set the coordinates of the card
     */
    public void setCoordinates(int x, int y){
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
}