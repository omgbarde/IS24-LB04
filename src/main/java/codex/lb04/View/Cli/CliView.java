package codex.lb04.View.Cli;

import codex.lb04.View.Cli.State.CliBoardState;
import codex.lb04.View.Cli.State.CliViewState;
import codex.lb04.View.View;

import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.System.out;

/**
 * This class represents the view of the game in CLI mode
 */
public class CliView extends View implements Runnable {
    private static Scanner scanner;
    private final ArrayList<String> chat;
    ArrayList<String> lobby;
    CliBoardModel boardModel;
    CliController controller;
    private CliViewState state = CliViewState.HELLO;

    /**
     * Constructor for the CliView
     */
    public CliView() {
        this.lobby = new ArrayList<>();
        this.boardModel = new CliBoardModel();
        this.controller = new CliController(this);
        this.chat = new ArrayList<>();
        scanner = new Scanner(System.in);
    }

    /**
     * This method is called when the view is started
     */
    public void run() {
        drawHelloScene();
        //game loop to keep the view running and waiting for input
        //noinspection InfiniteLoopStatement
        while (true) {
            String input = scanner.nextLine().trim().toUpperCase();
            controller.handleInput(input);
        }
    }

    /**
     * Prints a string to the standard output that welcomes a player.
     */
    @Override
    public void drawHelloScene() {
        printSpaces();
        out.println("""
                 ___               _                           \s
                (  _`\\            ( )                          \s
                | ( (_)   _      _| |   __                     \s
                | |  _  /'_`\\  /'_` | /'__`(`\\/')              \s
                | (_( )( (_) )( (_| |(  ___/>  <               \s
                (____/'`\\___/'`\\__,_)`\\____|_/\\_)              \s
                 _   _         _                     _         \s
                ( ) ( )       ( )_                  (_ ) _     \s
                | `\\| |   _ _ | ,_) _   _  _ __  _ _ | |(_) ___\s
                | , ` | /'_` )| |  ( ) ( )( '__)'_` )| || /',__)
                | |`\\ |( (_| || |_ | (_) || | ( (_| || || \\__, \\
                (_) (_)`\\__,_)`\\__)`\\___/'(_) `\\__,_|___|_|____/\s
                by Pitesse, Barde, AlexIlLeone, Brio""");
        out.println();
        out.println();
        out.println("Welcome to Codex Naturalis Board Game!");
        out.println("Press 'C' to Create a Game or 'J' to Join a Game");
    }

    /**
     * Draw the login scene
     */
    @Override
    public void drawLoginScene() {
        printSpaces();
        out.println("If you want to go back press B, else press L to login");

    }

    /**
     * Draw the lobby scene
     */
    @Override
    public void drawLobbyScene() {
        boardModel.resetBoard();
        setState(CliViewState.LOBBY);
        printSpaces();
        out.println("CODEX - LOBBY:");
        for (String name : lobby) {
            out.println("   " + name);
        }
        out.println("If you want to go back press 'B', else press 'P' to start the game");
    }

    /**
     * Draw the create game scene
     */
    @Override
    public void drawCreateGameScene() {
        printSpaces();
        out.println("press any key to continue and set the number of players and your username");
        out.println("press 'B' to go back");
    }


    /**
     * Draw the board scene
     */
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

    /**
     * Draw the hand from the local model
     */
    private void drawHand() {
        boardModel.printHand();
    }

    /**
     * Draw the initial card from the local model
     */
    private void drawInitialCard() {
        boardModel.printInitial();
    }

    /**
     * Display the commands that the player can use
     */
    private void displayCommands() {
        if (boardModel.getBoardState() == CliBoardState.PLACING || boardModel.getBoardState() == CliBoardState.SELECTING) {
            out.println("First press 0,1 or 2 to select a card from your hand then,   F) to flip it     P) to place it    B) to deselect it ");
            //out.println("normal turn: first place then draw");
        }
        if (boardModel.getBoardState() == CliBoardState.DRAWING) {
            out.println("First press 3,4,5,6,7 or 8 to draw a card");
            //out.println("normal turn: first place then draw");
        }
        if (boardModel.getBoardState() == CliBoardState.END) {
            out.println("finished! press E to pass the turn");
            //out.println("normal turn: first place then draw");
        }
        if (boardModel.getInitialCard() != null) {
            out.println("First things first select the face of the initial card by pressing F if you want to flip it, once you've done it place it by pressing P");
        }
        out.println("press 'C' to chat");
    }

    /**
     * Draw the turn label from the local model
     */
    private void drawTurnLabel() {
        out.println("-----------------------------------------------------------------------------------" +
                boardModel.getTurnLabel() +
                "-------------------------------------------------------------------------------------");
    }

    /**
     * Draw the visible cards from the local model
     */
    private void drawVisibleCards() {
        boardModel.printVisibleCards();

    }

    /**
     * Draw the played cards from the local model
     */
    private void drawPlayedCards() {
        boardModel.printGridMap();
    }

    /**
     * Draw the choices from the local model
     */
    private void drawChoices() {

        boardModel.displayChoices();
    }

    /**
     * Display the points from the local model
     */
    private void displayPoints() {
        out.println("Score: " + boardModel.printPoints());
    }

    /**
     * Draw the winner scene scaffold
     */
    @Override
    public void drawWinnerScene() {
        printSpaces();
        out.println("The game is over!");
        out.println("press any key to continue");
        out.println();
    }

    /**
     * print the winner
     *
     * @param winner the winner string already formatted passed by the controller
     */
    public void showWinners(String winner) {
        out.println(winner);
    }

    /**
     * Update the lobby and draw it
     *
     * @param lobby the arraylist of players in the lobby
     */
    @Override
    public void updateLobby(ArrayList<String> lobby) {
        this.lobby = lobby;
        System.out.println("Players in the lobby:\n");
        drawLobbyScene();
    }

    /**
     * Display an alert by redrawing the state and adding the alert
     *
     * @param alert the alert to be displayed
     */
    @Override
    public void displayAlert(String alert) {
        drawState();
        out.println(alert);
    }

    /**
     * Draw the current scene based on the state
     */
    private void drawState() {
        switch (state) {
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
        for (int i = 0; i < 100; i++) {
            out.println();
        }
    }

    /**
     * Update the chat
     *
     * @param message the message to be added
     */
    public void updateChat(String message) {
        chat.add(message);
    }

    /**
     * Show the chat
     */
    public void showChat() {
        printSpaces();

        out.println("----------------------------------------------------------------------------------- " +
                "CHAT: " +
                "-----------------------------------------------------------------------------------");
        for (String message : chat) {
            out.println("   " + message);
        }
        out.println("write a message below or press 'B' to go back");
    }

    //GETTER

    /**
     * Gets the state
     *
     * @return the state
     */
    public CliViewState getState() {
        return state;
    }

    /**
     * Sets the state
     *
     * @param state the state
     */
    public void setState(CliViewState state) {
        this.state = state;
    }

    //SETTER

    /**
     * Gets the board
     *
     * @return the board
     */
    public CliBoardModel getBoard() {
        return boardModel;
    }

}
