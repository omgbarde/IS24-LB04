package codex.lb04.Model;

import codex.lb04.Model.Enumerations.Color;

/**
 * Class that represents a Gold Card
 */
public class GoldCard extends Card {
    //number of points you gain from the "objective"
    private final int points;
    private final int mushroom_needed;
    private final int animals_needed;
    private final int insects_needed;
    private final int leaf_needed;

    /**
     * Default constructor
     *
     * @param color the color of the card
     * @param front the face of the card
     * @param back  the face of the card
     * @param points the number of points you gain from the "objective"
     * @param mushroom_needed the number of mushrooms needed to complete the "objective"
     * @param animals_needed the number of animals needed to complete the "objective"
     * @param leaf_needed the number of leafs needed to complete the "objective"
     * @param insects_needed the number of insects needed to complete the "objective"
     */
    public GoldCard(Color color, Face front, Face back, int points, int mushroom_needed, int animals_needed, int leaf_needed, int insects_needed) {
        super(color, front, back);
        this.points = points;
        this.mushroom_needed = mushroom_needed;
        this.animals_needed = animals_needed;
        this.insects_needed = insects_needed;
        this.leaf_needed = leaf_needed;
    }

    public Integer getPoints() {
        return points;
    }

    public Integer getMushroom_needed() {
        return mushroom_needed;
    }

    public Integer getAnimals_needed() {
        return animals_needed;
    }

    public Integer getInsects_needed() {
        return insects_needed;
    }

    public Integer getLeaf_needed() {
        return leaf_needed;
    }


}
