package codex.lb04.Message.GameMessage;

import codex.lb04.Message.Message;
import codex.lb04.Message.MessageType;

public class StartGameMessage extends Message {

    public StartGameMessage(String username) {
        super(username, MessageType.START_GAME);
    }
}
