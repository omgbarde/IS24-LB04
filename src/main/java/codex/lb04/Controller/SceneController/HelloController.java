package codex.lb04.Controller.SceneController;

import codex.lb04.CodexClientApp;
import codex.lb04.Message.LoginMessage;
import codex.lb04.Utils.ConnectionUtil;
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
    //FXML fields
    @FXML
    private Label errorLabel;
    @FXML
    private TextField username;
    @FXML
    private TextField serverAddress;
    @FXML
    private TextField serverPort;
    @FXML
    private Button playButton;

    /**
     * method to initialize the view
     * @param url is the url
     * @param resourceBundle is the resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        playButton.setOnAction(actionEvent -> {
            String usr = username.getText();
            String addr = serverAddress.getText();
            int port = ConnectionUtil.defaultPort;
            if (checkValid(usr, addr, port)){
                try {
                    port = Integer.parseInt(serverPort.getText());
                } catch (NumberFormatException e) {
                    setErrorLabel("Using default port");
                    return;
                }
                try {
                    CodexClientApp.setClientSocket(usr, addr, port);
                } catch (RuntimeException e) {
                    setErrorLabel("Server not available");
                    return;
                }
                LoginMessage loginMessage = new LoginMessage(usr);
                CodexClientApp.sendMessage(loginMessage);
                disableAll();

            } else {
                setErrorLabel("Enter valid username, address and port");
            }
        });
    }

    /**
     * method to check if the input from the view is valid, it uses ConnectionUtil methods
     * @param usr is the username
     * @param addr is the address
     * @param port is the port
     * @return true if the input is valid
     */
    private boolean checkValid(String usr, String addr, int port) {
        return !usr.isEmpty() && ConnectionUtil.isValidAddr(addr) && ConnectionUtil.isValidPort(port);
    }

    /**
     * method to disable all the input fields while awaiting server response
     */
    private void disableAll() {
        if (CodexClientApp.isConnected()) {
            username.setDisable(true);
            serverAddress.setDisable(true);
            serverPort.setDisable(true);
            playButton.setDisable(true);
        }
    }

    /**
     * method to set the error label
     * @param error is the error message you want to show
     */
    public void setErrorLabel(String error) {
        errorLabel.setText(error);
    }

}