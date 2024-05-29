package codex.lb04.Message.GameMessage;

import codex.lb04.Message.Message;
import codex.lb04.Message.MessageType;

import java.io.Serial;

/**
 * Message sent when the game is ended and contains a string with the names of the winners
 */
public class WinnersMessage extends Message {
    @Serial
    private static final long serialVersionUID = 8754824018534389357L;

    private final String winner;

    /**
     * Constructor for WinnersMessage
     *
     * @param username username of the player
     * @param winner   string with the names of the winners
     */
    public WinnersMessage(String username, String winner) {
        super(username, MessageType.WINNERS);
        this.winner = winner;
    }

    /**
     * Getter for winner string
     *
     * @return the winner string already formatted
     */
    public String getWinner() {
        return winner;
    }
}
