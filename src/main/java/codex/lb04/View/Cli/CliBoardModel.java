package codex.lb04.View.Cli;

import codex.lb04.Model.*;
import codex.lb04.View.Cli.State.CliBoardState;

import java.util.ArrayList;

import static java.lang.System.out;

/**
 * simplified model to keep a copy of the game board when playing in CLI mode
 */
public class CliBoardModel {
    private static final int gridSize = 20;

    private CliBoardState boardState;
    private String turnLabel;
    private ArrayList<Card> hand;
    private ArrayList<GoldCard> visibleGold;
    private ArrayList<ResourceCard> visibleResources;
    private ArrayList<ObjectiveCard> objectiveCards;
    private ArrayList<Card> playedCards;
    private ObjectiveCard secretObjective;
    private InitialCard initialCard;
    private ArrayList<ObjectiveCard> choices;
    private ArrayList<Integer> points;
    private String[][][] gridMap;
    private Card selectedCard;

    /**
     * constructor for the CliBoardModel
     */
    public CliBoardModel() {
        resetBoard();
    }

    /**
     * method to reset the board at startup and every time a player is in the lobby (before restarting a game)
     */
    public void resetBoard(){
        this.turnLabel = "not your turn";
        this.boardState = CliBoardState.END;
        this.hand = new ArrayList<>();
        this.playedCards = new ArrayList<>();
        this.visibleGold = new ArrayList<>();
        this.visibleResources = new ArrayList<>();
        this.objectiveCards = new ArrayList<>();
        this.choices = new ArrayList<>();
        this.points = new ArrayList<>();
        this.gridMap = new String[gridSize][gridSize][3];
        gridMapInit();
        this.selectedCard = null;
    }

    /**
     * updates the board by placing a card
     *
     * @param x    x coordinate
     * @param y    y coordinate
     * @param card card to be placed
     */
    public void placeCard(Integer x, Integer y, Card card) {
        int k = gridSize / 2;
        //inverse transforms the coordinates and places the card, if it's going to cover a corner
        //the covered card will be rendered again
        for (Card c : playedCards) {
            //sets corners as not visible for the card being covered by the one you are placing
            if (c != null) {
                if (c.getX() == x + 1 && c.getY() == y + 1) {
                    c.getShownFace().getLowerLeft().setCovered(c);
                    gridMap[-c.getY() + k][k + c.getX()] = CardRenderer.renderInGame(c);
                } else if (c.getX() == x + 1 && c.getY() == y - 1) {
                    c.getShownFace().getUpperLeft().setCovered(c);
                    gridMap[-c.getY() + k][k + c.getY()] = CardRenderer.renderInGame(c);
                } else if (c.getX() == x - 1 && c.getY() == y + 1) {
                    c.getShownFace().getLowerRight().setCovered(c);
                    gridMap[-c.getY() + k][k + c.getX()] = CardRenderer.renderInGame(c);
                } else if (c.getX() == x - 1 && c.getY() == y - 1) {
                    c.getShownFace().getUpperRight().setCovered(c);
                    gridMap[-c.getY() + k][k + c.getX()] = CardRenderer.renderInGame(c);
                }
            }
        }
        playedCards.add(card);
        card.setCoordinates(x, y);

        gridMap[-y + k][k + x] = CardRenderer.renderInGame(card);
    }

    /**
     * initializes the grid map with placeholders and coordinates
     */
    public void gridMapInit() {
        int k = gridSize / 2;
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                //fills the grid in a checkered pattern with transformed x and y to match the game system of coordinates
                if ((i % 2 == 0 && j % 2 == 0) || (i % 2 != 0 && j % 2 != 0)) {
                    gridMap[i][j] = CardRenderer.renderPlaceHolder((j - k) + "," + (k - i));
                } else gridMap[i][j] = CardRenderer.renderPlaceHolder("");
            }
        }
    }

    /**
     * deselect a card
     */
    public void deselectCard() {
        this.selectedCard = null;
        //deselect is called after placing (the initial card too) so you need to return to selecting only if you were in placing
        if (boardState == CliBoardState.PLACING) setBoardState(CliBoardState.SELECTING);
    }

    /**
     * gets the points
     *
     * @return the arraylist of all resources and points
     */
    public String printPoints() {
        //emojis for the resources visible, the order is consistent with the order of the points array (server convention)
        String[] emojis = {"\uD83C\uDF44", "\uD83D\uDC3A", "\uD83E\uDD8B", "\uD83C\uDF43", "\uD83E\uDEB6", "\uD83E\uDED9", "\uD83D\uDCDC", "points"};
        StringBuilder pointsBuilder = new StringBuilder();
        int p;
        for (int i = 0; i < 8; i++) {
            try {
                p = points.get(i);
            } catch (IndexOutOfBoundsException e) {
                p = 0;
            }
            pointsBuilder.append(emojis[i]).append(": ").append(p).append(" ");
        }
        return pointsBuilder.toString();
    }

    /**
     * prints the grid map
     */
    public void printGridMap() {
        //prints the grid map by the rows and iterating over the 3 layers of the grid
        //(nested for loops are inverted to print each row 3 times, once every component of the card)
        for (int i = 0; i < gridSize; i++) {
            for (int k = 0; k < 3; k++) {
                for (int j = 0; j < gridSize; j++) {
                    System.out.print(gridMap[i][j][k]);
                }
                System.out.println();
            }
        }
    }

    /**
     * prints the initial card
     */
    public void printInitial() {
        if (initialCard != null) System.out.println(CardRenderer.printInHand(initialCard));
    }

    /**
     * flips the initial card
     */
    public void flipInitialCard() {
        if (initialCard != null) initialCard.flip();
    }

    /**
     * displays the choices
     */
    public void displayChoices() {
        if (secretObjective == null) {
            System.out.println("secret objectives to choose from (press 1 or 2):");
            for (int i = 0; i < choices.size(); i++) {
                String renderedObjective = CardRenderer.renderObjective(choices.get(i).getID());
                System.out.print(i + 1 + ")" + renderedObjective + "     ");
            }
            System.out.println();
        } else {
            printObjectives();
        }
    }

    /**
     * prints the objectives
     */
    public void printObjectives() {
        System.out.println("Your common objectives are:");
        for (int i = 0; i < objectiveCards.size(); i++) {
            String renderedObjective = CardRenderer.renderObjective(objectiveCards.get(i).getID());
            System.out.println("    " + (i + 1) + ")" + renderedObjective);
        }
        System.out.print("Your secret objective is:\n");
        out.println("   " + CardRenderer.renderObjective(secretObjective.getID()));
    }

    /**
     * prints the hand
     */
    public void printHand() {
        String[][] printableHand = new String[3][5];
        String[] toAdd;
        out.println("Your hand is:");
        for (int i = 0; i < hand.size(); i++) {
            Card toRender = hand.get(i);
            //if the card is null it adds spaces to the hand
            if (toRender == null)
                toAdd = new String[]{"          ", "          ", "          ", "          ", "          "};
            else toAdd = CardRenderer.renderInHand(toRender);
            printableHand[i] = toAdd;
        }
        for (int j = 0; j < 5; j++) {
            for (int i = 0; i < 3; i++) {
                String toPrint = printableHand[i][j];
                if (toPrint == null) toPrint = "";
                out.print(toPrint);
            }
            out.println();
        }
        //prints the index of the cards in the hand to help the player choose
        out.println("         0                   1                   2");

    }

    /**
     * prints the visible cards
     */
    public void printVisibleCards() {
        String[][] printableCards = new String[6][5];
        String[] toAdd;
        out.println("Drawable cards:");
        for (int i = 0; i < visibleResources.size(); i++) {
            Card toRender = visibleResources.get(i);
            toAdd = CardRenderer.renderInHand(toRender);
            printableCards[i] = toAdd;
        }
        for (int i = 0; i < visibleGold.size(); i++) {
            Card toRender = visibleGold.get(i);
            toAdd = CardRenderer.renderInHand(toRender);
            printableCards[i + 3] = toAdd;
        }

        for (int j = 0; j < 5; j++) {
            for (int i = 0; i < 6; i++) {
                String toPrint = printableCards[i][j];
                //if the card is null it adds spaces to the screen
                if (toPrint == null) toPrint = "";
                out.print(toPrint);
            }
            out.println();
        }
        //prints the index of the drawable cards to help the player choose
        out.println("          3                   4                   5                   6                   7                   8");

    }

    /**
     * gets the initial card
     */
    public InitialCard getInitialCard() {
        return initialCard;
    }

    //GETTER

    /**
     * gets the turn label
     *
     * @return turn label
     */
    public String getTurnLabel() {
        return turnLabel;
    }

    /**
     * gets the hand
     *
     * @return the arraylist of cards in the hand
     */
    public ArrayList<Card> getHand() {
        return hand;
    }


    /**
     * sets the objective cards
     *
     * @return the arraylist of objective cards
     */
    public ArrayList<ObjectiveCard> getObjectiveCards() {
        return objectiveCards;
    }

    /**
     * gets the choices
     *
     * @return the arraylist of objective cards to choose from
     */
    public ArrayList<ObjectiveCard> getChoices() {
        return choices;
    }

    /**
     * gets the secret objective
     *
     * @return the secret objective card
     */
    public ObjectiveCard getSecretObjective() {
        return secretObjective;
    }

    /**
     * gets the selected card
     *
     * @return the currently selected card
     */
    public Card getSelectedCard() {
        return selectedCard;
    }

    /**
     * gets the board state
     *
     * @return the current state of the board
     */
    public CliBoardState getBoardState() {
        return boardState;
    }

    //SETTER

    /**
     * sets the turn label
     *
     * @param turnLabel turn label
     */
    public void setTurnLabel(String turnLabel) {
        this.turnLabel = turnLabel;
    }

    /**
     * sets the initial card
     */
    public void setInitialCard(InitialCard initialCard) {
        this.initialCard = initialCard;
    }

    /**
     * sets the hand
     *
     * @param hand the arraylist of cards in the hand
     */
    public void setHand(ArrayList<Card> hand) {
        this.hand = hand;
    }

    /**
     * sets the visible gold cards
     *
     * @param visibleGold the arraylist of gold cards to set as visible
     */
    public void setVisibleGold(ArrayList<GoldCard> visibleGold) {
        this.visibleGold = visibleGold;
    }

    /**
     * sets the visible resource cards
     *
     * @param visibleResources the arraylist of resource cards to set as visible
     */
    public void setVisibleResources(ArrayList<ResourceCard> visibleResources) {
        this.visibleResources = visibleResources;
    }

    /**
     * sets the choices
     */
    public void setChoices(ArrayList<ObjectiveCard> secretObjectivesToChooseFrom) {
        this.choices = secretObjectivesToChooseFrom;
    }

    /**
     * sets the objective cards
     *
     * @param objectiveCards the arraylist of objective cards to set
     */
    public void setObjectiveCards(ArrayList<ObjectiveCard> objectiveCards) {
        this.objectiveCards = objectiveCards;
    }

    /**
     * sets the secret objective
     *
     * @param secretObjective the secret objective card to set
     */
    public void setSecretObjective(ObjectiveCard secretObjective) {
        this.secretObjective = secretObjective;
    }

    /**
     * sets the selected card
     *
     * @param selectedCard the card to set as selected
     */
    public void setSelectedCard(Card selectedCard) {
        this.selectedCard = selectedCard;
    }

    /**
     * sets the points
     *
     * @param points the arraylist of all resources and points
     */
    public void setPoints(ArrayList<Integer> points) {
        this.points = points;
    }

    /**
     * sets the board state
     *
     * @param boardState the state to set the board to
     */
    public void setBoardState(CliBoardState boardState) {
        this.boardState = boardState;
    }

}