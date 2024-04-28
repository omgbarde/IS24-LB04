package codex.lb04.Controller;

import codex.lb04.Controller.GameController.GameController;
import codex.lb04.Message.ErrorMessage;
import codex.lb04.Message.GameMessage.StartGameMessage;
import codex.lb04.Message.LoginMessage;
import codex.lb04.Message.MessageType;
import codex.lb04.Model.Enumerations.GameState;
import codex.lb04.Model.Game;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GameControllerTest {
    private GameController gameController;
    private Game game;

    @BeforeEach
    void setUp() {
        this.game = Game.getInstance();
        this.gameController = GameController.getInstance();
    }
    @AfterEach
    void tearDown() {
        this.gameController = null;
        this.game = null;
    }

    @Test
    void testCreateGameController() {
        assertNotNull(GameController.getInstance());
    }
    @Test
    void testOnMessageReceived() {
        game.setGameState(GameState.LOGIN);
        gameController.onMessageReceived(new ErrorMessage("test","Error"));
        assertEquals(GameState.LOGIN, game.getGameState());
        game.setGameState(GameState.INIT);
        gameController.onMessageReceived(new ErrorMessage("test","Error"));
        assertEquals(GameState.INIT, game.getGameState());
        game.setGameState(GameState.IN_GAME);
        gameController.onMessageReceived(new ErrorMessage("test","Error"));
        assertEquals(GameState.IN_GAME, game.getGameState());
        game.setGameState(GameState.LOGIN);
        gameController.onMessageReceived(new LoginMessage("test"));
        assertEquals(GameState.LOGIN, game.getGameState());
        game.setGameState(GameState.LOGIN);
        gameController.onMessageReceived(new StartGameMessage("test", MessageType.START_GAME));
        assertEquals(GameState.IN_GAME, game.getGameState());
    }
    @Test
    void startGame() {
        gameController.startGame();
        assertEquals(GameState.IN_GAME, game.getGameState());
    }
    @Test
    void testGetTurnController() {
        assertNotNull(gameController.getTurnController());
    }


}
