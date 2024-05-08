package codex.lb04.Controller;

import codex.lb04.Message.GameMessage.StartTurnMessage;
import codex.lb04.Model.Game;
import codex.lb04.ServerApp;
import codex.lb04.Utils.CircularIterator;

import java.util.ArrayList;

/**
 * TurnController class is responsible for managing the turns of the players in the game.
 */
public class TurnController {

    private String activePlayer;
    private ArrayList<String> lobby;
    private Game game;
    private CircularIterator<String> playersQueueIterator;
    private static TurnController instance;
    private boolean placedCard = false;
    private boolean drawnCard = false;

    /**
     * sets if the player has drawn a card
     * @param drawnCard is true if the player has drawn a card and false otherwise
     */
    public void setDrawnCard(boolean drawnCard) {
        this.drawnCard = drawnCard;
    }
    /**
     * sets if the player has placed a card
     * @param placedCard is true if the player has placed a card and false otherwise
     */
    public void setPlacedCard(boolean placedCard) {
        this.placedCard = placedCard;
    }
    /**
     * sets if the player has drawn a card
     * @return true if the player has drawn a card and false otherwise
     */
    public boolean hasDrawnCard() {
        return drawnCard;
    }
    /**
     * sets if the player has placed a card
     * @return true if the player has placed a card and false otherwise
     */
    public boolean hasPlacedCard() {
        return placedCard;
    }

    /**
     * Singleton pattern for the TurnController class
     * @return the instance of the TurnController
     */
    public static TurnController getInstance() {
        if (instance == null) {
            instance = new TurnController();
        }
        return instance;
    }

    /**
     * Method to reset the instance of the TurnController
     */
    public void resetInstance() {
        instance = null;
    }

    /**
     * Constructor for the TurnController class with the players list and the active player
     */
    private TurnController() {
        this.game = Game.getInstance();
        this.lobby = game.getLobby();
        this.playersQueueIterator = new CircularIterator<>(lobby);
        this.activePlayer = lobby.getFirst();
    }

    /**
     * getter of the active player
     * @return the active player
     */
    public String getActivePlayer() {
        return activePlayer;
    }

    /**
     * method to set the next player active player and change turn
     */
    public void changeTurn() {
        setDrawnCard(false);
        setPlacedCard(false);
        activePlayer = playersQueueIterator.next();
        ServerApp.sendMessageToClient(new StartTurnMessage(activePlayer), activePlayer);
    }

    /**
     * getter of the lobby
     * @return the name list of clients in the lobby
     */
    public ArrayList<String> getLobby() {
        return lobby;
    }
}
