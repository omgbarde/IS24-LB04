package codex.lb04.View;

import codex.lb04.Message.DrawMessage.UpdateGoldMessage;
import codex.lb04.Message.Message;
import codex.lb04.Model.Card;
import codex.lb04.Model.GoldCard;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.w3c.dom.css.Rect;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BoardSceneController {

    private Stage stageReference;

    // Declare the map

    private Map<Rectangle, Card> gridMap = new HashMap<>();
    private Card selectedCard = null;
    private Rectangle selectedRectangle = null;
    private Map<Rectangle, Card> drawableResources = new HashMap<>();
    private Map<Rectangle, Card> drawableGold = new HashMap<>();
    private Map<Rectangle, Card> hand = new HashMap<>();
    private Map<Rectangle, Card> commonObjectives = new HashMap<>();
    private Map<Rectangle, Card> secretObjectivesToChoose = new HashMap<>();
    private Map<Rectangle, Card> secretObjective = new HashMap<>();
    private GuiView view;


    public BoardSceneController(GuiView view) {
        this.gridMap = new HashMap<>();
        this.view = view;
        this.stageReference = view.getStageReference();
    }

    public void onMessageReceived(Message message) {
        switch (message.getMessageType()) {
            case UPDATE_GOLD:
                updateGold((UpdateGoldMessage) message);
                break;
        }
    }

    private void updateGold(UpdateGoldMessage message) {
        ArrayList<GoldCard> goldCards = message.getGold();
        for (int i = 0; i < goldCards.size(); i++) {
            GoldCard goldCard = goldCards.get(i);
            drawableGold.put((Rectangle) drawableGold.keySet().toArray()[i], goldCard);
            Rectangle rectangle = (Rectangle) drawableGold.keySet().toArray()[i];
            addCardDrawableGoldToMap(rectangle, goldCard);
        }
        updateView();
    }



    /**
     * draws a card on the board
     * @param card
     */
    public void drawCard(Card card) {
        for (Map.Entry<Rectangle, Card> entry : gridMap.entrySet()) {
            if (card.equals(entry.getValue())) {
                Rectangle rectangle = entry.getKey();
            }
        }
    }

    public void updateHashMap() {

    }

    public void updateDrawableResources() {

    }

    // Method to handle rectangle click
    public void onGridClick(MouseEvent event) {
        if (selectedCard != null) {
            Rectangle clickedRectangle = (Rectangle) event.getSource();
            ArrayList<Integer> coordinates = (ArrayList<Integer>) clickedRectangle.getUserData();
            Integer X = coordinates.get(0);
            Integer Y = coordinates.get(1);
            //clientsocket.send(new PlaceCardMessage(selectedCard, clickedRectangle));
        }

        // Now you can use the correspondingCard object
    }


    public void onSelectCardClick(MouseEvent event) {
        Rectangle clickedRectangle = (Rectangle) event.getSource();
        Card correspondingCard = gridMap.get(clickedRectangle);
        // Now you can use the correspondingCard object
    }







    public void addRectangleToGridMap(Rectangle rectangle) {
        gridMap.put(rectangle, null);
    }

    public void addRectangleToDrawableGoldMap(Rectangle rectangle) {
        drawableGold.put(rectangle, null);
    }

    public void addRectangleToDrawableResourcesMap(Rectangle rectangle) {
        drawableResources.put(rectangle, null);
    }

    public void addRectangleToHandMap(Rectangle rectangle) {
        hand.put(rectangle, null);
    }

    public void addRectangleToCommonObjectivesMap(Rectangle rectangle) {
        commonObjectives.put(rectangle, null);
    }

    public void addRectangleToSecretObjectiveMap(Rectangle rectangle) {
        secretObjective.put(rectangle, null);
    }
    public void testImage(){
        InputStream is = getClass().getResourceAsStream("/cards_images/CODEX_cards_gold_front/427371a2-5897-4015-8c67-34dd8707c4ba-001.png");
        Image image = new Image(is);

        // Create the pattern
        ImagePattern imagePattern = new ImagePattern(image);

        // Set the fill of the rectangle to the pattern

        Rectangle rectangle = (Rectangle) secretObjective.keySet().toArray()[0];
        rectangle.setFill(imagePattern);
    }

    /**
     * adds a card to the map
     * @param card the card
     */
    public void addCardDrawableGoldToMap(Rectangle rectangle, Card card) {
        // Load the image for the card
        // initialized for view debug purposes
        InputStream is = getClass().getResourceAsStream("/cards_images/CODEX_cards_gold_front/427371a2-5897-4015-8c67-34dd8707c4ba-001.png");


        if (card.iShowingFront()) {
            is = getClass().getResourceAsStream("cards_images/CODEX_cards_gold_front/427371a2-5897-4015-8c67-34dd8707c4ba-0" + card.getID() + ".png");
        } else {
            is = getClass().getResourceAsStream("cards_images/CODEX_cards_gold_back/427371a2-5897-4015-8c67-34dd8707c4ba-0" + card.getID() + ".png");
        }

        Image image = new Image(is);

        // Create the pattern
        ImagePattern imagePattern = new ImagePattern(image);

        // Set the fill of the rectangle to the pattern
        rectangle.setFill(imagePattern);
        // Set other properties of the rectangle
        drawableGold.put(rectangle, card);
    }

    public void setUpDrawableGold(Rectangle Top, Rectangle v1, Rectangle v2) {
        addRectangleToDrawableGoldMap(Top);
        addRectangleToDrawableGoldMap(v1);
        addRectangleToDrawableGoldMap(v2);

        InputStream is = getClass().getResourceAsStream("/cards_images/CODEX_cards_gold_front/427371a2-5897-4015-8c67-34dd8707c4ba-001.png");
        Image image = new Image(is);

        // Create the pattern
        ImagePattern imagePattern = new ImagePattern(image);

        // Set the fill of the rectangle to the pattern

        Rectangle rectangle = (Rectangle) drawableGold.keySet().toArray()[0];
        rectangle.setFill(imagePattern);
    }

    public void setUpDrawableResources(Rectangle top, Rectangle v1, Rectangle v2) {
        addRectangleToDrawableResourcesMap(top);
        addRectangleToDrawableResourcesMap(v1);
        addRectangleToDrawableResourcesMap(v2);
    }

    public void setUpHandMap(Rectangle hand1, Rectangle hand2, Rectangle hand3) {
        addRectangleToHandMap(hand1);
        addRectangleToHandMap(hand2);
        addRectangleToHandMap(hand3);
    }

    public void setUpCommonObjectivesMap(Rectangle common1, Rectangle common2) {
        addRectangleToCommonObjectivesMap(common1);
        addRectangleToCommonObjectivesMap(common2);
    }


    public void setSecretObjectiveMap(Rectangle secret) {
        addRectangleToSecretObjectiveMap(secret);
    }



    // Method to select a card
    public void selectCard(Card card, Rectangle rectangle) {
        this.selectedCard = card;
        this.selectedRectangle = rectangle;
    }


    //GETTERS


    // Method to get the selected card
    public Card getSelectedCard() {
        return this.selectedCard;
    }

    // Method to get the selected rectangle
    public Rectangle getSelectedRectangle() {
        return this.selectedRectangle;
    }


    public void updateView() {
        stageReference.show();
    }


}
