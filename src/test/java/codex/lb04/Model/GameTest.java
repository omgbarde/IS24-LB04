package codex.lb04.Model;

import codex.lb04.Model.Enumerations.Color;
import codex.lb04.Model.Enumerations.ResourceType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;


public class GameTest {

    private Game game;
    private Corner corner = new Corner(ResourceType.ANIMAL);
    private Face face = new Face(corner, corner, corner, corner, ResourceType.INSECT);
    private Card card = new Card(Color.BLUE, face, face);
    private Player player;
    private Player player1;
    private ArrayList<Player> players = new ArrayList<Player>();
    private Board board;
    private Deck deck;
    private Corner CoveredCorner = new Corner(true);
    private Face BlankFace = new Face(CoveredCorner,CoveredCorner,CoveredCorner,CoveredCorner);
    private ObjectiveCard cardOb = new ObjectiveCard(Color.BLUE, BlankFace, BlankFace, 2, 89);
    private ObjectiveCard cardOb2 = new ObjectiveCard(Color.BLUE, BlankFace, BlankFace, 2, 89);
    private ArrayList<ObjectiveCard> objectiveCards = new ArrayList<>();
    private ArrayList<Card> cards = new ArrayList<>();
    private ObjectiveCard SecretobjectiveCard = new ObjectiveCard(Color.BLUE, face, face, 2, 87);


    @Before
    public void setUp() {
        this.player = new Player("test");
        this.player1 = new Player("test1");
        this.players.add(player);
        this.players.add(player1);
        this.board = new Board();
        this.deck = Deck.getInstance();
        this.game = Game.getInstance();
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

    @Test
    public void setInitialCardForAllPlayers(){
        game.setInitialCardForAllPlayers();
        for(Player p : game.getPlayers()){
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


    @Test
    public void setSecretObjectives(){
        ObjectiveCard card1 = deck.getObjectiveCards().getFirst();
        ObjectiveCard card2 = deck.getObjectiveCards().get(1);
        game.setSecretObjectives("test", 0);
        assertEquals(card1,player.getBoard().getSecretObjective());
        game.setSecretObjectives("test", 0); //to zero because first card was removed before by the same method
        assertEquals(card2,player.getBoard().getSecretObjective());
    }

    @Test
    public void getLobby(){assertNotNull(game.getLobby());}


    @Test
    public void removePlayerFromLobby(){
        game.removePlayerFromLobby("test");
        assertFalse(game.getLobby().contains("test"));
    }
//TODO mettere a posto il test
   /* @Test
    public void checkWinner(){
        player.getBoard().setSecretObjective(0);
        player1.getBoard().setSecretObjective(1);
        game.setCommonObjectivesForallPlayers();
        player.getBoard().setPoints(10);
        player1.getBoard().setPoints(5);
        assertTrue(game.checkWinner().contains(player.getUsername()));
    }*/


}