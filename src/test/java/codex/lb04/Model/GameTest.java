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

    @Test
    public void drawResourceCard(){
        game.drawResourceCard("test", 0);
        assertNotNull(player.getBoard().getHand());
    }

    @Test
    public void drawGoldCard(){
        game.drawGoldCard("test", 0);
        assertNotNull(player.getBoard().getHand());
    }

    @Test
    public void getInitialCard(){
        game.getPlayerByName("test").getBoard().setInitialCard();
        assertNotNull(game.getInitialCard("test"));
    }
    //TODO doesn't work
    @Test
    public void setInitialCardForAllPlayers(){
        game.setInitialCardForAllPlayers();
        for(Player p :players){
            assertNotNull(p.getBoard().getInitialCard());
        }
    }

    @Test
    public void setCommonObjectivesForallPlayers(){
        game.setCommonObjectivesForallPlayers();
        for(Player p :players){
            assertEquals(p.getBoard().getCommonObjectives(), game.getCommonObjectives());
        }
    }

    @Test
    public void getCommonObjectives(){
        assertNotNull(game.getCommonObjectives());
    }
    //TODO fix it, it doesn't set the secret objective to the player
    @Test
    public void setSecretObjectives(){
        Player player1 = new Player("test1");
        ObjectiveCard card1 = deck.getObjectiveCards().get(0);
        ObjectiveCard card2 = deck.getObjectiveCards().get(1);
        game.setSecretObjectives("test", 0);
        game.setSecretObjectives("test1", 0);
        assertEquals(card1,player.getBoard().getSecretObjective());
        assertEquals(card2,player1.getBoard().getSecretObjective());
    }

    @Test
    public void getLobby(){assertNotNull(game.getLobby());}


    @Test
    public void removePlayerFromLobby(){
        game.removePlayerFromLobby("test");
        assertFalse(game.getLobby().contains("test"));
    }


}