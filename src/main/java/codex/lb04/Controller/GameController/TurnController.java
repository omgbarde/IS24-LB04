package codex.lb04.Controller.GameController;

import codex.lb04.Model.Game;

import java.util.ArrayList;

public class TurnController {

    private String activePlayer;
    private ArrayList<String> playersQueue;
    private Game game;

    /**
     * Constructor for the TurnController class with the players list and the active player
     */
    public TurnController() {
        this.game = Game.getInstance();
        this.playersQueue = game.getPlayerNames();
        this.activePlayer = playersQueue.getFirst();
    }

    /**
     * getter of the active player
     * @return the active player
     */
    public String getActivePlayer() {
        return activePlayer;
    }


}
