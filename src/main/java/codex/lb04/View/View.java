package codex.lb04.View;

import codex.lb04.CodexClientApp;
import codex.lb04.Model.Card;
import codex.lb04.Network.client.ClientParser;

import java.util.ArrayList;


/**
 * This class represents the abstract view of the client
 */
public abstract class View {
    private static ClientParser clientParser;

    public void print(String string) {
        CodexClientApp.print(string);
    }

    public abstract void drawHelloScene();
    public abstract void drawLoginScene();
    public abstract void drawLobbyScene();
    public abstract void drawCreateGameScene();
    public abstract void drawBoardScene();
    public abstract void updateLobby(ArrayList<String> names);
    public abstract void drawCard(Card card);

}
