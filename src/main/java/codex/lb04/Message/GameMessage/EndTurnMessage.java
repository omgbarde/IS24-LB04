package codex.lb04.Message.GameMessage;

import codex.lb04.Message.Message;
import codex.lb04.Message.MessageType;

public class EndTurnMessage extends Message {
    public EndTurnMessage(String usr) {
        super(usr, MessageType.END_TURN);
    }
}
