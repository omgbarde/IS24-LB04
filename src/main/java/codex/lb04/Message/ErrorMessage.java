package codex.lb04.Message;

/**
 * Message to notify an error to the user.
 */
public class ErrorMessage extends Message {

    private static final long serialVersionUID = 2;

    private final String error;

    public ErrorMessage(String username, String error) {
        super(username, MessageType.ERROR);
        this.error = error;
    }

    public String getError() {
        return error;
    }

    @Override
    public String toString() {
        return "ErrorMessage{" +
                "nickname=" + getUsername() +
                ", error=" + error +
                '}';
    }
}
