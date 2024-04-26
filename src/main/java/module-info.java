module codex.lb {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires java.desktop;
    requires java.logging;
    requires java.naming;

    opens codex.lb04 to javafx.fxml;
    exports codex.lb04;
    opens codex.lb04.Controller.GameController to javafx.fxml;
    exports codex.lb04.Controller.GameController;
    opens codex.lb04.Controller.SceneController to javafx.fxml;
    exports codex.lb04.Controller.SceneController;
    opens codex.lb04.Message.GameMessage to javafx.fxml;
    exports codex.lb04.Message.GameMessage;
    opens codex.lb04.Message to javafx.fxml;
    exports codex.lb04.Message;
    opens codex.lb04.Model.Enumerations to javafx.fxml;
    exports codex.lb04.Model.Enumerations;
    opens codex.lb04.Model to javafx.fxml;
    exports codex.lb04.Model;
    opens codex.lb04.Network.client to javafx.fxml;
    exports codex.lb04.Network.client;
    opens codex.lb04.Network.server to javafx.fxml;
    exports codex.lb04.Network.server;
    opens codex.lb04.Observer to javafx.fxml;
    exports codex.lb04.Observer;
    opens codex.lb04.Utils to javafx.fxml;
    exports codex.lb04.Utils;
    opens codex.lb04.View to javafx.fxml;
    exports codex.lb04.View;


}