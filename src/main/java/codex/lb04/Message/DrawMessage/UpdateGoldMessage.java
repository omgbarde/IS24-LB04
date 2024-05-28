package codex.lb04.Message.DrawMessage;

import codex.lb04.Message.Message;
import codex.lb04.Message.MessageType;
import codex.lb04.Model.GoldCard;

import java.io.Serial;
import java.util.ArrayList;

/**
 * Message to notify the user to update the gold cards.
 */
public class UpdateGoldMessage extends Message {
    @Serial
    private static final long serialVersionUID = -3141819156133549832L;

    private final ArrayList<GoldCard> gold;

    /**
     * Constructor for UpdateGoldMessage
     *
     * @param goldCards gold cards to update
     */
    public UpdateGoldMessage(ArrayList<GoldCard> goldCards) {
        super("server", MessageType.UPDATE_GOLD);
        this.gold = goldCards;
    }

    /**
     * Getter for gold cards
     *
     * @return gold cards
     */
    public ArrayList<GoldCard> getGold() {
        return gold;
    }


}
