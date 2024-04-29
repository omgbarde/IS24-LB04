package codex.lb04.Model;


import codex.lb04.Model.Enumerations.Color;
import codex.lb04.Model.Enumerations.ResourceType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;


public class DeckTest {

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
        this.card = new Card(Color.BLUE, front, back);
        this.board = new Board();
    }

    @After
    public void tearDown() {
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
    public void getResourceCards() {
        assertNotNull(deck.getResourceCards());
    }

    @Test
    public void getGoldCards() {
        assertNotNull(deck.getGoldCards());
    }

    @Test
    public void getInstance() {
        assertNotNull(deck.getInstance());
    }

    @Test
    public void getObjectiveCards() {
        assertNotNull(deck.getObjectiveCards());
    }

    @Test
    public void getInitialCards() {
        assertNotNull(deck.getInitialCards());
    }

    @Test
    public void shuffleResources() {
        ArrayList<ResourceCard> originalOrder = new ArrayList<ResourceCard>(deck.getResourceCards());
        boolean orderChanged = false;
        for (int i = 0; i < 10; i++) {
            deck.shuffleResources();
            if (!deck.getResourceCards().equals(originalOrder)) {
                orderChanged = true;
                break;
            }
        }
        assertTrue(orderChanged);
    }

    @Test
    public void shuffleGold() {
        ArrayList<GoldCard> originalOrder = new ArrayList<GoldCard>(deck.getGoldCards());
        boolean orderChanged = false;
        for (int i = 0; i < 10; i++) {
            deck.shuffleGold();
            if (!deck.getGoldCards().equals(originalOrder)) {
                orderChanged = true;
                break;
            }
        }
        assertTrue(orderChanged);
    }

    @Test
    public void shuffleObjectives() {
        ArrayList<ObjectiveCard> originalOrder = new ArrayList<ObjectiveCard>(deck.getObjectiveCards());
        boolean orderChanged = false;
        for (int i = 0; i < 10; i++) {
            deck.shuffleObjectives();
            if (!deck.getObjectiveCards().equals(originalOrder)) {
                orderChanged = true;
                break;
            }
        }
        assertTrue(orderChanged);
    }

    @Test
    public void drawResource() {
        assertNotNull(deck.drawResource());
    }


    @Test
    public void initializeDeck() {
        assertNotNull(deck.getResourceCards());
        assertNotNull(deck.getInitialCards());
        assertNotNull(deck.getObjectiveCards());
        assertNotNull(deck.getGoldCards());
        assertNotNull(deck.getVisibleGoldCards());
        assertNotNull(deck.getVisibleResourceCards());
    }

    @Test
    public void updateVisibleResource() {
        ArrayList<ResourceCard> original = new ArrayList<ResourceCard>(deck.getVisibleResourceCards());
        boolean changed = false;
        board.drawResourceCard(1);
        if (!original.equals(deck.getVisibleResourceCards())) {
            changed = true;
        }
        assertTrue(changed);
    }

    @Test
    public void drawGold() {
        assertNotNull(deck.drawGold());
    }

    @Test
    public void updateVisibleGold() {
        ArrayList<GoldCard> original = new ArrayList<GoldCard>(deck.getVisibleGoldCards());
        boolean changed = false;
        board.drawGoldCard(0);
        if (!original.equals(deck.getVisibleGoldCards())) {
            changed = true;
        }
        assertTrue(changed);
    }

    @Test
    public void SecretObjectivesChoice() {
        ArrayList<ObjectiveCard> obj_cards = new ArrayList<ObjectiveCard>();
        ObjectiveCard obj_1 = deck.getObjectiveCards().get(1);
        obj_cards.add(obj_1);
        assertEquals(obj_1, deck.SecretObjectivesChoice(1));
    }

    @Test
    public void drawObjective() {
        assertNotNull(deck.drawObjective());
    }

    @Test
    public void setCommonObjectives() {
        assertNotNull(deck.setCommonObjectives());
    }

    @Test
    public void drawInitialCard() {
        assertNotNull(deck.drawInitialCard());
    }

    // TODO testare i metodi per settare le gold e le resource visibili
    @Test
    public void setVisibleGoldCards() {

    }

    @Test
    public void setVisibleResourceCards() {

    }

    @Test
    public void getVisibleGoldCards() {
        assertNotNull(deck.getVisibleGoldCards());
    }

    @Test
    public void getVisibleResourceCards() {
        assertNotNull(deck.getVisibleResourceCards());
    }


}