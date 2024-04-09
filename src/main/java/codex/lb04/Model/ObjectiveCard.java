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

    /**
     * Default constructor
     *
     * @param color the color of the card
     * @param front the face of the card
     * @param back  the face of the card
     */
    public ObjectiveCard(Color color, Face front, Face back, int points, ResourceType resourceNeeded) {
        super(color, front, back);
        this.points = points;
        this.resourceNeeded.add(resourceNeeded);
        this.inGame = false;
    }

    public Integer getPoints() {
        return points;
    }
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
}
