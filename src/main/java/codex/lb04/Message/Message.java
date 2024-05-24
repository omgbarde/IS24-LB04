package codex.lb04.Message;

import java.io.Serial;
import java.io.Serializable;

/**
 * Abstract message class that represents a message that can be sent between server and clients
 */
public abstract class Message implements Serializable {
    @Serial
    private static final long serialVersionUID = -7732141462227877149L;
    private final String username;
    private final MessageType messageType;
    /**
     * Constructor for Message
     * @param username username of the client that sent the message
     * @param messageType type of the message
     */
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

    /**
     * Getter for messageType
     * @return messageType
     */
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