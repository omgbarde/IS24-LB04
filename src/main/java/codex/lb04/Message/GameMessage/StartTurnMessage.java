package codex.lb04.Message.GameMessage;

import codex.lb04.Message.Message;
import codex.lb04.Message.MessageType;

import java.io.Serial;
/**
 * Message sent by the server when a client starts his turn
 */
public class StartTurnMessage extends Message {
    @Serial
    private static final long serialVersionUID = 21;
    /**
     * Constructor for StartTurnMessage
     * @param usr username of the client that has started his turn
     */
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
