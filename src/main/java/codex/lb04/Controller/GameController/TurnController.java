package codex.lb04.Controller.GameController;

import codex.lb04.Message.GameMessage.StartTurnMessage;
import codex.lb04.Model.Game;
import codex.lb04.ServerApp;
import codex.lb04.Utils.CircularIterator;

import java.util.ArrayList;


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

    public void resetInstance() {
        instance = null;
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
        ServerApp.sendMessage(new StartTurnMessage(activePlayer), activePlayer);
    }
}
