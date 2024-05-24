package codex.lb04.Message.DrawMessage;

import codex.lb04.Message.Message;
import codex.lb04.Message.MessageType;
import codex.lb04.Model.InitialCard;

import java.io.Serial;

/**
 * Message to notify the user to update the gold cards.
 */
public class UpdateInitialCardDisplayMessage extends Message {
    @Serial
    private static final long serialVersionUID = -5484271686300635977L;

    private final InitialCard card;
    /**
     * Constructor for UpdateInitialCardDisplayMessage
     * @param initialCard card cards to update
     */
    public UpdateInitialCardDisplayMessage(String username , InitialCard initialCard) {
        super(username , MessageType.UPDATE_INITIAL_CARD_DISPLAY);
        this.card = initialCard;
    }
    /**
     * Getter for card cards
     * @return card cards
     */
    public InitialCard getInitialCard() {
        return card;
    }


}