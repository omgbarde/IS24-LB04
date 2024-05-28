package codex.lb04.Message;

import java.io.Serial;

/**
 * Message exchange generic information, that does not need to be handled automatically.
 */
public class GenericMessage extends Message {
    @Serial
    private static final long serialVersionUID = -4779509558069870609L;
    private final String msg;

    /**
     * Constructor for GenericMessage
     *
     * @param username is the nickname of the user sending the message
     * @param s        is the message
     */
    public GenericMessage(String username, String s) {
        super(username, MessageType.GENERIC_MESSAGE);
        this.msg = s;
    }

    @Override
    public String toString() {
        return msg;
    }
}
