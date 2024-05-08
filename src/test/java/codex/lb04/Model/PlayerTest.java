package codex.lb04.Model;


import codex.lb04.Observer.GameObserver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class PlayerTest {
    private Player player;
    private Board board;

    @Before
    public void setUp() {
        this.board = new Board();
        this.board.getDeck().addObserver(new GameObserver());
        this.player = new Player("test");
    }

    @After
    public void tearDown() {
        this.player = null;
        this.board = null;
    }

    @Test
    public void getUsername() {
        assertEquals("test", player.getUsername());
    }

    @Test
    public void getBoard() {
        assertNotNull(player.getBoard());
    }
}