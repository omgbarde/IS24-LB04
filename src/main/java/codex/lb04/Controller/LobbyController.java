package codex.lb04.Controller;

import codex.lb04.GuiApp;
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
            GuiApp.setTitle("Codex! - Welcome");
            GuiApp.switchScene("Hello.fxml");
            //disconnect from server
            GuiApp.disconnect();
        });
    }
}
