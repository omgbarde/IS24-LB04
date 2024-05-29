package codex.lb04.Controller;

import codex.lb04.Message.GameMessage.StartTurnMessage;
import codex.lb04.Model.Game;
import codex.lb04.Network.server.Server;
import codex.lb04.Utils.CircularIterator;

import java.util.ArrayList;

/**
 * TurnController class is responsible for managing the turns of the players in the game.
 */
public class TurnController {
    private static TurnController instance;

    private final ArrayList<String> lobby;
    private final CircularIterator<String> playersQueueIterator;
    private String activePlayer;
    private boolean placedCard = false;
    private boolean drawnCard = false;

    /**
     * Constructor for the TurnController class with the players list and the active player
     */
    private TurnController() {
        Game game = Game.getInstance();
        this.lobby = game.getLobby();
        this.playersQueueIterator = new CircularIterator<>(lobby);
        this.activePlayer = lobby.getFirst();
    }

    /**
     * Singleton pattern for the TurnController class
     *
     * @return the instance of the TurnController
     */
    public static TurnController getInstance() {
        if (instance == null) {
            instance = new TurnController();
        }
        return instance;
    }

    /**
     * sets if the player has drawn a card
     *
     * @param drawnCard is true if the player has drawn a card and false otherwise
     */
    public void setDrawnCard(boolean drawnCard) {
        this.drawnCard = drawnCard;
    }

    /**
     * sets if the player has placed a card
     *
     * @param placedCard is true if the player has placed a card and false otherwise
     */
    public void setPlacedCard(boolean placedCard) {
        this.placedCard = placedCard;
    }

    /**
     * sets if the player has drawn a card
     *
     * @return true if the player has drawn a card and false otherwise
     */
    public boolean hasDrawnCard() {
        return drawnCard;
    }

    /**
     * sets if the player has placed a card
     *
     * @return true if the player has placed a card and false otherwise
     */
    public boolean hasPlacedCard() {
        return placedCard;
    }

    /**
     * Method to reset the instance of the TurnController
     */
    public void resetInstance() {
        instance = null;
    }

    /**
     * getter of the active player
     *
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
        Server.sendMessageToClient(new StartTurnMessage(activePlayer), activePlayer);
    }

    /**
     * getter of the lobby
     *
     * @return the name list of clients in the lobby
     */
    public ArrayList<String> getLobby() {
        return lobby;
    }
}
