package codex.lb04.Message;

import java.io.Serial;

/**
 * Message to notify an error to the user.
 */
public class ErrorMessage extends Message {
    @Serial
    private static final long serialVersionUID = 6018127259917713548L;

    private final String error;

    /**
     * Constructor for ErrorMessage
     * @param username is typically "server"
     * @param error a brief description of the error
     */
    public ErrorMessage(String username, String error) {
        super(username, MessageType.ERROR);
        this.error = error;
    }
    /**
     * Getter for error description
     * @return error string
     */
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
