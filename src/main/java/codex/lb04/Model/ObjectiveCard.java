package codex.lb04.Model;

import codex.lb04.Model.Enumerations.Color;
import codex.lb04.Model.Enumerations.ResourceType;

import java.util.ArrayList;

/**
 * This class represents an objective card, both secret and non
 */
public class ObjectiveCard extends Card{
    private final int points;
    private ArrayList<ResourceType> resourceNeeded = new ArrayList<>();
    private boolean inGame;
    private Integer ID;

    /**
     * Default constructor
     *
     * @param color the color of the card
     * @param front the face of the card
     * @param back  the face of the card
     */
    public ObjectiveCard(Color color, Face front, Face back, int points, int ID) {
        super(color, front, back);
        this.points = points;
        this.inGame = false;
        this.ID = ID;
    }

    public Integer getPoints() {
        return points;
    }

    // forse conviene mettere il check dell'obiettivo in un'altra classe, in questa mettiamo solo le condizioni
    //metodo che dice se ho raggiunto l'obiettivo
    /*
    public boolean enoughResources(){
        if(myresources>ResourcesObj){
            return true
        }else{
            return false;
        }
    }*/
    public void setInGame(){
        this.inGame = true;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    //TODO implementare gli obiettivi -- trovare un modo per implementarli più che altro
    //ogni numero rappresenta una carta obb



}
