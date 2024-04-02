package codex.lb04.Model;

import java.util.ArrayList;

public class Game {
    public static Game instance;
    private ArrayList<Player> players = new ArrayList<Player>();
    private Deck deck;
    private Board board;

    private GameState gameState;

    /**
     * Private constructor to prevent instantiation from outside the class
     * @param player the player
     * @param deck the deck
     * @param board the board
     */
    private Game(Player player, Deck deck, Board board) {
        this.players.add(player);
        this.deck = deck;
        this.board = board;
    }

    /**
     * secondary constructor to add a player to the game
     * @param player
     */
    public Game(Player player){
        this.players.add(player);
    }
    /**
     * Singleton instance method
     * @return the singleton instance of the Game class
     */
    public static Game getInstance(Player player, Deck deck, Board board) {
        if (instance == null) {
            instance = new Game(player, deck, board);
        }
        return instance;
    }
    /**
     * Adds a player to the game
     * @param player the player to add
     */
    public void addPlayer(Player player) {
        this.players.add(player);
    }
    /**
     * returns the game state
     * @return the game state
     */
    public GameState getGameState() {
        return gameState;
    }
    /**
     * sets the game state
     * @param gameState the game state
     */
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }
    /**
     * returns the players
     * @return the players
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }
    /**
     * returns the deck
     * @return the deck
     */
    public Deck getDeck() {
        return deck;
    }
    /**
     * returns the board
     * @return the board
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Places a card on the board
     * @param card the card to place
     */
    public void placeCard(Card card) {
        board.placeCard(card);
    }
}
