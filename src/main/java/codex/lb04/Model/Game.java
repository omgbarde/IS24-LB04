package codex.lb04.Model;

import codex.lb04.Network.server.ClientHandler;

import java.util.ArrayList;

//TODO implementare gestione dei turni e delle fasi di gioco
//TODO implementare inizio della partita
//TODO implementare win condition
//TODO implementare calcolo dei punti di tutti i giocatori e dichiarazione del vincitore
//TODO test della classe game

/**
 * This class represents the game
 */
public class Game {
    private static Game instance;
    private ArrayList<Player> players = new ArrayList<Player>();
    private Deck deck;
    private GameState gameState;
    private ArrayList<String> lobby = new ArrayList<String>();
    private ArrayList<ObjectiveCard> inGameObjectiveCards = new ArrayList<ObjectiveCard>();

    /**
     * Private constructor to prevent instantiation from outside the class
     *
     *
     */
    private Game() {
        this.deck = Deck.getInstance();
    }

    /**
     * Singleton instance method
     *
     * @return the singleton instance of the Game class
     */
    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

    /**
     * Draws a resource card
     * @param username the player who draws the card
     * @param pick the card to pick
     */
    public void drawResourceCard(String username, Integer pick) {
        Player player = getPlayerByName(username);
        player.getBoard().drawResourceCard(pick);
    }

    public InitialCard getInitialCard(String username){
        Player player = getPlayerByName(username);
        return player.getBoard().getInitialCard();
    }

    /**
     * draws the initial card for a player
     * @param username the player who draws the card
     */
    public void setInitialCard(String username){
        Player player = getPlayerByName(username);
        player.getBoard().setInitialCard();
    }

    /**
     * Draws a gold card
     * @param username the player who draws the card
     * @param pick the card to pick
     */
    public void drawGoldCard(String username, Integer pick) {
        Player player = getPlayerByName(username);
        player.getBoard().drawGoldCard(pick);
    }

    /**
     * Places a card on the board
     *
     * @param card the card to place
     */
    public void placeCard(Card card, Integer x, Integer y, Player player) {
        player.getBoard().placeCard(card, x, y);
    }

    /**
     * Sets the common objectives for all players
     */
    public void setCommonObjectivesForallPlayers() {
        inGameObjectiveCards = this.deck.setCommonObjectives();
        for (Player player : players) {
            player.getBoard().setCommonObjectives(inGameObjectiveCards);
        }
    }

    /**
     * returns the common objectives
     * @return the common objectives
     */
    public ArrayList<ObjectiveCard> getCommonObjectives() {
        return inGameObjectiveCards;
    }

    /**
     * sets the secret objective for a player
     * @param username the player who picks the card
     * @param pick the card to pick
     */
    public void setSecretObjectives(String username, Integer pick) {
        Player player = getPlayerByName(username);
        player.getBoard().setSecretObjective(pick);
    }

    /**
     * Returns the player names
     * @return the player names
     */
    public ArrayList<String> getPlayerNames() {
        return lobby;
    }

    /**
     * Adds a player name to the list
     * @param player the player name to add
     */
    public void addPlayerName(String player) {
        this.lobby.add(player);
    }

    /**
     * Removes a player name from the list
     * @param player the player name to remove
     */
    public void removePlayerName(String player) {
        this.lobby.remove(player);
    }

    /**
     * Starts the game ,creates the players and personal boards
     */
    public void startGame() {
        if (lobby.size() >= 2 && lobby.size() <= 4 /*&&  messaggio d'avvio*/) {
            for (String name : lobby) {
                Player player = new Player(name, new Board());
                this.addPlayer(player);
            }
            this.setGameState(GameState.IN_GAME);
        }
    }

    /**
     * Adds a player to the game
     *
     * @param player the player to add
     */
    public void addPlayer(Player player) {
        this.players.add(player);
    }

    /**
     * Removes a player from the game
     *
     * @param player the player to remove
     */
    public void removePlayer(String player) {
        this.players.remove(getPlayerByName(player));
    }

    /**
     * search for a player by name
     * @param player the player to search
     * @return the player
     */
    public Player getPlayerByName(String player) {
        for (Player p : players) {
            if (p.getUsername().equals(player)) {
                return p;
            }
        }
        return null;
    }

    /**
     * returns the game state
     *
     * @return the game state
     */
    public GameState getGameState() {
        return gameState;
    }

    /**
     * sets the game state
     *
     * @param gameState the game state
     */
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    /**
     * returns the players
     *
     * @return the players
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * returns the deck
     *
     * @return the deck
     */
    public Deck getDeck() {
        return deck;
    }

}
