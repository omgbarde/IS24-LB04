package codex.lb04.Network.server;

import codex.lb04.Message.DeadClientMessage;
import codex.lb04.Message.Message;
import codex.lb04.Message.MessageType;
import codex.lb04.ServerApp;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

/**
 * client handler class handles client-server comunication
 */
public class ClientHandler implements Runnable {
    //TODO avoid this upward dependency if possible
    private final ServerApp server;
    private final Socket clientSocket;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private String username;

    /**
     * the client handler constructor creates a handler for a single client
     *
     * @param socket is the socket of the client
     * //@param server is the serverApp instance that the client is connected to
     */
    /* @param game is the game instance that the player wants to join */
    public ClientHandler(Socket socket, ServerApp server) {
        this.clientSocket = socket;
        this.server = server;
        try {
            input = new ObjectInputStream(clientSocket.getInputStream());
            output = new ObjectOutputStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * run method for the clientHandler thread:
     * it reads messages form the input stream and forwards them to the server that will handle them
     */
    @Override
    public void run() {
        try {
            while (clientSocket.isConnected()) {
                Message message = (Message) input.readObject();
                if (message != null) {
                    //just check if the message is a login request and set the username
                    if ((message.getMessageType() == MessageType.LOGIN_REQUEST|| message.getMessageType() == MessageType.CREATE_GAME) && this.username == null) {
                        this.username = message.getUsername();
                    }
                    //forward the message to the server
                    server.onMessageReceived(message);
                }
            }
        } catch (SocketException | EOFException e) {
            System.out.println("client disconnected: " + getUsername());

        } catch (IOException | ClassNotFoundException e) {
            //TODO separare i catch
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
                server.onMessageReceived(new DeadClientMessage(this.username));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * this method reads messages from the server and sends them to the client
     *
     * @param message is the message passed from the server
     */
    public synchronized void sendMessage(Message message) {
        try {
            output.writeObject(message);
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * method to get the username associated with the clientHandler
     *
     * @return the username associated with the clientHandler
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * method to set the username associated with the clientHandler
     *
     * @param username is the username to be associated with the clientHandler
     */
    public void setUsername(String username) {
        this.username = username;
    }
}
