package codex.lb04.View.Cli;

import codex.lb04.View.Cli.State.CliViewState;
import codex.lb04.View.View;

import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.System.out;

/**
 * This class represents the CLI view
 */
public class CliView extends View implements Runnable{
    private CliViewState state = CliViewState.HELLO;
    ArrayList<String> lobby ;
    CliBoardModel boardModel;
    CliController controller;
    private static Scanner scanner;

    /**
     * Constructor for the CliView
     */
    public CliView(){
        this.lobby = new ArrayList<>();
        this.boardModel = new CliBoardModel();
        this.controller = new CliController(this);
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        drawHelloScene();
        while(true){
            String input = scanner.nextLine().trim().toUpperCase();
            controller.handleInput(input);
        }
    }

    /**
     * Prints a string to the standard output that welcomes a player.
     *
     */
    @Override
    public void drawHelloScene() {
        printSpaces();
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
        printSpaces();
        out.println("If you want to go back press B, else press L to login");

    }

    @Override
    public void drawLobbyScene() {
        printSpaces();
        for(String name:lobby){
            out.println(name + "\n");
        }
        out.println("If you want to go back press 'B', else press 'P' to start the game");
    }

    @Override
    public void drawCreateGameScene() {
        printSpaces();
        out.println("press any key to continue and set the number of players and your username");
        out.println("press 'B' to go back");
    }

    @Override
    public void drawBoardScene() {
        printSpaces();
        drawPlayedCards();
        drawVisibleResourceCards();
        drawVisibleGoldCards();
        drawObjectives();
        drawInitialCard();
        displayPoints();
        drawTurnLabel();
        displayCommands();
    }

    private void drawInitialCard() {
        boardModel.printInitial();
    }

    private void displayCommands() {
        out.println("0-9) to select a card            F) to flip             x,y) to place");
        out.println("first place initial card, then choose secret objective");
        out.println("normal turn: first place then draw");
    }

    private void drawTurnLabel() {
        out.println("----------------------------------------------------------------" + boardModel.getTurnLabel() + "--------------------------------");
    }

    private void drawVisibleResourceCards(){

    }
    private void drawVisibleGoldCards(){

    }
    private void drawPlayedCards(){
        boardModel.printGridMap();
    }
    private void drawObjectives(){
        boardModel.printObjectives();
        boardModel.displayChoices();
    }
    private void displayPoints(){

    }

    @Override
    public void updateLobby(ArrayList<String> lobby) {
        this.lobby = lobby;
        System.out.println("Players in the lobby:\n");
        drawLobbyScene();
    }


    @Override
    public void displayAlert(String alert) {
        out.println(alert);
    }


    private void printSpaces() {
        for(int i = 0; i < 50; i++){
            out.println();
        }
    }

    public CliViewState getState() {
        return state;
    }

    public void setState(CliViewState state) {
        this.state = state;
    }

    public CliBoardModel getBoard() {
        return boardModel;
    }

}
