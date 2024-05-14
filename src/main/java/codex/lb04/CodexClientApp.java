package codex.lb04;
import codex.lb04.Utils.GraphicUtil;
import codex.lb04.View.Cli.CliView;
import codex.lb04.View.View;
import javafx.application.Application;
/**
 * class for the client app launcher
 */
public class CodexClientApp {
    /**
     * starts the client application in the desired mode
     *
     * @param args are used to choose port and mode (CLI/GUI)
     *
     */
    public static void main(String[] args) {
        if (args.length > 0 && args[0].equals("-cli")) {
            //launches CLI
            new CliView().run();
        }
        else if (args.length > 0 && args[0].equals("-gui")) {
            //launches GUI
            Application.launch(GuiApp.class);
        }
        else {
            //launches GUI by default
            System.out.println("error parsing the launch command");
            Application.launch(GuiApp.class);
        }
    }
    /**
     * this method returns the view associated with the clientApp
     * @return the view of the client
     */
    public static View getView() {
        if (GraphicUtil.mode.equals("GUI")) {
            return GuiApp.getGuiView();
        } else {
            return null;
        }
    }
    /**
     * this method prints a message to the console
     * @param message is the message to be printed
     */
    public static void print(String message) {
        System.out.println(message);
    }
}