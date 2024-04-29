package codex.lb04.Model;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {
    private Player player;
    private Board board;

    @Before
    public void setUp() {
        this.board = new Board();
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