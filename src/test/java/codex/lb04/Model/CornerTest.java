package codex.lb04.Model;

import codex.lb04.Model.Enumerations.Color;
import codex.lb04.Model.Enumerations.ResourceType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CornerTest {

    private Corner corner;
    private Card cover;
    private Face front;
    private Face back;

    @BeforeEach
    void setUp() {
        this.corner = new Corner(ResourceType.INSECT);
        this.front = new Face(corner, corner, corner, corner, ResourceType.INSECT);
        this.back = new Face(corner, corner, corner, corner, ResourceType.INSECT, ResourceType.INSECT);
        this.cover = new Card(Color.BLUE, front, back);
    }

    @AfterEach
    void tearDown() {
        corner = null;
        cover = null;
    }

    @Test
    void setCovered() {
        corner.setCovered(cover);
        assertEquals(cover , corner.getCover() );
    }

    @Test
    void isCovered() {
        corner.setCovered(cover);
        assertTrue(corner.isCovered());
    }

    @Test
    void getCover() {
        corner.setCovered(cover);
        assertEquals(cover, corner.getCover());
    }

    @Test
    void getResource() {
        assertEquals(corner.getResource() , ResourceType.INSECT);
    }
}