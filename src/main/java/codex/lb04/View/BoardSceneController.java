package codex.lb04.View;

import codex.lb04.Model.Card;
import codex.lb04.Model.Enumerations.Color;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BoardSceneController {

    private Stage stageReference;

    // Declare the map
    private Map<Rectangle, Card> rectangleCardMap = new HashMap<>();
    private ArrayList<Rectangle> drawableResourcesRectangles = new ArrayList<>();
    private ArrayList<Rectangle> drawableGoldRectangles = new ArrayList<>();
    private ArrayList<Rectangle> HandRectangles = new ArrayList<>();
    private GuiView view;


    public BoardSceneController(GuiView view){
        this.rectangleCardMap = new HashMap<>();
        this.view   = view;
        this.stageReference = view.getStageReference();
    }

    public void addRectangleToMap(Rectangle rectangle){
        rectangleCardMap.put(rectangle, null);
    }

    /**
     * adds a card to the map
     * @param card the card
     */
    public void addCardToMap(Rectangle rectangle , Card card) {
        // Load the image for the card
        // initialized for view debug purposes
        InputStream is = getClass().getResourceAsStream("cards_images/CODEX_cards_gold_front/427371a2-5897-4015-8c67-34dd8707c4ba-001.png" );;
        if(card.iShowingFront()){
            is = getClass().getResourceAsStream("cards_images/CODEX_cards_gold_front/427371a2-5897-4015-8c67-34dd8707c4ba-0"+card.getID()+".png" );
        }else{
            is = getClass().getResourceAsStream("cards_images/CODEX_cards_gold_back/427371a2-5897-4015-8c67-34dd8707c4ba-0"+card.getID()+".png" );
        }
        Image image = new Image(is);

        // Create the pattern
        ImagePattern imagePattern = new ImagePattern(image);

        // Set the fill of the rectangle to the pattern
        rectangle.setFill(imagePattern);
        // Set other properties of the rectangle
        rectangleCardMap.put(rectangle, card);
        updateView();
    }

    public void updateView(){
        stageReference.show();
    }

    /**
     * draws a card on the board
     * @param card
     */
    public void drawCard(Card card){
        for (Map.Entry<Rectangle, Card> entry : rectangleCardMap.entrySet()) {
            if(card.equals(entry.getValue())){
                Rectangle rectangle = entry.getKey();


            }
        }
    }

    public void updateHashMap(){

    }

    public void updateDrawableResources(){

    }

    // Method to handle rectangle click
    public void onGridClick(MouseEvent event) {
        Rectangle clickedRectangle = (Rectangle) event.getSource();
        Card correspondingCard = rectangleCardMap.get(clickedRectangle);
        // Now you can use the correspondingCard object
    }

    public void onSelectCardClick(MouseEvent event) {
        Rectangle clickedRectangle = (Rectangle) event.getSource();
        Card correspondingCard = rectangleCardMap.get(clickedRectangle);
        // Now you can use the correspondingCard object
    }

    public void startGame() {

    }
}
