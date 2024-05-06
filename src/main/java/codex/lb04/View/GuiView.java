package codex.lb04.View;

import codex.lb04.Message.GameMessage.CreateGameMessage;
import codex.lb04.Message.LoginMessage;
import codex.lb04.Message.LogoutMessage;
import codex.lb04.Model.Enumerations.Color;
import codex.lb04.Network.client.ClientSocket;
import codex.lb04.Utils.ConnectionUtil;
import javafx.scene.Group;
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

/**
 * class that represents the GUI view
 */
public class GuiView extends View {
    private static Stage stageReference;
    private static ClientSocket clientSocket;

    public GuiView(Stage stage) {
        stageReference = stage;
        //this.helloController =  new LoginController();
        //this.lobbyController = new LobbyController();
        //this.createGameController = new CreateGameController();
    }

    public static Stage getStageReference() {
        return stageReference;
    }


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


    public void updateLobby(ArrayList<String> names) {
        Parent root = stageReference.getScene().getRoot();

        StringBuilder sb = new StringBuilder();
        for (String name : names) {
            sb.append(name).append("\n");
        }
        Label nameList = new Label(sb.toString());

        ((StackPane) root).getChildren().add(nameList);
    }

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

    public void drawBoardScene() {
        stageReference.setTitle("Codex! - your board");
        Group root = new Group();

        // Load the image
        InputStream is = getClass().getResourceAsStream("/cards_images/CODEX_cards_gold_front/427371a2-5897-4015-8c67-34dd8707c4ba-001.png");
        Image image = new Image(is);

        // Create the pattern
        ImagePattern imagePattern = new ImagePattern(image);

        // Create the rectangle and set the fill to the pattern
        Rectangle rect = new Rectangle(100, 100, 200, 200);
        rect.setFill(imagePattern);

        root.getChildren().add(rect);

        // Create a translate transformation for the group
        Translate cameraTranslate = new Translate();
        root.getTransforms().add(cameraTranslate);

        Scene scene = new Scene(root, 1520, 850);

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
        stageReference.show();
    }


}
