module codex.lb {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires org.apache.pdfbox;
    requires java.desktop;

    opens codex.lb04 to javafx.fxml;
    exports codex.lb04;
    exports codex.lb04.Utils;
    opens codex.lb04.Utils to javafx.fxml;
}