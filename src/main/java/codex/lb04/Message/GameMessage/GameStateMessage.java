package codex.lb04.Message.GameMessage;

import codex.lb04.Message.Message;
import codex.lb04.Message.MessageType;
import codex.lb04.Model.Enumerations.GameState;

public class GameStateMessage extends Message {
    private  GameState gameState;
    public GameStateMessage(String username, GameState state) {
        super(username, MessageType.GAME_STATE);
        this.gameState = state;
    }

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
