package codex.lb04.Observer;

import codex.lb04.Controller.GameController.GameController;
import codex.lb04.Message.Message;
import codex.lb04.ServerApp;

public class GameObserver implements Observer {
    //TODO has to update the view based on the message received
    @Override
    public void update(Message message) {
        String usr = message.getUsername();
        switch (message.getMessageType()) {
            case LOGIN_REPLY:
                ServerApp.sendMessage(message,usr);
                break;
            case PLAYERS_CONNECTED:
                ServerApp.broadcast(message);
                break;
            case GAME_STATE:
                ServerApp.broadcast(message);
                break;
            case FLIP_CARD:
                ServerApp.sendMessage(message,usr);
                break;
            case PLACE_CARD:
                break;
            case PICK_GOLD_CARD:
                break;
            case PICK_INITIAL_CARD_SIDE:
                break;
            case PICK_RESOURCE_CARD:
                break;
            case PICK_SECRET_OBJECTIVE:
                break;
            case START_GAME:
                GameController.getInstance().onMessageReceived(message);
                break;
            case END_TURN:
                break;
            default:
                ServerApp.print("Message from game not recognized");
                break;
        }

    }
}
