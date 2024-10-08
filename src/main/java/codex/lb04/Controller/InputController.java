package codex.lb04.Controller;

import codex.lb04.Message.GameMessage.*;
import codex.lb04.Message.Message;
import codex.lb04.Model.Card;
import codex.lb04.Model.Game;

/**
 * InputController class is responsible for verifying the data sent by the client to the server.
 */
public class InputController {

    private final GameController gameController;
    private final Game game;

    /**
     * Constructor for InputController
     *
     * @param gameController is the GameController
     * @param game           is the Game object
     */
    public InputController(GameController gameController, Game game) {
        this.gameController = gameController;
        this.game = game;
    }

    /**
     * Verify data sent by client to server.
     *
     * @param message Message from Client.
     * @return true if Message contains valid data false otherwise.
     */
    public boolean verifyReceivedData(Message message) {
        return switch (message.getMessageType()) {
            case PICK_SECRET_OBJECTIVE -> pickSecretObjectiveCheck(message);
            case PICK_RESOURCE_CARD -> pickResourceCardCheck(message);
            case PICK_GOLD_CARD -> pickGoldCardCheck(message);
            case PICK_INITIAL_CARD_SIDE -> pickInitialCardSideCheck(message);
            case PLACE_CARD -> placeCardCheck(message);
            case FLIP_CARD -> flipCardCheck(message);
            default -> false;  // should never reach this statement
        };
    }

    /**
     * Check if the card pick is valid.
     *
     * @param message Message from Client.
     * @return {code @true} if card pick is valid {@code false} otherwise.
     */
    public boolean pickSecretObjectiveCheck(Message message) {
        return ((PickSecretObjectiveMessage) message).getCardPick() >= 0 && ((PickSecretObjectiveMessage) message).getCardPick() <= 1;
    }

    /**
     * Check if the card pick is valid.
     *
     * @param message Message from Client.
     * @return {code @true} if card pick is valid {@code false} otherwise.
     */
    public boolean pickResourceCardCheck(Message message) {
        Integer pick = ((PickResourceCardMessage) message).getCardPick();
        return pick >= 0 && pick <= 2 && pick < game.getDeck().getVisibleResourceCards().size();
    }

    /**
     * Check if the card pick is valid.
     *
     * @param message Message from Client.
     * @return {code @true} if card pick is valid {@code false} otherwise.
     */
    public boolean pickGoldCardCheck(Message message) {
        Integer pick = ((PickGoldCardMessage) message).getCardPick();
        return pick >= 0 && pick <= 2 && pick < game.getDeck().getVisibleGoldCards().size();
    }

    /**
     * Check if the initial card pick is valid.
     *
     * @param message Message from Client.
     * @return {code @true} if card pick is valid {@code false} otherwise.
     */
    public boolean pickInitialCardSideCheck(Message message) {
        String usr = message.getUsername();
        return ((PickInitialCardSideMessage) message).getInitialCard().getID().equals(game.getPlayerByName(usr).getBoard().getInitialCard().getID());
    }

    /**
     * check if the card can be placed in the given coordinates
     *
     * @param message Message from Client.
     * @return {code @true} if card can be placed {@code false} otherwise.
     */
    public boolean placeCardCheck(Message message) {
        Integer X = ((PlaceCardMessage) message).getX();
        Integer Y = ((PlaceCardMessage) message).getY();
        Card card = ((PlaceCardMessage) message).getCard();
        return game.getPlayerByName(message.getUsername()).getBoard().canBePlaced(X, Y, card);
    }

    /**
     * check if the card can be flipped (if it's in the hand)
     *
     * @param message Message from Client.
     * @return {code @true} if card can be flipped {@code false} otherwise.
     */
    public boolean flipCardCheck(Message message) {
        return game.getPlayerByName(message.getUsername()).getBoard().isInHand(((FlipCardMessage) message).getCard());

    }

    /**
     * Check if the user is the active player.
     *
     * @param receivedMessage Message from Client.
     * @return {code @true} if user is the active player {@code false} otherwise.
     */
    public boolean checkUser(Message receivedMessage) {
        return receivedMessage.getUsername().equals(gameController.getTurnController().getActivePlayer());
    }
}
