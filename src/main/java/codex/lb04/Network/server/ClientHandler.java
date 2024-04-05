package codex.lb04.Network.server;

import codex.lb04.Message.LoginReply;
import codex.lb04.Message.Message;
import codex.lb04.Message.MessageType;
import codex.lb04.ServerApp;

import java.io.*;
import java.net.Socket;

/**
 * client handler class handles client-server comunication
 */
public class ClientHandler implements Runnable{
    private Socket clientSocket;
    private ServerApp server;
    private ObjectOutputStream output;
    private ObjectInputStream input;
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
            input = new ObjectInputStream(clientSocket.getInputStream());
            output = new ObjectOutputStream(clientSocket.getOutputStream());
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
                Message message = (Message) input.readObject();
                if (message != null) {
                    this.handleInput(message);
                }
            }
        }
        catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        finally {
            try {
                clientSocket.close();
                this.server.removeClientHandler(this);
            }
            catch (IOException ex){
                ex.printStackTrace();
            }
        }
    }

    /**
     * this method parses messages from the client and invokes methods based on the type and parameters of those messages
     * */
    public void handleInput(Message input) {
        if(input.getMessageType().equals(MessageType.LOGIN_REQUEST)&& getUsername()==null){
            setUsername(input.getNickname());
            server.print("new user wants to play: " + getUsername());
            LoginReply reply = new LoginReply(getUsername(),true);
            server.broadcast(reply);
        }
        else{
            ServerApp.print("message not recognized");
        }
    }

    /**
     * this method reads messages from the server and sends them to the client
     * @param message is the message passed from the server
     */
    public void sendMessage(Message message) {
        try {
            output.writeObject(message);
            output.flush();
        }
        catch (IOException e) {
            e.printStackTrace();}
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
