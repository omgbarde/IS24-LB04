package codex.lb04.Network.server;

import codex.lb04.ServerApp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * client handler class handles client-server comunication
 */
public class ClientHandler implements Runnable{
    private Socket clientSocket;
    private ServerApp server;
    private DataOutputStream output;
    private DataInputStream input;
    private String username;

    /**
     * the client handler constructor creates a handler for a single client
     * @param socket is the socket of the client
     * @param server is the serverApp instance that the client is connected to
     */
     /* @param game is the game instance that the player wants to join */
    public ClientHandler(Socket socket, ServerApp server) {
        this.clientSocket = socket;
        this.server = server;
        try {
            input = new DataInputStream(clientSocket.getInputStream());
            output = new DataOutputStream(clientSocket.getOutputStream());
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * run method for the clientHandler thread:
     * it reads messages form the input stream and handles them
     */
    @Override
    public void run() {
        //send message back to client
        try {
            while (clientSocket.isConnected()) {
                String message = input.readUTF();
                if (message != null) {
                    this.handleInput(message);
                }
            }
        }catch (IOException ex){
            ex.printStackTrace();
        }
        finally {
            try {
                clientSocket.close();
                this.server.removeClientHandler(this);
            }catch (IOException ex){
                ex.printStackTrace();
            }
        }
    }

    /**
     * this method parses messages from the client and invokes methods based on the type and parameters of those messages
     * */
    public void handleInput(String input) {
        //se è un messaggio di login aggiorno username per ora faccio cosi ma va sistemato
        if(getUsername()==null){
            setUsername(input);
        }
        ServerApp.print("nuovo messaggio da " + getUsername() + ": " + input);
        //server.broadcast(input);
    }

    /**
     * this method reads messages from the server and sends them to the client
     * @param message is the message passed from the server
     */
    public void sendMessage(String message) {
        try {
            output.writeUTF(message);
            output.flush();
        } catch (IOException ex) {ex.printStackTrace();}
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}