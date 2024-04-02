package codex.lb04.Message;

public class LoginMessage extends Message{
    private static final long serialVersionUID = 1;
    private String username;

    public LoginMessage(String username) {
        super(username,MessageType.LOGIN_REQUEST);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "LoginMessage{" +
                "username='" + username + '\'' +
                '}';
    }
}
