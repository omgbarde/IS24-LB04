package codex.lb04.Message.GameMessage;

import codex.lb04.Message.Message;
import codex.lb04.Message.MessageType;

import java.io.Serial;

public class PickGoldCardMessage extends Message {
    @Serial
    private static final long serialVersionUID = 15;

    private Integer cardPick;

    /**
     * Constructor for PickGoldCardMessage
     * @param username username of the player
     * @param cardPick choice of the card to pick
     */
    public PickGoldCardMessage(String username, Integer cardPick) {
        super(username, MessageType.PICK_GOLD_CARD);
        this.cardPick = cardPick;
    }

    /**
     * Getter for cardPick
     * @return cardPick
     */
    public Integer getCardPick() {
        return cardPick;
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
                ", pick=" + cardPick +
                '}';
    }
}
