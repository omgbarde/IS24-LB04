package codex.lb04.Network.server;

import codex.lb04.Message.*;

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
        //TODO implementare metodo parse e aggiungere casi per gli altri messaggi

        switch (input.getMessageType()) {
            case LOGIN_REQUEST:
                if (clientHandler.getUsername() == null) {
                    String usr = input.getUsername();
                    //checks maximum number of clients connected and if username is available
                    if (ServerApp.getNumClient() <= 4 && ServerApp.checkUsername(usr)) {
                        clientHandler.setUsername(usr);
                        clientHandler.sendMessage(new LoginReply(input.getUsername(), true));
                        //risponderà solo con fconferma ricezione
                        //clientHandler.sendMessage(new OkMessage())
                    } else clientHandler.sendMessage(new LoginReply(input.getUsername(), false));
                }
                break;

            case LOGOUT_REQUEST:
                //server.print("user wants to logout: " + getUsername());
                clientHandler.sendMessage(new OkMessage());
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
