package codex.lb04.Model;

import codex.lb04.Model.Enumerations.ResourceType;

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
        return toDraw;
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
        objectiveCards.removeFirst();
        return toDraw;
    }

    /**
     * this method draws a card from the deck of initial cards
     *
     * @return the card drawn
     */
    public InitialCard drawInitial() {
        if (initialCards.isEmpty()) {
            throw new IllegalStateException("Deck is empty");
        }
        InitialCard toDraw = initialCards.getFirst();
        initialCards.removeFirst();
        return toDraw;
    }

    /**
     * this method draws the first two cards of the goldcards deck, and shows the front face
     *
     * @return the first two cards of the goldcards deck
     */

    public ArrayList<GoldCard> setVisibleGoldCards(){
        GoldCard visible_gc1 = drawGold();
        GoldCard visible_gc2 = drawGold();
        visible_gc1.flip();
        visible_gc2.flip();
        VisibleGoldCards.add(visible_gc1);
        VisibleGoldCards.add(visible_gc2);
        return VisibleGoldCards;
    }

    public ArrayList<ResourceCard> setVisibleResourceCards(){
        ResourceCard visible_rc1 = drawResource();
        ResourceCard visible_rc2 = drawResource();
        visible_rc1.flip();
        visible_rc2.flip();
        VisibleResourceCards.add(visible_rc1);
        VisibleResourceCards.add(visible_rc2);
        return VisibleResourceCards;
    }


}
