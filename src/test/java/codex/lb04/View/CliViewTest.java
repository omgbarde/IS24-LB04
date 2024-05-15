package codex.lb04.View;

import codex.lb04.Model.Deck;
import codex.lb04.Model.GoldCard;
import codex.lb04.View.Cli.CardRenderer;
import codex.lb04.View.Cli.CliView;
import javafx.scene.text.Text;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Array;


public class CliViewTest {
    private CliView cliView;
    private Deck deck;


    @Before
    public void setUp() {
        cliView = new CliView();
    }

    @After
    public void tearDown() {
        cliView = null;
    }

    @Test
    public void testDrawHelloScene() {
        cliView.drawHelloScene();
    }

    @Test
    public void testDrawLoginScene() {
        cliView.drawLoginScene();
    }

    @Test
    public void testDrawCreateGameScene() {
        cliView.drawCreateGameScene();
    }

    @Test
    public void testDrawLobbyScene() {
        cliView.drawLobbyScene();
    }

    @Test
    public void testDrawBoardScene() {
        cliView.drawBoardScene();
    }
}

    /*@Test
    public void drawPlayedCards() {
        StringBuilder sb = new StringBuilder();
        sb.append("⬛----------------⬛\n");
        sb.append("|                  |\n");
        sb.append("|                  |\n");
        sb.append("|                  |\n");
        sb.append("⬛----------------⬛\n");
        //System.out.println(sb.toString());
       CardRenderer cardRenderer = new CardRenderer();
        deck = Deck.getInstance();
        String[][] array = {
                {CardRenderer.cardToString(deck.getGoldCards().get(0)),sb.toString(),sb.toString()},
                {sb.toString(),CardRenderer.cardToString(deck.getGoldCards().get(1)),sb.toString()},
                {sb.toString(),sb.toString(),CardRenderer.cardToString(deck.getGoldCards().get(2))}
        };
                for (int i = 0; i < array.length; i++) {
                    for (int j = 0; j < array[i].length; j++) {
                        System.out.print(array[i][j] + " ");
                    }
                    System.out.println();
                }
            }
        }*/
