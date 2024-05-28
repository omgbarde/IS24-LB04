module codex.lb {
    requires javafx.controls;
    requires javafx.fxml;
    requires junit;
    requires java.desktop;
    requires java.logging;
    requires java.naming;
    requires jdk.xml.dom;
    requires com.google.gson;

    exports codex.lb04;
    exports codex.lb04.Utils;
    exports codex.lb04.View;
    exports codex.lb04.Network.client;
    exports codex.lb04.Network.server;
    exports codex.lb04.Message;
    exports codex.lb04.Controller;
    exports codex.lb04.Message.GameMessage;
    exports codex.lb04.Message.DrawMessage;
    exports codex.lb04.Model;
    exports codex.lb04.Model.Enumerations;
    exports codex.lb04.View.Gui;
    exports codex.lb04.View.Cli;

    opens codex.lb04 to
            javafx.fxml;
    opens codex.lb04.Utils to
            javafx.fxml;
    opens codex.lb04.View to
            javafx.fxml;
    opens codex.lb04.Network.client to
            javafx.fxml;
    opens codex.lb04.Network.server to
            javafx.fxml;
    opens codex.lb04.Message to
            javafx.fxml;
    opens codex.lb04.Controller to
            javafx.fxml;
    opens codex.lb04.Message.GameMessage to
            javafx.fxml;
    opens codex.lb04.Message.DrawMessage to
            javafx.fxml;
    opens codex.lb04.View.Gui to
            javafx.fxml;
    opens codex.lb04.View.Cli to
            javafx.fxml;
    opens codex.lb04.Model to
            com.google.gson;
}