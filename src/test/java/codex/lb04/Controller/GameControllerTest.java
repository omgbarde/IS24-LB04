package codex.lb04.Controller;

import codex.lb04.Message.DrawMessage.ReadyMessage;
import codex.lb04.Message.ErrorMessage;
import codex.lb04.Message.GameMessage.*;
import codex.lb04.Message.LoginMessage;
import codex.lb04.Model.Card;
import codex.lb04.Model.Enumerations.Color;
import codex.lb04.Model.Enumerations.GameState;
import codex.lb04.Model.Face;
import codex.lb04.Model.Game;
import codex.lb04.Model.ResourceCard;
import codex.lb04.Observer.GameObserver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class GameControllerTest {
    private GameObserver gameObserver;
    private GameController gameController;
    private Game game;


    @Before
    public void setUp() {
        this.gameObserver = new GameObserver();
        this.game = Game.getInstance();
        this.game.addObserver(gameObserver);
        game.setDeck();
        this.gameController = GameController.getInstance();
    }

    @After
    public void tearDown() {
        this.game.resetInstance();
        this.gameController.resetInstance();
        this.game = null;
        this.gameObserver = null;
        this.gameController = null;
    }


    @Test
    public void testCreateGameController() {
        assertNotNull(GameController.getInstance());
    }


    @Test
    public void testOnMessageReceived() {
        gameController.onMessageReceived(new CreateGameMessage("test" , 2));

        gameController.onMessageReceived(new ErrorMessage("test", "Error"));
        assertEquals(GameState.LOGIN, game.getGameState());

        game.setGameState(GameState.INIT);
        gameController.onMessageReceived(new ErrorMessage("test", "Error"));
        assertEquals(GameState.INIT, game.getGameState());


        game.setGameState(GameState.LOGIN);
        gameController.onMessageReceived(new LoginMessage("test"));
        assertEquals(GameState.LOGIN, game.getGameState());

        game.setGameState(GameState.LOGIN);
        gameController.onMessageReceived(new CreateGameMessage("test" , 2));
        gameController.onMessageReceived(new LoginMessage("test2"));
        gameController.onMessageReceived(new ReadyMessage("test"));
        gameController.onMessageReceived(new ReadyMessage("test2"));
        assertEquals(GameState.IN_GAME, game.getGameState());

        game.setGameState(GameState.IN_GAME);
        gameController.onMessageReceived(new ErrorMessage("test", "Error"));
        assertEquals(GameState.IN_GAME, game.getGameState());
    }

    @Test
    public void startGame() {
        gameController.onMessageReceived(new CreateGameMessage("test", 2));

        game.setGameState(GameState.LOGIN);
        game.addPlayerToLobby("test");
        game.addPlayerToLobby("test2");
        gameController.startGame();
        assertEquals(GameState.IN_GAME, game.getGameState());
        game.resetInstance();
    }

    @Test
    public void testGetTurnController() {
        gameController.onMessageReceived(new CreateGameMessage("test", 3));

        game.setGameState(GameState.LOGIN);
        game.addPlayerToLobby("test1");
        game.addPlayerToLobby("test2");
        gameController.startGame();
        assertNotNull(gameController.getTurnController());
    }

    @Test
    public void simulationGame() {
        String player1 = "Pitesse";
        String player2 = "Barde";
        String player3 = "AlexIlLeone";
        String player4 = "Brio";
        String player5 = "Rafa Leao";
        Face randomFace = new Face(null, null, null, null);

        CreateGameMessage createGameMessage = new CreateGameMessage(player1, 4);
        gameController.onMessageReceived(createGameMessage);

        LoginMessage login2 = new LoginMessage(player2);
        gameController.onMessageReceived(login2);

        LoginMessage login3 = new LoginMessage(player3);
        gameController.onMessageReceived(login3);

        LoginMessage login4 = new LoginMessage(player4);
        gameController.onMessageReceived(login4);

        ReadyMessage start1 = new ReadyMessage(player1);
        gameController.onMessageReceived(start1);
        ReadyMessage start2 = new ReadyMessage(player2);
        gameController.onMessageReceived(start2);
        ReadyMessage start3 = new ReadyMessage(player3);
        gameController.onMessageReceived(start3);
        ReadyMessage start4 = new ReadyMessage(player4);
        gameController.onMessageReceived(start4);

        TurnController turnController = gameController.getTurnController();

        //first player tries to end his turn without choosing an initial card and a secret objective
        String activePlayer = gameController.getTurnController().getActivePlayer();
        EndTurnMessage EndFirstTurn = new EndTurnMessage(activePlayer);
        gameController.onMessageReceived(EndFirstTurn);
        assertNotEquals(player2, gameController.getTurnController().getActivePlayer());
        assertEquals(player1, gameController.getTurnController().getActivePlayer());

        //first player picks the side of the initial card
        PickInitialCardSideMessage pick1 = new PickInitialCardSideMessage(activePlayer, game.getPlayerByName(activePlayer).getBoard().getInitialCard());
        gameController.onMessageReceived(pick1);
        assertEquals(pick1.getInitialCard(), game.getPlayerByName(activePlayer).getBoard().getCard(0, 0));

        //first player picks his secret objective
        PickSecretObjectiveMessage secret = new PickSecretObjectiveMessage(activePlayer, 0);
        gameController.onMessageReceived(secret);
        assertEquals(game.getPlayerByName(activePlayer).getBoard().getSecretObjectiveToPick().get(0), game.getPlayerByName(activePlayer).getBoard().getSecretObjective());

        //message from non-active player doesn't modify his board
        PickInitialCardSideMessage pick2 = new PickInitialCardSideMessage(player2, game.getPlayerByName(player2).getBoard().getInitialCard());
        gameController.onMessageReceived(pick2);
        assertNotEquals(pick2.getInitialCard(), game.getPlayerByName(player2).getBoard().getCard(0, 0));

        //message from non-logged player doesn't modify the board
        Card noSense = new Card(Color.BLUE, randomFace, randomFace);
        PlaceCardMessage external = new PlaceCardMessage(player5, 0, 0, noSense);
        gameController.onMessageReceived(external);
        assertNotEquals(noSense, game.getPlayerByName(activePlayer).getBoard().getCard(0, 0));

        //first player places a card flipping it to be sure it has no cost restriction
        Card toPlace = game.getPlayerByName(activePlayer).getBoard().getHand().get(1);
        toPlace.flip();
        PlaceCardMessage correct = new PlaceCardMessage(activePlayer, 1, 1, toPlace);
        gameController.onMessageReceived(correct);
        assertEquals(toPlace, game.getPlayerByName(activePlayer).getBoard().getCard(1, 1));

        //active player draws a resource card
        PickResourceCardMessage resource = new PickResourceCardMessage(activePlayer, 1);
        ResourceCard resourceCard = game.getPlayerByName(activePlayer).getBoard().getDeck().getVisibleResourceCards().get(1);
        gameController.onMessageReceived(resource);
        assertEquals(resourceCard, game.getPlayerByName(activePlayer).getBoard().getHand().get(2));

        //first player end his turn
        EndFirstTurn = new EndTurnMessage(activePlayer);
        gameController.onMessageReceived(EndFirstTurn);
        activePlayer = turnController.getActivePlayer();
        assertNotEquals(player1, turnController.getActivePlayer());
        assertEquals(player2, turnController.getActivePlayer());

        //second player turn

        //second player tries to place a card without choosing an initial card
        toPlace = game.getPlayerByName(activePlayer).getBoard().getHand().get(1);
        PlaceCardMessage incorrect = new PlaceCardMessage(activePlayer, 0, 0, toPlace);
        gameController.onMessageReceived(incorrect);
        assertNotEquals(game.getPlayerByName(activePlayer).getBoard().getCard(0, 0),toPlace);

        //second player picks the side of the initial card
        pick1 = new PickInitialCardSideMessage(activePlayer, game.getPlayerByName(activePlayer).getBoard().getInitialCard());
        gameController.onMessageReceived(pick1);
        assertEquals(pick1.getInitialCard(), game.getPlayerByName(activePlayer).getBoard().getCard(0, 0));

        //second player tries to end his turn before choosing his secret objective
        EndTurnMessage EndSecondTurn = new EndTurnMessage(activePlayer);
        gameController.onMessageReceived(EndSecondTurn);
        assertNotEquals(player3, gameController.getTurnController().getActivePlayer());
        assertEquals(player2, gameController.getTurnController().getActivePlayer());

        //second player picks his secret objective
        secret = new PickSecretObjectiveMessage(activePlayer, 0);
        gameController.onMessageReceived(secret);
        assertEquals(game.getPlayerByName(activePlayer).getBoard().getSecretObjectiveToPick().get(0), game.getPlayerByName(activePlayer).getBoard().getSecretObjective());

        //second player places a card flipping it to be sure it has no cost restriction
        toPlace = game.getPlayerByName(activePlayer).getBoard().getHand().get(1);
        toPlace.flip();
        correct = new PlaceCardMessage(activePlayer, 1, 1, toPlace);
        gameController.onMessageReceived(correct);
        assertEquals(toPlace, game.getPlayerByName(activePlayer).getBoard().getCard(1, 1));

        //second player draws a resource card
        resource = new PickResourceCardMessage(activePlayer, 1);
        resourceCard = game.getPlayerByName(activePlayer).getBoard().getDeck().getVisibleResourceCards().get(1);
        gameController.onMessageReceived(resource);
        assertEquals(resourceCard, game.getPlayerByName(activePlayer).getBoard().getHand().get(2));

        //second player end his turn
        EndSecondTurn = new EndTurnMessage(activePlayer);
        gameController.onMessageReceived(EndSecondTurn);
        activePlayer = turnController.getActivePlayer();
        assertNotEquals(player2, gameController.getTurnController().getActivePlayer());
        assertEquals(player3, gameController.getTurnController().getActivePlayer());

        //third player turn
        //third player picks the side of the initial card
        pick1 = new PickInitialCardSideMessage(activePlayer, game.getPlayerByName(activePlayer).getBoard().getInitialCard());
        gameController.onMessageReceived(pick1);
        assertEquals(pick1.getInitialCard(), game.getPlayerByName(activePlayer).getBoard().getCard(0, 0));

        //third player tries to place a card without picking the secret objective
        toPlace = game.getPlayerByName(activePlayer).getBoard().getHand().get(1);
        correct = new PlaceCardMessage(activePlayer, 1, 1, toPlace);
        gameController.onMessageReceived(correct);
        assertNull(game.getPlayerByName(activePlayer).getBoard().getCard(1, 1));

        //third player picks his secret objective
        secret = new PickSecretObjectiveMessage(activePlayer, 0);
        gameController.onMessageReceived(secret);
        assertEquals(game.getPlayerByName(activePlayer).getBoard().getSecretObjectiveToPick().get(0), game.getPlayerByName(activePlayer).getBoard().getSecretObjective());

        //third player places a card flipping it to be sure it has no cost restriction
        toPlace = game.getPlayerByName(activePlayer).getBoard().getHand().get(1);
        toPlace.flip();
        correct = new PlaceCardMessage(activePlayer, 1, 1, toPlace);
        gameController.onMessageReceived(correct);
        assertEquals(toPlace, game.getPlayerByName(activePlayer).getBoard().getCard(1, 1));

        //third player tries to end his turn without drawing a card
        EndTurnMessage EndThirdTurn = new EndTurnMessage(activePlayer);
        gameController.onMessageReceived(EndThirdTurn);
        assertEquals(player3, gameController.getTurnController().getActivePlayer());
        assertNotEquals(player4, gameController.getTurnController().getActivePlayer());

        //third player draws a resource card
        resource = new PickResourceCardMessage(activePlayer, 1);
        resourceCard = game.getPlayerByName(activePlayer).getBoard().getDeck().getVisibleResourceCards().get(1);
        gameController.onMessageReceived(resource);
        assertEquals(resourceCard, game.getPlayerByName(activePlayer).getBoard().getHand().get(2));

        //third player end his turn
        EndThirdTurn = new EndTurnMessage(activePlayer);
        gameController.onMessageReceived(EndThirdTurn);
        activePlayer = turnController.getActivePlayer();
        assertNotEquals(player3, gameController.getTurnController().getActivePlayer());
        assertEquals(player4, gameController.getTurnController().getActivePlayer());

        //fourth player turn
        //fourth player picks the side of the initial card
        pick1 = new PickInitialCardSideMessage(activePlayer, game.getPlayerByName(activePlayer).getBoard().getInitialCard());
        gameController.onMessageReceived(pick1);
        assertEquals(pick1.getInitialCard(), game.getPlayerByName(activePlayer).getBoard().getCard(0, 0));

        //fourth player picks his secret objective
        secret = new PickSecretObjectiveMessage(activePlayer, 0);
        gameController.onMessageReceived(secret);
        assertEquals(game.getPlayerByName(activePlayer).getBoard().getSecretObjectiveToPick().get(0), game.getPlayerByName(activePlayer).getBoard().getSecretObjective());

        //fourth player tries to end his turn without placing and drawing a card
        EndTurnMessage EndFourthTurn = new EndTurnMessage(activePlayer);
        gameController.onMessageReceived(EndFourthTurn);
        assertNotEquals(player1, gameController.getTurnController().getActivePlayer());
        assertEquals(player4, gameController.getTurnController().getActivePlayer());

        //fourth player places a card flipping it to be sure it has no cost restriction
        toPlace = game.getPlayerByName(activePlayer).getBoard().getHand().get(1);
        toPlace.flip();
        correct = new PlaceCardMessage(activePlayer, 1, 1, toPlace);
        gameController.onMessageReceived(correct);
        assertEquals(toPlace, game.getPlayerByName(activePlayer).getBoard().getCard(1, 1));

        //fourth player draws a resource card
        resource = new PickResourceCardMessage(activePlayer, 1);
        resourceCard = game.getPlayerByName(activePlayer).getBoard().getDeck().getVisibleResourceCards().get(1);
        gameController.onMessageReceived(resource);
        assertEquals(resourceCard, game.getPlayerByName(activePlayer).getBoard().getHand().get(2));

        //fourth player end his turn
        EndFourthTurn = new EndTurnMessage(activePlayer);
        gameController.onMessageReceived(EndFourthTurn);
        assertNotEquals(player4, gameController.getTurnController().getActivePlayer());
        assertEquals(player1, gameController.getTurnController().getActivePlayer());

    }

}