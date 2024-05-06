package codex.lb04.Message;

import java.io.Serial;

/**
 * Message sent when a client dies unexpectedly
 */
public class DeadClientMessage extends Message {
    @Serial
    private static final long serialVersionUID = 1;
    public DeadClientMessage(String username) {
        super(username, MessageType.DEAD_CLIENT);
    }
}
