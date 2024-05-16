package codex.lb04.View.Cli;

import codex.lb04.Model.*;
import codex.lb04.View.Cli.State.CliBoardState;

import java.util.ArrayList;

import static java.lang.System.out;

public class CliBoardModel {
    private CliBoardState boardState;
    private String turnLabel;
    private ArrayList<Card> hand;
    private ArrayList<GoldCard> visibleGold;
    private ArrayList<ResourceCard> visibleResources;
    private ArrayList<ObjectiveCard> objectiveCards;
    private ObjectiveCard secretObjective;
    private InitialCard initialCard;
    private ArrayList<ObjectiveCard> choices;
    private ArrayList<Integer> points;
    private String[][][] gridMap;
    private Card selectedCard;

    public CliBoardModel() {
        turnLabel = "not your turn";
        boardState = CliBoardState.END;
        hand = new ArrayList<>();
        visibleGold = new ArrayList<>();
        visibleResources = new ArrayList<>();
        objectiveCards = new ArrayList<>();
        choices = new ArrayList<>();
        points = new ArrayList<>();
        gridMap = new String[20][20][3];
        gridmapInit();
        selectedCard = null;
    }


    public void updateBoard(InitialCard initialCard) {
        //TODO
    }

    public void placeCard(Integer x, Integer y, Card card) {
        //TODO
    }

    public void gridmapInit(){
        for(int i = 0; i<20;i++){
            for(int j = 0;j<20;j++){
                String transformedCoordinates = transform(i,j);

                gridMap[i][j] = CardRenderer.placeHolder(transformedCoordinates);

            }
        }
    }

    //TODO: fix tranform
    private String transform(int i, int j){
        int k = 10;
        int z = 10;
        if(i%2==0){
            if(j%2==0){
                z--;
                return (i-k) +","+ (j+z);
            }
            else {
                k--;
                return "";
            }
        }
        else{
            if(j%2!=0){
                z--;
                return (i-k) +","+ (j+z);
            }
            else {
                k--;
                return "";
            }
        }

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

    public String[][][] getGridMap() {
        return gridMap;
    }

    public void setGridMap(String[][][] gridMap) {
        this.gridMap = gridMap;
    }

    public Card getSelectedCard() {
        return selectedCard;
    }

    public void setSelectedCard(Card selectedCard) {
        this.selectedCard = selectedCard;
    }

    public CliBoardState getBoardState() {
        return boardState;
    }

    public void setBoardState(CliBoardState boardState) {
        this.boardState = boardState;
    }

    public void printGridMap(){
        for(int i = 0; i < 20; i++){
            for(int k = 0; k < 3; k++){
                for(int j = 0; j < 20; j++){
                  System.out.print(gridMap[i][j][k]);
                }
                System.out.println();
            }
        }
    }


    public void setInitialCard(InitialCard initialCard) {
        this.initialCard = initialCard;
    }

    public void printInitial() {
        if(initialCard!=null) System.out.println(CardRenderer.printInHand(initialCard));
    }

    public void flipInitialCard() {
        if(initialCard!=null && turnLabel.equals("YOUR TURN")) initialCard.flip();
    }

    public void displayChoices() {
        System.out.println("secret objectives to choose from:");
        for (int i = 0; i < choices.size(); i++) {
            String renderedObjective = CardRenderer.rederObjective(choices.get(i).getID());
            System.out.print(i+1+")" + renderedObjective + "     ");
        }
        System.out.println();
    }

    public void setChoices(ArrayList<ObjectiveCard> secretObjectivesToChooseFrom){
        this.choices = secretObjectivesToChooseFrom;
    }

    public void printObjectives() {
        System.out.println("Your objectives are:");
        for (int i = 0; i < objectiveCards.size(); i++){
            String renderedObjective = CardRenderer.rederObjective(objectiveCards.get(i).getID());
            System.out.println(i+1+")" + renderedObjective);
        }
        out.println();
    }

    public void printHand() {
        String[][] printableHand = new String[3][5];
        out.println("Your hand is:");
        for (int i = 0; i < hand.size(); i++){
            String[] toAdd = CardRenderer.renderInHand(hand.get(i));
            printableHand[i] = toAdd;
        }
        for (int j = 0; j < 5; j++){
            for(int i = 0; i < 3; i++){
                out.print(printableHand[i][j]);
            }
            out.println();
        }
    }

    public void printVisibleCards() {
        String[][] printableCards = new String[6][5];
        String[] toAdd;
        out.println("Drawable cards:");
        for (int i = 0; i < visibleResources.size(); i++){
            toAdd = CardRenderer.renderInHand(visibleResources.get(i));
            printableCards[i] = toAdd;
        }
        for (int i = 0; i < visibleGold.size(); i++){
            toAdd = CardRenderer.renderInHand(visibleGold.get(i));
            printableCards[i+3] = toAdd;
        }

        for (int j = 0; j < 5; j++){
            for(int i = 0; i < 6; i++){
                out.print(printableCards[i][j]);
            }
            out.println();
        }

    }
}
