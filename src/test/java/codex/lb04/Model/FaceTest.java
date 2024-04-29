package codex.lb04.Model;

import codex.lb04.Model.Enumerations.ResourceType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class FaceTest {
    private Face face;
    private Corner upperLeft;
    private Corner upperRight;
    private Corner lowerLeft;
    private Corner lowerRight;

    @Before
    public void setUp() {
        this.upperLeft = new Corner(ResourceType.INSECT);
        this.upperRight = new Corner(ResourceType.INSECT);
        this.lowerLeft = new Corner(ResourceType.INSECT);
        this.lowerRight = new Corner(ResourceType.INSECT);
        this.face = new Face(upperRight, upperLeft, lowerRight, lowerLeft, ResourceType.INSECT);
    }

    @After
    public void tearDown() {
        upperLeft = null;
        upperRight = null;
        lowerLeft = null;
        lowerRight = null;
    }

    @Test
    public void getUpperLeft() {
        assertEquals(upperLeft, face.getUpperLeft());
    }

    @Test
    public void getUpperRight() {
        assertEquals(upperRight, face.getUpperRight());
    }

    @Test
    public void getLowerLeft() {
        assertEquals(lowerLeft, face.getLowerLeft());
    }

    @Test
    public void getLowerRight() {
        assertEquals(lowerRight, face.getLowerRight());
    }

    @Test
    public void getCentralResources() {
        assertEquals(ResourceType.INSECT, face.getCentralResources().getFirst());
    }

    @Test
    public void getCorners() {
        assertEquals(upperLeft, face.getCorners().get(0));
        assertEquals(upperRight, face.getCorners().get(1));
        assertEquals(lowerLeft, face.getCorners().get(2));
        assertEquals(lowerRight, face.getCorners().get(3));
    }
}