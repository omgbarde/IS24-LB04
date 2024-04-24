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
    private Deck() {
        resourceCards = new ArrayList<>();
        goldCards = new ArrayList<>();
        objectiveCards = new ArrayList<>();
        initialCards = new ArrayList<>();
        VisibleGoldCards = new ArrayList<>();
        VisibleResourceCards = new ArrayList<>();
        initializeDeck();
        this.shuffleResources();
        this.shuffleGold();
        this.shuffleObjectives();
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
     * this method shuffles the deck of objective cards
     */
    public void shuffleObjectives() {
        Collections.shuffle(objectiveCards);
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
     * this method updates the visible resource cards
     */
    public void updateVisibleResource() {
        ResourceCard toDraw = drawResource();
        toDraw.flip();
        VisibleResourceCards.add(toDraw);
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
        toDraw.flip();
        VisibleGoldCards.add(toDraw);
    }

    /**
     * this method allows the player to choose between two secret objectives
     * @param choice the choice of the player
     * @return the card chosen
     */
    public ObjectiveCard SecretObjectivesChoice(Integer choice) {
        if (objectiveCards.isEmpty()) {
            throw new IllegalStateException("Deck is empty");
        }
        ObjectiveCard toDraw;
        switch (choice) {
            case 0:
                toDraw = this.objectiveCards.get(0);
                this.objectiveCards.remove(0);
                return toDraw;
            case 1:
                toDraw = this.objectiveCards.get(1);
                this.objectiveCards.remove(1);
                return toDraw;
            default:
                System.out.println("invalid choice");
                return null;
        }
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
        ObjectiveCard toDraw = objectiveCards.get(0);
        objectiveCards.remove(0);
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
    public InitialCard drawInitial() {
        if (initialCards.isEmpty()) {
            throw new IllegalStateException("Deck is empty");
        }
        InitialCard toDraw = initialCards.get(0);
        initialCards.remove(0);
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
        visible_gc1.flip();
        visible_gc2.flip();
        VisibleGoldCards.add(visible_gc1);
        VisibleGoldCards.add(visible_gc2);
        return VisibleGoldCards;
    }

    /**
     * this method draws the first two cards of the resourcecards deck, and shows the front faces
     * @return the first two cards of the resourcecards deck
     */
    public ArrayList<ResourceCard> setVisibleResourceCards() {
        ResourceCard visible_rc1 = drawResource();
        ResourceCard visible_rc2 = drawResource();
        visible_rc1.flip();
        visible_rc2.flip();
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
}
