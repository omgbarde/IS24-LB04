package codex.lb04.Message;

import java.io.Serial;

/**
 * message that represents a login request from a client
 */
public class LoginMessage extends Message{
    @Serial
    private static final long serialVersionUID = 4;

    public LoginMessage(String username) {
        super(username,MessageType.LOGIN_REQUEST);
    }

    @Override
    public String toString() {
        return "LoginMessage{" +
                "username='" + getUsername() + '\'' +
                '}';
    }
}
