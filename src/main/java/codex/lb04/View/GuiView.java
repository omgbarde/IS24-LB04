package codex.lb04.View;

import codex.lb04.CodexClientApp;
import codex.lb04.Observer.Observable;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * class that represents the GUI view
 */
public class GuiView extends Observable implements View{
    private static Stage stageReference;
    public GuiView(Stage stage) {
        stageReference = stage;
    }

    /**
     * this method switches the scene to the one specified in the fxml file
     *
     * @param fxml is the name of the fxml file to load
     */
    @Override
    public void switchScene(String fxml) {
        Platform.runLater(() -> {
            try {
                    loadScene(fxml);
            } catch (IOException e) {
                System.out.println("Error loading the" + fxml + "scene");
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
    public static void setTitle(String s) {
        Platform.runLater(() -> stageReference.setTitle(s));

    }
}
