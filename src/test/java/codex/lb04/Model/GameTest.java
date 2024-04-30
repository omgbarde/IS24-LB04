package codex.lb04.Model;

import codex.lb04.Model.Enumerations.Color;
import codex.lb04.Model.Enumerations.ResourceType;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class GameTest {

    private Game game;
    private Corner corner = new Corner(ResourceType.ANIMAL);
    private Face face = new Face(corner, corner, corner, corner, ResourceType.INSECT);
    private Card card = new Card(Color.BLUE, face, face);
    private Player player;
    private ArrayList<Player> players = new ArrayList<Player>();
    private Board board;
    private Deck deck;
    private ArrayList<ObjectiveCard> objectiveCards = new ArrayList<>();
    private ArrayList<Card> cards = new ArrayList<>();
    private ObjectiveCard SecretobjectiveCard = new ObjectiveCard(Color.BLUE, face, face, 2, 87);


    @Before
    public void setUp() {
        this.player = new Player("test");
        this.players.add(player);
        this.board = new Board();
        this.deck = Deck.getInstance();
        this.game = Game.getInstance();
        this.game.addPlayer(player);
    }

    @After
    public void tearDown() {
        this.player = null;
        this.board = null;
        this.game.resetInstance();

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
        assertNotNull(Game.getInstance());
    }

}