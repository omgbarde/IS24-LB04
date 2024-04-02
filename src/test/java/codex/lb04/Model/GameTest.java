package codex.lb04.Model;

import codex.lb04.Model.Enumerations.ResourceType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    Game game;
    Player player;
    Board board;
    Deck deck;

    ArrayList<ObjectiveCard> objectiveCards = new ArrayList<>();
    ArrayList<Card> cards = new ArrayList<>();
    ObjectiveCard objectiveCard;


    @BeforeEach
    void setUp() {
        player = new Player("test" , objectiveCards, objectiveCard, cards);
        board = new Board();
        deck = new Deck();
        game = Game.getInstance(player , deck , board);
        game.setGameState(GameState.PLAYING);
    }

    @AfterEach
    void tearDown() {
        player = null;
        board = null;
        deck = null;
        game = null;
    }

    @Test
    void getGameState() {
        assertEquals(GameState.PLAYING, game.getGameState());
    }

    @Test
    void setGameState() {
        game.setGameState(GameState.PAUSED);
        assertEquals(GameState.PAUSED, game.getGameState());
    }

    @Test
    void getPlayer() {
        assertEquals(player, game.getPlayers());
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
}