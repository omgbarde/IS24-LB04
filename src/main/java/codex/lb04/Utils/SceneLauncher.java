package codex.lb04.Utils;

import codex.lb04.View.GuiView;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.util.Scanner;

public class SceneLauncher extends Application{
    private GuiView guiView;


    @Override
    public void start(Stage primaryStage) {
        this.guiView = new GuiView(primaryStage);
        String arg = getParameters().getRaw().get(0);
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
            default:
                System.out.println("cheffai");
                break;
        }
        //this.guiView.getStageReference().getScene().getStylesheets().add("src/main/resources/codexTheme.css");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        launch(String.valueOf(scanner.nextInt()));
    }


    // Add more methods to launch other scenes as needed
}