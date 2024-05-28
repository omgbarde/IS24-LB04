package codex.lb04.Message.GameMessage;

import codex.lb04.Message.Message;
import codex.lb04.Message.MessageType;

import java.io.Serial;

/**
 * Message sent when a client picks a resource card
 */
public class PickResourceCardMessage extends Message {
    @Serial
    private static final long serialVersionUID = 3067569892535805654L;

    private final Integer cardPick;

    /**
     * constructor for PickResourceCardMessage
     *
     * @param username username of the player
     * @param cardPick choice of the card to pick
     */
    public PickResourceCardMessage(String username, Integer cardPick) {
        super(username, MessageType.PICK_RESOURCE_CARD);
        this.cardPick = cardPick;
    }

    /**
     * Getter for cardPick
     *
     * @return cardPick
     */
    public Integer getCardPick() {
        return cardPick;
    }

    /**
     * Getter for username
     *
     * @return username
     */
    public String getUsername() {
        return super.getUsername();
    }

    @Override
    public String toString() {
        return "PickResourceCardMessage{" + "nickname=" + getUsername() + ", pick=" + cardPick + '}';
    }
}
