package codex.lb04.Model;

import codex.lb04.Model.Enumerations.ResourceType;

/**
 * This class represents a corner of a card, it can contain items
 */
public class Corner {
    private final ResourceType resource;
    private boolean Covered;
    private Card Cover;

    /**
     * Default constructor
     * @param resource the resource the corner has
     *
     */
    public Corner(ResourceType resource) {
        this.resource = resource;
        Covered = false;
        this.Cover = null;
    }

    /**
     * Secondary constructor
     * @param resource the resource the corner has
     * @param covered tells if a corner is covered or not
     */
    public Corner(ResourceType resource , boolean covered) {
        this.resource = resource;
        Covered = covered;
        this.Cover = null;
    }

    /**
     * This method covers a corner by applying a card onto it
     * @param Cover The Card that covers the corner
     */
    public void getCovered(Card Cover){
        this.Cover = Cover;
        Covered = true;
    }

    /**
     * This method tells if a corner ha a cover or not
     * @return {@code true} if the corner is covered, {@code false} if it's not
     */
    public boolean isCovered(){
        return Covered;
    }

    /**
     * This method returns the cover of the corner
     * @return The card that covers the corner
     */
    public Card getCover(){
        return Cover;
    }

    /**
     * Returns the resource
     * @return the resource inside the corner
     */
    public ResourceType getResource(){
        return resource;
    }

}
