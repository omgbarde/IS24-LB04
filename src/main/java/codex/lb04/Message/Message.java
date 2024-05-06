package codex.lb04.Message;

import java.io.Serial;
import java.io.Serializable;

/**
 * Abstract message class that represents a message that can be sent between server and clients
 */
public abstract class Message implements Serializable {
    @Serial
    private static final long serialVersionUID = 0;

    private final String username;
    private final MessageType messageType;

    protected Message(String username, MessageType messageType) {
        this.username = username;
        this.messageType = messageType;
    }
    /**
     * Getter for username
     * @return username
     */
    public String getUsername() {
        return username;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    @Override
    public String toString() {
        return "Message{" +
                "nickname=" + username +
                ", messageType=" + messageType +
                '}';
    }
}