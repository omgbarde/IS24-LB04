package codex.lb04.Controller;

import codex.lb04.Controller.GameController.GameController;
import codex.lb04.Controller.GameController.InputController;
import codex.lb04.Message.GameMessage.PickGoldCardMessage;
import codex.lb04.Message.GameMessage.PickInitialCardSideMessage;
import codex.lb04.Message.GameMessage.PickResourceCardMessage;
import codex.lb04.Message.GameMessage.PickSecretObjectiveMessage;
import codex.lb04.Message.Message;
import codex.lb04.Model.Face;
import codex.lb04.Model.Game;
import codex.lb04.Model.InitialCard;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class InputControllerTest {
    private InputController inputController;
    private GameController gameController;
    private Face front;
    private Face back;
    private InitialCard initialCard;

    @Before
    public void setUp() {
        gameController = GameController.getInstance();
        inputController = new InputController(gameController, Game.getInstance());
        front = new Face(null, null, null, null);
        back = new Face(null, null, null, null, null);
        this.initialCard = new InitialCard(front, back, 1);
    }

    @After
    public void tearDown() {
        this.inputController = null;
        this.gameController = null;
    }

    @Test
    public void testPickSecretObjective() {
        Message message = new PickSecretObjectiveMessage("test", 1);
        assertTrue(inputController.pickSecretObjectiveCheck(message));
        message = new PickSecretObjectiveMessage("test", 2);
        assertFalse(inputController.pickSecretObjectiveCheck(message));
    }

    @Test
    public void testPickResourceCard() {
        Message message = new PickResourceCardMessage("test", 1);
        assertTrue(inputController.pickResourceCardCheck(message));
        message = new PickResourceCardMessage("test", 3);
        assertFalse(inputController.pickResourceCardCheck(message));
    }

    @Test
    public void testPickGoldCard() {
        Message message = new PickGoldCardMessage("test", 1);
        assertTrue(inputController.pickGoldCardCheck(message));
        message = new PickGoldCardMessage("test", 3);
        assertFalse(inputController.pickGoldCardCheck(message));
    }

    @Test
    public void testPickInitialCardSide() {

        Message message = new PickInitialCardSideMessage("test", initialCard);
        assertFalse(inputController.pickInitialCardSideCheck(message));
    }
}