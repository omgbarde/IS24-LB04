package codex.lb04.Controller;

import codex.lb04.Controller.GameController.GameController;
import codex.lb04.Controller.GameController.InputController;
import codex.lb04.Message.GameMessage.*;
import codex.lb04.Message.LoginMessage;
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
    private Game game;
    private Face front;
    private Face back;
    private InitialCard initialCard;

    @Before
    public void setUp() {
        gameController = GameController.getInstance();
        game = Game.getInstance();
        inputController = new InputController(gameController, Game.getInstance());
        String player1 = "player1";
        String player2 = "player2";
        LoginMessage loginMessage1 = new LoginMessage(player1);
        gameController.onMessageReceived(loginMessage1);
        LoginMessage loginMessage2 = new LoginMessage(player2);
        gameController.onMessageReceived(loginMessage2);
        StartGameMessage start = new StartGameMessage(player1);
        gameController.onMessageReceived(start);
        PickInitialCardSideMessage pickInitialCardSideMessage = new PickInitialCardSideMessage(player1,game.getPlayerByName(player1).getBoard().getInitialCard());
        gameController.onMessageReceived(pickInitialCardSideMessage);
        PickInitialCardSideMessage pickInitialCardSideMessage2 = new PickInitialCardSideMessage(player2,game.getPlayerByName(player2).getBoard().getInitialCard());
        gameController.onMessageReceived(pickInitialCardSideMessage2);
    }

    @After
    public void tearDown() {
        this.inputController = null;
        this.gameController = null;
    }

}