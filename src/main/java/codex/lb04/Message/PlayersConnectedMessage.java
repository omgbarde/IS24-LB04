package codex.lb04.Message;

import java.io.Serial;
import java.util.ArrayList;

public class PlayersConnectedMessage extends Message {
    @Serial
    private static final long serialVersionUID = 9;
    ArrayList<String> lobby;
    public PlayersConnectedMessage(String username, ArrayList<String> lobby) {
        super(username, MessageType.PLAYERS_CONNECTED);
        this.lobby = lobby;
    }

    /**
     * Getter for lobby
     * @return lobby
     */
    public ArrayList<String> getLobby() {
        return lobby;
    }

    @Override
    public String toString() {
        return "PlayersConnectedMessage{" +
                "lobby=" + lobby +
                '}';
    }
}
