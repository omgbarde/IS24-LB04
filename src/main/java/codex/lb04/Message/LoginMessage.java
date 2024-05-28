package codex.lb04.Message;

import java.io.Serial;

/**
 * message that represents a login request from a client
 */
public class LoginMessage extends Message {
    @Serial
    private static final long serialVersionUID = 7477711891519850477L;

    /**
     * Constructor for LoginMessage
     *
     * @param username username of the client that wants to log in
     */
    public LoginMessage(String username) {
        super(username, MessageType.LOGIN_REQUEST);
    }

    @Override
    public String toString() {
        return "LoginMessage{" +
                "username='" + getUsername() + '\'' +
                '}';
    }
}
