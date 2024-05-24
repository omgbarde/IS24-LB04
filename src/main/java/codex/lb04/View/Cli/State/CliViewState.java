package codex.lb04.View.Cli.State;

/**
 * Enum for the different states of the CLI view
 */
public enum CliViewState {
    HELLO,          // when the client is in the hello scene
    LOGIN,          // when the client is in the login scene
    CREATE_GAME,    // when the client is in the create game scene
    LOBBY,          // when the client is in the lobby scene
    BOARD,          // when the client is in the board scene
    CHAT,           // when the client is in the chat scene
    END             // when the client is in the game ended scene
}
