package codex.lb04.Model;

import codex.lb04.Model.Enumerations.ResourceType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FaceTest {
    private Face face;
    private Corner upperLeft;
    private Corner upperRight;
    private Corner lowerLeft;
    private Corner lowerRight;

    @BeforeEach
    void setUp() {
        this.upperLeft = new Corner(ResourceType.INSECT);
        this.upperRight = new Corner(ResourceType.INSECT);
        this.lowerLeft = new Corner(ResourceType.INSECT);
        this.lowerRight = new Corner(ResourceType.INSECT);
        this.face = new Face(upperRight, upperLeft, lowerRight, lowerLeft, ResourceType.INSECT);
    }

    @AfterEach
    void tearDown() {
        upperLeft = null;
        upperRight = null;
        lowerLeft = null;
        lowerRight = null;
    }

    @Test
    void getUpperLeft() {
        assertEquals(upperLeft, face.getUpperLeft());
    }

    @Test
    void getUpperRight() {
        assertEquals(upperRight, face.getUpperRight());
    }

    @Test
    void getLowerLeft() {
        assertEquals(lowerLeft, face.getLowerLeft());
    }

    @Test
    void getLowerRight() {
        assertEquals(lowerRight, face.getLowerRight());
    }

    @Test
    void getCentralResources() {
        assertEquals(ResourceType.INSECT, face.getCentralResources().getFirst());
    }

    @Test
    void getCorners() {
        assertEquals(upperLeft, face.getCorners().get(0));
        assertEquals(upperRight, face.getCorners().get(1));
        assertEquals(lowerLeft, face.getCorners().get(2));
        assertEquals(lowerRight, face.getCorners().get(3));
    }
}