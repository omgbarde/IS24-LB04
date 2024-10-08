package codex.lb04.Message;

import java.io.Serial;

/**
 * This class represents a Ping message
 */
public class PingMessage extends Message {
    @Serial
    private static final long serialVersionUID = -7633821512465690662L;

    /**
     * Constructor for PingMessage
     *
     * @param s is the message
     */
    public PingMessage(String s) {
        super(s, MessageType.PING);
    }
}
