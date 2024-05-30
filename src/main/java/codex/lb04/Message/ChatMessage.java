package codex.lb04.Message;

import java.io.Serial;

/**
 * Message sent between clients to chat (broadcast)
 */
public class ChatMessage extends Message {
    @Serial
    private static final long serialVersionUID = 39106417832738172L;

    private final String text;

    /**
     * Constructor for ChatMessage
     *
     * @param username is the username of the client that sent the message
     * @param text     is the message
     */
    public ChatMessage(String username, String text) {
        super(username, MessageType.CHAT_MESSAGE);
        this.text = text;
    }

    /**
     * custom toString method to directly print the message
     *
     * @return the message with the username of the sender
     */
    @Override
    public String toString() {
        return getUsername() + ": " + text + "\n";
    }
}
