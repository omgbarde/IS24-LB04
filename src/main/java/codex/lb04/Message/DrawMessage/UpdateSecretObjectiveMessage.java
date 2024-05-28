package codex.lb04.Message.DrawMessage;

import codex.lb04.Message.Message;
import codex.lb04.Message.MessageType;
import codex.lb04.Model.ObjectiveCard;

import java.io.Serial;

/**
 * Message to notify the user to update the secret objective card after the choice.
 */
public class UpdateSecretObjectiveMessage extends Message {
    @Serial
    private static final long serialVersionUID = -3185609100175093365L;

    private final ObjectiveCard card;

    /**
     * Constructor for UpdateSecretObjectiveMessage
     *
     * @param secretObjective card cards to update
     */
    public UpdateSecretObjectiveMessage(String username, ObjectiveCard secretObjective) {
        super(username, MessageType.UPDATE_SECRET_OBJECTIVE);
        this.card = secretObjective;
    }

    /**
     * Getter for card cards
     *
     * @return card cards
     */
    public ObjectiveCard getSecretObjective() {
        return card;
    }


}