package codex.lb04.Message.GameMessage;

import codex.lb04.Message.Message;
import codex.lb04.Message.MessageType;

import java.io.Serial;

public class WinnersMessage extends Message {
    @Serial
    private static final long serialVersionUID = 22;

    private String winner;

    public WinnersMessage(String username , String winner) {
        super(username, MessageType.WINNERS);
        this.winner = winner;
    }

    /**
     * Getter for winner
     * @return winner
     */
    public String getWinner() {
        return winner;
    }
}
