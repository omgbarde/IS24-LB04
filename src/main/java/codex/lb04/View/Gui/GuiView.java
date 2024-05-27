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
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
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
    double centerX = 1000 / 2.0;
    double centerY = 600 / 2.0;
    double cardWidth = 124;
    double cardHeight = 82.5;
    double resourceWidth = 50;
    double resourceHeight = 50;
    double stageWidth = 1000;
    double stageHeight = 600;

    private final Stage stageReference;
    private ClientSocket clientSocket;
    private final Label lobbyLabel;
    private TextArea chatText;
    private final Label winnerLabel;
    GuiController controller;

    Group movableRootReference;
    Group staticGroupReference;
    Group chatGroupReference;

    Rectangle initCardBackground;
    Rectangle secretObjectiveBackground;
    Text alert;
    Text turnText;

    /**
     * Constructor for the GUI view
     * @param stage the javafx stage
     */
    public GuiView(Stage stage) {
        stage.setHeight(600);
        stage.setWidth(1000);
        stage.setResizable(false);//leave it to false because boardScene will be bugged when resized
        stage.minHeightProperty().set(600);
        stage.minWidthProperty().set(1000);
        this.alert = new Text("");
        this.lobbyLabel = new Label();
        this.chatText = new TextArea();
        this.winnerLabel = new Label();
        this.controller = new GuiController(this);
        stageReference = stage;
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
        root.getChildren().add(createGameButton);
        root.getChildren().add(joinGameButton);
        Scene scene = new Scene(root, 1520, 850);
        scene.getStylesheets().add("/codexTheme.css");
        stageReference.setScene(scene);
        stageReference.show();

    }

    /**
     * method to display an image
     * @param root the stack pane
     * @param is the input stream
     * @return
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
                    clientSocket = new ClientSocket(usr, addr, port,this.controller);
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
        root.getChildren().add(imageView);
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

        stageReference.setTitle("Codex! - Lobby");
        root.getChildren().add(imageView);
        root.getChildren().add(titleLabel);
        root.getChildren().add(lobbyLabel);
        root.getChildren().add(playButton);
        root.getChildren().add(backButton);

        Scene scene = new Scene(root, stageWidth, stageHeight);
        scene.getStylesheets().add("/codexTheme.css");
        stageReference.setScene(scene);
        stageReference.show();

    }

    /**
     * method to update the lobby scene
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
                if(ConnectionUtil.isValidPort(port)){
                    try {
                        clientSocket = new ClientSocket(usr, ConnectionUtil.getLocalhost(), port ,this.controller);
                        controller.setClientSocket(clientSocket);
                    } catch (IOException e) {
                        errorLabel.setText("Server not available");
                        confirmButton.setDisable(false);
                        return;
                    }
                }
                else {
                    try {
                    clientSocket = new ClientSocket(usr, ConnectionUtil.getLocalhost(), ConnectionUtil.defaultPort ,this.controller);
                    controller.setClientSocket(clientSocket);
                    }
                    catch (IOException e) {
                        errorLabel.setText("Server not available");
                        confirmButton.setDisable(false);
                        return;
                    }
                }
                clientSocket.sendMessage(new CreateGameMessage(usr, num));
            }else {
                errorLabel.setText("Invalid input");
                confirmButton.setDisable(false);
            }
        });
        backButton.setOnAction(actionEvent -> drawHelloScene());

        //append elements to the root
        stageReference.setTitle("Codex! - Create Game");
        root.getChildren().add(imageView);
        root.getChildren().add(localHostLabel);
        localHostLabel.setTranslateY(-200);
        numPlayersChoice.setTranslateY(-50);
        portField.setTranslateY(0);
        usernameField.setTranslateY(-100);
        confirmButton.setTranslateY(50);
        backButton.setTranslateY(50);
        errorLabel.setTranslateY(100);
        confirmButton.setTranslateX(50);
        backButton.setTranslateX(-50);
        root.getChildren().add(usernameField);
        root.getChildren().add(numPlayersChoice);
        root.getChildren().add(portField);
        root.getChildren().add(confirmButton);
        root.getChildren().add(errorLabel);
        root.getChildren().add(backButton);

        Scene scene = new Scene(root, 1520, 850);
        scene.getStylesheets().add("/codexTheme.css");
        stageReference.setScene(scene);stageReference.show();
    }

    //TODO: refactor this method, it's too long and has repeated code
    /**
     * method to instantiate the board scene
     */
    @Override
    public void drawBoardScene() {
        controller.reset();
        // Create a group for static elements
        Group staticRoot = new Group();
        // Add static elements to staticRoot here

        // Create a group for movable elements
        Group movableRoot = new Group();
        movableRoot.setTranslateY(260);
        movableRoot.setTranslateX(415);

        // Create a group to hold both the static and movable groups
        Group root = new Group();
        Rectangle background = new Rectangle();
        background.setHeight(4000);
        background.setWidth(4000);
        background.setFill(javafx.scene.paint.Color.BLACK);
        root.getChildren().add(background);


        root.getChildren().addAll(movableRoot, staticRoot);

        Text alert = new Text("");
        alert.setFill(javafx.scene.paint.Color.WHITE);
        alert.setWrappingWidth(700);
        setAlert(alert);
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

        Text yourTurn = new Text("");
        yourTurn.setFill(javafx.scene.paint.Color.WHITE);
        yourTurn.setWrappingWidth(200);
        yourTurn.setX(stageWidth / 2 + 130);
        yourTurn.setY(20);
        setYouTurnDisplay(yourTurn);


        root.getChildren().add(alert);
        root.getChildren().add(yourTurn);

        setMovableRootReference(movableRoot);
        setStaticGroupReference(staticRoot);


        //STATIC PART OF THE STAGE

        // BOX THAT CONTAINS RESOURCE CARDS THAT CAN BE DRAWN
        double rectangleWidthOfResourceCardPicker = 130;
        double rectangleHeightOfResourceCardPicker = 259.5;
        Rectangle ResourceCardsBox = new Rectangle(stageWidth - rectangleWidthOfResourceCardPicker, 0, rectangleWidthOfResourceCardPicker, rectangleHeightOfResourceCardPicker);
        ResourceCardsBox.setFill(Color.BLACK.getPaint());

        //Display of the resource cards that can be drawn
        Rectangle ResourceCard1 = new Rectangle(stageWidth - cardWidth - 3, 3, cardWidth, cardHeight);
        //ResourceCard1.setFill(Color.RED.getPaint());
        ResourceCard1.setUserData(0);

        Rectangle ResourceCard2 = new Rectangle(stageWidth - cardWidth - 3, 3 + cardHeight + 3, cardWidth, cardHeight);
       // ResourceCard2.setFill(Color.RED.getPaint());
        ResourceCard2.setUserData(1);

        Rectangle ResourceCard3 = new Rectangle(stageWidth - cardWidth - 3, 3 + cardHeight + 3 + cardHeight + 3, cardWidth, cardHeight);
        //ResourceCard3.setFill(Color.RED.getPaint());
        ResourceCard3.setUserData(2);

        controller.setUpDrawableResources(ResourceCard1, ResourceCard2, ResourceCard3);


        // BOX THAT CONTAINS GOLD CARDS THAT CAN BE DRAWN
        double rectangleWidthOfGoldCardPicker = 130;
        double rectangleHeightOfGoldCardPicker = 259.5;
        Rectangle GoldCardsBox = new Rectangle(stageWidth - rectangleWidthOfGoldCardPicker, 270, rectangleWidthOfGoldCardPicker, rectangleHeightOfGoldCardPicker);
        //GoldCardsBox.setFill(Color.BLACK.getPaint());

        //Display of the resource cards that can be drawn
        Rectangle GoldCard1 = new Rectangle(stageWidth - cardWidth - 3, 270 + 3, cardWidth, cardHeight);
        //GoldCard1.setFill(Color.RED.getPaint());
        GoldCard1.setUserData(0);


        Rectangle GoldCard2 = new Rectangle(stageWidth - cardWidth - 3, 270 + 3 + cardHeight + 3, cardWidth, cardHeight);
        //GoldCard2.setFill(Color.RED.getPaint());
        GoldCard2.setUserData(1);


        Rectangle GoldCard3 = new Rectangle(stageWidth - cardWidth - 3, 270 + 3 + cardHeight + 3 + cardHeight + 3, cardWidth, cardHeight);
        //GoldCard3.setFill(Color.RED.getPaint());
        GoldCard3.setUserData(2);
        controller.setUpDrawableGold(GoldCard1, GoldCard2, GoldCard3);

        // HAND BOX
        double rectangleWidthHand = 130;
        double rectangleHeightHand = 259.5;
        Rectangle HandBox = new Rectangle(0, 0, rectangleWidthHand, rectangleHeightHand);
        HandBox.setFill(Color.BLACK.getPaint());

        //cards in hand
        Rectangle HandCard1 = new Rectangle(3, 3, cardWidth, cardHeight);
        //HandCard1.setFill(Color.RED.getPaint());

        Rectangle HandCard2 = new Rectangle(3, 3 + cardHeight + 3, cardWidth, cardHeight);
        //HandCard2.setFill(Color.RED.getPaint());

        Rectangle HandCard3 = new Rectangle(3, 3 + cardHeight + 3 + cardHeight + 3, cardWidth, cardHeight);
        //HandCard3.setFill(Color.RED.getPaint());

        controller.setUpHandMap(HandCard1, HandCard2, HandCard3);

        // COMMON OBJECTIVES BOX
        double rectangleWidthCommonObjectives = cardWidth * 2 + 3 * 3;
        double rectangleHeightCommonObjectives = cardHeight + 6;
        Rectangle CommonObjectivesBox = new Rectangle(0, stageHeight - rectangleHeightCommonObjectives, rectangleWidthCommonObjectives, rectangleHeightCommonObjectives);
        CommonObjectivesBox.setFill(Color.BLACK.getPaint());

        //common objectives
        Rectangle CommonObjective1 = new Rectangle(3, stageHeight - cardHeight - 3, cardWidth, cardHeight);
        //CommonObjective1.setFill(Color.RED.getPaint());

        Rectangle CommonObjective2 = new Rectangle(3 + cardWidth + 3, stageHeight - cardHeight - 3, cardWidth, cardHeight);
        //CommonObjective2.setFill(Color.RED.getPaint());

        controller.setUpCommonObjectivesMap(CommonObjective1, CommonObjective2);

        // SECRET OBJECTIVE BOX
        double rectangleWidthSecretObjective = cardWidth + 6;
        double rectangleHeightSecretObjective = cardHeight + 6;
        Rectangle SecretObjectiveBox = new Rectangle(0 + rectangleWidthCommonObjectives + 5, stageHeight - rectangleHeightSecretObjective, rectangleWidthSecretObjective, rectangleHeightSecretObjective);
        SecretObjectiveBox.setFill(Color.BLACK.getPaint());


        //secret objective
        Rectangle SecretObjective = new Rectangle(0 + rectangleWidthCommonObjectives + 5 + 3, stageHeight - cardHeight - 3, cardWidth, cardHeight);
        //SecretObjective.setFill(Color.RED.getPaint());
        controller.setSecretObjectiveMap(SecretObjective);

        //INITIAL CARD DISPLAY FOR FACE SELECTION
        double rectangleWidthInitialCardDisplay = cardWidth + 6;
        double rectangleHeightInitialCardDisplay = cardHeight + 6;
        Rectangle InitialCardDisplayBox = new Rectangle(stageWidth / 2 - rectangleWidthInitialCardDisplay - 50, stageHeight / 2 - rectangleHeightInitialCardDisplay, rectangleWidthInitialCardDisplay, rectangleHeightInitialCardDisplay);
        InitialCardDisplayBox.setFill(Color.BLACK.getPaint());
        this.initCardBackground = InitialCardDisplayBox;

        Rectangle InitialCardDisplay = new Rectangle(stageWidth / 2 - rectangleWidthInitialCardDisplay - 50 + 3, stageHeight / 2 - rectangleHeightInitialCardDisplay + 3, cardWidth, cardHeight);
        //InitialCardDisplay.setFill(Color.RED.getPaint());
        controller.setUpInitialCardDisplay(InitialCardDisplay);

        //SECRET OBJECTIVES DISPLAY FOR SELECTION
        double rectangleWidthSecretObjectiveDisplay = cardWidth + 6;
        double rectangleHeightObjectiveCardsDisplay = 2 * cardHeight + 9;
        Rectangle secretObjectivesDisplayBox = new Rectangle(stageWidth / 2 + rectangleWidthSecretObjectiveDisplay - 60, stageHeight / 2 - rectangleHeightObjectiveCardsDisplay / 2 - rectangleHeightObjectiveCardsDisplay / 4, rectangleWidthSecretObjectiveDisplay, rectangleHeightObjectiveCardsDisplay);
        secretObjectivesDisplayBox.setFill(Color.BLACK.getPaint());
        this.secretObjectiveBackground = secretObjectivesDisplayBox;

        Rectangle secretObjective1Display = new Rectangle(stageWidth / 2 + rectangleWidthSecretObjectiveDisplay - 60 + 3, stageHeight / 2 - rectangleHeightObjectiveCardsDisplay / 2 - rectangleHeightObjectiveCardsDisplay / 4 + 3, cardWidth, cardHeight);
        //secretObjective1Display.setFill(Color.RED.getPaint());
        secretObjective1Display.setUserData(0);

        Rectangle secretObjective2Display = new Rectangle(stageWidth / 2 + rectangleWidthSecretObjectiveDisplay - 60 + 3, stageHeight / 2 - rectangleHeightObjectiveCardsDisplay / 2 - rectangleHeightObjectiveCardsDisplay / 4 + 3 + cardHeight + 3, cardWidth, cardHeight);
        //secretObjective2Display.setFill(Color.RED.getPaint());
        secretObjective2Display.setUserData(1);
        controller.setUpSecretObjectivesToChoose(secretObjective1Display, secretObjective2Display);


        //Button to flip the card selected
        Button flipButton = new Button("flip card");
        flipButton.setTextFill(javafx.scene.paint.Color.WHITE);
        flipButton.setLayoutX(10);
        flipButton.setLayoutY(265);
        flipButton.setMaxHeight(10);
        flipButton.setMaxWidth(75);
        flipButton.setBackground(Background.fill(javafx.scene.paint.Color.BLACK));
        flipButton.setOnMouseClicked(e -> {
            try {
                controller.flipCard();
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });

        //Button end turn
        Button endTurnButton = new Button("end turn");
        endTurnButton.setTextFill(javafx.scene.paint.Color.WHITE);
        endTurnButton.setLayoutX(centerX - 37.5);
        endTurnButton.setLayoutY(5);
        endTurnButton.setMaxHeight(10);
        endTurnButton.setMaxWidth(75);
        endTurnButton.setBackground(Background.fill(javafx.scene.paint.Color.BLACK));
        endTurnButton.setOnMouseClicked(e -> clientSocket.sendMessage(new EndTurnMessage(clientSocket.getUsername())));

        //Chat Button
        Button chatButton = new Button("Chat");
        chatButton.setLayoutX(10);
        chatButton.setLayoutY(295);
        chatButton.setMaxHeight(10);
        chatButton.setMaxWidth(75);
        chatButton.setOnMouseClicked(e -> controller.toggleChat());

        // Chat group (can be toggled)
        chatText = new TextArea();
        Group chatRoot = new Group();
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

        chatBox.setLayoutX(centerX - chatBox.getWidth() / 2);
        chatBox.setLayoutY(centerY - chatBox.getHeight() / 2 - 150);
        chatText.setPrefSize(400, 350);
        chatText.setLayoutX(centerX - 200);
        chatText.setLayoutY(centerY-300);
        messageField.setLayoutX(centerX - 100);
        messageField.setLayoutY(350);
        sendButton.setLayoutX(centerX + 50);
        sendButton.setLayoutY(350);

        chatRoot.getChildren().add(chatBox);
        chatRoot.getChildren().add(chatText);
        chatRoot.getChildren().add(messageField);
        chatRoot.getChildren().add(sendButton);

        chatRoot.setVisible(false);
        setChatGroupReference(chatRoot);

        //resources & points box
        double rectanglePointsBoxWidth = 422.5;
        Rectangle PointsBox = new Rectangle(0 + rectangleWidthCommonObjectives + rectangleWidthSecretObjective + 20, stageHeight - rectangleHeightSecretObjective, rectanglePointsBoxWidth, rectangleHeightSecretObjective);

        PointsBox.setFill(Color.BLACK.getPaint());

        //resources

        Rectangle mushrooms = new Rectangle(0 + rectangleWidthCommonObjectives + rectangleWidthSecretObjective + 20 + 2.5, stageHeight - rectangleHeightSecretObjective + 3, resourceWidth, resourceHeight);
        Rectangle mushrooms_points = new Rectangle(0 + rectangleWidthCommonObjectives + rectangleWidthSecretObjective + 20 + 2.5, stageHeight - rectangleHeightSecretObjective + resourceHeight + 3, resourceWidth, resourceHeight);
        Text mush_label = new Text("0");
        mush_label.setFill(javafx.scene.paint.Color.WHITE);
        mush_label.setFont(new Font("Nimbus Roman", 20));
        mush_label.setLayoutX(15 + rectangleWidthCommonObjectives + rectangleWidthSecretObjective + 20 + 2.5 + 5);
        mush_label.setLayoutY(stageHeight - rectangleHeightSecretObjective + resourceHeight + 3 + 20);
        controller.addRectangleToPointsDisplay(mushrooms_points, mush_label);

        try {
            controller.setImageToRectangle("/board_icons/mushroom_icon.png", mushrooms);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        Rectangle animals = new Rectangle(0 + rectangleWidthCommonObjectives + rectangleWidthSecretObjective + 20 + 5 + resourceWidth, stageHeight - rectangleHeightSecretObjective + 3, resourceWidth, resourceHeight);
        Rectangle animals_points = new Rectangle(0 + rectangleWidthCommonObjectives + rectangleWidthSecretObjective + 20 + 5 + resourceWidth, stageHeight - rectangleHeightSecretObjective + resourceHeight + 3, resourceWidth, resourceHeight);
        Text animals_label = new Text("0");
        animals_label.setFont(new Font("Nimbus Roman", 20));
        animals_label.setFill(javafx.scene.paint.Color.WHITE);
        animals_label.setLayoutX(15 + rectangleWidthCommonObjectives + rectangleWidthSecretObjective + 20 + 5 + resourceWidth + 5);
        animals_label.setLayoutY(stageHeight - rectangleHeightSecretObjective + resourceHeight + 3 + 20);
        controller.addRectangleToPointsDisplay(animals_points, animals_label);

        try {
            controller.setImageToRectangle("/board_icons/animal_icon.png", animals);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        Rectangle insect = new Rectangle(0 + rectangleWidthCommonObjectives + rectangleWidthSecretObjective + 20 + 7.5 + resourceWidth * 2, stageHeight - rectangleHeightSecretObjective + 3, resourceWidth, resourceHeight);
        Rectangle insect_points = new Rectangle(0 + rectangleWidthCommonObjectives + rectangleWidthSecretObjective + 20 + 7.5 + resourceWidth * 2, stageHeight - rectangleHeightSecretObjective + resourceHeight + 3, resourceWidth, resourceHeight);
        Text insect_label = new Text("0");
        insect_label.setFont(new Font("Nimbus Roman", 20));
        insect_label.setFill(javafx.scene.paint.Color.WHITE);
        insect_label.setLayoutX(15 + rectangleWidthCommonObjectives + rectangleWidthSecretObjective + 20 + 7.5 + resourceWidth * 2 + 5);
        insect_label.setLayoutY(stageHeight - rectangleHeightSecretObjective + resourceHeight + 3 + 20);
        controller.addRectangleToPointsDisplay(insect_points, insect_label);


        try {
            controller.setImageToRectangle("/board_icons/insect_icon.png", insect);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        Rectangle leaves = new Rectangle(0 + rectangleWidthCommonObjectives + rectangleWidthSecretObjective + 20 + 10 + resourceWidth * 3, stageHeight - rectangleHeightSecretObjective + 3, resourceWidth, resourceHeight);
        Rectangle leaves_points = new Rectangle(0 + rectangleWidthCommonObjectives + rectangleWidthSecretObjective + 20 + 10 + resourceWidth * 3, stageHeight - rectangleHeightSecretObjective + resourceHeight + 3, resourceWidth, resourceHeight);
        Text leaves_label = new Text("0");
        leaves_label.setFont(new Font("Nimbus Roman", 20));
        leaves_label.setFill(javafx.scene.paint.Color.WHITE);
        leaves_label.setLayoutX(15 + rectangleWidthCommonObjectives + rectangleWidthSecretObjective + 20 + 10 + resourceWidth * 3 + 5);
        leaves_label.setLayoutY(stageHeight - rectangleHeightSecretObjective + resourceHeight + 3 + 20);
        controller.addRectangleToPointsDisplay(leaves_points, leaves_label);

        try {
            controller.setImageToRectangle("/board_icons/leaf_icon.png", leaves);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        Rectangle quills = new Rectangle(0 + rectangleWidthCommonObjectives + rectangleWidthSecretObjective + 20 + 12.5 + resourceWidth * 4, stageHeight - rectangleHeightSecretObjective + 3, resourceWidth, resourceHeight);
        Rectangle quills_points = new Rectangle(0 + rectangleWidthCommonObjectives + rectangleWidthSecretObjective + 20 + 12.5 + resourceWidth * 4, stageHeight - rectangleHeightSecretObjective + resourceHeight + 3, resourceWidth, resourceHeight);
        Text quills_label = new Text("0");
        quills_label.setFont(new Font("Nimbus Roman", 20));
        quills_label.setFill(javafx.scene.paint.Color.WHITE);
        quills_label.setLayoutX(15 + rectangleWidthCommonObjectives + rectangleWidthSecretObjective + 20 + 12.5 + resourceWidth * 4 + 5);
        quills_label.setLayoutY(stageHeight - rectangleHeightSecretObjective + resourceHeight + 3 + 20);
        controller.addRectangleToPointsDisplay(quills_points, quills_label);

        try {
            controller.setImageToRectangle("/board_icons/quill_icon.png", quills);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        Rectangle inkwells = new Rectangle(0 + rectangleWidthCommonObjectives + rectangleWidthSecretObjective + 20 + 15 + resourceWidth * 5, stageHeight - rectangleHeightSecretObjective + 3, resourceWidth, resourceHeight);
        Rectangle inkwells_points = new Rectangle(0 + rectangleWidthCommonObjectives + rectangleWidthSecretObjective + 20 + 15 + resourceWidth * 5, stageHeight - rectangleHeightSecretObjective + resourceHeight + 3, resourceWidth, resourceHeight);
        Text inkwells_label = new Text("0");
        inkwells_label.setFont(new Font("Nimbus Roman", 20));
        inkwells_label.setFill(javafx.scene.paint.Color.WHITE);
        inkwells_label.setLayoutX(15 + rectangleWidthCommonObjectives + rectangleWidthSecretObjective + 20 + 15 + resourceWidth * 5 + 5);
        inkwells_label.setLayoutY(stageHeight - rectangleHeightSecretObjective + resourceHeight + 3 + 20);
        controller.addRectangleToPointsDisplay(inkwells_points, inkwells_label);

        try {
            controller.setImageToRectangle("/board_icons/inkwell_icon.png", inkwells);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        Rectangle manuscript = new Rectangle(0 + rectangleWidthCommonObjectives + rectangleWidthSecretObjective + 20 + 17.5 + resourceWidth * 6, stageHeight - rectangleHeightSecretObjective + 3, resourceWidth, resourceHeight);
        Rectangle manuscript_points = new Rectangle(0 + rectangleWidthCommonObjectives + rectangleWidthSecretObjective + 20 + 17.5 + resourceWidth * 6, stageHeight - rectangleHeightSecretObjective + resourceHeight + 3, resourceWidth, resourceHeight);
        Text manuscript_label = new Text("0");
        manuscript_label.setFont(new Font("Nimbus Roman", 20));
        manuscript_label.setFill(javafx.scene.paint.Color.WHITE);
        manuscript_label.setLayoutX(15 + rectangleWidthCommonObjectives + rectangleWidthSecretObjective + 20 + 17.5 + resourceWidth * 6 + 5);
        manuscript_label.setLayoutY(stageHeight - rectangleHeightSecretObjective + resourceHeight + 3 + 20);
        controller.addRectangleToPointsDisplay(manuscript_points, manuscript_label);

        try {
            controller.setImageToRectangle("/board_icons/manuscript_icon.png", manuscript);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        Rectangle points = new Rectangle(0 + rectangleWidthCommonObjectives + rectangleWidthSecretObjective + 20 + 20 + resourceWidth * 7, stageHeight - rectangleHeightSecretObjective + 3, resourceWidth, resourceHeight);
        Rectangle Points_points = new Rectangle(0 + rectangleWidthCommonObjectives + rectangleWidthSecretObjective + 20 + 20 + resourceWidth * 7, stageHeight - rectangleHeightSecretObjective + resourceHeight + 3, resourceWidth, resourceHeight);
        Text points_label = new Text("0");
        points_label.setFont(new Font("Nimbus Roman", 20));
        points_label.setFill(javafx.scene.paint.Color.WHITE);
        points_label.setLayoutX(15 + rectangleWidthCommonObjectives + rectangleWidthSecretObjective + 20 + 20 + resourceWidth * 7 + 5);
        points_label.setLayoutY(stageHeight - rectangleHeightSecretObjective + resourceHeight + 3 + 20);
        controller.addRectangleToPointsDisplay(Points_points, points_label);
        Text points_text = new Text("Points");
        points_text.setFont(new Font("Nimbus Roman", 20));
        points_text.setX(rectangleWidthCommonObjectives + rectangleWidthSecretObjective + 18 + 20 + resourceWidth * 7 + 5);
        points_text.setY(stageHeight - rectangleHeightSecretObjective + resourceHeight - 12);
        points_text.setFill(javafx.scene.paint.Color.WHITE);
        //points.setFill(Color.RED.getPaint());

        // Add the static rectangle to the staticRoot group
        staticRoot.getChildren().add(ResourceCardsBox);
        staticRoot.getChildren().add(GoldCardsBox);
        staticRoot.getChildren().add(CommonObjectivesBox);
        staticRoot.getChildren().add(SecretObjectiveBox);
        staticRoot.getChildren().add(HandBox);
        staticRoot.getChildren().add(PointsBox);
        staticRoot.getChildren().add(InitialCardDisplayBox);
        staticRoot.getChildren().add(secretObjectivesDisplayBox);


        staticRoot.getChildren().add(ResourceCard1);
        staticRoot.getChildren().add(ResourceCard2);
        staticRoot.getChildren().add(ResourceCard3);

        staticRoot.getChildren().add(GoldCard1);
        staticRoot.getChildren().add(GoldCard2);
        staticRoot.getChildren().add(GoldCard3);

        staticRoot.getChildren().add(HandCard1);
        staticRoot.getChildren().add(HandCard2);
        staticRoot.getChildren().add(HandCard3);

        staticRoot.getChildren().add(CommonObjective1);
        staticRoot.getChildren().add(CommonObjective2);

        staticRoot.getChildren().add(SecretObjective);

        staticRoot.getChildren().add(InitialCardDisplay);

        staticRoot.getChildren().add(secretObjective1Display);
        staticRoot.getChildren().add(secretObjective2Display);

        staticRoot.getChildren().add(flipButton);
        staticRoot.getChildren().add(endTurnButton);
        staticRoot.getChildren().add(chatButton);

        staticRoot.getChildren().add(mushrooms);
        staticRoot.getChildren().add(leaves);
        staticRoot.getChildren().add(insect);
        staticRoot.getChildren().add(manuscript);
        staticRoot.getChildren().add(inkwells);
        staticRoot.getChildren().add(animals);
        staticRoot.getChildren().add(quills);
        staticRoot.getChildren().add(points);


        staticRoot.getChildren().add(mushrooms_points);
        staticRoot.getChildren().add(leaves_points);
        staticRoot.getChildren().add(insect_points);
        staticRoot.getChildren().add(manuscript_points);
        staticRoot.getChildren().add(inkwells_points);
        staticRoot.getChildren().add(animals_points);
        staticRoot.getChildren().add(quills_points);
        staticRoot.getChildren().add(Points_points);
        staticRoot.getChildren().add(mush_label);
        staticRoot.getChildren().add(leaves_label);
        staticRoot.getChildren().add(insect_label);
        staticRoot.getChildren().add(manuscript_label);
        staticRoot.getChildren().add(inkwells_label);
        staticRoot.getChildren().add(animals_label);
        staticRoot.getChildren().add(quills_label);
        staticRoot.getChildren().add(points_label);
        staticRoot.getChildren().add(points_text);

        staticRoot.getChildren().add(chatRoot);

        //THE GRID

        int gridSize = 20;
        double rectangleWidth = cardWidth - 30;//change these values to change di distance between rectangles
        double rectangleHeight = cardHeight - 33;
        double opacity = 0;

        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                // Subtract half the grid size from the x and y coordinates to center the grid
                double x = (i - gridSize / 2.0) * rectangleWidth;
                double y = (j - gridSize / 2.0) * rectangleHeight;

                Rectangle gridRectangle = new Rectangle(x, y, cardWidth, cardHeight);

                gridRectangle.setFill(Color.GREY.getPaint());
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

        //THE CAMERA

        // Create a translation transform for the movable group
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
            Platform.runLater(()-> {
                scene.getStylesheets().add("/codexTheme.css");
                stageReference.setTitle("Codex! - your board");
                stageReference.setScene(scene);
                stageReference.setHeight(stageHeight + 37);
                stageReference.setWidth(stageWidth);
                stageReference.show();
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
        backButton.setTranslateY(50);

        backButton.setOnAction(actionEvent -> {
            clientSocket.disconnect();
            drawHelloScene();
        });

        stageReference.setTitle("Codex! - Game Ended");
        root.getChildren().add(imageView);
        root.getChildren().add(winnerLabel);
        root.getChildren().add(backButton);

        Scene scene = new Scene(root, stageWidth, stageHeight);
        scene.getStylesheets().add("/codexTheme.css");
        stageReference.setScene(scene);
        stageReference.show();
    }

    /**
     * utility method to display an alert
     * @param alert the alert string to be displayed
     */
    @Override
    public void displayAlert(String alert) {
        //show dialog box containing string alert
         displayTimedAlertText(alert, 7);
    }

    /**
     * method to set the static group reference for the board scene
     * @param staticGroupReference the static group of the board scene
     */
    private void setStaticGroupReference(Group staticGroupReference) {
        this.staticGroupReference = staticGroupReference;
    }

    /**
     * method to set the movable root reference for the board scene
     * @param movableGroupReference the movable group of the board scene
     */
    private void setMovableRootReference(Group movableGroupReference) {
        this.movableRootReference = movableGroupReference;
    }

    /**
     * method to set the chat group reference for the board scene
     * @param chatGroup the chat group of the board scene
     */
    private void setChatGroupReference(Group chatGroup) {
        this.chatGroupReference = chatGroup;
    }

    /**
     * method to set the alert text for the board scene
     * @param alert the alert text to set in the board scene
     */
    public void setAlert(Text alert) {
        this.alert = alert;
    }

    /**
     * method to set the text for the turn display in the board scene
     * @param text the text to set in the turn display
     */
    public void setYouTurnDisplay(Text text) {
        this.turnText = text;
    }

    /**
     * method to display a timed alert text
     * @param text the text to display
     * @param timer the time the text will be displayed
     */
    private void displayTimedAlertText(String text, Integer timer) {
        this.alert.setText(text);
        double stageWidth = stageReference.getWidth();
        double textWidth = alert.getLayoutBounds().getWidth();

        double centerX = (stageWidth - textWidth) / 2;

        PauseTransition delay = new PauseTransition(Duration.seconds(timer));
        delay.setOnFinished(event -> alert.setText(""));
        this.alert.setX(centerX);
        this.alert.setY(50);
        delay.play();
    }

    /**
     * method to get the background rectangle of the secret objective
     * @return the rectangle of the secret objective
     */
    public Rectangle getSecretObjectiveBackground() {
        return secretObjectiveBackground;
    }

    /**
     * method to get the background rectangle of the initial card
     * @return the rectangle of the initial card
     */
    public Rectangle getInitCardBackground() {
        return initCardBackground;
    }

    /**
     * method that removes a rectangle node from the board and re adds it to bring it to the front
     * @param rectangle the rectangle to bring to the front
     */
    public void bringRectangleToFront(Rectangle rectangle) {
        Group parent = (Group) rectangle.getParent();
        parent.getChildren().remove(rectangle);
        parent.getChildren().add(rectangle);
    }

    //GETTER

    /**
     * method to get the client socket
     * @return the client socket
     */
    public ClientSocket getClientSocket() {
        return clientSocket;
    }

    /**
     * method to get the chat group
     * @return the chat group reference
     */
    public Group getChatGroup() {
        return chatGroupReference;
    }

    /**
     * method to get the place to append the chat text
     * @return the chat text
     */
    public TextArea getChatText() {
        return chatText;
    }

    /**
     * method to get the place to append the turn text
     * @return the chat text
     */
    public Text getTurnText() {
        return turnText;
    }

    /**
     * method to set the winner label
     * @param winner the winner
     */
    public void setWinnerLabel(String winner){
        winnerLabel.setText(winner);
    }
}