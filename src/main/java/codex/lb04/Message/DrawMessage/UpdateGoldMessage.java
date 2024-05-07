package codex.lb04.Message.DrawMessage;

import codex.lb04.Message.Message;
import codex.lb04.Message.MessageType;
import codex.lb04.Model.GoldCard;
import codex.lb04.Model.GoldCard;

import java.util.ArrayList;

public class UpdateGoldMessage extends Message {

    private ArrayList<GoldCard> gold;

    public UpdateGoldMessage(ArrayList<GoldCard> goldCards) {
        super("" , MessageType.UPDATE_GOLD);
        this.gold = gold;
    }

    public ArrayList<GoldCard> getGold() {
        return gold;
    }


}
