package codex.lb04.Message;

import java.io.Serial;

/**
 * Message to notify a logout request
 */
public class LogoutMessage extends Message{
    @Serial
    private static final long serialVersionUID = 6;

    public LogoutMessage(String username) {
        super(username,MessageType.LOGOUT_REQUEST);
    }

    @Override
    public String toString() {
        return "LoginMessage{" +
                "username='" + getUsername() + '\'' +
                '}';
    }
}
