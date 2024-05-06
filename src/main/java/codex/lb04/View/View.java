package codex.lb04.View;

import codex.lb04.CodexClientApp;
import codex.lb04.Network.client.ClientParser;

import java.util.ArrayList;


/**
 * Interface for the view
 */
public abstract class View {
    private static ClientParser clientParser;
    /**
     * abstract method to switch the scene to the one specified by the name
     *
     * @param sceneName is the name of the scene to load
     */
    public abstract void switchScene(String sceneName);

    public abstract void setTitle(String title);

    public abstract void setMode(String mode);

    public abstract void updateList(ArrayList<String> names);

    public abstract void updateListLater(ArrayList<String> names);

    public void print(String string) {
        CodexClientApp.print(string);
    }

    public ClientParser getMessageParser() {
        return this.clientParser;
    }

    public abstract void drawHelloScene();
    public abstract void drawLoginScene();
    public abstract void drawLobbyScene();
    public abstract void drawCreateGameScene();
    public abstract void drawBoardScene();

    public abstract void updateLobby(ArrayList<String> names);

}
