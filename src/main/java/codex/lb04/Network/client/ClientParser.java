package codex.lb04.Network.client;

import codex.lb04.Controller.HelloController;
import codex.lb04.GuiApp;
import codex.lb04.Message.*;
import codex.lb04.ServerApp;

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
                    HelloController.switchToLobby();
                } else {
                    GuiApp.print("login refused");
                    clientSocket.disconnect();
                }
                break;
            case LOGOUT_REPLY:
                //TODO vedere come farlo fare al controller della scena
                GuiApp.getGuiView().switchScene("Hello");
                break;
            case ERROR:
                GuiApp.print("error: " + input.toString());
                clientSocket.disconnect();
                break;
            case OK_MESSAGE:
                GuiApp.print("server: received");
                break;
            case GENERIC_MESSAGE:
                GuiApp.print(input.toString());
                break;

            default:
                GuiApp.print("message not recognized");
                clientSocket.disconnect();
                break;
        }
    }
}
