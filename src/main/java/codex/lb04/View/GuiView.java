package codex.lb04.View;

import codex.lb04.CodexClientApp;
import codex.lb04.Controller.SceneController.CreateGameController;
import codex.lb04.Controller.SceneController.LoginController;
import codex.lb04.Message.GameMessage.CreateGameMessage;
import codex.lb04.Message.LoginMessage;
import codex.lb04.Message.LogoutMessage;
import codex.lb04.Network.client.ClientSocket;
import codex.lb04.Utils.ConnectionUtil;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * class that represents the GUI view
 */
public class GuiView extends View {
    private static Stage stageReference;
    private static ClientSocket clientSocket;
    private LoginController helloController;
    //private LobbyController lobbyController;
    private CreateGameController createGameController;

    public GuiView(Stage stage) {
        stageReference = stage;
        //this.helloController =  new LoginController();
        //this.lobbyController = new LobbyController();
        //this.createGameController = new CreateGameController();
    }

    /**
     * this method switches the scene to the one specified by the name
     *
     * @param sceneName is the name of the scene to load
     */
    @Override
    public void switchScene(String sceneName) {
        //in guiview we need to add the .fxml extension to the scene name
        Platform.runLater(() -> {
            try {
                loadScene(sceneName + ".fxml");
            } catch (IOException e) {
                System.out.println("Error loading the " + sceneName + " scene");
            }
        });
    }

    /**
     * this method loads the scene from the fxml file
     *
     * @param fxml is the fxml file to load
     * @throws IOException when an error occurs in loading the fxml
     */
    public static void loadScene(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CodexClientApp.class.getResource(fxml));
        Scene scene = new Scene(fxmlLoader.load(), 1520, 850);
        stageReference.setScene(scene);
    }

    @Override
    public void setTitle(String title) {
        Platform.runLater(() -> stageReference.setTitle(title));

    }

    @Override
    public void setMode(String mode) {
        if (mode.equals("fullscreen")) {
            Platform.runLater(() -> stageReference.setFullScreen(true));
        }
    }

    @Override
    public void updateList(ArrayList<String> names) {
        //Platform.runLater(()->lobbyController.updateList(Objects.requireNonNull(names)));
    }

    @Override
    public void updateListLater(ArrayList<String> names){
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                updateList(names);
            }
        };
        int delay = 1000;
        timer.schedule(task, delay);
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
            if (ConnectionUtil.checkValid(usr, addr, port)){
                try {
                    clientSocket = new ClientSocket(this,usr, addr, port);
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


    public void updateLobby(ArrayList<String>names){
        Parent root = stageReference.getScene().getRoot();

        StringBuilder sb = new StringBuilder();
        for (String name : names) {
            sb.append(name).append("\n");
        }
        Label nameList = new Label(sb.toString());

        ((StackPane)root).getChildren().add(nameList);
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
            }catch (NumberFormatException e){
                errorLabel.setText("Enter a valid number of players");
                return;
            }
            String usr = usernameField.getText();
            if (ConnectionUtil.checkValid(num,usr)) {
                confirmButton.setDisable(true);
                try {
                    clientSocket = new ClientSocket(this, usr, ConnectionUtil.getLocalHost(), ConnectionUtil.defaultPort);
                } catch (IOException e) {
                    errorLabel.setText("Server not available");
                    return;
                }
                clientSocket.sendMessage(new CreateGameMessage(usr, ConnectionUtil.defaultPort, num));
            }
            else {
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

    public void drawBoardScene(){
        stageReference.setTitle("Codex! - your board");
        StackPane root = new StackPane();


    }


}
