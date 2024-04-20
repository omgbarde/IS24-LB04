package codex.lb04.Network.server;

import codex.lb04.Message.*;

/**
 * class that represents the message parser
 */
public class Parser {
    ClientHandler clientHandler;

    public Parser(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    /**
     * this method parses messages from the client and invokes methods based on the type and parameters of those messages
     *
     * @param input is the message passed from the client
     */
    public void handleInput(Message input) {
        //TODO implementare metodo parse

        if (input.getMessageType().equals(MessageType.LOGIN_REQUEST) && clientHandler.getUsername() == null) {
            clientHandler.setUsername(input.getUsername());
            //server.print("new user wants to play: " + getUsername());
            //mando al game, risponde lui se accettato
            clientHandler.sendMessage(new LoginReply(input.getUsername(), true));
            //risponderà solo con fconferma ricezione
            clientHandler.sendMessage(new OkMessage());
        } else if (input.getMessageType().equals(MessageType.LOGOUT_REQUEST)) {
            //server.print("user wants to logout: " + getUsername());
            clientHandler.sendMessage(new OkMessage());
        } else {
            //server.print("message not recognized");
            ErrorMessage error = new ErrorMessage("server", "message not recognized or double login");
            clientHandler.sendMessage(error);
        }
    }
}
