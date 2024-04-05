package codex.lb04;

import codex.lb04.Message.Message;
import codex.lb04.Network.client.ClientSocket;
import codex.lb04.Utils.ConnectionUtil;
import codex.lb04.Utils.GraphicUtil;
import codex.lb04.View.GuiApp;
import javafx.application.Application;

/**
 * class for the client app launcher
 */
public class CodexClientApp{
    private static ClientSocket clientSocket;

    /**
     * starts the client application in the desired mode
     * @param args are used to choose port and mode (CLI/GUI)
     */
    public static void main(String[] args) {
        //if the port number is passed as the first argument it's set
        try {
            int portNumber = Integer.parseInt(args[0]);
            ConnectionUtil.setPortFromCmdLine(portNumber);
        }catch (Exception e){
            System.out.println("the default port is used: " + ConnectionUtil.port);
        }
        System.out.println("attempt connection...");
        clientSocket = new ClientSocket(ConnectionUtil.host,ConnectionUtil.port);
        System.out.println("connected to server");

        if(GraphicUtil.mode.equals("GUI")) {
            Application.launch(GuiApp.class);
        }
        else {
            //defaults to CLI
        }
        //clientSocket.readMessage();
    }

    public static ClientSocket getClientSocket() {
        return clientSocket;
    }

    public static void print(String string) {
        System.out.println(string);
    }
}