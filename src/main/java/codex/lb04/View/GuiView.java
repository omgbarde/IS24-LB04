package codex.lb04.View;

import codex.lb04.CodexClientApp;
import codex.lb04.Controller.SceneController.HelloController;
import codex.lb04.Controller.SceneController.LobbyController;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

/**
 * class that represents the GUI view
 */
public class GuiView extends View {
    private static Stage stageReference;
    private HelloController helloController;
    private LobbyController lobbyController;

    public GuiView(Stage stage ) {
        stageReference = stage;
        this.helloController =  new HelloController();
        this.lobbyController = new LobbyController();
    }

    /**
     * this method switches the scene to the one specified by the name
     *
     * @param sceneName is the name of the scene to load
     */
    @Override
    public void switchScene(String sceneName) {
        //in guiview we need to add the .fxml extension to the scene name
        Platform.runLater(() -> {
            try {
                loadScene(sceneName + ".fxml");
            } catch (IOException e) {
                System.out.println("Error loading the" + sceneName + "scene");
            }
        });
    }

    /**
     * this method loads the scene from the fxml file
     *
     * @param fxml is the fxml file to load
     * @throws IOException when an error occurs in loading the fxml
     */
    public static void loadScene(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CodexClientApp.class.getResource(fxml));
        Scene scene = new Scene(fxmlLoader.load(), 1520, 850);
        stageReference.setScene(scene);
    }

    @Override
    public void setTitle(String title) {
        Platform.runLater(() -> stageReference.setTitle(title));

    }

    @Override
    public void setMode(String mode) {
        if (mode.equals("fullscreen")) {
            Platform.runLater(() -> stageReference.setFullScreen(true));
        }
    }

    @Override
    public void updateList(ArrayList<String> names) {
        Platform.runLater(()->lobbyController.updateList(names));
    }
}
