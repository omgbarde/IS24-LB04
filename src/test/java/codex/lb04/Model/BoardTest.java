package codex.lb04.Model;

import codex.lb04.Model.Enumerations.Color;
import codex.lb04.Model.Enumerations.ResourceType;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

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

    @BeforeEach
    void setUp() {
        this.deck = Deck.getInstance();
        this.corner1 = new Corner(ResourceType.ANIMAL);
        this.corner2 = new Corner(ResourceType.MUSHROOM);
        this.corner3 = new Corner(ResourceType.INSECT);
        this.corner4 = new Corner(ResourceType.LEAF);
        this.EmptyCorner = new Corner(false);
        this.CoveredCorner = new Corner(true);
        this.front = new Face( corner1 , corner2 , corner3 , corner4 );
        this.back = new Face( EmptyCorner , EmptyCorner , EmptyCorner , EmptyCorner , ResourceType.ANIMAL);
        this.card = new Card(Color.BLUE , front , back);
        this.board = new Board();
    }

    @AfterEach
    void tearDown() {
        this.deck = null;
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
    }

    @Test
    void setZeroResources() {
        board.setZeroResources();
        assertEquals(0, board.getInsects());
        assertEquals(0, board.getAnimals());
        assertEquals(0, board.getMushrooms());
        assertEquals(0, board.getLeaves());
        assertEquals(0, board.getQuills());
        assertEquals(0, board.getInkwells());
        assertEquals(0, board.getManuscripts());
    }

    @Test
    void placeCard() {
        board.placeCard(card, 0, 0);
        assertEquals(1, board.getIngameCards().size());
    }

    @Test
    void updateResources() {
        board.placeCard(card, 0, 0);
        board.updateResources();
        assertEquals(1, board.getAnimals());
        assertEquals(1, board.getMushrooms());
        assertEquals(1, board.getInsects());
        assertEquals(1, board.getLeaves());
    }

    @Test
    void canBePlaced() {
        assertTrue(board.canBePlaced(0, 0));
    }

    @Test
    void getResourceCards() {
        assertNotNull(board.getResourceCards());
    }

    @Test
    void getGoldCards() {
        assertNotNull(board.getGoldCards());
    }

    @Test
    void getInsects() {
        board.placeCard(card, 0, 0);
        assertEquals(1, board.getInsects());
    }

    @Test
    void getAnimals() {
        board.placeCard(card, 0, 0);
        assertEquals(1, board.getAnimals());
    }

    @Test
    void getMushrooms() {
        board.placeCard(card, 0, 0);
        assertEquals(1, board.getMushrooms());
    }

    @Test
    void getLeaves() {
        board.placeCard(card, 0, 0);
        assertEquals(1, board.getLeaves());
    }

    @Test
    void getQuills() {
        board.placeCard(card, 0, 0);
        assertEquals(0, board.getQuills());
    }

    @Test
    void getInkwells() {
        board.placeCard(card, 0, 0);
        assertEquals(0, board.getInkwells());
    }

    @Test
    void getManuscripts() {
        board.placeCard(card, 0, 0);
        assertEquals(0, board.getManuscripts());
    }

    @Test
    void getDeck() {
    }
}