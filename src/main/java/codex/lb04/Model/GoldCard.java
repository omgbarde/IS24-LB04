package codex.lb04.Model;

import codex.lb04.Model.Enumerations.Color;

import java.io.Serial;

/**
 * Class that represents a Gold Card
 */
public class GoldCard extends Card {

    @Serial
    private static final long serialVersionUID = -4833730647298877958L;

    private final int mushroom_needed;
    private final int animals_needed;
    private final int insects_needed;
    private final int leaf_needed;

    /**
     * Default constructor
     *
     * @param color           the color of the card
     * @param front           the face of the card
     * @param back            the face of the card
     * @param points          the number of points you gain from the "multiplier"
     * @param mushroom_needed the number of mushrooms needed to place the card
     * @param animals_needed  the number of animals needed to place the card
     * @param leaf_needed     the number of leafs needed to place the card
     * @param insects_needed  the number of insects needed to place the card
     * @param ID              the ID of the card
     */
    public GoldCard(Color color, Face front, Face back, int points, int mushroom_needed, int animals_needed, int leaf_needed, int insects_needed, Integer ID) {
        super(color, front, back, points, ID);
        this.mushroom_needed = mushroom_needed;
        this.animals_needed = animals_needed;
        this.insects_needed = insects_needed;
        this.leaf_needed = leaf_needed;
    }

    /**
     * Constructor for the GoldCard with coordinates
     *
     * @param color                       the color of the card
     * @param front                       the face of the card
     * @param back                        the face of the card
     * @param points                      the number of points you gain from the "multiplier"
     * @param mushroom_needed             the number of mushrooms needed to place the card
     * @param animals_needed              the number of animals needed to place the card
     * @param leaf_needed                 the number of leafs needed to place the card
     * @param insects_needed              the number of insects needed to place the card
     * @param ID                          the ID of the card
     * @param x                           the x coordinate of the card
     * @param y                           the y coordinate of the card
     * @param isShowingFront              true if the card is showing the front, false otherwise
     * @param usedForPositionalObjectives true if the card is used for positional objectives, false otherwise
     */
    public GoldCard(Color color, Face front, Face back, int points, int mushroom_needed, int animals_needed, int leaf_needed, int insects_needed, Integer ID, Integer x, Integer y, boolean isShowingFront, boolean usedForPositionalObjectives) {
        super(color, front, back, points, ID, x, y, isShowingFront, usedForPositionalObjectives);
        this.mushroom_needed = mushroom_needed;
        this.animals_needed = animals_needed;
        this.insects_needed = insects_needed;
        this.leaf_needed = leaf_needed;
    }

    /**
     * override of the clone method to create a deep copy of the card to avoid problems in serialization
     *
     * @return a copy of the card
     */
    @Override
    public GoldCard clone() {
        return new GoldCard(this.getColor(), this.getFront(), this.getBack(), this.getPoints(), this.mushroom_needed, this.animals_needed, this.leaf_needed, this.insects_needed, this.getID(), this.getX(), this.getY(), this.isShowingFront(), this.isUsedForPositionalObjectives());
    }

    //GETTER

    /**
     * Getter for the mushrooms needed from a gold card to be placed
     *
     * @return the mushrooms needed
     */
    public Integer getMushroom_needed() {
        return mushroom_needed;
    }

    /**
     * Getter for the animals needed from a gold card to be placed
     *
     * @return the mushrooms needed
     */
    public Integer getAnimals_needed() {
        return animals_needed;
    }

    /**
     * Getter for the insects needed from a gold card to be placed
     *
     * @return the mushrooms needed
     */
    public Integer getInsects_needed() {
        return insects_needed;
    }

    /**
     * Getter for the leafs needed from a gold card to be placed
     *
     * @return the mushrooms needed
     */
    public Integer getLeaf_needed() {
        return leaf_needed;
    }

}