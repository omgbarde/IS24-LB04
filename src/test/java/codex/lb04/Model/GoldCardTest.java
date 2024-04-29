package codex.lb04.Model;
import java.util.ArrayList;
import codex.lb04.Model.Enumerations.Color;
import codex.lb04.Model.Enumerations.ResourceType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class GoldCardTest {
    private GoldCard goldCard;
    private Face front;
    private Face back;
    private Corner corner1;
    private Corner EmptyCorner;
    private Corner CoveredCorner;



    @Before
    public void setUp() {
        this.corner1 = new Corner(ResourceType.QUILL);
        this.EmptyCorner = new Corner(false);
        this.CoveredCorner = new Corner(true);
        this.front = new Face(EmptyCorner, CoveredCorner, EmptyCorner, corner1);
        this.back = new Face(EmptyCorner, EmptyCorner, EmptyCorner, EmptyCorner, ResourceType.MUSHROOM);
        this.goldCard = new GoldCard(Color.RED, front, back, 1, 2, 1, 0, 0, 41);

    }

    @After
    public void tearDown() {
        front = null;
        back = null;
        goldCard = null;
        corner1 = null;
        EmptyCorner = null;
        CoveredCorner = null;
    }

    @Test
    public void getPoints(){
        assertEquals((Integer) 1, goldCard.getPoints());
    }

    @Test
    public void getMushroom_needed() {
        assertEquals((Integer) 2, goldCard.getMushroom_needed());
    }

    @Test
    public void getAnimals_needed() {
        assertEquals((Integer) 1, goldCard.getAnimals_needed());
    }

    @Test
    public void getInsects_needed() {
        assertEquals((Integer) 0, goldCard.getInsects_needed());
    }

    @Test
    public void getLeaf_needed() {
        assertEquals((Integer) 0, goldCard.getLeaf_needed());
    }

    @Test
    public void getID() {
        assertEquals((Integer) 41, goldCard.getID());
    }
}