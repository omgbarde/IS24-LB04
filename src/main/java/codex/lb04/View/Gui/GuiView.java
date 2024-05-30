package codex.lb04.View.Gui;

import codex.lb04.Message.ChatMessage;
import codex.lb04.Message.DrawMessage.DrawBoardMessage;
import codex.lb04.Message.GameMessage.CreateGameMessage;
import codex.lb04.Message.GameMessage.EndTurnMessage;
import codex.lb04.Message.LoginMessage;
import codex.lb04.Model.Enumerations.Color;
import codex.lb04.Network.client.ClientSocket;
import codex.lb04.Utils.ConnectionUtil;
import codex.lb04.View.View;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * This class represents the view of the game in GUI mode
 */
public class GuiView extends View {
    //sizing constants
    private static final double cardWidth = 124;
    private static final double cardHeight = 82.5;
    private static final double resourceWidth = 50;
    private static final double resourceHeight = 50;

    private final Stage stageReference;
    private ClientSocket clientSocket;

    //GUI elements to be updated/set multiple times
    private final Label lobbyLabel;
    private TextArea chatText;
    private final Label winnerLabel;
    private final GuiController controller;
    private Group chatGroup;
    private Rectangle initCardBackground;
    private Text alert;
    private Text turnText;

    /**
     * Constructor for the GUI view
     *
     * @param stage the javafx stage
     */
    public GuiView(Stage stage) {
        this.alert = new Text("");
        this.lobbyLabel = new Label();
        this.chatText = new TextArea();
        this.winnerLabel = new Label();
        this.controller = new GuiController(this);
        this.stageReference = stage;
    }

    /**
     * method to instantiate the hello scene
     */
    @Override
    public void drawHelloScene() {
        //creating elements
        StackPane root = new StackPane();
        InputStream is = getClass().getResourceAsStream("/graphics/CODEX_wallpaper_minimal.png");

        ImageView imageView = createImageView(root, is);

        Button createGameButton = new Button("Create Game");
        createGameButton.setOnAction(actionEvent -> drawCreateGameScene());
        Button joinGameButton = new Button("Join Game");
        joinGameButton.setOnAction(actionEvent -> drawLoginScene());

        Label creditsLabel = new Label("by Pitesse, Barde, AlexIlLeone, Brio");
        creditsLabel.getStyleClass().remove("label");
        creditsLabel.getStyleClass().add("credits-text");

        //append elements to the root
        stageReference.setTitle("Codex! - Welcome");
        root.getChildren().add(imageView);
        root.getChildren().add(creditsLabel);

        creditsLabel.setTranslateY(200);
        joinGameButton.setTranslateY(50);
        joinGameButton.setTranslateY(120);
        joinGameButton.setTranslateX(50);
        createGameButton.setTranslateY(120);
        createGameButton.setTranslateX(-50);
        root.getChildren().addAll(createGameButton, joinGameButton);
        Scene scene = new Scene(root, 1520, 850);
        scene.getStylesheets().add("/codexTheme.css");
        stageReference.setScene(scene);
        stageReference.show();
    }

    /**
     * method to display an image
     *
     * @param root the stack pane
     * @param is   the input stream
     * @return the image view
     */
    private ImageView createImageView(StackPane root, InputStream is) {
        assert is != null;
        Image image = new Image(is);

        ImageView imageView = new ImageView(image);
        imageView.setImage(image);
        imageView.setPreserveRatio(true);

        imageView.setFitWidth(root.computeAreaInScreen());
        imageView.setFitHeight(root.computeAreaInScreen());
        return imageView;
    }

    /**
     * method to instantiate the login scene
     */
    @Override
    public void drawLoginScene() {
        //creating elements
        StackPane root = new StackPane();

        InputStream is = getClass().getResourceAsStream("/graphics/CODEX_wallpaper_pattern.png");
        ImageView imageView = createImageView(root, is);

        TextField usernameField = new TextField();
        usernameField.setPromptText("username");
        usernameField.setMaxWidth(200);

        TextField serverAddressField = new TextField();
        serverAddressField.setPromptText("server address");
        serverAddressField.setMaxWidth(200);

        TextField serverPortField = new TextField();
        serverPortField.setPromptText("server port");
        serverPortField.setMaxWidth(200);

        Button loginButton = new Button("Login");
        Button backButton = new Button("Back");

        Label titleLabel = new Label("insert your username, server address and server port");
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
                    clientSocket = new ClientSocket(usr, addr, port, this.controller);
                    controller.setClientSocket(clientSocket);
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

        backButton.setOnAction(actionEvent -> drawHelloScene());

        //append elements to the root
        stageReference.setTitle("Codex! - Login");

        titleLabel.setTranslateY(-200);
        usernameField.setTranslateY(-100);
        serverAddressField.setTranslateY(-50);
        serverPortField.setTranslateY(0);
        loginButton.setTranslateY(50);
        backButton.setTranslateY(50);
        errorLabel.setTranslateY(100);
        loginButton.setTranslateX(50);
        backButton.setTranslateX(-50);

        root.getChildren().addAll(imageView, titleLabel, usernameField, serverAddressField, serverPortField, loginButton, backButton, errorLabel);

        Scene scene = new Scene(root, 1520, 850);
        scene.getStylesheets().add("/codexTheme.css");
        stageReference.setScene(scene);
        stageReference.show();
    }

    /**
     * method to instantiate the lobby scene
     */
    @Override
    public void drawLobbyScene() {
        StackPane root = new StackPane();

        InputStream is = getClass().getResourceAsStream("/graphics/CODEX_wallpaper_pattern.png");
        ImageView imageView = createImageView(root, is);

        Label titleLabel = new Label("Players in the lobby");

        Button playButton = new Button("Play");
        Button backButton = new Button("Back");

        stageReference.setTitle("Codex! - Lobby");
        titleLabel.setTranslateY(-200);
        lobbyLabel.setTranslateY(-100);
        playButton.setTranslateY(50);
        playButton.setTranslateX(50);
        backButton.setTranslateY(50);
        backButton.setTranslateX(-50);

        backButton.setOnAction(actionEvent -> {
            clientSocket.disconnect();
            drawHelloScene();
        });

        playButton.setOnMouseClicked(actionEvent -> clientSocket.sendMessage(new DrawBoardMessage(clientSocket.getUsername())));

        root.getChildren().addAll(imageView, titleLabel, lobbyLabel, playButton, backButton);

        Scene scene = new Scene(root);
        scene.getStylesheets().add("/codexTheme.css");
        stageReference.setScene(scene);
        stageReference.show();

    }

    /**
     * method to update the lobby scene
     *
     * @param names the updated name list of the players in the lobby
     */
    @Override
    public void updateLobby(ArrayList<String> names) {
        StringBuilder sb = new StringBuilder();
        for (String name : names) {
            sb.append(name).append("\n");
        }
        lobbyLabel.setText(sb.toString());
    }

    /**
     * method to instantiate the game creation scene
     */
    @Override
    public void drawCreateGameScene() {
        //creating elements
        StackPane root = new StackPane();

        InputStream is = getClass().getResourceAsStream("/graphics/CODEX_wallpaper_pattern.png");
        ImageView imageView = createImageView(root, is);

        Label localHostLabel = new Label("Insert username, number of players\n" +
                "and server port to setup a game");

        TextField numPlayersChoice = new TextField();
        numPlayersChoice.setPromptText("number of players");
        numPlayersChoice.setMaxWidth(200);

        TextField usernameField = new TextField();
        usernameField.setPromptText("choose a username");
        usernameField.setMaxWidth(200);

        TextField portField = new TextField();
        portField.setPromptText("server port (refer to serverApp):");
        portField.setMaxWidth(200);

        Button confirmButton = new Button("Confirm");
        Button backButton = new Button("Back");
        Label errorLabel = new Label();


        //adding listeners
        confirmButton.setOnAction(actionEvent -> {
            int num;
            int port;
            try {
                num = Integer.parseInt(numPlayersChoice.getText());
            } catch (NumberFormatException e) {
                errorLabel.setText("Enter a valid number of players");
                return;
            }
            try {
                port = Integer.parseInt(portField.getText());
            } catch (NumberFormatException e) {
                errorLabel.setText("Enter a valid number as port");
                return;
            }
            String usr = usernameField.getText();
            confirmButton.setDisable(true);
            if (ConnectionUtil.checkValid(num, usr)) {
                if (ConnectionUtil.isValidPort(port)) {
                    try {
                        clientSocket = new ClientSocket(usr, ConnectionUtil.getLocalhost(), port, this.controller);
                        controller.setClientSocket(clientSocket);
                    } catch (IOException e) {
                        errorLabel.setText("Server not available");
                        confirmButton.setDisable(false);
                        return;
                    }
                } else {
                    try {
                        clientSocket = new ClientSocket(usr, ConnectionUtil.getLocalhost(), ConnectionUtil.defaultPort, this.controller);
                        controller.setClientSocket(clientSocket);
                    } catch (IOException e) {
                        errorLabel.setText("Server not available");
                        confirmButton.setDisable(false);
                        return;
                    }
                }
                clientSocket.sendMessage(new CreateGameMessage(usr, num));
            } else {
                errorLabel.setText("Invalid input");
                confirmButton.setDisable(false);
            }
        });
        backButton.setOnAction(actionEvent -> drawHelloScene());

        //append elements to the root
        stageReference.setTitle("Codex! - Create Game");

        localHostLabel.setTranslateY(-200);
        numPlayersChoice.setTranslateY(-50);
        portField.setTranslateY(0);
        usernameField.setTranslateY(-100);
        confirmButton.setTranslateY(50);
        backButton.setTranslateY(50);
        errorLabel.setTranslateY(100);
        confirmButton.setTranslateX(50);
        backButton.setTranslateX(-50);

        root.getChildren().addAll(imageView, localHostLabel, usernameField, numPlayersChoice, portField, confirmButton, errorLabel, backButton);

        Scene scene = new Scene(root, 1520, 850);
        scene.getStylesheets().add("/codexTheme.css");
        stageReference.setScene(scene);
        stageReference.show();
    }


    /**
     * method to instantiate the board scene
     */
    @Override
    public void drawBoardScene() {
        controller.reset();
        chatText = new TextArea();
        chatGroup = new Group();

        //border pane containing all
        BorderPane root = new BorderPane();
        //group for the movable grid
        Group movableRoot = new Group();

        //pane for the center of the window
        Pane center = new Pane();

        //Hbox containing turn text and end turn button
        HBox topBox = new HBox(50);
        HBox.setHgrow(topBox, Priority.ALWAYS);
        topBox.setBackground(new Background(new BackgroundFill(javafx.scene.paint.Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        topBox.setAlignment(Pos.CENTER);
        topBox.setMaxHeight(50);

        //Vbox containing cards in hand and buttons to chat and flip, positioned on the left
        VBox handBox = new VBox();
        VBox.setVgrow(handBox, Priority.ALWAYS);
        handBox.setBackground(new Background(new BackgroundFill(javafx.scene.paint.Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        handBox.setAlignment(Pos.CENTER);
        handBox.setMaxWidth(cardWidth);

        //Vbox containing drawable cards positioned on the right
        VBox visibleCardsBox = new VBox();
        VBox.setVgrow(handBox, Priority.ALWAYS);
        visibleCardsBox.setBackground(new Background(new BackgroundFill(javafx.scene.paint.Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        visibleCardsBox.setAlignment(Pos.TOP_RIGHT);
        visibleCardsBox.setMaxWidth(cardWidth);

        //Hbox containing points and objectives at the bottom of the screen
        HBox bottomBox = new HBox(1);
        HBox.setHgrow(bottomBox, Priority.ALWAYS);
        bottomBox.setBackground(new Background(new BackgroundFill(javafx.scene.paint.Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setMaxHeight(cardHeight);

        //sub-box containing resources and points
        HBox pointsBox = new HBox();
        HBox.setHgrow(pointsBox, Priority.ALWAYS);
        pointsBox.setAlignment(Pos.CENTER);
        pointsBox.setMaxHeight(resourceHeight);

        //sub-box containing objective cards
        HBox objectivesBox = new HBox();
        HBox.setHgrow(pointsBox, Priority.ALWAYS);
        objectivesBox.setAlignment(Pos.CENTER);
        objectivesBox.setMaxHeight(cardHeight);

        //button to flip selected card
        Button flipButton = new Button("flip card");
        flipButton.setMaxWidth(75);
        flipButton.setOnMouseClicked(e -> {
            try {
                controller.flipCard();
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });

        //button to end turn
        Button endTurnButton = new Button("end turn");
        endTurnButton.setMaxWidth(75);
        endTurnButton.setOnMouseClicked(e -> clientSocket.sendMessage(new EndTurnMessage(clientSocket.getUsername())));

        //button to toggle chat
        Button chatButton = new Button("Chat");
        chatButton.setMaxWidth(75);
        chatButton.setOnMouseClicked(e -> controller.toggleChat());

        BorderPane.setAlignment(movableRoot, Pos.CENTER);
        BorderPane.setAlignment(handBox, Pos.CENTER);
        BorderPane.setAlignment(visibleCardsBox, Pos.CENTER);
        BorderPane.setAlignment(endTurnButton, Pos.CENTER);
        BorderPane.setAlignment(objectivesBox, Pos.BOTTOM_LEFT);
        BorderPane.setAlignment(pointsBox, Pos.BOTTOM_RIGHT);
        BorderPane.setAlignment(endTurnButton, Pos.CENTER);
        BorderPane.setAlignment(bottomBox, Pos.CENTER);

        //setup center
        createGrid(movableRoot);
        center.getChildren().add(movableRoot);
        createChoiceBoxes(center);
        createAlertText(center);
        createChatBox(chatGroup, center);
        root.setCenter(center);

        //setup top
        createTurnText(topBox);
        topBox.getChildren().add(endTurnButton);
        root.setTop(topBox);

        //setup left
        createHandBox(handBox);
        handBox.getChildren().addAll(new Rectangle(cardWidth, 10), flipButton, new Rectangle(cardWidth, 10), chatButton);
        root.setLeft(handBox);

        //setup right
        createResourceCardsBox(visibleCardsBox);
        visibleCardsBox.getChildren().add(new Rectangle(cardWidth, 10));
        createGoldCardsBox(visibleCardsBox);
        root.setRight(visibleCardsBox);

        //setup bottom
        createObjectivesBox(bottomBox);
        bottomBox.getChildren().add(new Rectangle(50, cardHeight));
        createPointsBox(bottomBox);
        root.setBottom(bottomBox);

        Scene scene = new Scene(root);
        //set camera movement for the movable grid
        createCameraTranslate(scene, movableRoot);

        Platform.runLater(() -> {
            scene.getStylesheets().add("/codexTheme.css");
            stageReference.setTitle("Codex! - your board");
            stageReference.setScene(scene);
            stageReference.show();
        });
    }

    /**
     * sets the alert text with initial styling and controls tutorial
     *
     * @param centerRoot the center pane where the text is placed
     */
    private void createAlertText(Pane centerRoot) {
        this.alert = new Text("");
        displayTimedAlertText("""
                        GAME CONTROLS:
                        To select a card LEFT click on it
                        To flip a card select it and click the flip card button
                        To pick the initial card and secret objective RIGHT click on it
                        To draw a card click on it
                        To pass the turn click on the end turn button
                        To place a card select it and click where you want to place it
                        To move the camera use WASD""",
                25);
        centerRoot.getChildren().add(alert);
    }

    /**
     * creates the turn text
     *
     * @param topBox the HBox at the top of the screen where the text is placed
     */
    private void createTurnText(HBox topBox) {
        Text yourTurn = new Text("");
        yourTurn.getStyleClass().add("turn-text");
        yourTurn.setWrappingWidth(200);
        setYouTurnDisplay(yourTurn);
        topBox.getChildren().add(yourTurn);
    }

    /**
     * creates choice boxes for initial card and secret objectives
     *
     * @param centerRoot the center pane where the boxes are placed
     */
    private void createChoiceBoxes(Pane centerRoot) {
        Rectangle initialCardDisplay = new Rectangle(300, 250, cardWidth, cardHeight);
        //initialCardDisplay.setFill(Color.RED.getPaint());
        this.initCardBackground = initialCardDisplay;

        Rectangle secretObjectiveDisplay1 = new Rectangle(450, 200, cardWidth, cardHeight);
        //secretObjectiveDisplay1.setFill(Color.RED.getPaint());
        secretObjectiveDisplay1.setUserData(0);

        Rectangle secretObjectiveDisplay2 = new Rectangle(450, 300, cardWidth, cardHeight);
        //secretObjectiveDisplay2.setFill(Color.RED.getPaint());
        secretObjectiveDisplay2.setUserData(1);

        controller.setUpInitialCardDisplay(initialCardDisplay);
        controller.setUpSecretObjectivesToChoose(secretObjectiveDisplay1, secretObjectiveDisplay2);

        centerRoot.getChildren().addAll(initialCardDisplay, secretObjectiveDisplay1, secretObjectiveDisplay2);
    }

    /**
     * creates the chat box
     *
     * @param chatRoot   the group containing the chat elements
     * @param centerRoot the center pane where the chat box is placed
     */
    private void createChatBox(Group chatRoot, Pane centerRoot) {
        //background for the chat box
        Rectangle chatBox = new Rectangle(0, 0, 500, 500);
        chatBox.setFill(Color.BLACK.getPaint());

        chatText.getStyleClass().add("chat-text");
        chatText.setEditable(false);

        TextField messageField = new TextField();
        messageField.setPromptText("Type your message here");

        Button sendButton = new Button("Send");

        sendButton.setOnAction(actionEvent -> {
            String msg = messageField.getText();
            if (!msg.isEmpty()) {
                clientSocket.sendMessage(new ChatMessage(clientSocket.getUsername(), messageField.getText()));
            }
            messageField.clear();
        });

        //set spacing and position
        chatText.setPrefSize(400, 350);
        chatText.setTranslateX(40);
        chatText.setTranslateY(40);
        sendButton.setTranslateX(390);
        sendButton.setTranslateY(400);
        messageField.setMinWidth(300);
        messageField.setTranslateX(50);
        messageField.setTranslateY(400);

        chatRoot.getChildren().addAll(chatBox, chatText, messageField, sendButton);
        chatRoot.setVisible(false);

        centerRoot.getChildren().add(chatRoot);
    }

    /**
     * creates rectangles for the resource cards
     *
     * @param visibleCardsBox the VBox where the decks are placed
     */
    private void createResourceCardsBox(VBox visibleCardsBox) {
        //Display of the resource cards that can be drawn
        Rectangle ResourceCard1 = new Rectangle(0, 0, cardWidth, cardHeight);
        //ResourceCard1.setFill(Color.RED.getPaint());
        ResourceCard1.setUserData(0);

        Rectangle ResourceCard2 = new Rectangle(0, 0, cardWidth, cardHeight);
        //ResourceCard2.setFill(Color.RED.getPaint());
        ResourceCard2.setUserData(1);

        Rectangle ResourceCard3 = new Rectangle(0, 0, cardWidth, cardHeight);
        //ResourceCard3.setFill(Color.RED.getPaint());
        ResourceCard3.setUserData(2);

        controller.setUpDrawableResources(ResourceCard1, ResourceCard2, ResourceCard3);

        visibleCardsBox.getChildren().addAll(ResourceCard1, ResourceCard2, ResourceCard3);
    }

    /**
     * creates rectangles for the gold cards
     *
     * @param visibleCardsBox the VBox where the decks are placed
     */
    private void createGoldCardsBox(VBox visibleCardsBox) {
        //Display of the gold cards that can be drawn
        Rectangle GoldCard1 = new Rectangle(cardWidth, cardHeight);
        //GoldCard1.setFill(Color.RED.getPaint());
        GoldCard1.setUserData(0);

        Rectangle GoldCard2 = new Rectangle(cardWidth, cardHeight);
        //GoldCard2.setFill(Color.RED.getPaint());
        GoldCard2.setUserData(1);

        Rectangle GoldCard3 = new Rectangle(cardWidth, cardHeight);
        //GoldCard3.setFill(Color.RED.getPaint());
        GoldCard3.setUserData(2);

        controller.setUpDrawableGold(GoldCard1, GoldCard2, GoldCard3);

        visibleCardsBox.getChildren().addAll(GoldCard1, GoldCard2, GoldCard3);

    }

    /**
     * creates rectangles for the hand cards
     *
     * @param handBox the VBox where the hand is placed
     */
    private void createHandBox(VBox handBox) {
        //cards in hand
        Rectangle HandCard1 = new Rectangle(cardWidth, cardHeight);
        //HandCard1.setFill(Color.RED.getPaint());

        Rectangle HandCard2 = new Rectangle(cardWidth, cardHeight);
        //HandCard2.setFill(Color.RED.getPaint());

        Rectangle HandCard3 = new Rectangle(cardWidth, cardHeight);
        //HandCard3.setFill(Color.RED.getPaint());

        controller.setUpHandMap(HandCard1, HandCard2, HandCard3);

        handBox.getChildren().addAll(HandCard1, HandCard2, HandCard3);
    }

    /**
     * creates rectangles for the objective cards
     *
     * @param objectivesBox the HBox where the objectives are placed
     */
    private void createObjectivesBox(HBox objectivesBox) {
        //common objectives
        Rectangle commonObjective1 = new Rectangle(cardWidth, cardHeight);
        //commonObjective1.setFill(Color.RED.getPaint());

        Rectangle commonObjective2 = new Rectangle(cardWidth, cardHeight);
        //commonObjective2.setFill(Color.RED.getPaint());

        //secret objective
        Rectangle secretObjective = new Rectangle(cardWidth, cardHeight);
        //secretObjective.setFill(Color.RED.getPaint());
        //this.secretObjectiveBackground = secretObjective;

        controller.setUpCommonObjectivesMap(commonObjective1, commonObjective2);
        controller.setSecretObjectiveMap(secretObjective);

        objectivesBox.getChildren().addAll(commonObjective1, commonObjective2, secretObjective);
    }

    /**
     * creates the points box
     *
     * @param pointsBox the HBox where the points are placed
     */
    private void createPointsBox(HBox pointsBox) {
        //resources
        VBox mushroomBox = new VBox();
        mushroomBox.setAlignment(Pos.CENTER);
        StackPane mushroomStack = new StackPane();
        Rectangle mushrooms = new Rectangle(resourceWidth, resourceHeight);
        Rectangle mushrooms_points = new Rectangle(resourceWidth, cardHeight);
        Text mush_label = new Text("0");
        mush_label.getStyleClass().add("points-text");
        controller.addRectangleToPointsDisplay(mushrooms_points, mush_label);

        try {
            controller.setImageToRectangle("/board_icons/mushroom_icon.png", mushrooms);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        mushroomBox.getChildren().addAll(mushrooms, mush_label);
        mushroomStack.getChildren().addAll(mushrooms_points, mushroomBox);

        VBox animalsBox = new VBox();
        animalsBox.setAlignment(Pos.CENTER);
        StackPane animalStack = new StackPane();
        Rectangle animals = new Rectangle(resourceWidth, resourceHeight);
        Rectangle animals_points = new Rectangle(resourceWidth, cardHeight);
        Text animals_label = new Text("0");
        animals_label.getStyleClass().add("points-text");
        controller.addRectangleToPointsDisplay(animals_points, animals_label);

        try {
            controller.setImageToRectangle("/board_icons/animal_icon.png", animals);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        animalsBox.getChildren().addAll(animals, animals_label);
        animalStack.getChildren().addAll(animals_points, animalsBox);


        VBox insectBox = new VBox();
        insectBox.setAlignment(Pos.CENTER);
        StackPane insectStack = new StackPane();
        Rectangle insect = new Rectangle(resourceWidth, resourceHeight);
        Rectangle insect_points = new Rectangle(resourceWidth, cardHeight);
        Text insect_label = new Text("0");
        insect_label.getStyleClass().add("points-text");
        controller.addRectangleToPointsDisplay(insect_points, insect_label);

        try {
            controller.setImageToRectangle("/board_icons/insect_icon.png", insect);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        insectBox.getChildren().addAll(insect, insect_label);
        insectStack.getChildren().addAll(insect_points, insectBox);

        VBox leafBox = new VBox();
        leafBox.setAlignment(Pos.CENTER);
        StackPane leafStack = new StackPane();
        Rectangle leaves = new Rectangle(resourceWidth, resourceHeight);
        Rectangle leaves_points = new Rectangle(resourceWidth, cardHeight);
        Text leaves_label = new Text("0");
        leaves_label.getStyleClass().add("points-text");
        controller.addRectangleToPointsDisplay(leaves_points, leaves_label);

        try {
            controller.setImageToRectangle("/board_icons/leaf_icon.png", leaves);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        leafBox.getChildren().addAll(leaves, leaves_label);
        leafStack.getChildren().addAll(leaves_points, leafBox);

        VBox quillBox = new VBox();
        quillBox.setAlignment(Pos.CENTER);
        StackPane quillStack = new StackPane();
        Rectangle quills = new Rectangle(resourceWidth, resourceHeight);
        Rectangle quills_points = new Rectangle(resourceWidth, cardHeight);
        Text quills_label = new Text("0");
        quills_label.getStyleClass().add("points-text");
        controller.addRectangleToPointsDisplay(quills_points, quills_label);

        try {
            controller.setImageToRectangle("/board_icons/quill_icon.png", quills);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        quillBox.getChildren().addAll(quills, quills_label);
        quillStack.getChildren().addAll(quills_points, quillBox);

        VBox inkwellBox = new VBox();
        inkwellBox.setAlignment(Pos.CENTER);
        StackPane inkwellStack = new StackPane();
        Rectangle inkwells = new Rectangle(0, 0, resourceWidth, resourceHeight);
        Rectangle inkwells_points = new Rectangle(resourceWidth, cardHeight);
        Text inkwells_label = new Text("0");
        inkwells_label.getStyleClass().add("points-text");
        controller.addRectangleToPointsDisplay(inkwells_points, inkwells_label);

        try {
            controller.setImageToRectangle("/board_icons/inkwell_icon.png", inkwells);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        inkwellBox.getChildren().addAll(inkwells, inkwells_label);
        inkwellStack.getChildren().addAll(inkwells_points, inkwellBox);

        VBox manuscriptBox = new VBox();
        manuscriptBox.setAlignment(Pos.CENTER);
        StackPane manuscriptStack = new StackPane();
        Rectangle manuscript = new Rectangle(0, 0, resourceWidth, resourceHeight);
        Rectangle manuscript_points = new Rectangle(resourceWidth, cardHeight);
        Text manuscript_label = new Text("0");
        manuscript_label.getStyleClass().add("points-text");
        controller.addRectangleToPointsDisplay(manuscript_points, manuscript_label);

        try {
            controller.setImageToRectangle("/board_icons/manuscript_icon.png", manuscript);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        manuscriptBox.getChildren().addAll(manuscript, manuscript_label);
        manuscriptStack.getChildren().addAll(manuscript_points, manuscriptBox);

        VBox scoreBox = new VBox();
        scoreBox.setAlignment(Pos.CENTER);
        StackPane scoreStack = new StackPane();
        Rectangle pointsPadding = new Rectangle(0, 0, resourceWidth, 22);
        Rectangle Points_points = new Rectangle(resourceWidth, cardHeight);
        Text points_label = new Text("0");
        controller.addRectangleToPointsDisplay(Points_points, points_label);
        Text points_text = new Text("Points");
        points_label.getStyleClass().add("points-text");
        points_text.getStyleClass().add("points-text");
        scoreBox.getChildren().addAll(points_text, pointsPadding, points_label);
        scoreStack.getChildren().addAll(Points_points, scoreBox);

        pointsBox.getChildren().addAll(mushroomStack, animalStack, insectStack, leafStack, quillStack, inkwellStack, manuscriptStack, scoreStack);
    }

    /**
     * creates the grid of the board
     *
     * @param movableRoot the group containing the grid
     */
    private void createGrid(Group movableRoot) {
        int gridSize = 50;
        double rectangleWidth = cardWidth - 30;//change these values to change di distance between rectangles
        double rectangleHeight = cardHeight - 33;
        double opacity = 0;

        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                // Subtract half the grid size from the x and y coordinates to center the grid
                double x = (i - gridSize / 2.0) * rectangleWidth;
                double y = (j - gridSize / 2.0) * rectangleHeight;

                Rectangle gridRectangle = new Rectangle(x, y, cardWidth, cardHeight);

                gridRectangle.setFill(javafx.scene.paint.Color.GREY);
                gridRectangle.setOpacity(opacity);
                // Add an outline to the rectangle
                gridRectangle.setStroke(javafx.scene.paint.Color.BLACK);
                gridRectangle.setStrokeWidth(0.5);

                int finalJ = gridSize / 2 - j; // Invert the y-coordinate
                int finalI = i - gridSize / 2;

                if ((Math.abs(finalI) == Math.abs(finalJ)) || (finalI == finalJ) || (finalI % 2 == 0 && finalJ % 2 == 0) || ((finalI + finalJ) % 2 == 0)) {
                    ArrayList<Integer> coordinates = new ArrayList<>();
                    coordinates.add(finalI);
                    coordinates.add(finalJ);
                    gridRectangle.setUserData(coordinates);
                    gridRectangle.setOnMouseClicked(e -> System.out.println("Rectangle at (" + finalI + ", " + finalJ + ") was clicked!"));
                    controller.addRectangleToGridMap(gridRectangle);
                    movableRoot.getChildren().addAll(gridRectangle/*, label*/);
                }
            }
        }
    }

    /**
     * creates and sets the translation animation for the board
     *
     * @param scene       the scene where the animation is set
     * @param movableRoot the group containing the board
     */
    private void createCameraTranslate(Scene scene, Group movableRoot) {
        Translate cameraTranslate = new Translate();
        movableRoot.getTransforms().add(cameraTranslate);
        cameraTranslate.setX(350);
        cameraTranslate.setY(250);

        // Add key listeners to the scene to move the camera
        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case W:
                    if (cameraTranslate.getY() <= 1500) cameraTranslate.setY(cameraTranslate.getY() + 20);
                    break;
                case S:
                    if (cameraTranslate.getY() >= -1500) cameraTranslate.setY(cameraTranslate.getY() - 20);
                    break;
                case A:
                    if (cameraTranslate.getX() <= 2000) cameraTranslate.setX(cameraTranslate.getX() + 20);
                    break;
                case D:
                    if (cameraTranslate.getX() >= -2000) cameraTranslate.setX(cameraTranslate.getX() - 20);
                    break;
            }
        });
    }

    /**
     * method to instantiate the winners scene
     */
    @Override
    public void drawWinnerScene() {
        StackPane root = new StackPane();

        InputStream is = getClass().getResourceAsStream("/graphics/CODEX_wallpaper_pattern.png");
        ImageView imageView = createImageView(root, is);

        Button backButton = new Button("Back to Menu");

        //adding listeners
        backButton.setOnAction(actionEvent -> {
            clientSocket.disconnect();
            drawHelloScene();
        });

        stageReference.setTitle("Codex! - Game Ended");

        backButton.setTranslateY(50);
        root.getChildren().addAll(imageView, winnerLabel, backButton);

        Scene scene = new Scene(root);
        scene.getStylesheets().add("/codexTheme.css");
        stageReference.setScene(scene);
        stageReference.show();
    }

    /**
     * utility method to display an alert
     *
     * @param alert the alert string to be displayed
     */
    @Override
    public void displayAlert(String alert) {
        //show dialog box containing string alert
        displayTimedAlertText(alert, 7);
    }


    /**
     * method to set the text for the turn display in the board scene
     *
     * @param text the text to set in the turn display
     */
    public void setYouTurnDisplay(Text text) {
        this.turnText = text;
    }

    /**
     * method to display a timed alert text
     *
     * @param text  the text to display
     * @param timer the time the text will be displayed
     */
    private void displayTimedAlertText(String text, Integer timer) {
        this.alert.setText(text);
        alert.getStyleClass().add("alert-text");
        PauseTransition delay = new PauseTransition(Duration.seconds(timer));
        delay.setOnFinished(event -> alert.setText(""));
        alert.setX(100);
        alert.setY(50);
        delay.play();
    }

    /**
     * method that removes a rectangle node from the board and re adds it to bring it to the front
     *
     * @param rectangle the rectangle to bring to the front
     */
    public void bringRectangleToFront(Rectangle rectangle) {
        Group parent = (Group) rectangle.getParent();
        parent.getChildren().remove(rectangle);
        parent.getChildren().add(rectangle);
    }

    //GETTER

    /**
     * method to get the chat group
     *
     * @return the chat group reference
     */
    public Group getChatGroup() {
        return chatGroup;
    }

    /**
     * method to get the place to append the chat text
     *
     * @return the chat text
     */
    public TextArea getChatText() {
        return chatText;
    }

    /**
     * method to get the place to append the turn text
     *
     * @return the chat text
     */
    public Text getTurnText() {
        return turnText;
    }

    /**
     * method to get the background rectangle of the initial card
     *
     * @return the rectangle of the initial card
     */
    public Rectangle getInitCardBackground() {
        return initCardBackground;
    }

    /**
     * method to set the winner label
     *
     * @param winner the winner
     */
    public void setWinnerLabel(String winner) {
        winnerLabel.setText(winner);
    }
}