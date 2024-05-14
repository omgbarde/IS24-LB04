package codex.lb04.View.Cli;

import codex.lb04.Model.*;
import codex.lb04.Network.client.ClientSocket;
import codex.lb04.View.View;

import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.System.out;

/**
 * This class represents the CLI view
 */
public class CliView extends View implements Runnable{
    private CliState state = CliState.HELLO;
    ArrayList<String> lobby ;
    ClientSocket clientSocket;
    BoardSceneControllerCLI bsc;
    private static Scanner scanner;

    /**
     * Constructor for the CliView
     */
    public CliView(){
        this.lobby = new ArrayList<>();
        this.bsc = new BoardSceneControllerCLI(this);
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        drawHelloScene();
        while(true){
            String input = scanner.nextLine().trim().toUpperCase();
            bsc.handleInput(input);
        }
    }

    /**
     * Prints a string to the standard output that welcomes a player.
     *
     */
    @Override
    public void drawHelloScene() {
        out.flush();
        out.println(" ___               _                            \n" +
                "(  _`\\            ( )                           \n" +
                "| ( (_)   _      _| |   __                      \n" +
                "| |  _  /'_`\\  /'_` | /'__`(`\\/')               \n" +
                "| (_( )( (_) )( (_| |(  ___/>  <                \n" +
                "(____/'`\\___/'`\\__,_)`\\____|_/\\_)               \n" +
                "                                                \n" +
                "                                                \n" +
                " _   _         _                     _          \n" +
                "( ) ( )       ( )_                  (_ ) _      \n" +
                "| `\\| |   _ _ | ,_) _   _  _ __  _ _ | |(_) ___ \n" +
                "| , ` | /'_` )| |  ( ) ( )( '__)'_` )| || /',__)\n" +
                "| |`\\ |( (_| || |_ | (_) || | ( (_| || || \\__, \\\n" +
                "(_) (_)`\\__,_)`\\__)`\\___/'(_) `\\__,_|___|_|____/");

        out.println("Welcome to Codex Naturalis Board Game!");
        out.println("Press 'C' to Create a Game or 'J' to Join a Game");
    }

    @Override
    public void drawLoginScene(){
        out.flush();
        out.println("If you want to go back press B, else press L to login");

    }

    @Override
    public void drawLobbyScene() {
        out.flush();
        for(String name:lobby){
            out.println(name + "\n");
        }
        out.println("If you want to go back press 'B', else press 'P' to start the game");
    }

    @Override
    public void drawCreateGameScene() {
        out.flush();
        out.println("press any key to continue and set the number of players and your username");
        out.println("press 'B' to go back");
    }

    @Override
    public void drawBoardScene() {
        out.flush();
        bsc.drawBoard();
    }
    @Override
    public void updateLobby(ArrayList<String> names) {
        lobby = names;
        System.out.println("Players in the lobby:\n");
        drawLobbyScene();
    }

    @Override
    public void drawCard(Card card) {

    }

    @Override
    public void displayAlert(String alert) {
        out.println(alert);
    }
    @Override
    public void updateGold(ArrayList<GoldCard> goldCards) {
        bsc.updateDrawableGold(goldCards);
    }

    @Override
    public void updateResource(ArrayList<ResourceCard> resourceCards) {

    }

    @Override
    public void updateHand(ArrayList<Card> hand) {

    }

    @Override
    public void updateCommonObjectives(ArrayList<ObjectiveCard> commonObjectives) {

    }


    @Override
    public void updateSecretObjectiveToChoose(ArrayList<ObjectiveCard> secretObjectives){

    }

    @Override
    public  void placeCard(Integer x , Integer y , Card card){

    }

    @Override
    public void updateSecretObjective(ObjectiveCard secretObjectives){

    }
    @Override
    public void updatePoints(ArrayList<Integer> points){

    }

    @Override
    public void updateInitialCardDisplay(InitialCard card) {

    }

    @Override
    public void setYourTurnText(){

    }

    @Override
    public void cleanYourTurnText(){

    }

    @Override
    public void deselectCard(){
    }

    public CliState getState() {
        return state;
    }

    public void setState(CliState state) {
        this.state = state;
    }
}
