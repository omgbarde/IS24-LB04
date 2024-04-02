module codex.lb {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires org.apache.pdfbox;
    requires java.desktop;
    requires java.logging;

    opens codex.lb04 to javafx.fxml;
    exports codex.lb04;
    exports codex.lb04.Utils;
    opens codex.lb04.Utils to javafx.fxml;
    exports codex.lb04.View;
    opens codex.lb04.View to javafx.fxml;
    exports codex.lb04.Network.client;
    opens codex.lb04.Network.client to javafx.fxml;
    exports codex.lb04.Network.server;
    opens codex.lb04.Network.server to javafx.fxml;
    exports codex.lb04.Message;
    opens codex.lb04.Message to javafx.fxml;
}