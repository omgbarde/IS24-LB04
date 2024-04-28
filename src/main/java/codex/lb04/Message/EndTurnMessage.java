package codex.lb04.Message;

public class EndTurnMessage extends Message {
    public EndTurnMessage(String usr) {
        super(usr, MessageType.END_TURN);
    }
}
