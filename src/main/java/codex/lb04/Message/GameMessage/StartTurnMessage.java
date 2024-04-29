package codex.lb04.Message.GameMessage;

import codex.lb04.Message.Message;
import codex.lb04.Message.MessageType;

public class StartTurnMessage extends Message {
    public StartTurnMessage(String usr) {
        super(usr, MessageType.START_TURN);
    }
}
