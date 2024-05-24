package codex.lb04.Model;

/**
 * This class represents a player
 */
public class Player {
    private final String username;
    private final Board board;

    /**
     * default constructor
     *
     * @param username        username
     *
     */
    public Player(String username) {
        this.username = username;
        this.board = new Board();
    }

    //GETTER

    /**
     * This method returns the username of the player
     *
     * @return the username of the player
     */
    public String getUsername() {
        return username;
    }

    /**
     * This method returns the board of the player
     *
     * @return the board of the player
     */
    public Board getBoard() {
        return board;
    }

}