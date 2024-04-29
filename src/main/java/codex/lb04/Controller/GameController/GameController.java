package codex.lb04.Controller.GameController;

import codex.lb04.Message.*;
import codex.lb04.Message.GameMessage.*;
import codex.lb04.Model.Card;
import codex.lb04.Model.Enumerations.GameState;
import codex.lb04.Model.Face;
import codex.lb04.Model.Game;
import codex.lb04.Model.InitialCard;
import codex.lb04.ServerApp;

public class GameController {

    private Game game;
    private InputController inputController;
    private TurnController turnController;
    private static GameController instance;


    public static GameController getInstance(){
        if (instance == null) {
            instance = new GameController();
        }
        return instance;
    }

    private GameController() {
        createGameController();
    }


    private void createGameController(){
        this.game = Game.getInstance();
        this.inputController = new InputController(this, game);
        //game.setGameState(GameState.LOGIN);
    }


    /**
     * when a message is received, the controller checks the game state and the message type
     * this method will be used in the server to handle the messages
     * @param receivedMessage Message from Active Player.
     */
    public void onMessageReceived(Message receivedMessage) {
        if(receivedMessage.getMessageType() == MessageType.DEAD_CLIENT){
            game.removePlayer(receivedMessage.getUsername());
            game.removePlayerName(receivedMessage.getUsername());
            turnController.removePlayer(receivedMessage.getUsername());
        }

        switch (game.getGameState()) {
            case LOGIN:
                    inLoginState(receivedMessage);
                break;
            case INIT:
                ServerApp.sendMessage(new GenericMessage("server", "invalid in this phase"), receivedMessage.getUsername());
                break;
            case IN_GAME:
                if (inputController.checkUser(receivedMessage)) { // check if the message is from the active player
                    inGameState(receivedMessage);
                }
                break;
            case END_GAME:
                if (inputController.checkUser(receivedMessage)) { // check if the message is from the active player
                    //inEndGameState(receivedMessage);
                }
                break;
            case ENDED:
                ServerApp.sendMessage(new GenericMessage("server", "game is ended, play again or quit"), receivedMessage.getUsername());
                break;
            default:
                ServerApp.sendMessage(new ErrorMessage("server", "game state not recognized"), receivedMessage.getUsername());
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
            case LOGIN_REQUEST:
                //checks maximum number of clients connected and if username is available
                if (game.getPlayers().size() <= 4 && ServerApp.checkUsername(usr)) {
                    game.addPlayerName(usr);
                } else ServerApp.sendMessage(new LoginReply(usr, false), usr);
                break;
            case LOGOUT_REQUEST:
                ServerApp.sendMessage(new OkMessage(), usr);
                game.removePlayerName(usr);
                break;
            case PONG:
                break;
            case START_GAME:
                if (game.getPlayerNames().size() >= 2 && game.getPlayerNames().size() <= 4){
                    startGame();
                }
                break;
            case ERROR:
                ErrorMessage error = new ErrorMessage("server",((ErrorMessage)receivedMessage).getError());
                ServerApp.sendMessage(error, usr);
                break;
            default:
                ErrorMessage defaultError = new ErrorMessage("server", "message not recognized or double login");
                ServerApp.sendMessage(defaultError, usr);
                break;
        }
    }


    /**
     * handles the messages received when the game is in progress
     * @param receivedMessage Message from Active Player.
     */
    private void inGameState(Message receivedMessage) {
        String usr = receivedMessage.getUsername();
        switch (receivedMessage.getMessageType()) {
            case PICK_SECRET_OBJECTIVE:
                if (inputController.verifyReceivedData(receivedMessage)) {
                    setSecretObjectiveHandler((PickSecretObjectiveMessage) receivedMessage);
                }
                break;
            case PICK_RESOURCE_CARD:
                if (inputController.verifyReceivedData(receivedMessage)) {
                    drawResourceCardHandler((PickResourceCardMessage) receivedMessage);
                }
                break;
            case PICK_GOLD_CARD:
                if (inputController.verifyReceivedData(receivedMessage)) {
                    drawGoldCardHandler((PickGoldCardMessage) receivedMessage);
                }
                break;
            case PICK_INITIAL_CARD_SIDE:
                if (inputController.verifyReceivedData(receivedMessage)) {
                    pickInitialCardSideHandler((PickInitialCardSideMessage) receivedMessage);
                }
            case PLACE_CARD:
                if (inputController.verifyReceivedData(receivedMessage)) {
                    placeCardHandler((PlaceCardMessage) receivedMessage);
                }
                break;
            case FLIP_CARD:
                if (inputController.verifyReceivedData(receivedMessage)) {
                    flipCardHandler((FlipCardMessage) receivedMessage);
                }
                break;
            case END_TURN:
                turnController.changeTurn();
                break;
            case LOGOUT_REQUEST:
                //server.print("user wants to logout: " + getUsername());
                ServerApp.sendMessage(new OkMessage(), usr);
                game.removePlayerName(usr);
                break;

            default:
                break;
        }
    }

    /**
     * Starts the game ,creates the players and personal boards
     */
    public void startGame() {
        game.setGameState(GameState.INIT);
        game.createPlayers();
        turnController = TurnController.getInstance();
        game.setGameState(GameState.IN_GAME);
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

    public void pickInitialCardSideHandler(PickInitialCardSideMessage pickMessage) {
        String username = pickMessage.getUsername();
        Face side = pickMessage.getCardSide();
        for(InitialCard initialCard : game.getDeck().getInitialCards()){
            if(!initialCard.getShownFace().equals(side)){
                game.getInitialCard(username).flip();
            }
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
}
