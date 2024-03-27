package codex.lb04;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientApp.class.getResource("Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1900, 1000);
        //stage.getIcons().add(new Image(ClientApp.class.getResourceAsStream("../resources/graphics/codex-naturalis-espt.jpg")));
        stage.setTitle("Codex! - Login");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}