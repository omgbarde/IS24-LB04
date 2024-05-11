package codex.lb04.Message;

import java.io.Serial;

/**
 * This class represents a Ping message
 */
public class PingMessage extends Message {
    @Serial
    private static final long serialVersionUID = 33;

    public PingMessage(String s) {
        super(s, MessageType.PING);
    }
}
