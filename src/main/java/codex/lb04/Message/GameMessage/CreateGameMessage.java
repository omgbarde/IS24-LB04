package codex.lb04.Message.GameMessage;

import codex.lb04.Message.Message;
import codex.lb04.Message.MessageType;

import java.io.Serial;

/**
 * Message sent by a client to create a new game
 */
public class CreateGameMessage extends Message {
    @Serial
    private static final long serialVersionUID = -2584692589852687425L;
    private final int numberOfPlayers;

    /**
     * Constructor for CreateGameMessage
     * @param username username of the client that wants to create a game
     * @param port port the client wants to use
     * @param num number of players in the game
     */
    public CreateGameMessage(String username, int num) {
        super(username, MessageType.CREATE_GAME);
        this.numberOfPlayers = num;
    }

    @Override
    public String toString() {
        return "CreateGameMessage{" +
                "numberOfPlayers=" + numberOfPlayers +
                '}';
    }

    /**
     * getter for number of players
     * @return number of players
     **/
    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

}
