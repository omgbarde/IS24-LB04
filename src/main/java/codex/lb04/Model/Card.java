package codex.lb04.Model;

import codex.lb04.Model.Enumerations.Color;

public class Card {
    public Face direction;
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
        this.direction = front;
        this.color = color;
        this.back = back;
        this.front = front;
    }

    /**
     *
     */
    public void changeDirection (Card card){
        if (direction == front){
            direction = back;
        }else{
            direction = front;
        }
    }

    public Face getDirection() {
        return direction;
    }

}