
package codex.lb04;

import codex.lb04.Controller.GameController;
import codex.lb04.Message.ErrorMessage;
import codex.lb04.Message.Message;
import codex.lb04.Message.MessageType;
import codex.lb04.Network.server.ClientHandler;
import codex.lb04.Utils.ConnectionUtil;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * ServerApp class that represents the server side of the game.
 */
public class ServerApp implements Runnable {
    //default port
    private static int port;
    //list of all client handlers
    private static final List<ClientHandler> clientHandlerList = new ArrayList<>();
    private final GameController gameController = GameController.getInstance();

    /**
     * sends a message to a specific client
     *
     * @param message is the message to be sent
     * @param username is the username of the client
     */
    public static void sendMessageToClient(Message message, String username) {
        for (ClientHandler clientHandler : clientHandlerList) {
            String clientName = clientHandler.getUsername();
            if (clientName.equals(username)) {
                clientHandler.sendMessage(message);
            }
        }
    }

    /**
     * creates the server socket, game objects and multiple client handlers based on incoming connection requests
     *
     */
    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            print("Server is running:\n" + serverSocket);
            ConnectionUtil.displayInfo();
            while (!serverSocket.isClosed()) {
                Socket clientSocket = serverSocket.accept();
                print("client connected: " + clientSocket.getLocalAddress());
                ClientHandler clientHandler = new ClientHandler(clientSocket, this);
                clientHandlerList.add(clientHandler);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            print("Server error on port " + port);
        }
    }

    /**
     * send message to all connected clients
     *
     * @param message message to be broadcast
     */
    public static void broadcast(Message message) {
        for (ClientHandler clientHandler : clientHandlerList) {
            clientHandler.sendMessage(message);
        }
    }

    /**
     * remove a client handler from the list
     *
     * @param clientHandlerName is the name of the client handler to be removed
     */
    public void removeClientHandler(String clientHandlerName) {
        if (!clientHandlerList.isEmpty()) {
            clientHandlerList.removeIf(ch -> ch.getUsername().equals(clientHandlerName));
        }
    }

    /**
     * starts the server on a predefined port, if no port is provided in the args, the default port is used
     *
     */
    public static void main(String[] args) {
        port = ConnectionUtil.defaultPort;
        if (args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                print("Invalid port, using default port");
            }
        }
        new Thread(new ServerApp()).start();
    }

    /**
     * method that is called when a message is received
     *
     * @param receivedMessage is the message received
     */
    public void onMessageReceived(Message receivedMessage) {
        if (receivedMessage.getMessageType() == MessageType.DEAD_CLIENT){
            String usr = receivedMessage.getUsername();
            removeClientHandler(usr);
            ErrorMessage message = new ErrorMessage("server", "client " + usr + " disconnected");
            broadcast(message);
        }
        this.gameController.onMessageReceived(receivedMessage);
    }

    /**
     * print utility method
     *
     * @param s is the string to be printed
     */
    public static void print(String s) {
        System.out.println(s);
    }

}