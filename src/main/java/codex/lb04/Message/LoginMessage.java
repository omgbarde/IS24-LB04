package codex.lb04.Message;

/**
 * message that represents a login request from a client
 */
public class LoginMessage extends Message{
    private static final long serialVersionUID = 1;

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
