package codex.lb04.Network.server;

import codex.lb04.Message.Message;
import codex.lb04.Message.MessageType;
import codex.lb04.Message.PingMessage;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * client handler class handles client-server communication
 */
public class ClientHandler implements Runnable {
    private final Server server;
    private final Socket clientSocket;
    private final ScheduledExecutorService pinger;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private String username;
    private long pingSentTime;
    private long pongReceivedTime;

    /**
     * the client handler constructor creates a handler for a single client
     *
     * @param socket is the socket of the client
     * @param server is the serverApp instance that the client is connected to
     */
    public ClientHandler(Socket socket, Server server) {
        this.clientSocket = socket;
        this.server = server;

        this.pongReceivedTime = System.currentTimeMillis();
        this.pinger = Executors.newSingleThreadScheduledExecutor();
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
            startPinger();
            while (clientSocket.isConnected()) {
                Message message = (Message) input.readObject();
                if (message != null) {
                    //just check if the message is a login request and set the username
                    if ((message.getMessageType() == MessageType.LOGIN_REQUEST || message.getMessageType() == MessageType.CREATE_GAME) && this.username == null) {
                        this.username = message.getUsername();
                    } else if (message.getMessageType() == MessageType.DEAD_CLIENT) {
                        closeClientHandler();
                    } else if (message.getMessageType() == MessageType.PONG) {
                        pongReceivedTime = System.currentTimeMillis();
                        //don't forward the pong message to the server
                    }
                    //forward the message to the server
                    server.onMessageReceived(message);
                }
            }
        } catch (SocketException | EOFException e) {
            System.out.println("client disconnected: " + getUsername());
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("error reading message from client");
        } finally {
            closeClientHandler();
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
            output.reset();
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

    /**
     * starts a new executor to ping the client
     * it sends a ping message to the client every 5 seconds and checks if the client has responded in the last 10 seconds
     *
     * @see PingMessage
     */
    private void startPinger() {
        pinger.scheduleAtFixedRate(() -> {
            pingSentTime = System.currentTimeMillis();
            String s = "pinged at" + pingSentTime;
            sendMessage(new PingMessage(s));
            //check for elapsed time
            if (System.currentTimeMillis() - pongReceivedTime > 10000) {
                closeClientHandler();
            }
        }, 0, 5, TimeUnit.SECONDS);
    }

    /**
     * closes the client handler and removes it from the server it also signals to disconnect all clients
     *
     * @see codex.lb04.Network.client.ClientSocket#disconnect()
     */
    void closeClientHandler() {
        pinger.shutdown();
        server.removeClientHandler(this);
        server.disconnectAllClients();
        try {
            clientSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
