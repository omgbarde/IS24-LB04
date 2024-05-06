package codex.lb04.View;

import codex.lb04.Message.GameMessage.CreateGameMessage;
import codex.lb04.Message.LoginMessage;
import codex.lb04.Message.LogoutMessage;
import codex.lb04.Model.Card;
import codex.lb04.Model.Enumerations.Color;
import codex.lb04.Network.client.ClientSocket;
import codex.lb04.Utils.ConnectionUtil;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * class that represents the GUI view
 */
public class GuiView extends View {
    private static Stage stageReference;
    private static ClientSocket clientSocket;

    Map<Rectangle, Card> cardMap = new HashMap<>();

    public GuiView(Stage stage) {
        stage.setHeight(600);
        stage.setWidth(1000);
        stage.setResizable(false);
        stageReference = stage;
        //this.helloController =  new LoginController();
        //this.lobbyController = new LobbyController();
        //this.createGameController = new CreateGameController();
    }

    public static Stage getStageReference() {
        return stageReference;
    }


    @Override
    public void drawHelloScene() {
        //creating elements
        StackPane root = new StackPane();
        InputStream is = getClass().getResourceAsStream("/graphics/CODEX_wallpaper_1080.jpg");
        Image image = new Image(is);
        ImageView imageView = new ImageView(image);
        imageView.setImage(image);
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(root.computeAreaInScreen());
        //adding listeners
        Button createGameButton = new Button("Create Game");
        createGameButton.setOnAction(actionEvent -> {
            drawCreateGameScene();
        });
        Button joinGameButton = new Button("Join Game");
        joinGameButton.setOnAction(actionEvent -> {
            drawLoginScene();
        });
        Label titleLabel = new Label("Codex naturalis");
        //append elements to the root
        root.getChildren().add(imageView);
        root.getChildren().add(titleLabel);
        titleLabel.setTranslateY(-200);
        joinGameButton.setTranslateY(50);
        root.getChildren().add(createGameButton);
        root.getChildren().add(joinGameButton);

        stageReference.setScene(new Scene(root, 1520, 850));
        stageReference.show();
    }


    @Override
    public void drawLoginScene() {
        //creating elements
        stageReference.setTitle("Codex! - Login");
        StackPane root = new StackPane();

        TextField usernameField = new TextField();
        usernameField.setPromptText("username");
        usernameField.setMaxWidth(200);

        TextField serverAddressField = new TextField();
        serverAddressField.setPromptText("server address");
        usernameField.setMaxWidth(200);

        TextField serverPortField = new TextField();
        serverPortField.setPromptText("server port");
        usernameField.setMaxWidth(200);

        Button loginButton = new Button("Login");
        Button backButton = new Button("Back");

        Label titleLabel = new Label("insert your username, server address and server port plis");
        Label errorLabel = new Label();

        //adding listeners
        loginButton.setOnAction(actionEvent -> {
            String usr = usernameField.getText();
            String addr = serverAddressField.getText();
            int port = ConnectionUtil.defaultPort;
            try {
                port = Integer.parseInt(serverPortField.getText());
            } catch (NumberFormatException e) {
                errorLabel.setText("Using default port");
            }
            if (ConnectionUtil.checkValid(usr, addr, port)) {
                try {
                    clientSocket = new ClientSocket(this, usr, addr, port);
                } catch (IOException e) {
                    errorLabel.setText("Server not available");
                    return;
                }
                LoginMessage loginMessage = new LoginMessage(usr);
                clientSocket.sendMessage((loginMessage));
                loginButton.setDisable(true);
            } else {
                errorLabel.setText("Enter valid username, address and port");
            }
        });

        backButton.setOnAction(actionEvent -> {
            drawHelloScene();
        });


        //append elements to the root
        root.getChildren().add(titleLabel);
        titleLabel.setTranslateY(-200);
        usernameField.setTranslateY(-100);
        serverAddressField.setTranslateY(-50);
        serverPortField.setTranslateY(0);
        loginButton.setTranslateY(50);
        backButton.setTranslateY(50);
        errorLabel.setTranslateY(100);
        loginButton.setTranslateX(50);
        backButton.setTranslateX(-50);


        root.getChildren().add(usernameField);
        root.getChildren().add(serverAddressField);
        root.getChildren().add(serverPortField);
        root.getChildren().add(loginButton);
        root.getChildren().add(backButton);
        root.getChildren().add(errorLabel);


        stageReference.setScene(new Scene(root, 1520, 850));
        stageReference.show();
    }


    @Override
    public void drawLobbyScene() {
        stageReference.setTitle("Codex! - Lobby");
        StackPane root = new StackPane();

        Label titleLabel = new Label("Players in the lobby");
        Label nameList = new Label();

        Button playButton = new Button("Play");
        Button backButton = new Button("Back");
        root.getChildren().add(titleLabel);

        titleLabel.setTranslateY(-200);
        nameList.setTranslateY(-100);

        root.getChildren().add(nameList);

        playButton.setTranslateY(50);
        playButton.setTranslateX(50);
        backButton.setTranslateY(50);
        backButton.setTranslateX(-50);

        backButton.setOnAction(actionEvent -> {
            clientSocket.sendMessage(new LogoutMessage(clientSocket.getUsername()));
        });

        root.getChildren().add(playButton);
        root.getChildren().add(backButton);
        stageReference.setScene(new Scene(root, 1520, 850));
        stageReference.show();

    }


    @Override
    public void updateLobby(ArrayList<String> names) {
        Parent root = stageReference.getScene().getRoot();

        StringBuilder sb = new StringBuilder();
        for (String name : names) {
            sb.append(name).append("\n");
        }
        Label nameList = new Label(sb.toString());

        ((StackPane) root).getChildren().add(nameList);
    }

    @Override
    public void drawCreateGameScene() {
        //creating elements
        stageReference.setTitle("Codex! - Create Game");
        StackPane root = new StackPane();

        Label localHostLabel = new Label("Localhost: " + ConnectionUtil.getLocalHost());

        TextField numPlayersChoice = new TextField();
        numPlayersChoice.setPromptText("number of players");
        numPlayersChoice.setMaxWidth(200);

        TextField usernameField = new TextField();
        usernameField.setPromptText("choose a username");
        usernameField.setMaxWidth(200);

        Button confirmButton = new Button("Confirm");
        Button backButton = new Button("Back");
        Label errorLabel = new Label();


        //adding listeners
        confirmButton.setOnAction(actionEvent -> {
            int num = 0;
            try {
                num = Integer.parseInt(numPlayersChoice.getText());
            } catch (NumberFormatException e) {
                errorLabel.setText("Enter a valid number of players");
                return;
            }
            String usr = usernameField.getText();
            if (ConnectionUtil.checkValid(num, usr)) {
                confirmButton.setDisable(true);
                try {
                    clientSocket = new ClientSocket(this, usr, ConnectionUtil.getLocalHost(), ConnectionUtil.defaultPort);
                } catch (IOException e) {
                    errorLabel.setText("Server not available");
                    return;
                }
                clientSocket.sendMessage(new CreateGameMessage(usr, ConnectionUtil.defaultPort, num));
            } else {
                errorLabel.setText("Invalid input");
            }
        });
        backButton.setOnAction(actionEvent -> {
            drawHelloScene();
        });
        root.getChildren().add(localHostLabel);
        localHostLabel.setTranslateY(-200);
        numPlayersChoice.setTranslateY(-100);
        usernameField.setTranslateY(-50);
        confirmButton.setTranslateY(50);
        backButton.setTranslateY(50);
        errorLabel.setTranslateY(100);
        errorLabel.setTranslateX(50);
        confirmButton.setTranslateX(-50);
        root.getChildren().add(numPlayersChoice);
        root.getChildren().add(usernameField);
        root.getChildren().add(confirmButton);
        root.getChildren().add(errorLabel);
        root.getChildren().add(backButton);
        stageReference.setScene(new Scene(root, 1520, 850));
        stageReference.show();
    }

    @Override
    public void drawBoardScene() {

        double centerX = 1000 / 2.0;
        double centerY = 600 / 2.0;
        double cardWidth = 124;
        double cardHeight = 82.5;


        stageReference.setTitle("Codex! - your board");

        // Create a group for static elements
        Group staticRoot = new Group();
        // Add static elements to staticRoot here

        // Create a group for movable elements
        Group movableRoot = new Group();

        // Create a group to hold both the static and movable groups
        Group root = new Group();
        root.getChildren().addAll(movableRoot, staticRoot);

        double stageWidth = 1000;
        double stageHeigth = 600;


        /**
         * random image for debug
         */
        // Load the image
        InputStream is = getClass().getResourceAsStream("/cards_images/CODEX_cards_gold_front/427371a2-5897-4015-8c67-34dd8707c4ba-001.png");
        Image image = new Image(is);

        // Create the pattern
        ImagePattern imagePattern = new ImagePattern(image);

        // Create the rectangle and set the fill to the pattern
        Rectangle rect = new Rectangle(centerX - 124, centerY - 82.5, 124, 82.5); // Subtract half the width and height of the rectangle to center it
        rect.setFill(imagePattern);


        rect.setOnMouseClicked(e -> {
            System.out.println("Rectangle was clicked!");
            // Add your code here to perform the action when the rectangle is clicked
        });

        movableRoot.getChildren().add(rect);

        /**
         * STATIC PART OF THE STAGE
         */
        //TODO implementare selected card

        // BOX THAT CONTAINS RESOURCE CARDS THAT CAN BE DRAWN
        double rectangleWidthOfResourceCardPicker = 130;
        double rectangleHeightOfResourceCardPicker = 259.5;
        Rectangle ResourceCardsBox = new Rectangle(stageWidth - rectangleWidthOfResourceCardPicker, 0, rectangleWidthOfResourceCardPicker, rectangleHeightOfResourceCardPicker);
        ResourceCardsBox.setFill(Color.BLACK.getPaint());

        //Display of the resource cards that can be drawn
        Rectangle ResourceCard1 = new Rectangle(stageWidth - cardWidth - 3, 3, cardWidth, cardHeight);
        ResourceCard1.setFill(Color.RED.getPaint());

        Rectangle ResourceCard2 = new Rectangle(stageWidth - cardWidth - 3, 3 + cardHeight + 3, cardWidth, cardHeight);
        ResourceCard2.setFill(Color.RED.getPaint());

        Rectangle ResourceCard3 = new Rectangle(stageWidth - cardWidth - 3, 3 + cardHeight + 3 + cardHeight + 3, cardWidth, cardHeight);
        ResourceCard3.setFill(Color.RED.getPaint());

        // BOX THAT CONTAINS GOLD CARDS THAT CAN BE DRAWN
        double rectangleWidthOfGoldCardPicker = 130;
        double rectangleHeightOfGoldCardPicker = 259.5;
        Rectangle GoldCardsBox = new Rectangle(stageWidth - rectangleWidthOfGoldCardPicker, 270, rectangleWidthOfGoldCardPicker, rectangleHeightOfGoldCardPicker);
        GoldCardsBox.setFill(Color.BLACK.getPaint());

        //Display of the resource cards that can be drawn
        Rectangle GoldCard1 = new Rectangle(stageWidth - cardWidth - 3, 270 + 3, cardWidth, cardHeight);
        GoldCard1.setFill(Color.RED.getPaint());

        Rectangle GoldCar2 = new Rectangle(stageWidth - cardWidth - 3, 270 + 3 + cardHeight + 3, cardWidth, cardHeight);
        GoldCar2.setFill(Color.RED.getPaint());

        Rectangle GoldCard3 = new Rectangle(stageWidth - cardWidth - 3, 270 + 3 + cardHeight + 3 + cardHeight + 3, cardWidth, cardHeight);
        GoldCard3.setFill(Color.RED.getPaint());

        // HAND BOX
        double rectangleWidthHand = 130;
        double rectangleHeightHand = 259.5;
        Rectangle HandBox = new Rectangle(0, 0, rectangleWidthHand, rectangleHeightHand);
        HandBox.setFill(Color.BLACK.getPaint());

        //cards in hand
        Rectangle HandCard1 = new Rectangle(3, 3, cardWidth, cardHeight);
        HandCard1.setFill(Color.RED.getPaint());

        Rectangle HandCard2 = new Rectangle(3, 3 + cardHeight + 3, cardWidth, cardHeight);
        HandCard2.setFill(Color.RED.getPaint());

        Rectangle HandCard3 = new Rectangle(3, 3 + cardHeight + 3 + cardHeight + 3, cardWidth, cardHeight);
        HandCard3.setFill(Color.RED.getPaint());


        // Add the static rectangle to the staticRoot group
        staticRoot.getChildren().add(ResourceCardsBox);
        staticRoot.getChildren().add(GoldCardsBox);
        staticRoot.getChildren().add(ResourceCard1);
        staticRoot.getChildren().add(ResourceCard2);
        staticRoot.getChildren().add(ResourceCard3);
        staticRoot.getChildren().add(GoldCard1);
        staticRoot.getChildren().add(GoldCar2);
        staticRoot.getChildren().add(GoldCard3);
        staticRoot.getChildren().add(HandBox);
        staticRoot.getChildren().add(HandCard1);
        staticRoot.getChildren().add(HandCard2);
        staticRoot.getChildren().add(HandCard3);


        /**
         * THE GRID
         */
        // Create a grid of barely visible rectangles
        int gridSize = 20;
        double rectangleWidth = cardWidth - 24;
        double rectangleHeight = cardHeight - 24;
        double opacity = 0.3;

        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                // Subtract half the grid size from the x and y coordinates to center the grid

                double x = (i - gridSize / 2.0) * rectangleWidth;
                double y = (j - gridSize / 2.0) * rectangleHeight; // Invert the y-coordinate

                Rectangle gridRectangle = new Rectangle(x, y, rectangleWidth + 24, rectangleHeight + 24);
                gridRectangle.setFill(Color.GREY.getPaint());
                gridRectangle.setOpacity(opacity);
                // Add an outline to the rectangle
                gridRectangle.setStroke(javafx.scene.paint.Color.LIGHTGRAY);
                gridRectangle.setStrokeWidth(2);

                // Create a label for the rectangle's coordinates
                Label label = new Label("(" + (i - gridSize / 2) + ", " + (gridSize / 2 - j) + ")"); // Invert the y-coordinate in the label
                label.setTranslateX(x);
                label.setTranslateY(y);

                // Add a mouse click event handler to the rectangle
                int finalJ = gridSize / 2 - j; // Invert the y-coordinate
                int finalI = i - gridSize / 2;
                if ((Math.abs(finalI) == Math.abs(finalJ)) || (finalI == finalJ) || (finalI % 2 == 0 && finalJ % 2 == 0) || ((finalI + finalJ) % 2 == 0)) {
                    gridRectangle.setOnMouseClicked(e -> {
                        System.out.println("Rectangle at (" + finalI + ", " + finalJ + ") was clicked!");

                        // TODO Send a message to the server with the card selected and the position
                        //TODO capire come usare questo sotto
                        //gridRectangle.addEventHandler(MouseEvent.MOUSE_CLICKED , this::drawCard);
                    });

                    movableRoot.getChildren().addAll(gridRectangle, label);
                }
            }

        }


        /**
         * THE CAMERA
         */
        // Create a translate transformation for the movable group
        Translate cameraTranslate = new Translate();
        movableRoot.getTransforms().add(cameraTranslate);

        Scene scene = new Scene(root, 1400, 900);

        // Add key listeners to the scene to move the camera
        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case W:
                    cameraTranslate.setY(cameraTranslate.getY() + 20);
                    break;
                case S:
                    cameraTranslate.setY(cameraTranslate.getY() - 20);
                    break;
                case A:
                    cameraTranslate.setX(cameraTranslate.getX() + 20);
                    break;
                case D:
                    cameraTranslate.setX(cameraTranslate.getX() - 20);
                    break;
            }
        });

        stageReference.setScene(scene);
        stageReference.sizeToScene();
        stageReference.show();

    }

    @Override
    public void drawCard(Card card) {
        Card cardToDraw = card;
        // Load the image
        InputStream is = getClass().getResourceAsStream("/cards_images/CODEX_cards_gold_front/427371a2-5897-4015-8c67-34dd8707c4ba-001.png");
        Image image = new Image(is);

        // Create the pattern
        ImagePattern imagePattern = new ImagePattern(image);

        // Create the rectangle and set the fill to the pattern
        Rectangle rect = new Rectangle(100, 100, 200, 200);
        rect.setFill(imagePattern);
    }

    //TODO
    private void onGridClick(MouseEvent event) {
        Node clickedNode = event.getPickResult().getIntersectedNode();

    }


}
