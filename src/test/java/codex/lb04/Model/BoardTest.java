package codex.lb04.Model;

import codex.lb04.Model.Enumerations.Color;
import codex.lb04.Model.Enumerations.ResourceType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class BoardTest {

    private Board board;
    private Deck deck;
    private Card card;
    private Card card1;
    private Card card2;
    private Face front;
    private Face back;
    private Face BlankFace;
    private Corner corner1;
    private Corner corner2;
    private Corner corner3;
    private Corner corner4;
    private Corner EmptyCorner;
    private Corner CoveredCorner;
    private ObjectiveCard cardOb;
    private ObjectiveCard cardOb2;
    private ArrayList<ObjectiveCard> comm_obj;


    @Before
    public void setUp() {
        this.deck = Deck.getInstance();
        this.corner1 = new Corner(ResourceType.ANIMAL);
        this.corner2 = new Corner(ResourceType.MUSHROOM);
        this.corner3 = new Corner(ResourceType.INSECT);
        this.corner4 = new Corner(ResourceType.LEAF);
        this.EmptyCorner = new Corner(false);
        this.CoveredCorner = new Corner(true);
        this.front = new Face(corner1, corner2, corner3, corner4);
        this.back = new Face(EmptyCorner, EmptyCorner, EmptyCorner, EmptyCorner, ResourceType.ANIMAL);
        this.BlankFace = new Face(CoveredCorner,CoveredCorner,CoveredCorner,CoveredCorner);
        this.card = new Card(Color.BLUE, front, back, 1);
        this.card1 = new Card(Color.BLUE, front, back, 1);
        this.card2 = new Card(Color.BLUE, front, back, 1);
        this.cardOb = new ObjectiveCard(Color.BLUE, BlankFace, BlankFace, 2, 89);
        this.cardOb2 = new ObjectiveCard(Color.BLUE, BlankFace, BlankFace, 2, 89);
        card.flip();
        card1.flip();
        card2.flip();
        this.comm_obj = new ArrayList<ObjectiveCard>();
        comm_obj.add(cardOb);
        comm_obj.add(cardOb2);
        this.board = new Board();
    }

    @After
    public void tearDown() {
        this.deck.resetInstance();
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
        this.cardOb = null;
        this.BlankFace = null;
        this.card1 = null;
        this.card2 = null;
    }

    @Test
    public void setZeroResources() {
        board.setZeroResources();
        assertEquals((Integer) 0, board.getInsects());
        assertEquals((Integer) 0, board.getAnimals());
        assertEquals((Integer) 0, board.getMushrooms());
        assertEquals((Integer) 0, board.getLeaves());
        assertEquals((Integer) 0, board.getQuills());
        assertEquals((Integer) 0, board.getInkwells());
        assertEquals((Integer) 0, board.getManuscripts());
    }

    @Test
    public void placeCard() {
        board.placeCard(card, 0, 0);
        assertEquals(1, board.getIngameCards().size());
    }

    @Test
    public void updateResources() {
        board.placeCard(card, 0, 0);
        board.updateResources();
        assertEquals((Integer) 1, board.getAnimals());
        assertEquals((Integer) 1, board.getMushrooms());
        assertEquals((Integer) 1, board.getInsects());
        assertEquals((Integer) 1, board.getLeaves());
    }

    @Test
    public void getCornerCovered() {
        board.placeCard(card, 0, 0);
        assertEquals((Integer) 1, board.getCornerCovered(card, 1, 1));
    }

    @Test
    public void canBePlaced() {
        assertTrue(board.canBePlaced(0, 0, card));
    }



    @Test
    public void getCard() {
        board.placeCard(card, 0, 0);
        assertEquals(card, board.getCard(0, 0));
    }

    @Test
    public void getIngameCards() {
        assertNotNull(board.getIngameCards());
    }


    @Test
    public void getInsects() {
        board.placeCard(card, 0, 0);
        assertEquals((Integer) 1, board.getInsects());
    }

    @Test
    public void getAnimals() {
        board.placeCard(card, 0, 0);
        assertEquals((Integer) 1, board.getAnimals());
    }

    @Test
    public void getMushrooms() {
        board.placeCard(card, 0, 0);
        assertEquals((Integer) 1, board.getMushrooms());
    }

    @Test
    public void getLeaves() {
        board.placeCard(card, 0, 0);
        assertEquals((Integer) 1, board.getLeaves());
    }

    @Test
    public void getQuills() {
        board.placeCard(card, 0, 0);
        assertEquals((Integer) 0, board.getQuills());
    }

    @Test
    public void getInkwells() {
        board.placeCard(card, 0, 0);
        assertEquals((Integer) 0, board.getInkwells());
    }

    @Test
    public void getManuscripts() {
        board.placeCard(card, 0, 0);
        assertEquals((Integer) 0, board.getManuscripts());
    }

    @Test
    public void getDeck() {
        assertNotNull(board.getDeck());
    }

  @Test
    public void getPoints(){
        board.placeCard(card, 0, 0);
        board.pointsUpdate();
        assertEquals((Integer) 1, board.getPoints());
    }

    // TODO test finalpointsupdate - conviene usare carte specifiche per i test (resource card o gold) al posto della card generica xchè certi metodi sono implementati apposta per gestire casi particolari
    // TODO il test funziona solo partendo dalla carta in posizione 0,0
    @Test
    public void conditionCheckOnPositions(){
        board.setCommonObjectives(comm_obj);
        board.placeCard(card,0,0);
        board.placeCard(card1,-1,-1);
        board.placeCard(card2,1,1);
        assertTrue(board.conditionCheckOnPositions(cardOb,card));
    }

    @Test
    public void checkNumberObjectives(){

    }


}