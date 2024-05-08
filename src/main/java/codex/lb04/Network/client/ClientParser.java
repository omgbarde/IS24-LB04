package codex.lb04.Network.client;

import codex.lb04.Message.DrawMessage.DrawCardMessage;
import codex.lb04.Message.DrawMessage.UpdateGoldMessage;
import codex.lb04.Message.LoginReply;
import codex.lb04.Message.Message;
import codex.lb04.Message.PlayersConnectedMessage;
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
                //Todo: renderlo view independent
            case DRAW_CARD:
                view.drawCard(((DrawCardMessage) input).getCard());
                break;
            case UPDATE_GOLD:
                view.updateGold(((UpdateGoldMessage)input).getGold());
                break;
            case ERROR:
                view.displayAlert(input.toString());
                clientSocket.disconnect();
                view.drawHelloScene();
                break;
            case GENERIC_MESSAGE:
                view.displayAlert(input.toString());
                break;
            case INVALID_INPUT:
                view.displayAlert(input.toString());
                break;
            case START_GAME:
                break;
            case DRAW_BOARD:
                view.drawBoardScene();
                break;
            default:
                view.displayAlert("message not recognized");
                clientSocket.disconnect();
                break;
        }
    }
}
