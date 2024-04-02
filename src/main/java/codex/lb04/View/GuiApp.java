package codex.lb04.View;

import codex.lb04.CodexClientApp;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class GuiApp extends Application {
    /**
     * this method starts the GUI
     * @param stage is the stage (window) reference
     * @throws IOException when an error occurs in loading the fxml
     */
    @Override
    public void start(Stage stage) throws IOException {
        //loads the fxml structure and initiates the scene
        FXMLLoader fxmlLoader = new FXMLLoader(CodexClientApp.class.getResource("Hello.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1520, 850);
        try {
            stage.getIcons().add(new Image(new FileInputStream("src/main/resources/graphics/codex-naturalis-espt.jpg")));
        }catch (FileNotFoundException e){}
        stage.setTitle("Codex! - Welcome");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

}
