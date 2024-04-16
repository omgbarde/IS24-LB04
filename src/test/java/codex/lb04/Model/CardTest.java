package codex.lb04.Model;

import codex.lb04.Model.Enumerations.Color;
import codex.lb04.Model.Enumerations.ResourceType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {
    private Card card;
    private Card CardWithPoints;
    private Face front;
    private Face back;
    private Corner corner1;
    private Corner corner2;
    private Corner corner3;
    private Corner EmptyCorner;
    private Corner CoveredCorner;


    @BeforeEach
    void setUp() {
        this.corner1 = new Corner(ResourceType.ANIMAL);
        this.corner2 = new Corner(ResourceType.MUSHROOM);
        this.corner3 = new Corner(ResourceType.INSECT);
        this.EmptyCorner = new Corner(false);
        this.CoveredCorner = new Corner(true);
        this.front = new Face( corner1 , corner2 , corner3 , CoveredCorner );
        this.back = new Face( EmptyCorner , EmptyCorner , EmptyCorner , EmptyCorner , ResourceType.ANIMAL);
        this.card = new Card(Color.BLUE , front , back);
        this.CardWithPoints = new Card(Color.BLUE , front , back , 2);
    }

    @AfterEach
    void tearDown() {
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
    void flip() {
        card.flip(card);
        assertEquals(back, card.getShownFace());
    }

    @Test
    void getShownFace() {
        assertEquals(front, card.getShownFace());
    }

    @Test
    void setCoordinates() {
        card.setCoordinates(1, 2);
        assertEquals(1, card.getX());
        assertEquals(2, card.getY());
    }

    @Test
    void getFront() {
        assertEquals(front, card.getFront());
    }

    @Test
    void getBack() {
        assertEquals(back, card.getBack());
    }

    @Test
    void getColor() {
        assertEquals(Color.BLUE, card.getColor());
    }

    @Test
    void getPoints() {
        assertEquals(2 , CardWithPoints.getPoints());
    }

    @Test
    void getX() {
        card.setCoordinates(1, 2);
        assertEquals(1, card.getX());
    }

    @Test
    void getY() {
        card.setCoordinates(1, 2);
        assertEquals(2, card.getY());
    }
}