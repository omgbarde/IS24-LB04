package codex.lb04.Message.DrawMessage;

import codex.lb04.Message.Message;
import codex.lb04.Message.MessageType;

import java.io.Serial;
import java.util.ArrayList;

/**
 * Message to notify the user with the updated points.
 */
public class UpdatePointsMessage extends Message {
    @Serial
    private static final long serialVersionUID = -7378567170247137380L;

    ArrayList<Integer> points;

    /**
     * Constructor for UpdatePointsMessage
     *
     * @param points points to update
     */
    public UpdatePointsMessage(String username, ArrayList<Integer> points) {
        super(username, MessageType.UPDATE_POINTS);
        this.points = points;
    }


    /**
     * Getter for points
     *
     * @return points
     */
    public ArrayList<Integer> getPoints() {
        return points;
    }
}
