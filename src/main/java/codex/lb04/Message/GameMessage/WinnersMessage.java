package codex.lb04.Message.GameMessage;

import codex.lb04.Message.Message;
import codex.lb04.Message.MessageType;

public class WinnersMessage extends Message {

    private String winner;

    public WinnersMessage(String username , String winner) {
        super(username, MessageType.START_GAME);
        this.winner = winner;
    }

    public String getUsername() {
        return super.getUsername();
    }

    public String getWinner() {
        return winner;
    }
}
