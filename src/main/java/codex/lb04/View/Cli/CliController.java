package codex.lb04.View.Cli;

import codex.lb04.Message.DrawMessage.DrawBoardMessage;
import codex.lb04.Message.GameMessage.CreateGameMessage;
import codex.lb04.Message.LoginMessage;
import codex.lb04.Model.*;
import codex.lb04.Network.client.ClientSocket;
import codex.lb04.Utils.ConnectionUtil;
import codex.lb04.View.ViewController;

import java.io.IOException;
import java.util.ArrayList;

import static java.lang.System.out;


public class CliController extends ViewController {
    private CliView cliView;
    private InputThread inputThread;
    private ClientSocket clientSocket;

    /**
     * Constructor of the board scene controller
     *
     * @param view the view
     */
    public CliController(CliView view) {
        this.cliView = view;
        this.inputThread = new InputThread();
    }

    public void setClientSocket(ClientSocket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void positionAvailable(){
        System.out.println("Position available are:");
        //TODO
    }

    @Override
    public void drawLobbyScene() {
        cliView.drawLobbyScene();
    }

    @Override
    public void drawHelloScene() {
        cliView.drawHelloScene();
    }

    @Override
    public void updateLobby(ArrayList<String> lobby) {
        cliView.updateLobby(lobby);
    }

    @Override
    public void drawCard(Card card) {
    }

    public void updateDrawableGold(ArrayList<GoldCard> goldCards) {
         cliView.getBoard().setVisibleGold(goldCards);
         cliView.drawBoardScene();
    }

    @Override
    public void updateDrawableResources(ArrayList<ResourceCard> resourceCards) {
        cliView.getBoard().setVisibleResources(resourceCards);
        cliView.drawBoardScene();

    }

    @Override
    public void updateSecretObjective(ObjectiveCard secretObjective) {
        cliView.getBoard().setSecretObjective(secretObjective);
        cliView.drawBoardScene();

    }

    @Override
    public void updateHand(ArrayList<Card> hand) {
        cliView.getBoard().setHand(hand);
        cliView.drawBoardScene();
    }

    @Override
    public void updateCommonObjectives(ArrayList<ObjectiveCard> commonObjectives) {
        cliView.getBoard().setObjectiveCards(commonObjectives);
        cliView.drawBoardScene();

    }

    @Override
    public void updateInitialCardDisplay(InitialCard initialCard) {
        cliView.getBoard().updateBoard(initialCard);
        cliView.drawBoardScene();
    }

    @Override
    public void updateSecretObjectiveToChoose(ArrayList<ObjectiveCard> secretObjectives) {
        cliView.displayChoices(secretObjectives);
        cliView.drawBoardScene();

    }

    @Override
    public void placeCard(Integer x, Integer y, Card card) {
        cliView.getBoard().placeCard(x, y, card);
    }

    @Override
    public void deselectCard() {
        cliView.getBoard().deselectCard();
    }

    @Override
    public void displayAlert(String alert) {
        cliView.displayAlert(alert);
    }

    @Override
    public void drawBoardScene() {
        cliView.drawBoardScene();
    }

    @Override
    public void setYourTurnText() {
        cliView.getBoard().setTurnLabel("YOUR TURN");
    }

    @Override
    public void updatePoints(ArrayList<Integer> points) {
        cliView.getBoard().setPoints(points);
        cliView.drawBoardScene();
    }

    @Override
    public void cleanYourTurnText() {
        cliView.getBoard().setTurnLabel("NOT YOUR TURN");
    }

    @Override
    public void updateChat(String message) {
        //TODO
    }

    @Override
    public void print(String string) {
        out.println(string);
    }

    private void drawHand(ArrayList<Card> hand){
        out.println("Your hand is:");
        for (int i = 0; i < hand.size(); i++) {

        }
    }

    public void handleInput(String input) {
        CliState cliState = this.cliView.getState();
        switch (cliState){
            case HELLO -> helloHandler(input);
            case LOGIN -> loginHandler(input);
            case LOBBY -> lobbyHandler(input);
            case CREATE_GAME -> createGameHandler(input);
            case BOARD -> boardHandler(input);
            case END -> endHandler(input);
        }
    }

    private void helloHandler(String input) {
        switch (input) {
            case "C":
                cliView.drawCreateGameScene();
                cliView.setState(CliState.CREATE_GAME);
                break;
            case "J":
                cliView.drawLoginScene();
                cliView.setState(CliState.LOGIN);
                break;
            default:
                System.out.println("Invalid input, please enter 'C' to create or 'J' to join a game.");
                cliView.drawHelloScene();
                break;
        }
    }

    private void loginHandler(String input) {
        switch (input) {
            case "B":
                cliView.drawHelloScene();
                cliView.setState(CliState.HELLO);
                break;
            case "L":
                out.println("Enter your username:");
                String usr = null;
                try {
                    usr = inputThread.call();
                } catch (IOException e) {
                    out.println("Error reading input");
                    cliView.drawLoginScene();
                    break;
                }
                out.println("Enter the server address");
                String addr = null;
                try {
                    addr = inputThread.call();
                } catch (IOException e) {
                    out.println("Error reading input");
                    cliView.drawLoginScene();
                    break;
                }
                int port = ConnectionUtil.defaultPort;
                try {
                    port = Integer.parseInt(addr);
                } catch (NumberFormatException e) {
                    out.println("Using default port");
                }
                if (ConnectionUtil.checkValid(usr, addr, port)) {
                    try {
                        clientSocket = new ClientSocket( usr, addr, port,this);
                        setClientSocket(clientSocket);
                    } catch (IOException e) {
                        out.println("Server not available");
                        cliView.drawHelloScene();
                        cliView.setState(CliState.HELLO);
                        break;
                    }
                    LoginMessage loginMessage = new LoginMessage(usr);
                    clientSocket.sendMessage((loginMessage));
                } else {
                    out.println("Invalid input, please enter a valid username and server address.");
                    cliView.drawLoginScene();
                }
                break;
            default:
                System.out.println("Invalid input");
                cliView.drawLoginScene();
                break;
        }
    }

    private void lobbyHandler(String input) {
        switch (input) {
            case "B":
                clientSocket.disconnect();
                cliView.drawHelloScene();
                break;
            case "P":
                clientSocket.sendMessage(new DrawBoardMessage(clientSocket.getUsername()));
                break;
            default:
                System.out.println("Invalid input, please enter 'B' to go back or 'P' to start the game.");
                cliView.drawLobbyScene();
                break;
        }
    }

    private void createGameHandler(String input) {
        switch (input) {
            case "B":
                cliView.drawHelloScene();
                cliView.setState(CliState.HELLO);
                break;
            default:
                int num = 0;
                out.println("Enter the number of players (2-4):");
                String numPlayersChoice = null;
                try {
                    numPlayersChoice = inputThread.call();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                try {
                    num = Integer.parseInt(numPlayersChoice);
                } catch (NumberFormatException e) {
                    out.println("Enter a valid number of players");
                    cliView.drawCreateGameScene();
                    break;
                }
                if(num<2||num>4){
                    out.println("Enter a valid number of players");
                    cliView.drawCreateGameScene();
                }
                out.println("Enter your username:");
                String usr = null;
                try {
                    usr = inputThread.call();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                if (ConnectionUtil.checkValid(num, usr)) {
                    try {
                        clientSocket = new ClientSocket(usr, ConnectionUtil.getLocalhost(), ConnectionUtil.defaultPort,this);
                        setClientSocket(clientSocket);
                    } catch (IOException e) {
                        out.println("Server not available");
                        cliView.drawHelloScene();
                        cliView.setState(CliState.HELLO);
                        return;
                    }
                    clientSocket.sendMessage(new CreateGameMessage(usr, ConnectionUtil.defaultPort, num));
                } else {
                    out.println("Invalid input, please enter a valid number of players and username.");
                    cliView.drawCreateGameScene();
                }
        }
    }

    private void boardHandler(String input) {
        switch (input) {
            case "C":
                break;
            case "D":
                break;
            case "F":
                break;
            case "P":
                break;
            case "E":
                break;
        }
    }

    private void endHandler(String input) {

    }

}

