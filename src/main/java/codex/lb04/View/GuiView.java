package codex.lb04.View;

import codex.lb04.Message.DrawMessage.DrawBoardMessage;
import codex.lb04.Message.GameMessage.CreateGameMessage;
import codex.lb04.Message.GameMessage.EndTurnMessage;
import codex.lb04.Message.LoginMessage;
import codex.lb04.Model.*;
import codex.lb04.Model.Enumerations.Color;
import codex.lb04.Network.client.ClientSocket;
import codex.lb04.Utils.ConnectionUtil;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
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
 * class that represents the GUI view
 */
public class GuiView extends View {
    private Stage stageReference;
    private ClientSocket clientSocket;
    private Label lobbyLabel = new Label();
    BoardSceneController bsc;

    private ArrayList<Text> points_display = new ArrayList<>();

    double centerX = 1000 / 2.0;
    double centerY = 600 / 2.0;
    double cardWidth = 124;
    double cardHeight = 82.5;
    double resourceWidth = 50;
    double resourceHeigth = 50;
    double stageWidth = 1000;
    double stageHeigth = 600;

    Group movableRootReference;
    Group staticGroupReference;

    Rectangle initCardBackground;
    Rectangle secretObjectivesBackground;
    Text alert;
    Text yourTurn;

    public GuiView(Stage stage) {
        stage.setHeight(600);
        stage.setWidth(1000);
        stage.setResizable(false);//leave it to false because boardScene will be bugged when resized
        stageReference = stage;

        bsc = new BoardSceneController(this);
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
        imageView.setFitHeight(root.computeAreaInScreen());

        Button createGameButton = new Button("Create Game");
        createGameButton.setOnAction(actionEvent -> {
            drawCreateGameScene();
        });
        Button joinGameButton = new Button("Join Game");
        joinGameButton.setOnAction(actionEvent -> drawLoginScene());
        Label titleLabel = new Label("Codex naturalis");
        //append elements to the root
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                stageReference.setTitle("Codex! - Welcome");
                root.getChildren().add(imageView);
                root.getChildren().add(titleLabel);
                titleLabel.setTranslateY(-200);
                joinGameButton.setTranslateY(50);
                root.getChildren().add(createGameButton);

                root.getChildren().add(joinGameButton);
                Scene scene = new Scene(root, 1520, 850);
                scene.getStylesheets().add("/codexTheme.css");
                stageReference.setScene(scene);
                stageReference.show();
            }
        });
    }


    @Override
    public void drawLoginScene() {
        //creating elements
        StackPane root = new StackPane();

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
                    clientSocket = new ClientSocket(this, usr, addr, port);
                    bsc.setClientSocket(clientSocket);
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

        Platform.runLater(() -> {
            //append elements to the root
            stageReference.setTitle("Codex! - Login");
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
        });
    }


    @Override
    public void drawLobbyScene() {
        StackPane root = new StackPane();

        Label titleLabel = new Label("Players in the lobby");

        Button playButton = new Button("Play");
        Button backButton = new Button("Back");
        root.getChildren().add(titleLabel);

        titleLabel.setTranslateY(-200);
        lobbyLabel.setTranslateY(-100);

        root.getChildren().add(lobbyLabel);

        playButton.setTranslateY(50);
        playButton.setTranslateX(50);
        backButton.setTranslateY(50);
        backButton.setTranslateX(-50);

        backButton.setOnAction(actionEvent -> {
            clientSocket.disconnect();
            drawHelloScene();
        });

        playButton.setOnMouseClicked(actionEvent -> clientSocket.sendMessage(new DrawBoardMessage(clientSocket.getUsername())));
        Platform.runLater(() -> {
            stageReference.setTitle("Codex! - Lobby");
            root.getChildren().add(playButton);
            root.getChildren().add(backButton);

            Scene scene = new Scene(root, stageWidth, stageHeigth);
            scene.getStylesheets().add("/codexTheme.css");
            stageReference.setScene(scene);
            stageReference.show();
        });
    }


    @Override
    public void updateLobby(ArrayList<String> names) {
        StringBuilder sb = new StringBuilder();
        for (String name : names) {
            sb.append(name).append("\n");
        }
        Platform.runLater(() -> lobbyLabel.setText(sb.toString()));
    }

    @Override
    public void drawCreateGameScene() {
        //creating elements
        StackPane root = new StackPane();

        Label localHostLabel = new Label("Localhost: " + ConnectionUtil.getLocalhost());

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
                    clientSocket = new ClientSocket(this, usr, ConnectionUtil.getLocalhost(), ConnectionUtil.defaultPort);
                    bsc.setClientSocket(clientSocket);
                } catch (IOException e) {
                    errorLabel.setText("Server not available");
                    return;
                }
                clientSocket.sendMessage(new CreateGameMessage(usr, ConnectionUtil.defaultPort, num));
            } else {
                errorLabel.setText("Invalid input");
            }
        });
        backButton.setOnAction(actionEvent -> drawHelloScene());
        Platform.runLater(() -> {
            //append elements to the root
            stageReference.setTitle("Codex! - Create Game");
            root.getChildren().add(localHostLabel);
            localHostLabel.setTranslateY(-200);
            numPlayersChoice.setTranslateY(-50);
            usernameField.setTranslateY(-100);
            confirmButton.setTranslateY(50);
            backButton.setTranslateY(50);
            errorLabel.setTranslateY(100);
            errorLabel.setTranslateX(50);
            confirmButton.setTranslateX(50);
            backButton.setTranslateX(-50);
            root.getChildren().add(usernameField);
            root.getChildren().add(numPlayersChoice);
            root.getChildren().add(confirmButton);
            root.getChildren().add(errorLabel);
            root.getChildren().add(backButton);

            Scene scene = new Scene(root, 1520, 850);
            scene.getStylesheets().add("/codexTheme.css");
            stageReference.setScene(scene);
            stageReference.show();
        });
    }

    @Override
    public void drawBoardScene() {


        //TODO creare text a inizio gioco che spiega i comandi e sparisce una volta settato l'obiettivo segreto

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
        displayCenteredTimedAlert("GAME CONTROLS:\nTo select a card LEFT click on it\nTo flip a card select it and click the flip card button\nTo pick the initial card and secret objective RIGHT click on it\nTo draw a card click on it\nTo pass the turn click on the end turn button\nTo place a card select it and click where you want to place it" , 25);

        Text yourTurn = new Text("");
        yourTurn.setFill(javafx.scene.paint.Color.WHITE);
        yourTurn.setWrappingWidth(200);
        yourTurn.setX(stageWidth/2 + 130);
        yourTurn.setY(20);
        setYouTurnDisplay(yourTurn);



        root.getChildren().add(alert);
        root.getChildren().add(yourTurn);

        setMovableRootReference(movableRoot);
        setStaticGroupReference(staticRoot);


        /**
         * STATIC PART OF THE STAGE
         */

        // BOX THAT CONTAINS RESOURCE CARDS THAT CAN BE DRAWN
        double rectangleWidthOfResourceCardPicker = 130;
        double rectangleHeightOfResourceCardPicker = 259.5;
        Rectangle ResourceCardsBox = new Rectangle(stageWidth - rectangleWidthOfResourceCardPicker, 0, rectangleWidthOfResourceCardPicker, rectangleHeightOfResourceCardPicker);
        ResourceCardsBox.setFill(Color.BLACK.getPaint());

        //Display of the resource cards that can be drawn
        Rectangle ResourceCard1 = new Rectangle(stageWidth - cardWidth - 3, 3, cardWidth, cardHeight);
        ResourceCard1.setFill(Color.RED.getPaint());
        ResourceCard1.setUserData(0);

        Rectangle ResourceCard2 = new Rectangle(stageWidth - cardWidth - 3, 3 + cardHeight + 3, cardWidth, cardHeight);
        ResourceCard2.setFill(Color.RED.getPaint());
        ResourceCard2.setUserData(1);

        Rectangle ResourceCard3 = new Rectangle(stageWidth - cardWidth - 3, 3 + cardHeight + 3 + cardHeight + 3, cardWidth, cardHeight);
        ResourceCard3.setFill(Color.RED.getPaint());
        ResourceCard3.setUserData(2);

        bsc.setUpDrawableResources(ResourceCard1, ResourceCard2, ResourceCard3);


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
        bsc.setUpDrawableGold(GoldCard1, GoldCard2, GoldCard3);

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

        bsc.setUpHandMap(HandCard1, HandCard2, HandCard3);

        // COMMON OBJECTIVES BOX
        double rectangleWidthCommonObjectives = cardWidth * 2 + 3 * 3;
        double rectangleHeightCommonObjectives = cardHeight + 6;
        Rectangle CommonObjectivesBox = new Rectangle(0, stageHeigth - rectangleHeightCommonObjectives, rectangleWidthCommonObjectives, rectangleHeightCommonObjectives);
        CommonObjectivesBox.setFill(Color.BLACK.getPaint());

        //common objectives
        Rectangle CommonObjective1 = new Rectangle(3, stageHeigth - cardHeight - 3, cardWidth, cardHeight);
        CommonObjective1.setFill(Color.RED.getPaint());

        Rectangle CommonObjective2 = new Rectangle(3 + cardWidth + 3, stageHeigth - cardHeight - 3, cardWidth, cardHeight);
        CommonObjective2.setFill(Color.RED.getPaint());

        bsc.setUpCommonObjectivesMap(CommonObjective1, CommonObjective2);

        // SECRET OBJECTIVE BOX
        double rectangleWidthSecretObjective = cardWidth + 6;
        double rectangleHeightSecretObjective = cardHeight + 6;
        Rectangle SecretObjectiveBox = new Rectangle(0 + rectangleWidthCommonObjectives + 5, stageHeigth - rectangleHeightSecretObjective, rectangleWidthSecretObjective, rectangleHeightSecretObjective);
        SecretObjectiveBox.setFill(Color.BLACK.getPaint());


        //secret objective
        Rectangle SecretObjective = new Rectangle(0 + rectangleWidthCommonObjectives + 5 + 3, stageHeigth - cardHeight - 3, cardWidth, cardHeight);
        SecretObjective.setFill(Color.RED.getPaint());
        bsc.setSecretObjectiveMap(SecretObjective);

        //INITIAL CARD DISPLAY FOR FACE SELECTION
        double rectangleWidthInitialCardDisplay = cardWidth + 6;
        double rectangleHeightInitialCardDisplay = cardHeight + 6;
        Rectangle InitialCardDisplayBox = new Rectangle(stageWidth / 2 - rectangleWidthInitialCardDisplay - 50, stageHeigth / 2 - rectangleHeightInitialCardDisplay, rectangleWidthInitialCardDisplay, rectangleHeightInitialCardDisplay);
        InitialCardDisplayBox.setFill(Color.BLACK.getPaint());
        this.initCardBackground = InitialCardDisplayBox;

        Rectangle InitialCardDisplay = new Rectangle(stageWidth / 2 - rectangleWidthInitialCardDisplay - 50 + 3, stageHeigth / 2 - rectangleHeightInitialCardDisplay + 3, cardWidth, cardHeight);
        InitialCardDisplay.setFill(Color.RED.getPaint());
        bsc.setUpInitialCardDisplay(InitialCardDisplay);

        //SECRET OBJECTIVES DISPLAY FOR SELECTION
        double rectangleWidthSecretObjectiveDisplay = cardWidth + 6;
        double rectangleHeightObjectiveCardsDisplay = 2 * cardHeight + 9;
        Rectangle secretObjectivesDisplayBox = new Rectangle(stageWidth / 2 + rectangleWidthSecretObjectiveDisplay - 60, stageHeigth / 2 - rectangleHeightObjectiveCardsDisplay / 2 - rectangleHeightObjectiveCardsDisplay / 4, rectangleWidthSecretObjectiveDisplay, rectangleHeightObjectiveCardsDisplay);
        secretObjectivesDisplayBox.setFill(Color.BLACK.getPaint());
        this.secretObjectivesBackground = secretObjectivesDisplayBox;

        Rectangle secretObjective1Display = new Rectangle(stageWidth / 2 + rectangleWidthSecretObjectiveDisplay - 60 + 3, stageHeigth / 2 - rectangleHeightObjectiveCardsDisplay / 2 - rectangleHeightObjectiveCardsDisplay / 4 + 3, cardWidth, cardHeight);
        secretObjective1Display.setFill(Color.RED.getPaint());
        secretObjective1Display.setUserData(0);

        Rectangle secretObjective2Display = new Rectangle(stageWidth / 2 + rectangleWidthSecretObjectiveDisplay - 60 + 3, stageHeigth / 2 - rectangleHeightObjectiveCardsDisplay / 2 - rectangleHeightObjectiveCardsDisplay / 4 + 3 + cardHeight + 3, cardWidth, cardHeight);
        secretObjective2Display.setFill(Color.RED.getPaint());
        secretObjective2Display.setUserData(1);
        bsc.setUpSecretObjectivesToChoose(secretObjective1Display, secretObjective2Display);


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
                bsc.flipCard();
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
        endTurnButton.setOnMouseClicked(e -> {
            clientSocket.sendMessage(new EndTurnMessage(clientSocket.getUsername()));
        });


        //resources & points box
        double rectangleWidthPointsBox = 422.5;
        double rectangleHeightPointsBox = cardHeight + 6;
        Rectangle PointsBox = new Rectangle(0 + rectangleWidthCommonObjectives + rectangleWidthSecretObjective + 20, stageHeigth - rectangleHeightSecretObjective, rectangleWidthPointsBox, rectangleHeightSecretObjective);

        PointsBox.setFill(Color.BLACK.getPaint());

        //resources

        Rectangle mushrooms = new Rectangle(0 + rectangleWidthCommonObjectives + rectangleWidthSecretObjective + 20 + 2.5, stageHeigth - rectangleHeightSecretObjective + 3, resourceWidth, resourceHeigth);
        Rectangle mushrooms_points = new Rectangle(0 + rectangleWidthCommonObjectives + rectangleWidthSecretObjective + 20 + 2.5, stageHeigth - rectangleHeightSecretObjective + resourceHeigth + 3, resourceWidth, resourceHeigth);
        Text mush_label = new Text("0");
        mush_label.setFill(javafx.scene.paint.Color.WHITE);
        mush_label.setFont(new Font("Nimbus Roman", 20));
        mush_label.setLayoutX(15 + rectangleWidthCommonObjectives + rectangleWidthSecretObjective + 20 + 2.5 + 5);
        mush_label.setLayoutY(stageHeigth - rectangleHeightSecretObjective + resourceHeigth + 3 + 20);
        bsc.addRectangleToPointsDisplay(mushrooms_points, mush_label);

        try {
            bsc.setImageToRectangle("/board_icons/mushroom_icon.png", mushrooms);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        Rectangle animals = new Rectangle(0 + rectangleWidthCommonObjectives + rectangleWidthSecretObjective + 20 + 5 + resourceWidth, stageHeigth - rectangleHeightSecretObjective + 3, resourceWidth, resourceHeigth);
        Rectangle animals_points = new Rectangle(0 + rectangleWidthCommonObjectives + rectangleWidthSecretObjective + 20 + 5 + resourceWidth, stageHeigth - rectangleHeightSecretObjective + resourceHeigth + 3, resourceWidth, resourceHeigth);
        Text animals_label = new Text("0");
        animals_label.setFont(new Font("Nimbus Roman", 20));
        animals_label.setFill(javafx.scene.paint.Color.WHITE);
        animals_label.setLayoutX(15 + rectangleWidthCommonObjectives + rectangleWidthSecretObjective + 20 + 5 + resourceWidth + 5);
        animals_label.setLayoutY(stageHeigth - rectangleHeightSecretObjective + resourceHeigth + 3 + 20);
        bsc.addRectangleToPointsDisplay(animals_points, animals_label);

        try {
            bsc.setImageToRectangle("/board_icons/animal_icon.png", animals);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        Rectangle insect = new Rectangle(0 + rectangleWidthCommonObjectives + rectangleWidthSecretObjective + 20 + 7.5 + resourceWidth * 2, stageHeigth - rectangleHeightSecretObjective + 3, resourceWidth, resourceHeigth);
        Rectangle insect_points = new Rectangle(0 + rectangleWidthCommonObjectives + rectangleWidthSecretObjective + 20 + 7.5 + resourceWidth * 2, stageHeigth - rectangleHeightSecretObjective + resourceHeigth + 3, resourceWidth, resourceHeigth);
        Text insect_label = new Text("0");
        insect_label.setFont(new Font("Nimbus Roman", 20));
        insect_label.setFill(javafx.scene.paint.Color.WHITE);
        insect_label.setLayoutX(15 + rectangleWidthCommonObjectives + rectangleWidthSecretObjective + 20 + 7.5 + resourceWidth * 2 + 5);
        insect_label.setLayoutY(stageHeigth - rectangleHeightSecretObjective + resourceHeigth + 3 + 20);
        bsc.addRectangleToPointsDisplay(insect_points, insect_label);


        try {
            bsc.setImageToRectangle("/board_icons/insect_icon.png", insect);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        Rectangle leaves = new Rectangle(0 + rectangleWidthCommonObjectives + rectangleWidthSecretObjective + 20 + 10 + resourceWidth * 3, stageHeigth - rectangleHeightSecretObjective + 3, resourceWidth, resourceHeigth);
        Rectangle leaves_points = new Rectangle(0 + rectangleWidthCommonObjectives + rectangleWidthSecretObjective + 20 + 10 + resourceWidth * 3, stageHeigth - rectangleHeightSecretObjective + resourceHeigth + 3, resourceWidth, resourceHeigth);
        Text leaves_label = new Text("0");
        leaves_label.setFont(new Font("Nimbus Roman", 20));
        leaves_label.setFill(javafx.scene.paint.Color.WHITE);
        leaves_label.setLayoutX(15 + rectangleWidthCommonObjectives + rectangleWidthSecretObjective + 20 + 10 + resourceWidth * 3 + 5);
        leaves_label.setLayoutY(stageHeigth - rectangleHeightSecretObjective + resourceHeigth + 3 + 20);
        bsc.addRectangleToPointsDisplay(leaves_points, leaves_label);

        try {
            bsc.setImageToRectangle("/board_icons/leaf_icon.png", leaves);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        Rectangle quills = new Rectangle(0 + rectangleWidthCommonObjectives + rectangleWidthSecretObjective + 20 + 12.5 + resourceWidth * 4, stageHeigth - rectangleHeightSecretObjective + 3, resourceWidth, resourceHeigth);
        Rectangle quills_points = new Rectangle(0 + rectangleWidthCommonObjectives + rectangleWidthSecretObjective + 20 + 12.5 + resourceWidth * 4, stageHeigth - rectangleHeightSecretObjective + resourceHeigth + 3, resourceWidth, resourceHeigth);
        Text quills_label = new Text("0");
        quills_label.setFont(new Font("Nimbus Roman", 20));
        quills_label.setFill(javafx.scene.paint.Color.WHITE);
        quills_label.setLayoutX(15 + rectangleWidthCommonObjectives + rectangleWidthSecretObjective + 20 + 12.5 + resourceWidth * 4 + 5);
        quills_label.setLayoutY(stageHeigth - rectangleHeightSecretObjective + resourceHeigth + 3 + 20);
        bsc.addRectangleToPointsDisplay(quills_points, quills_label);

        try {
            bsc.setImageToRectangle("/board_icons/quill_icon.png", quills);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        Rectangle inkwells = new Rectangle(0 + rectangleWidthCommonObjectives + rectangleWidthSecretObjective + 20 + 15 + resourceWidth * 5, stageHeigth - rectangleHeightSecretObjective + 3, resourceWidth, resourceHeigth);
        Rectangle inkwells_points = new Rectangle(0 + rectangleWidthCommonObjectives + rectangleWidthSecretObjective + 20 + 15 + resourceWidth * 5, stageHeigth - rectangleHeightSecretObjective + resourceHeigth + 3, resourceWidth, resourceHeigth);
        Text inkwells_label = new Text("0");
        inkwells_label.setFont(new Font("Nimbus Roman", 20));
        inkwells_label.setFill(javafx.scene.paint.Color.WHITE);
        inkwells_label.setLayoutX(15 + rectangleWidthCommonObjectives + rectangleWidthSecretObjective + 20 + 15 + resourceWidth * 5 + 5);
        inkwells_label.setLayoutY(stageHeigth - rectangleHeightSecretObjective + resourceHeigth + 3 + 20);
        bsc.addRectangleToPointsDisplay(inkwells_points, inkwells_label);

        try {
            bsc.setImageToRectangle("/board_icons/inkwell_icon.png", inkwells);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        Rectangle manuscript = new Rectangle(0 + rectangleWidthCommonObjectives + rectangleWidthSecretObjective + 20 + 17.5 + resourceWidth * 6, stageHeigth - rectangleHeightSecretObjective + 3, resourceWidth, resourceHeigth);
        Rectangle manuscript_points = new Rectangle(0 + rectangleWidthCommonObjectives + rectangleWidthSecretObjective + 20 + 17.5 + resourceWidth * 6, stageHeigth - rectangleHeightSecretObjective + resourceHeigth + 3, resourceWidth, resourceHeigth);
        Text manuscript_label = new Text("0");
        manuscript_label.setFont(new Font("Nimbus Roman", 20));
        manuscript_label.setFill(javafx.scene.paint.Color.WHITE);
        manuscript_label.setLayoutX(15 + rectangleWidthCommonObjectives + rectangleWidthSecretObjective + 20 + 17.5 + resourceWidth * 6 + 5);
        manuscript_label.setLayoutY(stageHeigth - rectangleHeightSecretObjective + resourceHeigth + 3 + 20);
        bsc.addRectangleToPointsDisplay(manuscript_points, manuscript_label);

        try {
            bsc.setImageToRectangle("/board_icons/manuscript_icon.png", manuscript);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        Rectangle points = new Rectangle(0 + rectangleWidthCommonObjectives + rectangleWidthSecretObjective + 20 + 20 + resourceWidth * 7, stageHeigth - rectangleHeightSecretObjective + 3, resourceWidth, resourceHeigth);
        Rectangle Points_points = new Rectangle(0 + rectangleWidthCommonObjectives + rectangleWidthSecretObjective + 20 + 20 + resourceWidth * 7, stageHeigth - rectangleHeightSecretObjective + resourceHeigth + 3, resourceWidth, resourceHeigth);
        Text points_label = new Text("0");
        points_label.setFont(new Font("Nimbus Roman", 20));
        points_label.setFill(javafx.scene.paint.Color.WHITE);
        points_label.setLayoutX(15 + rectangleWidthCommonObjectives + rectangleWidthSecretObjective + 20 + 20 + resourceWidth * 7 + 5);
        points_label.setLayoutY(stageHeigth - rectangleHeightSecretObjective + resourceHeigth + 3 + 20);
        bsc.addRectangleToPointsDisplay(Points_points, points_label);

        points.setFill(Color.RED.getPaint());

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

        /**
         * THE GRID
         */
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
                    gridRectangle.setOnMouseClicked(e -> {
                        System.out.println("Rectangle at (" + finalI + ", " + finalJ + ") was clicked!");

                    });
                    bsc.addRectangleToGridMap(gridRectangle); //TODO settare l'evento di click qua dentro
                    movableRoot.getChildren().addAll(gridRectangle/*, label*/);
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

        Platform.runLater(() -> {
            stageReference.setTitle("Codex! - your board");
            scene.getStylesheets().add("/codexTheme.css");
            stageReference.setScene(scene);
            stageReference.setHeight(stageHeigth + 37);
            stageReference.setWidth(stageWidth);
            stageReference.show();
        });
    }

    @Override
    public void displayAlert(String alert) {
        //show dialog box containing string alert
        Platform.runLater(() -> {
            displayCenteredTimedAlert(alert, 7);
        });
    }

    // METHODS CALLED BY CLIENTPARSER BELOW THIS COMMENT


    @Override
    public void updateGold(ArrayList<GoldCard> goldCards) {
        bsc.updateDrawableGold(goldCards);
    }

    @Override
    public void updateResource(ArrayList<ResourceCard> resourceCards) {
        bsc.updateDrawableResources(resourceCards);
    }

    @Override
    public void updateHand(ArrayList<Card> hand) {
        bsc.updateHand(hand);
    }

    @Override
    public void updateInitialCardDisplay(InitialCard card) {
        bsc.updateInitialCardDisplay(card);
    }

    @Override
    public void updateSecretObjectiveToChoose(ArrayList<ObjectiveCard> secretObjectives) {
        bsc.updateSecretObjectiveToChoose(secretObjectives);
    }

    @Override
    public void updateCommonObjectives(ArrayList<ObjectiveCard> commonObjectives) {
        bsc.updateCommonObjectives(commonObjectives);
    }

    @Override
    public void updateSecretObjective(ObjectiveCard secretObjective) {
        bsc.updateSecretObjective(secretObjective);
    }


    @Override
    public void drawCard(Card card) {
        //bsc.drawCard(card);
    }

    @Override
    public void updatePoints(ArrayList<Integer> points) {
        bsc.updatePoints(points);
    }

    @Override
    public void placeCard(Integer x, Integer y, Card card) {
        bsc.placeCard(x, y, card);
    }


    public Stage getStageReference() {
        return this.stageReference;
    }

    public ClientSocket getClientSocket() {
        return clientSocket;
    }

    public void removeRectangleFromMovableRoot(Rectangle rectangle) {
        movableRootReference.getChildren().remove(rectangle);
    }

    public void removeRectangleFromStaticRoot(Rectangle rectangle) {
        staticGroupReference.getChildren().remove(rectangle);
    }


    public void setStaticGroupReference(Group staticGroupReference) {
        this.staticGroupReference = staticGroupReference;
    }

    public void setMovableRootReference(Group movableRootReference) {
        this.movableRootReference = movableRootReference;
    }

    public void setAlert(Text alert) {
        this.alert = alert;
    }

    public void setYouTurnDisplay(Text yourTurn){
        this.yourTurn = yourTurn;
    }

    @Override
    public void setYourTurnText(){
        this.yourTurn.setText("YOUR TURN");
    }

    @Override
    public void cleanYourTurnText(){
        this.yourTurn.setText("NOT YOUR TURN");
    }


    public void displayCenteredTimedAlert(String text , Integer timer){
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

    public Rectangle getSecretObjectivesBackground() {
        return secretObjectivesBackground;
    }

    public Rectangle getInitCardBackground() {
        return initCardBackground;
    }

    public void bringRectangleToFront(Rectangle rectangle) {
        Group parent = (Group) rectangle.getParent();
        parent.getChildren().remove(rectangle);
        parent.getChildren().add(rectangle);
    }

    public void displayPoints(Rectangle rectangle, Integer points) {
        Group parent = (Group) rectangle.getParent();
        parent.getChildren().remove(rectangle);
    }
}
