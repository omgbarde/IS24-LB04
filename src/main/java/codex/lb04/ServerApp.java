
package codex.lb04;

import codex.lb04.Controller.GameController.GameController;
import codex.lb04.Message.GenericMessage;
import codex.lb04.Message.Message;
import codex.lb04.Message.MessageType;
import codex.lb04.Network.server.ClientHandler;
import codex.lb04.Utils.ConnectionUtil;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

/**
 * ServerApp class that represents the server side of the game.
 */
public class ServerApp implements Runnable {
    //default port
    private int port;
    private String address;
    private ServerSocket serverSocket;
    private int numPlayers;
    //list of all client handlers
    private static List<ClientHandler> clientHandlerList = new ArrayList<>();
    //game controller to manage the game
    private GameController gameController;

    /**
     * sends a message to a specific client
     *
     * @param message is the message to be sent
     * @param username is the username of the client
     */
    public static void sendMessage(Message message, String username) {
        for (ClientHandler clientHandler : clientHandlerList) {
            if (clientHandler.getUsername().equals(username)) {
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
            //TODO: dice sempre already bound e non mi fa scegliere l'indirizzo
            serverSocket = new ServerSocket(ConnectionUtil.defaultPort);
            serverSocket.setReuseAddress(true);
            try {
                serverSocket.bind(new java.net.InetSocketAddress(address, port));

            }
            catch (SocketException e){
                print("Port already in use, using default port");
            }
            print("Server is running:\n" + serverSocket);
            gameController = GameController.getInstance().setNumPlayers(numPlayers);
            while (!serverSocket.isClosed()) {
                Socket clientSocket = serverSocket.accept();
                print("client connected: " + clientSocket.getLocalAddress());
                ClientHandler clientHandler = new ClientHandler(clientSocket, this);
                clientHandlerList.add(clientHandler);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * send message to all connected clients
     *
     * @param message message to be broadcasted
     */
    public static void broadcast(Message message) {
        for (ClientHandler clientHandler : clientHandlerList) {
            clientHandler.sendMessage(message);
        }
    }

    /**
     * remove a client handler from the list
     *
     * @param clientHandler is the client handler to be removed
     */
    public void removeClientHandler(ClientHandler clientHandler) {
        clientHandlerList.remove(clientHandler);
        GenericMessage genericMessage = new GenericMessage("server", "client" + clientHandler.getUsername() + "disconnected");
        broadcast(genericMessage);
    }

    /**
     * remove a client handler from the list
     *
     * @param clientHandlerName is the name of the client handler to be removed
     */
    public void removeClientHandler(String clientHandlerName) {
        clientHandlerList.removeIf(ch -> ch.getUsername().equals(clientHandlerName));
        GenericMessage genericMessage = new GenericMessage("server", "client" + clientHandlerName + "disconnected");
        broadcast(genericMessage);
    }

    /**
     * starts the server on a predefined port, if no port is provided in the args, the default port is used
     *
     * @param address is the address of the server
     * @param port is the port of the server
     * @param numPlayers is the number of players
     */
    public ServerApp(String address, int port, int numPlayers) {
        if (checkValid(port, numPlayers)){
            this.address = address;
            this.port = port;
            this.numPlayers = numPlayers;
            new Thread(this).start();
        } else {
            print("not valid port or number of players");
        }
    }

    private boolean checkValid(int port, int numPlayers) {
        return (ConnectionUtil.isValidPort(port) &&  numPlayers >= 2 && numPlayers <= 4);
    }

    public void onMessageReceived(Message receivedMessage) {
        if (receivedMessage.getMessageType() == MessageType.DEAD_CLIENT)
            removeClientHandler(receivedMessage.getUsername());
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

    /**
     * get the number of connected clients
     * @return the number of connected clients
     */
    public static int getNumClient() {
        return clientHandlerList.size();
    }

}