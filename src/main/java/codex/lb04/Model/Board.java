package codex.lb04.Model;

import codex.lb04.Model.Enumerations.Color;
import codex.lb04.Model.Enumerations.ResourceType;

import java.util.ArrayList;
import java.util.Objects;

/**
 * This class represents the board of the game
 */
public class Board {
    //cards played
    private ArrayList<Card> playedCards = new ArrayList<>();
    //objectives
    private ArrayList<ObjectiveCard> inGameObjectiveCards;
    private ObjectiveCard secretObjective;
    //cards
    private ArrayList<GoldCard> VisibleGoldCards;
    private ArrayList<ResourceCard> VisibleResourceCards;
    private ArrayList<Card> hand;
    private InitialCard initialCard;
    //resources
    private Integer Insects;
    private Integer Animals;
    private Integer Mushrooms;
    private Integer Leaves;
    private Integer Quills;
    private Integer Inkwells;
    private Integer Manuscripts;
    //deck
    private Deck deck;
    //points
    private Integer Points;
    private Integer PointsByGoldCards;

    /**
     * Default constructor
     */
    public Board() {
        this.deck = Deck.getInstance();
        //game
        Game game = Game.getInstance();
        this.VisibleResourceCards = deck.getVisibleResourceCards();
        this.VisibleGoldCards = deck.getVisibleGoldCards();
        this.inGameObjectiveCards = game.getCommonObjectives();
        this.hand = new ArrayList<>();
        this.Insects = 0;
        this.Animals = 0;
        this.Mushrooms = 0;
        this.Leaves = 0;
        this.Quills = 0;
        this.Inkwells = 0;
        this.Manuscripts = 0;
        this.Points = 0;
        this.PointsByGoldCards = 0;
    }

    /**
     * This method sets the resources to zero
     */
    public void setZeroResources() {
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
     *
     * @param toBePlaced the card to be placed on the board
     */
    public void placeCard(Card toBePlaced, Integer x, Integer y) {
        if (canBePlaced(x, y, toBePlaced)) {
            for (Card card : playedCards) {
                if (card.getX() == x + 1 && card.getY() == y + 1) {
                    card.getShownFace().getLowerLeft().setCovered(toBePlaced);
                }
                if (card.getX() == x + 1 && card.getY() == y - 1) {
                    card.getShownFace().getUpperLeft().setCovered(toBePlaced);
                }
                if (card.getX() == x - 1 && card.getY() == y + 1) {
                    card.getShownFace().getLowerRight().setCovered(toBePlaced);
                }
                if (card.getX() == x - 1 && card.getY() == y - 1) {
                    card.getShownFace().getUpperRight().setCovered(toBePlaced);
                }
            }
            toBePlaced.setCoordinates(x, y);
            playedCards.add(toBePlaced);
            updateResources();
            if (toBePlaced.getClass() == GoldCard.class) {
                updateGoldCardsPoints((GoldCard) toBePlaced);
            }
            pointsUpdate();
        }

    }

    /**
     * This method tells if a card can be placed with certain coordinates
     *
     * @param x coordinate
     * @param y coordinate
     * @return true if the card can be placed false otherwise
     */
    public boolean canBePlaced(Integer x, Integer y, Card toBePlaced) {
        //if it's a gold card checks if we have enough resources
        if (toBePlaced.getClass() == GoldCard.class) {
            if (Insects <= ((GoldCard) toBePlaced).getInsects_needed()) {
                return false;
            }
            if (Animals <= ((GoldCard) toBePlaced).getAnimals_needed()) {
                return false;
            }
            if (Leaves <= ((GoldCard) toBePlaced).getLeaf_needed()) {
                return false;
            }
            if (Mushrooms <= ((GoldCard) toBePlaced).getMushroom_needed()) {
                return false;
            }
        }
        //checks if the card can be placed in the specified coordinates
        for (Card card : playedCards) {
            if (card.getX() == x + 1 && card.getY() == y + 1) {
                if (card.getShownFace().getLowerLeft().isCovered()) {
                    return false;
                }
            }
            if (card.getX() == x + 1 && card.getY() == y - 1) {
                if (card.getShownFace().getUpperLeft().isCovered()) {
                    return false;
                }
            }
            if (card.getX() == x - 1 && card.getY() == y + 1) {
                if (card.getShownFace().getLowerRight().isCovered()) {
                    return false;
                }
            }
            if (card.getX() == x - 1 && card.getY() == y - 1) {
                if (card.getShownFace().getUpperRight().isCovered()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * This method sets the initial card
     */
    public void setInitialCard(){
        this.initialCard = deck.drawInitialCard();
    }

    /**
     * This method returns the initial card
     * @return the initial card
     */
    public InitialCard getInitialCard(){
        return this.initialCard;
    }

    /**
     * sets the common objectives
     * @param CommonObjectives the common objectives
     */
    public void setCommonObjectives(ArrayList<ObjectiveCard> CommonObjectives) {
        this.inGameObjectiveCards = CommonObjectives;
    }


    /**
     * This method sets the secret objective of the player
     * @param pick the secret objective to set
     */
    public void setSecretObjective(Integer pick) {
        this.secretObjective = deck.SecretObjectivesChoice(pick);
    }

    /**
     * draws a gold card between the choices
     * @param pick the choice of the player
     */
    public void drawGoldCard(Integer pick) {
        switch (pick) {
            case 0, 1:
                this.hand.add(this.VisibleGoldCards.get(pick));
                this.deck.getVisibleGoldCards().remove(pick);
                this.deck.updateVisibleGold();
                break;
            case 2:
                this.hand.add(this.deck.drawGold());
                break;
        }

    }

    /**
     * draws a resource card between the choices
     * @param pick the choice of the player
     */
    public void drawResourceCard(Integer pick) {
        switch (pick) {
            case 0, 1:
                this.hand.add(this.VisibleResourceCards.get(pick));
                this.VisibleResourceCards.remove(pick);
                this.deck.getVisibleResourceCards().remove(pick);
                this.deck.updateVisibleResource();
                break;
            case 2:
                this.hand.add(this.deck.drawResource());
                break;
        }
    }

    /**
     * This method returns the number of corner you're going to cover placing a card
     */
    public Integer getCornerCovered(Card toBePlaced, Integer x, Integer y) {
        int corner_covered = 0;
        for (Card card : playedCards) {
            if (card.getX() == x + 1 && card.getY() == y + 1) {
                corner_covered += 1;
            }
            if (card.getX() == x + 1 && card.getY() == y - 1) {
                corner_covered += 1;
            }
            if (card.getX() == x - 1 && card.getY() == y + 1) {
                corner_covered += 1;
            }
            if (card.getX() == x - 1 && card.getY() == y - 1) {
                corner_covered += 1;
            }
        }
        return corner_covered;
    }

    /**
     * updates the points a player has received only by placing gold cards
     *
     * @param toBePlaced the gold card that has been placed
     */
    public void updateGoldCardsPoints(GoldCard toBePlaced) {
        switch (toBePlaced.getID()) {
            case 41, 51, 63, 71:
                this.PointsByGoldCards += toBePlaced.getPoints() * getQuills();
                break;
            case 42, 53, 61, 73:
                this.PointsByGoldCards += toBePlaced.getPoints() * getInkwells();
                break;
            case 43, 52, 62, 72:
                this.PointsByGoldCards += toBePlaced.getPoints() * getManuscripts();
                break;
            case 44, 45, 46, 54, 55, 56, 64, 65, 66, 74, 75, 76:
                this.PointsByGoldCards += getCornerCovered(toBePlaced, toBePlaced.getX(), toBePlaced.getY()) * 2;
                break;
            case 47, 48, 49, 57, 58, 59, 67, 68, 69, 77, 78, 79:
                this.PointsByGoldCards += 3;
                break;
            case 50, 60, 70, 80:
                this.PointsByGoldCards += 5;
                break;
        }
    }

    /**
     * updates the resources of a player when a card is placed
     */
    public void updateResources() {
        setZeroResources();
        for (Card card : playedCards) {
            for (ResourceType resource : card.getShownFace().getCentralResources()) {
                switch (resource) {
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
            //TODO Debuggare e correggere problema della faccia back
            for (Corner corner : card.getShownFace().getCorners()) {
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
     * returns the card at the specified coordinates
     *
     * @param x the x coordinate
     * @param y the y coordinate
     * @return the card at the specified coordinates
     */
    public Card getCard(Integer x, Integer y) {
        for (Card card : playedCards) {
            if (Objects.equals(card.getX(), x) && Objects.equals(card.getY(), y)) {
                return card;
            }
        }
        return null;
    }

    /**
     * This method updates the points of the player when a new resource card is placed
     */
    public void pointsUpdate() {
        this.Points = this.PointsByGoldCards;
        for (Card card : playedCards) {
            if (card.getClass() == ResourceCard.class) {
                this.Points += card.getPoints();
            }
        }
    }

    /**
     * This method checks if the conditions of the objective cards regarding the resources quantities are met
     *
     * @param objectiveCard the objective card to check
     * @return the points that the player has received
     */
    public Integer conditionCheckResources(ObjectiveCard objectiveCard) {
        return switch (objectiveCard.getID()) {
            case 95 -> objectiveCard.getPoints() * this.getMushrooms() / 3;
            case 96 -> objectiveCard.getPoints() * this.getLeaves() / 3;
            case 97 -> objectiveCard.getPoints() * this.getAnimals() / 3;
            case 98 -> objectiveCard.getPoints() * this.getInsects() / 3;
            case 99 ->
                    objectiveCard.getPoints() * Math.min(this.getManuscripts(), Math.min(this.getInkwells(), this.getQuills()));
            case 100 -> objectiveCard.getPoints() * this.getManuscripts() / 2;
            case 101 -> objectiveCard.getPoints() * this.getInkwells() / 2;
            case 102 -> objectiveCard.getPoints() * this.getQuills() / 2;
            default -> 0;
        };
    }

    /**
     * This method checks if the conditions of the objective cards regarding the positions of the cards are met
     *
     * @param objectiveCard the objective card to check
     * @param card          the card to check
     * @return true if the conditions are met, false otherwise
     */
    public boolean conditionCheckOnPositions(ObjectiveCard objectiveCard, Card card) {
        switch (objectiveCard.getID()) {
            case 87:
                if (card.getColor() == Color.RED) {
                    if (getCard(card.getX() + 1, card.getY() + 1).getColor() == Color.RED && getCard(card.getX() - 1, card.getY() - 1).getColor() == Color.RED) {
                        return true;
                    }
                }
                break;
            case 88:
                if (card.getColor() == Color.GREEN) {
                    if (getCard(card.getX() + 1, card.getY() - 1).getColor() == Color.GREEN && getCard(card.getX() - 1, card.getY() + 1).getColor() == Color.GREEN) {
                        return true;
                    }
                }
                break;
            case 89:
                if (card.getColor() == Color.BLUE) {
                    if (getCard(card.getX() + 1, card.getY() + 1).getColor() == Color.BLUE && getCard(card.getX() - 1, card.getY() - 1).getColor() == Color.BLUE) {
                        return true;
                    }
                }
                break;
            case 90:
                if (card.getColor() == Color.PURPLE) {
                    if (getCard(card.getX() + 1, card.getY() - 1).getColor() == Color.PURPLE && getCard(card.getX() - 1, card.getY() + 1).getColor() == Color.PURPLE) {
                        return true;
                    }
                }
                break;
            case 91:
                if (card.getColor() == Color.RED) {
                    if (getCard(card.getX(), card.getY() - 1).getColor() == Color.RED && getCard(card.getX() + 1, card.getY() - 2).getColor() == Color.GREEN) {
                        return true;
                    }
                }
                break;
            case 92:
                if (card.getColor() == Color.GREEN) {
                    if (getCard(card.getX(), card.getY() - 1).getColor() == Color.GREEN && getCard(card.getX() - 1, card.getY() - 2).getColor() == Color.PURPLE) {
                        return true;
                    }
                }
                break;
            case 93:
                if (card.getColor() == Color.BLUE) {
                    if (getCard(card.getX(), card.getY() + 1).getColor() == Color.BLUE && getCard(card.getX() + 1, card.getY() + 2).getColor() == Color.RED) {
                        return true;
                    }
                }
                break;
            case 94:
                if (card.getColor() == Color.PURPLE) {
                    if (getCard(card.getX(), card.getY() + 1).getColor() == Color.PURPLE && getCard(card.getX() - 1, card.getY() + 2).getColor() == Color.BLUE) {
                        return true;
                    }
                }
                break;
        }
        return false;
    }

    /**
     * This method calculate the points a player receives with objectives cards
     */
    public void finalPointsUpdate() {
        for (ObjectiveCard objectiveCard : inGameObjectiveCards) {
            for (Card card : playedCards) {
                if (this.conditionCheckOnPositions(objectiveCard, card)) {
                    this.Points += objectiveCard.getPoints();
                }
            }
        }
        for (Card card : playedCards) {
            if (this.conditionCheckOnPositions(secretObjective, card)) {
                this.Points += secretObjective.getPoints();
            }
        }
        for (ObjectiveCard objectiveCard : inGameObjectiveCards) {
            this.Points += conditionCheckResources(objectiveCard);
        }

        this.Points += conditionCheckResources(secretObjective);
    }

    public boolean isInHand(Card card){
        for(Card hand : hand){
            if(hand.equals(card)){
                return true;
            }
        }
        return false;
    }

    /**
     * This method flips a card in the hand
     * @param toFlip the card to flip
     */
    public void flipCardInHand(Card toFlip){
        for(Card card : hand){
            if(card.equals(toFlip)){
                card.flip();
            }
        }
    }

    //GETTERS

    /**
     * This method returns the in game cards
     *
     * @return in gameCards the cards that are on the board
     */
    public ArrayList<Card> getIngameCards() {
        return playedCards;
    }

    /**
     * This method returns the number of "insect" resources that are on the board
     *
     * @return Insects the number of insects(resources) on the board
     */
    public Integer getInsects() {
        return Insects;
    }

    /**
     * This method returns the number of "animal" resources that are on the board
     *
     * @return Animals the number of animals(resources) on the board
     */
    public Integer getAnimals() {
        return Animals;
    }

    /**
     * This method returns the number of "mushroom" resources that are on the board
     *
     * @return Mushrooms the number of mushrooms(resources) on the board
     */
    public Integer getMushrooms() {
        return Mushrooms;
    }

    /**
     * This method returns the number of "leaf" resources that are on the board
     *
     * @return Leaves the number of leaves(resources) on the board
     */
    public Integer getLeaves() {
        return Leaves;
    }

    /**
     * This method returns the number of "quill" resources that are on the board
     *
     * @return Quills the number of quills(resources) on the board
     */
    public Integer getQuills() {
        return Quills;
    }

    /**
     * This method returns the number of "inkwell" resources that are on the board
     *
     * @return Inkwells the number of inkwells(resources) on the board
     */
    public Integer getInkwells() {
        return Inkwells;
    }

    /**
     * This method returns the number of "manuscript" resources that are on the board
     *
     * @return Manuscripts the number of manuscripts(resources) on the board
     */
    public Integer getManuscripts() {
        return Manuscripts;
    }

    /**
     * This method returns the deck of the game
     *
     * @return deck the deck of the game
     */
    public Deck getDeck() {
        return deck;
    }

    public Integer getPoints() {
        return Points;
    }

    public ArrayList<Card> getPlayedCards() {
        return playedCards;
    }

    public ArrayList<ObjectiveCard> getInGameObjectiveCards() {
        return inGameObjectiveCards;
    }

    public ObjectiveCard getSecretObjective() {
        return secretObjective;
    }

    public ArrayList<GoldCard> getVisibleGoldCards() {
        return VisibleGoldCards;
    }

    public ArrayList<ResourceCard> getVisibleResourceCards() {
        return VisibleResourceCards;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public Integer getPointsByGoldCards() {
        return PointsByGoldCards;
    }
}