package codex.lb04.Model;

import codex.lb04.Model.Enumerations.Color;
import codex.lb04.Model.Enumerations.ResourceType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class CardTest {
    private Card card;
    private Card CardWithPoints;
    private Face front;
    private Face back;
    private Corner corner1;
    private Corner corner2;
    private Corner corner3;
    private Corner EmptyCorner;
    private Corner CoveredCorner;


    @Before
    public void setUp() {
        this.corner1 = new Corner(ResourceType.ANIMAL);
        this.corner2 = new Corner(ResourceType.MUSHROOM);
        this.corner3 = new Corner(ResourceType.INSECT);
        this.EmptyCorner = new Corner(false);
        this.CoveredCorner = new Corner(true);
        this.front = new Face(corner1, corner2, corner3, CoveredCorner);
        this.back = new Face(EmptyCorner, EmptyCorner, EmptyCorner, EmptyCorner, ResourceType.ANIMAL);
        this.card = new Card(Color.BLUE, front, back);
        this.CardWithPoints = new Card(Color.BLUE, front, back, 2, 0);
    }

    @After
    public void tearDown() {
        front = null;
        back = null;
        card = null;
        corner1 = null;
        corner2 = null;
        corner3 = null;
        EmptyCorner = null;
        CoveredCorner = null;
    }

    @Test
    public void flip() {
        card.flip();
        assertEquals(front, card.getShownFace());
    }

    @Test
    public void setCoordinates() {
        card.setCoordinates(1, 2);
        assertEquals((Integer) 1, card.getX());
        assertEquals((Integer) 2, card.getY());
    }

    @Test
    public void getFront() {
        assertEquals(front, card.getFront());
    }

    @Test
    public void getBack() {
        assertEquals(back, card.getBack());
    }

    @Test
    public void getColor() {
        assertEquals(Color.BLUE, card.getColor());
    }

    @Test
    public void getPoints() {
        assertEquals((Integer) 2, CardWithPoints.getPoints());
    }

    @Test
    public void getX() {
        card.setCoordinates(1, 2);
        assertEquals((Integer) 1, card.getX());
    }

    @Test
    public void getY() {
        card.setCoordinates(1, 2);
        assertEquals((Integer) 2, card.getY());
    }
}