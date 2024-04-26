package codex.lb04.Model;

import codex.lb04.Model.Enumerations.Color;
import codex.lb04.Model.Enumerations.ResourceType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import codex.lb04.Model.DeckBuilder;
import java.util.ArrayList;


import static org.junit.jupiter.api.Assertions.*;

class DeckBuilderTest {

    private Board board;
    private Deck deck;
    private Card card;
    private Face front;
    private Face back;
    private Corner corner1;
    private Corner corner2;
    private Corner corner3;
    private Corner corner4;
    private Corner EmptyCorner;
    private Corner CoveredCorner;
    private DeckBuilder deckBuilder;

    @BeforeEach
    void setUp() {
        this.corner1 = new Corner(ResourceType.ANIMAL);
        this.corner2 = new Corner(ResourceType.MUSHROOM);
        this.corner3 = new Corner(ResourceType.INSECT);
        this.corner4 = new Corner(ResourceType.LEAF);
        this.EmptyCorner = new Corner(false);
        this.CoveredCorner = new Corner(true);
        this.front = new Face(corner1, corner2, corner3, corner4);
        this.back = new Face(EmptyCorner, EmptyCorner, EmptyCorner, EmptyCorner, ResourceType.ANIMAL);
        this.card = new Card(Color.BLUE, front, back);
        this.board = new Board();
        this.deckBuilder = new DeckBuilder();
    }

    @AfterEach
    void tearDown() {
        this.corner1 = null;
        this.corner2 = null;
        this.corner3 = null;
        this.corner4 = null;
        this.EmptyCorner = null;
        this.CoveredCorner = null;
        this.front = null;
        this.back = null;
        this.card = null;
        this.board = null;
        this.deckBuilder = null;
    }

    @Test
    void createResourceCards() {
    }

    @Test
    void createGoldCards() {
    }

    @Test
    void createInitialCards() {
    }

    @Test
    void createObjectiveCards() {
    }
}