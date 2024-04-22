package codex.lb04.Network.client;

import codex.lb04.Message.Message;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * This class represents a socket client implementation.
 * NON IN USO AL MOMENTO SOLO PER REFERENCE
 */
public class ClientSocketReference extends AbstractClient {

    private final Socket socket;

    private final DataOutputStream outputStm;
    private final DataInputStream inputStm;
    //private final ExecutorService readExecutionQueue;
    //private final ScheduledExecutorService pinger;

    private static final int SOCKET_TIMEOUT = 10000;

    public ClientSocketReference(String address, int port) throws IOException {
        this.socket = new Socket();
        this.socket.connect(new InetSocketAddress(address, port), SOCKET_TIMEOUT);
        this.outputStm = new DataOutputStream(socket.getOutputStream());
        this.inputStm = new DataInputStream(socket.getInputStream());
        System.out.println("setup complete");

        //this.readExecutionQueue = Executors.newSingleThreadExecutor();
        //this.pinger = Executors.newSingleThreadScheduledExecutor();

    }

    /**
     * Asynchronously reads a message from the server via socket and notifies the ClientController.
     */
    @Override
    public void readMessage() {
        /*readExecutionQueue.execute(() -> {

            while (!readExecutionQueue.isShutdown()) {
                Message message;
                try {
                    message = (Message) inputStm.readObject();
                    Client.LOGGER.info("Received: " + message);
                } catch (IOException | ClassNotFoundException e) {
                    message = new ErrorMessage(null, "Connection lost with the server.");
                    disconnect();
                    //readExecutionQueue.shutdownNow();
                }
                //notifyObserver(message);
            }
        });*/
    }

    /**
     * Sends a message to the server via socket.
     *
     * @param message the message to be sent.
     */
    @Override
    public void sendMessage(Message message) {
        try {
            //andrà serializzato
            outputStm.writeUTF(message.toString());
            outputStm.flush();
        } catch (IOException e) {
            disconnect();
            //notifyObserver(new ErrorMessage(null, "Could not send message."));
        }
    }

    /**
     * Disconnect the socket from the server.
     */
    @Override
    public void disconnect() {
        try {
            if (!socket.isClosed()) {
                //readExecutionQueue.shutdownNow();
                //enablePinger(false);
                socket.close();
            }
        } catch (IOException e) {
            //notifyObserver(new ErrorMessage(null, "Could not disconnect."));
        }
    }

    /*/**
     * Enable a heartbeat (ping messages) between client and server sockets to keep the connection alive.
     *
     * @param enabled set this argument to {@code true} to enable the heartbeat.
     *                set to {@code false} to kill the heartbeat.


    public void enablePinger(boolean enabled) {
        if (enabled) {
            //pinger.scheduleAtFixedRate(() -> sendMessage(new PingMessage()), 0, 1000, TimeUnit.MILLISECONDS);
        } else {
            pinger.shutdownNow();
        }
    }*/
}
