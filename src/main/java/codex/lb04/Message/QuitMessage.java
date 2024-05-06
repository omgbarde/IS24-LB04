package codex.lb04.Message;

import java.io.Serial;

public class QuitMessage extends Message{
    @Serial
    private static final long serialVersionUID = 24;
    private final String message;
    public QuitMessage(String server, String s) {
        super(server, MessageType.QUIT);
        this.message = s;
    }
    @Override
    public String toString() {
        return "QuitMessage{" +
                "message='" + message + '\'' +
                '}';
    }
}
