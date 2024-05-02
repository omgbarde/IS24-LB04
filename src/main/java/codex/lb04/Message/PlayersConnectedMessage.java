package codex.lb04.Message;

import java.util.ArrayList;

public class PlayersConnectedMessage extends Message {
    ArrayList<String> lobby;
    public PlayersConnectedMessage(String username, ArrayList<String> lobby) {
        super(username, MessageType.PLAYERS_CONNECTED);
        this.lobby = lobby;
    }

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
