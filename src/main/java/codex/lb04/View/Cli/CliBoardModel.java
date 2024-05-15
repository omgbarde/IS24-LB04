package codex.lb04.View.Cli;

import codex.lb04.Model.*;

import java.util.ArrayList;

public class CliBoardModel {
    private String turnLabel;
    private ArrayList<Card> hand;
    private ArrayList<GoldCard> visibleGold;
    private ArrayList<ResourceCard> visibleResources;
    private ArrayList<ObjectiveCard> objectiveCards;
    private ObjectiveCard secretObjective;
    private ArrayList<Integer> points;
    private Card[][] gridMap;
    private Card selectedCard;

    public CliBoardModel() {
        turnLabel = "not your turn";
        hand = new ArrayList<>();
        visibleGold = new ArrayList<>();
        visibleResources = new ArrayList<>();
        objectiveCards = new ArrayList<>();
        points = new ArrayList<>();
        gridMap = new Card[20][20];
        selectedCard = null;
    }


    public void updateBoard(InitialCard initialCard) {
        //TODO
    }

    public void placeCard(Integer x, Integer y, Card card) {
        //TODO
    }

    public void deselectCard() {
        this.selectedCard = null;
    }

    public String getTurnLabel() {
        return turnLabel;
    }

    public void setTurnLabel(String turnLabel) {
        this.turnLabel = turnLabel;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public void setHand(ArrayList<Card> hand) {
        this.hand = hand;
    }

    public ArrayList<GoldCard> getVisibleGold() {
        return visibleGold;
    }

    public void setVisibleGold(ArrayList<GoldCard> visibleGold) {
        this.visibleGold = visibleGold;
    }

    public ArrayList<ResourceCard> getVisibleResources() {
        return visibleResources;
    }

    public void setVisibleResources(ArrayList<ResourceCard> visibleResources) {
        this.visibleResources = visibleResources;
    }

    public ArrayList<ObjectiveCard> getObjectiveCards() {
        return objectiveCards;
    }

    public void setObjectiveCards(ArrayList<ObjectiveCard> objectiveCards) {
        this.objectiveCards = objectiveCards;
    }

    public ObjectiveCard getSecretObjective() {
        return secretObjective;
    }

    public void setSecretObjective(ObjectiveCard secretObjective) {
        this.secretObjective = secretObjective;
    }

    public ArrayList<Integer> getPoints() {
        return points;
    }

    public void setPoints(ArrayList<Integer> points) {
        this.points = points;
    }

    public Card[][] getGridMap() {
        return gridMap;
    }

    public void setGridMap(Card[][] gridMap) {
        this.gridMap = gridMap;
    }

    public Card getSelectedCard() {
        return selectedCard;
    }

    public void setSelectedCard(Card selectedCard) {
        this.selectedCard = selectedCard;
    }
}
