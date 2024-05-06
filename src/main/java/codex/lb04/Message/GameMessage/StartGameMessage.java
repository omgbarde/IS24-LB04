package codex.lb04.Message.GameMessage;

import codex.lb04.Message.Message;
import codex.lb04.Message.MessageType;

import java.io.Serial;

public class StartGameMessage extends Message {
    @Serial
    private static final long serialVersionUID = 20;

    public StartGameMessage(String username) {
        super(username, MessageType.START_GAME);
    }
    @Override
    public String toString() {
        return "StartGameMessage{" +
                "username=" + super.getUsername() +
                '}';
    }
}
