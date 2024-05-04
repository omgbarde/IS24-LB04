package codex.lb04.Controller.SceneController;

import codex.lb04.CodexClientApp;
import codex.lb04.Message.GameMessage.CreateGameMessage;
import codex.lb04.Utils.ConnectionUtil;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;
public class CreateGameController implements Initializable {
    @FXML
    private Label localHostLabel;
    @FXML
    private TextField numPlayersChoice;
    @FXML
    private TextField usernameField;
    @FXML
    private Button startServerButton;
    @FXML
    private Label errorLabel;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        localHostLabel.setText(ConnectionUtil.getLocalHost().toString());
        startServerButton.setOnAction(actionEvent -> {
            int num = 0;
            try {
                num = Integer.parseInt(numPlayersChoice.getText());
            }catch (NumberFormatException e){
                setErrorLabel("Enter a valid number of players");
                return;
            }
                String usr = usernameField.getText();
            if (checkValid(num,usr)) {
                disableAll();
                try {
                    CodexClientApp.setClientSocket(usr, ConnectionUtil.getLocalHost(), ConnectionUtil.defaultPort);
                } catch (RuntimeException e) {
                    setErrorLabel("Server not available");
                    return;
                }
                CodexClientApp.sendMessageToServer(new CreateGameMessage(usr, ConnectionUtil.defaultPort, num));
            }
            else {
                setErrorLabel("Invalid input");
            }
        });

    }

    private void disableAll() {
        numPlayersChoice.setDisable(true);
        startServerButton.setDisable(true);
    }

    private boolean checkValid(int num, String usr) {
        return (usr != null &&  num >= 2 && num <= 4);
    }

    private void setErrorLabel(String message) {
        errorLabel.setText(message);
    }
}
