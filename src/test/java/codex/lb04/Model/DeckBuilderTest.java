package codex.lb04.Model;

import codex.lb04.Model.Enumerations.Color;
import codex.lb04.Model.Enumerations.ResourceType;
import codex.lb04.Observer.GameObserver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DeckBuilderTest {
    private GameObserver observer;

    private Board board;
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

    @Before
    public void setUp() {
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
        board.getDeck().addObserver(observer);
        this.deckBuilder = new DeckBuilder();
    }

    @After
    public void tearDown() {
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
        this.observer = null;
    }

    @Test
    public void createResourceCards() {
    }

    @Test
    public void createGoldCards() {
    }

    @Test
    public void createInitialCards() {
    }

    @Test
    public void createObjectiveCards() {
    }
}