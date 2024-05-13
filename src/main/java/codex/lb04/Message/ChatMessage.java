package codex.lb04.Message;

public class ChatMessage extends Message {
    private String text;
    public ChatMessage(String username, String text) {
        super(username, MessageType.CHAT_MESSAGE);
        this.text = text;
    }

    @Override
    public String toString() {
        return getUsername() + ": " + text + "\n";
    }
}
