package codex.lb04.Controller.SceneController;

import codex.lb04.CodexClientApp;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    public Button createGameButton;
    @FXML
    private Button joinGameButton;

    /**
     * method to initialize the view
     * @param url is the url
     * @param resourceBundle is the resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        createGameButton.setOnAction(actionEvent -> {
            CodexClientApp.getView().setTitle("Codex! - Create Game");
            CodexClientApp.getView().switchScene("ServerSettings");
        });

        joinGameButton.setOnAction(actionEvent -> {
            CodexClientApp.getView().setTitle("Codex! - Login");
            CodexClientApp.getView().switchScene("Login");
        });
    };
}
