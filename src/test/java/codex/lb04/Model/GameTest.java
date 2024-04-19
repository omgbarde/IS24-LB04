package codex.lb04.Model;

import codex.lb04.Model.Enumerations.Color;
import codex.lb04.Model.Enumerations.ResourceType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GameTest {

    private static Game game;
    private static Corner corner = new Corner(ResourceType.ANIMAL);
    private static Face face = new Face(corner , corner , corner , corner , ResourceType.INSECT);
    private static Player player;
    private static ArrayList<Player> players = new ArrayList<Player>();
    private static Board board;
    private static Deck deck;
    private static ArrayList<ObjectiveCard> objectiveCards = new ArrayList<>();
    private static ArrayList<Card> cards = new ArrayList<>();
    private static ObjectiveCard SecretobjectiveCard = new ObjectiveCard(Color.BLUE , face , face , 2, ResourceType.MUSHROOM);


    @BeforeAll
    static void setUp() {
        player = new Player("test" , objectiveCards, SecretobjectiveCard, cards);
        players.add(player);
        board = new Board();
        deck = Deck.getInstance();
        game = Game.getInstance(player , deck , board);
        game.setGameState(GameState.PLAYING);
    }

    @AfterAll
    static void tearDown() {
        player = null;
        board = null;
        deck = null;
        game = null;
    }

    @Test
    void getGameState() {
        game.setGameState(GameState.PLAYING);
        assertEquals(GameState.PLAYING, game.getGameState());
    }

    @Test
    void setGameState() {
        game.setGameState(GameState.PAUSED);
        assertEquals(GameState.PAUSED, game.getGameState());
    }

    @Test
    void getPlayer() {
        assertEquals(player, game.getPlayers().getFirst());
    }

    @Test
    void getDeck() {
        assertEquals(deck, game.getDeck());
    }

    @Test
    void getBoard() {
        assertEquals(board, game.getBoard());
    }

    @Test
    void placeCard() {
        //TODO
    }

    @Test
    void addPlayer() {
        game.addPlayer(player);
        assertEquals(player, game.getPlayers().getLast());
    }
}