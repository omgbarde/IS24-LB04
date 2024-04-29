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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InputControllerTest {
    private InputController inputController;
    private GameController gameController;
    private Game game;
    private Face front;
    private Face back;
    private InitialCard initialCard;

    @BeforeEach
    void setUp() {
        this.game = Game.getInstance();
        gameController =  GameController.getInstance();
        inputController = new InputController(gameController, game);
        front = new Face(null, null, null, null);
        back = new Face(null, null, null, null, null);
        this.initialCard = new InitialCard(front, back,1);
    }

    @AfterEach
    void tearDown() {
        this.inputController = null;
        this.gameController = null;
        this.game = null;
    }

    @Test
    void testPickSecretObjective() {
        Message message = new PickSecretObjectiveMessage("test", 1);
        assertEquals(true, inputController.pickSecretObjectiveCheck(message));
        message = new PickSecretObjectiveMessage("test", 2);
        assertEquals(false, inputController.pickSecretObjectiveCheck(message));
    }

    @Test
    void testPickResourceCard() {
        Message message = new PickResourceCardMessage("test", 1);
        assertEquals(true, inputController.pickResourceCardCheck(message));
        message = new PickResourceCardMessage("test", 3);
        assertEquals(false, inputController.pickResourceCardCheck(message));
    }

    @Test
    void testPickGoldCard() {
        Message message = new PickGoldCardMessage("test", 1);
        assertEquals(true, inputController.pickGoldCardCheck(message));
        message = new PickGoldCardMessage("test", 3);
        assertEquals(false, inputController.pickGoldCardCheck(message));
    }

    @Test
    void testPickInitialCardSide() {

        Message message = new PickInitialCardSideMessage("test", initialCard);
        assertEquals(false, inputController.pickInitialCardSideCheck(message));
    }
}