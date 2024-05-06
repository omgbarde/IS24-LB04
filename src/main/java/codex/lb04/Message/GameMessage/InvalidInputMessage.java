package codex.lb04.Message.GameMessage;

import codex.lb04.Message.Message;
import codex.lb04.Message.MessageType;

import java.io.Serial;

public class InvalidInputMessage extends Message {
    @Serial
    private static final long serialVersionUID = 14;

    String message;

    public InvalidInputMessage(String username , String message) {
        super(username, MessageType.INVALID_INPUT);
        this.message = message;
    }
    @Override
    public String toString() {
        return "InvalidInputMessage{" +
                "message='" + message + '\'' +
                '}';
    }


}
