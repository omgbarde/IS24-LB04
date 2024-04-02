package codex.lb04.View;

import codex.lb04.CodexClientApp;
import codex.lb04.Network.client.ClientSocket;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * class that controls the hello view
 */
public class HelloController implements Initializable {
    @FXML
    private Label errorLabel;
    @FXML
    private TextField username;
    @FXML
    private Button playButton;
    private ClientSocket clientSocket = CodexClientApp.getClientSocket();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        playButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String usr = username.getText();
                if(!usr.isEmpty()){
                    clientSocket.sendMessage(usr);
                }
            }
        });
    }
}