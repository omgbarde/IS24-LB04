package codex.lb04.Message.GameMessage;

import codex.lb04.Message.Message;
import codex.lb04.Message.MessageType;
import codex.lb04.Model.Card;

import java.io.Serial;

/**
 * Message sent when a client places a card
 */
public class PlaceCardMessage extends Message {
    @Serial
    private static final long serialVersionUID = 19;

    private Integer X, Y;
    private Card card;

    /**
     * Constructor for PlaceCardMessage
     * @param username username of the player
     * @param X x coordinate of the card
     * @param Y y coordinate of the card
     * @param card card to place
     *
     */
    public PlaceCardMessage(String username, Integer X, Integer Y, Card card) {
        super(username, MessageType.PLACE_CARD);
        this.X = X;
        this.Y = Y;
        this.card = card;
    }

    /**
     * Getter for card
     * @return card
     */
    public Card getCard() {
        return card;
    }

    /**
     * Getter for X
     * @return X
     */
    public Integer getX() {
        return X;
    }

    /**
     * Getter for Y
     * @return Y
     */
    public Integer getY() {
        return Y;
    }

    /**
     * Getter for username
     * @return username
     */
    public String getUsername() {
        return super.getUsername();
    }

    @Override
    public String toString() {
        return "PlaceCardMessage{" + "nickname=" + getUsername() + ", X=" + X + ", Y=" + Y + ", cardID=" + card + '}';
    }

}
