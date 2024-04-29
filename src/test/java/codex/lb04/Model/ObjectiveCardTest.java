package codex.lb04.Model;

import codex.lb04.Model.Enumerations.Color;
import codex.lb04.Model.Enumerations.ResourceType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class ObjectiveCardTest {

    private Face front;
    private Face back;
    private ObjectiveCard objectiveCard;
    private Corner CoveredCorner;
    private ResourceType CoveredResource;


    @Before
    public void setUp() {
        this.front = new Face(CoveredCorner, CoveredCorner, CoveredCorner, CoveredCorner);
        this.back = new Face(CoveredCorner, CoveredCorner, CoveredCorner, CoveredCorner);
        this.objectiveCard = new ObjectiveCard(Color.RED, front, back, 2, 87);
    }

    @After
    public void tearDown() {
        front = null;
        back = null;
        objectiveCard = null;
        CoveredCorner = null;
    }

    @Test
    public void getPoints() {
        assertEquals((Integer) 2, objectiveCard.getPoints());
    }
}
