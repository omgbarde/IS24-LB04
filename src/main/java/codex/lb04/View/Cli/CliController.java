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

import java.io.IOException;
import java.util.ArrayList;

import static java.lang.System.out;

/**
 * controller class for the CLI view
 */
public class CliController extends ViewController {
    private final CliView cliView;
    private final CliBoardModel cliBoardModel;
    private final InputThread inputThread;
    private ClientSocket clientSocket;
    private boolean firstTurn = true;
    private boolean placed = false;

    /**
     * Constructor of cli controller
     *
     * @param view the view
     */
    public CliController(CliView view) {
        this.cliView = view;
        this.cliBoardModel = view.getBoard();
        this.inputThread = new InputThread();
    }

    /**
     * calls the drawLobbyScene method of the cliView
     */
    @Override
    public void drawLobbyScene() {
        cliView.drawLobbyScene();
    }

    /**
     * calls the drawLoginScene method of the cliView and sets the state to LOGIN
     */
    @Override
    public void drawHelloScene() {
        cliView.drawHelloScene();
        cliView.setState(CliViewState.HELLO);
    }

    /**
     * updates the lobby
     *
     * @param lobby the arraylist of names of the players in the lobby
     */
    @Override
    public void updateLobby(ArrayList<String> lobby) {
        cliView.updateLobby(lobby);
    }

    /**
     * Update the drawable gold in the model and re calls the draw method of the view
     *
     * @param goldCards the gold cards
     */
    public void updateDrawableGold(ArrayList<GoldCard> goldCards) {
        cliBoardModel.setVisibleGold(goldCards);
        cliView.drawBoardScene();
    }

    /**
     * Update the drawable resources in the model and re calls the draw method of the view
     *
     * @param resourceCards the resource cards
     */
    @Override
    public void updateDrawableResources(ArrayList<ResourceCard> resourceCards) {
        cliBoardModel.setVisibleResources(resourceCards);
        cliView.drawBoardScene();

    }

    /**
     * Update the secret objective in the model and re calls the draw method of the view
     *
     * @param secretObjective the secret objective
     */
    @Override
    public void updateSecretObjective(ObjectiveCard secretObjective) {
        cliBoardModel.setSecretObjective(secretObjective);
        cliView.drawBoardScene();

    }

    /**
     * Update the hand in the model and re calls the draw method of the view
     *
     * @param hand the hand
     */
    @Override
    public void updateHand(ArrayList<Card> hand) {
        cliBoardModel.setHand(hand);
        cliView.drawBoardScene();
    }

    /**
     * Update the common objectives in the model and re calls the draw method of the view
     *
     * @param commonObjectives the common objectives
     */
    @Override
    public void updateCommonObjectives(ArrayList<ObjectiveCard> commonObjectives) {
        cliBoardModel.setObjectiveCards(commonObjectives);
        cliView.drawBoardScene();

    }

    /**
     * Update the initial card in the model and re calls the draw method of the view
     *
     * @param initialCard the initial card
     */
    @Override
    public void updateInitialCardDisplay(InitialCard initialCard) {
        cliBoardModel.setBoardState(CliBoardState.CHOOSE_INIT);
        cliBoardModel.setInitialCard(initialCard);
        cliView.drawBoardScene();
    }

    /**
     * Update the secret objectives to choose in the model and re calls the draw method of the view
     *
     * @param secretObjectives the secret objectives
     */
    @Override
    public void updateSecretObjectiveToChoose(ArrayList<ObjectiveCard> secretObjectives) {
        cliBoardModel.setChoices(secretObjectives);
        cliView.drawBoardScene();

    }

    /**
     * Place the card in the local model and re calls the draw method of the view
     * (after confirmation from the server)
     *
     * @param x    the x coordinate
     * @param y    the y coordinate
     * @param card the card to place
     */
    @Override
    public void placeCard(Integer x, Integer y, Card card) {
        cliBoardModel.placeCard(x, y, card);
        //if the reply is for an initial card the state is changed to choose secret
        if (card.getClass().equals(InitialCard.class)) {
            cliBoardModel.setBoardState(CliBoardState.CHOOSE_SECRET);
        }
        //else if the reply is for other cards the state is changed to drawing
        else {
            placed = true;
            cliBoardModel.setBoardState(CliBoardState.DRAWING);
        }
        cliView.drawBoardScene();
    }

    /**
     * Deselects the card in the local model
     */
    @Override
    public void deselectCard() {
        cliBoardModel.deselectCard();
    }

    /**
     * Display an alert
     *
     * @param alert the alert string
     */
    @Override
    public void displayAlert(String alert) {
        cliView.displayAlert(alert);
    }

    /**
     * calls the drawBoardScene method of the cliView and sets the state to BOARD
     */
    @Override
    public void drawBoardScene() {
        cliView.setState(CliViewState.BOARD);
        cliView.drawBoardScene();
    }

    /**
     * Set the text to "YOUR TURN" and set the state to SELECTING or CHOOSE_INIT depending on if it's the first turn or not
     */
    @Override
    public void setYourTurnText() {
        if (firstTurn) {
            firstTurn = false;
            cliBoardModel.setBoardState(CliBoardState.CHOOSE_INIT);
        } else cliBoardModel.setBoardState(CliBoardState.SELECTING);
        placed = false;
        cliBoardModel.setTurnLabel("YOUR TURN");
        cliView.drawBoardScene();
    }

    /**
     * Update the points in the model and re calls the draw method of the view
     *
     * @param points the points
     */
    @Override
    public void updatePoints(ArrayList<Integer> points) {
        cliBoardModel.setPoints(points);
        cliView.drawBoardScene();
    }

    /**
     * Send a message to the server to end the turn and set the text to "NOT YOUR TURN" also re calls the draw method of the view
     */
    @Override
    public void cleanYourTurnText() {
        clientSocket.sendMessage(new EndTurnMessage(clientSocket.getUsername()));
        cliBoardModel.setTurnLabel("NOT YOUR TURN");
        cliView.drawBoardScene();
    }

    /**
     * Update the chat in the model and re calls the draw method of the chat if the state is CHAT
     *
     * @param message the message
     */
    @Override
    public void updateChat(String message) {
        cliView.updateChat(message);
        //if already in chat it refreshes the view
        if (cliView.getState() == CliViewState.CHAT) cliView.showChat();
    }

    /**
     * print utility method
     *
     * @param string the string to print
     */
    @Override
    public void print(String string) {
        out.println(string);
    }

    /**
     * Show the winners of the game
     *
     * @param winner the winners string already formatted as received from the server
     */
    @Override
    public void showWinners(String winner) {
        cliBoardModel.setBoardState(CliBoardState.END);
        cliBoardModel.resetBoard();
        cliView.setState(CliViewState.END);
        cliView.drawWinnerScene();
        cliView.showWinners(winner);
        clientSocket.disconnect();
    }

    /**
     * Handle the input based on the current state
     *
     * @param input the input
     */
    public void handleInput(String input) {
        CliViewState cliState = this.cliView.getState();
        switch (cliState) {
            case HELLO -> helloHandler(input);
            case LOGIN -> loginHandler(input);
            case LOBBY -> lobbyHandler(input);
            case CREATE_GAME -> createGameHandler(input);
            case BOARD -> boardHandler(input);
            case CHAT -> chatHandler(input);
            case END -> drawHelloScene();
        }
    }

    /**
     * Handle the inputs in chat state
     *
     * @param input the input
     */
    private void chatHandler(String input) {
        if (input.equals("B")) {
            cliView.setState(CliViewState.BOARD);
            drawBoardScene();
            return;
        }
        String usr = clientSocket.getUsername();
        clientSocket.sendMessage(new ChatMessage(usr, input));
    }

    /**
     * Handle the inputs in hello state
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
     * Handle the input in login state
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
                String usr;
                try {
                    usr = inputThread.call();
                } catch (IOException e) {
                    cliView.drawLoginScene();
                    out.println("Error reading input");
                    break;
                }
                out.println("Enter the server address");
                String addr;
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
                } catch (IOException e) {
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
                        clientSocket = new ClientSocket(usr, addr, port, this);
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
     * Handle the input in lobby state
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
     * Handle the input in the creation of the game
     *
     * @param input the input
     */
    private void createGameHandler(String input) {
        if (input.equals("B")) {
            cliView.drawHelloScene();
            cliView.setState(CliViewState.HELLO);
        } else {
            int num;
            out.println("Enter the number of players (2-4):");
            String numPlayersChoice;
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
                return;
            }
            if (num < 2 || num > 4) {
                cliView.drawCreateGameScene();
                out.println("Enter a valid number of players");
                return;
            }
            out.println("Enter your username:");
            String usr;
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
                return;
            }
            if (ConnectionUtil.checkValid(num, usr)) {
                if (ConnectionUtil.isValidPort(portNumber)) {
                    try {
                        clientSocket = new ClientSocket(usr, ConnectionUtil.getLocalhost(), portNumber, this);
                        setClientSocket(clientSocket);
                    } catch (IOException e) {
                        cliView.drawHelloScene();
                        out.println("Server was not available");
                        cliView.setState(CliViewState.HELLO);
                        return;
                    }
                    clientSocket.sendMessage(new CreateGameMessage(usr, num));
                } else {
                    cliView.drawCreateGameScene();
                    out.println("Invalid port, trying to use default port..");
                    try {
                        clientSocket = new ClientSocket(usr, ConnectionUtil.getLocalhost(), ConnectionUtil.defaultPort, this);
                        setClientSocket(clientSocket);
                    } catch (IOException e) {
                        cliView.drawHelloScene();
                        out.println("Server was not available");
                        cliView.setState(CliViewState.HELLO);
                    }
                }
            } else {
                cliView.drawCreateGameScene();
                out.println("Invalid input, please enter a valid number of players and username.");

            }
        }
    }

    /**
     * Handle the input in board state
     *
     * @param input the input
     */
    private void boardHandler(String input) {
        CliBoardState boardState = cliBoardModel.getBoardState();
        if (input.equals("C")) {
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
                initialCardHandler(input);
                break;
            case CHOOSE_SECRET:
                secretObjectiveHandler(input);
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
     * Handle the input in drawing state
     *
     * @param input the input
     */
    private void drawingHandler(String input) {
        int pick;
        switch (input) {
            case "3":
                pick = Integer.parseInt(input) - 3;
                out.println("You selected the first resource card");
                clientSocket.sendMessage(new PickResourceCardMessage(clientSocket.getUsername(), pick));
                cliBoardModel.setBoardState(CliBoardState.END);
                out.println("turn ended press 'E' to end turn");
                break;
            case "4":
                pick = Integer.parseInt(input) - 3;
                out.println("You selected the second resource card");
                clientSocket.sendMessage(new PickResourceCardMessage(clientSocket.getUsername(), pick));
                cliBoardModel.setBoardState(CliBoardState.END);
                out.println("turn ended press 'E' to end turn");
                break;
            case "5":
                pick = Integer.parseInt(input) - 3;
                out.println("You selected the third resource card");
                clientSocket.sendMessage(new PickResourceCardMessage(clientSocket.getUsername(), pick));
                cliBoardModel.setBoardState(CliBoardState.END);
                out.println("turn ended press 'E' to end turn");

                break;
            case "6":
                pick = Integer.parseInt(input) - 6;
                out.println("You selected the first gold card");
                clientSocket.sendMessage(new PickGoldCardMessage(clientSocket.getUsername(), pick));
                cliBoardModel.setBoardState(CliBoardState.END);
                out.println("turn ended press 'E' to end turn");

                break;
            case "7":
                pick = Integer.parseInt(input) - 6;
                out.println("You selected the second gold card");
                clientSocket.sendMessage(new PickGoldCardMessage(clientSocket.getUsername(), pick));
                cliBoardModel.setBoardState(CliBoardState.END);
                out.println("turn ended press 'E' to end turn");
                break;
            case "8":
                pick = Integer.parseInt(input) - 6;
                out.println("You selected the third gold card");
                clientSocket.sendMessage(new PickGoldCardMessage(clientSocket.getUsername(), pick));
                cliBoardModel.setBoardState(CliBoardState.END);
                out.println("turn ended press 'E' to end turn");
                break;
            default:
                out.println("Invalid input, please enter a number between 3 and 8 to draw a card");
        }
    }

    /**
     * Handle the input in placing state
     *
     * @param input the input
     */
    private void placingHandler(String input) {
        Card selected = cliBoardModel.getSelectedCard();
        switch (input) {
            case "F":
                if (selected != null) cliBoardModel.getSelectedCard().flip();
                cliView.drawBoardScene();
                break;
            case "P":
                if (selected == null) {
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
                int x, y;
                try {
                    x = Integer.parseInt(coords[0]);
                    y = Integer.parseInt(coords[1]);

                } catch (NumberFormatException e) {
                    out.println("Invalid coordinates, please enter two numbers separated by a comma");
                    break;
                }
                clientSocket.sendMessage(new PlaceCardMessage(clientSocket.getUsername(), x, y, cliBoardModel.getSelectedCard()));

                cliBoardModel.deselectCard();
                cliView.drawBoardScene();
                break;
            case "B":
                if (!placed) {
                    cliBoardModel.deselectCard();
                    out.println("You deselected the card");
                    cliBoardModel.setBoardState(CliBoardState.SELECTING);
                }
                break;
        }
    }


    /**
     * Handle the input fpr the selection of the card
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
     * Handle the input for the selection of the secret objective
     *
     * @param input the input
     */
    private void secretObjectiveHandler(String input) {
        switch (input) {
            case "1":
                cliBoardModel.setSecretObjective(cliBoardModel.getChoices().getFirst());
                clientSocket.sendMessage(new PickSecretObjectiveMessage(clientSocket.getUsername(), 0));
                cliBoardModel.setBoardState(CliBoardState.SELECTING);
                cliView.drawBoardScene();
                break;
            case "2":
                cliBoardModel.setSecretObjective(cliBoardModel.getChoices().get(1));
                clientSocket.sendMessage(new PickSecretObjectiveMessage(clientSocket.getUsername(), 1));
                cliBoardModel.setBoardState(CliBoardState.SELECTING);
                cliView.drawBoardScene();
                break;
            default:
                System.out.println("Invalid input, please enter a number between 1 and 2.");
                break;
        }
    }

    /**
     * Handle the input for the selection of the initial card side
     *
     * @param input the input
     */
    private void initialCardHandler(String input) {
        switch (input) {
            case "F":
                if (cliBoardModel.getTurnLabel().equals("YOUR TURN")) {
                    cliBoardModel.flipInitialCard();
                    cliView.drawBoardScene();
                } else {
                    System.out.println("It's not your turn, you can't flip the initial card");
                }
                break;
            case "P":
                if (cliBoardModel.getTurnLabel().equals("YOUR TURN")) {
                    clientSocket.sendMessage(new PickInitialCardSideMessage(clientSocket.getUsername(), cliBoardModel.getInitialCard()));
                    cliBoardModel.setInitialCard(null);
                    drawBoardScene();
                    cliBoardModel.setBoardState(CliBoardState.CHOOSE_SECRET);
                } else {
                    System.out.println("It's not your turn, you can't place the initial card");
                }
                break;
            default:
                System.out.println("Invalid input, please enter 'F' to flip the card or 'P' to place it.");
                break;
        }
    }

    /**
     * Handle the inputs in end state
     *
     * @param input the input
     */
    private void endHandler(String input) {
        if (input.equals("E")) {
            cleanYourTurnText();
            drawBoardScene();
        } else {
            out.println("Invalid input, please enter 'E' to end turn.");
        }

    }

    //SETTER

    /**
     * Sets the client socket
     *
     * @param clientSocket the client socket
     */
    public void setClientSocket(ClientSocket clientSocket) {
        this.clientSocket = clientSocket;
    }

}