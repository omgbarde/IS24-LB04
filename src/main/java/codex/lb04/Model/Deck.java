package codex.lb04.Model;

import codex.lb04.Message.DrawMessage.UpdateGoldMessage;
import codex.lb04.Message.DrawMessage.UpdateResourceMessage;
import codex.lb04.Observer.GameObserver;
import codex.lb04.Observer.Observable;
import codex.lb04.Utils.DeckBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.NoSuchElementException;

/**
 * this class represents the deck of cards
 */
public class Deck extends Observable {
    private ArrayList<ResourceCard> resourceCards;
    private ArrayList<GoldCard> goldCards;
    private ArrayList<ObjectiveCard> objectiveCards;
    private ArrayList<InitialCard> initialCards;
    private ArrayList<GoldCard> visibleGoldCards;
    private ArrayList<ResourceCard> visibleResourceCards;
    private static Deck instance;

    /**
     * Default constructor
     */
    private Deck() {
        resourceCards = new ArrayList<>();
        goldCards = new ArrayList<>();
        objectiveCards = new ArrayList<>();
        initialCards = new ArrayList<>();
        visibleGoldCards = new ArrayList<>();
        visibleResourceCards = new ArrayList<>();
        initializeDeck();
    }

    /**
     * this method creates the deck of cards by deserializing the deck builder and adds an observer to it
     */
    public void initializeDeck() {
        DeckBuilder deckBuilder = DeckBuilder.deserialize();

        this.addObserver(new GameObserver());
        resourceCards = deckBuilder.getResourceCards();
        goldCards = deckBuilder.getGoldCards();
        initialCards = deckBuilder.getInitialCards();
        objectiveCards = deckBuilder.getObjectiveCards();
        this.shuffleResources();
        this.shuffleGold();
        this.shuffleObjectives();
        this.shuffleInitial();
        visibleGoldCards = setVisibleGoldCards();
        visibleResourceCards = setVisibleResourceCards();
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
     * resets the deck instance
     */
    public void resetInstance() {
        instance = null;
        removeAllObservers();
    }

    /**
     * this method draws a card from the deck of resources
     *
     * @return the card drawn
     */
    public ResourceCard drawResource() {
        if (!resourceCards.isEmpty()) {
            ResourceCard toDraw = resourceCards.getFirst();
            resourceCards.removeFirst();
            toDraw.flip();
            return toDraw;
        } else return null;
    }

    /**
     * this method draws a card from the deck of gold
     *
     * @return the card drawn
     */
    public GoldCard drawGold() {
        if (!goldCards.isEmpty()) {
            GoldCard toDraw = goldCards.getFirst();
            goldCards.removeFirst();
            toDraw.flip();
            return toDraw;
        } else return null;
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

    /**
     * thi method sets the common objectives
     *
     * @return the common objectives arraylist
     */
    public ArrayList<ObjectiveCard> setCommonObjectives() {
        ArrayList<ObjectiveCard> chosenObjectives = new ArrayList<>();
        chosenObjectives.add(this.drawObjective());
        chosenObjectives.add(this.drawObjective());
        //notifyObserver(new UpdateCommonObjectivesMessage(chosenObjectives)); // broadcast
        return chosenObjectives;
    }

    /**
     * this method draws a card from the deck of initial cards
     *
     * @return the card drawn
     */
    public InitialCard drawInitialCard() {
        if (!initialCards.isEmpty()) {
            InitialCard toDraw = initialCards.getFirst();
            toDraw.flip();
            initialCards.removeFirst();
            return toDraw;
        } else return null;
    }


    /**
     * this method draws the first three cards of the gold cards deck, and shows the front faces
     *
     * @return the first two cards of the gold cards deck
     */
    public ArrayList<GoldCard> setVisibleGoldCards() {
        GoldCard visible_gc1 = drawGold();
        GoldCard visible_gc2 = drawGold();
        GoldCard topGold = getTopGold();
        visibleGoldCards.add(visible_gc1);
        visibleGoldCards.add(visible_gc2);
        visibleGoldCards.add(topGold);

        //noinspection unchecked
        ArrayList<GoldCard> toSend = ((ArrayList<GoldCard>) visibleGoldCards.clone());
        notifyObserver(new UpdateGoldMessage(toSend)); // broadcast
        return visibleGoldCards;
    }


    /**
     * this method draws the first two cards of the resource cards deck, and shows the front faces
     *
     * @return the first two cards of the resource cards deck
     */
    public ArrayList<ResourceCard> setVisibleResourceCards() {
        ResourceCard visible_rc1 = drawResource();
        ResourceCard visible_rc2 = drawResource();
        visibleResourceCards.add(visible_rc1);
        visibleResourceCards.add(visible_rc2);
        visibleResourceCards.add(getTopResource());
        //noinspection unchecked
        ArrayList<ResourceCard> toSend = ((ArrayList<ResourceCard>) visibleResourceCards.clone());
        notifyObserver(new UpdateResourceMessage(toSend)); // broadcast
        return visibleResourceCards;
    }

    /**
     * this method updates the visible gold cards
     */
    public void updateVisibleGold(int pick) {
        this.visibleGoldCards.remove(pick);
        GoldCard topGold;
        switch (pick) {
            case 0, 1:
                drawGold();
                topGold = getTopGold();
                if (topGold != null) visibleGoldCards.add(topGold);
                break;
            case 2:
                topGold = getTopGold();
                if (topGold != null) visibleGoldCards.add(getTopGold());
                break;
        }
        //noinspection unchecked
        ArrayList<GoldCard> toSend = ((ArrayList<GoldCard>) visibleGoldCards.clone());
        notifyObserver(new UpdateGoldMessage(toSend)); // broadcast
    }

    /**
     * this method updates the visible resource cards
     */
    public void updateVisibleResource(int pick) {
        visibleResourceCards.remove(pick);
        ResourceCard topResource;
        switch (pick) {
            case 0, 1:
                drawResource();
                topResource = getTopResource();
                if (topResource != null) visibleResourceCards.add(topResource);
                break;
            case 2:
                topResource = getTopResource();
                if (topResource != null) visibleResourceCards.add(topResource);
                break;
        }
        //noinspection unchecked
        ArrayList<ResourceCard> toSend = ((ArrayList<ResourceCard>) visibleResourceCards.clone());
        notifyObserver(new UpdateResourceMessage(toSend)); // broadcast
    }


    //GETTERS

    /**
     * getter for the visible gold cards
     *
     * @return the visible gold cards arrayList
     */
    public ArrayList<GoldCard> getVisibleGoldCards() {
        return visibleGoldCards;
    }

    /**
     * returns the visible resource cards
     *
     * @return the visible resource cards
     */
    public ArrayList<ResourceCard> getVisibleResourceCards() {
        return visibleResourceCards;
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
     * this method shuffles the deck of initial cards
     */
    public void shuffleInitial() {
        Collections.shuffle(initialCards);
    }

    /**
     * this method returns the first resource card
     *
     * @return the first resource card
     */
    public ResourceCard getTopResource() {
        ResourceCard top;
        try {
            top = resourceCards.getFirst();
        } catch (NoSuchElementException e) {
            return null;
        }
        return top;
    }

    /**
     * this method returns the first gold card
     *
     * @return the first gold card
     */
    public GoldCard getTopGold() {
        GoldCard top;
        try {
            top = goldCards.getFirst();
        } catch (NoSuchElementException e) {
            return null;
        }
        return top;
    }

    /**
     * this method returns true if all the decks are empty
     *
     * @return true if all the decks are empty
     */
    public boolean isEmpty() {
        return goldCards.isEmpty() && resourceCards.isEmpty() && visibleGoldCards.isEmpty() && visibleResourceCards.isEmpty();
    }

}