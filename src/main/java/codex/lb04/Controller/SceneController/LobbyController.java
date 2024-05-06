package codex.lb04.Controller.SceneController;

import codex.lb04.CodexClientApp;
import codex.lb04.View.GuiView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LobbyController implements Initializable {
    @FXML
    private Button playButton;
    @FXML
    private Button backButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        backButton.setOnAction(actionEvent -> {
            //GuiApp.sendMessage(new LogoutMessage(GuiApp.getClientSocket().getUsername()));
            CodexClientApp.getView().setTitle("Codex! - Welcome");
            CodexClientApp.getView().switchScene("Hello");
            //disconnect from server
            //CodexClientApp.disconnect();
        });
        playButton.setOnAction(actionEvent -> {
            //CodexClientApp.sendMessageToServer(new StartGameMessage(""));
        });

    }

    public void updateList(ArrayList<String> names){
        Label nameList = new Label();
        StringBuilder sb = new StringBuilder();
        for (String name : names) {
            sb.append(name).append("\n");
        }
        nameList.setText(sb.toString());
        StackPane root = new StackPane();
        root.getChildren().add(nameList);
        GuiView.getStageReference().setScene(new Scene(root, 1520, 850));
        GuiView.getStageReference().show();
    }
}
