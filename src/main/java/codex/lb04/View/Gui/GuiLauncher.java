package codex.lb04.View.Gui;

import codex.lb04.View.View;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class GuiLauncher extends Application {

    /**
     * this method starts the GUI
     *
     * @param stage is the stage (window) reference
     * @throws IOException when an error occurs in loading the fxml
     */
    @Override
    public void start(Stage stage) throws IOException {
        View guiView = new GuiView(stage);
        guiView.drawHelloScene();
        /*try {*/
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/graphics/codex-naturalis-espt.jpg")));
       /* } catch (FileNotFoundException e) {
            System.out.println("window icon not found");
        }*/
        stage.setHeight(600);
        stage.setWidth(1000);
        stage.setResizable(false);
    }

    /**
     * this method stops the GUI
     */
    public void stop() {
        Platform.exit();
        System.exit(0);
    }

}
