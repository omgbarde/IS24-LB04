package codex.lb04.Controller.GameController;

import codex.lb04.Controller.GameControllerInterface;

public class PlayerController implements GameControllerInterface {
    public static void addPlayer(String name){
        game.addPlayerName(name);
    }
    public static void removePlayer(String name){
        game.removePlayerName(name);
    }
}
