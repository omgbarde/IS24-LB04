package codex.lb04.Model;

import codex.lb04.Message.DrawMessage.UpdateHandMessage;
import codex.lb04.Message.DrawMessage.UpdateInitialCardDisplayMessage;
import codex.lb04.Message.DrawMessage.UpdatePointsMessage;
import codex.lb04.Message.DrawMessage.UpdateSecretObjectiveMessage;
import codex.lb04.Message.GameMessage.EndTurnMessage;
import codex.lb04.Message.GameMessage.PlaceCardMessage;
import codex.lb04.Model.Enumerations.Color;
import codex.lb04.Model.Enumerations.ResourceType;
import codex.lb04.Observer.Observable;

import java.util.ArrayList;
import java.util.Objects;

/**
 * This class represents the board of the game
 */
public class Board extends Observable {
    //cards played
    private final ArrayList<Card> playedCards = new ArrayList<>();

    //objectives
    private ArrayList<ObjectiveCard> CommonObjectives;
    private ObjectiveCard secretObjective;
    private final ArrayList<ObjectiveCard> secretObjectiveToPick;

    //cards
    private final ArrayList<Card> hand;
    private InitialCard initialCard;

    //resources
    private Integer insects;
    private Integer animals;
    private Integer mushrooms;
    private Integer leaves;
    private Integer quills;
    private Integer inkwells;
    private Integer manuscripts;

    //points
    private Integer points;
    private Integer pointsByGoldCards;

    //deck
    private final Deck deck;

    //boolean to check if the player chose the secret objective
    private boolean secretObjectiveChosen = false;

    //boolean to check if the player chose the initial card
    private boolean initialCardChosen = false;

    //username of the player
    private String username;

    //boolean to check if the player has placed a card and drawn a card
    private boolean hasPlacedACard = false;
    private boolean hasDrawnACard = false;

    /**
     * Default constructor of the board, adds an observer to it and sets all the resources to zero
     */

    public Board() {
        this.deck = Deck.getInstance();
        Game game = Game.getInstance();
        this.CommonObjectives = game.getCommonObjectives();
        this.secretObjectiveToPick = new ArrayList<>();
        this.secretObjectiveToPick.add(this.deck.drawObjective());
        this.secretObjectiveToPick.add(this.deck.drawObjective());
        this.hand = new ArrayList<>();
        this.insects = 0;
        this.animals = 0;
        this.mushrooms = 0;
        this.leaves = 0;
        this.quills = 0;
        this.inkwells = 0;
        this.manuscripts = 0;
        this.points = 0;
        this.pointsByGoldCards = 0;
    }

    /**
     * This method sets the resources to zero
     */
    public void setZeroResources() {
        this.insects = 0;
        this.animals = 0;
        this.mushrooms = 0;
        this.leaves = 0;
        this.quills = 0;
        this.inkwells = 0;
        this.manuscripts = 0;
    }

    /**
     * draws the first cards of the game
     */
    public void drawInitial() {
        this.hand.add(this.deck.drawGold());
        this.deck.updateVisibleGold(2);
        this.hand.add(this.deck.drawResource());
        this.deck.updateVisibleResource(2);
        this.hand.add(this.deck.drawResource());
        this.deck.updateVisibleResource(2);
        ArrayList<Card> cardsToSend = cloneHand();
        notifyObserver(new UpdateHandMessage(username, cardsToSend));
    }

    /**
     * draws a gold card between the choices
     *
     * @param pick the choice of the player
     */
    public void drawGoldCard(Integer pick) {
        if (hasPlacedACard && !hasDrawnACard) {
            switch (pick) {
                case 0, 1:
                    this.hand.add(this.deck.getVisibleGoldCards().get(pick));
                    break;
                case 2:
                    this.hand.add(this.deck.drawGold());
                    break;
                default: //should never be reached
                    System.out.println("invalid choice");
                    return;
            }
            this.deck.updateVisibleGold(pick);
            ArrayList<Card> cardsToSend = cloneHand();
            notifyObserver(new UpdateHandMessage(username, cardsToSend));
            this.hasDrawnACard = true;
        }
    }

    /**
     * draws a resource card between the choices
     *
     * @param pick the choice of the player
     */
    public void drawResourceCard(Integer pick) {
        if (hasPlacedACard && !hasDrawnACard) {
            switch (pick) {
                case 0, 1:
                    this.hand.add(this.deck.getVisibleResourceCards().get(pick));
                    break;
                case 2:
                    this.hand.add(this.deck.drawResource());
                    break;
                default: //should never be reached
                    System.out.println("invalid choice");
                    return;
            }
            this.deck.updateVisibleResource(pick);
            ArrayList<Card> toSend = cloneHand();
            notifyObserver(new UpdateHandMessage(username, toSend));
            this.hasDrawnACard = true;
        }
    }

    /**
     * This method flips a card in the hand
     *
     * @param toFlip the card to flip
     */
    public void flipCardInHand(Card toFlip) {
        for (Card card : hand) {
            if (card.equals(toFlip)) {
                card.flip();
            }
        }
        ArrayList<Card> toSend = cloneHand();
        notifyObserver(new UpdateHandMessage(username, toSend)); // broadcast
    }

    /**
     * This method clones the hand of the player
     *
     * @return the cloned hand
     */
    public ArrayList<Card> cloneHand() {
        ArrayList<Card> clone = new ArrayList<>();
        for (Card card : hand) {
            clone.add(card.clone());
        }
        return clone;
    }

    /**
     * This method places a card on the board
     *
     * @param toBePlaced the card to be placed on the board
     */
    public void placeCard(Card toBePlaced, Integer x, Integer y) {
        if (!hasPlacedACard && (toBePlaced.getClass() == ResourceCard.class || toBePlaced.getClass() == GoldCard.class) || ((toBePlaced.getClass() == InitialCard.class) && x == 0 && y == 0)) {
            if (canBePlaced(x, y, toBePlaced)) {
                for (Card card : playedCards) {
                    Integer boardX = card.getX();
                    Integer boardY = card.getY();
                    Face toBeCovered = card.getShownFace();

                    if (boardX == x + 1 && boardY == y + 1) {
                        toBeCovered.getLowerLeft().setCovered(toBePlaced);
                    }
                    if (boardX == x + 1 && boardY == y - 1) {
                        toBeCovered.getUpperLeft().setCovered(toBePlaced);
                    }
                    if (boardX == x - 1 && boardY == y + 1) {
                        toBeCovered.getLowerRight().setCovered(toBePlaced);
                    }
                    if (boardX == x - 1 && boardY == y - 1) {
                        toBeCovered.getUpperRight().setCovered(toBePlaced);
                    }
                }
                toBePlaced.setCoordinates(x, y);
                playedCards.add(toBePlaced);

                hasPlacedACard = true;
                //notify the observer with the move
                Card toBePlacedClone = (toBePlaced.clone());
                notifyObserver(new PlaceCardMessage(this.username, x, y, toBePlacedClone));

                Integer idToRemove = toBePlacedClone.getID();
                for (Card card : hand) {
                    if (Objects.equals(card.getID(), idToRemove)) {
                        hand.remove(card);
                        break;
                    }
                }

                //notify the observer with the hand
                ArrayList<Card> toSend = cloneHand();
                notifyObserver(new UpdateHandMessage(this.username, toSend));

                updateResources();
                if (toBePlaced.getClass() == GoldCard.class) {
                    updateGoldCardsPoints((GoldCard) toBePlaced);
                }
                pointsUpdate();
            }
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
        if (toBePlaced.getClass() == GoldCard.class && toBePlaced.getShownFace() == toBePlaced.getFront()) {
            if (insects < ((GoldCard) toBePlaced).getInsects_needed()) {
                return false;
            }
            if (animals < ((GoldCard) toBePlaced).getAnimals_needed()) {
                return false;
            }
            if (leaves < ((GoldCard) toBePlaced).getLeaf_needed()) {
                return false;
            }
            if (mushrooms < ((GoldCard) toBePlaced).getMushroom_needed()) {
                return false;
            }
        }

        //checks if the card can be placed in the specified coordinates
        if (getCard(x, y) == null) {
            if (playedCards.isEmpty()) {
                return true;
            }
            if (noSurroundingCars(x, y)) {
                return false;
            }
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
        } else {
            return false;
        }
    }

    /**
     * This method checks if there are no surrounding cards
     *
     * @param x the x coordinate
     * @param y the y coordinate
     * @return true if there are no surrounding cards, false otherwise
     */
    private boolean noSurroundingCars(Integer x, Integer y) {
        return (getCard(x + 1, y + 1) == null &&
                getCard(x + 1, y - 1) == null &&
                getCard(x - 1, y + 1) == null &&
                getCard(x - 1, y - 1) == null);
    }

    /**
     * This method returns the number of corner you're going to cover placing a card
     */
    public Integer getCornerCovered(Integer x, Integer y) {
        int corner_covered = 0;
        for (Card card : playedCards) {
            Integer boardX = card.getX();
            Integer boardY = card.getY();

            if (boardX == x + 1 && boardY == y + 1) {
                corner_covered += 1;
            }
            if (boardX == x + 1 && boardY == y - 1) {
                corner_covered += 1;
            }
            if (boardX == x - 1 && boardY == y + 1) {
                corner_covered += 1;
            }
            if (boardX == x - 1 && boardY == y - 1) {
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
        if (toBePlaced.getShownFace() == toBePlaced.getFront()) {
            switch (toBePlaced.getID()) {
                case 41, 51, 63, 71:
                    this.pointsByGoldCards += toBePlaced.getPoints() * (this.quills);
                    break;
                case 42, 53, 61, 73:
                    this.pointsByGoldCards += toBePlaced.getPoints() * (this.inkwells);
                    break;
                case 43, 52, 62, 72:
                    this.pointsByGoldCards += toBePlaced.getPoints() * (this.manuscripts);
                    break;
                case 44, 45, 46, 54, 55, 56, 64, 65, 66, 74, 75, 76:
                    this.pointsByGoldCards += getCornerCovered(toBePlaced.getX(), toBePlaced.getY()) * 2;
                    break;
                case 47, 48, 49, 57, 58, 59, 67, 68, 69, 77, 78, 79:
                    this.pointsByGoldCards += 3;
                    break;
                case 50, 60, 70, 80:
                    this.pointsByGoldCards += 5;
                    break;
            }
        }
    }

    /**
     * updates the resources of a player when a card is placed
     */
    public void updateResources() {
        setZeroResources();
        for (Card card : playedCards) {
            if (card.getShownFace().getCentralResources() != null) {
                for (ResourceType resource : card.getShownFace().getCentralResources()) {
                    switch (resource) {
                        case ResourceType.ANIMAL:
                            this.animals++;
                            break;
                        case ResourceType.INSECT:
                            this.insects++;
                            break;
                        case ResourceType.LEAF:
                            this.leaves++;
                            break;
                        case ResourceType.MUSHROOM:
                            this.mushrooms++;
                            break;
                        case ResourceType.QUILL:
                            this.quills++;
                            break;
                        case ResourceType.INKWELL:
                            this.inkwells++;
                            break;
                        case ResourceType.MANUSCRIPT:
                            this.manuscripts++;
                            break;
                        default: //should never be reached
                            break;

                    }
                }
            }
            for (Corner corner : card.getShownFace().getCorners()) {
                if (!corner.isCovered()) {
                    if (corner.getResource() != null) {
                        switch (corner.getResource()) {
                            case ResourceType.ANIMAL:
                                this.animals++;
                                break;
                            case ResourceType.INSECT:
                                this.insects++;
                                break;
                            case ResourceType.LEAF:
                                this.leaves++;
                                break;
                            case ResourceType.MUSHROOM:
                                this.mushrooms++;
                                break;
                            case ResourceType.QUILL:
                                this.quills++;
                                break;
                            case ResourceType.INKWELL:
                                this.inkwells++;
                                break;
                            case ResourceType.MANUSCRIPT:
                                this.manuscripts++;
                                break;
                            default: //should never be reached
                                break;
                        }
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
     * This method checks if the conditions of the objective cards regarding the resources quantities are met
     *
     * @param objectiveCard the objective card to check
     * @return the points that the player has received
     */
    public Integer conditionCheckResources(ObjectiveCard objectiveCard) {
        return switch (objectiveCard.getID()) {
            case 95 -> objectiveCard.getPoints() * (this.mushrooms / 3);
            case 96 -> objectiveCard.getPoints() * (this.leaves / 3);
            case 97 -> objectiveCard.getPoints() * (this.animals / 3);
            case 98 -> objectiveCard.getPoints() * (this.insects / 3);
            case 99 -> objectiveCard.getPoints() * Math.min(this.manuscripts, Math.min(this.inkwells, this.quills));
            case 100 -> objectiveCard.getPoints() * (this.manuscripts / 2);
            case 101 -> objectiveCard.getPoints() * (this.inkwells / 2);
            case 102 -> objectiveCard.getPoints() * (this.quills / 2);
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
    public boolean conditionCheckOnPositionRelatedObjectives(ObjectiveCard objectiveCard, Card card) {
        int cardId = objectiveCard.getID();
        Color cardColor = card.getColor();

        switch (cardId) {
            case 87:
                if (cardColor == Color.RED) {
                    if (getCard(card.getX() + 1, card.getY() + 1) != null && getCard(card.getX() - 1, card.getY() - 1) != null) {
                        if (getCard(card.getX() + 1, card.getY() + 1).getColor() == Color.RED && getCard(card.getX() - 1, card.getY() - 1).getColor() == Color.RED && !card.isUsedForPositionalObjectives() && !getCard(card.getX() + 1, card.getY() + 1).isUsedForPositionalObjectives() && !getCard(card.getX() - 1, card.getY() - 1).isUsedForPositionalObjectives()) {
                            card.setUsedForPositionalObjectives(true);
                            getCard(card.getX() + 1, card.getY() + 1).setUsedForPositionalObjectives(true);
                            getCard(card.getX() - 1, card.getY() - 1).setUsedForPositionalObjectives(true);
                            return true;
                        }
                    }
                }
                break;
            case 88:
                if (cardColor == Color.GREEN) {
                    if (getCard(card.getX() + 1, card.getY() - 1) != null && getCard(card.getX() - 1, card.getY() + 1) != null) {
                        if (getCard(card.getX() + 1, card.getY() - 1).getColor() == Color.GREEN && getCard(card.getX() - 1, card.getY() + 1).getColor() == Color.GREEN && !card.isUsedForPositionalObjectives() && !getCard(card.getX() + 1, card.getY() - 1).isUsedForPositionalObjectives() && !getCard(card.getX() - 1, card.getY() + 1).isUsedForPositionalObjectives()) {
                            card.setUsedForPositionalObjectives(true);
                            getCard(card.getX() + 1, card.getY() - 1).setUsedForPositionalObjectives(true);
                            getCard(card.getX() - 1, card.getY() + 1).setUsedForPositionalObjectives(true);
                            return true;
                        }
                    }
                }
                break;
            case 89:
                if (cardColor == Color.BLUE) {
                    if (getCard(card.getX() + 1, card.getY() + 1) != null && getCard(card.getX() - 1, card.getY() - 1) != null) {
                        if (getCard(card.getX() + 1, card.getY() + 1).getColor() == Color.BLUE && getCard(card.getX() - 1, card.getY() - 1).getColor() == Color.BLUE && !card.isUsedForPositionalObjectives() && !getCard(card.getX() + 1, card.getY() + 1).isUsedForPositionalObjectives() && !getCard(card.getX() - 1, card.getY() - 1).isUsedForPositionalObjectives()) {
                            card.setUsedForPositionalObjectives(true);
                            getCard(card.getX() + 1, card.getY() + 1).setUsedForPositionalObjectives(true);
                            getCard(card.getX() - 1, card.getY() - 1).setUsedForPositionalObjectives(true);
                            return true;
                        }
                    }
                }
                break;
            case 90:
                if (cardColor == Color.PURPLE) {
                    if (getCard(card.getX() + 1, card.getY() - 1) != null && getCard(card.getX() - 1, card.getY() + 1) != null) {
                        if (getCard(card.getX() + 1, card.getY() - 1).getColor() == Color.PURPLE && getCard(card.getX() - 1, card.getY() + 1).getColor() == Color.PURPLE && !card.isUsedForPositionalObjectives() && !getCard(card.getX() + 1, card.getY() - 1).isUsedForPositionalObjectives() && !getCard(card.getX() - 1, card.getY() + 1).isUsedForPositionalObjectives()) {
                            card.setUsedForPositionalObjectives(true);
                            getCard(card.getX() + 1, card.getY() - 1).setUsedForPositionalObjectives(true);
                            getCard(card.getX() - 1, card.getY() + 1).setUsedForPositionalObjectives(true);
                            return true;
                        }
                    }
                }
                break;
            case 91:
                if (cardColor == Color.RED) {
                    if (getCard(card.getX(), card.getY() - 2) != null && getCard(card.getX() + 1, card.getY() - 3) != null) {
                        if (getCard(card.getX(), card.getY() - 2).getColor() == Color.RED && getCard(card.getX() + 1, card.getY() - 3).getColor() == Color.GREEN && !card.isUsedForPositionalObjectives() && !getCard(card.getX(), card.getY() - 2).isUsedForPositionalObjectives() && !getCard(card.getX() + 1, card.getY() - 3).isUsedForPositionalObjectives()) {
                            card.setUsedForPositionalObjectives(true);
                            getCard(card.getX(), card.getY() - 2).setUsedForPositionalObjectives(true);
                            getCard(card.getX() + 1, card.getY() - 3).setUsedForPositionalObjectives(true);
                            return true;
                        }
                    }
                }
                break;
            case 92:
                if (cardColor == Color.GREEN) {
                    if (getCard(card.getX(), card.getY() - 2) != null && getCard(card.getX() - 1, card.getY() - 3) != null) {
                        if (getCard(card.getX(), card.getY() - 2).getColor() == Color.GREEN && getCard(card.getX() - 1, card.getY() - 3).getColor() == Color.PURPLE && !card.isUsedForPositionalObjectives() && !getCard(card.getX(), card.getY() - 2).isUsedForPositionalObjectives() && !getCard(card.getX() - 1, card.getY() - 3).isUsedForPositionalObjectives()) {
                            card.setUsedForPositionalObjectives(true);
                            getCard(card.getX(), card.getY() - 2).setUsedForPositionalObjectives(true);
                            getCard(card.getX() - 1, card.getY() - 3).setUsedForPositionalObjectives(true);
                            return true;
                        }
                    }
                }
                break;
            case 93:
                if (cardColor == Color.BLUE) {
                    if (getCard(card.getX(), card.getY() + 2) != null && getCard(card.getX() + 1, card.getY() + 3) != null) {
                        if (getCard(card.getX(), card.getY() + 2).getColor() == Color.BLUE && getCard(card.getX() + 1, card.getY() + 3).getColor() == Color.RED && !card.isUsedForPositionalObjectives() && !getCard(card.getX(), card.getY() + 2).isUsedForPositionalObjectives() && !getCard(card.getX() + 1, card.getY() + 3).isUsedForPositionalObjectives()) {
                            card.setUsedForPositionalObjectives(true);
                            getCard(card.getX(), card.getY() + 2).setUsedForPositionalObjectives(true);
                            getCard(card.getX() + 1, card.getY() + 3).setUsedForPositionalObjectives(true);
                            return true;
                        }
                    }
                }
                break;
            case 94:
                if (cardColor == Color.PURPLE) {
                    if (getCard(card.getX(), card.getY() + 2) != null && getCard(card.getX() - 1, card.getY() + 3) != null) {
                        if (getCard(card.getX(), card.getY() + 2).getColor() == Color.PURPLE && getCard(card.getX() - 1, card.getY() + 3).getColor() == Color.BLUE && !card.isUsedForPositionalObjectives() && !getCard(card.getX(), card.getY() + 2).isUsedForPositionalObjectives() && !getCard(card.getX() - 1, card.getY() + 3).isUsedForPositionalObjectives()) {
                            card.setUsedForPositionalObjectives(true);
                            getCard(card.getX(), card.getY() + 2).setUsedForPositionalObjectives(true);
                            getCard(card.getX() - 1, card.getY() + 3).setUsedForPositionalObjectives(true);
                            return true;
                        }
                    }
                }
                break;
        }
        return false;
    }

    /**
     * This method updates the points of the player when a new resource card is placed
     */
    public void pointsUpdate() {
        this.points = this.pointsByGoldCards;
        for (Card card : playedCards) {
            if (card.getClass() == ResourceCard.class || card.getClass() == Card.class) {
                if (card.getPoints() != null && card.getShownFace() == card.getFront()) {
                    this.points += card.getPoints();
                }
            }
        }
        notifyPointsUpdate();
    }

    /**
     * This method calculate the points a player receives with objectives cards
     */
    public void finalPointsUpdate() {
        for (ObjectiveCard objectiveCard : CommonObjectives) {
            for (Card card : playedCards) {
                if (this.conditionCheckOnPositionRelatedObjectives(objectiveCard, card)) {
                    this.points += objectiveCard.getPoints();
                }
            }
        }
        for (Card card : playedCards) {
            if (this.conditionCheckOnPositionRelatedObjectives(secretObjective, card)) {
                this.points += secretObjective.getPoints();
            }
        }
        for (ObjectiveCard objectiveCard : CommonObjectives) {
            this.points += conditionCheckResources(objectiveCard);
        }

        this.points += conditionCheckResources(secretObjective);

        notifyPointsUpdate();
    }

    /**
     * This method checks if a card is in the hand
     *
     * @param card the card to check
     * @return true if the card is in the hand, false otherwise
     */
    public boolean isInHand(Card card) {
        for (Card hand : hand) {
            if (hand.equals(card)) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method checks if the player chose a secret objective
     *
     * @return true if the player chose a secret objective (!=null), false otherwise
     */
    public boolean isSecretObjectiveChosen() {
        return secretObjectiveChosen;
    }

    /**
     * this method checks how many objectives a player has completed
     */
    public Integer checkNumberObjectives() {
        int count = 0;
        for (Card card : playedCards) {
            if (conditionCheckOnPositionRelatedObjectives(this.getSecretObjective(), card)) {
                count += 1;
                break;
            }
        }
        if (conditionCheckResources(this.getSecretObjective()) > 0) {
            count += 1;
        }
        for (ObjectiveCard objectiveCard : CommonObjectives) {
            for (Card card : playedCards) {
                if (conditionCheckOnPositionRelatedObjectives(objectiveCard, card)) {
                    count += 1;
                    break;
                }
            }
            if (conditionCheckResources(objectiveCard) > 0) {
                count += 1;
            }
        }
        return count;
    }

    /**
     * This method notifies the observer of the points update
     */
    public void notifyPointsUpdate() {
        ArrayList<Integer> points = new ArrayList<>();
        points.add(this.mushrooms);
        points.add(this.animals);
        points.add(this.insects);
        points.add(this.leaves);
        points.add(this.quills);
        points.add(this.inkwells);
        points.add(this.manuscripts);
        points.add(this.points);
        notifyObserver(new UpdatePointsMessage(this.username, points));
    }

    /**
     * This method notifies the observer that the turn has ended
     */
    public void notifyEndTurn() {
        notifyObserver(new EndTurnMessage(this.username));
    }


    //GETTERS

    /**
     * This method returns the deck of the game
     *
     * @return deck the deck of the game
     */
    public Deck getDeck() {
        return deck;
    }

    /**
     * This method returns the initial card
     *
     * @return the initial card
     */
    public InitialCard getInitialCard() {
        return this.initialCard;
    }

    /**
     * getter for the points
     *
     * @return the points
     */
    public Integer getPoints() {
        return points;
    }

    /**
     * getter for the common objectives
     *
     * @return the common objectives as an array list
     */
    public ArrayList<ObjectiveCard> getCommonObjectives() {
        return CommonObjectives;
    }

    public ObjectiveCard getSecretObjective() {
        return secretObjective;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public ArrayList<ObjectiveCard> getSecretObjectiveToPick() {
        return secretObjectiveToPick;
    }

    public boolean isInitialCardChosen() {
        return initialCardChosen;
    }


    //SETTERS

    /**
     * This method sets the initial card
     */
    public void setInitialCard() {
        this.initialCard = deck.drawInitialCard();
        notifyObserver(new UpdateInitialCardDisplayMessage(this.username, initialCard));
    }

    public void setInitialCardChosen(boolean initialCardChosen) {
        this.initialCardChosen = initialCardChosen;
    }

    /**
     * sets the common objectives
     *
     * @param CommonObjectives the common objectives
     */
    public void setCommonObjectives(ArrayList<ObjectiveCard> CommonObjectives) {
        this.CommonObjectives = CommonObjectives;
    }

    /**
     * This method sets the secret objective of the player
     *
     * @param pick the secret objective to set
     */
    public void setSecretObjective(Integer pick) {
        if (secretObjectiveToPick.isEmpty()) {
            throw new IllegalStateException("Deck is empty");
        }
        switch (pick) {
            case 0, 1:
                this.secretObjective = this.secretObjectiveToPick.get(pick);
                break;
            default: //should never be reached
                System.out.println("invalid choice");
                return;
        }

        secretObjectiveChosen = true;
        notifyObserver(new UpdateSecretObjectiveMessage(username, this.secretObjective));
    }

    public void setHasPlacedACard(boolean hasPlacedACard) {
        this.hasPlacedACard = hasPlacedACard;
    }

    public void setHasDrawnACard(boolean hasDrawnACard) {
        this.hasDrawnACard = hasDrawnACard;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}