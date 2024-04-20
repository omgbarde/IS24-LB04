package codex.lb04;

import codex.lb04.Network.server.ClientHandler;
import codex.lb04.Utils.ConnectionUtil;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerApp implements Runnable {
    //default port
    private static int port = ConnectionUtil.port;
    private ServerSocket serverSocket;
    //list of all client handlers
    private List<ClientHandler> clientHandlerList = new ArrayList<ClientHandler>();

    /**
     * creates the server socket and multiple client handlers based on incoming connection requests
     * (and will generate the game instance)
     */
    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server is running:\n" + serverSocket);

            while(!serverSocket.isClosed()) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("player connected: " + clientSocket.getLocalAddress());
                ClientHandler clientHandler = new ClientHandler(clientSocket,this);
                clientHandlerList.add(clientHandler);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * send message to all connected clients
     * @param message
     */
    public void broadcast(String message) {
        for (ClientHandler clientHandler : this.clientHandlerList) {
            clientHandler.sendMessage(message);
        }
    }

    /**
     * remove a client handler from the list
     * @param clientHandler
     */
    public void removeClientHandler(ClientHandler clientHandler){
        clientHandlerList.remove(clientHandler);
        broadcast(clientHandler.getUsername() + "disconnected");

    }

    public static void main(String[] args) {
        try {
            port = Integer.parseInt(args[0]);
        }
        catch (Exception e){
            System.out.println("port error or not specified, default port is used");
        }
        new Thread(new ServerApp()).run();
    }
    public static void print(String s){
        System.out.println(s);
    }
}
