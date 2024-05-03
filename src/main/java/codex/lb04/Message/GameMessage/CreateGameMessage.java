package codex.lb04.Message.GameMessage;

import codex.lb04.Message.Message;
import codex.lb04.Message.MessageType;

public class CreateGameMessage extends Message {
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

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }
    public int getDesiredPort() {
        return desiredPort;
    }

}
