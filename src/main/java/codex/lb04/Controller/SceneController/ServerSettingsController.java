package codex.lb04.Controller.SceneController;

import codex.lb04.CodexClientApp;
import codex.lb04.ServerApp;
import codex.lb04.Utils.ConnectionUtil;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;
public class ServerSettingsController implements Initializable {
    @FXML
    private Label localHostLabel;
    @FXML
    private TextField serverPortChoice;
    @FXML
    private TextField numPlayersChoice;
    @FXML
    private Button startServerButton;
    @FXML
    private Label errorLabel;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        localHostLabel.setText(ConnectionUtil.getLocalHost().toString());
        startServerButton.setOnAction(actionEvent -> {
            int port = ConnectionUtil.defaultPort;
            try {
                port = Integer.parseInt(serverPortChoice.getText());
            } catch (NumberFormatException e) {
                setErrorLabel("Using default port");
            }
            int num = 0;
            try {
                num = Integer.parseInt(numPlayersChoice.getText());
            }catch (NumberFormatException e){
                setErrorLabel("Enter a valid number of players");
                return;
            }
            if (checkValid(port, num)) {
                new ServerApp(ConnectionUtil.getLocalHost(),port, num);
                disableAll();
                CodexClientApp.getView().setTitle("Codex!");
                CodexClientApp.getView().switchScene("login");
                }
                else {
                    setErrorLabel("Enter a free port");
                }
            });

    }

    private void disableAll() {
        serverPortChoice.setDisable(true);
        numPlayersChoice.setDisable(true);
        startServerButton.setDisable(true);
    }

    private boolean checkValid(int port, int num) {
        return (ConnectionUtil.isValidPort(port) &&  num >= 2 && num <= 4);
    }

    private void setErrorLabel(String message) {
        errorLabel.setText(message);
    }
}
