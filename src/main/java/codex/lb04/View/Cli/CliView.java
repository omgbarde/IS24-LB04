package codex.lb04.View.Cli;

import codex.lb04.View.Cli.State.CliBoardState;
import codex.lb04.View.Cli.State.CliViewState;
import codex.lb04.View.View;
import javafx.concurrent.Task;

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
    private ArrayList<String> chat;
    private Task task;

    private static Scanner scanner;

    /**
     * Constructor for the CliView
     */
    public CliView(){
        this.lobby = new ArrayList<>();
        this.boardModel = new CliBoardModel();
        this.controller = new CliController(this);
        this.chat = new ArrayList<>();
        this.scanner = new Scanner(System.in);
    }

    public void setTask(Runnable runnable){
        this.task = new Task() {
            @Override
            protected Object call() throws Exception {
                runnable.run();
                return null;
            }
        };
    }

    /**
     * This method is called when the view is started
     */
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
                "(_) (_)`\\__,_)`\\__)`\\___/'(_) `\\__,_|___|_|____/ \n" +
                "by Pitesse, Barde, AlexIlLeone, Brio");

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
        setState(CliViewState.LOBBY);
        printSpaces();
        out.println("CODEX - LOBBY:");
        for(String name:lobby){
            out.println("   " + name);
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
        drawHand();
        drawVisibleCards();
        drawChoices();
        drawInitialCard();
        displayPoints();
        drawTurnLabel();
        displayCommands();
    }


    private void drawHand() {
        boardModel.printHand();
    }

    private void drawInitialCard() {
        boardModel.printInitial();
    }

    /**
     * Display the commands that the player can use
     */
    private void displayCommands() {
       if(boardModel.getBoardState()== CliBoardState.PLACING || boardModel.getBoardState()== CliBoardState.SELECTING){
           out.println("First press 0,1 or 2 to select a card from your hand then,   F) to flip it     P) to place it    B) to deselect it ");
           //out.println("normal turn: first place then draw");
       }
        if(boardModel.getBoardState()== CliBoardState.DRAWING){
            out.println("First press 3,4,5,6,7 or 8 to draw a card");
            //out.println("normal turn: first place then draw");
        }
        if(boardModel.getBoardState()== CliBoardState.END){
            out.println("finished! press E to pass the turn");
            //out.println("normal turn: first place then draw");
        }
        if(boardModel.getInitialCard() != null) {
            out.println("First things first select the face of the initial card by pressing F if you want to flip it, once you've done it place it by pressing P");
        }
        out.println("press 'C' to chat");
    }

    /**
     * Draw the turn label
     */
    private void drawTurnLabel() {
        out.println("-----------------------------------------------------------------------------------" +
                    boardModel.getTurnLabel() +
                    "-------------------------------------------------------------------------------------");
    }

    /**
     * Draw the visible cards
     */
    private void drawVisibleCards(){
        boardModel.printVisibleCards();

    }

    /**
     * Draw the played cards
     */
    private void drawPlayedCards(){
        boardModel.printGridMap();
    }

    /**
     * Draw the choices
     */
    private void drawChoices(){

        boardModel.displayChoices();
    }

    /**
     * Draw the objectives
     */
    private void drawObjectives(){
        boardModel.printObjectives();
    }

    /**
     * Display the points
     */
    private void displayPoints(){
        out.println("Score: " + boardModel.printPoints());
    }

    @Override
    public void updateLobby(ArrayList<String> lobby) {
        this.lobby = lobby;
        System.out.println("Players in the lobby:\n");
        drawLobbyScene();
    }


    @Override
    public void displayAlert(String alert) {
        drawState();
        out.println(alert);
    }

    /**
     * Draw the current state
     */
    private void drawState() {
        switch (state){
            case HELLO:
                drawHelloScene();
                break;
            case LOGIN:
                drawLoginScene();
                break;
            case LOBBY:
                drawLobbyScene();
                break;
            case CREATE_GAME:
                drawCreateGameScene();
                break;
            case BOARD:
                drawBoardScene();
                break;
            case END:
                break;
        }
    }


    /**
     * Print spaces to the standard output
     */
    private void printSpaces() {
        for(int i = 0; i < 50; i++){
            out.println();
        }
    }

    /**
     * Update the chat
     * @param message the message to be added
     */
    public void updateChat(String message) {
        chat.add(message);
    }

    /**
     * Show the chat
     */
    public void showChat(){
        printSpaces();

        out.println("----------------------------------------------------------------------------------- " +
                "CHAT: " +
                "-----------------------------------------------------------------------------------");
        for (String message: chat){
            out.println("   "+message);
        }
        out.println("write a message below or press 'B' to go back");
    }

    /**
     * Get the state
     * @return the state
     */
    public CliViewState getState() {
        return state;
    }

    /**
     * Set the state
     * @param state the state
     */
    public void setState(CliViewState state) {
        this.state = state;
    }

    /**
     * Get the board
     * @return the board
     */
    public CliBoardModel getBoard() {
        return boardModel;
    }

    public void showWinners(String winner) {
        printSpaces();
        out.println(winner);
        out.println("press any key to continue");
    }
}
