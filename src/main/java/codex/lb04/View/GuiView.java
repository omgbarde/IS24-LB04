package codex.lb04.View;

import codex.lb04.CodexClientApp;
import codex.lb04.GuiApp;
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
    @Override
    public void switchToLobby(){
        //loads the fxml structure and initiates the scene
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(CodexClientApp.class.getResource("Lobby.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1520, 850);
            Stage stage = GuiApp.getStage();
            stage.setTitle("Codex! - Lobby");
            Platform.runLater(() -> stage.setScene(scene));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
