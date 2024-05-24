package codex.lb04.Message.GameMessage;

import codex.lb04.Message.Message;
import codex.lb04.Message.MessageType;

import java.io.Serial;
/**
 * Message sent by  the server when a client ends his turn
 */
public class EndTurnMessage extends Message {
    @Serial
    private static final long serialVersionUID = 3046586501663263702L;
    /**
     * Constructor for EndTurnMessage
     * @param usr username of the client that has ended his turn
     */
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
