package codex.lb04.Network.client;

import codex.lb04.Controller.HelloController;
import codex.lb04.Message.LoginMessage;
import codex.lb04.Message.LoginReply;
import codex.lb04.Message.Message;
import codex.lb04.Message.MessageType;
import codex.lb04.ServerApp;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;

/**
 * this class represents a client connection
 */
public class ClientSocket {
    private String username;
    private final Socket socket;
    private final ObjectOutputStream output;
    private final ObjectInputStream input;

    /**
     * generates a client socket with the parameters in input
     * @param address is the port address
     * @param port is the desired port
     */
    public ClientSocket(String username, String address, int port) throws RuntimeException{
        try {
            this.username = username;
            this.socket = new Socket(address, port);
            this.output = new ObjectOutputStream(socket.getOutputStream());
            this.input = new ObjectInputStream(socket.getInputStream());
            readMessage();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getUsername() {
        return username;
    }

    /**
     * method to close the socket
     */
    public void disconnect(){
        if (!socket.isClosed()) {
            try {
                socket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * this method reads messages from the inteface and sends them to the server (invoked by the interface)
     * @param message is the message passed from the server
     */
    public void sendMessage(Message message) {
        try {
            output.writeObject(message);
            output.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * starts a new thread to listen for incoming messages
     */
    public void readMessage() {
        (new Thread(() -> {
            while (!socket.isClosed()) {
                try {
                    Message message = (Message) input.readObject();
                    //CodexClientApp.print(message.toString());
                    parseMessage(message);
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                    disconnect();
                }
            }
        })).start();
    }

    //TODO fare classe client parser
    /**
     * method to parse the message received from the server
     * @param message is the message passed from the server
     */
    private void parseMessage(Message message) {
        if(message.getMessageType().equals(MessageType.LOGIN_REPLY)){
            if(((LoginReply)message).isAccepted()){
                HelloController.switchToLobby();
            }
            else{
                ServerApp.print("login refused");
                disconnect();
            }
        }
        else if(message.getMessageType().equals(MessageType.ERROR)){
            ServerApp.print("error: " + message.toString());
            disconnect();
        }
        else if(message.getMessageType().equals(MessageType.OK_MESSAGE)){
            ServerApp.print("message received");
        }
        else{
            ServerApp.print("message not recognized");
            disconnect();
        }

    }

}
