package codex.lb04.Controller.GameController;

import codex.lb04.Model.Game;
import codex.lb04.Utils.CircularIterator;

import java.util.ArrayList;
import java.util.Objects;


public class TurnController {

    private String activePlayer;
    private ArrayList<String> lobby;
    private Game game;
    private CircularIterator<String> playersQueueIterator;
    private static TurnController instance;


    public static TurnController getInstance() {
        if (instance == null) {
            instance = new TurnController();
        }
        return instance;
    }

    /**
     * Constructor for the TurnController class with the players list and the active player
     */
    private TurnController() {
        this.game = Game.getInstance();
        this.lobby = game.getLobby();
        this.playersQueueIterator = new CircularIterator<>(lobby);
        this.activePlayer = lobby.getFirst();
    }

    /**
     * getter of the active player
     * @return the active player
     */
    public String getActivePlayer() {
        return activePlayer;
    }

    /**
     * method to set the next player active player and change turn
     */
    public void changeTurn() {
        activePlayer = playersQueueIterator.next();
    }

    /**
     * removes a player from lobby
     * @param username the player to remove
     */
    public void removePlayerFromLobby(String username) {
        for (String player : lobby) {
            if (Objects.equals(player, username)) {
                if (Objects.equals(player, activePlayer)) {
                    changeTurn();
                }
                lobby.remove(player);
            }
        }
    }
}
