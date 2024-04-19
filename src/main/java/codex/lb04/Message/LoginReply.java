package codex.lb04.Message;

/**
 * mesaage sent as reply to login message
 */
public class LoginReply extends Message{
    private static final long serialVersionUID = 4;
    private final boolean accepted;

    public LoginReply(String nickname, boolean accepted) {
        super(nickname, MessageType.LOGIN_REPLY);
        this.accepted = accepted;
    }

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
