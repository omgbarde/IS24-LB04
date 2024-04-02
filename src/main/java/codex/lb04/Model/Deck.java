package codex.lb04.Model;

import java.util.*;

public class Deck {
    private ArrayList<Card> cards;

    private static Deck instance;

    /**
     * Default constructor
     */
    public Deck() {
        cards = new ArrayList<>();
        // Initialize Deck
        initializeDeck();
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

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card draw() {
        if(cards.isEmpty()){
            throw new IllegalStateException("Deck is empty");
        }
        return cards.removeFirst();
    }
    private void initializeDeck() {
        //TODO
    }


}
