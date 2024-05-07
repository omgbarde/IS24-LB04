package codex.lb04.View;

import codex.lb04.Model.Card;
import codex.lb04.Model.Enumerations.Color;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BoardSceneController {



    // Declare the map
    private Map<Rectangle, Card> rectangleCardMap = new HashMap<>();
    private ArrayList<Rectangle> drawableResourcesRectangles = new ArrayList<>();
    private ArrayList<Rectangle> drawableGoldRectangles = new ArrayList<>();
    private ArrayList<Rectangle> HandRectangles = new ArrayList<>();


    public BoardSceneController(){
        this.rectangleCardMap = new HashMap<>();
    }

    // Method to add a card to the map
    public void addCardToMap(Card card) {
        Rectangle rectangle = new Rectangle(); // Initialize your rectangle here
        rectangle.setFill(Color.RED.getPaint());
        // Set other properties of the rectangle
        rectangleCardMap.put(rectangle, card);
    }

    public void setUpDrawableResources(Rectangle box1 , Rectangle box2 , Rectangle box3){

    }

    public void updateDrawableResources(){

    }

    // Method to handle rectangle click
    private void onRectangleClick(MouseEvent event) {
        Rectangle clickedRectangle = (Rectangle) event.getSource();
        Card correspondingCard = rectangleCardMap.get(clickedRectangle);
        // Now you can use the correspondingCard object
    }
}
