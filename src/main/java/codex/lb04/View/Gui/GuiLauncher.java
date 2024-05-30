package codex.lb04.View.Gui;

import codex.lb04.View.View;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class GuiLauncher extends Application {

    /**
     * this method starts the GUI
     *
     * @param stage is the stage (window) reference
     */
    @Override
    public void start(Stage stage) {
        View guiView = new GuiView(stage);
        //add icon to the window
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/graphics/codex-naturalis-espt.jpg"))));
        stage.setHeight(650);
        stage.setWidth(1000);
        stage.setResizable(true);
        stage.setMinHeight(650);
        stage.setMinWidth(1000);

        guiView.drawHelloScene();
    }

    /**
     * this method stops the GUI
     */
    public void stop() {
        Platform.exit();
        System.exit(0);
    }

}