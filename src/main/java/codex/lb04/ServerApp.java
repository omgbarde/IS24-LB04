
package codex.lb04;

import codex.lb04.Message.ErrorMessage;
import codex.lb04.Message.GenericMessage;
import codex.lb04.Message.LogoutReply;
import codex.lb04.Message.Message;
import codex.lb04.Model.Game;
import codex.lb04.Network.server.ClientHandler;
import codex.lb04.Utils.ConnectionUtil;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ServerApp implements Runnable {
    //default port
    private static int port = ConnectionUtil.defaultPort;
    private ServerSocket serverSocket;
    //list of all client handlers
    private static List<ClientHandler> clientHandlerList = new ArrayList<>();
    private Game game;

    public static boolean checkUsername(String usr) {
        for(ClientHandler clientHandler : clientHandlerList){
            if (Objects.equals(clientHandler.getUsername(), usr)) return false;
        }
        return true;
    }

    /**
     * creates the server socket and multiple client handlers based on incoming connection requests
     * (and will generate the game instance)
     */
    @Override
    public void run() {
        try {

            serverSocket = new ServerSocket(port);
            System.out.println("Server is running:\n" + serverSocket);
            createGame();
            while (!serverSocket.isClosed()) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("client connected: " + clientSocket.getLocalAddress());
                ClientHandler clientHandler = new ClientHandler(clientSocket, this);
                if(clientHandlerList.size()<=4){
                    clientHandlerList.add(clientHandler);
                    game.addPlayerName(clientHandler.getUsername());
                }
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createGame() {
        this.game = Game.getInstance();
    }

    /**
     * send message to all connected clients
     *
     * @param message message to be broadcasted
     */
    public void broadcast(Message message) {
        for (ClientHandler clientHandler : this.clientHandlerList) {
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
        if(game != null) game.removePlayer(clientHandler.getUsername());
        GenericMessage genericMessage = new GenericMessage("server", "client" + clientHandler.getUsername() + "disconnected");
        broadcast(genericMessage);
    }

    public static void main(String[] args) {
        try {
            port = Integer.parseInt(args[0]);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            System.out.println("port reading error, default port is used");
        }
        if (ConnectionUtil.isValidPort(port)) {
            System.out.println("using port " + port);
        } else {
            System.out.println("invalid port, default port is used");
        }

        new Thread(new ServerApp()).start();
    }

    public static void print(String s) {
        System.out.println(s);
    }

    public static int getNumClient(){
        return clientHandlerList.size();
    }
}