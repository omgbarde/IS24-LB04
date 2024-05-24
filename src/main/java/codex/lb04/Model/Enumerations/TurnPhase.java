package codex.lb04.Model.Enumerations;

/**
 * This enum contains all turn phases
 */
public enum TurnPhase {

    PLACE_CARD_PHASE,       //when the player is placing a card on the board
    DRAW_PHASE,             //when the player is drawing a card from the deck
    END_TURN                //when the player has ended his turn and is waiting for the next players to play
}
