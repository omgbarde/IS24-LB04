package codex.lb04.Observer;

import codex.lb04.Message.Message;
import codex.lb04.Network.server.Server;

/**
 * this class represents the observer of the game
 */
public class GameObserver implements Observer {
    /**
     * method called on changes of the model that notifies the clients connected with different messages
     *
     * @param message the message to be sent to one or more clients
     */
    @Override
    public void update(Message message) {
        String usr = message.getUsername();
        switch (message.getMessageType()) {
            case LOGIN_REPLY, FLIP_CARD, UPDATE_INITIAL_CARD_DISPLAY, UPDATE_SECRET_OBJECTIVE_TO_CHOOSE,
                 UPDATE_SECRET_OBJECTIVE, UPDATE_HAND, UPDATE_POINTS, PLACE_CARD, START_TURN, END_TURN:
                Server.sendMessageToClient(message, usr);
                break;
            case PLAYERS_CONNECTED, UPDATE_GOLD, UPDATE_RESOURCE, DRAW_BOARD, UPDATE_COMMON_OBJECTIVES,
                 GENERIC_MESSAGE, WINNERS:
                Server.broadcast(message);
                break;
            case PICK_GOLD_CARD, PICK_INITIAL_CARD_SIDE, PICK_RESOURCE_CARD, PICK_SECRET_OBJECTIVE:
                break;
            default: //should never be reached
                Server.print("Message from game not recognize");
                break;
        }
    }
}
