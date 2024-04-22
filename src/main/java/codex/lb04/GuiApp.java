package codex.lb04;

import codex.lb04.Message.Message;
import codex.lb04.Network.client.ClientSocket;
import codex.lb04.View.GuiView;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class GuiApp extends Application{
    private static GuiView guiView;
    private static ClientSocket clientSocket;
    private static Stage stageReference;

    /**
     * this method starts the GUI
     *
     * @param stage is the stage (window) reference
     * @throws IOException when an error occurs in loading the fxml
     */
    @Override
    public void start(Stage stage) throws IOException {
        stageReference = stage;
        guiView = new GuiView(stage);
        guiView.switchScene("Hello.fxml");
        try {
            stage.getIcons().add(new Image(new FileInputStream("src/main/resources/graphics/codex-naturalis-espt.jpg")));
        } catch (FileNotFoundException e) {
            System.out.println("window icon not found");
        }
        stage.setTitle("Codex! - Welcome");
        stage.setResizable(false);
        stage.show();
    }

    /**
     * this method stops the GUI
     */
    public void stop() {
        Platform.exit();
        System.exit(0);
    }

    /**
     * this method returns the stage reference
     *
     * @return the stage reference
     */
    public static Stage getStage() {
        return stageReference;
    }

    public static void setMode(String s) {
        if (s.equals("full")) {
            Platform.runLater(() -> stageReference.setFullScreen(true));
        } else {
            Platform.runLater(() -> stageReference.setFullScreen(true));
        }
    }

    public static GuiView getGuiView() {
        return guiView;
    }

    public static ClientSocket getClientSocket() {
        return clientSocket;
    }

    public static void setClientSocket(String usr, String addr, int port) {
        clientSocket = new ClientSocket(guiView, usr, addr, port);
    }

    public static void disconnect() {
        clientSocket.disconnect();
    }

    public static void sendMessage(Message message) {
        clientSocket.sendMessage(message);
    }

    public static void print(String s) {
        System.out.println(s);
    }
}
