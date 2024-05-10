package codex.lb04.View;

import codex.lb04.Model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import static java.lang.System.out;

/**
 * This class represents the CLI view
 */
public class CliView extends View {

    private static final String STR_INPUT_CANCELED = "User input canceled.";
    private Thread inputThread;

    /**
     * Reads a line from the standard input.
     *
     * @return the string read from the input.
     * @throws ExecutionException if the input stream thread is interrupted.
     */
    public String readLine() throws ExecutionException {
        FutureTask<String> futureTask = new FutureTask<>(new InputReadTask());
        inputThread = new Thread(futureTask);
        inputThread.start();

        String input = null;

        try {
            input = futureTask.get();
        } catch (InterruptedException e) {
            futureTask.cancel(true);
            Thread.currentThread().interrupt();
        }
        return input;
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
        try {
            askServerInfo();
        } catch (ExecutionException e) {
            out.println(STR_INPUT_CANCELED);
        }

        }

    /**
     * Asks the server address and port to the user.
     *
     * @throws ExecutionException if the input stream thread is interrupted.
     */
    public void askServerInfo() throws ExecutionException {
        Map<String, String> serverInfo = new HashMap<>();
        String defaultAddress = "localhost";
        String defaultPort = "16847";
        boolean validInput;

        out.println("Please specify the following settings. The default value is shown between brackets.");

        /*do {
            out.print("Enter the server address [" + defaultAddress + "]


            : ");

            String address = readLine();

            if (address.equals("")) {
                serverInfo.put("address", defaultAddress);
                validInput = true;
            } else if (trueClientController.isValidIpAddress(address)) {
                serverInfo.put("address", address);
                validInput = true;
            } else {
                out.println("Invalid address!");
               // clearCli();
                validInput = false;
            }
        } while (!validInput);

        do {
            out.print("Enter the server port [" + defaultPort + "]: ");
            String port = readLine();

            if (port.equals("")) {
                serverInfo.put("port", defaultPort);
                validInput = true;
            } else {
              //  if (ClientController.isValidPort(port)) {
                    serverInfo.put("port", port);
                    validInput = true;
                } else {
                    out.println("Invalid port!");
                    validInput = false;
                }
            }*/
       // } while (!validInput);

       // notifyObserver(obs -> obs.onUpdateServerInfo(serverInfo));
    }

    @Override
    public void drawLoginScene() {

    }
    @Override
    public void drawLobbyScene() {

    }
    @Override
    public void drawCreateGameScene() {

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
    public void updateInitialCardDisplay(InitialCard card) {

    }
}
