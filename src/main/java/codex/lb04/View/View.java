package codex.lb04.View;

import codex.lb04.CodexClientApp;

import java.io.IOException;
import java.util.ArrayList;


/**
 * This class represents the abstract view of the client
 */
public abstract class View {
    public void print(String string) {
        CodexClientApp.print(string);
    }

    public abstract void drawHelloScene();
    public abstract void drawLoginScene() throws IOException;
    public abstract void drawLobbyScene();
    public abstract void drawCreateGameScene();
    public abstract void drawBoardScene();
    public abstract void updateLobby(ArrayList<String> names);

    public abstract void displayAlert(String alert);

}
