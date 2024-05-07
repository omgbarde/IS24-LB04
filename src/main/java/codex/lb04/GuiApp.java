package codex.lb04;

import codex.lb04.View.GuiView;
import codex.lb04.View.View;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class GuiApp extends Application {
    private static View guiView;

    /**
     * this method starts the GUI
     *
     * @param stage is the stage (window) reference
     * @throws IOException when an error occurs in loading the fxml
     */
    @Override
    public void start(Stage stage) throws IOException {
        guiView = new GuiView(stage);
        Platform.runLater(()->guiView.drawHelloScene());
        try {
            stage.getIcons().add(new Image(new FileInputStream("src/main/resources/graphics/codex-naturalis-espt.jpg")));
        } catch (FileNotFoundException e) {
            System.out.println("window icon not found");
        }
        stage.setTitle("Codex! - Welcome");
        stage.setHeight(600);
        stage.setWidth(1000);
        stage.setResizable(true);
    }

    /**
     * this method stops the GUI
     */
    public void stop() {
        Platform.exit();
        System.exit(0);
    }

    public static View getGuiView() {
        return guiView;
    }

}
