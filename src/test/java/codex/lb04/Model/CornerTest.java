package codex.lb04.Model;

import codex.lb04.Model.Enumerations.Color;
import codex.lb04.Model.Enumerations.ResourceType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CornerTest {

    private Corner corner;
    private Card cover;
    private Face front;
    private Face back;

    @Before
    public void setUp() {
        this.corner = new Corner(ResourceType.INSECT);
        this.front = new Face(corner, corner, corner, corner, ResourceType.INSECT);
        this.back = new Face(corner, corner, corner, corner, ResourceType.INSECT, ResourceType.INSECT);
        this.cover = new Card(Color.BLUE, front, back);
    }

    @After
    public void tearDown() {
        corner = null;
        cover = null;
    }

    @Test
    public void setCovered() {
        corner.setCovered(cover);
        assertEquals(cover, corner.getCover());
    }

    @Test
    public void isCovered() {
        corner.setCovered(cover);
        assertTrue(corner.isCovered());
    }

    @Test
    public void getCover() {
        corner.setCovered(cover);
        assertEquals(cover, corner.getCover());
    }

    @Test
    public void getResource() {
        assertEquals(corner.getResource(), ResourceType.INSECT);
    }
}