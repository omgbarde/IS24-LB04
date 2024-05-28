package codex.lb04.Network.client;

import codex.lb04.Message.DrawMessage.*;
import codex.lb04.Message.GameMessage.PlaceCardMessage;
import codex.lb04.Message.GameMessage.WinnersMessage;
import codex.lb04.Message.LoginReply;
import codex.lb04.Message.Message;
import codex.lb04.Message.PlayersConnectedMessage;
import codex.lb04.Message.PongMessage;
import codex.lb04.View.ViewController;

/**
 * This class parses messages client side
 */
public class ClientParser {
    ClientSocket clientSocket;

    ViewController viewController;

    /**
     * Constructor for ClientParser
     *
     * @param clientSocket   the client socket
     * @param viewController the view controller
     */
    public ClientParser(ClientSocket clientSocket, ViewController viewController) {
        this.clientSocket = clientSocket;
        this.viewController = viewController;
    }

    /**
     * this method parses messages from the client and invokes methods based on the type and parameters of those messages
     *
     * @param input is the message passed from the client
     */
    public void handleInput(Message input) {
        switch (input.getMessageType()) {
            case LOGIN_REPLY:
                if (((LoginReply) input).isAccepted()) {
                    viewController.drawLobbyScene();
                } else {
                    viewController.displayAlert("login refused");
                    clientSocket.disconnect();
                    viewController.drawHelloScene();
                }
                break;
            case PLAYERS_CONNECTED:
                viewController.updateLobby(((PlayersConnectedMessage) input).getLobby());
                break;
            case UPDATE_GOLD:
                viewController.updateDrawableGold(((UpdateGoldMessage) input).getGold());
                break;
            case UPDATE_RESOURCE:
                viewController.updateDrawableResources(((UpdateResourceMessage) input).getResource());
                break;
            case UPDATE_SECRET_OBJECTIVE:
                viewController.updateSecretObjective(((UpdateSecretObjectiveMessage) input).getSecretObjective());
                break;
            case UPDATE_HAND:
                viewController.updateHand(((UpdateHandMessage) input).getHand());
                break;
            case UPDATE_COMMON_OBJECTIVES:
                viewController.updateCommonObjectives(((UpdateCommonObjectivesMessage) input).getCommonObjectives());
                break;
            case UPDATE_INITIAL_CARD_DISPLAY:
                viewController.updateInitialCardDisplay(((UpdateInitialCardDisplayMessage) input).getInitialCard());
                break;
            case UPDATE_SECRET_OBJECTIVE_TO_CHOOSE:
                viewController.updateSecretObjectiveToChoose(((UpdateSecretObjectiveToChooseMessage) input).getSecretObjectives());
                break;
            case PLACE_CARD:
                viewController.placeCard(((PlaceCardMessage) input).getX(), ((PlaceCardMessage) input).getY(), ((PlaceCardMessage) input).getCard());
                viewController.deselectCard();
                break;
            case ERROR:
                viewController.displayAlert(input.toString());
                clientSocket.disconnect();
                viewController.drawHelloScene();
                break;
            case GENERIC_MESSAGE, INVALID_INPUT:
                viewController.displayAlert(input.toString());
                break;
            case PING:
                clientSocket.sendMessage(new PongMessage("pong" + System.currentTimeMillis()));
                break;
            case DRAW_BOARD:
                viewController.drawBoardScene();
                clientSocket.sendMessage(new ReadyMessage("ready"));
                break;
            case START_TURN:
                viewController.setYourTurnText();
                break;
            case UPDATE_POINTS:
                viewController.updatePoints(((UpdatePointsMessage) input).getPoints());
                break;
            case END_TURN:
                viewController.cleanYourTurnText();
                break;
            case CHAT_MESSAGE:
                viewController.updateChat(input.toString());
                break;
            case WINNERS:
                viewController.showWinners(((WinnersMessage) input).getWinner());
                break;
            default: //should never be reached
                viewController.displayAlert("message not recognized");
                clientSocket.disconnect();
                break;
        }
    }
}
