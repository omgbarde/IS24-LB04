package codex.lb04.Controller.GameController;

import codex.lb04.Message.Message;
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
     * Switch on Game State.
     *
     * @param receivedMessage Message from Active Player.
     */
    public void onMessageReceived(Message receivedMessage) {

        switch (gameState) {
            case LOGIN:
                break;
            case INIT:
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

    private void inGameState(Message receivedMessage) {
        switch (receivedMessage.getMessageType()) {
            case PICK_SECRET_OBJECTIVE:
                if (inputController.verifyReceivedData(receivedMessage)) {
                    setSecretObjectiveHandler((PickSecretObjectiveMessage) receivedMessage);
                }
                break;
            default:
                break;
        }
    }


    public void setSecretObjectiveHandler(PickSecretObjectiveMessage pickMessage) {
        Integer pick = pickMessage.getCardPick();
        String username = pickMessage.getUsername();

        game.setSecretObjectives(username, pick);

    }

    public TurnController getTurnController() {
        return turnController;
    }
}
