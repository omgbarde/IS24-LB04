package codex.lb04;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class ServerApp {
    /**
     * creates the server socket, the game instance and multiple client handlers based on incoming TCP requests
     * @param args is the deafult port, still need to implement!
     */
    public static void main(String[] args) {
        //default port
        int port = 4444;

        try {
            port = Integer.parseInt(args[0]);
        }
        catch (Exception e){
            System.out.println("port error or not specified, default port is used");
        }

        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server is running:\n" + serverSocket);
            //Game game = new Game();
            while(true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("player connected: " + clientSocket.getLocalAddress());
                new Thread(new ClientHandler(clientSocket/*, game*/)).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
