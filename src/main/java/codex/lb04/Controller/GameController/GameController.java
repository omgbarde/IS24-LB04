package codex.lb04.Controller.GameController;

import codex.lb04.Message.Message;
import codex.lb04.Message.PickGoldCardMessage;
import codex.lb04.Message.PickResourceCardMessage;
import codex.lb04.Message.PickSecretObjectiveMessage;
import codex.lb04.Model.Game;
import codex.lb04.Model.GameState;

public class GameController {

    private Game game;
    private InputController inputController;
    private TurnController turnController;
    private GameState gameState;

    public GameController(Game game) {
        this.game = game;
        this.inputController = new InputController(this, game);
    }

    /**
     * when a message is received, the controller checks the game state and the message type
     *
     * @param receivedMessage Message from Active Player.
     */
    public void onMessageReceived(Message receivedMessage) {

        switch (gameState) {
            case LOGIN:
                //TODO
                break;
            case INIT:
                //TODO
                break;
            case IN_GAME:
                if (inputController.checkUser(receivedMessage)) {
                    inGameState(receivedMessage);
                }
                break;
            default:
                break;
        }
    }

    /**
     * handles the messages received when the game is in progress
     * @param receivedMessage Message from Active Player.
     */
    private void inGameState(Message receivedMessage) {
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
            default:
                break;
        }
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
     * getter for the turn controller
     * @return the turn controller
     */
    public TurnController getTurnController() {
        return turnController;
    }
}
