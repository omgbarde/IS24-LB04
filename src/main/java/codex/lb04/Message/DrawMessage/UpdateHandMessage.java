package codex.lb04.Message.DrawMessage;

import codex.lb04.Message.Message;
import codex.lb04.Message.MessageType;
import codex.lb04.Model.Card;

import java.io.Serial;
import java.util.ArrayList;

/**
 * Message to notify the user to update the gold cards.
 */
public class UpdateHandMessage extends Message {
    @Serial
    private static final long serialVersionUID = -8787726164243701177L;

    private final ArrayList<Card> hand;

    /**
     * Constructor for UpdateGoldMessage
     *
     * @param hand hand cards to update
     */
    public UpdateHandMessage(String username, ArrayList<Card> hand) {
        super(username, MessageType.UPDATE_HAND);
        this.hand = hand;
    }

    /**
     * Getter for hand cards
     *
     * @return hand cards
     */
    public ArrayList<Card> getHand() {
        return hand;
    }


}