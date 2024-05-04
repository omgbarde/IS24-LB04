package codex.lb04.Network.client;

import codex.lb04.CodexClientApp;
import codex.lb04.Message.GameMessage.GameStateMessage;
import codex.lb04.Message.LoginReply;
import codex.lb04.Message.Message;
import codex.lb04.Message.PlayersConnectedMessage;

public class ClientParser {

    ClientSocket clientSocket;

    public ClientParser(ClientSocket clientSocket) {
        this.clientSocket = clientSocket;
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
                    CodexClientApp.getView().switchScene("Lobby");
                    //CodexClientApp.getView().setMode("fullscreen");
                } else {
                    CodexClientApp.print("login refused");
                    clientSocket.disconnect();
                }
                break;
            case PLAYERS_CONNECTED:
                CodexClientApp.getView().updateListLater(((PlayersConnectedMessage)input).getLobby());
                break;
            case LOGOUT_REPLY:
                CodexClientApp.getView().switchScene("Hello");
                break;
            case ERROR:
                CodexClientApp.print("error: " + input.toString());
                clientSocket.disconnect();
                break;
            case OK_MESSAGE:
                CodexClientApp.print("server: received");
                break;
            case GENERIC_MESSAGE:
                CodexClientApp.print(input.toString());
                break;
            case GAME_STATE:
                CodexClientApp.getView().switchScene(sceneMap((GameStateMessage)input)) ;
                break;

            default:
                CodexClientApp.print("message not recognized");
                clientSocket.disconnect();
                break;
        }
    }

    private String sceneMap(GameStateMessage input) {
        switch (input.getGameState()) {
            case LOGIN:
                return "Hello";
            case INIT:
                return "Lobby";
            case IN_GAME:
                return "Board";
            case END_GAME:
                break;
            case ENDED:
                return "Results";
            default:
                return "Hello";
        }
        return null;
    }
}
