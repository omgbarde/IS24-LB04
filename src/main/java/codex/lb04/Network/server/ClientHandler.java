package codex.lb04.Network.server;

import codex.lb04.Message.*;
import codex.lb04.ServerApp;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

/**
 * client handler class handles client-server comunication
 */
public class ClientHandler implements Runnable{
    //TODO avoid this upward dependency
    private ServerApp server;
    private Socket clientSocket;
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
        } catch (SocketException e){
               System.out.println("client disconnected: " + getUsername());

        } catch (IOException | ClassNotFoundException e){
            //TODO separare i catch
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
                //TODO move this in ServerApp
                this.server.removeClientHandler(this);
            }
            catch (IOException ex){
                ex.printStackTrace();
            }
        }
    }

    /**
     * this method parses messages from the client and invokes methods based on the type and parameters of those messages
     * @param input is the message passed from the client
     * */
    public void handleInput(Message input) {
        //TODO implementare metodo parse

        if(input.getMessageType().equals(MessageType.LOGIN_REQUEST)&& getUsername()==null){
            setUsername(input.getUsername());
            server.print("new user wants to play: " + getUsername());
            //mando al game, risponde lui se accettato

            //risponderà solo con fconferma ricezione
            sendMessage(new OkMessage());
        }
        else{
            server.print("message not recognized");
            ErrorMessage error = new ErrorMessage("server", "message not recognized or double login");
            sendMessage(error);
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

    /**
     * method to get the username associated with the clientHandler
     * @return the username associated with the clientHandler
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * method to set the username associated with the clientHandler
     * @param username is the username to be associated with the clientHandler
     */
    public void setUsername(String username) {
        this.username = username;
    }
}
