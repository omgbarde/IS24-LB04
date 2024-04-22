package codex.lb04.Controller.SceneController;

import codex.lb04.CodexClientApp;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class LobbyController implements Initializable {
    @FXML
    private Button backButton;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        backButton.setOnAction(actionEvent -> {
            //GuiApp.sendMessage(new LogoutMessage(GuiApp.getClientSocket().getUsername()));
            CodexClientApp.getView().setTitle("Codex! - Welcome");
            CodexClientApp.getView().switchScene("Hello");
            //disconnect from server
            CodexClientApp.disconnect();
        });
    }
}
