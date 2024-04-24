package codex.lb04.Controller.GameController;

import codex.lb04.Model.Game;

import java.util.ArrayList;

public class TurnController {

    private String activePlayer;
    private ArrayList<String> playersQueue;
    private Game game;


    public TurnController(){
        this.game = Game.getInstance();
        this.playersQueue = game.getPlayerNames();
        this.activePlayer = playersQueue.get(0);
    }

    public String getActivePlayer(){
        return activePlayer;
    }
}
