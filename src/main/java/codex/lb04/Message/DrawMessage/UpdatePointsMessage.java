package codex.lb04.Message.DrawMessage;

import codex.lb04.Message.Message;
import codex.lb04.Message.MessageType;

import java.util.ArrayList;

public class UpdatePointsMessage extends Message {

    ArrayList<Integer> points;

    public UpdatePointsMessage(String username, ArrayList<Integer> points) {
        super(username, MessageType.UPDATE_POINTS);
        this.points = points;
    }


    public ArrayList<Integer> getPoints() {
        return points;
    }
}
