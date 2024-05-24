package codex.lb04.Message;

import java.io.Serial;

/**
 * This class represents a Pong message
 */
public class PongMessage extends Message {
    @Serial
    private static final long serialVersionUID = 995598000317838624L;

    public PongMessage(String s) {
        super(s,MessageType.PONG);
    }
}
