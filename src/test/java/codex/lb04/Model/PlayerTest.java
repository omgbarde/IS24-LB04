package codex.lb04.Model;


import java.util.ArrayList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    private Player player;
    private Board board;

    @BeforeEach
    void setUp() {
        this.board = new Board();
        this.player = new Player("test", board);
    }

    @AfterEach
    void tearDown() {
        this.player = null;
        this.board = null;
    }

    @Test
    void getUsername() {
        assertEquals("test", player.getUsername());
    }

    @Test
    void getBoard() {
        assertNotNull(player.getBoard());
    }
}