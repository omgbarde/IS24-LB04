package codex.lb04.Controller.SceneController;

import codex.lb04.CodexClientApp;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LobbyController implements Initializable {
    @FXML
    private Button backButton;
    @FXML
    private static TextArea nameList;

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

    public static void updateList(ArrayList<String> names){
        nameList.clear();
        for(String name : names){
            nameList.appendText(name + "\n");
        }
    }
}
