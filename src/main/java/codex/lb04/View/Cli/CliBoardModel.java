package codex.lb04.View.Cli;

import codex.lb04.Model.*;
import codex.lb04.View.Cli.State.CliBoardState;

import java.util.ArrayList;

import static java.lang.System.out;

public class CliBoardModel {
    private static final int gridSize = 20;

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
        gridMap = new String[gridSize][gridSize][3];
        gridmapInit();
        selectedCard = null;
    }


    public void updateBoard(InitialCard initialCard) {
        //Not in use
    }

    /**
     * updates the board with the new card
     * @param x x coordinate
     * @param y y coordinate
     * @param card card to be placed
     */
    public void placeCard(Integer x, Integer y, Card card) {
        int k = gridSize/2;
        //inverse transforms the coordinates and places the card
        gridMap[-y+k][k+x] = CardRenderer.renderIngame(card);

    }

    /**
     * initializes the gridmap with placeholders and coordinates
     */
    public void gridmapInit(){
        int k = gridSize/2;
        for(int i = 0; i<gridSize;i++){
            for(int j = 0;j<gridSize;j++){
                //fills the grid in checkered pattern with transformed coordinates
                if((i%2==0 && j%2==0)||(i%2!=0 && j%2!=0)){
                    gridMap[i][j] = CardRenderer.placeHolder((j-k)+","+(k-i));
                }
                else gridMap[i][j] = CardRenderer.placeHolder("");
            }
        }
    }



    /**
     * deselect a card
     */
    public void deselectCard() {
        this.selectedCard = null;
        //deselct is called after placing (the initial card too) so you need to return to selecting only if you were in placing
        if(boardState == CliBoardState.PLACING) setBoardState(CliBoardState.SELECTING);
    }

    /**
     * gets the turn label
     * @return
     */
    public String getTurnLabel() {
        return turnLabel;
    }

    /**
     * sets the turn label
     * @param turnLabel
     */
    public void setTurnLabel(String turnLabel) {
        this.turnLabel = turnLabel;
    }

    /**
     * gets the hand
     * @return
     */
    public ArrayList<Card> getHand() {
        return hand;
    }

    /**
     * sets the hand
     * @param hand
     */
    public void setHand(ArrayList<Card> hand) {
        this.hand = hand;
    }

    /**
     * gets the visible gold cards
     * @return
     */
    public ArrayList<GoldCard> getVisibleGold() {
        return visibleGold;
    }

    /**
     * sets the visible gold cards
     * @param visibleGold
     */
    public void setVisibleGold(ArrayList<GoldCard> visibleGold) {
        this.visibleGold = visibleGold;
    }

    /**
     * gets the visible resource cards
     * @return
     */
    public ArrayList<ResourceCard> getVisibleResources() {
        return visibleResources;
    }

    /**
     * sets the visible resource cards
     * @param visibleResources
     */
    public void setVisibleResources(ArrayList<ResourceCard> visibleResources) {
        this.visibleResources = visibleResources;
    }

    /**
     * sets the objective cards
     * @return
     */
    public ArrayList<ObjectiveCard> getObjectiveCards() {
        return objectiveCards;
    }

    /**
     * sets the objective cards
     * @param objectiveCards
     */
    public void setObjectiveCards(ArrayList<ObjectiveCard> objectiveCards) {
        this.objectiveCards = objectiveCards;
    }

    /**
     * gets the secret objective
     * @return
     */
    public ObjectiveCard getSecretObjective() {
        return secretObjective;
    }

    /**
     * sets the secret objective
     * @param secretObjective
     */
    public void setSecretObjective(ObjectiveCard secretObjective) {
        this.secretObjective = secretObjective;
    }

    /**
     * gets the points
     * @return
     */
    public String printPoints() {
        String[] emojis = {"\uD83C\uDF44","\uD83E\uDD8A","\uD83E\uDD8B","\uD83C\uDF43","\uD83E\uDEB6","\uD83E\uDED9","\uD83D\uDCDC","points"};
        StringBuilder pointsBuilder = new StringBuilder();
        int p = 0;
        for(int i=0; i<8; i++){
            try {
                p = points.get(i);
            }
            catch (IndexOutOfBoundsException e){
                p = 0;
            }
            pointsBuilder.append(emojis[i]+": "+ p +" ");
        }
        return pointsBuilder.toString();
    }

    /**
     * sets the points
     * @param points
     */
    public void setPoints(ArrayList<Integer> points) {
        this.points = points;
    }

    /**
     * gets the grid map
     * @return
     */
    public String[][][] getGridMap() {
        return gridMap;
    }

    /**
     * sets the grid map
     * @param gridMap
     */
    public void setGridMap(String[][][] gridMap) {
        this.gridMap = gridMap;
    }

    /**
     * gets the selected card
     * @return
     */
    public Card getSelectedCard() {
        return selectedCard;
    }

    /**
     * sets the selected card
     * @param selectedCard
     */
    public void setSelectedCard(Card selectedCard) {
        this.selectedCard = selectedCard;
    }

    /**
     * gets the board state
     * @return
     */
    public CliBoardState getBoardState() {
        return boardState;
    }

    /**
     * sets the board state
     * @param boardState
     */
    public void setBoardState(CliBoardState boardState) {
        this.boardState = boardState;
    }

    /**
     * prints the grid map
     */
    public void printGridMap(){
        for(int i = 0; i < gridSize; i++){
            for(int k = 0; k < 3; k++){
                for(int j = 0; j < gridSize; j++){
                  System.out.print(gridMap[i][j][k]);
                }
                System.out.println();
            }
        }
    }


    /**
     * sets the initial card
     */
    public void setInitialCard(InitialCard initialCard) {
        this.initialCard = initialCard;
    }

    /**
     * prints the initial card
     */
    public void printInitial() {
        if(initialCard!=null) System.out.println(CardRenderer.printInHand(initialCard));
    }

    /**
     * flips the initial card
     */
    public void flipInitialCard() {
        if(initialCard!=null) initialCard.flip();
    }

    /**
     * flips the selected card
     */
    public void flipSelectedCard(){
        if(selectedCard != null && turnLabel.equals("YOUR TURN")) selectedCard.flip();
    }

    /**
     * displays the choices
     */
    public void displayChoices() {
        if(secretObjective==null) {
            System.out.println("secret objectives to choose from (press 1 or 2):");
            for (int i = 0; i < choices.size(); i++) {
                String renderedObjective = CardRenderer.rederObjective(choices.get(i).getID());
                System.out.print(i + 1 + ")" + renderedObjective + "     ");
            }
            System.out.println();
        }else{
            printObjectives();
        }
    }

    /**
     * sets the choices
     */
    public void setChoices(ArrayList<ObjectiveCard> secretObjectivesToChooseFrom){
        this.choices = secretObjectivesToChooseFrom;
    }

    /**
     * gets the choices
     * @return
     */
    public ArrayList<ObjectiveCard> getChoices(){
        return choices;
    }

    /**
     * prints the objectives
     */
    public void printObjectives() {
        System.out.println("Your common objectives are:");
        for (int i = 0; i < objectiveCards.size(); i++){
            String renderedObjective = CardRenderer.rederObjective(objectiveCards.get(i).getID());
            System.out.println("    "+(i+1)+ ")" + renderedObjective);
        }
        System.out.print("Your secret objective is:\n");
        out.println("   "+CardRenderer.rederObjective(secretObjective.getID()));
    }

    /**
     * prints the hand
     */
    public void printHand() {
        String[][] printableHand = new String[3][5];
        String[] toAdd;
        out.println("Your hand is:");
        for (int i = 0; i < hand.size(); i++){
            Card toRender = hand.get(i);
            if(toRender ==null) toAdd = new String[]{"          ","          ","          ","          ","          "};
            else toAdd = CardRenderer.renderInHand(toRender);
            printableHand[i] = toAdd;
        }
        for (int j = 0; j < 5; j++){
            for(int i = 0; i < 3; i++){
                String toPrint = printableHand[i][j];
                if(toPrint == null) toPrint = new String("");
                out.print(toPrint);
            }
            out.println();
        }
        out.println("         0                   1                   2" );

    }

    /**
     * prints the visible cards
     */
    public void printVisibleCards() {
        String[][] printableCards = new String[6][5];
        String[] toAdd;
        out.println("Drawable cards:");
        for (int i = 0; i < visibleResources.size(); i++){
            Card toRender = visibleResources.get(i);
            toAdd = CardRenderer.renderInHand(toRender);
            printableCards[i] = toAdd;
        }
        for (int i = 0; i < visibleGold.size(); i++){
            Card toRender = visibleGold.get(i);
            toAdd = CardRenderer.renderInHand(toRender);
            printableCards[i+3] = toAdd;
        }

        for (int j = 0; j < 5; j++){
            for(int i = 0; i < 6; i++){
                String toPrint = printableCards[i][j];
                if(toPrint == null) toPrint = new String("");
                out.print(toPrint);
            }
            out.println();
        }
        out.println("          3                   4                   5                   6                   7                   8" );

    }

    /**
     * gets the initial card
     */
    public InitialCard getInitialCard(){
        return initialCard;
    }
}
