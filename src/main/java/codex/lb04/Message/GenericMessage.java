package codex.lb04.Message;

import java.io.Serial;

public class GenericMessage extends Message {
    @Serial
    private static final long serialVersionUID = 7;
    private String msg;

    public GenericMessage(String username, String s) {
        super(username, MessageType.GENERIC_MESSAGE);
        this.msg = s;
    }

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
