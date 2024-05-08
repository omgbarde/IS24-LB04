package codex.lb04.Message;

import java.io.Serial;

/**
 * message sent as reply to LoginMessage
 */
public class LoginReply extends Message{
    @Serial
    private static final long serialVersionUID = 5;
    private final boolean accepted;

    /**
     * Constructor for LoginReply
     * @param nickname nickname of the client
     * @param accepted true if the login was accepted, false otherwise
     */
    public LoginReply(String nickname, boolean accepted) {
        super(nickname, MessageType.LOGIN_REPLY);
        this.accepted = accepted;
    }

    /**
     * Getter for accepted
     * @return accepted
     */
    public boolean isAccepted() {
        return accepted;
    }

    @Override
    public String toString() {
        return "LoginReply{" +
                "nickname=" + getUsername() +
                ", accepted=" + accepted +
                '}';
    }
}
