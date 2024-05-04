package codex.lb04;

import codex.lb04.Message.Message;
import codex.lb04.Network.client.ClientSocket;
import codex.lb04.Utils.GraphicUtil;
import codex.lb04.View.View;
import javafx.application.Application;

import java.io.IOException;

/**
 * class for the client app launcher
 */
public class CodexClientApp {
    private static ClientSocket clientSocket;

    /**
     * starts the client application in the desired mode
     *
     * @param args are used to choose port and mode (CLI/GUI)
     *             //TODO: formalize command to launch the app
     */
    public static void main(String[] args) {
        //launches the app in the desired mode it now uses the GraphicUtil class to get the mode
        //TODO: add mode selection via args
        if (GraphicUtil.mode.equals("GUI")) {
            Application.launch(GuiApp.class);
        } else {
            //defaults to CLI
        }
    }

    public static View getView() {
        if (GraphicUtil.mode.equals("GUI")) {
            return GuiApp.getGuiView();
        } else {
            return null;
        }
    }

    public static void print(String message) {
        System.out.println(message);
    }


    public static void setClientSocket(String usr, String addr, int port) {
        try {
            clientSocket = new ClientSocket(usr, addr, port);
        }catch (IOException e){
            System.out.println("Connection refused");
        }
    }

    public static void disconnect() {
        clientSocket.disconnect();
    }

    public static boolean isConnected() {
        return clientSocket != null;
    }

    public static void sendMessageToServer(Message message) {
        try {
            clientSocket.sendMessage(message);
        }
        catch (NullPointerException e){
            System.out.println("No connection available");
        }
    }

}