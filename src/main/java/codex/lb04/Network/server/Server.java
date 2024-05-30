package codex.lb04.Network.server;

import codex.lb04.Controller.GameController;
import codex.lb04.Message.Message;
import codex.lb04.Utils.ConnectionUtil;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * ServerApp class that represents the server side of the game.
 */
public class Server implements Runnable {
    //default port
    private int port = ConnectionUtil.defaultPort;
    //list of all client handlers, use copy on write array list to avoid concurrent modification exceptions
    private static final CopyOnWriteArrayList<ClientHandler> clientHandlerList = new CopyOnWriteArrayList<>();
    private final GameController gameController = GameController.getInstance();

    /**
     * sends a message to a specific client
     *
     * @param message  is the message to be sent
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
     */
    @Override
    public void run() {
        System.out.println("select desired port or press enter to use default (49152)");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        try {
            int desiredPort = Integer.parseInt(input);
            if (ConnectionUtil.isValidPort(desiredPort)) {
                port = desiredPort;
            } else System.out.println("not a free port, using default");
        } catch (NumberFormatException e) {
            System.out.println("using default port");
        }

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
     * @param clientHandler is the clientHandler to be removed
     */
    public void removeClientHandler(ClientHandler clientHandler) {
        if (!clientHandlerList.isEmpty()) {
            clientHandlerList.remove(clientHandler);
        }
    }

    /**
     * method that is called when a message is received
     *
     * @param receivedMessage is the message received
     */
    public void onMessageReceived(Message receivedMessage) {
        /*if (receivedMessage.getMessageType() == MessageType.DEAD_CLIENT) {
            String usr = receivedMessage.getUsername();
            ErrorMessage message = new ErrorMessage("server", "client " + usr + " disconnected");
            broadcast(message);
        }*/
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
     * method to disconnect all clients
     */
    public void disconnectAllClients() {
        for (ClientHandler clientHandler : clientHandlerList) {
            clientHandler.closeClientHandler();
        }
        gameController.resetInstance();
    }
}