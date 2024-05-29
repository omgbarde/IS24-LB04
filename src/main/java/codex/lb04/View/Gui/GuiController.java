package codex.lb04.View.Gui;

import codex.lb04.Message.GameMessage.*;
import codex.lb04.Model.*;
import codex.lb04.Network.client.ClientSocket;
import codex.lb04.View.ViewController;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;

/**
 * Controller class for the GUI view (mainly for the board scene)
 */
public class GuiController extends ViewController {
    private final GuiView guiView;
    private ClientSocket clientSocket;
    //grid map fields
    private Map<Rectangle, Card> gridMap;

    private Card selectedCard = null;
    private Rectangle selectedRectangle = null;

    private Map<Rectangle, Card> drawableResources;
    private Map<Rectangle, Card> drawableGold;
    private Map<Rectangle, Card> hand;
    private Map<Rectangle, Card> commonObjectives;
    private Map<Rectangle, Card> secretObjectivesToChoose;
    private Map<Rectangle, Card> secretObjective;
    private Map<Rectangle, Card> initialCardDisplay;
    private Map<Rectangle, Text> pointsDisplay;

    /**
     * Constructor of the board scene controller
     *
     * @param guiView the view
     */
    public GuiController(GuiView guiView) {
        this.gridMap = new HashMap<>();
        this.guiView = guiView;
        this.drawableResources = new LinkedHashMap<>();
        this.hand = new LinkedHashMap<>();
        this.drawableGold = new LinkedHashMap<>();
        this.commonObjectives = new LinkedHashMap<>();
        this.secretObjectivesToChoose = new LinkedHashMap<>();
        this.secretObjective = new LinkedHashMap<>();
        this.initialCardDisplay = new LinkedHashMap<>();
        this.pointsDisplay = new LinkedHashMap<>();

    }

    /**
     * Method to draw the lobby scene
     */
    @Override
    public void drawLobbyScene() {
        Platform.runLater(guiView::drawLobbyScene);
    }

    /**
     * Method to draw the hello scene
     */
    @Override
    public void drawHelloScene() {
        Platform.runLater(guiView::drawHelloScene);
    }

    /**
     * Method to update the lobby
     */
    @Override
    public void updateLobby(ArrayList<String> lobby) {
        Platform.runLater(() -> guiView.updateLobby(lobby));
    }

    /**
     * draws the visible gold cards
     *
     * @param goldCards the gold cards arrayList
     */
    @Override
    public void updateDrawableGold(ArrayList<GoldCard> goldCards) {
        drawableGold.replaceAll((r, v) -> null);
        for (Rectangle rectangle : drawableGold.keySet()) {
            Platform.runLater(() -> cleanImage(rectangle));
        }
        for (int i = 0; i < goldCards.size(); i++) {
            GoldCard goldCard = goldCards.get(i);
            drawableGold.put((Rectangle) drawableGold.keySet().toArray()[i], goldCard);
            Rectangle rectangle = (Rectangle) drawableGold.keySet().toArray()[i];
            Platform.runLater(() -> {
                try {
                    drawDrawableGold(rectangle, goldCard);
                } catch (FileNotFoundException e) {
                    System.out.println("card image for gold card not found");
                }
            });
        }
    }

    /**
     * updates the drawable resources
     *
     * @param resourceCards the resource cards arrayList
     */
    @Override
    public void updateDrawableResources(ArrayList<ResourceCard> resourceCards) {
        drawableResources.replaceAll((r, v) -> null);
        for (Rectangle rectangle : drawableResources.keySet()) {
            Platform.runLater(() -> cleanImage(rectangle));
        }
        for (int i = 0; i < resourceCards.size(); i++) {
            ResourceCard resourceCard = resourceCards.get(i);
            drawableResources.put((Rectangle) drawableResources.keySet().toArray()[i], resourceCard);
            Rectangle rectangle = (Rectangle) drawableResources.keySet().toArray()[i];
            Platform.runLater(() -> {
                try {
                    drawDrawableResource(rectangle, resourceCard);
                } catch (FileNotFoundException e) {
                    System.out.println("card image for resource card not found");
                }
            });
        }
    }

    /**
     * updates the hand
     *
     * @param handCards the hand cards arrayList
     */
    @Override
    public void updateHand(ArrayList<Card> handCards) {
        hand.replaceAll((r, v) -> null);
        for (Rectangle rectangle : hand.keySet()) {
            Platform.runLater(() -> cleanImage(rectangle));
        }
        for (int i = 0; i < handCards.size(); i++) {
            Card card = handCards.get(i);
            hand.put((Rectangle) hand.keySet().toArray()[i], card);
            Rectangle rectangle = (Rectangle) hand.keySet().toArray()[i];
            Platform.runLater(() -> {
                try {
                    drawHand(rectangle, card);
                } catch (FileNotFoundException e) {
                    System.out.println("card image for a card in hand not found");

                }
            });
        }
    }

    /**
     * updates the common objectives
     *
     * @param objectiveCards the objective cards arrayList
     */
    @Override
    public void updateCommonObjectives(ArrayList<ObjectiveCard> objectiveCards) {
        for (int i = 0; i < objectiveCards.size(); i++) {
            ObjectiveCard card = objectiveCards.get(i);
            commonObjectives.put((Rectangle) commonObjectives.keySet().toArray()[i], card);
            Rectangle rectangle = (Rectangle) commonObjectives.keySet().toArray()[i];
            Platform.runLater(() -> {
                try {
                    drawCommonObjectives(rectangle, card);
                } catch (FileNotFoundException e) {
                    System.out.println("card image for objective not found");
                }
            });
        }
    }

    /**
     * updates the initial card display
     *
     * @param card the initial card
     */
    @Override
    public void updateInitialCardDisplay(InitialCard card) {
        initialCardDisplay.put((Rectangle) initialCardDisplay.keySet().toArray()[0], card);
        Rectangle rectangle = (Rectangle) initialCardDisplay.keySet().toArray()[0];
        Platform.runLater(() -> {
            try {
                drawInitialCardDisplay(rectangle, card);
            } catch (FileNotFoundException e) {
                System.out.println("card image for initial card not found");

            }
        });
    }

    /**
     * updates the secret objectives to choose
     *
     * @param objectiveCards the objective cards arrayList
     */
    @Override
    public void updateSecretObjectiveToChoose(ArrayList<ObjectiveCard> objectiveCards) {
        for (int i = 0; i < objectiveCards.size(); i++) {
            ObjectiveCard card = objectiveCards.get(i);
            secretObjectivesToChoose.put((Rectangle) secretObjectivesToChoose.keySet().toArray()[i], card);
            Rectangle rectangle = (Rectangle) secretObjectivesToChoose.keySet().toArray()[i];
            Platform.runLater(() -> {
                try {
                    drawSecretObjectivesToChoose(rectangle, card);
                } catch (FileNotFoundException e) {
                    System.out.println("card image for secret objective card not found");
                }
            });
        }
    }

    /**
     * updates the secret objective
     *
     * @param objectiveCard the secret objective card chosen
     */
    @Override
    public void updateSecretObjective(ObjectiveCard objectiveCard) {
        secretObjective.put((Rectangle) secretObjective.keySet().toArray()[0], objectiveCard);
        Rectangle rectangle = (Rectangle) secretObjective.keySet().toArray()[0];

        Platform.runLater(() -> {
            try {
                drawSecretObjective(rectangle, objectiveCard);
            } catch (FileNotFoundException e) {
                System.out.println("card image for secret objective card not found");
            }
        });

    }

    /**
     * this method shows or covers the chat scene
     */
    public void toggleChat() {
        Group chatGroup = guiView.getChatGroup();
        if (chatGroup.isVisible()) {
            Platform.runLater(() -> chatGroup.setVisible(false));
        } else {
            Platform.runLater(() -> chatGroup.setVisible(true));
        }
    }

    /**
     * Method to clean an image
     *
     * @param rectangle the part to clean
     */
    public void cleanImage(Rectangle rectangle) {
        rectangle.setFill(Color.BLACK);
    }

    /**
     * Method to place a card on the grid
     *
     * @param x    the x coordinate
     * @param y    the y coordinate
     * @param card the card
     */
    @Override
    public void placeCard(Integer x, Integer y, Card card) {
        for (Rectangle rectangle : gridMap.keySet()) {
            //noinspection unchecked
            ArrayList<Integer> coordinates = (ArrayList<Integer>) rectangle.getUserData();
            Integer X = coordinates.get(0);
            Integer Y = coordinates.get(1);
            if (Objects.equals(X, x) && Objects.equals(Y, y)) {
                if (x == 0 && y == 0) {
                    Rectangle toDisable = (Rectangle) initialCardDisplay.keySet().toArray()[0];
                    toDisable.setOpacity(0);
                    toDisable.setHeight(0);
                    toDisable.setWidth(0);
                    toDisable.setDisable(true);
                    guiView.getInitCardBackground().setOpacity(0);
                    guiView.getInitCardBackground().setHeight(0);
                    guiView.getInitCardBackground().setWidth(0);
                    guiView.getInitCardBackground().setDisable(true);
                }

                gridMap.put(rectangle, card);
                rectangle.setOpacity(1);
                Platform.runLater(() -> {
                    try {
                        drawGenericCard(rectangle, card);
                    } catch (FileNotFoundException e) {
                        System.out.println("card image for placed card not found");
                    }
                });
                Platform.runLater(() -> guiView.bringRectangleToFront(rectangle));

            }
        }
    }

    /**
     * Method to deselect a card
     */
    @Override
    public void deselectCard() {
        if (this.selectedRectangle != null && this.selectedCard != null) {
            this.selectedRectangle.setEffect(new Glow(0));
            this.selectedCard = null;
            this.selectedRectangle = null;
        }
    }

    /**
     * Method to display an alert
     *
     * @param alert the alert
     */
    @Override
    public void displayAlert(String alert) {
        Platform.runLater(() -> guiView.displayAlert(alert));
    }

    /**
     * Method to draw the board scene
     */
    @Override
    public void drawBoardScene() {
        guiView.drawBoardScene();
    }

    /**
     * Method to set the turn text
     */
    @Override
    public void setYourTurnText() {
        Platform.runLater(() -> guiView.getTurnText().setText("YOUR TURN"));

    }

    /**
     * Method to clean the turn text
     */
    @Override
    public void cleanYourTurnText() {
        Platform.runLater(() -> guiView.getTurnText().setText("NOT YOUR TURN"));
    }

    /**
     * Method to handle the click on the grid
     *
     * @param event the mouse event
     */
    public void onGridClick(MouseEvent event) {
        if (selectedCard != null && selectedRectangle != null) {
            Rectangle clickedRectangle = (Rectangle) event.getSource();
            //noinspection unchecked
            ArrayList<Integer> coordinates = (ArrayList<Integer>) clickedRectangle.getUserData();
            Integer X = coordinates.get(0);
            Integer Y = coordinates.get(1);
            clientSocket.sendMessage(new PlaceCardMessage(clientSocket.getUsername(), X, Y, selectedCard));
        }
    }

    /**
     * Method to handle the click on the initial card
     *
     * @param event the context menu event
     */
    public void onInitialCardSelection(ContextMenuEvent event) {
        Rectangle initCardDisplay = (Rectangle) initialCardDisplay.keySet().toArray()[0];
        InitialCard initCard = (InitialCard) initialCardDisplay.get(initCardDisplay);
        clientSocket.sendMessage(new PickInitialCardSideMessage(clientSocket.getUsername(), initCard));
    }

    /**
     * Method to handle the click on the secret objective
     *
     * @param event the context menu event
     */
    public void onSecretObjectivePick(ContextMenuEvent event) {
        Rectangle rectangle = (Rectangle) event.getSource();
        clientSocket.sendMessage(new PickSecretObjectiveMessage(clientSocket.getUsername(), (Integer) rectangle.getUserData()));
    }

    /**
     * Method to handle the click on the gold pick
     *
     * @param event the mouse event
     */
    public void onDrawGoldPick(MouseEvent event) {
        Rectangle rectangle = (Rectangle) event.getSource();
        Integer pick = (Integer) rectangle.getUserData();
        clientSocket.sendMessage(new PickGoldCardMessage(clientSocket.getUsername(), pick));
    }

    /**
     * Method to handle the click on the resource pick
     *
     * @param event the mouse event
     */
    public void onDrawResourcePick(MouseEvent event) {
        Rectangle rectangle = (Rectangle) event.getSource();
        Integer pick = (Integer) rectangle.getUserData();
        clientSocket.sendMessage(new PickResourceCardMessage(clientSocket.getUsername(), pick));
    }

    /**
     * Method to handle the click on the hand
     *
     * @param event the mouse event
     */
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
    }

    /**
     * Method to update the points
     *
     * @param points the points
     */
    @Override
    public void updatePoints(ArrayList<Integer> points) {
        for (int i = 0; i < points.size(); i++) {
            Rectangle rectangle = (Rectangle) pointsDisplay.keySet().toArray()[i];
            Integer point = points.get(i);
            Platform.runLater(() -> drawPoints(rectangle, point));
        }
    }

    /**
     * Method to update the chat
     *
     * @param message the message
     */
    @Override
    public void updateChat(String message) {
        Platform.runLater(() -> guiView.getChatText().appendText(message));
    }

    /**
     * print utility method (not in use in GUI mode)
     *
     * @param string string to print
     */
    @Override
    public void print(String string) {

    }

    /**
     * Method to show the winners
     *
     * @param winner the winner
     */
    @Override
    public void showWinners(String winner) {
        Platform.runLater(() -> {
            guiView.drawWinnerScene();
            guiView.setWinnerLabel(winner);
        });
    }


    /**
     * sets the point text on the board
     *
     * @param rectangle the rectangle containing the points
     * @param point     the points to display
     */
    public void drawPoints(Rectangle rectangle, Integer point) {
        Text text = pointsDisplay.get(rectangle);
        text.setText(point.toString());
    }

    /**
     * sets the image to a rectangle on the board view
     *
     * @param imagePath the file path to the image
     * @param rectangle the rectangle to set the image to
     * @throws FileNotFoundException if the file is not found
     */
    public void setImageToRectangle(String imagePath, Rectangle rectangle) throws FileNotFoundException {
        InputStream is = getClass().getResourceAsStream(imagePath);
        assert is != null;
        Image image = new Image(is);
        ImagePattern imagePattern = new ImagePattern(image);
        rectangle.setFill(imagePattern);
    }

    /**
     * adds a gold card to the map
     *
     * @param rectangle the rectangle to set the image to
     * @param card      the card
     * @throws FileNotFoundException if the file is not found
     */
    public void drawDrawableGold(Rectangle rectangle, Card card) throws FileNotFoundException {
        if (card != null) {
            String imagePath = loadImagePath(card);
            setImageToRectangle(imagePath, rectangle);
            drawableGold.put(rectangle, card);
        }
    }


    /**
     * adds a resource card to the map
     *
     * @param rectangle the rectangle to set the image to
     * @param card      the card
     * @throws FileNotFoundException if the file is not found
     */
    public void drawDrawableResource(Rectangle rectangle, Card card) throws FileNotFoundException {
        if (card != null) {
            String imagePath = loadImagePath(card);
            setImageToRectangle(imagePath, rectangle);
            drawableResources.put(rectangle, card);
        }
    }

    /**
     * method to draw the common objectives
     *
     * @param rectangle the rectangle
     * @param card      the card
     * @throws FileNotFoundException if the file is not found
     */
    public void drawCommonObjectives(Rectangle rectangle, Card card) throws FileNotFoundException {
        String imagePath = loadImagePath(card);
        setImageToRectangle(imagePath, rectangle);
        commonObjectives.put(rectangle, card);
    }

    /**
     * method to draw card images in hand
     *
     * @param rectangle the rectangle containing the card in hand
     * @param card      the card
     * @throws FileNotFoundException if the file is not found
     */
    public void drawHand(Rectangle rectangle, Card card) throws FileNotFoundException {
        String imagePath = loadImagePath(card);
        setImageToRectangle(imagePath, rectangle);
        hand.put(rectangle, card);
    }

    /**
     * method to draw the initial card display
     *
     * @param rectangle the rectangle to set the image to
     * @param card      the card
     * @throws FileNotFoundException if the file is not found
     */
    public void drawInitialCardDisplay(Rectangle rectangle, Card card) throws FileNotFoundException {
        String imagePath = loadImagePath(card);
        setImageToRectangle(imagePath, rectangle);
        initialCardDisplay.put(rectangle, card);
    }

    /**
     * method to draw the secret objective
     *
     * @param rectangle the rectangle to set the image to
     * @param card      the card
     * @throws FileNotFoundException if the file is not found
     */
    public void drawSecretObjective(Rectangle rectangle, Card card) throws FileNotFoundException {
        Rectangle toDisable1 = (Rectangle) secretObjectivesToChoose.keySet().toArray()[0];
        Rectangle toDisable2 = (Rectangle) secretObjectivesToChoose.keySet().toArray()[1];
        this.disableRectangle(toDisable1);
        this.disableRectangle(toDisable2);
        this.disableRectangle(guiView.getSecretObjectiveBackground());

        String imagePath = loadImagePath(card);
        setImageToRectangle(imagePath, rectangle);
        secretObjective.put(rectangle, card);
    }

    /**
     * method to draw the secret objectives to choose
     *
     * @param rectangle the rectangle to set the image to
     * @param card      the card
     * @throws FileNotFoundException if the file is not found
     */
    public void drawSecretObjectivesToChoose(Rectangle rectangle, Card card) throws FileNotFoundException {
        String imagePath = loadImagePath(card);
        setImageToRectangle(imagePath, rectangle);

        secretObjectivesToChoose.put(rectangle, card);
    }

    /**
     * method to draw a generic card placed on the board
     *
     * @param rectangle the rectangle to set the image to
     * @param card      the card
     * @throws FileNotFoundException if the file is not found
     */
    public void drawGenericCard(Rectangle rectangle, Card card) throws FileNotFoundException {
        String imagePath = loadImagePath(card);
        setImageToRectangle(imagePath, rectangle);

        if (initialCardDisplay.containsKey(selectedRectangle)) {
            initialCardDisplay.put(rectangle, card);
        }

    }

    /**
     * method to compose the image path of a card based on its side and id
     *
     * @param card the card
     * @return the image path to the showing side of the card
     */
    private String loadImagePath(Card card) {
        String imagePath;
        if (card.isShowingFront()) {
            imagePath = "/cards_images/CODEX_cards_front/card_front_" + card.getID() + ".png";
        } else {
            imagePath = "/cards_images/CODEX_cards_back/card_back_" + card.getID() + ".png";
        }
        return imagePath;
    }

    /**
     * method to add a rectangle to the grid map
     *
     * @param rectangle the rectangle to add to the map
     */
    public void addRectangleToGridMap(Rectangle rectangle) {
        rectangle.setOnMouseClicked(this::onGridClick);
        gridMap.put(rectangle, null);
    }

    /**
     * method to add a rectangle to the secret objectives to choose map
     *
     * @param rectangle the rectangle to add to the map
     */
    public void addRectangleToSecretObjectivesToChoose(Rectangle rectangle) {
        rectangle.setOnContextMenuRequested(this::onSecretObjectivePick);
        secretObjectivesToChoose.put(rectangle, null);
    }

    /**
     * method to add a rectangle to the initial card display map
     *
     * @param rectangle the rectangle to add to the map
     */
    public void addRectangleToInitialCardDisplay(Rectangle rectangle) {
        rectangle.setOnMouseClicked(this::onSelectCardClick);
        rectangle.setOnContextMenuRequested(this::onInitialCardSelection);
        initialCardDisplay.put(rectangle, null);
    }

    /**
     * method to add a rectangle to the points display map
     *
     * @param rectangle the rectangle to add to the map
     * @param text      the text to display
     */
    public void addRectangleToPointsDisplay(Rectangle rectangle, Text text) {
        pointsDisplay.put(rectangle, text);
    }

    /**
     * method to add a rectangle to the drawable gold map
     *
     * @param rectangle the rectangle to add to the map
     */
    public void addRectangleToDrawableGoldMap(Rectangle rectangle) {
        rectangle.setOnMouseClicked(this::onDrawGoldPick);
        drawableGold.put(rectangle, null);
    }

    /**
     * method to add a rectangle to the drawable resources map
     *
     * @param rectangle the rectangle to add to the map
     */
    public void addRectangleToDrawableResourcesMap(Rectangle rectangle) {
        rectangle.setOnMouseClicked(this::onDrawResourcePick);
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
     * method to disable a rectangle and make it invisible
     *
     * @param rectangle the rectangle to disable
     */
    public void disableRectangle(Rectangle rectangle) {
        rectangle.setOpacity(0);
        rectangle.setHeight(0);
        rectangle.setWidth(0);
        rectangle.setDisable(true);
    }

    /**
     * sets up the drawable gold card rectangles
     *
     * @param Top    the top rectangle
     * @param mid    the middle rectangle
     * @param bottom the bottom rectangle
     */
    public void setUpDrawableGold(Rectangle Top, Rectangle mid, Rectangle bottom) {
        addRectangleToDrawableGoldMap(Top);
        addRectangleToDrawableGoldMap(mid);
        addRectangleToDrawableGoldMap(bottom);
    }

    /**
     * sets up the drawable resources
     *
     * @param top    the top rectangle
     * @param mid    the middle rectangle
     * @param bottom the bottom rectangle
     */
    public void setUpDrawableResources(Rectangle top, Rectangle mid, Rectangle bottom) {
        addRectangleToDrawableResourcesMap(top);
        addRectangleToDrawableResourcesMap(mid);
        addRectangleToDrawableResourcesMap(bottom);
    }

    /**
     * sets up the secret objectives to choose rectangles
     *
     * @param top    the top rectangle
     * @param bottom the bottom rectangle
     */
    public void setUpSecretObjectivesToChoose(Rectangle top, Rectangle bottom) {
        addRectangleToSecretObjectivesToChoose(top);
        addRectangleToSecretObjectivesToChoose(bottom);
    }

    /**
     * sets up the initial card rectangle
     *
     * @param initialCardRectangle the rectangle that will display the initial card
     */
    public void setUpInitialCardDisplay(Rectangle initialCardRectangle) {
        addRectangleToInitialCardDisplay(initialCardRectangle);
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

    /**
     * method to flip a card in hand or in the initial card display
     *
     * @throws FileNotFoundException if the file for the card face is not found
     */
    public void flipCard() throws FileNotFoundException {
        if (this.selectedCard != null && this.selectedRectangle != null) {
            if (hand.containsKey(selectedRectangle) || initialCardDisplay.containsKey(selectedRectangle)) {
                this.selectedCard.flip();
                drawGenericCard(this.selectedRectangle, this.selectedCard);
            }
        }
    }

    /**
     * method to reset the board scene controller
     */
    public void reset() {
        this.gridMap = new HashMap<>();
        this.drawableResources = new LinkedHashMap<>();
        this.hand = new LinkedHashMap<>();
        this.drawableGold = new LinkedHashMap<>();
        this.commonObjectives = new LinkedHashMap<>();
        this.secretObjectivesToChoose = new LinkedHashMap<>();
        this.secretObjective = new LinkedHashMap<>();
        this.initialCardDisplay = new LinkedHashMap<>();
        this.pointsDisplay = new LinkedHashMap<>();
    }

    //SETTER

    /**
     * method to set the client socket
     *
     * @param clientSocket the client socket
     */
    public void setClientSocket(ClientSocket clientSocket) {
        this.clientSocket = clientSocket;
    }

}