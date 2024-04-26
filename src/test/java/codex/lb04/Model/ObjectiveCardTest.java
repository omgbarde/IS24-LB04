package codex.lb04.Model;

import codex.lb04.Model.Enumerations.Color;
import codex.lb04.Model.Enumerations.ResourceType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ObjectiveCardTest {

    private Face front;
    private Face back;
    private ObjectiveCard objectiveCard;
    private Corner CoveredCorner;
    private ResourceType CoveredResource;


    @BeforeEach
    void setUp() {
        this.front = new Face(CoveredCorner, CoveredCorner, CoveredCorner, CoveredCorner);
        this.back = new Face(CoveredCorner, CoveredCorner, CoveredCorner, CoveredCorner);
        this.objectiveCard = new ObjectiveCard(Color.RED, front, back, 2, 87);
    }

    @AfterEach
    void tearDown() {
        front = null;
        back = null;
        objectiveCard = null;
        CoveredCorner = null;
    }

    @Test
    void getPoints() {
        assertEquals(2, objectiveCard.getPoints());
    }
}
