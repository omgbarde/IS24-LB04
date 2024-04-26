package codex.lb04.Model;
import java.util.ArrayList;
import codex.lb04.Model.Enumerations.Color;
import codex.lb04.Model.Enumerations.ResourceType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GoldCardTest {
    private GoldCard goldCard;
    private Face front;
    private Face back;
    private Corner corner1;
    private Corner EmptyCorner;
    private Corner CoveredCorner;



    @BeforeEach
    void setUp() {
        this.corner1 = new Corner(ResourceType.QUILL);
        this.EmptyCorner = new Corner(false);
        this.CoveredCorner = new Corner(true);
        this.front = new Face(EmptyCorner, CoveredCorner, EmptyCorner, corner1);
        this.back = new Face(EmptyCorner, EmptyCorner, EmptyCorner, EmptyCorner, ResourceType.MUSHROOM);
        this.goldCard = new GoldCard(Color.RED, front, back, 1, 2, 1, 0, 0, 41);

    }

    @AfterEach
    void tearDown() {
        front = null;
        back = null;
        goldCard = null;
        corner1 = null;
        EmptyCorner = null;
        CoveredCorner = null;
    }

    @Test
    void getPoints(){
        assertEquals(1, goldCard.getPoints());
    }

    @Test
    void getMushroom_needed() {
        assertEquals(2, goldCard.getMushroom_needed());
    }

    @Test
    void getAnimals_needed() {
        assertEquals(1, goldCard.getAnimals_needed());
    }

    @Test
    void getInsects_needed() {
        assertEquals(0, goldCard.getInsects_needed());
    }

    @Test
    void getLeaf_needed() {
        assertEquals(0, goldCard.getLeaf_needed());
    }

    @Test
    void getID() {
        assertEquals(41, goldCard.getID());
    }
}