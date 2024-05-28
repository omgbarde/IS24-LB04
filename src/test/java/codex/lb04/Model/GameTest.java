package codex.lb04.Model;

import codex.lb04.Model.Enumerations.Color;
import codex.lb04.Model.Enumerations.ResourceType;
import codex.lb04.Observer.GameObserver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class GameTest {
    private GameObserver observer;
    private Game game;
    private final Corner corner = new Corner(ResourceType.ANIMAL);
    private final Face face = new Face(corner, corner, corner, corner, ResourceType.INSECT);
    private final ResourceCard card = new ResourceCard(Color.BLUE, face, face, 0, 1);
    private Player player;
    private Player player1;
    private final ArrayList<Player> players = new ArrayList<Player>();
    private Board board;
    private Deck deck;
    private final Corner CoveredCorner = new Corner(true);
    private final Face BlankFace = new Face(CoveredCorner, CoveredCorner, CoveredCorner, CoveredCorner);
    private ObjectiveCard cardOb = new ObjectiveCard(Color.BLUE, BlankFace, BlankFace, 2, 89);
    private ObjectiveCard cardOb2 = new ObjectiveCard(Color.BLUE, BlankFace, BlankFace, 2, 89);
    private final ArrayList<ObjectiveCard> objectiveCards = new ArrayList<>();
    private final ArrayList<Card> cards = new ArrayList<>();
    private final ObjectiveCard SecretobjectiveCard = new ObjectiveCard(Color.BLUE, face, face, 2, 87);
    ObjectiveCard threeMushroom = new ObjectiveCard(Color.RED, BlankFace, BlankFace, 2, 95);
    ObjectiveCard objective_card_96 = new ObjectiveCard(Color.GREEN, BlankFace, BlankFace, 2, 96);


    @Before
    public void setUp() {
        this.observer = new GameObserver();
        this.player = new Player("test");
        this.player1 = new Player("test1");
        this.players.add(player);
        this.players.add(player1);
        this.board = new Board();
        this.deck = Deck.getInstance();
        this.game = Game.getInstance();
        this.game.addObserver(observer);
        this.game.setDeck();
        this.card.setShowingFront(true);
        this.cardOb = cardOb;
        this.cardOb2 = cardOb2;
        objectiveCards.add(cardOb);
        objectiveCards.add(cardOb2);
        this.game.addPlayerToLobby(player.getUsername());
        this.game.addPlayerToLobby(player1.getUsername());
        this.game.addPlayer(player);
        this.game.addPlayer(player1);
    }

    @After
    public void tearDown() {
        this.observer = null;
        this.player = null;
        this.player1 = null;
        this.board = null;
        this.game.resetInstance();

    }


    @Test
    public void getPlayers() {
        assertEquals(players, game.getPlayers());
    }

    @Test
    public void getDeck() {
        assertEquals(deck, game.getDeck());
    }

    @Test
    public void placeCard() {
        board.setHasPlacedACard(false);
        board.placeCard(card, 0, 0);
        assertEquals(card, board.getCard(0, 0));
    }

    @Test
    public void addPlayer() {
        game.addPlayer(player);
        assertEquals(player, game.getPlayers().getLast());
    }

    @Test
    public void getInstance() {
        assertNotNull(Game.getInstance());
    }

    @Test
    public void drawResourceCard() {
        game.drawResourceCard("test", 0);
        assertNotNull(player.getBoard().getHand());
    }

    @Test
    public void drawGoldCard() {
        game.drawGoldCard("test", 0);
        assertNotNull(player.getBoard().getHand());
    }

    @Test
    public void getInitialCard() {
        game.getPlayerByName("test").getBoard().setInitialCard();
        assertNotNull(game.getInitialCard("test"));
    }

    @Test
    public void setInitialCardForAllPlayers() {
        game.setInitialCardForAllPlayers();
        for (Player p : game.getPlayers()) {
            assertNotNull(p.getBoard().getInitialCard());
        }
    }

    @Test
    public void setCommonObjectivesForallPlayers() {
        game.setCommonObjectivesForallPlayers();
        for (Player p : players) {
            assertEquals(p.getBoard().getCommonObjectives(), game.getCommonObjectives());
        }
    }

    @Test
    public void getCommonObjectives() {
        assertNotNull(game.getCommonObjectives());
    }


    @Test
    public void setSecretObjectives() {
        ObjectiveCard card1 = player.getBoard().getSecretObjectiveToPick().get(0);
        game.setSecretObjectives("test", 0);
        assertEquals(card1, player.getBoard().getSecretObjective());
    }

    @Test
    public void getLobby() {
        assertNotNull(game.getLobby());
    }

    @Test
    public void getWinnersTest() {
        //sets up a dummy condition where the players have the same score and same amount of objectives
        game.getPlayers().get(0).getBoard().setPoints(21, 21);
        game.getPlayers().get(1).getBoard().setPoints(21, 21);

        game.getPlayers().get(0).getBoard().setCommonObjectives(new ArrayList<>(List.of(threeMushroom, objective_card_96)));
        game.getPlayers().get(1).getBoard().setCommonObjectives(new ArrayList<>(List.of(threeMushroom, objective_card_96)));

        game.getPlayers().get(0).getBoard().setSecretObjective(1);
        game.getPlayers().get(1).getBoard().setSecretObjective(1);

        game.getPlayers().get(0).getBoard().setMushroom(3);
        game.getPlayers().get(1).getBoard().setMushroom(3);

        ArrayList<String> names = new ArrayList<>();
        for (Player p : game.getPlayers()) {
            names.add(p.getUsername());
        }

        assertEquals(names, game.getWinners());

    }

}