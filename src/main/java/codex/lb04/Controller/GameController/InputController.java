package codex.lb04.Controller.GameController;

import codex.lb04.Message.Message;
import codex.lb04.Message.*;
import codex.lb04.Model.Game;

public class InputController {

    private GameController gameController;
    private Game game;

    public InputController(GameController gameController, Game game) {
        this.gameController = gameController;
        this.game = game;
    }

    /**
     * Verify data sent by client to server.
     *
     * @param message Message from Client.
     * @return {code @true} if Message contains valid data {@code false} otherwise.
     */
    public boolean verifyReceivedData(Message message) {

        switch (message.getMessageType()) {
            case PICK_SECRET_OBJECTIVE:
                return PickSecretObjectiveCheck(message);
            case PICK_RESOURCE_CARD:
                return PickResourceCardCheck(message);
            case PICK_GOLD_CARD:
                return PickGoldCardCheck(message);
            default: // should never reach this statement.
                return false;
        }

    }


    public boolean PickSecretObjectiveCheck(Message message) {
        return ((PickSecretObjectiveMessage) message).getCardPick() >= 0 && ((PickSecretObjectiveMessage) message).getCardPick() <= 1;
    }

    public boolean PickResourceCardCheck(Message message) {
        return ((PickResourceCardMessage) message).getCardPick() >= 0 && ((PickResourceCardMessage) message).getCardPick() <= 2;
    }

    public boolean PickGoldCardCheck(Message message) {
        return ((PickGoldCardMessage) message).getCardPick() >= 0 && ((PickGoldCardMessage) message).getCardPick() <= 2;
    }

    public boolean checkUser(Message receivedMessage) {
        return receivedMessage.getUsername().equals(gameController.getTurnController().getActivePlayer());
    }
}
