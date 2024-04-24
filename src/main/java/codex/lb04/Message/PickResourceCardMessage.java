package codex.lb04.Message;

public class PickResourceCardMessage extends Message {

    private Integer cardPick;

    /**
     * constructor for PickResourceCardMessage
     * @param username username of the player
     * @param cardPick choice of the card to pick
     */
    public PickResourceCardMessage(String username, Integer cardPick) {
        super(username, MessageType.PICK_RESOURCE_CARD);
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
        return "PickResourceCardMessage{" + "nickname=" + getUsername() + ", pick=" + cardPick + '}';
    }
}
