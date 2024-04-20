package codex.lb04.Message;

import java.io.Serial;

public class LogoutMessage extends Message{
    @Serial
    private static final long serialVersionUID = 3;

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
