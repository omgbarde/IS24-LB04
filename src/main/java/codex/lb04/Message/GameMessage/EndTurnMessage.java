package codex.lb04.Message.GameMessage;

import codex.lb04.Message.Message;
import codex.lb04.Message.MessageType;

import java.io.Serial;

public class EndTurnMessage extends Message {
    @Serial
    private static final long serialVersionUID = 11;
    public EndTurnMessage(String usr) {
        super(usr, MessageType.END_TURN);
    }
    @Override
    public String toString() {
        return "EndTurnMessage{" +
                "username='" + getUsername() + '\'' +
                '}';
    }
}
