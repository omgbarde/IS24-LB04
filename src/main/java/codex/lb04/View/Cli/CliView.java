package codex.lb04.View.Cli;

import codex.lb04.Message.DrawMessage.DrawBoardMessage;
import codex.lb04.Message.GameMessage.CreateGameMessage;
import codex.lb04.Message.LoginMessage;
import codex.lb04.Model.*;
import codex.lb04.Network.client.ClientSocket;
import codex.lb04.Utils.ConnectionUtil;
import codex.lb04.View.View;

import java.util.Scanner;


import java.io.IOException;
import java.util.ArrayList;

import static java.lang.System.out;

/**
 * This class represents the CLI view
 */
public class CliView extends View{
    ArrayList<String> lobby ;

    ClientSocket clientSocket;
    BoardSceneControllerCLI bsc;
    private static Scanner scanner;

    /**
     * Constructor for the CliView
     */
    public CliView(){
        this.lobby = new ArrayList<>();
        this.scanner = new Scanner(System.in);
    }

    /**
     * Prints a string to the standard output that welcomes a player.
     *
     */
    @Override
    public void drawHelloScene() {

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

        String input = scanner.nextLine().trim().toUpperCase();

        switch (input) {
            case "C":
                drawCreateGameScene();
                break;
            case "J":
                drawLoginScene();
                break;
            default:
                System.out.println("Invalid input, please enter 'C' to create or 'J' to join a game.");
                drawHelloScene();
                break;
        }
    }

    @Override
    public void drawLoginScene(){
        out.println("If you want to go back press B, else press L to login");
        String input = scanner.nextLine().trim().toUpperCase();
        switch (input) {
            case "B":
                drawHelloScene();
                break;
            case "L":
                out.println("Enter your username:");
                String usr = scanner.nextLine().trim();
                out.println("Enter the server address");
                String addr = scanner.nextLine().trim();
                int port = ConnectionUtil.defaultPort;
                try {
                    port = Integer.parseInt(addr);
                } catch (NumberFormatException e) {
                    out.println("Using default port");
                }
                if (ConnectionUtil.checkValid(usr, addr, port)) {
                    try {
                        clientSocket = new ClientSocket(this, usr, addr, port);
                        bsc.setClientSocket(clientSocket);
                    } catch (IOException e) {
                        out.println("Server not available");
                        drawHelloScene();
                        break;
                    }
                    LoginMessage loginMessage = new LoginMessage(usr);
                    clientSocket.sendMessage((loginMessage));
                } else {
                    out.println("Invalid input, please enter a valid username and server address.");
                    drawLoginScene();
                }
                break;
            default:
                System.out.println("Invalid input");
                drawLoginScene();
                break;
        }

    }

    @Override
    public void drawLobbyScene() {
        System.out.println("Players in the lobby:\n");
        for(String name:lobby){
            out.println(name + "\n");
        }
        out.println("If you want to go back press 'B', else press 'P' to start the game");
        String input = scanner.nextLine().trim().toUpperCase();
        switch (input) {
            case "B":
                clientSocket.disconnect();
                drawHelloScene();
                break;
            case "P":
                clientSocket.sendMessage(new DrawBoardMessage(clientSocket.getUsername()));
                break;
            default:
                System.out.println("Invalid input, please enter 'B' to go back or 'P' to start the game.");
                drawLobbyScene();
                break;
        }
    }


    @Override
    public void drawCreateGameScene() {
        int num = 0;
        out.println("Enter the number of players (2-4):");
        String numPlayersChoice = scanner.nextLine().trim();
        try {
            num = Integer.parseInt(numPlayersChoice);
        } catch (NumberFormatException e) {
            out.println("Enter a valid number of players");
            drawCreateGameScene();
            return;
        }
        out.println("Enter your username:");
        String usr = scanner.nextLine().trim();
        if (ConnectionUtil.checkValid(num, usr)) {
            try {
                clientSocket = new ClientSocket(this, usr, ConnectionUtil.getLocalhost(), ConnectionUtil.defaultPort);
                bsc.setClientSocket(clientSocket);
            } catch (IOException e) {
                out.println("Server not available");
                drawHelloScene();
                return;
            }
            clientSocket.sendMessage(new CreateGameMessage(usr, ConnectionUtil.defaultPort, num));
        } else {
            out.println("Invalid input, please enter a valid number of players and username.");
            drawCreateGameScene();
        }
    }


    @Override
    public void drawBoardScene() {
        bsc.drawBoard();
    }
    @Override
    public void updateLobby(ArrayList<String> names) {
        lobby = names;
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
}
