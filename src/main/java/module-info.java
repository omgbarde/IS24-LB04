module codex.lb {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;

    opens codex.lb04 to javafx.fxml;
    exports codex.lb04;
}