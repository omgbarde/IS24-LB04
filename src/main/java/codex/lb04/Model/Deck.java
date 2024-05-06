package codex.lb04.Model;

import java.util.ArrayList;
import java.util.Collections;

/**
 * this class represents the deck of cards
 */
public class Deck {
    private ArrayList<ResourceCard> resourceCards;
    private ArrayList<GoldCard> goldCards;
    private ArrayList<ObjectiveCard> objectiveCards;
    private ArrayList<InitialCard> initialCards;
    private ArrayList<GoldCard> VisibleGoldCards;
    private ArrayList<ResourceCard> VisibleResourceCards;
    private static Deck instance;

    /**
     * Default constructor
     */
    //TODO shuffle doesnt' work -- DON'T FIX IT FOR NOW
    private Deck() {
        resourceCards = new ArrayList<>();
        goldCards = new ArrayList<>();
        objectiveCards = new ArrayList<>();
        initialCards = new ArrayList<>();
        VisibleGoldCards = new ArrayList<>();
        VisibleResourceCards = new ArrayList<>();
        initializeDeck();
    }

    /**
     * this method creates the deck of cards
     */
    public void initializeDeck() {

        DeckBuilder deckBuilder = new DeckBuilder();
        resourceCards = deckBuilder.createResourceCards();
        goldCards = deckBuilder.createGoldCards();
        initialCards = deckBuilder.createInitialCards();
        objectiveCards = deckBuilder.createObjectiveCards();
        VisibleGoldCards = setVisibleGoldCards();
        VisibleResourceCards = setVisibleResourceCards();
        //this.shuffleResources();
        //this.shuffleGold();
        //this.shuffleObjectives();
        //this.shuffleInitial();
    }

    /**
     * returns the deck instance
     *
     * @return the deck instance
     */
    public static Deck getInstance() {
        if (instance == null) {
            instance = new Deck();
        }
        return instance;
    }

    public void resetInstance() {
        instance = null;
    }



    /**
     * this method draws a card from the deck of resources
     *
     * @return the card drawn
     */
    public ResourceCard drawResource() {
        if (resourceCards.isEmpty()) {
            throw new IllegalStateException("Deck is empty");
        }
        ResourceCard toDraw = resourceCards.getFirst();
        resourceCards.removeFirst();
        toDraw.flip();
        return toDraw;
    }

    /**
     * this method draws a card from the deck of gold
     *
     * @return the card drawn
     */
    public GoldCard drawGold() {
        if (goldCards.isEmpty()) {
            throw new IllegalStateException("Deck is empty");
        }
        GoldCard toDraw = goldCards.getFirst();
        goldCards.removeFirst();
        toDraw.flip();
        return toDraw;
    }

    /**
     * this method updates the visible gold cards
     */
    public void updateVisibleGold() {
        GoldCard toDraw = drawGold();
        VisibleGoldCards.add(toDraw);
    }


    /**
     * this method draws a card from the deck of objectives
     *
     * @return the card drawn
     */
    public ObjectiveCard drawObjective() {
        if (objectiveCards.isEmpty()) {
            throw new IllegalStateException("Deck is empty");
        }
        ObjectiveCard toDraw = objectiveCards.getFirst();
        toDraw.flip();
        objectiveCards.removeFirst();
        return toDraw;
    }

    public ArrayList<ObjectiveCard> setCommonObjectives() {
        ArrayList<ObjectiveCard> chosenObjectives = new ArrayList<ObjectiveCard>();
        chosenObjectives.add(this.drawObjective());
        chosenObjectives.add(this.drawObjective());
        return chosenObjectives;
    }

    /**
     * this method draws a card from the deck of initial cards
     *
     * @return the card drawn
     */
    public InitialCard drawInitialCard() {
        if (initialCards.isEmpty()) {
            throw new IllegalStateException("Deck is empty");
        }
        InitialCard toDraw = initialCards.getFirst();
        toDraw.flip();
        initialCards.removeFirst();
        return toDraw;
    }

    /**
     * this method draws the first two cards of the goldcards deck, and shows the front faces
     *
     * @return the first two cards of the goldcards deck
     */
    public ArrayList<GoldCard> setVisibleGoldCards() {
        GoldCard visible_gc1 = drawGold();
        GoldCard visible_gc2 = drawGold();
        VisibleGoldCards.add(visible_gc1);
        VisibleGoldCards.add(visible_gc2);
        return VisibleGoldCards;
    }

    /**
     * this method updates the visible resource cards
     */
    public void updateVisibleResource() {
        ResourceCard toDraw = drawResource();
        VisibleResourceCards.add(toDraw);
    }

    /**
     * this method draws the first two cards of the resource cards deck, and shows the front faces
     * @return the first two cards of the resource cards deck
     */
    public ArrayList<ResourceCard> setVisibleResourceCards() {
        ResourceCard visible_rc1 = drawResource();
        ResourceCard visible_rc2 = drawResource();
        VisibleResourceCards.add(visible_rc1);
        VisibleResourceCards.add(visible_rc2);
        return VisibleResourceCards;
    }

    /**
     * returns the visible gold cards
     * @return
     */
    public ArrayList<GoldCard> getVisibleGoldCards() {
        return VisibleGoldCards;
    }

    /**
     * returns the visible resource cards
     * @return the visible resource cards
     */
    public ArrayList<ResourceCard> getVisibleResourceCards() {
        return VisibleResourceCards;
    }

    /**
     * this method returns the deck of resource cards
     *
     * @return the deck of resource cards
     */
    public ArrayList<ResourceCard> getResourceCards() {
        return resourceCards;
    }

    /**
     * this method returns the deck of gold cards
     *
     * @return the deck of gold cards
     */
    public ArrayList<GoldCard> getGoldCards() {
        return goldCards;
    }

    /**
     * this method returns the deck of objective cards
     *
     * @return the deck of objective cards
     */
    public ArrayList<ObjectiveCard> getObjectiveCards() {
        return objectiveCards;
    }

    /**
     * this method returns the deck of initial cards
     *
     * @return the deck of initial cards
     */
    public ArrayList<InitialCard> getInitialCards() {
        return initialCards;
    }

    /**
     * this method shuffles the deck of resources cards
     *
     */
    public void shuffleResources() {
        Collections.shuffle(resourceCards);
    }

    /**
     * this method shuffles the deck of gold cards
     */
    public void shuffleGold() {
        Collections.shuffle(goldCards);
    }

    /**
     * this method shuffles the deck of objective cards
     */
    public void shuffleObjectives() {
        Collections.shuffle(objectiveCards);
    }

    public void shuffleInitial(){ Collections.shuffle(initialCards);}
}