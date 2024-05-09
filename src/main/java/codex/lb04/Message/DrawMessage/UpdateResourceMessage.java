package codex.lb04.Message.DrawMessage;

import codex.lb04.Message.Message;
import codex.lb04.Message.MessageType;
import codex.lb04.Model.GoldCard;
import codex.lb04.Model.ResourceCard;

import java.util.ArrayList;
/**
 * Message to notify the user to update the gold cards.
 */
public class UpdateResourceMessage extends Message {

    private ArrayList<ResourceCard> resource;
    /**
     * Constructor for UpdateGoldMessage
     * @param resourceCards resource cards to update
     */
    public UpdateResourceMessage(ArrayList<ResourceCard> resourceCards) {
        super("" , MessageType.UPDATE_RESOURCE);
        this.resource = resourceCards;
    }
    /**
     * Getter for resource cards
     * @return resource cards
     */
    public ArrayList<ResourceCard> getResource() {
        return resource;
    }


}
