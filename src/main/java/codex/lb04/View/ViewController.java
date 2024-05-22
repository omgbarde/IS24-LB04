package codex.lb04.View;

import codex.lb04.Model.*;

import java.util.ArrayList;

public abstract class ViewController {
    public abstract void drawLobbyScene();

    public abstract void drawHelloScene() ;

    public abstract void updateLobby(ArrayList<String> lobby);

    public abstract void drawCard(Card card) ;

    public abstract void updateDrawableGold(ArrayList<GoldCard> gold) ;

    public abstract void updateDrawableResources(ArrayList<ResourceCard> resourceCards) ;

    public abstract void updateSecretObjective(ObjectiveCard secretObjective) ;

    public abstract void updateHand(ArrayList<Card> hand) ;

    public abstract void updateCommonObjectives(ArrayList<ObjectiveCard> commonObjectives);

    public abstract void updateInitialCardDisplay(InitialCard initialCard) ;

    public abstract void updateSecretObjectiveToChoose(ArrayList<ObjectiveCard> secretObjectives) ;

    public abstract void placeCard(Integer x, Integer y, Card card);

    public abstract void deselectCard() ;

    public abstract void displayAlert(String alert) ;

    public abstract void drawBoardScene() ;

    public abstract void setYourTurnText();

    public abstract void updatePoints(ArrayList<Integer> points);

    public abstract void cleanYourTurnText();

    public abstract void updateChat(String message);

    public abstract void print(String string);

    public abstract void showWinners(String winner);
}
