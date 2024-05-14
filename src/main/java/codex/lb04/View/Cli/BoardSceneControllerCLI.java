package codex.lb04.View.Cli;

import codex.lb04.Model.Card;
import codex.lb04.Model.GoldCard;
import codex.lb04.Model.ObjectiveCard;
import codex.lb04.Model.ResourceCard;
import codex.lb04.Network.client.ClientSocket;

import java.util.ArrayList;

import static java.lang.System.out;


public class BoardSceneControllerCLI {
    private String turn = "not your turn";
    private ClientSocket clientSocket;
    private ArrayList<Card> hand = new ArrayList<>();
    private ArrayList<GoldCard> visibleGold = new ArrayList<>();
    private ArrayList<ResourceCard> visibleResources = new ArrayList<>();
    private ArrayList<ObjectiveCard> objectiveCards = new ArrayList<>();
    private ArrayList<Integer> points = new ArrayList<>();
    private Card[][] gridMap;
    private Card selectedCard = null;
    private CliView view;

    /**
     * Constructor of the board scene controller
     *
     * @param view the view
     */
    public BoardSceneControllerCLI(CliView view) {
        //this.gridMap = new HashMap<>();
        this.view = view;
        //this.stageReference = view.getStageReference();
    }

    public void setClientSocket(ClientSocket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void positionAvailable(){
        System.out.println("Position available are:");

    }

    public void updateDrawableGold(ArrayList<GoldCard> goldCards) {

        for (int i = 0; i < goldCards.size(); i++) {
            System.out.println(CardRenderer.cardToString(goldCards.get(i)));
            }
        }

    public void drawBoard() {
        out.println("_____________________________________________________________________________");
        out.println(turn);
        drawHand(hand);
        out.println("_____________________________________________________________________________");
        drawVisibleResources();
        drawVisibleGold();
        drawPlayedCards();
        drawObjectives();
        displayPoints();
    }
    private void drawHand(ArrayList<Card> hand){
        out.println("Your hand is:");
        for (int i = 0; i < hand.size(); i++) {
            out.println(i + ": " + CardRenderer.cardToString(hand.get(i)));
        }
    }
    private void  drawVisibleResources(){

    }
    private void drawVisibleGold(){

    }
    private void drawPlayedCards(){

    }
    private void drawObjectives(){

    }
    private void displayPoints(){

    }

}

