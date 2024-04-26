package codex.lb04.Model;

/**
 * This class represents a player
 */
public class Player {
    private String username;
    private Board board;

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

    /**
     * This method returns the username of the player
     *
     * @return the username of the player
     */
    public String getUsername() {
        return username;
    }

    public Board getBoard() {
        return board;
    }



}
