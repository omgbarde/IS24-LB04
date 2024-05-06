package codex.lb04.Network.client;

import codex.lb04.CodexClientApp;
import codex.lb04.Message.GameMessage.GameStateMessage;
import codex.lb04.Message.LoginReply;
import codex.lb04.Message.Message;
import codex.lb04.Message.PlayersConnectedMessage;
import codex.lb04.View.View;
import javafx.application.Platform;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class ClientParser {
    ClientSocket clientSocket;

    View view;

    ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    public ClientParser(ClientSocket clientSocket) {
        this.clientSocket = clientSocket;
        this.view = CodexClientApp.getView();
    }

    /**
     * this method parses messages from the client and invokes methods based on the type and parameters of those messages
     *
     * @param input is the message passed from the client
     */
    public void handleInput(Message input) {//TODO handle messages from wrong inputs (see gamecontroller)
        switch (input.getMessageType()) {
            case LOGIN_REPLY:
                //potrebbe essere inutile ora che manda game state
                if (((LoginReply) input).isAccepted()) {
                    Platform.runLater(()->view.drawLobbyScene());
                } else {
                    view.print("login refused");
                    clientSocket.disconnect();
                    Platform.runLater(()->view.drawHelloScene());
                }
                break;
            case PLAYERS_CONNECTED:
                /*Task updateListTask = new Task() {
                    @Override
                    protected Object call() throws Exception {
                        view.updateLobby(((PlayersConnectedMessage)input).getLobby());
                        return null;
                    }
                };*/
                //executorService.schedule(updateListTask,2000, TimeUnit.MILLISECONDS);
                Platform.runLater(()->view.updateLobby(((PlayersConnectedMessage)input).getLobby()));
                break;
            case LOGOUT_REPLY:
                Platform.runLater(()->view.drawHelloScene());
                break;
            case ERROR:
                view.print("error: " + input.toString());
                clientSocket.disconnect();
                break;
            case OK_MESSAGE:
                view.print("server: received");
                break;
            case GENERIC_MESSAGE:
                view.print(input.toString());
                break;
            case GAME_STATE:
                sceneMap((GameStateMessage)input);
                break;

            default:
                view.print("message not recognized");
                clientSocket.disconnect();
                break;
        }
    }

    private void sceneMap(GameStateMessage input) {
        switch (input.getGameState()) {
            case LOGIN:
                Platform.runLater(()->view.drawHelloScene());
                break;
            case INIT:
                Platform.runLater(()->view.drawLobbyScene());
                break;
            case IN_GAME:
                return "Board";
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

    public void setClientSocket(ClientSocket clientSocket) {
        this.clientSocket = clientSocket;
    }
}
