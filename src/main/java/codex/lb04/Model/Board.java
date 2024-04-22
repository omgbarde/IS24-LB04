package codex.lb04.Model;

import codex.lb04.Model.Enumerations.Color;
import codex.lb04.Model.Enumerations.ResourceType;

import java.util.ArrayList;
import java.util.Objects;

/**
 * This class represents the board of the game
 */
public class Board {
    private ArrayList<Card> inGameCards = new ArrayList<Card>();
    private ArrayList<ObjectiveCard> inGameObjectiveCards = new ArrayList<ObjectiveCard>();
    private ObjectiveCard secretObjective;
    private ArrayList<ResourceCard> ResourceCards = new ArrayList<ResourceCard>();
    private ArrayList<GoldCard> GoldCards = new ArrayList<GoldCard>();
    private ArrayList<GoldCard> VisibleGoldCards = new ArrayList<>();
    private ArrayList<ResourceCard> VisibleResourceCards = new ArrayList<>();
    private Integer Insects;
    private Integer Animals;
    private Integer Mushrooms;
    private Integer Leaves;
    private Integer Quills;
    private Integer Inkwells;
    private Integer Manuscripts;
    private Deck deck;
    private Integer Points;
    private Integer PointsByGoldCards;

    /**
     * Default constructor
     */
    public Board() {
        this.deck = Deck.getInstance();
        this.GoldCards = deck.getGoldCards();
        this.ResourceCards = deck.getResourceCards();
        this.VisibleGoldCards = deck.setVisibleGoldCards();
        //this.inGameObjectiveCards = deck.getObjectiveCards();
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
        for (Card card : inGameCards) {
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
     * This method returns the number of corner you're going to cover placing a card
     */
    public Integer getCornerCovered(Card toBePlaced, Integer x, Integer y){
        int corner_covered = 0;
        for (Card card : inGameCards){
            if(card.getX() == x + 1 && card.getY() == y + 1){
                corner_covered += 1;
            }
            if(card.getX() == x + 1 && card.getY() == y - 1){
                corner_covered += 1;
            }
            if(card.getX() == x - 1 && card.getY() == y + 1){
                corner_covered += 1;
            }
            if (card.getX() == x - 1 && card.getY() == y - 1){
                corner_covered += 1;
            }
        }
        return corner_covered;
    }

    /**
     * This method places a card on the board
     *
     * @param toBePlaced the card to be placed on the board
     */
    public void placeCard(Card toBePlaced, Integer x, Integer y) {
        if (canBePlaced(x, y, toBePlaced)) {
            for (Card card : inGameCards) {
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
            inGameCards.add(toBePlaced);
            updateResources();
            if (toBePlaced.getClass() == GoldCard.class) {
                updateGoldCardsPoints((GoldCard) toBePlaced);
            }
            pointsUpdate();
        }
        //magari uno switch
    }

    /**
     * updates the points a player has received only by placing gold cards
     *
     * @param toBePlaced the gold card that has been placed
     */
    public void updateGoldCardsPoints(GoldCard toBePlaced) {
        switch (toBePlaced.getID()) {
            case 41:
                this.PointsByGoldCards += toBePlaced.getPoints()*getQuills();
                break;
            case 42:
                this.PointsByGoldCards += toBePlaced.getPoints()*getInkwells();
                break;
            case 43:
                this.PointsByGoldCards += toBePlaced.getPoints()*getManuscripts();
                break;
            case 44:
                this.PointsByGoldCards += getCornerCovered(toBePlaced,toBePlaced.getX(),toBePlaced.getY())*2;
                break;
            case 45:
                this.PointsByGoldCards += getCornerCovered(toBePlaced,toBePlaced.getX(),toBePlaced.getY())*2;
                break;
            case 46:
                this.PointsByGoldCards += getCornerCovered(toBePlaced,toBePlaced.getX(),toBePlaced.getY())*2;
                break;
            case 47:
                this.PointsByGoldCards += 3;
                break;
            case 48:
                this.PointsByGoldCards += 3;
                break;
            case 49:
                this.PointsByGoldCards += 3;
                break;
            case 50:
                this.PointsByGoldCards += 5;
                break;
            case 51:
                this.PointsByGoldCards += toBePlaced.getPoints()*getQuills();
                break;
            case 52:
                this.PointsByGoldCards += toBePlaced.getPoints()*getManuscripts();
                break;
            case 53:
                this.PointsByGoldCards += toBePlaced.getPoints()*getInkwells();
                break;
            case 54:
                this.PointsByGoldCards += getCornerCovered(toBePlaced,toBePlaced.getX(),toBePlaced.getY())*2;
                break;
            case 55:
                this.PointsByGoldCards += getCornerCovered(toBePlaced,toBePlaced.getX(),toBePlaced.getY())*2;
                break;
            case 56:
                this.PointsByGoldCards += getCornerCovered(toBePlaced,toBePlaced.getX(),toBePlaced.getY())*2;
                break;
            case 57:
                this.PointsByGoldCards += 3;
                break;
            case 58:
                this.PointsByGoldCards += 3;
                break;
            case 59:
                this.PointsByGoldCards += 3;
                break;
            case 60:
                this.PointsByGoldCards += 5;
                break;
            case 61:
                this.PointsByGoldCards += toBePlaced.getPoints()*getInkwells();
                break;
            case 62:
                this.PointsByGoldCards += toBePlaced.getPoints()*getManuscripts();
                break;
            case 63:
                this.PointsByGoldCards += toBePlaced.getPoints()*getQuills();
                break;
            case 64:
                this.PointsByGoldCards += getCornerCovered(toBePlaced,toBePlaced.getX(), toBePlaced.getY())*2;
                break;
            case 65:
                this.PointsByGoldCards += getCornerCovered(toBePlaced,toBePlaced.getX(), toBePlaced.getY())*2;
                break;
            case 66:
                this.PointsByGoldCards += getCornerCovered(toBePlaced,toBePlaced.getX(), toBePlaced.getY())*2;
                break;
            case 67:
                this.PointsByGoldCards += 3;
                break;
            case 68:
                this.PointsByGoldCards += 3;
                break;
            case 69:
                this.PointsByGoldCards += 3;
                break;
            case 70:
                this.PointsByGoldCards += 5;
                break;
            case 71:
                this.PointsByGoldCards += toBePlaced.getPoints()*getQuills();
                break;
            case 72:
                this.PointsByGoldCards += toBePlaced.getPoints()*getManuscripts();
                break;
            case 73:
                this.PointsByGoldCards += toBePlaced.getPoints()*getInkwells();
                break;
            case 74:
                this.PointsByGoldCards += getCornerCovered(toBePlaced,toBePlaced.getX(),toBePlaced.getY())*2;
                break;
            case 75:
                this.PointsByGoldCards += getCornerCovered(toBePlaced,toBePlaced.getX(),toBePlaced.getY())*2;
                break;
            case 76:
                this.PointsByGoldCards += getCornerCovered(toBePlaced,toBePlaced.getX(),toBePlaced.getY())*2;
                break;
            case 77:
                this.PointsByGoldCards += 3;
                break;
            case 78:
                this.PointsByGoldCards += 3;
                break;
            case 79:
                this.PointsByGoldCards += 3;
                break;
            case 80:
                this.PointsByGoldCards += 5;
                break;
        }
    }

    /**
     * updates the resources of a player when a card is placed
     */
    public void updateResources() {
        setZeroResources();
        for (Card card : inGameCards) {
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
        for (Card card : inGameCards) {
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
        for (Card card : inGameCards) {
            if (card.getClass() == ResourceCard.class) {
                this.Points += ((ResourceCard) card).getPoints();
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
            case 95 -> (Integer) objectiveCard.getPoints() * this.getMushrooms() / 3;
            case 96 -> (Integer) objectiveCard.getPoints() * this.getLeaves() / 3;
            case 97 -> (Integer) objectiveCard.getPoints() * this.getAnimals() / 3;
            case 98 -> (Integer) objectiveCard.getPoints() * this.getInsects() / 3;
            case 99 ->
                    (Integer) objectiveCard.getPoints() * Math.min(this.getManuscripts(), Math.min(this.getInkwells(), this.getQuills()));
            case 100 -> (Integer) objectiveCard.getPoints() * this.getManuscripts() / 2;
            case 101 -> (Integer) objectiveCard.getPoints() * this.getInkwells() / 2;
            case 102 -> (Integer) objectiveCard.getPoints() * this.getQuills() / 2;
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
            for (Card card : inGameCards) {
                if (this.conditionCheckOnPositions(objectiveCard, card)) {
                    this.Points += objectiveCard.getPoints();
                }
            }
        }
        for (Card card : inGameCards) {
            if (this.conditionCheckOnPositions(secretObjective, card)) {
                this.Points += secretObjective.getPoints();
            }
        }
        for (ObjectiveCard objectiveCard : inGameObjectiveCards) {
            this.Points += conditionCheckResources(objectiveCard);
        }

        this.Points += conditionCheckResources(secretObjective);
    }

    //GETTERS

    /**
     * This method returns the resource cards that can be picked
     *
     * @return ResourceCards the resource cards that can be picked
     */
    public ArrayList<ResourceCard> getResourceCards() {
        return ResourceCards;
    }

    /**
     * This method returns the ingame cards
     *
     * @return ingameCards the cards that are on the board
     */
    public ArrayList<Card> getIngameCards() {
        return inGameCards;
    }

    /**
     * This method returns the gold cards that can be picked
     *
     * @return Goldcards the gold cards that can be picked
     */
    public ArrayList<GoldCard> getGoldCards() {
        return GoldCards;
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
}