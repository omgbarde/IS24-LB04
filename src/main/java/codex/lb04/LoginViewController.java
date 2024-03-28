package codex.lb04;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * class that controls the login view
 */
public class LoginViewController {
    @FXML
    private Label errorLabel;
    @FXML
    private TextField username;
    @FXML
    private TextField port;
    @FXML
    protected void login() {
        if (username.getText() != null && port.getText() != null){
            int defaultPort = 4444;
            try {
                defaultPort = Integer.parseInt(port.getText());
            }
            catch (Exception e){
                errorLabel.setText("port error or not specified, default port is used");
            }
            Socket clientSocket = null;
            try {
                clientSocket = new Socket(username.getText(),defaultPort);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            //ClientHandler clientHandler = new ClientHandler(clientSocket);
        }else {
            errorLabel.setText("insert username + port");
        }
    }

}