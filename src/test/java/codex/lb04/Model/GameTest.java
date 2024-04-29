package codex.lb04.Model;

import codex.lb04.Model.Enumerations.Color;
import codex.lb04.Model.Enumerations.ResourceType;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class GameTest {

    private static Game game;
    private static Corner corner = new Corner(ResourceType.ANIMAL);
    private static Face face = new Face(corner, corner, corner, corner, ResourceType.INSECT);
    private static Card card = new Card(Color.BLUE, face, face);
    private static Player player;
    private static ArrayList<Player> players = new ArrayList<Player>();
    private static Board board;
    private static Deck deck;
    private static ArrayList<ObjectiveCard> objectiveCards = new ArrayList<>();
    private static ArrayList<Card> cards = new ArrayList<>();
    private static ObjectiveCard SecretobjectiveCard = new ObjectiveCard(Color.BLUE, face, face, 2, 87);


    @Before
    public void setUp() {
        player = new Player("test");
        players.add(player);
        board = new Board();
        deck = Deck.getInstance();
        game = Game.getInstance();
        game.addPlayer(player);
    }

    @After
    public void tearDown() {
        player = null;
        board = null;
        deck = null;
        game = null;
    }


    //TODO doesn't work when starting tests with maven
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
        assertNotNull(game.getInstance());
    }

}