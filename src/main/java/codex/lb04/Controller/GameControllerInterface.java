package codex.lb04.Controller;

import codex.lb04.Model.Game;

/**
 * GameController interface each controller that extends this interface will have access to the game instance
 * and will control different aspects of the game
 */
public interface GameControllerInterface {
     Game game = Game.getInstance();
}
