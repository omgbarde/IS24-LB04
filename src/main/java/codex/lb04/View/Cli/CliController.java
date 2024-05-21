package codex.lb04.View.Cli;

import codex.lb04.Message.ChatMessage;
import codex.lb04.Message.DrawMessage.DrawBoardMessage;
import codex.lb04.Message.GameMessage.*;
import codex.lb04.Message.LoginMessage;
import codex.lb04.Model.*;
import codex.lb04.Network.client.ClientSocket;
import codex.lb04.Utils.ConnectionUtil;
import codex.lb04.View.Cli.State.CliBoardState;
import codex.lb04.View.Cli.State.CliViewState;
import codex.lb04.View.ViewController;
import javafx.concurrent.Task;

import java.io.IOException;
import java.util.ArrayList;

import static java.lang.System.out;


public class CliController extends ViewController {
    private CliView cliView;
    private CliBoardModel cliBoardModel;
    private InputThread inputThread;
    private ClientSocket clientSocket;
    private boolean firstTurn = true;
    private boolean placed = false;
    Task task;

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

    /**
     * Set the client socket
     *
     * @param clientSocket the client socket
     */
    public void setClientSocket(ClientSocket clientSocket) {
        this.clientSocket = clientSocket;
    }


    @Override
    public void drawLobbyScene() {
        cliView.drawLobbyScene();
    }

    @Override
    public void drawHelloScene() {
        cliView.drawHelloScene();
        cliView.setState(CliViewState.HELLO);
    }

    @Override
    public void updateLobby(ArrayList<String> lobby) {
        cliView.updateLobby(lobby);
    }

    @Override
    public void drawCard(Card card) {
    }

    /**
     * Update the drawable gold
     *
     * @param goldCards the gold cards
     */
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
        //if the reply is for an initial card the state is changed to choose secret
        if (card.getClass().equals(InitialCard.class)){
            cliBoardModel.setBoardState(CliBoardState.CHOOSE_SECRET);
        }
        //else if the reply is for other cards the state is changed to drawing
        else {
            placed = true;
            cliBoardModel.setBoardState(CliBoardState.DRAWING);
        }
        cliView.drawBoardScene();
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
        if (firstTurn){
            firstTurn = false;
            cliBoardModel.setBoardState(CliBoardState.CHOOSE_INIT);
        }
        else cliBoardModel.setBoardState(CliBoardState.SELECTING);
        placed = false;
        cliBoardModel.setTurnLabel("YOUR TURN");
        cliView.drawBoardScene();
    }

    @Override
    public void updatePoints(ArrayList<Integer> points) {
        cliBoardModel.setPoints(points);
        cliView.drawBoardScene();
    }

    @Override
    public void cleanYourTurnText() {
        clientSocket.sendMessage(new EndTurnMessage(clientSocket.getUsername()));
        cliBoardModel.setTurnLabel("NOT YOUR TURN");
        cliView.drawBoardScene();
    }

    @Override
    public void updateChat(String message) {
        cliView.updateChat(message);
        //if already in chat it refreshes the view
        if (cliView.getState() == CliViewState.CHAT) cliView.showChat();
    }

    @Override
    public void print(String string) {
        out.println(string);
    }

    /**
     * Handle the input
     *
     * @param input the input
     */
    public void handleInput(String input) {
        CliViewState cliState = this.cliView.getState();
        switch (cliState){
            case HELLO -> helloHandler(input);
            case LOGIN -> loginHandler(input);
            case LOBBY -> lobbyHandler(input);
            case CREATE_GAME -> createGameHandler(input);
            case BOARD -> boardHandler(input);
            case CHAT -> chatHandler(input);
            case END -> {}
        }
    }

    /**
     * Handle the chat
     *
     * @param input the input
     */
    private void chatHandler(String input) {
        if (input.equals("B")){
            cliView.setState(CliViewState.BOARD);
            drawBoardScene();
            return;
        }
        String usr = clientSocket.getUsername();
        clientSocket.sendMessage(new ChatMessage(usr,input));
    }

    /**
     * Handle the hello
     *
     * @param input the input
     */
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
                cliView.drawHelloScene();
                System.out.println("Invalid input");
                break;
        }
    }

    /**
     * Handle the login
     *
     * @param input the input
     */
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
                    cliView.drawLoginScene();
                    out.println("Error reading input");
                    break;
                }
                out.println("Enter the server address");
                String addr = null;
                try {
                    addr = inputThread.call();
                } catch (IOException e) {
                    cliView.drawLoginScene();
                    out.println("Error reading input");
                    break;
                }
                out.println("Enter the server port");
                String portString;
                try {
                    portString = inputThread.call();
                }catch (IOException e){
                    out.println("Error reading port");
                    break;
                }
                int port = ConnectionUtil.defaultPort;
                try {
                    port = Integer.parseInt(portString);
                } catch (NumberFormatException e) {
                    out.println("Using default port");
                }
                if (ConnectionUtil.checkValid(usr, addr, port)) {
                    try {
                        clientSocket = new ClientSocket( usr, addr, port,this);
                        setClientSocket(clientSocket);
                    } catch (IOException e) {
                        cliView.drawHelloScene();
                        out.println("Server was not available");
                        cliView.setState(CliViewState.HELLO);
                        break;
                    }
                    LoginMessage loginMessage = new LoginMessage(usr);
                    clientSocket.sendMessage((loginMessage));
                } else {
                    cliView.drawLoginScene();
                    out.println("Invalid input, please enter a valid username and server address.");
                }
                break;
            default:
                cliView.drawLoginScene();
                System.out.println("Invalid input");
                break;
        }
    }

    /**
     * Handle the lobby
     *
     * @param input the input
     */
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
                cliView.drawLobbyScene();
                System.out.println("Invalid input");
                break;
        }
    }

    /**
     * Handle the creation of the game
     *
     * @param input the input
     */
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
                    cliView.drawCreateGameScene();
                    out.println("Enter a valid number of players");
                    break;
                }
                if(num<2||num>4){
                    cliView.drawCreateGameScene();
                    out.println("Enter a valid number of players");
                    break;
                }
                out.println("Enter your username:");
                String usr = null;
                try {
                    usr = inputThread.call();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                out.println("Enter the server port (refer to serverApp):");
                String portChoice;
                int portNumber;
                try {
                    portChoice = inputThread.call();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                try {
                    portNumber = Integer.parseInt(portChoice);
                } catch (NumberFormatException e) {
                    cliView.drawCreateGameScene();
                    out.println("enter a valid number");
                    break;
                }
                if (ConnectionUtil.checkValid(num,usr)) {
                    if(ConnectionUtil.isValidPort(portNumber)){
                        try {
                            clientSocket = new ClientSocket(usr, ConnectionUtil.getLocalhost(), portNumber, this);
                            setClientSocket(clientSocket);
                        } catch (IOException e) {
                            cliView.drawHelloScene();
                            out.println("Server was not available");
                            cliView.setState(CliViewState.HELLO);
                            return;
                        }
                        //TODO remove port from this message
                        clientSocket.sendMessage(new CreateGameMessage(usr, ConnectionUtil.defaultPort, num));
                    }else{
                        cliView.drawCreateGameScene();
                        out.println("Invalid port, trying to use default port..");
                        try {
                            clientSocket = new ClientSocket(usr, ConnectionUtil.getLocalhost(), ConnectionUtil.defaultPort, this);
                            setClientSocket(clientSocket);
                        } catch (IOException e) {
                            cliView.drawHelloScene();
                            out.println("Server was not available");
                            cliView.setState(CliViewState.HELLO);
                            return;
                        }
                    }
                } else {
                    cliView.drawCreateGameScene();
                    out.println("Invalid input, please enter a valid number of players and username.");

                }
        }
    }

    /**
     * Handle the board
     *
     * @param input the input
     */
    private void boardHandler(String input) {
        CliBoardState boardState = cliBoardModel.getBoardState();
        if(input.equals("C")){
                cliView.showChat();
                cliView.setState(CliViewState.CHAT);
                return;
        }
        //go to other handlers instead
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
                drawingHandler(input);
                break;
            case END:
                endHandler(input);
                break;
            default:
                break;
        }
    }

    /**
     * Handle the drawing
     *
     * @param input the input
     */
    private void drawingHandler(String input) {
        switch(input){
            case "3":
                int pick = Integer.parseInt(input)-3;
                out.println("You selected the first resource card");
                clientSocket.sendMessage(new PickResourceCardMessage(clientSocket.getUsername(),pick));
                cliBoardModel.setBoardState(CliBoardState.END);
                out.println("turn ended press 'E' to end turn");
                break;
            case "4":
                pick = Integer.parseInt(input) - 3;
                out.println("You selected the second resource card");
                clientSocket.sendMessage(new PickResourceCardMessage(clientSocket.getUsername(),pick));
                cliBoardModel.setBoardState(CliBoardState.END);
                out.println("turn ended press 'E' to end turn");
                break;
            case "5":
                pick = Integer.parseInt(input)-3;
                out.println("You selected the third resource card");
                clientSocket.sendMessage(new PickResourceCardMessage(clientSocket.getUsername(),pick));
                cliBoardModel.setBoardState(CliBoardState.END);
                out.println("turn ended press 'E' to end turn");

                break;
            case "6":
                pick = Integer.parseInt(input)-6;
                out.println("You selected the first gold card");
                clientSocket.sendMessage(new PickGoldCardMessage(clientSocket.getUsername(),pick));
                cliBoardModel.setBoardState(CliBoardState.END);
                out.println("turn ended press 'E' to end turn");

                break;
            case "7":
                pick = Integer.parseInt(input)-6;
                out.println("You selected the second gold card");
                clientSocket.sendMessage(new PickGoldCardMessage(clientSocket.getUsername(),pick));
                cliBoardModel.setBoardState(CliBoardState.END);
                out.println("turn ended press 'E' to end turn");
                break;
            case "8":
                pick = Integer.parseInt(input)-6;
                out.println("You selected the third gold card");
                clientSocket.sendMessage(new PickGoldCardMessage(clientSocket.getUsername(),pick));
                cliBoardModel.setBoardState(CliBoardState.END);
                out.println("turn ended press 'E' to end turn");
                break;
            default:
                out.println("Invalid input, please enter a number between 3 and 8 to draw a card");
        }
    }

    /**
     * Handle the placing
     *
     * @param input the input
     */
    private void placingHandler(String input){
        Card selected = cliBoardModel.getSelectedCard();
        switch (input) {
            case "F":
                if(selected!=null) cliBoardModel.getSelectedCard().flip();
                cliView.drawBoardScene();
                break;
            case "P":
                if(selected == null) {
                    out.println("select a card first");
                    break;
                }
                out.println("Enter the x,y coordinates:");
                String coordinates;
                try {
                     coordinates = inputThread.call();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                String[] coords = coordinates.split(",");
                int x,y;
                try {
                    x = Integer.parseInt(coords[0]);
                    y = Integer.parseInt(coords[1]);

                }catch (NumberFormatException e){
                    out.println("Invalid coordinates, please enter two numbers separated by a comma");
                    break;
                }
                clientSocket.sendMessage(new PlaceCardMessage(clientSocket.getUsername(),x, y, cliBoardModel.getSelectedCard()));
                cliBoardModel.deselectCard();
                cliView.drawBoardScene();
                break;
            case"B":
                if(!placed) {
                    cliBoardModel.deselectCard();
                    out.println("You deselected the card");
                    cliBoardModel.setBoardState(CliBoardState.SELECTING);
                }
                break;
        }
    }


    /**
     * Handle the selection of the card
     *
     * @param input the input
     */
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

    /**
     * Handle the secret objective
     *
     * @param input the input
     */
    private void secretobjectiveHandler(String input) {
        switch (input) {
            case "1":
                cliBoardModel.setSecretObjective(cliBoardModel.getChoices().getFirst());
                clientSocket.sendMessage(new PickSecretObjectiveMessage(clientSocket.getUsername(),0));
                cliBoardModel.setBoardState(CliBoardState.SELECTING);
                cliView.drawBoardScene();
                break;
            case "2":
                cliBoardModel.setSecretObjective(cliBoardModel.getChoices().get(1));
                clientSocket.sendMessage(new PickSecretObjectiveMessage(clientSocket.getUsername(),1));
                cliBoardModel.setBoardState(CliBoardState.SELECTING);
                cliView.drawBoardScene();
                break;
            default:
                System.out.println("Invalid input, please enter a number between 1 and 2.");
                break;
        }
    }

    /**
     * Handle the initial card
     *
     * @param input the input
     */
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
                    clientSocket.sendMessage(new PickInitialCardSideMessage(clientSocket.getUsername(),cliBoardModel.getInitialCard()));
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

    /**
     * Handle the end
     *
     * @param input the input
     */
    private void endHandler(String input) {
        switch (input) {
            case "E":
                cleanYourTurnText();
                drawBoardScene();
                break;
            default:
                System.out.println("Invalid input, please enter 'E' to end turn.");
                break;
        }

    }

}

