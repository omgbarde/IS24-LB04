
package codex.lb04;

import codex.lb04.Message.ErrorMessage;
import codex.lb04.Message.Message;
import codex.lb04.Network.server.ClientHandler;
import codex.lb04.Utils.ConnectionUtil;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerApp implements Runnable {
    //default port
    private static int port = ConnectionUtil.defaultPort;
    private ServerSocket serverSocket;
    //list of all client handlers
    private List<ClientHandler> clientHandlerList = new ArrayList<>();

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
                System.out.println("client connected: " + clientSocket.getLocalAddress());
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
     * @param message message to be broadcasted
     */
    public void broadcast(Message message) {
        for (ClientHandler clientHandler : this.clientHandlerList) {
            clientHandler.sendMessage(message);
        }
    }

    /**
     * remove a client handler from the list
     * @param clientHandler is the client handler to be removed
     */
    public void removeClientHandler(ClientHandler clientHandler){
        clientHandlerList.remove(clientHandler);
        ErrorMessage errorMessage = new ErrorMessage("server", "client"+ clientHandler.getUsername() + "disconnected");
        broadcast(errorMessage);
    }

    public static void main(String[] args) {
        try {
            port = Integer.parseInt(args[0]);
        }
        catch (NumberFormatException|IndexOutOfBoundsException e){
            System.out.println("port reding error, default port is used");
        }
        if(ConnectionUtil.isValidPort(port)){
            System.out.println("using port " + port);
        }
        else {
            System.out.println("invalid port, default port is used");
        }

        new Thread(new ServerApp()).start();
    }
    public static void print(String s){
        System.out.println(s);
    }
}