package codex.lb04.Model;

import codex.lb04.Model.Enumerations.Color;

/**
 * Class that represents a Gold Card
 */
public class GoldCard extends Card{
    private final int points;
    /**
     * Default constructor
     *
     * @param color the color of the card
     * @param front the face of the card
     * @param back  the face of the card
     */
    public GoldCard(Color color, Face front, Face back, int points) {
        super(color, front, back);
        this.points = points;
    }
}