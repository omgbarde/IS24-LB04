package codex.lb04.Message.GameMessage;

import codex.lb04.Message.Message;
import codex.lb04.Message.MessageType;

import java.io.Serial;

public class CreateGameMessage extends Message {
    @Serial
    private static final long serialVersionUID = 10;
    private int numberOfPlayers;
    private int desiredPort;

    public CreateGameMessage(String username, int port, int num) {
        super(username, MessageType.CREATE_GAME);
        this.numberOfPlayers = num;
        this.desiredPort = port;
    }

    @Override
    public String toString() {
        return "CreateGameMessage{" +
                "numberOfPlayers=" + numberOfPlayers +
                ", desiredPort=" + desiredPort +
                '}';
    }

    /**
     * Getter for desiredPort
     * @return desiredPort
     */
    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

}
