package codex.lb04.Message.DrawMessage;

import codex.lb04.Message.Message;
import codex.lb04.Message.MessageType;
import codex.lb04.Model.ObjectiveCard;

import java.io.Serial;
import java.util.ArrayList;
/**
 * Message to notify the user to update the gold cards.
 */
public class UpdateCommonObjectivesMessage extends Message {
    @Serial
    private static final long serialVersionUID = 2212552467992985655L;

    private final ArrayList<ObjectiveCard> objective;
    /**
     * Constructor for UpdateCommonObjectivesMessage
     * @param objectiveCards objective cards to update
     */
    public UpdateCommonObjectivesMessage(ArrayList<ObjectiveCard> objectiveCards) {
        super("server" , MessageType.UPDATE_COMMON_OBJECTIVES);
        this.objective = objectiveCards;
    }
    /**
     * Getter for objective cards
     * @return objective cards
     */
    public ArrayList<ObjectiveCard> getCommonObjectives() {
        return objective;
    }


}
