package codex.lb04.View.Cli;

import codex.lb04.Model.Card;
import codex.lb04.Model.GoldCard;
import codex.lb04.Model.ObjectiveCard;
import codex.lb04.Model.ResourceCard;
import codex.lb04.Network.client.ClientSocket;
import codex.lb04.Utils.ConnectionUtil;

import java.io.IOException;
import java.util.ArrayList;

import static java.lang.System.out;


public class BoardSceneControllerCLI {
    private CliView view;
    private InputThread inputThread;
    private String turn = "not your turn";
    private ClientSocket clientSocket;
    private ArrayList<Card> hand = new ArrayList<>();
    private ArrayList<GoldCard> visibleGold = new ArrayList<>();
    private ArrayList<ResourceCard> visibleResources = new ArrayList<>();
    private ArrayList<ObjectiveCard> objectiveCards = new ArrayList<>();
    private ArrayList<Integer> points = new ArrayList<>();
    private Card[][] gridMap;
    private Card selectedCard = null;

    /**
     * Constructor of the board scene controller
     *
     * @param view the view
     */
    public BoardSceneControllerCLI(CliView view) {
        this.view = view;
        this.inputThread = new InputThread();
    }

    public void setClientSocket(ClientSocket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void positionAvailable(){
        System.out.println("Position available are:");

    }

    public void updateDrawableGold(ArrayList<GoldCard> goldCards) {
        this.visibleGold = goldCards;
    }

    public void drawBoard() {
        view.setState(CliState.BOARD);
        out.println("_____________________________________________________________________________");
        out.println(turn);
        out.println("_____________________________________________________________________________");
        drawHand();
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

    public void handleInput(String input) {
        CliState cliState = this.view.getState();
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
                view.drawCreateGameScene();
                view.setState(CliState.CREATE_GAME);
                break;
            case "J":
                view.drawLoginScene();
                view.setState(CliState.LOGIN);
                break;
            default:
                System.out.println("Invalid input, please enter 'C' to create or 'J' to join a game.");
                view.drawHelloScene();
                break;
        }
    }

    private void loginHandler(String input) {
        switch (input) {
            case "B":
                view.drawHelloScene();
                view.setState(CliState.HELLO);
                break;
            case "L":
                out.println("Enter your username:");
                String usr = null;
                try {
                    usr = inputThread.call();
                } catch (IOException e) {
                    out.println("Error reading input");
                    view.drawLoginScene();
                    break;
                }
                out.println("Enter the server address");
                String addr = null;
                try {
                    addr = inputThread.call();
                } catch (IOException e) {
                    out.println("Error reading input");
                    view.drawLoginScene();
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
                        clientSocket = new ClientSocket(this.view, usr, addr, port);
                        setClientSocket(clientSocket);
                    } catch (IOException e) {
                        out.println("Server not available");
                        view.drawHelloScene();
                        view.setState(CliState.HELLO);
                        break;
                    }
                    LoginMessage loginMessage = new LoginMessage(usr);
                    clientSocket.sendMessage((loginMessage));
                } else {
                    out.println("Invalid input, please enter a valid username and server address.");
                    view.drawLoginScene();
                }
                break;
            default:
                System.out.println("Invalid input");
                view.drawLoginScene();
                break;
        }
    }

    private void lobbyHandler(String input) {
        switch (input) {
            case "B":
                clientSocket.disconnect();
                view.drawHelloScene();
                break;
            case "P":
                clientSocket.sendMessage(new DrawBoardMessage(clientSocket.getUsername()));
                break;
            default:
                System.out.println("Invalid input, please enter 'B' to go back or 'P' to start the game.");
                view.drawLobbyScene();
                break;
        }
    }
    private void createGameHandler(String input) {
        switch (input) {
            case "B":
                view.drawHelloScene();
                view.setState(CliState.HELLO);
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
                    view.drawCreateGameScene();
                    break;
                }
                if(num<2||num>4){
                    out.println("Enter a valid number of players");
                    view.drawCreateGameScene();
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
                        clientSocket = new ClientSocket(this.view, usr, ConnectionUtil.getLocalhost(), ConnectionUtil.defaultPort);
                        setClientSocket(clientSocket);
                    } catch (IOException e) {
                        out.println("Server not available");
                        view.drawHelloScene();
                        view.setState(CliState.HELLO);
                        return;
                    }
                    clientSocket.sendMessage(new CreateGameMessage(usr, ConnectionUtil.defaultPort, num));
                } else {
                    out.println("Invalid input, please enter a valid number of players and username.");
                    view.drawCreateGameScene();
                }
        }
    }

    private void boardHandler(String input) {

    }

    private void endHandler(String input) {

    }

}

