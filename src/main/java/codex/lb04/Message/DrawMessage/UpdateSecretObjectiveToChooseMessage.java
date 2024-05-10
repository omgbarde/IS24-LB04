package codex.lb04.Message.DrawMessage;

import codex.lb04.Message.Message;
import codex.lb04.Message.MessageType;
import codex.lb04.Model.Card;
import codex.lb04.Model.GoldCard;
import codex.lb04.Model.ObjectiveCard;

import java.util.ArrayList;

/**
 * Message to notify the user to update the gold cards.
 */
public class UpdateSecretObjectiveToChooseMessage extends Message {

    private ArrayList<ObjectiveCard> objectives;

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