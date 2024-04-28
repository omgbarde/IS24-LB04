package codex.lb04.Network.client;

import codex.lb04.CodexClientApp;
import codex.lb04.Controller.SceneController.HelloController;
import codex.lb04.Message.LoginReply;
import codex.lb04.Message.Message;

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
    public void handleInput(Message input) {
        switch (input.getMessageType()) {
            case LOGIN_REPLY:
                if (((LoginReply) input).isAccepted()) {
                    //TODO astrarre view per fare in questo modo e fare view controller
                    //CodexClientApp.getView().switchToLobby();
                    HelloController.switchToLobby();
                } else {
                    CodexClientApp.print("login refused");
                    clientSocket.disconnect();
                }
                break;
            case LOGOUT_REPLY:
                //TODO vedere come farlo fare al controller della scena
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

            default:
                CodexClientApp.print("message not recognized");
                clientSocket.disconnect();
                break;
        }
    }
}
