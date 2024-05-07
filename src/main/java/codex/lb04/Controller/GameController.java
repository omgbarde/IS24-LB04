package codex.lb04.Controller;

import codex.lb04.Message.ErrorMessage;
import codex.lb04.Message.GameMessage.*;
import codex.lb04.Message.GenericMessage;
import codex.lb04.Message.Message;
import codex.lb04.Message.OkMessage;
import codex.lb04.Model.Card;
import codex.lb04.Model.Enumerations.GameState;
import codex.lb04.Model.Face;
import codex.lb04.Model.Game;
import codex.lb04.Observer.GameObserver;
import codex.lb04.ServerApp;

import java.util.ArrayList;

public class GameController {

    private Game game;
    private InputController inputController;
    private TurnController turnController;
    private static GameController instance;
    private boolean endGame = false;
    private int countDown = -1;
    private ArrayList<String> winners;
    private boolean EndGame = false;

    /**
     * Singleton instance method
     * @return the singleton instance of the GameController class
     */
    public static GameController getInstance() {
        if (instance == null) {
            instance = new GameController();
        }
        return instance;
    }

    /**
     * Resets the instance of the game controller
     */
    public void resetInstance() {
        this.game.resetInstance();
        if (this.turnController != null) {
            this.turnController.resetInstance();
        }
        instance = null;
    }

    /**
     * Private constructor to prevent instantiation from outside the class
     */
    private GameController() {
        createGameController();
    }

    /**
     * Creates the game controller
     */
    private void createGameController() {
        this.game = Game.getInstance();
        game.addObserver(new GameObserver());
        this.inputController = new InputController(this, game);
        game.setGameState(GameState.LOGIN);
    }


    /**
     * when a message is received, the controller checks the game state and the message type
     * this method will be used in the server to handle the messages
     * @param receivedMessage Message from Active Player.
     */
    public void onMessageReceived(Message receivedMessage) {

        switch (game.getGameState()) {
            case LOGIN:
                inLoginState(receivedMessage);
                break;
            case INIT:
                ServerApp.sendMessageToClient(new GenericMessage("server", "invalid in this phase"), receivedMessage.getUsername());
                break;
            case IN_GAME:
                if (inputController.checkUser(receivedMessage)) { // check if the message is from the active player
                    inGameState(receivedMessage);
                }
                break;
            case END_GAME:
                if (inputController.checkUser(receivedMessage)) { // check if the message is from the active player
                    inEndGameState(receivedMessage);
                }
                break;
            case ENDED:
                ServerApp.sendMessageToClient(new GenericMessage("server", "game is ended, play again or quit"), receivedMessage.getUsername());
                break;
            default:
                ServerApp.sendMessageToClient(new ErrorMessage("server", "game state not recognized"), receivedMessage.getUsername());
                break;
        }
    }

    /**
     * handles the messages received when the game is in login state
     * @param receivedMessage Message from a client
     */
    private void inLoginState(Message receivedMessage) {
        String usr = receivedMessage.getUsername();
        switch (receivedMessage.getMessageType()) {
            case CREATE_GAME:
                game.setNumPlayers(((CreateGameMessage) receivedMessage).getNumberOfPlayers());
                game.addPlayerToLobby(usr);
                break;
            case LOGIN_REQUEST:
                game.addPlayerToLobby(usr);
                break;
            case LOGOUT_REQUEST:
                ServerApp.sendMessageToClient(new OkMessage(), usr);
                game.removePlayerFromLobby(usr);
                break;
            case PONG:
                //TODO
                break;
            case START_GAME:
                if (game.getLobby().size() >= 2 && game.getLobby().size() <= 4) {
                    startGame();
                }
                break;
            case ERROR:
                ErrorMessage error = new ErrorMessage("server", ((ErrorMessage) receivedMessage).getError());
                ServerApp.sendMessageToClient(error, usr);
                break;
            case DRAW_BOARD:
                game.drawBoard();
                break;
            case READY:
                if(game.checkReplies()) {
                    if (game.getLobby().size() >= 2 && game.getLobby().size() <= 4) {
                        startGame();
                    }
                }
                break;
            case DEAD_CLIENT:
                game.removePlayerFromLobby(usr);
                break;
            default:
                ErrorMessage defaultError = new ErrorMessage("server", "message not recognized or double login");
                ServerApp.sendMessageToClient(defaultError, usr);
                break;
        }
    }


    /**
     * handles the messages received only from the active player when the game is in progress
     * @param receivedMessage Message from Active Player.
     */
    private void inGameState(Message receivedMessage) {
        String usr = receivedMessage.getUsername();
        switch (receivedMessage.getMessageType()) {
            case PICK_INITIAL_CARD_SIDE:
                if (inputController.verifyReceivedData(receivedMessage)) {
                    pickInitialCardSideHandler((PickInitialCardSideMessage) receivedMessage);
                } else {
                    ServerApp.sendMessageToClient(new InvalidInputMessage(usr, "invalid input"), usr);
                }
                break;
            case PICK_SECRET_OBJECTIVE:
                if (inputController.verifyReceivedData(receivedMessage) && isInitCardPlaced(usr)) {
                    setSecretObjectiveHandler((PickSecretObjectiveMessage) receivedMessage);
                } else {
                    ServerApp.sendMessageToClient(new InvalidInputMessage(usr, "choose the initial card face first"), usr);
                }
                break;
            case PICK_RESOURCE_CARD:
                if (inputController.verifyReceivedData(receivedMessage) && isInitCardPlaced(usr) && isSecretObjectiveChosen(usr)) {
                    drawResourceCardHandler((PickResourceCardMessage) receivedMessage);
                    turnController.setDrawnCard(true);
                } else {
                    ServerApp.sendMessageToClient(new InvalidInputMessage(usr, "invalid input or initial card side/secret objective not chosen"), usr);
                }
                break;
            case PICK_GOLD_CARD:
                if (inputController.verifyReceivedData(receivedMessage) && isInitCardPlaced(usr) && isSecretObjectiveChosen(usr)) {
                    drawGoldCardHandler((PickGoldCardMessage) receivedMessage);
                    turnController.setDrawnCard(true);
                } else {
                    ServerApp.sendMessageToClient(new InvalidInputMessage(usr, "invalid input or initial card side/secret objective not chosen"), usr);
                }
                break;
            case PLACE_CARD:
                if (inputController.verifyReceivedData(receivedMessage) && isInitCardPlaced(usr) && isSecretObjectiveChosen(usr)) {
                    placeCardHandler((PlaceCardMessage) receivedMessage);
                    turnController.setPlacedCard(true);
                } else {
                    ServerApp.sendMessageToClient(new InvalidInputMessage(usr, "invalid card placement or initial card side/secret objective not chosen"), usr);
                }
                break;
            case FLIP_CARD:
                if (inputController.verifyReceivedData(receivedMessage) && isInitCardPlaced(usr) && isSecretObjectiveChosen(usr)) {
                    flipCardHandler((FlipCardMessage) receivedMessage);
                } else {
                    ServerApp.sendMessageToClient(new InvalidInputMessage(usr, "can't be flipped"), usr);
                }
                break;
            case END_TURN:
                if (turnController.isDrawnCard() && turnController.isPlacedCard()) { //TODO try to end turn when the player hasn't placed a card or drawn a card ye in simulation game test
                    if (game.getPlayerByName(turnController.getActivePlayer()).getBoard().getPoints() >= 20 && !EndGame) {
                        game.setGameState(GameState.END_GAME);
                        endGame = true;
                        triggerEndGame();
                    }
                    turnController.changeTurn();
                }else {
                    ServerApp.sendMessageToClient(new InvalidInputMessage(usr, "finish turn actions first (place & draw a card)"), usr);
                }
                break;
            case LOGOUT_REQUEST:
                ServerApp.sendMessageToClient(new OkMessage(), usr);
                game.removePlayer(usr);
                break;
            case DEAD_CLIENT:
                game.removePlayer(usr);
                break;
            default:
                ServerApp.sendMessageToClient(new ErrorMessage("server", "message not recognized"), usr);
                break;
        }
    }


    /**
     * handles the messages received only from the active player when the game is in end game state
     * @param receivedMessage Message from Active Player.
     */
    private void inEndGameState(Message receivedMessage) {
        String usr = receivedMessage.getUsername();

        //no need to check if initial card is placed or secret objective is chosen because they have to in order to get to endgame state
        switch (receivedMessage.getMessageType()) {
            case PICK_RESOURCE_CARD:
                if (inputController.verifyReceivedData(receivedMessage)) {
                    drawResourceCardHandler((PickResourceCardMessage) receivedMessage);
                } else {
                    ServerApp.sendMessageToClient(new InvalidInputMessage(usr, "invalid input"), usr);
                }
                break;
            case PICK_GOLD_CARD:
                if (inputController.verifyReceivedData(receivedMessage)) {
                    drawGoldCardHandler((PickGoldCardMessage) receivedMessage);
                } else {
                    ServerApp.sendMessageToClient(new ErrorMessage("server", "invalid input"), usr);
                }
                break;
            case PLACE_CARD:
                if (inputController.verifyReceivedData(receivedMessage)) {
                    placeCardHandler((PlaceCardMessage) receivedMessage);
                } else {
                    ServerApp.sendMessageToClient(new ErrorMessage("server", "invalid card placement"), usr);
                }
                break;
            case FLIP_CARD:
                if (inputController.verifyReceivedData(receivedMessage)) {
                    flipCardHandler((FlipCardMessage) receivedMessage);
                } else {
                    ServerApp.sendMessageToClient(new ErrorMessage("server", "can't be flipped"), usr);
                }
                break;
            case END_TURN:
                if (game.getPlayerByName(turnController.getActivePlayer()).getBoard().getPoints() >= 20) {
                    if (endGame && countDown != -1) {
                        countDown--;
                    }
                    if (endGame && countDown == 0) {
                        game.setGameState(GameState.ENDED);
                        winners = game.getWinners();
                        for (String winner : winners) {
                            for (String player : game.getLobby()) {
                                ServerApp.sendMessageToClient(new WinnersMessage(player, "winner: " + winner), player);
                            }
                        }
                    }
                    if (endGame && turnController.getActivePlayer().equals(turnController.getLobby().getFirst())) {
                        countDown = 3;
                    }
                }
                turnController.changeTurn();
                break;
            case LOGOUT_REQUEST:
                //server.print("user wants to logout: " + getUsername());
                ServerApp.sendMessageToClient(new OkMessage(), usr);
                game.removePlayer(usr);
                break;
            case DEAD_CLIENT:
                game.removePlayer(usr);
                break;
            default:
                ServerApp.sendMessageToClient(new ErrorMessage("server", "message not recognized"), usr);
                break;
        }
    }


    public void triggerEndGame() {
        endGame = true;
    }

    public boolean isInitCardPlaced(String player) {
        return game.getPlayerByName(player).getBoard().isInitialCardChosen();
    }

    public boolean isSecretObjectiveChosen(String player) {
        return game.getPlayerByName(player).getBoard().isSecretObjectiveChosen();
    }

    /**
     * Starts the game ,creates the players and personal boards
     */
    public void startGame() {
        game.setGameState(GameState.INIT);
        game.createPlayers();
        game.drawHandForAllPlayers();
        game.setCommonObjectivesForallPlayers();
        game.setInitialCardForAllPlayers();
        turnController = TurnController.getInstance();
        game.setGameState(GameState.IN_GAME);
        game.notifyGameStarting();
    }

    /**
     * sets the secret objective for the player
     * @param pickMessage the message containing the pick
     */
    public void setSecretObjectiveHandler(PickSecretObjectiveMessage pickMessage) {
        Integer pick = pickMessage.getCardPick();
        String username = pickMessage.getUsername();

        game.setSecretObjectives(username, pick);
    }

    /**
     * draws a resource card for the player
     * @param pickMessage the message containing the pick
     */
    public void drawResourceCardHandler(PickResourceCardMessage pickMessage) {
        Integer pick = pickMessage.getCardPick();
        String username = pickMessage.getUsername();

        game.drawResourceCard(username, pick);
    }

    /**
     * draws a gold card for the player
     * @param pickMessage the message containing the pick
     */
    public void drawGoldCardHandler(PickGoldCardMessage pickMessage) {
        Integer pick = pickMessage.getCardPick();
        String username = pickMessage.getUsername();

        game.drawGoldCard(username, pick);
    }

    /**
     * picks the initial card side for the player
     * @param pickMessage the message containing the side
     */
    public void pickInitialCardSideHandler(PickInitialCardSideMessage pickMessage) {
        String username = pickMessage.getUsername();
        Face side = pickMessage.getCardSide();
        if (!game.getPlayerByName(username).getBoard().getInitialCard().getShownFace().equals(side)) {
            game.getPlayerByName(username).getBoard().getInitialCard().flip();
            game.getPlayerByName(username).getBoard().placeCard(pickMessage.getInitialCard(), 0, 0);
            game.getPlayerByName(username).getBoard().setInitialCardChosen(true);
        } else {
            game.getPlayerByName(username).getBoard().placeCard(pickMessage.getInitialCard(), 0, 0);
            game.getPlayerByName(username).getBoard().setInitialCardChosen(true);
        }
    }

    /**
     * places a card on the board of a player
     * @param placeCardMessage the message containing the card and the position
     */
    public void placeCardHandler(PlaceCardMessage placeCardMessage) {
        Card card = placeCardMessage.getCard();
        Integer x = placeCardMessage.getX();
        Integer y = placeCardMessage.getY();
        String username = placeCardMessage.getUsername();

        game.placeCard(card, x, y, game.getPlayerByName(username));
    }

    /**
     * flips the card in hand given by the message
     * @param flipCardMessage the message containing the card
     */
    public void flipCardHandler(FlipCardMessage flipCardMessage) {
        Card card = flipCardMessage.getCard();
        String username = flipCardMessage.getUsername();

        game.getPlayerByName(username).getBoard().flipCardInHand(card);
    }

    /**
     * getter for the turn controller
     * @return the turn controller
     */
    public TurnController getTurnController() {
        return turnController;
    }

    public GameController setNumPlayers(int numPlayers) {
        this.game.setNumPlayers(numPlayers);
        return this;
    }
}