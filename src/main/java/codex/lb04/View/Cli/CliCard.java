package codex.lb04.View.Cli;

import codex.lb04.Model.DeckBuilder;

public class CliCard {
    DeckBuilder deckBuilder;

    public CliCard(DeckBuilder deckBuilder) {
        this.deckBuilder = deckBuilder;
    }

    /**
     * Draw a bronze card
     */
    public void drawGoldCard() {
        deckBuilder.createGoldCards();
    }
}
