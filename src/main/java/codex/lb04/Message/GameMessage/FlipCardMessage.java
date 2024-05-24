package codex.lb04.Message.GameMessage;

import codex.lb04.Message.Message;
import codex.lb04.Message.MessageType;
import codex.lb04.Model.Card;

import java.io.Serial;
/**
 * Message sent when a client flips a card
 */
public class FlipCardMessage extends Message {
    @Serial
    private static final long serialVersionUID = -5242076736742108786L;

    private final Card card;

    /**
     * Constructor for FlipCardMessage
     * @param username username of the player
     */
    public FlipCardMessage(String username, Card card) {
        super(username, MessageType.FLIP_CARD);
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
        return "FlipCardMessage{" +
                "nickname=" + getUsername() +
                '}';
    }
}
