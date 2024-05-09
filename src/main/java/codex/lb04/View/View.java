package codex.lb04.View;

import codex.lb04.CodexClientApp;
import codex.lb04.Model.Card;
import codex.lb04.Model.GoldCard;
import codex.lb04.Model.ResourceCard;

import java.util.ArrayList;


/**
 * This class represents the abstract view of the client
 */
public abstract class View {
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
    public abstract void updateGold(ArrayList<GoldCard> goldCards);
    public abstract void updateResource(ArrayList<ResourceCard> resourceCards);
    public abstract void displayAlert(String alert);
}
