package codex.lb04.Message.GameMessage;

import codex.lb04.Message.Message;
import codex.lb04.Message.MessageType;
import codex.lb04.Model.Enumerations.GameState;

import java.io.Serial;

public class GameStateMessage extends Message {
    @Serial
    private static final long serialVersionUID = 13;
    private  GameState gameState;
    public GameStateMessage(String username, GameState state) {
        super(username, MessageType.GAME_STATE);
        this.gameState = state;
    }
    /**
     * Getter for gameState
     * @return gameState
     */
    public GameState getGameState() {
        return gameState;
    }
    @Override
    public String toString() {
        return "GameStateMessage{" +
                "gameState=" + gameState +
                '}';
    }
}
