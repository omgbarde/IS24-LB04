package codex.lb04.Message.DrawMessage;

import codex.lb04.Message.Message;
import codex.lb04.Message.MessageType;
import codex.lb04.Model.ObjectiveCard;

import java.io.Serial;
import java.util.ArrayList;

/**
 * Message to notify the user with the secret objectives to choose.
 */
public class UpdateSecretObjectiveToChooseMessage extends Message {
    @Serial
    private static final long serialVersionUID = -6889126711482360128L;

    private final ArrayList<ObjectiveCard> objectives;

    /**
     * Constructor for UpdateGoldMessage
     *
     * @param objectives objectives cards to update
     */
    public UpdateSecretObjectiveToChooseMessage(String username, ArrayList<ObjectiveCard> objectives) {
        super(username, MessageType.UPDATE_SECRET_OBJECTIVE_TO_CHOOSE);
        this.objectives = objectives;
    }

    /**
     * Getter for objectives cards
     *
     * @return objectives cards
     */
    public ArrayList<ObjectiveCard> getSecretObjectives() {
        return objectives;
    }


}