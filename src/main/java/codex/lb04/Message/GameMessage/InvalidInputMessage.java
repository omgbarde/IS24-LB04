package codex.lb04.Message.GameMessage;

import codex.lb04.Message.Message;
import codex.lb04.Message.MessageType;

import java.io.Serial;

/**
 * Message sent when the client sends an invalid input
 */
public class InvalidInputMessage extends Message {
    @Serial
    private static final long serialVersionUID = -226684372569875122L;

    String message;
    /**
     * Constructor for InvalidInputMessage
     * @param username is usually "server"
     * @param message the message that the server wants to send to the client
     */
    public InvalidInputMessage(String username , String message) {
        super(username, MessageType.INVALID_INPUT);
        this.message = message;
    }
    @Override
    public String toString() {
        return "Invalid input: " +
                 message;
    }


}
