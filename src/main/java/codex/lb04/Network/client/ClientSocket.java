package codex.lb04.Network.client;

import codex.lb04.CodexClientApp;
import codex.lb04.Message.ErrorMessage;
import codex.lb04.Message.Message;
import codex.lb04.View.ViewController;

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
    private ClientParser clientParser;

    /**
     * generates a client socket with the parameters in input
     *
     * @param address is the port address
     * @param port    is the desired port
     */
    public ClientSocket(String username, String address, int port, ViewController controller) throws IOException {
            this.username = username;
            this.socket = new Socket(address, port);
            this.output = new ObjectOutputStream(socket.getOutputStream());
            this.input = new ObjectInputStream(socket.getInputStream());
            this.clientParser = new ClientParser( this,controller);
            readmessage();
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
            output.reset();
            output.flush();
        } catch (IOException e) {
            disconnect();
        }
    }

    /**
     * starts a new thread to listen for incoming messages
     */
    public void readmessage() {
        (new Thread(() -> {
            while (!socket.isClosed()) {
                try {
                    Message message = (Message) input.readObject();
                    //CodexClientApp.print(message.toString());
                    clientParser.handleInput(message);
                } catch (SocketException | EOFException e) {
                    CodexClientApp.print("server disconnected");
                    clientParser.handleInput(new ErrorMessage("server", "server disconnected"));
                    disconnect();
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                    disconnect();
                }
            }
        })).start();
    }

}
