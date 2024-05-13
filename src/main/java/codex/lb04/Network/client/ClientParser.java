package codex.lb04.Network.client;

import codex.lb04.Message.DrawMessage.*;
import codex.lb04.Message.GameMessage.PlaceCardMessage;
import codex.lb04.Message.LoginReply;
import codex.lb04.Message.Message;
import codex.lb04.Message.PlayersConnectedMessage;
import codex.lb04.Message.PongMessage;
import codex.lb04.View.View;

/**
 * This class parses messages client side
 */
public class ClientParser {
    ClientSocket clientSocket;

    View view;

    public ClientParser(ClientSocket clientSocket, View view) {
        this.clientSocket = clientSocket;
        this.view = view;
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
                    view.drawLobbyScene();
                } else {
                    view.print("login refused");
                    clientSocket.disconnect();
                    view.drawHelloScene();
                }
                break;
            case PLAYERS_CONNECTED:
                view.updateLobby(((PlayersConnectedMessage) input).getLobby());
                break;
            case DRAW_CARD: //TODO non verrà mai usato
                view.drawCard(((DrawCardMessage) input).getCard());
                break;
            case UPDATE_GOLD:
                view.updateGold(((UpdateGoldMessage) input).getGold());
                break;
            case UPDATE_RESOURCE:
                view.updateResource(((UpdateResourceMessage) input).getResource());
                break;
             case UPDATE_SECRET_OBJECTIVE:
                view.updateSecretObjective(((UpdateSecretObjectiveMessage) input) . getSecretObjective());
                break;
            case UPDATE_HAND:
                view.updateHand(((UpdateHandMessage) input).getHand());
                break;
            case UPDATE_COMMON_OBJECTIVES:
                view.updateCommonObjectives(((UpdateCommonObjectivesMessage) input).getCommonObjectives());
                break;
            case UPDATE_INITIAL_CARD_DISPLAY:
                view.updateInitialCardDisplay(((UpdateInitialCardDisplayMessage) input).getInitialCard());
                break;
            case UPDATE_SECRET_OBJECTIVE_TO_CHOOSE:
                view.updateSecretObjectiveToChoose(((UpdateSecretObjectiveToChooseMessage) input).getSecretObjectives());
                break;
            case PLACE_CARD:
                view.placeCard(((PlaceCardMessage) input).getX() , ((PlaceCardMessage) input).getY() , ((PlaceCardMessage) input).getCard());
                view.deselectCard();
                break;
            case ERROR:
                view.displayAlert(input.toString());
                clientSocket.disconnect();
                view.drawHelloScene();
                break;
            case GENERIC_MESSAGE, INVALID_INPUT:
                view.displayAlert(input.toString());
                break;
            case PING:
                clientSocket.sendMessage(new PongMessage("pong"));
                System.out.println("pinged!");
                break;
            case DRAW_BOARD:
                view.drawBoardScene();
                clientSocket.sendMessage(new ReadyMessage("ready"));
                break;
            case START_TURN:
                view.setYourTurnText();
                break;
            case UPDATE_POINTS:
                view.updatePoints(((UpdatePointsMessage) input).getPoints());
                break;
            case END_TURN:

                view.cleanYourTurnText();
                break;
            default:
                view.displayAlert("message not recognized");
                clientSocket.disconnect();
                break;
        }
    }
}
