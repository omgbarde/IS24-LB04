package codex.lb04.Model;

import codex.lb04.Message.GameMessage.DrawCardMessage;
import codex.lb04.Message.GameMessage.StartGameMessage;
import codex.lb04.Message.LoginReply;
import codex.lb04.Message.LogoutReply;
import codex.lb04.Message.PlayersConnectedMessage;
import codex.lb04.Model.Enumerations.GameState;
import codex.lb04.Observer.Observable;

import java.util.ArrayList;
import java.util.Objects;

//TODO notify observer dove necessario

/**
 * This class represents the game
 */
public class Game extends Observable {
    private static Game instance;
    private ArrayList<Player> players = new ArrayList<Player>();
    private Deck deck;
    private GameState gameState = GameState.LOGIN;
    private ArrayList<String> lobby = new ArrayList<String>();
    private ArrayList<ObjectiveCard> inGameObjectiveCards = new ArrayList<ObjectiveCard>();
    private int numPlayers = 0;

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

    public void resetInstance() {
        this.deck.resetInstance();
        instance = null;
    }

    /**
     * Draws a resource card
     * @param username the player who draws the card
     * @param pick the card to pick
     */
    public void drawResourceCard(String username, Integer pick) {
        Player player = getPlayerByName(username);
        player.getBoard().drawResourceCard(pick);
        notifyObserver(new DrawCardMessage(username, player.getBoard().getLastDrawnCard()));
    }

    public InitialCard getInitialCard(String username) {
        Player player = getPlayerByName(username);
        return player.getBoard().getInitialCard();
    }

    /**
     * draws the initial card for a player
     *
     */
    public void setInitialCardForAllPlayers() {
        for(Player p : players){
            p.getBoard().setInitialCard();
        }
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
    public ArrayList<String> getLobby() {
        return lobby;
    }

    /**
     * Adds a player name to the list
     * @param player the player name to add
     */
    public void addPlayerToLobby(String player) {
        //checks maximum number of clients connected and if username is available
        if (this.lobby.size() < numPlayers && checkUsername(player)) {
            this.lobby.add(player);
            notifyObserver(new LoginReply(player, true));
            notifyObserver(new PlayersConnectedMessage("server",getLobby()));
            if(this.lobby.size() == numPlayers){
                notifyObserver(new StartGameMessage("server"));
            }
        } else {
            notifyObserver(new LoginReply(player, false));
        }
    }

    /**
     * Removes a player name from the list
     * @param player the player name to remove
     */
    public void removePlayerFromLobby(String player) {
        this.lobby.remove(player);
        notifyObserver(new LogoutReply(player, true));
        notifyObserver(new PlayersConnectedMessage("server",getLobby()));

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

    /**
     * creates the players from the arraylist of usernames of the players
     */
    public void createPlayers() {
        for (String player : lobby) {
            Player newPlayer = new Player(player);
            addPlayer(newPlayer);
        }
    }

    public void drawHandForAllPlayers(){
        for(Player player : players){
            player.getBoard().drawGoldCard(2);
            player.getBoard().drawResourceCard(2);
            player.getBoard().drawResourceCard(2);
        }
    }

    /**
     * check for victory conditions
     * if there is more than one player, the player with the most objectives completed wins
     */
    public ArrayList<String> getWinners() {
        Integer max = 0;
        ArrayList<String> winners = new ArrayList<>();
        ArrayList<String> obj_winners = new ArrayList<>();
        for (Player player : players) {
            player.getBoard().finalPointsUpdate();
            //System.out.println("Player " + player.getUsername() + " points after update: " + player.getBoard().getPoints());
            if (player.getBoard().getPoints() > max) {
                winners.clear();
                winners.add(player.getUsername());
                max = player.getBoard().getPoints();
            }
            if(Objects.equals(player.getBoard().getPoints(), max) && !winners.contains(player.getUsername())){
                winners.add(player.getUsername());
            }
        }
        // cosí facendo se ho un solo vincitore non faccio il controllo
        if (winners.size() > 1) {
            Integer obj = 0;
            for (String p : winners) {
                //System.out.println("Player " + p + " number of objectives: " + getPlayerByName(p).getBoard().checkNumberObjectives());
                if (getPlayerByName(p).getBoard().checkNumberObjectives() > obj) {
                    obj_winners.clear();
                    obj_winners.add(p);
                    obj = getPlayerByName(p).getBoard().checkNumberObjectives();
                }
                if (Objects.equals(getPlayerByName(p).getBoard().getPoints(), obj)) {
                    obj_winners.add(p);
                }
            }
        }else{
            obj_winners = winners;
        }
        //System.out.println("Winners: " + obj_winners);
        return obj_winners;
        // notifyObserver(new WinnerMessage(obj_winners));
    }

    /**
     * checks if the username is already taken
     *
     * @param usr is the username to be checked
     * @return true if the username is not taken, false otherwise
     */
    public boolean checkUsername(String usr) {
        for (String alreadyInLobby : this.lobby) {
            if (alreadyInLobby.equals(usr)) return false;
        }
        return true;
    }

    public void setNumPlayers(int numPlayers) {
        this.numPlayers = numPlayers;
    }

    public int getNumPlayers() {
        return numPlayers;
    }
}