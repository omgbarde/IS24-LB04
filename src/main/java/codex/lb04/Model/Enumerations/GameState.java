package codex.lb04.Model.Enumerations;

/**
 * This enum lists all the game states
 */
public enum GameState {
    LOGIN,              //when the game is accepting connections
    INIT,               //when the game is instantiating the controllers, players and relative boards
    IN_GAME,            //when the game is running and players are alternating their turns
    END_GAME,           //when a player reaches 20 points and the last turns are made for all players
    ENDED               //when the game is over and the winners are declared
}
