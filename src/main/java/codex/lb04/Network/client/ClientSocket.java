package codex.lb04.Network.client;

import codex.lb04.CodexClientApp;
import codex.lb04.Message.Message;

import java.io.*;
import java.net.Socket;

/**
 * this class represents a client connection
 */
public class ClientSocket {
    private final Socket socket;
    private final ObjectOutputStream output;
    private final ObjectInputStream input;

    /**
     * generates a client socket with the parameters in input
     * @param address is the port address
     * @param port is the desired port
     */
    public ClientSocket(String address, int port) {
        try {
            this.socket = new Socket(address, port);
            this.output = new ObjectOutputStream(socket.getOutputStream());
            this.input = new ObjectInputStream(socket.getInputStream());
            readMessage();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
        (new Thread(){
            public void run(){
                try{
                    Message message = (Message) input.readObject();
                    CodexClientApp.print(message.toString());
                    //andranno inviati all'interfaccia utente
                }catch(IOException | ClassNotFoundException e){
                    e.printStackTrace();
                    disconnect();
                }
            }
        }).start();
    }
}
