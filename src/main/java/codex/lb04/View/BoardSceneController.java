package codex.lb04.View;

import codex.lb04.Model.Card;
import codex.lb04.Model.GoldCard;
import codex.lb04.Model.ResourceCard;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * This class represents the controller of the board scene
 */
public class BoardSceneController {

    private Stage stageReference;

    // Declare the map
    private Map<Rectangle, Card> gridMap = new HashMap<>();
    private Card selectedCard = null;
    private Rectangle selectedRectangle = null;
    private Map<Rectangle, Card> drawableResources = new LinkedHashMap<>();
    private Map<Rectangle, Card> drawableGold = new LinkedHashMap<>();
    private Map<Rectangle, Card> hand = new LinkedHashMap<>();
    private Map<Rectangle, Card> commonObjectives = new LinkedHashMap<>();
    private Map<Rectangle, Card> secretObjectivesToChoose = new LinkedHashMap<>();
    private Map<Rectangle, Card> secretObjective = new LinkedHashMap<>();
    private GuiView view;

    /**
     * Constructor of the board scene controller
     * @param view the view
     */
    public BoardSceneController(GuiView view) {
        this.gridMap = new HashMap<>();
        this.view = view;
        this.stageReference = view.getStageReference();
    }

    /**
     * draws the visible gold cards
     * @param goldCards the gold cards arrayList
     */
    public void updateDrawableGold(ArrayList<GoldCard> goldCards) {
        for (int i = 0; i < goldCards.size(); i++) {
            GoldCard goldCard = goldCards.get(i);
            drawableGold.put((Rectangle) drawableGold.keySet().toArray()[i], goldCard);
            Rectangle rectangle = (Rectangle) drawableGold.keySet().toArray()[i];
            Platform.runLater(() -> {
                try {
                    drawDrawableGold(rectangle, goldCard);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    public void updateDrawableResources(ArrayList<ResourceCard> resourceCards){
        for (int i = 0; i < resourceCards.size(); i++) {
            ResourceCard resourceCard = resourceCards.get(i);
            drawableResources.put((Rectangle) drawableResources.keySet().toArray()[i], resourceCard);
            Rectangle rectangle = (Rectangle) drawableResources.keySet().toArray()[i];
            Platform.runLater(() -> {
                try {
                    drawDrawableResource(rectangle, resourceCard);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }



    public void updateHand(ArrayList<Card> handCards){
        for (int i = 0; i < handCards.size(); i++) {
            Card card = handCards.get(i);
            hand.put((Rectangle) hand.keySet().toArray()[i], card);
            Rectangle rectangle = (Rectangle) hand.keySet().toArray()[i];
            Platform.runLater(() -> {
                try {
                    drawHand(rectangle, card);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }




    /**
     * Method to handle the click on the grid
     * @param event the mouse event
     */
    //TODO per piazzare la carta
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

    /**
     * Method to handle the click on the hand
     * @param event the mouse event
     */
    //TODO
    public void onSelectCardClick(MouseEvent event) {
        Rectangle clickedRectangle = (Rectangle) event.getSource();
        Card correspondingCard = gridMap.get(clickedRectangle);
        // Now you can use the correspondingCard object
    }

    public void setImageToRectangle(String imagePath, Rectangle rectangle) throws FileNotFoundException {
        InputStream is = getClass().getResourceAsStream(imagePath);
        Image image = new Image(is);
        ImagePattern imagePattern = new ImagePattern(image);
        rectangle.setFill(imagePattern);
    }

    /**
     * adds a gold card to the map
     * @param card the card
     */
    public void drawDrawableGold(Rectangle rectangle, Card card) throws FileNotFoundException {
        String imagePath = "/cards_images/CODEX_front/card_front_1.png";
        if (card.iShowingFront()) {
            imagePath = "/cards_images/CODEX_cards_front/card_front_" + card.getID() + ".png";
        } else {
            imagePath = "/cards_images/CODEX_back/card_back_" + card.getID() + ".png";
        }
        setImageToRectangle(imagePath, rectangle);
        drawableGold.put(rectangle, card);
    }

    public void drawDrawableResource(Rectangle rectangle,Card card) throws FileNotFoundException {
        String imagePath = "/cards_images/CODEX_front/card_front_1.png";
        if (card.iShowingFront()) {
            imagePath = "/cards_images/CODEX_cards_front/card_front_" + card.getID() + ".png";
        } else {
            imagePath = "/cards_images/CODEX_back/card_back_" + card.getID() + ".png";
        }
        setImageToRectangle(imagePath, rectangle);
        drawableResources.put(rectangle, card);
    }

    public void drawHand(Rectangle rectangle,Card card) throws FileNotFoundException {
        String imagePath = "/cards_images/CODEX_front/card_front_1.png";
        //TODO capire perchè arrivano di back
        if (card.iShowingFront()) {
            imagePath = "/cards_images/CODEX_cards_front/card_front_" + card.getID() + ".png";
        } else {
            imagePath = "/cards_images/CODEX_back/card_back_" + card.getID() + ".png";
        }
        setImageToRectangle(imagePath, rectangle);
        hand.put(rectangle, card);
    }

    public void drawCommonObjectives(Rectangle rectangle,Card card){
        InputStream is = getClass().getResourceAsStream("/cards_images/CODEX_cards_gold_front/427371a2-5897-4015-8c67-34dd8707c4ba-001.png");
        if (card.iShowingFront()) {
            is = getClass().getResourceAsStream("/cards_images/CODEX_cards_front/card_front_" + card.getID() + ".png");
        } else {
            is = getClass().getResourceAsStream("/cards_images/CODEX_back/card_back_" + card.getID() + ".png");
        }
        Image image = new Image(is);
        ImagePattern imagePattern = new ImagePattern(image);
        rectangle.setFill(imagePattern);
        //TODO
        commonObjectives.put(rectangle, card);
    }

    public void drawSecretObjective(Rectangle rectangle,Card card){
        InputStream is = getClass().getResourceAsStream("/cards_images/CODEX_cards_gold_front/427371a2-5897-4015-8c67-34dd8707c4ba-001.png");
        if (card.iShowingFront()) {
            is = getClass().getResourceAsStream("/cards_images/CODEX_cards_front/card_front_" + card.getID() + ".png");
        } else {
            is = getClass().getResourceAsStream("/cards_images/CODEX_back/card_back_" + card.getID() + ".png");
        }
        Image image = new Image(is);
        ImagePattern imagePattern = new ImagePattern(image);
        rectangle.setFill(imagePattern);
        //TODO
        secretObjective.put(rectangle, card);
    }

    public void drawSecretObjectivesToChoose(Rectangle rectangle,Card card){
        InputStream is = getClass().getResourceAsStream("/cards_images/CODEX_cards_gold_front/427371a2-5897-4015-8c67-34dd8707c4ba-001.png");
        if (card.iShowingFront()) {
            is = getClass().getResourceAsStream("/cards_images/CODEX_cards_front/card_front_" + card.getID() + ".png");
        } else {
            is = getClass().getResourceAsStream("/cards_images/CODEX_back/card_back_" + card.getID() + ".png");
        }
        Image image = new Image(is);
        ImagePattern imagePattern = new ImagePattern(image);
        rectangle.setFill(imagePattern);
        //TODO
        secretObjectivesToChoose.put(rectangle, card);
    }




    /**
     * method to add a rectangle to the grid map
     * @param rectangle the rectangle to add to the map
     */
    //TODO
    public void addRectangleToGridMap(Rectangle rectangle) {
        gridMap.put(rectangle, null);
    }

    /**
     * method to add a rectangle to the drawable gold map
     * @param rectangle the rectangle to add to the map
     */
    public void addRectangleToDrawableGoldMap(Rectangle rectangle) {
        drawableGold.put(rectangle, null);
    }

    /**
     * method to add a rectangle to the drawable resources map
     * @param rectangle the rectangle to add to the map
     */
    public void addRectangleToDrawableResourcesMap(Rectangle rectangle) {
        drawableResources.put(rectangle, null);
    }

    /**
     * method to add a rectangle to the hand map
     * @param rectangle the rectangle to add to the map
     */
    public void addRectangleToHandMap(Rectangle rectangle) {
        hand.put(rectangle, null);
    }

    /**
     * method to add a rectangle to the common objectives map
     * @param rectangle the rectangle to add to the map
     */
    public void addRectangleToCommonObjectivesMap(Rectangle rectangle) {
        commonObjectives.put(rectangle, null);
    }

    /**
     * method to add a rectangle to the secret objectives map
     * @param rectangle the rectangle to add to the map
     */
    public void addRectangleToSecretObjectiveMap(Rectangle rectangle) {
        secretObjective.put(rectangle, null);
    }

    /*public void testImage() {
        InputStream is = getClass().getResourceAsStream("/cards_images/CODEX_cards_gold_front/427371a2-5897-4015-8c67-34dd8707c4ba-001.png");
        Image image = new Image(is);

        // Create the pattern
        ImagePattern imagePattern = new ImagePattern(image);

        // Set the fill of the rectangle to the pattern

        Rectangle rectangle = (Rectangle) secretObjective.keySet().toArray()[0];
        rectangle.setFill(imagePattern);
    }*/


    /**
     * sets up the drawable gold card
     * @param Top the top rectangle
     * @param v1 the first rectangle
     * @param v2 the second rectangle
     */
    public void setUpDrawableGold(Rectangle Top, Rectangle v1, Rectangle v2) {
        addRectangleToDrawableGoldMap(Top);
        addRectangleToDrawableGoldMap(v1);
        addRectangleToDrawableGoldMap(v2);//TODO set also the other ones backwards
    }

    /**
     * sets up the drawable resources
     * @param top the top rectangle
     * @param v1 the first rectangle
     * @param v2 the second rectangle
     */
    public void setUpDrawableResources(Rectangle top, Rectangle v1, Rectangle v2) {
        addRectangleToDrawableResourcesMap(top);
        addRectangleToDrawableResourcesMap(v1);
        addRectangleToDrawableResourcesMap(v2);
    }

    /**
     * sets up the hand map
     * @param hand1 the first rectangle
     * @param hand2 the second rectangle
     * @param hand3 the third rectangle
     */
    public void setUpHandMap(Rectangle hand1, Rectangle hand2, Rectangle hand3) {
        addRectangleToHandMap(hand1);
        addRectangleToHandMap(hand2);
        addRectangleToHandMap(hand3);
    }

    /**
     * sets up the common objectives map
     * @param common1 the first rectangle
     * @param common2 the second rectangle
     */
    public void setUpCommonObjectivesMap(Rectangle common1, Rectangle common2) {
        addRectangleToCommonObjectivesMap(common1);
        addRectangleToCommonObjectivesMap(common2);
    }

    /**
     * sets up the secret objectives map
     * @param secret the rectangle
     */
    public void setSecretObjectiveMap(Rectangle secret) {
        addRectangleToSecretObjectiveMap(secret);
    }

    /**
     * method to select a card
     *
     * @param rectangle the rectangle selected
     */
    //TODO
    public void selectCard(Rectangle rectangle) {
        this.selectedRectangle = rectangle;
        //this.selectedCard = card; card da prendere dal rif. di rectangle
    }


    //GETTERS

    /**
     * method to get the selected card
     * @return the selected card
     */
    public Card getSelectedCard() {
        return this.selectedCard;
    }

    /**
     * method to get the selected rectangle
     * @return the selected rectangle
     */
    public Rectangle getSelectedRectangle() {
        return this.selectedRectangle;
    }
}
