package codex.lb04.Message;

public class StartTurnMessage extends Message {
    public StartTurnMessage(String usr) {
        super(usr, MessageType.START_TURN);
    }
}
