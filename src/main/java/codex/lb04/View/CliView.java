package codex.lb04.View;

import codex.lb04.Message.DrawMessage.DrawBoardMessage;
import codex.lb04.Message.LoginMessage;
import codex.lb04.Model.*;
import codex.lb04.Network.client.ClientSocket;
import codex.lb04.Utils.ConnectionUtil;
import java.util.Scanner;


import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static java.lang.System.in;
import static java.lang.System.out;

/**
 * This class represents the CLI view
 */
public class CliView extends View {

    ClientSocket clientSocket;
    BoardSceneControllerCLI bsc;
    private static Scanner scanner = new Scanner(System.in);

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
    public void drawLoginScene() throws IOException {
        out.println("Enter your username:");
        String usr = scanner.nextLine().trim();
        out.println("Enter the server address and port:");
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
                return;
            }
            LoginMessage loginMessage = new LoginMessage(usr);
            clientSocket.sendMessage((loginMessage));
        } else {
            out.println("Enter valid username, address and port");
        }
    }

    @Override
    public void drawLobbyScene() {
        System.out.println("Players in the lobby:" + clientSocket.getUsername());
        out.println("If you want to go back press 'B', else press 'P' to start the game");
        String input = scanner.nextLine().trim().toUpperCase();
        switch (input) {
            case "B":
                clientSocket.disconnect();
                drawHelloScene();
                break;
            case "P":
                drawCreateGameScene();
                break;
            default:
                System.out.println("Invalid input, please enter 'B' to go back or 'P' to start the game.");
                drawLobbyScene();
                break;
        }
    }


    @Override
    public void drawCreateGameScene() {

        System.out.println("Press 'P' to Play or 'B' to go Back");
    }
    @Override
    public void drawBoardScene() {

    }
    @Override
    public void updateLobby(ArrayList<String> names) {

    }

    @Override
    public void drawCard(Card card) {

    }

    @Override
    public void displayAlert(String alert) {

    }
    @Override
    public void updateGold(ArrayList<GoldCard> goldCards) {

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
