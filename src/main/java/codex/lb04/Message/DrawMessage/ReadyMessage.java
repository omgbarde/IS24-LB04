package codex.lb04.Message.DrawMessage;

import codex.lb04.Message.Message;
import codex.lb04.Message.MessageType;

import java.io.Serial;

public class ReadyMessage extends Message {

    @Serial
    private static final long serialVersionUID = 20;

    public ReadyMessage(String username) {
        super(username, MessageType.READY);
    }

    @Override
    public String toString() {
        return "StartGameMessage{" +
                "username=" + super.getUsername() +
                '}';
    }
}
