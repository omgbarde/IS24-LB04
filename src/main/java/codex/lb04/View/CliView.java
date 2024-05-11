package codex.lb04.View;

import codex.lb04.Model.*;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static java.lang.System.out;

/**
 * This class represents the CLI view
 */
public class CliView extends View {
    @Override
    public void drawHelloScene() {

            out.println(" ___               _                            \n" +
                    "(  _`\\            ( )                           \n" +
                    "| ( (_)   _      _| |   __                      \n" +
                    "| |  _  /'_`\\  /'_` | /'__`(`\\/')               \n" +
                    "| (_( )( (_) )( (_| |(  ___/>  <                \n" +
                    "(____/'`\\___/'`\\__,_)`\\____|_/\\_)               \n" +
                    "                                                \n" +
                    "                                                \n" +
                    " _   _         _                     _          \n" +
                    "( ) ( )       ( )_                  (_ ) _      \n" +
                    "| `\\| |   _ _ | ,_) _   _  _ __  _ _ | |(_) ___ \n" +
                    "| , ` | /'_` )| |  ( ) ( )( '__)'_` )| || /',__)\n" +
                    "| |`\\ |( (_| || |_ | (_) || | ( (_| || || \\__, \\\n" +
                    "(_) (_)`\\__,_)`\\__)`\\___/'(_) `\\__,_|___|_|____/");

            out.println("Welcome to Codex Naturalis Board Game!");

        }
    @Override
    public void drawLoginScene() {

    }
    @Override
    public void drawLobbyScene() {

    }
    @Override
    public void drawCreateGameScene() {

    }
    @Override
    public void drawBoardScene() {

    }
    @Override
    public void updateLobby(ArrayList<String> names) {

    }

    @Override
    public void drawCard(Card card) {

    }

    @Override
    public void displayAlert(String alert) {

    }
    @Override
    public void updateGold(ArrayList<GoldCard> goldCards) {

    }

    @Override
    public void updateResource(ArrayList<ResourceCard> resourceCards) {

    }

    @Override
    public void updateHand(ArrayList<Card> hand) {

    }

    @Override
    public void updateCommonObjectives(ArrayList<ObjectiveCard> commonObjectives) {

    }

    @Override
    public void updateInitialCardDisplay(InitialCard card){

    }

    @Override
    public void updateSecretObjectiveToChoose(ArrayList<ObjectiveCard> secretObjectives){

    }

    @Override
    public  void placeCard(Integer x , Integer y , Card card){

    }

    @Override
    public void updateSecretObjective(ObjectiveCard secretObjectives){

    }
}
