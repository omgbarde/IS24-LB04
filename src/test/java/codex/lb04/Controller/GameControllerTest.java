package codex.lb04.Controller;

import codex.lb04.Controller.GameController.GameController;
import codex.lb04.Message.ErrorMessage;
import codex.lb04.Message.GameMessage.*;
import codex.lb04.Message.LoginMessage;
import codex.lb04.Model.*;
import codex.lb04.Model.Enumerations.Color;
import codex.lb04.Model.Enumerations.GameState;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

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

    @Test
    public void simulationGame() {
        String player1 = "Pitesse";
        String player2 = "Barde";
        String player3 = "AlexIlLeone2";
        String player4 = "Brio";
        String player5 = "Rafa Leao";
        Face randomFace = new Face(null, null, null, null);


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

        //first player places a card
        Card toPlace = game.getPlayerByName(activePlayer).getBoard().getHand().get(1);
        PlaceCardMessage correct = new PlaceCardMessage(activePlayer, 1, 1, toPlace);
        gameController.onMessageReceived(correct);
        assertEquals(toPlace, game.getPlayerByName(activePlayer).getBoard().getCard(1, 1));

        //active player draws a resource card
        PickResourceCardMessage resource = new PickResourceCardMessage(activePlayer, 1);
        ResourceCard resourceCard = game.getPlayerByName(activePlayer).getBoard().getDeck().getVisibleResourceCards().get(1);
        gameController.onMessageReceived(resource);
        assertEquals(resourceCard, game.getPlayerByName(activePlayer).getBoard().getHand().get(2));

        //test if circularIterator works
        EndFirstTurn = new EndTurnMessage(activePlayer);
        gameController.onMessageReceived(EndFirstTurn);
        assertNotEquals(player1, gameController.getTurnController().getActivePlayer());
        assertEquals(player2, gameController.getTurnController().getActivePlayer());
        gameController.getTurnController().changeTurn();
        assertEquals(player3, gameController.getTurnController().getActivePlayer());
        gameController.getTurnController().changeTurn();
        assertEquals(player4, gameController.getTurnController().getActivePlayer());
        gameController.getTurnController().changeTurn();
        assertEquals(player1, gameController.getTurnController().getActivePlayer());
        gameController.getTurnController().changeTurn();
        assertEquals(player2, gameController.getTurnController().getActivePlayer());
        activePlayer = player2;

        //second player turn

        //second player tries to place a card without choosing an initial card
        toPlace = game.getPlayerByName(activePlayer).getBoard().getHand().get(1);
        correct = new PlaceCardMessage(activePlayer, 0, 0, toPlace);
        gameController.onMessageReceived(correct);
        assertNull(game.getPlayerByName(activePlayer).getBoard().getCard(0, 0));

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

        //second player places a card
        toPlace = game.getPlayerByName(activePlayer).getBoard().getHand().get(1);
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
        assertNotEquals(player2, gameController.getTurnController().getActivePlayer());
        assertEquals(player3, gameController.getTurnController().getActivePlayer());
        activePlayer = player3;

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

        //third player places a card
        toPlace = game.getPlayerByName(activePlayer).getBoard().getHand().get(1);
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
        assertNotEquals(player3, gameController.getTurnController().getActivePlayer());
        assertEquals(player4, gameController.getTurnController().getActivePlayer());
        activePlayer = player4;

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

        //fourth player places a card
        toPlace = game.getPlayerByName(activePlayer).getBoard().getHand().get(1);
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
        activePlayer = player1;

        //starting of the second turn
        //first player's second turn
        //player1 places a card, but it can't be placed because of the position
        toPlace = game.getPlayerByName(activePlayer).getBoard().getHand().get(1);
        PlaceCardMessage wrong = new PlaceCardMessage(activePlayer, 3, 3, toPlace);
        gameController.onMessageReceived(wrong);
        assertNull(game.getPlayerByName(activePlayer).getBoard().getCard(3, 3));

        //player1 places a card, but it can't be placed because the upper right corner of the card in 1,1 is covered
        toPlace = game.getPlayerByName(activePlayer).getBoard().getHand().get(1);
        wrong = new PlaceCardMessage(activePlayer, 2, 2, toPlace);
        gameController.onMessageReceived(wrong);
        assertNull(game.getPlayerByName(activePlayer).getBoard().getCard(2, 2));

        //player1 places a card
        toPlace = game.getPlayerByName(activePlayer).getBoard().getHand().get(1);
        correct = new PlaceCardMessage(activePlayer, 0, 2, toPlace);
        gameController.onMessageReceived(correct);
        assertEquals(toPlace,game.getPlayerByName(activePlayer).getBoard().getCard(0, 2));

        //player1 draws a gold card
        PickGoldCardMessage gold = new PickGoldCardMessage(activePlayer, 1);
        GoldCard goldCard = game.getPlayerByName(activePlayer).getBoard().getDeck().getVisibleGoldCards().get(1);
        gameController.onMessageReceived(gold);
        assertEquals(goldCard, game.getPlayerByName(activePlayer).getBoard().getHand().get(2));

        //first player ends his second turn
        EndFirstTurn = new EndTurnMessage(activePlayer);
        gameController.onMessageReceived(EndFirstTurn);
        assertNotEquals(player1, gameController.getTurnController().getActivePlayer());
        assertEquals(player2, gameController.getTurnController().getActivePlayer());
        activePlayer = player2;

        //start of the second turn of the second player
        //player2 places a card
        toPlace = game.getPlayerByName(activePlayer).getBoard().getHand().get(1);
        correct = new PlaceCardMessage(activePlayer, 2, 2, toPlace);
        gameController.onMessageReceived(correct);
        assertEquals(toPlace,game.getPlayerByName(activePlayer).getBoard().getCard(2, 2));

        //player2 draws a resource card from the pile
        resource = new PickResourceCardMessage(activePlayer, 2);
        resourceCard = game.getPlayerByName(activePlayer).getBoard().getDeck().getResourceCards().get(0);
        gameController.onMessageReceived(resource);
        assertEquals(resourceCard, game.getPlayerByName(activePlayer).getBoard().getHand().get(2));

        //player2 ends his turn
        EndSecondTurn = new EndTurnMessage(activePlayer);
        gameController.onMessageReceived(EndSecondTurn);
        assertNotEquals(player2, gameController.getTurnController().getActivePlayer());
        assertEquals(player3, gameController.getTurnController().getActivePlayer());
        activePlayer = player3;

        //third player's second turn
        //player3 places a card
        toPlace = game.getPlayerByName(activePlayer).getBoard().getHand().get(1);
        correct = new PlaceCardMessage(activePlayer, 2, 2, toPlace);
        gameController.onMessageReceived(correct);
        assertEquals(toPlace, game.getPlayerByName(activePlayer).getBoard().getCard(2, 2));

        //third player draws a resource card
        resource = new PickResourceCardMessage(activePlayer, 1);
        resourceCard = game.getPlayerByName(activePlayer).getBoard().getDeck().getVisibleResourceCards().get(1);
        gameController.onMessageReceived(resource);
        assertEquals(resourceCard, game.getPlayerByName(activePlayer).getBoard().getHand().get(2));

        //third player end his turn
        EndThirdTurn = new EndTurnMessage(activePlayer);
        gameController.onMessageReceived(EndThirdTurn);
        assertNotEquals(player3, gameController.getTurnController().getActivePlayer());
        assertEquals(player4, gameController.getTurnController().getActivePlayer());
        activePlayer = player4;

        //fourth player's second turn
        //fourth player places a card
        toPlace = game.getPlayerByName(activePlayer).getBoard().getHand().get(1);
        correct = new PlaceCardMessage(activePlayer, 2, 0, toPlace);
        gameController.onMessageReceived(correct);
        assertEquals(toPlace, game.getPlayerByName(activePlayer).getBoard().getCard(2, 0));

        //fourth player draws a resource card
        resource = new PickResourceCardMessage(activePlayer, 1);
        resourceCard = game.getPlayerByName(activePlayer).getBoard().getDeck().getVisibleResourceCards().get(1);
        gameController.onMessageReceived(resource);
        assertEquals(resourceCard, game.getPlayerByName(activePlayer).getBoard().getHand().get(2));

        //fourth player end his second turn
        EndFourthTurn = new EndTurnMessage(activePlayer);
        gameController.onMessageReceived(EndFourthTurn);
        assertNotEquals(player4, gameController.getTurnController().getActivePlayer());
        assertEquals(player1, gameController.getTurnController().getActivePlayer());
        activePlayer = player1;

        //starting of the third turn
        //first player's third turn

        //player1 places a card
        toPlace = game.getPlayerByName(activePlayer).getBoard().getHand().get(1);
        correct = new PlaceCardMessage(activePlayer, 1, 3, toPlace);
        gameController.onMessageReceived(correct);
        assertEquals(toPlace,game.getPlayerByName(activePlayer).getBoard().getCard(1, 3));

        //player1 draws a resource card
        resource = new PickResourceCardMessage(activePlayer, 1);
        resourceCard = game.getPlayerByName(activePlayer).getBoard().getDeck().getVisibleResourceCards().get(1);
        gameController.onMessageReceived(resource);
        assertEquals(resourceCard, game.getPlayerByName(activePlayer).getBoard().getHand().get(2));

        //first player ends his second turn
        EndFirstTurn = new EndTurnMessage(activePlayer);
        gameController.onMessageReceived(EndFirstTurn);
        assertNotEquals(player1, gameController.getTurnController().getActivePlayer());
        assertEquals(player2, gameController.getTurnController().getActivePlayer());
        activePlayer = player2;

        //start of the third turn for the second player
        //player2 places a card
        toPlace = game.getPlayerByName(activePlayer).getBoard().getHand().get(1);
        correct = new PlaceCardMessage(activePlayer, -1, -1, toPlace);
        gameController.onMessageReceived(correct);
        assertEquals(toPlace,game.getPlayerByName(activePlayer).getBoard().getCard(-1, -1));

        //player2 draws a resource card from the pile
        resource = new PickResourceCardMessage(activePlayer, 2);
        resourceCard = game.getPlayerByName(activePlayer).getBoard().getDeck().getResourceCards().get(0);
        gameController.onMessageReceived(resource);
        assertEquals(resourceCard, game.getPlayerByName(activePlayer).getBoard().getHand().get(2));

        //player2 ends his third turn
        EndSecondTurn = new EndTurnMessage(activePlayer);
        gameController.onMessageReceived(EndSecondTurn);
        assertNotEquals(player2, gameController.getTurnController().getActivePlayer());
        assertEquals(player3, gameController.getTurnController().getActivePlayer());
        activePlayer = player3;

        //third player's third turn
        //player3 places a card
        toPlace = game.getPlayerByName(activePlayer).getBoard().getHand().get(1);
        correct = new PlaceCardMessage(activePlayer, -1, 1, toPlace);
        gameController.onMessageReceived(correct);
        assertEquals(toPlace, game.getPlayerByName(activePlayer).getBoard().getCard(-1, 1));

        //third player draws a resource card
        resource = new PickResourceCardMessage(activePlayer, 1);
        resourceCard = game.getPlayerByName(activePlayer).getBoard().getDeck().getVisibleResourceCards().get(1);
        gameController.onMessageReceived(resource);
        assertEquals(resourceCard, game.getPlayerByName(activePlayer).getBoard().getHand().get(2));

        //third player end his third turn
        EndThirdTurn = new EndTurnMessage(activePlayer);
        gameController.onMessageReceived(EndThirdTurn);
        assertNotEquals(player3, gameController.getTurnController().getActivePlayer());
        assertEquals(player4, gameController.getTurnController().getActivePlayer());
        activePlayer = player4;

        //fourth player's third turn
        //fourth player places a card
        toPlace = game.getPlayerByName(activePlayer).getBoard().getHand().get(1);
        correct = new PlaceCardMessage(activePlayer, 3, 1, toPlace);
        gameController.onMessageReceived(correct);
        assertEquals(toPlace, game.getPlayerByName(activePlayer).getBoard().getCard(3, 1));

        //fourth player draws a resource card
        resource = new PickResourceCardMessage(activePlayer, 1);
        resourceCard = game.getPlayerByName(activePlayer).getBoard().getDeck().getVisibleResourceCards().get(1);
        gameController.onMessageReceived(resource);
        assertEquals(resourceCard, game.getPlayerByName(activePlayer).getBoard().getHand().get(2));

        //fourth player end his third turn
        EndFourthTurn = new EndTurnMessage(activePlayer);
        gameController.onMessageReceived(EndFourthTurn);
        assertNotEquals(player4, gameController.getTurnController().getActivePlayer());
        assertEquals(player1, gameController.getTurnController().getActivePlayer());
        activePlayer = player1;

        //fourth turn starts
        //first player fourth turn

        //player1 tries to place a gold card without having the resources needed
        toPlace = game.getPlayerByName(activePlayer).getBoard().getHand().get(1);
        wrong = new PlaceCardMessage(activePlayer, 2, 4, toPlace);
        gameController.onMessageReceived(wrong);
        assertNull(game.getPlayerByName(activePlayer).getBoard().getCard(2, 4));

        //player1 places a gold card that completes the secret objective
        toPlace = game.getPlayerByName(activePlayer).getBoard().getHand().getFirst();
        correct = new PlaceCardMessage(activePlayer, 2, 4, toPlace);
        gameController.onMessageReceived(correct);
        assertEquals(toPlace, game.getPlayerByName(activePlayer).getBoard().getCard(2, 4));

        //player1 draws a gold card from the pile
        gold = new PickGoldCardMessage(activePlayer, 2);
        goldCard = game.getPlayerByName(activePlayer).getBoard().getDeck().getGoldCards().getFirst();
        gameController.onMessageReceived(gold);
        assertEquals(goldCard, game.getPlayerByName(activePlayer).getBoard().getHand().get(2));

        //player1 ends his fourth turn
        EndFirstTurn = new EndTurnMessage(activePlayer);
        gameController.onMessageReceived(EndFirstTurn);
        assertNotEquals(player1, gameController.getTurnController().getActivePlayer());
        assertEquals(player2, gameController.getTurnController().getActivePlayer());
        activePlayer = player2;

        //start of the fourth turn of the second player
        //player2 places a gold card that increases his points by 2 for every corner covered (1 corner covered)
        toPlace = game.getPlayerByName(activePlayer).getBoard().getHand().getFirst();
        correct = new PlaceCardMessage(activePlayer, 3, 3, toPlace);
        gameController.onMessageReceived(correct);
        assertEquals(toPlace, game.getPlayerByName(activePlayer).getBoard().getCard(3, 3));

        //player2 draws a gold card from the pile
        gold = new PickGoldCardMessage(activePlayer, 2);
        goldCard = game.getPlayerByName(activePlayer).getBoard().getDeck().getGoldCards().getFirst();
        gameController.onMessageReceived(gold);
        assertEquals(goldCard, game.getPlayerByName(activePlayer).getBoard().getHand().get(2));

        //player2 ends his fourth turn
        EndSecondTurn = new EndTurnMessage(activePlayer);
        gameController.onMessageReceived(EndSecondTurn);
        assertNotEquals(player2, gameController.getTurnController().getActivePlayer());
        assertEquals(player3, gameController.getTurnController().getActivePlayer());
        activePlayer = player3;

        //third player's fourth turn
        //player3 places a gold card that increases his points by 2 for every corner covered (2 corner covered)
        toPlace = game.getPlayerByName(activePlayer).getBoard().getHand().getFirst();
        correct = new PlaceCardMessage(activePlayer, 0, 2, toPlace);
        gameController.onMessageReceived(correct);
        assertEquals(toPlace, game.getPlayerByName(activePlayer).getBoard().getCard(0, 2));

        //player3 draws a gold from the pile
        gold = new PickGoldCardMessage(activePlayer, 2);
        goldCard = game.getPlayerByName(activePlayer).getBoard().getDeck().getGoldCards().getFirst();
        gameController.onMessageReceived(gold);
        assertEquals(goldCard, game.getPlayerByName(activePlayer).getBoard().getHand().get(2));

        //player3 ends his turn
        EndThirdTurn = new EndTurnMessage(activePlayer);
        gameController.onMessageReceived(EndThirdTurn);
        assertNotEquals(player3, gameController.getTurnController().getActivePlayer());
        assertEquals(player4, gameController.getTurnController().getActivePlayer());
        activePlayer = player4;

        //fourth player's fourth turn
        //player4 tries to place a card in a place where there's a covered corner
        toPlace = game.getPlayerByName(activePlayer).getBoard().getHand().get(1);
        wrong = new PlaceCardMessage(activePlayer, 2, 2, toPlace);
        gameController.onMessageReceived(wrong);
        assertNull(game.getPlayerByName(activePlayer).getBoard().getCard(2, 2));

        //player4 places a card
        toPlace = game.getPlayerByName(activePlayer).getBoard().getHand().get(1);
        correct = new PlaceCardMessage(activePlayer, 3, -1, toPlace);
        gameController.onMessageReceived(correct);
        assertEquals(toPlace, game.getPlayerByName(activePlayer).getBoard().getCard(3, -1));

        //player4 draws a gold card from the pile
        gold = new PickGoldCardMessage(activePlayer, 2);
        goldCard = game.getPlayerByName(activePlayer).getBoard().getDeck().getGoldCards().getFirst();
        gameController.onMessageReceived(gold);
        assertEquals(goldCard, game.getPlayerByName(activePlayer).getBoard().getHand().get(2));

        //player4 end his turn
        EndFourthTurn = new EndTurnMessage(activePlayer);
        gameController.onMessageReceived(EndFourthTurn);
        assertNotEquals(player4, gameController.getTurnController().getActivePlayer());
        assertEquals(player1, gameController.getTurnController().getActivePlayer());
        activePlayer = player1;

        //starting of the fifth turn with assert of the points
        //first player's fifth turn

        //player1 places a gold card that gives him 3 points
        toPlace = game.getPlayerByName(activePlayer).getBoard().getHand().get(2);
        correct = new PlaceCardMessage(activePlayer, -1, 1, toPlace);
        gameController.onMessageReceived(correct);
        assertEquals(toPlace, game.getPlayerByName(activePlayer).getBoard().getCard(-1, 1));

        //player1 draws a resource card from the visible resource cards
        resource = new PickResourceCardMessage(activePlayer, 1);
        resourceCard = game.getPlayerByName(activePlayer).getBoard().getDeck().getVisibleResourceCards().get(1);
        gameController.onMessageReceived(resource);
        assertEquals(resourceCard, game.getPlayerByName(activePlayer).getBoard().getHand().get(2));
        assertEquals((Integer)4, game.getPlayerByName(activePlayer).getBoard().getPoints());

        //player1 ends his fifth turn
        EndFirstTurn = new EndTurnMessage(activePlayer);
        gameController.onMessageReceived(EndFirstTurn);
        assertNotEquals(player1, gameController.getTurnController().getActivePlayer());
        assertEquals(player2, gameController.getTurnController().getActivePlayer());
        activePlayer = player2;

        //start of the fifth turn of the second player
        //player2 places a gold card that gives him 3 points
        toPlace = game.getPlayerByName(activePlayer).getBoard().getHand().get(2);
        correct = new PlaceCardMessage(activePlayer, 2, 4, toPlace);
        gameController.onMessageReceived(correct);
        assertEquals(toPlace, game.getPlayerByName(activePlayer).getBoard().getCard(2, 4));

        //player2 draws a resource card from the visible resource card
        resource = new PickResourceCardMessage(activePlayer, 1);
        resourceCard = game.getPlayerByName(activePlayer).getBoard().getDeck().getVisibleResourceCards().get(1);
        gameController.onMessageReceived(resource);
        assertEquals(resourceCard, game.getPlayerByName(activePlayer).getBoard().getHand().get(2));
        assertEquals((Integer)5, game.getPlayerByName(activePlayer).getBoard().getPoints());

        //player2 ends his fifth turn
        EndSecondTurn = new EndTurnMessage(activePlayer);
        gameController.onMessageReceived(EndSecondTurn);
        assertNotEquals(player2, gameController.getTurnController().getActivePlayer());
        assertEquals(player3, gameController.getTurnController().getActivePlayer());
        activePlayer = player3;

        //third player's fifth turn
        //player3 places a resource card that gives him a point
        toPlace = game.getPlayerByName(activePlayer).getBoard().getHand().get(1);
        correct = new PlaceCardMessage(activePlayer, 2, 0, toPlace);
        gameController.onMessageReceived(correct);
        assertEquals(toPlace, game.getPlayerByName(activePlayer).getBoard().getCard(2, 0));

        //player3 draws a resource card from the visible resource card
        resource = new PickResourceCardMessage(activePlayer, 0);
        resourceCard = game.getPlayerByName(activePlayer).getBoard().getDeck().getVisibleResourceCards().getFirst();
        gameController.onMessageReceived(resource);
        assertEquals(resourceCard, game.getPlayerByName(activePlayer).getBoard().getHand().get(2));
        assertEquals((Integer)6, game.getPlayerByName(activePlayer).getBoard().getPoints());

        //player3 ends his turn
        EndThirdTurn = new EndTurnMessage(activePlayer);
        gameController.onMessageReceived(EndThirdTurn);
        assertNotEquals(player3, gameController.getTurnController().getActivePlayer());
        assertEquals(player4, gameController.getTurnController().getActivePlayer());
        activePlayer = player4;

        //fourth player's fifth turn
        //player4 places a gold card, with enough resources but covering the resources needed which is allowed
        toPlace = game.getPlayerByName(activePlayer).getBoard().getHand().getFirst();
        correct = new PlaceCardMessage(activePlayer, 1, -1, toPlace);
        gameController.onMessageReceived(correct);
        assertEquals(toPlace, game.getPlayerByName(activePlayer).getBoard().getCard(1, -1));

        //player4 draws a gold card from the pile
        gold = new PickGoldCardMessage(activePlayer, 2);
        goldCard = game.getPlayerByName(activePlayer).getBoard().getDeck().getGoldCards().getFirst();
        gameController.onMessageReceived(gold);
        assertEquals(goldCard, game.getPlayerByName(activePlayer).getBoard().getHand().get(2));
        assertEquals((Integer)6, game.getPlayerByName(activePlayer).getBoard().getPoints());

        //player4 ends his turn
        EndFourthTurn = new EndTurnMessage(activePlayer);
        gameController.onMessageReceived(EndFourthTurn);
        assertNotEquals(player4, gameController.getTurnController().getActivePlayer());
        assertEquals(player1, gameController.getTurnController().getActivePlayer());
        activePlayer = player1;

        //start of the sixth turn
        //player1 places a resource card
        toPlace = game.getPlayerByName(activePlayer).getBoard().getHand().get(1);
        correct = new PlaceCardMessage(activePlayer, 1, -1, toPlace);
        gameController.onMessageReceived(correct);
        assertEquals(toPlace, game.getPlayerByName(activePlayer).getBoard().getCard(1, -1));

        //player1 draws a gold card from the visible gold card
        gold = new PickGoldCardMessage(activePlayer, 1);
        goldCard = game.getPlayerByName(activePlayer).getBoard().getDeck().getVisibleGoldCards().get(1);
        gameController.onMessageReceived(gold);
        assertEquals(goldCard, game.getPlayerByName(activePlayer).getBoard().getHand().get(2));

        //player1 ends his sixth turn
        EndFirstTurn = new EndTurnMessage(activePlayer);
        gameController.onMessageReceived(EndFirstTurn);
        assertNotEquals(player1, gameController.getTurnController().getActivePlayer());
        assertEquals(player2, gameController.getTurnController().getActivePlayer());
        activePlayer = player2;

        //player2's sixth turn
        //player2 places a resource card
        toPlace = game.getPlayerByName(activePlayer).getBoard().getHand().getFirst();
        correct = new PlaceCardMessage(activePlayer, 4, 2, toPlace);
        gameController.onMessageReceived(correct);
        assertEquals(toPlace, game.getPlayerByName(activePlayer).getBoard().getCard(4, 2));

        //player1 draws a gold card from the visible gold card
        gold = new PickGoldCardMessage(activePlayer, 0);
        goldCard = game.getPlayerByName(activePlayer).getBoard().getDeck().getVisibleGoldCards().getFirst();
        gameController.onMessageReceived(gold);
        assertEquals(goldCard, game.getPlayerByName(activePlayer).getBoard().getHand().get(2));

        //player2 ends his sixth turn
        EndSecondTurn = new EndTurnMessage(activePlayer);
        gameController.onMessageReceived(EndSecondTurn);
        assertNotEquals(player2, gameController.getTurnController().getActivePlayer());
        assertEquals(player3, gameController.getTurnController().getActivePlayer());
        activePlayer = player3;

        //third player's sixth turn
        //player3 places a resource card
        toPlace = game.getPlayerByName(activePlayer).getBoard().getHand().get(2);
        correct = new PlaceCardMessage(activePlayer, 1, 3, toPlace);
        gameController.onMessageReceived(correct);
        assertEquals(toPlace, game.getPlayerByName(activePlayer).getBoard().getCard(1, 3));

        //player3 draws a resource card from the visible resource card
        resource = new PickResourceCardMessage(activePlayer, 0);
        resourceCard = game.getPlayerByName(activePlayer).getBoard().getDeck().getVisibleResourceCards().getFirst();
        gameController.onMessageReceived(resource);
        assertEquals(resourceCard, game.getPlayerByName(activePlayer).getBoard().getHand().get(2));

        //player3 ends his turn
        EndThirdTurn = new EndTurnMessage(activePlayer);
        gameController.onMessageReceived(EndThirdTurn);
        assertNotEquals(player3, gameController.getTurnController().getActivePlayer());
        assertEquals(player4, gameController.getTurnController().getActivePlayer());
        activePlayer = player4;

        //fourth player's sixth turn
        //player4 places a card
        toPlace = game.getPlayerByName(activePlayer).getBoard().getHand().get(1);
        correct = new PlaceCardMessage(activePlayer, 0, -2, toPlace);
        gameController.onMessageReceived(correct);
        assertEquals(toPlace, game.getPlayerByName(activePlayer).getBoard().getCard(0, -2));

        //player4 draws a resource card from the pile
        resource = new PickResourceCardMessage(activePlayer, 2);
        resourceCard = game.getPlayerByName(activePlayer).getBoard().getDeck().getResourceCards().getFirst();
        gameController.onMessageReceived(resource);
        assertEquals(resourceCard, game.getPlayerByName(activePlayer).getBoard().getHand().get(2));

        //player4 end his turn
        EndFourthTurn = new EndTurnMessage(activePlayer);
        gameController.onMessageReceived(EndFourthTurn);
        assertNotEquals(player4, gameController.getTurnController().getActivePlayer());
        assertEquals(player1, gameController.getTurnController().getActivePlayer());
        activePlayer = player1;

        //start of the seventh turn
        //player1 places a card
        toPlace = game.getPlayerByName(activePlayer).getBoard().getHand().get(2);
        correct = new PlaceCardMessage(activePlayer, 3, 5, toPlace);
        gameController.onMessageReceived(correct);
        assertEquals(toPlace, game.getPlayerByName(activePlayer).getBoard().getCard(3, 5));

        //player1 draws a resource card from the pile
        resource = new PickResourceCardMessage(activePlayer, 2);
        resourceCard = game.getPlayerByName(activePlayer).getBoard().getDeck().getResourceCards().getFirst();
        gameController.onMessageReceived(resource);
        assertEquals(resourceCard, game.getPlayerByName(activePlayer).getBoard().getHand().get(2));

        //player1 ends his seventh turn
        EndFirstTurn = new EndTurnMessage(activePlayer);
        gameController.onMessageReceived(EndFirstTurn);
        assertNotEquals(player1, gameController.getTurnController().getActivePlayer());
        assertEquals(player2, gameController.getTurnController().getActivePlayer());
        activePlayer = player2;

        //player2's seventh turn
        //player2 places a gold card
        toPlace = game.getPlayerByName(activePlayer).getBoard().getHand().get(2);
        correct = new PlaceCardMessage(activePlayer, 4, 4, toPlace);
        gameController.onMessageReceived(correct);
        assertEquals(toPlace, game.getPlayerByName(activePlayer).getBoard().getCard(4, 4));

        //player2 draws a gold card from the pile
        gold = new PickGoldCardMessage(activePlayer, 2);
        goldCard = game.getPlayerByName(activePlayer).getBoard().getDeck().getGoldCards().getFirst();
        gameController.onMessageReceived(gold);
        assertEquals(goldCard, game.getPlayerByName(activePlayer).getBoard().getHand().get(2));

        //player2 ends his seventh turn
        EndSecondTurn = new EndTurnMessage(activePlayer);
        gameController.onMessageReceived(EndSecondTurn);
        assertNotEquals(player2, gameController.getTurnController().getActivePlayer());
        assertEquals(player3, gameController.getTurnController().getActivePlayer());
        activePlayer = player3;

        //third player's seventh turn
        //player3 places a resource card
        toPlace = game.getPlayerByName(activePlayer).getBoard().getHand().getFirst();
        correct = new PlaceCardMessage(activePlayer, -1, -1, toPlace);
        gameController.onMessageReceived(correct);
        assertEquals(toPlace, game.getPlayerByName(activePlayer).getBoard().getCard(-1, -1));

        //player3 draws a resource card from the visible resource card
        resource = new PickResourceCardMessage(activePlayer, 0);
        resourceCard = game.getPlayerByName(activePlayer).getBoard().getDeck().getVisibleResourceCards().getFirst();
        gameController.onMessageReceived(resource);
        assertEquals(resourceCard, game.getPlayerByName(activePlayer).getBoard().getHand().get(2));

        //player3 ends his turn
        EndThirdTurn = new EndTurnMessage(activePlayer);
        gameController.onMessageReceived(EndThirdTurn);
        assertNotEquals(player3, gameController.getTurnController().getActivePlayer());
        assertEquals(player4, gameController.getTurnController().getActivePlayer());
        activePlayer = player4;

        //fourth player's seventh turn
        //player4 places a card
        toPlace = game.getPlayerByName(activePlayer).getBoard().getHand().get(2);
        correct = new PlaceCardMessage(activePlayer, -1, -1, toPlace);
        gameController.onMessageReceived(correct);
        assertEquals(toPlace, game.getPlayerByName(activePlayer).getBoard().getCard(-1, -1));

        //player4 draws a resource card from the pile
        resource = new PickResourceCardMessage(activePlayer, 2);
        resourceCard = game.getPlayerByName(activePlayer).getBoard().getDeck().getResourceCards().getFirst();
        gameController.onMessageReceived(resource);
        assertEquals(resourceCard, game.getPlayerByName(activePlayer).getBoard().getHand().get(2));

        //player4 end his turn
        EndFourthTurn = new EndTurnMessage(activePlayer);
        gameController.onMessageReceived(EndFourthTurn);
        assertNotEquals(player4, gameController.getTurnController().getActivePlayer());
        assertEquals(player1, gameController.getTurnController().getActivePlayer());
        activePlayer = player1;

        //start of the eighth turn
        //player1 places a gold card
        toPlace = game.getPlayerByName(activePlayer).getBoard().getHand().getFirst();
        correct = new PlaceCardMessage(activePlayer, 2, 6, toPlace);
        gameController.onMessageReceived(correct);
        assertEquals(toPlace, game.getPlayerByName(activePlayer).getBoard().getCard(2, 6));

        //player1 draws a gold card from the visible gold card
        gold = new PickGoldCardMessage(activePlayer, 1);
        goldCard = game.getPlayerByName(activePlayer).getBoard().getDeck().getVisibleGoldCards().get(1);
        gameController.onMessageReceived(gold);
        assertEquals(goldCard, game.getPlayerByName(activePlayer).getBoard().getHand().get(2));

        //player1 ends his sixth turn
        EndFirstTurn = new EndTurnMessage(activePlayer);
        gameController.onMessageReceived(EndFirstTurn);
        assertNotEquals(player1, gameController.getTurnController().getActivePlayer());
        assertEquals(player2, gameController.getTurnController().getActivePlayer());
        activePlayer = player2;

        //player2's eighth turn
        //player2 places a gold card
        toPlace = game.getPlayerByName(activePlayer).getBoard().getHand().get(2);
        correct = new PlaceCardMessage(activePlayer, 3, 1, toPlace);
        gameController.onMessageReceived(correct);
        assertEquals(toPlace, game.getPlayerByName(activePlayer).getBoard().getCard(3, 1));

        //player2 draws a gold card from the pile
        gold = new PickGoldCardMessage(activePlayer, 2);
        goldCard = game.getPlayerByName(activePlayer).getBoard().getDeck().getGoldCards().getFirst();
        gameController.onMessageReceived(gold);
        assertEquals(goldCard, game.getPlayerByName(activePlayer).getBoard().getHand().get(2));

        //player2 ends his eighth turn
        EndSecondTurn = new EndTurnMessage(activePlayer);
        gameController.onMessageReceived(EndSecondTurn);
        assertNotEquals(player2, gameController.getTurnController().getActivePlayer());
        assertEquals(player3, gameController.getTurnController().getActivePlayer());
        activePlayer = player3;

        //third player's eighth turn
        //player3 places a resource card
        toPlace = game.getPlayerByName(activePlayer).getBoard().getHand().get(1);
        correct = new PlaceCardMessage(activePlayer, -2, -2, toPlace);
        gameController.onMessageReceived(correct);
        assertEquals(toPlace, game.getPlayerByName(activePlayer).getBoard().getCard(-2, -2));

        //player3 draws a resource card from the pile
        resource = new PickResourceCardMessage(activePlayer, 2);
        resourceCard = game.getPlayerByName(activePlayer).getBoard().getDeck().getResourceCards().getFirst();
        gameController.onMessageReceived(resource);
        assertEquals(resourceCard, game.getPlayerByName(activePlayer).getBoard().getHand().get(2));

        //player3 ends his turn
        EndThirdTurn = new EndTurnMessage(activePlayer);
        gameController.onMessageReceived(EndThirdTurn);
        assertNotEquals(player3, gameController.getTurnController().getActivePlayer());
        assertEquals(player4, gameController.getTurnController().getActivePlayer());
        activePlayer = player4;

        //fourth player's eighth turn
        //player4 places a card
        toPlace = game.getPlayerByName(activePlayer).getBoard().getHand().get(2);
        correct = new PlaceCardMessage(activePlayer, -1, 1, toPlace);
        gameController.onMessageReceived(correct);
        assertEquals(toPlace, game.getPlayerByName(activePlayer).getBoard().getCard(-1, 1));

        //player4 draws a resource card from the visible resourceCards
        resource = new PickResourceCardMessage(activePlayer, 0);
        resourceCard = game.getPlayerByName(activePlayer).getBoard().getDeck().getVisibleResourceCards().getFirst();
        gameController.onMessageReceived(resource);
        assertEquals(resourceCard, game.getPlayerByName(activePlayer).getBoard().getHand().get(2));

        //player4 end his turn
        EndFourthTurn = new EndTurnMessage(activePlayer);
        gameController.onMessageReceived(EndFourthTurn);
        assertNotEquals(player4, gameController.getTurnController().getActivePlayer());
        assertEquals(player1, gameController.getTurnController().getActivePlayer());
        activePlayer = player1;

        //start of the ninth turn
        //player1 places a resource card
        toPlace = game.getPlayerByName(activePlayer).getBoard().getHand().get(1);
        correct = new PlaceCardMessage(activePlayer, -2, 2, toPlace);
        gameController.onMessageReceived(correct);
        assertEquals(toPlace, game.getPlayerByName(activePlayer).getBoard().getCard(-2, 2));

        //player1 draws a gold card from the pile
        gold = new PickGoldCardMessage(activePlayer, 2);
        goldCard = game.getPlayerByName(activePlayer).getBoard().getDeck().getGoldCards().getFirst();
        gameController.onMessageReceived(gold);
        assertEquals(goldCard, game.getPlayerByName(activePlayer).getBoard().getHand().get(2));

        //player1 ends his ninth turn
        EndFirstTurn = new EndTurnMessage(activePlayer);
        gameController.onMessageReceived(EndFirstTurn);
        assertNotEquals(player1, gameController.getTurnController().getActivePlayer());
        assertEquals(player2, gameController.getTurnController().getActivePlayer());
        activePlayer = player2;

        //player2's ninth turn
        //player2 places a gold card
        toPlace = game.getPlayerByName(activePlayer).getBoard().getHand().get(2);
        correct = new PlaceCardMessage(activePlayer, -1, 1, toPlace);
        gameController.onMessageReceived(correct);
        assertEquals(toPlace, game.getPlayerByName(activePlayer).getBoard().getCard(-1, 1));

        //player2 draws a gold card from the pile
        gold = new PickGoldCardMessage(activePlayer, 2);
        goldCard = game.getPlayerByName(activePlayer).getBoard().getDeck().getGoldCards().getFirst();
        gameController.onMessageReceived(gold);
        assertEquals(goldCard, game.getPlayerByName(activePlayer).getBoard().getHand().get(2));

        //player2 ends his eighth turn
        EndSecondTurn = new EndTurnMessage(activePlayer);
        gameController.onMessageReceived(EndSecondTurn);
        assertNotEquals(player2, gameController.getTurnController().getActivePlayer());
        assertEquals(player3, gameController.getTurnController().getActivePlayer());
        activePlayer = player3;

        //third player's ninth turn
        //player3 places a resource card
        toPlace = game.getPlayerByName(activePlayer).getBoard().getHand().get(2);
        correct = new PlaceCardMessage(activePlayer, -1, -3, toPlace);
        gameController.onMessageReceived(correct);
        assertEquals(toPlace, game.getPlayerByName(activePlayer).getBoard().getCard(-1, -3));

        //player3 draws a gold card from the pile
        gold = new PickGoldCardMessage(activePlayer, 2);
        goldCard = game.getPlayerByName(activePlayer).getBoard().getDeck().getGoldCards().getFirst();
        gameController.onMessageReceived(gold);
        assertEquals(goldCard, game.getPlayerByName(activePlayer).getBoard().getHand().get(2));

        //player3 ends his turn
        EndThirdTurn = new EndTurnMessage(activePlayer);
        gameController.onMessageReceived(EndThirdTurn);
        assertNotEquals(player3, gameController.getTurnController().getActivePlayer());
        assertEquals(player4, gameController.getTurnController().getActivePlayer());
        activePlayer = player4;

        //fourth player's ninth turn
        //player4 places a gold card
        toPlace = game.getPlayerByName(activePlayer).getBoard().getHand().get(1);
        correct = new PlaceCardMessage(activePlayer, -1, -3, toPlace);
        gameController.onMessageReceived(correct);
        assertEquals(toPlace, game.getPlayerByName(activePlayer).getBoard().getCard(-1, -3));

        //player4 draws a gold card from the pile
        gold = new PickGoldCardMessage(activePlayer, 2);
        goldCard = game.getPlayerByName(activePlayer).getBoard().getDeck().getGoldCards().getFirst();
        gameController.onMessageReceived(gold);
        assertEquals(goldCard, game.getPlayerByName(activePlayer).getBoard().getHand().get(2));

        //player4 end his turn
        EndFourthTurn = new EndTurnMessage(activePlayer);
        gameController.onMessageReceived(EndFourthTurn);
        assertNotEquals(player4, gameController.getTurnController().getActivePlayer());
        assertEquals(player1, gameController.getTurnController().getActivePlayer());
        activePlayer = player1;

        //start of the 10th turn
        //player1 places a resource card
        toPlace = game.getPlayerByName(activePlayer).getBoard().getHand().getFirst();
        correct = new PlaceCardMessage(activePlayer, 1, 7, toPlace);
        gameController.onMessageReceived(correct);
        assertEquals(toPlace, game.getPlayerByName(activePlayer).getBoard().getCard(1, 7));

        //player1 draws a resource card from the pile
        resource = new PickResourceCardMessage(activePlayer, 2);
        resourceCard = game.getPlayerByName(activePlayer).getBoard().getDeck().getResourceCards().getFirst();
        gameController.onMessageReceived(resource);
        assertEquals(resourceCard, game.getPlayerByName(activePlayer).getBoard().getHand().get(2));

        //player1 ends his ninth turn
        EndFirstTurn = new EndTurnMessage(activePlayer);
        gameController.onMessageReceived(EndFirstTurn);
        assertNotEquals(player1, gameController.getTurnController().getActivePlayer());
        assertEquals(player2, gameController.getTurnController().getActivePlayer());
        activePlayer = player2;

        //player2's ninth turn
        //player2 places a gold card
        toPlace = game.getPlayerByName(activePlayer).getBoard().getHand().get(2);
        correct = new PlaceCardMessage(activePlayer, 5, 5, toPlace);
        gameController.onMessageReceived(correct);
        assertEquals(toPlace, game.getPlayerByName(activePlayer).getBoard().getCard(5, 5));

        //player2 draws a gold card from the pile
        gold = new PickGoldCardMessage(activePlayer, 2);
        goldCard = game.getPlayerByName(activePlayer).getBoard().getDeck().getGoldCards().getFirst();
        gameController.onMessageReceived(gold);
        assertEquals(goldCard, game.getPlayerByName(activePlayer).getBoard().getHand().get(2));

        //player2 ends his eighth turn
        EndSecondTurn = new EndTurnMessage(activePlayer);
        gameController.onMessageReceived(EndSecondTurn);
        assertNotEquals(player2, gameController.getTurnController().getActivePlayer());
        assertEquals(player3, gameController.getTurnController().getActivePlayer());
        activePlayer = player3;

        //third player's 10th turn
        //player3 places a gold card
        toPlace = game.getPlayerByName(activePlayer).getBoard().getHand().get(2);
        correct = new PlaceCardMessage(activePlayer, 2, 4, toPlace);
        gameController.onMessageReceived(correct);
        assertEquals(toPlace, game.getPlayerByName(activePlayer).getBoard().getCard(2, 4));

        //player3 draws a gold card from the pile
        gold = new PickGoldCardMessage(activePlayer, 2);
        goldCard = game.getPlayerByName(activePlayer).getBoard().getDeck().getGoldCards().getFirst();
        gameController.onMessageReceived(gold);
        assertEquals(goldCard, game.getPlayerByName(activePlayer).getBoard().getHand().get(2));

        //player3 ends his turn
        EndThirdTurn = new EndTurnMessage(activePlayer);
        gameController.onMessageReceived(EndThirdTurn);
        assertNotEquals(player3, gameController.getTurnController().getActivePlayer());
        assertEquals(player4, gameController.getTurnController().getActivePlayer());
        activePlayer = player4;

        //fourth player's 10th turn
        //player4 places a gold card
        toPlace = game.getPlayerByName(activePlayer).getBoard().getHand().get(2);
        correct = new PlaceCardMessage(activePlayer, 0, -4, toPlace);
        gameController.onMessageReceived(correct);
        assertEquals(toPlace, game.getPlayerByName(activePlayer).getBoard().getCard(0, -4));

        //player4 draws a gold card from the pile
        gold = new PickGoldCardMessage(activePlayer, 2);
        goldCard = game.getPlayerByName(activePlayer).getBoard().getDeck().getGoldCards().getFirst();
        gameController.onMessageReceived(gold);
        assertEquals(goldCard, game.getPlayerByName(activePlayer).getBoard().getHand().get(2));

        //player4 end his turn
        EndFourthTurn = new EndTurnMessage(activePlayer);
        gameController.onMessageReceived(EndFourthTurn);
        assertNotEquals(player4, gameController.getTurnController().getActivePlayer());
        assertEquals(player1, gameController.getTurnController().getActivePlayer());
        activePlayer = player1;

        //start of the 11th turn
        //player1 places a resource card
        toPlace = game.getPlayerByName(activePlayer).getBoard().getHand().get(2);
        correct = new PlaceCardMessage(activePlayer, 0, 6, toPlace);
        gameController.onMessageReceived(correct);
        assertEquals(toPlace, game.getPlayerByName(activePlayer).getBoard().getCard(0, 6));

        //player1 draws a resource card from the pile
        resource = new PickResourceCardMessage(activePlayer, 2);
        resourceCard = game.getPlayerByName(activePlayer).getBoard().getDeck().getResourceCards().getFirst();
        gameController.onMessageReceived(resource);
        assertEquals(resourceCard, game.getPlayerByName(activePlayer).getBoard().getHand().get(2));

        //player1 ends his 11th turn
        EndFirstTurn = new EndTurnMessage(activePlayer);
        gameController.onMessageReceived(EndFirstTurn);
        assertNotEquals(player1, gameController.getTurnController().getActivePlayer());
        assertEquals(player2, gameController.getTurnController().getActivePlayer());
        activePlayer = player2;

        //player2's 11th turn
        //player2 places a gold card
        toPlace = game.getPlayerByName(activePlayer).getBoard().getHand().get(1);
        correct = new PlaceCardMessage(activePlayer, -2, 2, toPlace);
        gameController.onMessageReceived(correct);
        assertEquals(toPlace, game.getPlayerByName(activePlayer).getBoard().getCard(-2, 2));

        //player2 draws a visible gold card
        gold = new PickGoldCardMessage(activePlayer, 1);
        goldCard = game.getPlayerByName(activePlayer).getBoard().getDeck().getVisibleGoldCards().get(1);
        gameController.onMessageReceived(gold);
        assertEquals(goldCard, game.getPlayerByName(activePlayer).getBoard().getHand().get(2));

        //player2 ends his 11th turn
        EndSecondTurn = new EndTurnMessage(activePlayer);
        gameController.onMessageReceived(EndSecondTurn);
        assertNotEquals(player2, gameController.getTurnController().getActivePlayer());
        assertEquals(player3, gameController.getTurnController().getActivePlayer());
        activePlayer = player3;

        //third player's 11th turn
        //player3 places a resource card
        toPlace = game.getPlayerByName(activePlayer).getBoard().getHand().get(1);
        correct = new PlaceCardMessage(activePlayer, -1, 3, toPlace);
        gameController.onMessageReceived(correct);
        assertEquals(toPlace, game.getPlayerByName(activePlayer).getBoard().getCard(-1, 3));

        //player3 draws a visible resource card
        resource = new PickResourceCardMessage(activePlayer, 0);
        resourceCard = game.getPlayerByName(activePlayer).getBoard().getDeck().getVisibleResourceCards().getFirst();
        gameController.onMessageReceived(resource);
        assertEquals(resourceCard, game.getPlayerByName(activePlayer).getBoard().getHand().get(2));

        //player3 ends his turn
        EndThirdTurn = new EndTurnMessage(activePlayer);
        gameController.onMessageReceived(EndThirdTurn);
        assertNotEquals(player3, gameController.getTurnController().getActivePlayer());
        assertEquals(player4, gameController.getTurnController().getActivePlayer());
        activePlayer = player4;

        //fourth player's 11th turn
        //player4 places a gold card
        toPlace = game.getPlayerByName(activePlayer).getBoard().getHand().get(2);
        correct = new PlaceCardMessage(activePlayer, -2, 0, toPlace);
        gameController.onMessageReceived(correct);
        assertEquals(toPlace, game.getPlayerByName(activePlayer).getBoard().getCard(-2, 0));

        //player4 draws a gold card from the pile
        gold = new PickGoldCardMessage(activePlayer, 2);
        goldCard = game.getPlayerByName(activePlayer).getBoard().getDeck().getGoldCards().getFirst();
        gameController.onMessageReceived(gold);
        assertEquals(goldCard, game.getPlayerByName(activePlayer).getBoard().getHand().get(2));

        //player4 end his turn
        EndFourthTurn = new EndTurnMessage(activePlayer);
        gameController.onMessageReceived(EndFourthTurn);
        assertNotEquals(player4, gameController.getTurnController().getActivePlayer());
        assertEquals(player1, gameController.getTurnController().getActivePlayer());
        activePlayer = player1;

        //start of the 11th turn, in this turn the second and fourth player will reach at least 20 points
        //Here begins the end game state beacuse in this turn some player reaches over 20 points

        //player1 flips a gold card and then places it
        Card toFlip = game.getPlayerByName(activePlayer).getBoard().getHand().getFirst();
        FlipCardMessage flipCardMessage = new FlipCardMessage(activePlayer, toFlip);
        gameController.onMessageReceived(flipCardMessage);
        toPlace = game.getPlayerByName(activePlayer).getBoard().getHand().getFirst();
        correct = new PlaceCardMessage(activePlayer, -3, 3, toPlace);
        gameController.onMessageReceived(correct);
        assertEquals(toPlace, game.getPlayerByName(activePlayer).getBoard().getCard(-3, 3));

        //player1 draws a resource card from the pile
        resource = new PickResourceCardMessage(activePlayer, 2);
        resourceCard = game.getPlayerByName(activePlayer).getBoard().getDeck().getResourceCards().getFirst();
        gameController.onMessageReceived(resource);
        assertEquals(resourceCard, game.getPlayerByName(activePlayer).getBoard().getHand().get(2));

        //player1 ends his 11th turn
        EndFirstTurn = new EndTurnMessage(activePlayer);
        gameController.onMessageReceived(EndFirstTurn);
        assertNotEquals(player1, gameController.getTurnController().getActivePlayer());
        assertEquals(player2, gameController.getTurnController().getActivePlayer());
        activePlayer = player2;

        //player2's 12th turn, in this turn he reaches 21 points
        //player2 places a gold card
        toPlace = game.getPlayerByName(activePlayer).getBoard().getHand().get(2);
        correct = new PlaceCardMessage(activePlayer, 2, 0, toPlace);
        gameController.onMessageReceived(correct);
        assertEquals(toPlace, game.getPlayerByName(activePlayer).getBoard().getCard(2, 0));

        //player2 draws a visible gold card
        gold = new PickGoldCardMessage(activePlayer, 1);
        goldCard = game.getPlayerByName(activePlayer).getBoard().getDeck().getVisibleGoldCards().get(1);
        gameController.onMessageReceived(gold);
        assertEquals(goldCard, game.getPlayerByName(activePlayer).getBoard().getHand().get(2));

        //player2 ends his 12th turn
        EndSecondTurn = new EndTurnMessage(activePlayer);
        gameController.onMessageReceived(EndSecondTurn);
        assertNotEquals(player2, gameController.getTurnController().getActivePlayer());
        assertEquals(player3, gameController.getTurnController().getActivePlayer());
        activePlayer = player3;

        //third player's 12th turn
        //player3 places a resource card
        toPlace = game.getPlayerByName(activePlayer).getBoard().getHand().get(2);
        correct = new PlaceCardMessage(activePlayer, -2, 4, toPlace);
        gameController.onMessageReceived(correct);
        assertEquals(toPlace, game.getPlayerByName(activePlayer).getBoard().getCard(-2, 4));

        //player3 draws a visible resource card
        resource = new PickResourceCardMessage(activePlayer, 0);
        resourceCard = game.getPlayerByName(activePlayer).getBoard().getDeck().getVisibleResourceCards().getFirst();
        gameController.onMessageReceived(resource);
        assertEquals(resourceCard, game.getPlayerByName(activePlayer).getBoard().getHand().get(2));

        //player3 ends his turn
        EndThirdTurn = new EndTurnMessage(activePlayer);
        gameController.onMessageReceived(EndThirdTurn);
        assertNotEquals(player3, gameController.getTurnController().getActivePlayer());
        assertEquals(player4, gameController.getTurnController().getActivePlayer());
        activePlayer = player4;

        //fourth player's 11th turn, in this turn he reaches 20 points
        //player4 places a gold card
        toPlace = game.getPlayerByName(activePlayer).getBoard().getHand().get(2);
        correct = new PlaceCardMessage(activePlayer, 4, 0, toPlace);
        gameController.onMessageReceived(correct);
        assertEquals(toPlace, game.getPlayerByName(activePlayer).getBoard().getCard(4, 0));

        //player4 draws a gold card from the pile
        gold = new PickGoldCardMessage(activePlayer, 2);
        goldCard = game.getPlayerByName(activePlayer).getBoard().getDeck().getGoldCards().getFirst();
        gameController.onMessageReceived(gold);
        assertEquals(goldCard, game.getPlayerByName(activePlayer).getBoard().getHand().get(2));

        //player4 end his turn
        EndFourthTurn = new EndTurnMessage(activePlayer);
        gameController.onMessageReceived(EndFourthTurn);
        assertNotEquals(player4, gameController.getTurnController().getActivePlayer());
        assertEquals(player1, gameController.getTurnController().getActivePlayer());
        activePlayer = player1;

        //last turn for the first player
        //player1 flips a gold card then places it
        toFlip = game.getPlayerByName(activePlayer).getBoard().getHand().getFirst();
        flipCardMessage = new FlipCardMessage(activePlayer, toFlip);
        gameController.onMessageReceived(flipCardMessage);
        toPlace = game.getPlayerByName(activePlayer).getBoard().getHand().getFirst();
        correct = new PlaceCardMessage(activePlayer, -4, 4, toPlace);
        gameController.onMessageReceived(correct);
        assertEquals(toPlace, game.getPlayerByName(activePlayer).getBoard().getCard(-4, 4));

        //player1 draws a resource card from the pile
        resource = new PickResourceCardMessage(activePlayer, 2);
        resourceCard = game.getPlayerByName(activePlayer).getBoard().getDeck().getResourceCards().getFirst();
        gameController.onMessageReceived(resource);
        assertEquals(resourceCard, game.getPlayerByName(activePlayer).getBoard().getHand().get(2));

        //player1 ends his last turn
        EndFirstTurn = new EndTurnMessage(activePlayer);
        gameController.onMessageReceived(EndFirstTurn);
        assertNotEquals(player1, gameController.getTurnController().getActivePlayer());
        assertEquals(player2, gameController.getTurnController().getActivePlayer());
        activePlayer = player2;

        //last turn for the second player
        //player2 places a gold card
        toPlace = game.getPlayerByName(activePlayer).getBoard().getHand().get(1);
        correct = new PlaceCardMessage(activePlayer, -1, 3, toPlace);
        gameController.onMessageReceived(correct);
        assertEquals(toPlace, game.getPlayerByName(activePlayer).getBoard().getCard(-1, 3));

        //player2 draws a visible gold card
        gold = new PickGoldCardMessage(activePlayer, 1);
        goldCard = game.getPlayerByName(activePlayer).getBoard().getDeck().getVisibleGoldCards().get(1);
        gameController.onMessageReceived(gold);
        assertEquals(goldCard, game.getPlayerByName(activePlayer).getBoard().getHand().get(2));

        //player2 ends his 12th turn
        EndSecondTurn = new EndTurnMessage(activePlayer);
        gameController.onMessageReceived(EndSecondTurn);
        assertNotEquals(player2, gameController.getTurnController().getActivePlayer());
        assertEquals(player3, gameController.getTurnController().getActivePlayer());
        activePlayer = player3;

        //third player's last turn
        //player3 places a resource card
        toPlace = game.getPlayerByName(activePlayer).getBoard().getHand().get(2);
        correct = new PlaceCardMessage(activePlayer, 1, -1, toPlace);
        gameController.onMessageReceived(correct);
        assertEquals(toPlace, game.getPlayerByName(activePlayer).getBoard().getCard(1, -1));

        //player3 draws a visible resource card
        resource = new PickResourceCardMessage(activePlayer, 0);
        resourceCard = game.getPlayerByName(activePlayer).getBoard().getDeck().getVisibleResourceCards().getFirst();
        gameController.onMessageReceived(resource);
        assertEquals(resourceCard, game.getPlayerByName(activePlayer).getBoard().getHand().get(2));

        //player3 ends his turn
        EndThirdTurn = new EndTurnMessage(activePlayer);
        gameController.onMessageReceived(EndThirdTurn);
        assertNotEquals(player3, gameController.getTurnController().getActivePlayer());
        assertEquals(player4, gameController.getTurnController().getActivePlayer());
        activePlayer = player4;

        //last turn for the fourth player
        //player4 places a gold card
        toPlace = game.getPlayerByName(activePlayer).getBoard().getHand().get(2);
        correct = new PlaceCardMessage(activePlayer, 5, -1, toPlace);
        gameController.onMessageReceived(correct);
        assertEquals(toPlace, game.getPlayerByName(activePlayer).getBoard().getCard(5, -1));

        //player4 draws a gold card from the pile
        gold = new PickGoldCardMessage(activePlayer, 2);
        goldCard = game.getPlayerByName(activePlayer).getBoard().getDeck().getGoldCards().getFirst();
        gameController.onMessageReceived(gold);
        assertEquals(goldCard, game.getPlayerByName(activePlayer).getBoard().getHand().get(2));

        //player4 end his turn
        EndFourthTurn = new EndTurnMessage(activePlayer);
        gameController.onMessageReceived(EndFourthTurn);
        assertNotEquals(player4, gameController.getTurnController().getActivePlayer());
        assertEquals(player1, gameController.getTurnController().getActivePlayer());

        //TODO fix the points update method in board


    }


}
