package codex.lb04.Message;

import java.io.Serial;
/**
 * Message exchange generic information, that does not need to be handled automatically.
 */
public class GenericMessage extends Message {
    @Serial
    private static final long serialVersionUID = 3;
    private String msg;

    public GenericMessage(String username, String s) {
        super(username, MessageType.GENERIC_MESSAGE);
        this.msg = s;
    }
    /**
     * Getter for message
     * @return message
     */
    public String getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return "Message{" +
                "nickname=" + getUsername() +
                ", message=" + msg +
                '}';
    }
}
