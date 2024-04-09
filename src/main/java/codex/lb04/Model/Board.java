package codex.lb04.Model;

import codex.lb04.Model.Enumerations.ResourceType;

import java.util.ArrayList;

/**
 * This class represents the board of the game
 */
public class Board {
    private ArrayList<Card> ingameCards = new ArrayList<Card>();
    private ArrayList<Card> ResourceCards = new ArrayList<Card>();
    private ArrayList<Card> GoldCards = new ArrayList<Card>();
    private Integer Insects;
    private Integer Animals;
    private Integer Mushrooms;
    private Integer Leaves;
    private Integer Quills;
    private Integer Inkwells;
    private Integer Manuscripts;
    private Deck deck;

    /**
     * Default constructor
     */
    public Board(){
        this.deck = Deck.getInstance();
        this.GoldCards = deck.getGoldCards();
        this.ResourceCards = deck.getResourceCards();
        this.Insects = 0;
        this.Animals = 0;
        this.Mushrooms = 0;
        this.Leaves = 0;
        this.Quills = 0;
        this.Inkwells = 0;
        this.Manuscripts = 0;
    }
    /**
     * This method places a card on the board
     * @param toBePlaced the card to be placed on the board
     *
     */
    public void placeCard(Card toBePlaced , Integer x, Integer y){
        if(canBePlaced(x,y)== true){
            toBePlaced.setCoordinates(x , y);
            ingameCards.add(toBePlaced);
            //setto i corner che vengono coperti
        }
    }
    /**
     * This method tells if a card can be placed with certain coordinates
     * @param x coordinate
     * @param y coordinate
     * @return true if the card can be placed false otherwise
     */
   public boolean canBePlaced(Integer x, Integer y){
       for (Card card : ingameCards){
           if(card.getX() == x+1 && card.getY() == y+1 ){
               if(card.getShownFace().getLowerLeft().isCovered()) {
                   return false;
               }
           }
           if(card.getX() == x+1 && card.getY() == y-1 ){
               if(card.getShownFace().getUpperLeft().isCovered()) {
                   return false;
               }
           }
           if(card.getX() == x-1 && card.getY() == y+1 ){
               if(card.getShownFace().getLowerRight().isCovered()) {
                   return false;
               }
           }
           if(card.getX() == x-1 && card.getY() == y-1 ){
               if(card.getShownFace().getUpperRight().isCovered()) {
                   return false;
               }
           }
       }
       return true;
   }
    /**
     * This method returns the resource cars that can be picked
     * @return ResourceCards the resource cards that can be picked
     */
    public ArrayList<Card> getResourceCards() {
        return ResourceCards;
    }

    /**
     * This method returns the gold cards that can be picked
     * @return Goldcards the gold cards that can be picked
     */
    public ArrayList<Card> getGoldCards() {
        return GoldCards;
    }

    /**
     * This method returns the number of "insect" resources that are on the board
     * @return Insects the number of insects(resources) on the board
     */
    public Integer getInsects() {
        return Insects;
    }

    /**
     * This method returns the number of "animal" resources that are on the board
     * @return Animals the number of animals(resources) on the board
     */
    public Integer getAnimals() {
        return Animals;
    }

    /**
     * This method returns the number of "mushroom" resources that are on the board
     * @return Mushrooms the number of mushrooms(resources) on the board
     */
    public Integer getMushrooms() {
        return Mushrooms;
    }

    /**
     * This method returns the number of "leaf" resources that are on the board
     * @return Leaves the number of leaves(resources) on the board
     */
    public Integer getLeaves() {
        return Leaves;
    }

    /**
     * This method returns the number of "quill" resources that are on the board
     * @return Quills the number of quills(resources) on the board
     */
    public Integer getQuills() {
        return Quills;
    }

    /**
     * This method returns the number of "inkwell" resources that are on the board
     * @return Inkwells the number of inkwells(resources) on the board
     */
    public Integer getInkwells() {
        return Inkwells;
    }

    /**
     * This method returns the number of "manuscript" resources that are on the board
     * @return Manuscripts the number of manuscripts(resources) on the board
     */
    public Integer getManuscripts() {
        return Manuscripts;
    }

    /**
     * This method returns the deck of the game
     * @return deck the deck of the game
     */
    public Deck getDeck() {
        return deck;
    }
}