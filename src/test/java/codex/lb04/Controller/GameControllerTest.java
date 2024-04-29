package codex.lb04.Controller;

import codex.lb04.Controller.GameController.GameController;
import codex.lb04.Message.ErrorMessage;
import codex.lb04.Message.GameMessage.StartGameMessage;
import codex.lb04.Message.LoginMessage;
import codex.lb04.Message.MessageType;
import codex.lb04.Model.Enumerations.GameState;
import codex.lb04.Model.Game;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


public class GameControllerTest {
    private GameController gameController;
    private Game game;

    @Before
    public void setUp() {
        this.game = Game.getInstance();
        this.gameController = GameController.getInstance();
    }
    @After
    public void tearDown() {
        this.gameController = null;
        this.game = null;
    }

    @Test
    public void testCreateGameController() {
        assertNotNull(GameController.getInstance());
    }

    //TODO doesn't work when starting tests with maven (to debug)
    @Test
    public void testOnMessageReceived() {
        game.setGameState(GameState.LOGIN);
        gameController.onMessageReceived(new ErrorMessage("test","Error"));
        assertEquals(GameState.LOGIN, game.getGameState());
        game.setGameState(GameState.INIT);
        gameController.onMessageReceived(new ErrorMessage("test","Error"));
        assertEquals(GameState.INIT, game.getGameState());

        game.setGameState(GameState.LOGIN);
        gameController.onMessageReceived(new LoginMessage("test"));
        assertEquals(GameState.LOGIN, game.getGameState());

        game.setGameState(GameState.LOGIN);
        game.addPlayerName("test");
        game.addPlayerName("test2");
        gameController.onMessageReceived(new StartGameMessage("test", MessageType.START_GAME));
        assertEquals(GameState.IN_GAME, game.getGameState());

        game.setGameState(GameState.IN_GAME);
        gameController.onMessageReceived(new ErrorMessage("test","Error"));
        assertEquals(GameState.IN_GAME, game.getGameState());
    }
    @Test
    public void startGame() {
        game.setGameState(GameState.LOGIN);
        game.addPlayerName("test");
        gameController.startGame();
        assertEquals(GameState.IN_GAME, game.getGameState());
    }
    @Test
    public void testGetTurnController() {
        game.setGameState(GameState.LOGIN);
        game.addPlayerName("test");
        gameController.startGame();
        assertNotNull(gameController.getTurnController());
    }


}
