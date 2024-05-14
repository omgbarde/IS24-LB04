package codex.lb04.View.Cli;

import codex.lb04.Model.Card;
import codex.lb04.Model.GoldCard;
import codex.lb04.Network.client.ClientSocket;
import codex.lb04.View.GuiView;
import javafx.application.Platform;
import javafx.scene.shape.Rectangle;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class BoardSceneControllerCLI {
    private ClientSocket clientSocket;
    private LinkedHashMap<String, GoldCard> drawableGold = new LinkedHashMap<>();
    private Card selectedCard = null;
   // private CliCard = null;


    /**
     * Constructor of the board scene controller
     *
     * @param view the view
     */
    public BoardSceneControllerCLI(GuiView view) {
        //this.gridMap = new HashMap<>();
        //this.view = view;
        //this.stageReference = view.getStageReference();
    }

    public void setClientSocket(ClientSocket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void positionAvailable(){
        System.out.println("Position available are:");

    }

    public void updateDrawableGold(ArrayList<GoldCard> goldCards) {

      /*  drawableGold.replaceAll((r, v) -> null);
        for (Rectangle rectangle : drawableGold.keySet()) {
            Platform.runLater(() -> {
                cleanImage(rectangle);
            });
        }
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
            });*/
        }
    }

