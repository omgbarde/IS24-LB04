package codex.lb04.View;

import codex.lb04.Model.Card;
import codex.lb04.Model.Deck;
import codex.lb04.Model.Game;
import codex.lb04.View.Cli.CardRenderer;
import org.junit.Before;
import org.junit.Test;

public class CardRendererTest {
    private CardRenderer cardRenderer;
    private Game game;
    private Deck deck;
    @Before
    public void setUp(){
        cardRenderer = new CardRenderer();
        game = Game.getInstance();
        deck = Deck.getInstance();
        game.setDeck();
    }

    @Test
    public void testRenderCard(){
        for (Card c : deck.getGoldCards()) {
            System.out.println(cardRenderer.cardToString(c));
            c.flip();
            System.out.println(cardRenderer.cardToString(c));
        }
        for (Card c : deck.getResourceCards()) {
            System.out.println(cardRenderer.cardToString(c));
            c.flip();
            System.out.println(cardRenderer.cardToString(c));
        }
        for (Card c : deck.getInitialCards()) {
            System.out.println(cardRenderer.cardToString(c));
            c.flip();
            System.out.println(cardRenderer.cardToString(c));
        }
        for (Card c : deck.getObjectiveCards()) {
            System.out.println(cardRenderer.cardToString(c));
            c.flip();
            System.out.println(cardRenderer.cardToString(c));
        }
    }
}
