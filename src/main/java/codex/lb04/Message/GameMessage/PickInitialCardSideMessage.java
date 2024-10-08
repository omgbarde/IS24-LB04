package codex.lb04.Message.GameMessage;

import codex.lb04.Message.Message;
import codex.lb04.Message.MessageType;
import codex.lb04.Model.Face;
import codex.lb04.Model.InitialCard;

import java.io.Serial;

/**
 * Message sent when a client picks a side of the initial card
 */
public class PickInitialCardSideMessage extends Message {
    @Serial
    private static final long serialVersionUID = 2636533059607620184L;

    private final Face cardSide;
    private final InitialCard initialCard;

    /**
     * Constructor for PickInitialCardSideMessage
     *
     * @param username username of the player
     */
    public PickInitialCardSideMessage(String username, InitialCard initialCard) {
        super(username, MessageType.PICK_INITIAL_CARD_SIDE);
        this.initialCard = initialCard;
        this.cardSide = initialCard.getShownFace();
    }

    /**
     * Getter for initialCard
     *
     * @return initialCard
     */
    public InitialCard getInitialCard() {
        return initialCard;
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
        return "PickInitialCardSideMessage{" +
                "nickname=" + getUsername() +
                ", pick=" + cardSide +
                '}';
    }
}
