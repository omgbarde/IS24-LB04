package codex.lb04.Model;

import java.util.ArrayList;

/**
 * This class represents a player
 */
public class Player {
    private String username;
    private ArrayList<ObjectiveCard> objectives;
    private ObjectiveCard secretObjective;
    private ArrayList<Card> hand;

    /**
     * default constructor
     *
     * @param username        username
     * @param objectives      objectives
     * @param secretObjective secret object
     * @param hand            hand
     */
    public Player(String username, ArrayList<ObjectiveCard> objectives, ObjectiveCard secretObjective, ArrayList<Card> hand) {
        this.username = username;
        this.objectives = objectives;
        this.secretObjective = secretObjective;
        this.hand = hand;
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

    public ObjectiveCard getSecretObjective() {
        return secretObjective;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

}
