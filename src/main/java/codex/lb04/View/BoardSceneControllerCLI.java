package codex.lb04.View;

import codex.lb04.Network.client.ClientSocket;

import java.util.HashMap;

public class BoardSceneControllerCLI {
    private ClientSocket clientSocket;
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
}
