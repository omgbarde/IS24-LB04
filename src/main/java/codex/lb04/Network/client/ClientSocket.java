package codex.lb04.Network.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * this class represents a client connection
 */
public class ClientSocket {
    private final Socket socket;
    private final DataOutputStream output;
    private final DataInputStream input;

    /**
     * generates a client socket with the parameters in input
     * @param address is the port address
     * @param port is the desired port
     */
    public ClientSocket(String address, int port) {
        try {
            this.socket = new Socket(address, port);
            this.output = new DataOutputStream(socket.getOutputStream());
            this.input = new DataInputStream(socket.getInputStream());
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
    public void sendMessage(String message) {
        try {
            output.writeUTF(message);
            output.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * starts a new thread to listen for incoming messages
     */
    public void readMessage() {
        (new Thread(){
            public void run(){
                try{
                    String message = input.readUTF();
                    //andranno inviati all'interfaccia utente
                }catch(IOException e){
                    disconnect();
                }
            }
        }).start();
    }
}
