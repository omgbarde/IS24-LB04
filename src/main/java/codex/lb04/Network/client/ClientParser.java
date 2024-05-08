package codex.lb04.Network.client;

import codex.lb04.Message.GameMessage.GameStateMessage;
import codex.lb04.Message.LoginReply;
import codex.lb04.Message.LogoutReply;
import codex.lb04.Message.Message;
import codex.lb04.Message.PlayersConnectedMessage;
import codex.lb04.View.View;
import javafx.application.Platform;

/**
 * This class parses messages client side
 */
public class ClientParser {
    ClientSocket clientSocket;


    View view;

    //ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

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
            case LOGOUT_REPLY:
                if (((LogoutReply) input).isAccepted()) {
                    clientSocket.disconnect();
                    view.drawHelloScene();
                    clientSocket.disconnect();
                } else {
                    view.print("logout refused");
                }
                break;
                //Todo: renderlo view independent
            case DRAW_CARD:
                Platform.runLater(() -> view.update(input));
                break;
            case UPDATE_GOLD:
                view.update(input);
                break;
            case ERROR:
                view.displayAlert(input.toString());
                clientSocket.disconnect();
                view.drawHelloScene();
                break;
            case OK_MESSAGE:
                view.print("server: received");
                break;
            case GENERIC_MESSAGE:
                view.displayAlert(input.toString());
                break;
            case INVALID_INPUT:
                view.displayAlert(input.toString());
                break;
                //Todo: non usare piu questo messaggio
            case GAME_STATE:
                sceneMap((GameStateMessage) input);
                break;
            case START_GAME:
                break;
            case DRAW_BOARD:
                Platform.runLater(()->view.drawBoardScene());
                break;
            default:
                view.displayAlert("message not recognized");
                clientSocket.disconnect();
                break;
        }
    }

    private void sceneMap(GameStateMessage input) {
        switch (input.getGameState()) {
            case LOGIN:
                Platform.runLater(() -> view.drawHelloScene());
                break;
            case INIT:
                Platform.runLater(() -> view.drawLobbyScene());
                break;
            case IN_GAME:
                Platform.runLater(() -> view.drawBoardScene());
                break;
            case END_GAME:
                break;
            case ENDED:
                //view.drawResults();
                break;
            default:
                view.drawHelloScene();
                break;
        }
    }


}
