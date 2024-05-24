package codex.lb04.View.Cli;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class CliViewTest {
    private CliView cliView;


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
