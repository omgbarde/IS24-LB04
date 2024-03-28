package codex.lb04;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientApp extends Application {
    /**
     * this method starts the GUI and changes scenes based on the input
     * @param stage is the stage (window) reference
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientApp.class.getResource("Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1520, 850);
        //stage.getIcons().add(new Image(ClientApp.class.getResourceAsStream("../resources/graphics/codex-naturalis-espt.jpg")));
        stage.setTitle("Codex! - Login");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * starts the client application in the desired mode and connects to the server
     * @param args will be for default port and mode (CLI/GUI)
     */
    public static void main(String[] args) {
        launch();
    }
}