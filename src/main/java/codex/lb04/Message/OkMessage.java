package codex.lb04.Message;

import java.io.Serial;

/**
 * message sent as confirmation of received message
 */
public class OkMessage extends Message {
    @Serial
    private static final long serialVersionUID = 8;
    public OkMessage() {
        super("Server", MessageType.OK_MESSAGE);
    }
}
