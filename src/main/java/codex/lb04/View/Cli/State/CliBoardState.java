package codex.lb04.View.Cli.State;

/**
 * Enum for the different states of the CLI board
 */
public enum CliBoardState {
    CHOOSE_INIT,        // when the player has to choose the initial card
    CHOOSE_SECRET,      // when the player has to choose the secret objective
    SELECTING,          // when the player is selecting a card
    PLACING,            // when the player is placing a card
    DRAWING,            // when the player is drawing a card
    END                // when the turn is over, and the client has to pass
}
