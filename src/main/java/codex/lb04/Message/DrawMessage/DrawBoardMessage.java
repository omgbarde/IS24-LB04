package codex.lb04.Message.DrawMessage;

import codex.lb04.Message.Message;
import codex.lb04.Message.MessageType;

import java.io.Serial;
/**
 * Message to notify the user to draw the board.
 */
public class DrawBoardMessage extends Message {
    @Serial
    private static final long serialVersionUID = 20;
    /**
     * Constructor for DrawBoardMessage
     * @param username username of the player
     */
    public DrawBoardMessage(String username) {
        super(username, MessageType.DRAW_BOARD);
    }
    @Override
    public String toString() {
        return "StartGameMessage{" +
                "username=" + super.getUsername() +
                '}';
    }
}
