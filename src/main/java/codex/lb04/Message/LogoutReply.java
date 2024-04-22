package codex.lb04.Message;

import java.io.Serial;

public class LogoutReply extends Message{
    @Serial
    private static final long serialVersionUID = 3;

    public LogoutReply(String s) {
        super(s,MessageType.LOGOUT_REPLY);
    }

    @Override
    public String toString() {
        return "LogoutReply{" +
                "username='" + getUsername() + '\'' +
                '}';
    }
}
