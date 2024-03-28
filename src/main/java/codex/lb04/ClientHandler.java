package codex.lb04;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable{
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    //private Game game;
    //private Player player;

    /**
     * the client handler constructor creates a handler for a single client
     * @param socket is the socket of the client
     */
     /* @param game is the game instance that the player wants to join */
    public ClientHandler(Socket socket/*, Game game*/) {
        this.clientSocket = socket;
        try {
            this.out = new PrintWriter(socket.getOutputStream());
            this.in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );
            /*this.game = game;
            if(this.game.isCirclePlayerTurn()) {
                this.player = this.game.getCirclePlayer();
            } else {
                this.player = this.game.getCrossPlayer();
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * run method for the clientHandler thread: it reads messages form the buffered reader and calls the input handler
     */
    @Override
    public void run() {
        while(true) {
            try {
                String input = in.readLine();
                if(input != null) {
                    System.out.println(input);
                    this.handleInput(input);
                }
            }catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * this method parses messages from the client and invokes methods based on the type and parameters of those messages
     * */
    public void handleInput(String input) {

    }
}
