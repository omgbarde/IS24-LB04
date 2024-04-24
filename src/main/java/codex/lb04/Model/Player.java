package codex.lb04.Model;

import java.util.ArrayList;

/**
 * This class represents a player
 */
public class Player {
    private String username;
    private ArrayList<ObjectiveCard> objectives;
    private ArrayList<Card> hand;
    private Board board;

    /**
     * default constructor
     *
     * @param username        username
     *
     */
    public Player(String username, Board board) {
        this.username = username;
        this.hand = new ArrayList<>();
        this.board = board;
    }

    /**
     * This method returns the username of the player
     *
     * @return the username of the player
     */
    public String getUsername() {
        return username;
    }

    public ArrayList<ObjectiveCard> getObjectives() {
        return objectives;
    }


    public ArrayList<Card> getHand() {
        return hand;
    }

    public Board getBoard() {
        return board;
    }



}
