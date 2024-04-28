package codex.lb04.Message;

public class DeadClientMessage extends Message {
    public DeadClientMessage(String username) {
        super(username, MessageType.DEAD_CLIENT);
    }
}
