package codex.lb04.Message;

import java.io.Serial;
import java.util.ArrayList;

/**
 * Message to notify the clients of the players connected to the server
 * used to update the lobby in the view
 */
public class PlayersConnectedMessage extends Message {
    @Serial
    private static final long serialVersionUID = -891983015716649899L;

    ArrayList<String> lobby;

    /**
     * Constructor for PlayersConnectedMessage
     * @param username is usually "server"
     * @param lobby list of players connected
     */
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
