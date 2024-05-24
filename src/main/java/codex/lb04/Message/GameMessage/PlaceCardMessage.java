package codex.lb04.Message.GameMessage;

import codex.lb04.Message.Message;
import codex.lb04.Message.MessageType;
import codex.lb04.Model.Card;

import java.io.Serial;

/**
 * Message sent when a client asks to place a card and is resent back if the placement is valid
 */
public class PlaceCardMessage extends Message {
    @Serial
    private static final long serialVersionUID = 1467211930231572873L;

    private final Integer x;
    private final Integer y;
    private final Card card;

    /**
     * Constructor for PlaceCardMessage
     * @param username username of the player
     * @param x the x coordinate where you want to place the card
     * @param y the y coordinate where you want to place the card
     * @param card card to place
     *
     */
    public PlaceCardMessage(String username, Integer x, Integer y, Card card) {
        super(username, MessageType.PLACE_CARD);
        this.x = x;
        this.y = y;
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
     * Getter for x coordinate
     * @return the x coordinate
     */
    public Integer getX() {
        return x;
    }

    /**
     * Getter for y coordinate
     * @return the y coordinate
     */
    public Integer getY() {
        return y;
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
        return "PlaceCardMessage{" + "nickname=" + getUsername() + ", X=" + x + ", Y=" + y + ", cardID=" + card + '}';
    }

}
