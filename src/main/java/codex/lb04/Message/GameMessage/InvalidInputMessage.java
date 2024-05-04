package codex.lb04.Message.GameMessage;

import codex.lb04.Message.Message;
import codex.lb04.Message.MessageType;

public class InvalidInputMessage extends Message {

    String message;

    public InvalidInputMessage(String username , String message) {
        super(username, MessageType.INVALID_INPUT);
        this.message = message;
    }


}
