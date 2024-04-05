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
    private Deck deck;
    public Board(){
        this.deck = Deck.getInstance();


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
     * This method return the current resources a player has
     * @param player
     * @return
     */
   public ArrayList<ResourceType> getcurrentResources(Player player){ return null; };

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
}