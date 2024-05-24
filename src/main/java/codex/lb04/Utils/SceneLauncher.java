package codex.lb04.Utils;

import codex.lb04.View.Gui.GuiView;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.util.Scanner;

/**
 * This class is used as a test bench to launch the different scenes of the application.
 */
public class SceneLauncher extends Application{
    private GuiView guiView;

    @Override
    public void start(Stage primaryStage) {
        this.guiView = new GuiView(primaryStage);
        String arg = getParameters().getRaw().getFirst();
        switch(arg){
            case "1":
                Platform.runLater(()->guiView.drawHelloScene());
                break;
            case "2":
                Platform.runLater(()->guiView.drawLoginScene());
                break;
            case "3":
                Platform.runLater(()->guiView.drawCreateGameScene());
                break;
            case "4":
                Platform.runLater(()->guiView.drawLobbyScene());
                break;
            case "5":
                Platform.runLater(()->guiView.drawBoardScene());
                break;
            case "6":
                Platform.runLater(()->guiView.drawWinnerScene());
                break;
            default:
                System.out.println("what are you doing?");
                break;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        launch(String.valueOf(scanner.nextInt()));
    }

}