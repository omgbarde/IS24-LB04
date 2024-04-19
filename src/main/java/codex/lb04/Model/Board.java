package codex.lb04.Model;

import codex.lb04.Model.Enumerations.Color;
import codex.lb04.Model.Enumerations.ResourceType;

import java.util.ArrayList;
//TODO commentare metodi non commentati
/**
 * This class represents the board of the game
 */
public class Board {
    private final ArrayList<Card> inGameCards = new ArrayList<Card>();
    private ArrayList<ObjectiveCard> inGameObjectiveCards = new ArrayList<ObjectiveCard>();
    private ObjectiveCard secretObjective;
    private ArrayList<Card> ResourceCards = new ArrayList<Card>();
    private ArrayList<Card> GoldCards = new ArrayList<Card>();
    private Integer Insects;
    private Integer Animals;
    private Integer Mushrooms;
    private Integer Leaves;
    private Integer Quills;
    private Integer Inkwells;
    private Integer Manuscripts;
    private final Deck deck;
    private Integer Points;
    private Integer PointsByGoldCards;

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
        this.Points = 0 ;
        this.PointsByGoldCards = 0;
    }

    /**
     * This method sets the resources to zero
     */
    public void setZeroResources(){
        this.Insects = 0;
        this.Animals = 0;
        this.Mushrooms = 0;
        this.Leaves = 0;
        this.Quills = 0;
        this.Inkwells = 0;
        this.Manuscripts = 0;
    }
    /**
     * This method tells if a card can be placed with certain coordinates
     * @param x coordinate
     * @param y coordinate
     * @return true if the card can be placed false otherwise
     */
    public boolean canBePlaced(Integer x, Integer y , Card toBePlaced){
        //if it's a gold card checks if we have enough resources
        if(toBePlaced.getClass() == GoldCard.class){
            if(Insects <= ((GoldCard) toBePlaced).getInsects_needed()){
                return false;
            }
            if(Animals <= ((GoldCard) toBePlaced).getAnimals_needed()){
                return false;
            }
            if(Leaves <= ((GoldCard) toBePlaced).getLeaf_needed()){
                return false;
            }
            if(Mushrooms <= ((GoldCard) toBePlaced).getMushroom_needed()){
                return false;
            }
        }
        for (Card card : inGameCards){
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
     * This method places a card on the board
     * @param toBePlaced the card to be placed on the board
     *
     */
    public void placeCard(Card toBePlaced , Integer x, Integer y){
        if(canBePlaced(x , y , toBePlaced)){
            for (Card card : inGameCards){
                if(card.getX() == x+1 && card.getY() == y+1 ){
                    card.getShownFace().getLowerLeft().setCovered(toBePlaced);
                }
                if (card.getX() == x+1 && card.getY() == y-1 ){
                    card.getShownFace().getUpperLeft().setCovered(toBePlaced);
                }
                if (card.getX() == x-1 && card.getY() == y+1 ){
                    card.getShownFace().getLowerRight().setCovered(toBePlaced);
                }
                if (card.getX() == x-1 && card.getY() == y-1 ){
                    card.getShownFace().getUpperRight().setCovered(toBePlaced);
                }
            }
            toBePlaced.setCoordinates(x , y);
            inGameCards.add(toBePlaced);
            updateResources();
            if(toBePlaced.getClass() == GoldCard.class){
                updateGoldCardsPoints((GoldCard) toBePlaced);
            }
            pointsUpdate();
        }
    }

    /**
     * updates the points a player has received only by placing gold cards
     * @param toBePlaced the gold card that has been placed
     */
    public void updateGoldCardsPoints(GoldCard toBePlaced){
        //TODO implementare conditioncheck per carte oro (aggiunta punti carte oro quando piazzate) (si aggiungono una sola volta)
        if( true /*toBePlaced.conditionCheck()*/){
            this.PointsByGoldCards += toBePlaced.getPoints();
        }
    }
    /**
     * updates the resources of a player when a card is placed
     *
     */
    public void updateResources(){
        setZeroResources();
        for (Card card : inGameCards){
            for(ResourceType resource : card.getShownFace().getCentralResources()){
                switch (resource){
                    case ResourceType.ANIMAL:
                        this.Animals++;
                        break;
                    case ResourceType.INSECT:
                        this.Insects++;
                        break;
                    case ResourceType.LEAF:
                        this.Leaves++;
                        break;
                    case ResourceType.MUSHROOM:
                        this.Mushrooms++;
                        break;
                    case ResourceType.QUILL:
                        this.Quills++;
                        break;
                    case ResourceType.INKWELL:
                        this.Inkwells++;
                        break;
                    case ResourceType.MANUSCRIPT:
                        this.Manuscripts++;
                        break;
                }
            }
            for( Corner corner : card.getShownFace().getCorners()) {
                if (!corner.isCovered()) {
                    switch (corner.getResource()) {
                        case ResourceType.ANIMAL:
                            this.Animals++;
                            break;
                        case ResourceType.INSECT:
                            this.Insects++;
                            break;
                        case ResourceType.LEAF:
                            this.Leaves++;
                            break;
                        case ResourceType.MUSHROOM:
                            this.Mushrooms++;
                            break;
                        case ResourceType.QUILL:
                            this.Quills++;
                            break;
                        case ResourceType.INKWELL:
                            this.Inkwells++;
                            break;
                        case ResourceType.MANUSCRIPT:
                            this.Manuscripts++;
                            break;
                    }
                }
            }
        }
    }

    /**
     * This method returns the resource cards that can be picked
     * @return ResourceCards the resource cards that can be picked
     */
    public ArrayList<Card> getResourceCards() {
        return ResourceCards;
    }

    /**
     * This method returns the ingame cards
     * @return ingameCards the cards that are on the board
     */
    public ArrayList<Card> getIngameCards() {
        return inGameCards;
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
    /**
     * returns the card at the specified coordinates
     * @param x the x coordinate
     * @param y the y coordinate
     * @return the card at the specified coordinates
     */
    public Card getCard(Integer x , Integer y){
        for(Card card : inGameCards){
            if(card.getX() == x && card.getY() == y){
                return card;
            }
        }
        return null;
    }
    /**
     * This method updates the points of the player
     */
    public void pointsUpdate(){
        this.Points = this.PointsByGoldCards;
        for(Card card : inGameCards){
            if(card.getClass() == ResourceCard.class){
                this.Points += ((ResourceCard) card).getPoints();
            }
        }
    }

    public void finalPointsUpdate(){
        for(ObjectiveCard objectiveCard : inGameObjectiveCards){
            for(Card card : inGameCards){
                if(this.conditionCheckOnPositions(objectiveCard , card)){
                    this.Points += objectiveCard.getPoints();
                }
            }
        }
        for(Card card : inGameCards){
            if(this.conditionCheckOnPositions(secretObjective , card)){
                this.Points += secretObjective.getPoints();
            }
        }
        for (ObjectiveCard objectiveCard : inGameObjectiveCards){
            this.Points += conditionCheckResources(objectiveCard);
        }

        this.Points += conditionCheckResources(secretObjective);
    }

    public Integer conditionCheckResources(ObjectiveCard objectiveCard){
        switch(objectiveCard.getID()){
            case 95 :
                return (Integer) objectiveCard.getPoints() * this.getMushrooms()/3;
            case 96 :
                return (Integer) objectiveCard.getPoints() * this.getLeaves()/3;
            case 97 :
                return (Integer) objectiveCard.getPoints() * this.getAnimals()/3;
            case 98 :
                return (Integer) objectiveCard.getPoints() * this.getInsects()/3;
            case 99 :
                return (Integer) objectiveCard.getPoints() * Math.min( this.getManuscripts() , Math.min(this.getInkwells() , this.getQuills()));
            case 100 :
                return (Integer) objectiveCard.getPoints() * this.getManuscripts()/2;
            case 101 :
                return (Integer) objectiveCard.getPoints() * this.getInkwells()/2;
            case 102 :
                return (Integer) objectiveCard.getPoints() * this.getQuills()/2;
        }
        return 0;
    }


    public boolean conditionCheckOnPositions(ObjectiveCard objectiveCard , Card card){
        switch(objectiveCard.getID()){
            case 87 :
                if(card.getColor() == Color.RED) {
                    if (getCard(card.getX() + 1, card.getY() + 1).getColor() == Color.RED && getCard(card.getX() - 1, card.getY() - 1).getColor() == Color.RED) {
                        return true;
                    }
                }
                break;
            case 88 :
                if(card.getColor() == Color.GREEN) {
                    if (getCard(card.getX() + 1, card.getY() - 1).getColor() == Color.GREEN && getCard(card.getX() - 1, card.getY() + 1).getColor() == Color.GREEN) {
                        return true;
                    }
                }
                break;
            case 89 :
                if(card.getColor() == Color.BLUE) {
                    if (getCard(card.getX() + 1, card.getY() + 1).getColor() == Color.BLUE && getCard(card.getX() - 1, card.getY() - 1).getColor() == Color.BLUE) {
                        return true;
                    }
                }
                break;
            case 90 :
                if(card.getColor() == Color.PURPLE) {
                    if (getCard(card.getX() + 1, card.getY() - 1).getColor() == Color.PURPLE && getCard(card.getX() - 1, card.getY() + 1).getColor() == Color.PURPLE) {
                        return true;
                    }
                }
                break;
            case 91 :
                if(card.getColor() == Color.RED) {
                    if (getCard(card.getX(), card.getY() - 1).getColor() == Color.RED && getCard(card.getX() + 1, card.getY() - 2).getColor() == Color.GREEN) {
                        return true;
                    }
                }
                break;
            case 92 :
                if(card.getColor() == Color.GREEN) {
                    if (getCard(card.getX(), card.getY() - 1).getColor() == Color.GREEN && getCard(card.getX() - 1, card.getY() - 2).getColor() == Color.PURPLE) {
                        return true;
                    }
                }
                break;
            case 93 :
                if(card.getColor() == Color.BLUE) {
                    if (getCard(card.getX(), card.getY() + 1).getColor() == Color.BLUE && getCard(card.getX() + 1, card.getY() + 2).getColor() == Color.RED) {
                        return true;
                    }
                }
                break;
            case 94 :
                if(card.getColor() == Color.PURPLE) {
                    if (getCard(card.getX(), card.getY() + 1).getColor() == Color.PURPLE && getCard(card.getX() - 1, card.getY() + 2).getColor() == Color.BLUE) {
                        return true;
                    }
                }
                break;
        }
        return false;
    }

}