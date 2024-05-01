package codex.lb04.Network.client;

import codex.lb04.CodexClientApp;
import codex.lb04.Message.Message;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

/**
 * this class represents a client connection
 */
public class ClientSocket {
    private String username;
    private Socket socket;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private ClientParser messageParser;

    /**
     * generates a client socket with the parameters in input
     *
     * @param address is the port address
     * @param port    is the desired port
     */
    public ClientSocket(String username, String address, int port) throws RuntimeException {
        try {
            this.username = username;
            this.socket = new Socket(address, port);
            this.output = new ObjectOutputStream(socket.getOutputStream());
            this.input = new ObjectInputStream(socket.getInputStream());
            this.messageParser = new ClientParser(this);
            readMessage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getUsername() {
        return username;
    }

    /**
     * method to close the socket
     */
    public void disconnect() {
        if (!socket.isClosed()) {
            try {
                socket.close();
                CodexClientApp.getView().switchScene("Hello");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * this method reads messages from the inteface and sends them to the server (invoked by the interface)
     *
     * @param message is the message passed from the server
     */
    public void sendMessage(Message message) {
        try {
            output.writeObject(message);
            output.flush();
        } catch (IOException e) {
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
                    messageParser.handleInput(message);
                } catch (SocketException | EOFException e) {
                    CodexClientApp.print("server disconnected");
                    disconnect();
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                    disconnect();
                }
            }
        })).start();
    }

}
