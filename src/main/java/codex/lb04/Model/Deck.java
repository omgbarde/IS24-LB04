package codex.lb04.Model;

import java.util.*;

/**
 * this class represents the deck of cards
 */
public class Deck {
    private ArrayList<Card> ResourceCards;
    private ArrayList<Card> GoldCards;
    private static Deck instance;

    /**
     * Default constructor
     */
    private Deck() {
        ResourceCards = new ArrayList<>();
        GoldCards = new ArrayList<>();
        // Initialize Deck - CREARE SIA DECK DI GOLD CHE DI RESOURCE
        initializeDeck();
    }

    /**
     * this method returns the deck of resource cards
     * @return the deck of resource cards
     */
    public ArrayList<Card> getResourceCards() {
        return ResourceCards;
    }

    /**
     * this method returns the deck of gold cards
     * @return the deck of gold cards
     */
    public ArrayList<Card> getGoldCards() {
        return GoldCards;
    }
    /**
     * returns the deck instance
     * @return the deck instance
     */
    public static Deck getInstance() {
        if (instance == null) {
            instance = new Deck();
        }
        return instance;
    }
    /**
     * this method shuffles the deck of resources cards
     */
    public void shuffleResources() {
        Collections.shuffle(ResourceCards);
    }
    /**
     * this method shuffles the deck of gold cards
     *
     */
    public void shuffleGold() {
        Collections.shuffle(GoldCards);
    }
    /**
     * this method draws a card from the deck of resources
     * @return the card drawn
     */
    public Card drawResource() {
        if(ResourceCards.isEmpty()){
            throw new IllegalStateException("Deck is empty");
        }
        return ResourceCards.removeFirst();
    }
    /**
     * this method draws a card from the deck of gold
     * @return the card drawn
     */
    public Card drawGold() {
        if (GoldCards.isEmpty()) {
            throw new IllegalStateException("Deck is empty");
        }
        return GoldCards.removeFirst();
    }
    /**
     * this method creates the deck of cards and returns it
     */
    public void initializeDeck() {
        //TODO
    }
}
