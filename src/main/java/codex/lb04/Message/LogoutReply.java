package codex.lb04.Message;

import java.io.Serial;
/**
 * Message to notify a logout reply
 */
public class LogoutReply extends Message{
    @Serial
    private static final long serialVersionUID = 7;
    private final boolean confirmed;

    public LogoutReply(String s, boolean confirmed) {
        super(s,MessageType.LOGOUT_REPLY);
        this.confirmed = confirmed;
    }
    /**
     * Getter for accepted
     * @return accepted
     */
    public boolean isAccepted() {
        return confirmed;
    }

    @Override
    public String toString() {
        return "LogoutReply{" +
                "nickname=" + getUsername() +
                ", accepted=" + confirmed +
                '}';
    }
}
