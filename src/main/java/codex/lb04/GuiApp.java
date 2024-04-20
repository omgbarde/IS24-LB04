package codex.lb04;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class GuiApp extends Application {
    private static Stage stageReference;

    /**
     * this method starts the GUI
     * @param stage is the stage (window) reference
     * @throws IOException when an error occurs in loading the fxml
     */
    @Override
    public void start(Stage stage) throws IOException {
        stageReference = stage;
        loadScene("Hello.fxml");
        try {
            stage.getIcons().add(new Image(new FileInputStream("src/main/resources/graphics/codex-naturalis-espt.jpg")));
        }catch (FileNotFoundException e){
            System.out.println("window icon not found");
        }
        stage.setTitle("Codex! - Welcome");
        stage.setResizable(false);
        stage.show();
    }

    /**
     * this method stops the GUI
     */
    public void stop(){
        Platform.exit();
        System.exit(0);
    }
    /**
     * this method returns the stage reference
     * @return the stage reference
     */
    public static Stage getStage() {
        return stageReference;
    }

    /**
     * this method switches the scene to the one specified in the fxml file
     * @param fxml is the name of the fxml file to load
     */
    public static void switchScene(String fxml) {
        Platform.runLater(() -> {
            try {
                loadScene(fxml);
            } catch (IOException e) {
                System.out.println("Error loading the" + fxml +"scene");
            }
        });
    }

    /**
     * this method loads the scene from the fxml file
     * @param fxml is the fxml file to load
     * @throws IOException when an error occurs in loading the fxml
     */
    private static void loadScene(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CodexClientApp.class.getResource(fxml));
        Scene scene = new Scene(fxmlLoader.load(), 1520, 850);
        stageReference.setScene(scene);
    }
    public static void setTitle(String s) {
        Platform.runLater(()->stageReference.setTitle(s));

    }

    public static void setMode(String s) {
        if (s.equals("full")) {
            Platform.runLater(()->stageReference.setFullScreen(true));
        } else {
            Platform.runLater(()->stageReference.setFullScreen(true));
        }
    }
}
