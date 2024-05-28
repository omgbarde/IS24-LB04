package codex.lb04.Controller;

import codex.lb04.Message.DrawMessage.ReadyMessage;
import codex.lb04.Message.GameMessage.*;
import codex.lb04.Message.GenericMessage;
import codex.lb04.Message.LoginMessage;
import codex.lb04.Model.Card;
import codex.lb04.Model.Game;
import codex.lb04.Model.GoldCard;
import codex.lb04.Model.InitialCard;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class InputControllerTest {
    private InputController inputController;
    private GameController gameController;
    private Game game;

    @Before
    public void setUp() {
        gameController = GameController.getInstance();
        game = Game.getInstance();
        inputController = new InputController(gameController, Game.getInstance());
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
    }

    @After
    public void tearDown() {
        this.inputController = null;
        this.gameController = null;
    }

    @Test
    public void testPickSecretObjective() {
        PickSecretObjectiveMessage correct = new PickSecretObjectiveMessage("player1", 1);
        assertTrue(inputController.verifyReceivedData(correct));
        PickSecretObjectiveMessage incorrect = new PickSecretObjectiveMessage("player1", 3);
        assertFalse(inputController.verifyReceivedData(incorrect));

    }

    @Test
    public void testPickResourceCard() {
        PickResourceCardMessage correct = new PickResourceCardMessage("player1", 1);
        assertTrue(inputController.verifyReceivedData(correct));
        PickResourceCardMessage incorrect = new PickResourceCardMessage("player1", 3);
        assertFalse(inputController.verifyReceivedData(incorrect));
    }

    @Test
    public void testPickGoldCard() {
        PickGoldCardMessage correct = new PickGoldCardMessage("player1", 1);
        assertTrue(inputController.verifyReceivedData(correct));
        PickGoldCardMessage incorrect = new PickGoldCardMessage("player1", 3);
        assertFalse(inputController.verifyReceivedData(incorrect));
    }

    @Test
    public void testPickInitialCardSide() {
        InitialCard dummy = new InitialCard(null, null, 0);
        PickInitialCardSideMessage correct = new PickInitialCardSideMessage("player1", game.getPlayerByName("player1").getBoard().getInitialCard());
        assertTrue(inputController.verifyReceivedData(correct));
        PickInitialCardSideMessage incorrect = new PickInitialCardSideMessage("player2", dummy);
        assertFalse(inputController.verifyReceivedData(incorrect));
    }

    @Test
    public void testPlaceCard() {
        InitialCard player1InitCard = game.getPlayerByName("player1").getBoard().getInitialCard();
        PlaceCardMessage correct = new PlaceCardMessage("player1", 0, 0, player1InitCard);
        assertTrue(inputController.verifyReceivedData(correct));
        GoldCard randomGold = new GoldCard(null, null, null, 0, 3, 0, 0, 0, 1);
        PlaceCardMessage incorrect = new PlaceCardMessage("player2", 2, 2, randomGold);
        assertFalse(inputController.verifyReceivedData(incorrect));
    }

    @Test
    public void testFlipCard() {
        Card handCard = game.getPlayerByName("player1").getBoard().getHand().getFirst();
        FlipCardMessage correct = new FlipCardMessage("player1", handCard);
        assertTrue(inputController.verifyReceivedData(correct));
        GoldCard randomGold = new GoldCard(null, null, null, 0, 3, 0, 0, 0, 1);
        FlipCardMessage incorrect = new FlipCardMessage("player1", randomGold);
        assertFalse(inputController.verifyReceivedData(incorrect));

    }

    @Test
    public void testCheckUser() {
        GenericMessage dummy = new GenericMessage("player1", "I'm the active player :)");
        assertTrue(inputController.checkUser(dummy));
        GenericMessage dummy2 = new GenericMessage("player2", "I'm not the active player :(");
        assertFalse(inputController.checkUser(dummy2));
    }


}