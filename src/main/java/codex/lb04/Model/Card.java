package codex.lb04.Model;

import codex.lb04.Model.Enumerations.Color;

import java.io.Serial;
import java.io.Serializable;

/**
 * This class represents a generic card
 */
public class Card implements Serializable {

    @Serial
    private static final long serialVersionUID = 1978554446086060428L;

    private final Face front;
    private final Face back;
    private boolean isShowingFront;

    private final Color color;
    private final Integer points;
    private Integer x, y;
    private Integer ID;

    private boolean usedForPositionalObjectives = false;

    /**
     * Test constructor for dummy cards with no points and no id
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

    /**
     * Default constructor for cards with points
     *
     * @param front  the face of the card
     * @param back   the face of the card
     * @param color  the color of the card
     * @param points the points of the card
     * @param ID     the ID of the card
     */
    public Card(Color color, Face front, Face back, Integer points, Integer ID) {
        this.color = color;
        this.back = back;
        this.front = front;
        this.points = points;
        this.ID = ID;
        this.x = null;
        this.y = null;
    }

    /**
     * constructor for cards with coordinates
     *
     * @param color                       the color of the card
     * @param front                       the front face of the card
     * @param back                        the back face of the card
     * @param points                      the points of the card
     * @param ID                          the ID of the card
     * @param x                           the x coordinate of the card
     * @param y                           the y coordinate of the card
     * @param isShowingFront              true if the card is showing the front face
     * @param usedForPositionalObjectives true if the card is used for positional objectives
     */
    public Card(Color color, Face front, Face back, Integer points, Integer ID, Integer x, Integer y, boolean isShowingFront, boolean usedForPositionalObjectives) {
        this.color = color;
        this.back = back;
        this.front = front;
        this.points = points;
        this.ID = ID;
        this.x = x;
        this.y = y;
        this.isShowingFront = isShowingFront;
        this.usedForPositionalObjectives = usedForPositionalObjectives;
    }

    /**
     * This method flips the card
     */
    public void flip() {
        isShowingFront = !isShowingFront;
    }

    /**
     * This method returns the shown face of the card
     *
     * @return true if the card is showing the front face , false otherwise
     */
    public boolean isShowingFront() {
        return isShowingFront;
    }

    /**
     * this method returns if the card is used for positional objectives
     *
     * @return true if the card is used for positional objectives , false otherwise
     */
    public boolean isUsedForPositionalObjectives() {
        return usedForPositionalObjectives;
    }

    /**
     * override of the clone method to create a deep copy of the card to avoid problems in serialization
     *
     * @return a deep copy of the card
     */
    @Override
    public Card clone() {
        return new Card(this.color,
                this.front,
                this.back,
                this.points,
                this.ID,
                this.x,
                this.y,
                this.isShowingFront,
                this.usedForPositionalObjectives);
    }

    //GETTER

    /**
     * This method returns the shown face of the card
     *
     * @return the shown face of the card
     */
    public Face getShownFace() {
        if (isShowingFront) {
            return front;
        } else {
            return back;
        }
    }

    /**
     * This method returns the front of the card
     *
     * @return the front of the card
     */
    public Face getFront() {
        return front;
    }

    /**
     * This method returns the back of the card
     *
     * @return the back of the card
     */
    public Face getBack() {
        return back;
    }

    /**
     * This method returns the color of the card
     *
     * @return the color of the card
     */
    public Color getColor() {
        return color;
    }

    /**
     * This method returns the points of the card
     *
     * @return the points of the card
     */
    public Integer getPoints() {
        return points;
    }

    /**
     * This method returns the x coordinate of the card
     *
     * @return the x coordinate of the card
     */
    public Integer getX() {
        return x;
    }

    /**
     * This method returns the y coordinate of the card
     *
     * @return the y coordinate of the card
     */
    public Integer getY() {
        return y;
    }

    /**
     * This method returns the ID of the card
     *
     * @return the ID of the card
     */
    public Integer getID() {
        return ID;
    }

    //SETTER

    /**
     * Set the coordinates of the card
     */
    public void setCoordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Set the shown face of the card
     *
     * @param showingFront true if the card needs to be showing the front face , false otherwise
     */
    public void setShowingFront(boolean showingFront) {
        this.isShowingFront = showingFront;
    }

    /**
     * Set the card as already used for positional objectives
     * this way the card will be counted only once for positional objectives
     *
     * @param usedForPositionalObjectives true if the card is used for positional objectives , false otherwise
     */
    public void setUsedForPositionalObjectives(boolean usedForPositionalObjectives) {
        this.usedForPositionalObjectives = usedForPositionalObjectives;
    }

}