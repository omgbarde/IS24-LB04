package codex.lb04.Message.DrawMessage;

import codex.lb04.Message.Message;
import codex.lb04.Message.MessageType;

import java.io.Serial;
/**
 * Message to notify the server that the user is ready.
 */
public class ReadyMessage extends Message {

    @Serial
    private static final long serialVersionUID = -4911231621102811512L;

    /** Constructor for ReadyMessage
     * @param username username of the player, usually set as "ready"
     */
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
