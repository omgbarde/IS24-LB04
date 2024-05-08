package codex.lb04.Message.DrawMessage;

import codex.lb04.Message.Message;
import codex.lb04.Message.MessageType;
import codex.lb04.Model.Card;

import java.io.Serial;

/**
 * Message to notify the user to draw a card.
 */
public class DrawCardMessage extends Message {
    @Serial
    private static final long serialVersionUID = 15;

    private Card card;

    /**
     * Constructor for DrawCardMessage
     * @param username username of the player
     * @param card card to draw
     */
    public DrawCardMessage(String username, Card card) {
        super(username, MessageType.DRAW_CARD);
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
     * Getter for username
     * @return username
     */
    public String getUsername() {
        return super.getUsername();
    }

    @Override
    public String toString() {
        return "PickGoldCardMessage{" +
                "nickname=" + getUsername() +
                ", pick=" + card +
                '}';
    }
}
