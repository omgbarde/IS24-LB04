package codex.lb04.Controller;

import codex.lb04.Message.DrawMessage.ReadyMessage;
import codex.lb04.Message.GameMessage.CreateGameMessage;
import codex.lb04.Message.LoginMessage;
import codex.lb04.Model.Game;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TurnControllerTest {
    private Game game;
    private TurnController turnController;
    private GameController gameController;

    @Before
    public void setUp() {
        game = Game.getInstance();
        gameController = GameController.getInstance();
        String player1 = "player1";
        String player2 = "player2";
        CreateGameMessage createGameMessage = new CreateGameMessage("player1", 2);
        gameController.onMessageReceived(createGameMessage);
        LoginMessage loginMessage2 = new LoginMessage(player2);
        gameController.onMessageReceived(loginMessage2);
        ReadyMessage start1 = new ReadyMessage(player1);
        ReadyMessage start2 = new ReadyMessage(player2);
        gameController.onMessageReceived(start1);
        gameController.onMessageReceived(start2);
        turnController = TurnController.getInstance();

    }

    @After
    public void tearDown() {
        game.resetInstance();
        game = null;
        turnController.resetInstance();
        turnController = null;
        gameController.resetInstance();
        gameController = null;
    }

    @Test
    public void testGetInstance() {
        TurnController instance = TurnController.getInstance();
        assertNotNull(instance);
    }

    @Test
    public void testSetDrawnCard() {
        turnController.setDrawnCard(true);
        assertTrue(turnController.hasDrawnCard());
    }

    @Test
    public void testSetPlacedCard() {
        turnController.setPlacedCard(true);
        assertTrue(turnController.hasPlacedCard());
    }

    @Test
    public void testChangeTurn() {
        String activePlayerOld = turnController.getActivePlayer();
        turnController.setDrawnCard(true);
        turnController.setPlacedCard(true);
        turnController.changeTurn();
        assertNotEquals(turnController.getActivePlayer(), activePlayerOld);
    }
}
