package codex.lb04.Message.GameMessage;

import codex.lb04.Message.Message;
import codex.lb04.Message.MessageType;

import java.io.Serial;

public class StartTurnMessage extends Message {
    @Serial
    private static final long serialVersionUID = 21;
    public StartTurnMessage(String usr) {
        super(usr, MessageType.START_TURN);
    }
    @Override
    public String toString() {
        return "StartTurnMessage{" +
                "username=" + super.getUsername() +
                '}';
    }
}
