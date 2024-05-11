package codex.lb04.View;

import codex.lb04.CodexClientApp;
import codex.lb04.Model.*;

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
    public abstract void updateHand(ArrayList<Card> hand);
    public abstract void displayAlert(String alert);
    public abstract void updateCommonObjectives(ArrayList<ObjectiveCard> commonObjectives);
    public abstract void placeCard(Integer x , Integer y , Card card);

    public abstract void updateSecretObjectiveToChoose(ArrayList<ObjectiveCard> secretObjectives);
    public abstract void updatePoints(ArrayList<Integer> points);


    public abstract void updateInitialCardDisplay(InitialCard card);
    public abstract void updateSecretObjective(ObjectiveCard secretObjectives);


}
