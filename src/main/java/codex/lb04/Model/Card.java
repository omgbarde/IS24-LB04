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
    private int points;
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
}