package codex.lb04.View.Cli;

import codex.lb04.Model.Card;
import codex.lb04.Model.Deck;
import codex.lb04.Model.Game;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CardRendererTest {
    private CardRenderer cardRenderer;
    private Game game;
    private Deck deck;

    @Before
    public void setUp() {
        cardRenderer = new CardRenderer();
        game = Game.getInstance();
        deck = Deck.getInstance();
        game.setDeck();
    }

    @After
    public void tearDown() {
        cardRenderer = null;
        game = null;
        deck = null;
    }

    @Test
    public void testPrintInGame() {
        for (Card c : deck.getGoldCards()) {
            System.out.println(cardRenderer.printInGame(c));
            c.flip();
            System.out.println(cardRenderer.printInGame(c));
        }
        for (Card c : deck.getResourceCards()) {
            System.out.println(cardRenderer.printInGame(c));
            c.flip();
            System.out.println(cardRenderer.printInGame(c));
        }
        for (Card c : deck.getInitialCards()) {
            System.out.println(cardRenderer.printInGame(c));
            c.flip();
            System.out.println(cardRenderer.printInGame(c));
        }
        for (Card c : deck.getObjectiveCards()) {
            System.out.println(cardRenderer.printInGame(c));
            c.flip();
            System.out.println(cardRenderer.printInGame(c));
        }
    }

    @Test
    public void testPrintInHand() {
        for (Card c : deck.getInitialCards()) {
            System.out.println(CardRenderer.printInHand(c));
            c.flip();
            System.out.println(CardRenderer.printInHand(c));
        }
        for (Card c : deck.getGoldCards()) {
            System.out.println(CardRenderer.printInHand(c));
            c.flip();
            System.out.println(CardRenderer.printInHand(c));
        }
        for (Card c : deck.getResourceCards()) {
            System.out.println(CardRenderer.printInHand(c));
            c.flip();
            System.out.println(CardRenderer.printInHand(c));
        }
        for (Card c : deck.getObjectiveCards()) {
            System.out.println(CardRenderer.printInHand(c));
            c.flip();
            System.out.println(CardRenderer.printInHand(c));
        }
    }

    @Test
    public void testPrintEmoji() {
        System.out.println("\uD83C\uDF44 \uD83E\uDD8A \uD83E\uDD8B \uD83C\uDF43");
    }
}
