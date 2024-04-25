package codex.lb04.Message.GameMessage;

import codex.lb04.Message.Message;
import codex.lb04.Message.MessageType;

public class PickSecretObjectiveMessage extends Message {

    private Integer cardPick;

    /**
     * Constructor for PickSecretObjectiveMessage
     * @param username username of the player
     * @param cardPick choice of the card to pick
     */
    public PickSecretObjectiveMessage(String username, Integer cardPick) {
        super(username, MessageType.PICK_SECRET_OBJECTIVE);
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
        return "PickSecretObjectiveMessage{" +
                "nickname=" + getUsername() +
                ", pick=" + cardPick +
                '}';
    }
}
