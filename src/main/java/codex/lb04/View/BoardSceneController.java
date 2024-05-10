package codex.lb04.View;

import codex.lb04.Model.*;
import javafx.application.Platform;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.FileInputStream;
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
    private Map<Rectangle, Card> initialCardDisplay = new LinkedHashMap<>();
    private GuiView view;

    /**
     * Constructor of the board scene controller
     *
     * @param view the view
     */
    public BoardSceneController(GuiView view) {
        this.gridMap = new HashMap<>();
        this.view = view;
        this.stageReference = view.getStageReference();
    }

    /**
     * draws the visible gold cards
     *
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

    public void updateDrawableResources(ArrayList<ResourceCard> resourceCards) {
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

    public void updateHand(ArrayList<Card> handCards) {
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

    public void updateCommonObjectives(ArrayList<ObjectiveCard> objectiveCards) {
        for (int i = 0; i < objectiveCards.size(); i++) {
            ObjectiveCard card = objectiveCards.get(i);
            commonObjectives.put((Rectangle) commonObjectives.keySet().toArray()[i], card);
            Rectangle rectangle = (Rectangle) commonObjectives.keySet().toArray()[i];
            Platform.runLater(() -> {
                try {
                    drawCommonObjectives(rectangle, card);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    public void updateInitialCardDisplay(InitialCard card) {
        initialCardDisplay.put((Rectangle) initialCardDisplay.keySet().toArray()[0], card);
        Rectangle rectangle = (Rectangle) initialCardDisplay.keySet().toArray()[0];
        Platform.runLater(() -> {
            try {
                drawInitialCardDisplay(rectangle, card);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void updateSecretObjectiveToChoose(ArrayList<ObjectiveCard> objectiveCards) {
        for (int i = 0; i < objectiveCards.size(); i++) {
            ObjectiveCard card = objectiveCards.get(i);
            secretObjectivesToChoose.put((Rectangle) secretObjectivesToChoose.keySet().toArray()[i], card);
            Rectangle rectangle = (Rectangle) secretObjectivesToChoose.keySet().toArray()[i];
            Platform.runLater(() -> {
                try {
                    drawSecretObjectivesToChoose(rectangle, card);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }


    /**
     * Method to handle the click on the grid
     *
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
     *
     * @param event the mouse event
     */
    //TODO
    public void onSelectCardClick(MouseEvent event) {
        if (this.selectedRectangle != null) {
            this.selectedRectangle.setEffect(new Glow(0));
        }
        Card correspondingCard;
        Rectangle clickedRectangle = (Rectangle) event.getSource();
        this.selectedRectangle = clickedRectangle;
        clickedRectangle.setEffect(new Glow(0.5));
        if (hand.containsKey(clickedRectangle)) {
            correspondingCard = hand.get(clickedRectangle);
            this.selectedCard = correspondingCard;
        }
        if (initialCardDisplay.containsKey(clickedRectangle)) {
            correspondingCard = initialCardDisplay.get(clickedRectangle);
            this.selectedCard = correspondingCard;
        }
        if (secretObjectivesToChoose.containsKey(clickedRectangle)) {
            correspondingCard = secretObjectivesToChoose.get(clickedRectangle);
            this.selectedCard = correspondingCard;
        }
    }

    public void setImageToRectangle(String imagePath, Rectangle rectangle) throws FileNotFoundException {
        InputStream is = getClass().getResourceAsStream(imagePath);
        Image image = new Image(is);
        ImagePattern imagePattern = new ImagePattern(image);
        rectangle.setFill(imagePattern);
    }

    /**
     * adds a gold card to the map
     *
     * @param card the card
     */
    public void drawDrawableGold(Rectangle rectangle, Card card) throws FileNotFoundException {
        String imagePath = "/cards_images/CODEX_cards_gold_front/427371a2-5897-4015-8c67-34dd8707c4ba-001.png";
        if (card.isShowingFront()) {
            imagePath = "/cards_images/CODEX_cards_front/card_front_"+ card.getID() + ".png";
        }
     else {
        imagePath = "/cards_images/CODEX_cards_back/card_back_"+ card.getID() + ".png";
    }

    setImageToRectangle(imagePath, rectangle);
        drawableGold.put(rectangle,card);
}

public void drawDrawableResource(Rectangle rectangle, Card card) throws FileNotFoundException {
    String imagePath = "/cards_images/CODEX_cards_gold_front/427371a2-5897-4015-8c67-34dd8707c4ba-001.png";
    if (card.isShowingFront()) {
        imagePath = "/cards_images/CODEX_cards_front/card_front_"+ card.getID() + ".png";
    }
    else {
        imagePath = "/cards_images/CODEX_cards_back/card_back_"+ card.getID() + ".png";
    }
    setImageToRectangle(imagePath, rectangle);
    drawableResources.put(rectangle, card);
}

public void drawCommonObjectives(Rectangle rectangle, Card card) throws FileNotFoundException {
    String imagePath = "/cards_images/CODEX_cards_gold_front/427371a2-5897-4015-8c67-34dd8707c4ba-001.png";
    if (card.isShowingFront()) {
        imagePath = "/cards_images/CODEX_cards_front/card_front_"+ card.getID() + ".png";
    }
    else {
        imagePath = "/cards_images/CODEX_cards_back/card_back_"+ card.getID() + ".png";
    }
    setImageToRectangle(imagePath, rectangle);
    commonObjectives.put(rectangle, card);
}

public void drawHand(Rectangle rectangle, Card card) throws FileNotFoundException {
    String imagePath = "/cards_images/CODEX_cards_gold_front/427371a2-5897-4015-8c67-34dd8707c4ba-001.png";
    if (card.isShowingFront()) {
        imagePath = "/cards_images/CODEX_cards_front/card_front_"+ card.getID() + ".png";
    }
    else {
        imagePath = "/cards_images/CODEX_cards_back/card_back_"+ card.getID() + ".png";
    }
    setImageToRectangle(imagePath, rectangle);
    hand.put(rectangle, card);
}

public void drawInitialCardDisplay(Rectangle rectangle, Card card) throws FileNotFoundException {
    String imagePath = "/cards_images/CODEX_cards_gold_front/427371a2-5897-4015-8c67-34dd8707c4ba-001.png";
    if (card.isShowingFront()) {
        imagePath = "/cards_images/CODEX_cards_front/card_front_"+ card.getID() + ".png";
    }
    else {
        imagePath = "/cards_images/CODEX_cards_back/card_back_"+ card.getID() + ".png";
    }
    setImageToRectangle(imagePath, rectangle);
    initialCardDisplay.put(rectangle, card);
}


public void drawSecretObjective(Rectangle rectangle, Card card) throws FileNotFoundException {
    String imagePath = "/cards_images/CODEX_cards_gold_front/427371a2-5897-4015-8c67-34dd8707c4ba-001.png";
    if (card.isShowingFront()) {
        imagePath = "/cards_images/CODEX_cards_front/card_front_"+ card.getID() + ".png";
    }
    else {
        imagePath = "/cards_images/CODEX_cards_back/card_back_"+ card.getID() + ".png";
    }
    setImageToRectangle(imagePath, rectangle);
    //TODO
    secretObjective.put(rectangle, card);
}

public void drawSecretObjectivesToChoose(Rectangle rectangle, Card card) throws FileNotFoundException {
    String imagePath = "/cards_images/CODEX_cards_gold_front/427371a2-5897-4015-8c67-34dd8707c4ba-001.png";
    if (card.isShowingFront()) {
        imagePath = "/cards_images/CODEX_cards_front/card_front_"+ card.getID() + ".png";
    }
    else {
        imagePath = "/cards_images/CODEX_cards_back/card_back_"+ card.getID() + ".png";
    }
    setImageToRectangle(imagePath, rectangle);

    secretObjectivesToChoose.put(rectangle, card);
}

public void drawGenericCard(Rectangle rectangle, Card card) throws FileNotFoundException {
    //InputStream is = getClass().getResourceAsStream("/cards_images/CODEX_cards_gold_front/427371a2-5897-4015-8c67-34dd8707c4ba-001.png");
    String imagePath = "/cards_images/CODEX_cards_gold_front/427371a2-5897-4015-8c67-34dd8707c4ba-001.png";
    if (card.isShowingFront()) {
        imagePath = "/cards_images/CODEX_cards_front/card_front_"+ card.getID() + ".png";
    }
    else {
        imagePath = "/cards_images/CODEX_cards_back/card_back_"+ card.getID() + ".png";
    }
    setImageToRectangle(imagePath, rectangle);

    if (hand.containsKey(selectedRectangle)) {
        hand.put(rectangle, card);
    }
    if (initialCardDisplay.containsKey(selectedRectangle)) {
        initialCardDisplay.put(rectangle, card);
    }

}

//TODO
public void drawPlacedCard(Rectangle rectangle, Card card) {
}


/**
 * method to add a rectangle to the grid map
 *
 * @param rectangle the rectangle to add to the map
 */
//TODO
public void addRectangleToGridMap(Rectangle rectangle) {
    gridMap.put(rectangle, null);
}

public void addRectangleToSecretObjectivesToChoose(Rectangle rectangle) {
    rectangle.setOnMouseClicked(this::onSelectCardClick);
    secretObjectivesToChoose.put(rectangle, null);
}

public void addRectangleToInitialCardDisplay(Rectangle rectangle) {
    rectangle.setOnMouseClicked(this::onSelectCardClick);
    initialCardDisplay.put(rectangle, null);
}

/**
 * method to add a rectangle to the drawable gold map
 *
 * @param rectangle the rectangle to add to the map
 */
public void addRectangleToDrawableGoldMap(Rectangle rectangle) {
    drawableGold.put(rectangle, null);
}

/**
 * method to add a rectangle to the drawable resources map
 *
 * @param rectangle the rectangle to add to the map
 */
public void addRectangleToDrawableResourcesMap(Rectangle rectangle) {
    drawableResources.put(rectangle, null);
}

/**
 * method to add a rectangle to the hand map
 *
 * @param rectangle the rectangle to add to the map
 */
public void addRectangleToHandMap(Rectangle rectangle) {
    rectangle.setOnMouseClicked(this::onSelectCardClick);
    hand.put(rectangle, null);
}

/**
 * method to add a rectangle to the common objectives map
 *
 * @param rectangle the rectangle to add to the map
 */
public void addRectangleToCommonObjectivesMap(Rectangle rectangle) {
    commonObjectives.put(rectangle, null);
}

/**
 * method to add a rectangle to the secret objectives map
 *
 * @param rectangle the rectangle to add to the map
 */
public void addRectangleToSecretObjectiveMap(Rectangle rectangle) {
    secretObjective.put(rectangle, null);
}


/**
 * sets up the drawable gold card
 *
 * @param Top the top rectangle
 * @param v1  the first rectangle
 * @param v2  the second rectangle
 */
public void setUpDrawableGold(Rectangle Top, Rectangle v1, Rectangle v2) {
    addRectangleToDrawableGoldMap(Top);
    addRectangleToDrawableGoldMap(v1);
    addRectangleToDrawableGoldMap(v2);
}

/**
 * sets up the drawable resources
 *
 * @param top the top rectangle
 * @param v1  the first rectangle
 * @param v2  the second rectangle
 */
public void setUpDrawableResources(Rectangle top, Rectangle v1, Rectangle v2) {
    addRectangleToDrawableResourcesMap(top);
    addRectangleToDrawableResourcesMap(v1);
    addRectangleToDrawableResourcesMap(v2);
}

public void setUpSecretObjectivesToChoose(Rectangle top, Rectangle v1) {
    addRectangleToSecretObjectivesToChoose(top);
    addRectangleToSecretObjectivesToChoose(v1);
}

public void setUpInitialCardDisplay(Rectangle v1) {
    addRectangleToInitialCardDisplay(v1);
}


/**
 * sets up the hand map
 *
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
 *
 * @param common1 the first rectangle
 * @param common2 the second rectangle
 */
public void setUpCommonObjectivesMap(Rectangle common1, Rectangle common2) {
    addRectangleToCommonObjectivesMap(common1);
    addRectangleToCommonObjectivesMap(common2);
}

/**
 * sets up the secret objectives map
 *
 * @param secret the rectangle
 */
public void setSecretObjectiveMap(Rectangle secret) {
    addRectangleToSecretObjectiveMap(secret);
}

public void flipCard() throws FileNotFoundException {
    if (this.selectedCard != null && this.selectedRectangle != null) {
        if (hand.containsKey(selectedRectangle) || initialCardDisplay.containsKey(selectedRectangle)) {
            this.selectedCard.flip();
            drawGenericCard(this.selectedRectangle, this.selectedCard);
        }
    }
}


//GETTERS

/**
 * method to get the selected card
 *
 * @return the selected card
 */
public Card getSelectedCard() {
    return this.selectedCard;
}

/**
 * method to get the selected rectangle
 *
 * @return the selected rectangle
 */
public Rectangle getSelectedRectangle() {
    return this.selectedRectangle;
}
}
