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

    /**
     * method to set the next player active player and change turn
     */
    public void changeTurn() {
        activePlayer = playersQueueIterator.next();
    }

    public void removePlayer(String username){
        for (String player : playersQueue){
            if(player == username){
                if (player == activePlayer){
                    changeTurn();
                }
                playersQueue.remove(player);
            }
        }
    }




}
