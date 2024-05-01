package codex.lb04.Controller;

import codex.lb04.Controller.GameController.GameController;
import codex.lb04.Message.ErrorMessage;
import codex.lb04.Message.GameMessage.PickInitialCardSideMessage;
import codex.lb04.Message.GameMessage.PlaceCardMessage;
import codex.lb04.Message.GameMessage.StartGameMessage;
import codex.lb04.Message.LoginMessage;
import codex.lb04.Model.Card;
import codex.lb04.Model.Enumerations.Color;
import codex.lb04.Model.Enumerations.GameState;
import codex.lb04.Model.Face;
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
        this.gameController.resetInstance();
    }



    @Test
    public void testCreateGameController() {
        assertNotNull(GameController.getInstance());
    }


    @Test
    public void testOnMessageReceived() {
        gameController.onMessageReceived(new ErrorMessage("test", "Error"));
        assertEquals(GameState.LOGIN, game.getGameState());

        game.setGameState(GameState.INIT);
        gameController.onMessageReceived(new ErrorMessage("test", "Error"));
        assertEquals(GameState.INIT, game.getGameState());


        game.setGameState(GameState.LOGIN);
        gameController.onMessageReceived(new LoginMessage("test"));
        assertEquals(GameState.LOGIN, game.getGameState());

        game.setGameState(GameState.LOGIN);
        game.addPlayerToLobby("test");
        game.addPlayerToLobby("test2");
        gameController.onMessageReceived(new StartGameMessage("test"));
        assertEquals(GameState.IN_GAME, game.getGameState());

        game.setGameState(GameState.IN_GAME);
        gameController.onMessageReceived(new ErrorMessage("test", "Error"));
        assertEquals(GameState.IN_GAME, game.getGameState());
    }

    @Test
    public void startGame() {
        game.setGameState(GameState.LOGIN);
        game.addPlayerToLobby("test");
        game.addPlayerToLobby("test2");
        gameController.startGame();
        assertEquals(GameState.IN_GAME, game.getGameState());
    }

    @Test
    public void testGetTurnController() {
        game.setGameState(GameState.LOGIN);
        game.addPlayerToLobby("test");
        game.addPlayerToLobby("test2");
        gameController.startGame();
        assertNotNull(gameController.getTurnController());
    }

    @Test//TODO
    public void simulationGame(){
        String player1 = "Pitesse";
        String player2 = "Barde";
        String player3 = "AlexIlLeone2";
        String player4 = "Brio";
        String player5 = "Rafa Leao";
        Face randomFace = new Face(null,null,null,null);



        LoginMessage login1 = new LoginMessage(player1);
        gameController.onMessageReceived(login1);

        LoginMessage login2 = new LoginMessage(player2);
        gameController.onMessageReceived(login2);

        LoginMessage login3 = new LoginMessage(player3);
        gameController.onMessageReceived(login3);

        LoginMessage login4 = new LoginMessage(player4);
        gameController.onMessageReceived(login4);

        StartGameMessage start = new StartGameMessage(player1);
        gameController.onMessageReceived(start);

        Game game = Game.getInstance();

        String activePlayer = gameController.getTurnController().getActivePlayer();
        PickInitialCardSideMessage pick1 = new PickInitialCardSideMessage(activePlayer , game.getPlayerByName(activePlayer).getBoard().getInitialCard());
        gameController.onMessageReceived(pick1);
        assertEquals(pick1.getInitialCard() , game.getPlayerByName(activePlayer).getBoard().getCard(0,0));

        PickInitialCardSideMessage pick2 = new PickInitialCardSideMessage(player2 , game.getPlayerByName(player2).getBoard().getInitialCard());
        gameController.onMessageReceived(pick2);
        assertNotEquals(pick2.getInitialCard() , game.getPlayerByName(player2).getBoard().getCard(0,0));


        Card noSense = new Card(Color.BLUE,randomFace,randomFace);
        PlaceCardMessage external = new PlaceCardMessage(player5 , 0 , 0 , noSense);
        gameController.onMessageReceived(external);
        assertNotEquals(noSense , game.getPlayerByName(activePlayer).getBoard().getCard(0,0));

        Card toPlace = game.getPlayerByName(activePlayer).getBoard().getHand().get(1);
        PlaceCardMessage correct = new PlaceCardMessage(activePlayer , 1 , 1 , toPlace);
        gameController.onMessageReceived(correct);
        assertEquals(toPlace , game.getPlayerByName(activePlayer).getBoard().getCard(1,1));


        System.out.println("zao");

    }


}
