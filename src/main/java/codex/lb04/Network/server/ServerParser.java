package codex.lb04.Network.server;

import codex.lb04.Controller.GameController.PlayerController;
import codex.lb04.Message.ErrorMessage;
import codex.lb04.Message.LoginReply;
import codex.lb04.Message.Message;
import codex.lb04.Message.OkMessage;
import codex.lb04.ServerApp;

/**
 * class that represents the message parser
 */
public class ServerParser {
    ClientHandler clientHandler;

    public ServerParser(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    /**
     * this method parses messages from the client and invokes methods based on the type and parameters of those messages
     *
     * @param input is the message passed from the client
     */
    public void handleInput(Message input) {
        //TODO aggiungere casi per gli altri messaggi
        switch (input.getMessageType()) {
            case LOGIN_REQUEST:
                if (clientHandler.getUsername() == null) {
                    String usr = input.getUsername();
                    //checks maximum number of clients connected and if username is available
                    if (ServerApp.getNumClient() <= 4 && ServerApp.checkUsername(usr)) {
                        clientHandler.setUsername(usr);
                        clientHandler.sendMessage(new OkMessage());
                        PlayerController.addPlayer(usr);
                        //TODO ora manda solo l'ack ma dovrebbe mandare anche la lista dei giocatori, compito dell' observer
                    } else clientHandler.sendMessage(new LoginReply(input.getUsername(), false));
                }
                break;

            case LOGOUT_REQUEST:
                //server.print("user wants to logout: " + getUsername());
                clientHandler.sendMessage(new OkMessage());
                PlayerController.removePlayer(clientHandler.getUsername());
                //TODO ora manda solo l'ack ma dovrebbe mandare anche la lista dei giocatori, compito dell' observer
                break;
            case PONG:

            case ERROR:

            default:
                ErrorMessage error = new ErrorMessage("server", "message not recognized or double login");
                clientHandler.sendMessage(error);
                break;
        }
    }
}
