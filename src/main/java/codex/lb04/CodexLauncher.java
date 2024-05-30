package codex.lb04;

import codex.lb04.Network.server.Server;
import codex.lb04.View.Cli.CliView;
import codex.lb04.View.Gui.GuiLauncher;
import javafx.application.Application;

/**
 * class for the client app launcher
 */
public class CodexLauncher {
    /**
     * starts the client application in the desired mode
     *
     * @param args are used to choose port and mode (CLI/GUI)
     */
    public static void main(String[] args) {
        if (args.length > 0 && args[0].equals("--cli")) {
            //launches CLI
            new CliView().run();
        } else if (args.length > 0 && args[0].equals("--gui")) {
            //launches GUI
            Application.launch(GuiLauncher.class);
        } else if (args.length > 0 && args[0].equals("--ser")) {
            //launches Server
            new Server().run();
        } else {
            //launches GUI by default
            System.out.println("no arguments provided, launching gui");
            Application.launch(GuiLauncher.class);
        }
    }

    /**
     * this method prints a message to the console
     *
     * @param message is the message to be printed
     */
    public static void print(String message) {
        System.out.println(message);
    }
}