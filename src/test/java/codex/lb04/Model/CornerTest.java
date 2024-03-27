package codex.lb04.Model;

import codex.lb04.Model.Enumerations.ResourceType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CornerTest {

    private Corner corner;
    private Card cover;

    @BeforeEach
    void setUp() {
        this.corner = new Corner(ResourceType.INSECT);
        this.cover = new Card();
    }

    @AfterEach
    void tearDown() {
        corner = null;
        cover = null;
    }

    @Test
    void getCovered() {
        corner.getCovered(cover);
        assertEquals(cover , corner.getCover() );
    }

    @Test
    void isCovered() {
        corner.getCovered(cover);
        assertTrue(corner.isCovered());
    }

    @Test
    void getCover() {
        corner.getCovered(cover);
        assertEquals(cover, corner.getCover());
    }

    @Test
    void getResource() {
        assertEquals(corner.getResource() , ResourceType.INSECT);
    }
}