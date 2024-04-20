package codex.lb04.Model;

import codex.lb04.Model.Enumerations.Color;
import codex.lb04.Model.Enumerations.ResourceType;

import java.util.*;

/**
 * this class represents the deck of cards
 */
public class Deck {
    private final ArrayList<Card> resourceCards;
    private final ArrayList<Card> goldCards;
    private static Deck instance;

    /**
     * Default constructor
     */
    private Deck() {
        resourceCards = new ArrayList<>();
        goldCards = new ArrayList<>();
        initializeDeck();
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
    public ArrayList<Card> getResourceCards() {
        return resourceCards;
    }

    /**
     * this method returns the deck of gold cards
     *
     * @return the deck of gold cards
     */
    public ArrayList<Card> getGoldCards() {
        return goldCards;
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
    public Card drawResource() {
        if (resourceCards.isEmpty()) {
            throw new IllegalStateException("Deck is empty");
        }
        return resourceCards.removeFirst();

    }

    /**
     * this method draws a card from the deck of gold
     *
     * @return the card drawn
     */
    public Card drawGold() {
        if (goldCards.isEmpty()) {
            throw new IllegalStateException("Deck is empty");
        }
        return goldCards.removeFirst();

    }

    /**
     * this method creates the deck of cards and returns it
     */
    public void initializeDeck() {
        //TODO implementare creazione mazzo - poi cambieremo questo metodo in modo che deserializzi il file serializzato del deck quando viene chiamato
        //DeckBuilder deckBuilder = new DeckBuilder();
        //resourceCards = deckBuilder.buildResourceDeck();
        //goldCards = deckBuilder.buildGoldDeck();

    }
}
