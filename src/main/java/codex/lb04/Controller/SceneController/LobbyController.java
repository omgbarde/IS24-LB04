package codex.lb04.Controller.SceneController;

import codex.lb04.CodexClientApp;
import codex.lb04.Message.GameMessage.StartGameMessage;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LobbyController implements Initializable {
    @FXML
    private Button playButton;
    @FXML
    private Button backButton;
    @FXML
    private Label lobbyLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        backButton.setOnAction(actionEvent -> {
            //GuiApp.sendMessage(new LogoutMessage(GuiApp.getClientSocket().getUsername()));
            CodexClientApp.getView().setTitle("Codex! - Welcome");
            CodexClientApp.getView().switchScene("Hello");
            //disconnect from server
            CodexClientApp.disconnect();
        });
        playButton.setOnAction(actionEvent -> {
            CodexClientApp.sendMessageToServer(new StartGameMessage(""));
        });

    }

    public void updateList(ArrayList<String> names){

        assert this.lobbyLabel != null;
        for (String name : names) {
            String txt = this.lobbyLabel.getText();
            assert txt != null;
            txt+=  "\n" + name;
            lobbyLabel.setText(txt);
        }
    }
}
