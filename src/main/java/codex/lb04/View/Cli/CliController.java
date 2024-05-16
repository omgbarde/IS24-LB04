package codex.lb04.View.Cli;

import codex.lb04.Message.DrawMessage.DrawBoardMessage;
import codex.lb04.Message.GameMessage.CreateGameMessage;
import codex.lb04.Message.GameMessage.PlaceCardMessage;
import codex.lb04.Message.LoginMessage;
import codex.lb04.Model.*;
import codex.lb04.Network.client.ClientSocket;
import codex.lb04.Utils.ConnectionUtil;
import codex.lb04.View.Cli.State.CliBoardState;
import codex.lb04.View.Cli.State.CliViewState;
import codex.lb04.View.ViewController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.System.out;


public class CliController extends ViewController {
    private CliView cliView;
    private CliBoardModel cliBoardModel;
    private InputThread inputThread;
    private ClientSocket clientSocket;

    /**
     * Constructor of the board scene controller
     *
     * @param view the view
     */
    public CliController(CliView view) {
        this.cliView = view;
        this.cliBoardModel = view.getBoard();
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
         cliBoardModel.setVisibleGold(goldCards);
         cliView.drawBoardScene();
    }

    @Override
    public void updateDrawableResources(ArrayList<ResourceCard> resourceCards) {
        cliBoardModel.setVisibleResources(resourceCards);
        cliView.drawBoardScene();

    }

    @Override
    public void updateSecretObjective(ObjectiveCard secretObjective) {
        cliBoardModel.setSecretObjective(secretObjective);
        cliView.drawBoardScene();

    }

    @Override
    public void updateHand(ArrayList<Card> hand) {
        cliBoardModel.setHand(hand);
        cliView.drawBoardScene();
    }

    @Override
    public void updateCommonObjectives(ArrayList<ObjectiveCard> commonObjectives) {
        cliBoardModel.setObjectiveCards(commonObjectives);
        cliView.drawBoardScene();

    }

    @Override
    public void updateInitialCardDisplay(InitialCard initialCard) {
        cliBoardModel.setBoardState(CliBoardState.CHOOSE_INIT);
        cliBoardModel.setInitialCard(initialCard);
        cliView.drawBoardScene();
    }

    @Override
    public void updateSecretObjectiveToChoose(ArrayList<ObjectiveCard> secretObjectives) {
        cliBoardModel.setChoices(secretObjectives);
        cliView.drawBoardScene();

    }

    @Override
    public void placeCard(Integer x, Integer y, Card card) {
        cliBoardModel.placeCard(x, y, card);
    }

    @Override
    public void deselectCard() {
        cliBoardModel.deselectCard();
    }

    @Override
    public void displayAlert(String alert) {
        cliView.displayAlert(alert);
    }

    @Override
    public void drawBoardScene() {
        cliView.setState(CliViewState.BOARD);
        cliView.drawBoardScene();
    }

    @Override
    public void setYourTurnText() {
        cliBoardModel.setBoardState(CliBoardState.PLACING);
        cliBoardModel.setTurnLabel("YOUR TURN");
    }

    @Override
    public void updatePoints(ArrayList<Integer> points) {
        cliBoardModel.setPoints(points);
        cliView.drawBoardScene();
    }

    @Override
    public void cleanYourTurnText() {
        cliBoardModel.setBoardState(CliBoardState.END);
        cliBoardModel.setTurnLabel("NOT YOUR TURN");
    }

    @Override
    public void updateChat(String message) {
        //TODO
    }

    @Override
    public void print(String string) {
        out.println(string);
    }

    public void handleInput(String input) {
        CliViewState cliState = this.cliView.getState();
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
                cliView.setState(CliViewState.CREATE_GAME);
                break;
            case "J":
                cliView.drawLoginScene();
                cliView.setState(CliViewState.LOGIN);
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
                cliView.setState(CliViewState.HELLO);
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
                        cliView.setState(CliViewState.HELLO);
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
                cliView.setState(CliViewState.HELLO);
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
                        cliView.setState(CliViewState.HELLO);
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
        CliBoardState boardState = cliBoardModel.getBoardState();
        switch (boardState) {
            case SELECTING:
                selectingCardHandler(input);
                break;
            case CHOOSE_INIT:
                initialcardHandler(input);
                break;
            case CHOOSE_SECRET:
                secretobjectiveHandler(input);
                break;
            case PLACING:
                placingHandler(input);
                break;
            case DRAWING:
                break;
            case END:
                break;
            default:
                break;
        }
    }

    private void placingHandler(String input){
        switch (input) {
            case "F":
                cliBoardModel.getSelectedCard().flip();
                cliView.drawBoardScene();
                break;
            case "P":
                //todo get x and y from input after the user pressed P
                //clientSocket().sendMessage(new PlaceCardMessage(clientSocket.getUsername(),11, 11, cliBoardModel.getSelectedCard())
                //cliBoardModel.placeCard(11, 11, cliBoardModel.getSelectedCard());
                cliView.drawBoardScene();
                cliBoardModel.setBoardState(CliBoardState.DRAWING);
                break;
            case"B":
                cliBoardModel.deselectCard();
                out.println("You deselected the card");
                cliBoardModel.setBoardState(CliBoardState.SELECTING);
                break;
        }
    }


    private void selectingCardHandler(String input) {
        switch (input) {
            case "0":
                cliBoardModel.setSelectedCard(cliBoardModel.getHand().getFirst());
                out.println("You selected your first card");
                cliBoardModel.setBoardState(CliBoardState.PLACING);
                break;
            case "1":
                cliBoardModel.setSelectedCard(cliBoardModel.getHand().get(1));
                out.println("You selected your second card");
                cliBoardModel.setBoardState(CliBoardState.PLACING);
                break;
            case "2":
                cliBoardModel.setSelectedCard(cliBoardModel.getHand().get(2));
                out.println("You selected your third card");
                cliBoardModel.setBoardState(CliBoardState.PLACING);
                break;
            default:
                System.out.println("Invalid input, please enter a number between 0 and 2.");
        }
    }

    private void secretobjectiveHandler(String input) {
        switch (input) {
            case "1":
                cliBoardModel.setSecretObjective(cliBoardModel.getChoices().getFirst());
                cliBoardModel.setBoardState(CliBoardState.SELECTING);
                cliView.drawBoardScene();
                break;
            case "2":
                cliBoardModel.setSecretObjective(cliBoardModel.getChoices().get(1));
                cliBoardModel.setBoardState(CliBoardState.SELECTING);
                cliView.drawBoardScene();
                break;
            default:
                System.out.println("Invalid input, please enter a number between 1 and 2.");
                break;
        }
    }

    private void initialcardHandler(String input) {
        switch (input) {
            case "F":
                if(cliBoardModel.getTurnLabel().equals("YOUR TURN")) {
                    cliBoardModel.flipInitialCard();
                    cliView.drawBoardScene();
                }else{
                    System.out.println("It's not your turn, you can't flip the initial card");
                }
                break;
            case "P":
                if(cliBoardModel.getTurnLabel().equals("YOUR TURN")) {
                    cliBoardModel.placeCard(10, 10, cliBoardModel.getInitialCard());
                    cliBoardModel.setInitialCard(null);
                    drawBoardScene();
                    cliBoardModel.setBoardState(CliBoardState.CHOOSE_SECRET);
                }else{
                    System.out.println("It's not your turn, you can't place the initial card");
                }
                break;
            default:
                System.out.println("Invalid input, please enter 'F' to flip the card or 'P' to place it.");
                break;
        }
    }

    private void endHandler(String input) {

    }

}

