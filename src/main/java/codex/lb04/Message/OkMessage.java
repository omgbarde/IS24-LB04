package codex.lb04.Message;

/**
 * message sent as confirmation of received message
 */
public class OkMessage extends Message {
    public OkMessage() {
        super("Server", MessageType.OK_MESSAGE);
    }
}
