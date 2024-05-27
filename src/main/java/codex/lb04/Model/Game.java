package codex.lb04.Model;

import codex.lb04.Message.DrawMessage.DrawBoardMessage;
import codex.lb04.Message.DrawMessage.UpdateCommonObjectivesMessage;
import codex.lb04.Message.DrawMessage.UpdateSecretObjectiveToChooseMessage;
import codex.lb04.Message.GameMessage.EndTurnMessage;
import codex.lb04.Message.GameMessage.StartTurnMessage;
import codex.lb04.Message.GameMessage.WinnersMessage;
import codex.lb04.Message.GenericMessage;
import codex.lb04.Message.LoginReply;
import codex.lb04.Message.PlayersConnectedMessage;
import codex.lb04.Model.Enumerations.GameState;
import codex.lb04.Observer.GameObserver;
import codex.lb04.Observer.Observable;

import java.util.ArrayList;
import java.util.Objects;

/**
 * This class represents the game
 */
public class Game extends Observable {
    private static Game instance;
    private final ArrayList<Player> players;
    private Deck deck;
    private GameState gameState;
    private final ArrayList<String> lobby;
    private ArrayList<ObjectiveCard> inGameObjectiveCards;
    private int numPlayers;
    private int replies;

    /**
     * Private constructor to prevent instantiation from outside the class
     */
    private Game() {
        this.players = new ArrayList<>();
        this.lobby = new ArrayList<>();
        this.inGameObjectiveCards = new ArrayList<>();
        this.gameState = GameState.LOGIN;
        this.numPlayers = 0;
        this.replies = 0;
    }

    /**
     * sets the deck
     */
    public void setDeck() {
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
     * resets the instance of the game
     */
    public void resetInstance() {
        if (this.deck != null) {
            this.deck.resetInstance();
        }
        instance = null;
    }

    /**
     * Draws a resource card
     *
     * @param username the player who draws the card
     * @param pick     the card to pick
     */
    public void drawResourceCard(String username, Integer pick) {
        Player player = getPlayerByName(username);
        player.getBoard().drawResourceCard(pick);
    }

    /**
     * gets the initial card for a player
     * @param username the player desired
     * @return his initial card
     */
    public InitialCard getInitialCard(String username) {
        Player player = getPlayerByName(username);
        return player.getBoard().getInitialCard();
    }

    /**
     * draws the initial card for all players
     */
    public void setInitialCardForAllPlayers() {
        for (Player p : players) {
            p.getBoard().setInitialCard();
        }
    }

    /**
     * Draws a gold card
     *
     * @param username the player who draws the card
     * @param pick     the card to pick
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
        notifyObserver(new UpdateCommonObjectivesMessage(inGameObjectiveCards));
    }

    /**
     * sets the secret objective for a player
     *
     * @param username the player who picks the card
     * @param pick     the card to pick
     */
    public void setSecretObjectives(String username, Integer pick) {
        Player player = getPlayerByName(username);
        player.getBoard().setSecretObjective(pick);
    }

    /**
     * Adds a player name to the list
     *
     * @param player the player name to add
     */
    public void addPlayerToLobby(String player) {
        //checks maximum number of clients connected and if username is available
        if (this.lobby.size() < numPlayers && checkUsername(player)) {
            this.lobby.add(player);
            notifyObserver(new LoginReply(player, true));

            //creates a clone to avoid discarding serialized messages
            //noinspection unchecked
            ArrayList<String> lobbyClone = (ArrayList<String>) this.lobby.clone();

            notifyObserver(new PlayersConnectedMessage("server", lobbyClone));
        } else {
            notifyObserver(new LoginReply(player, false));
        }
        if (this.lobby.size() == numPlayers) {
            notifyObserver(new DrawBoardMessage("server"));
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
     * search for a player by name
     *
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
     * creates the players from the arraylist of usernames of the players
     */
    public void createPlayers() {
        for (String player : lobby) {
            Player newPlayer = new Player(player);
            addPlayer(newPlayer);
            newPlayer.getBoard().setUsername(player);
            newPlayer.getBoard().addObserver(new GameObserver());
            notifyObserver(new UpdateSecretObjectiveToChooseMessage(player, newPlayer.getBoard().getSecretObjectiveToPick()));
            if (Objects.equals(newPlayer.getUsername(), lobby.getFirst())) {
                notifyObserver(new StartTurnMessage(newPlayer.getUsername()));
            } else {
                notifyObserver(new EndTurnMessage(newPlayer.getUsername()));
            }
        }
    }

    /**
     * draws the initial hand for all players, drawing one gold card and two resource cards
     */
    public void drawHandForAllPlayers() {
        for (Player player : players) {
            player.getBoard().drawInitial();
        }
    }

    /**
     * check for victory conditions
     * if there is more than one player, the player with the most objectives completed wins
     */
    public ArrayList<String> getWinners() {
        ArrayList<String> winners = new ArrayList<>();
        ArrayList<String> objWinners = new ArrayList<>();
        int max = 0;
        for (Player player : players) {
            player.getBoard().finalPointsUpdate();
            int playerPoints = player.getBoard().getPoints();
            String username = player.getUsername();

            if (playerPoints > max) {
                winners.clear();
                winners.add(player.getUsername());
                max = playerPoints;
            }
            else if (playerPoints == max){
                winners.add(username);
            }
        }

        if (winners.size() > 1) {
            int maxObj = 0;
            for (String p : winners) {
                int completedObjectives = getPlayerByName(p).getBoard().checkNumberObjectives();

                if (completedObjectives > maxObj) {
                    objWinners.clear();
                    objWinners.add(p);
                    maxObj = completedObjectives;
                }
                else if (completedObjectives == maxObj){
                    objWinners.add(p);
                }
            }
        } else {
            objWinners = winners;
        }
        return objWinners;

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

    /**
     * sends a message used to start drawing the board
     */
    public void drawBoard() {
        notifyObserver(new DrawBoardMessage("server"));
    }

    /**
     * checks if all players ave replied with a ready message
     * @return true if all players have replies, false otherwise
     */
    public boolean checkReplies() {
        replies += 1;
        return replies == lobby.size();
    }


    /**
     * sends a message that someone reached 20 points
     */
    public void notifyEndGame() {
        notifyObserver(new GenericMessage("server", "someone reached 20 pts, end game started!"));
    }

    /**
     * sends a message that the decks and visible decks are finished
     */
    public void notifyFinishedDeck(){
        notifyObserver(new GenericMessage("server", "Deck is finished, end game started!"));
    }

    /**
     * notifies the winner or winners to the observer
     * @param winners the winners
     */
    public void notifyWinner(ArrayList<String> winners) {
        switch (winners.size()) {
            case 1:
                notifyObserver(new WinnersMessage("server", "The winner is: " + getWinners().getFirst()));
                break;
            case 2:
                notifyObserver(new WinnersMessage("server", "The winners are: " + getWinners().get(0) + " and " + getWinners().get(1)));
                break;
            case 3:
                notifyObserver(new WinnersMessage(  "server", "The winners are: " + getWinners().get(0) + ", " + getWinners().get(1) + " and " + getWinners().get(2)));
                break;
            case 4:
                notifyObserver(new WinnersMessage("server", "The winners are: " + getWinners().get(0) + ", " + getWinners().get(1) + ", " + getWinners().get(2) + " and " + getWinners().get(3)));
                break;
            default:
                break;
        }

    }


    //GETTER

    /**
     * Returns the number of players
     *
     * @return the number of players
     */
    public int getNumPlayers() {
        return numPlayers;
    }

    /**
     * Returns the player names
     *
     * @return the player names
     */
    public ArrayList<String> getLobby() {
        return lobby;
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

    //SETTER

    /**
     * sets the game state
     *
     * @param gameState the game state
     */
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    /**
     * sets the number of players
     *
     * @param numPlayers the number of players
     */
    public void setNumPlayers(int numPlayers) {
        this.numPlayers = numPlayers;
    }

    /**
     * returns the common objectives
     *
     * @return the common objectives
     */
    public ArrayList<ObjectiveCard> getCommonObjectives() {
        return inGameObjectiveCards;
    }

}