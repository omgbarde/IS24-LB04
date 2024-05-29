package codex.lb04.Model;

import codex.lb04.Model.Enumerations.ResourceType;

import java.io.Serial;
import java.io.Serializable;

/**
 * This class represents a corner of a card, it can contain resources
 */
public class Corner implements Serializable {

    @Serial
    private static final long serialVersionUID = 1167113121676386863L;

    private final ResourceType resource;
    private boolean isCovered;
    private Card coveredBy;

    /**
     * Default constructor
     *
     * @param resource the resource the corner has
     */
    public Corner(ResourceType resource) {
        this.resource = resource;
        isCovered = false;
        this.coveredBy = null;
    }

    /**
     * constructor for corners with no resources that can be covered or not
     */
    public Corner(boolean isCovered) {
        this.resource = null;
        this.isCovered = isCovered;
        this.coveredBy = null;
    }

    /**
     * Secondary constructor
     *
     * @param resource  the resource the corner has
     * @param isCovered tells if a corner is covered or not
     */
    public Corner(ResourceType resource, boolean isCovered) {
        this.resource = resource;
        this.isCovered = isCovered;
        this.coveredBy = null;
    }


    //GETTER

    /**
     * This method tells if a corner is covered or not
     *
     * @return {@code true} if the corner is covered, {@code false} if it's not
     */
    public boolean isCovered() {
        return isCovered;
    }

    /**
     * This method covers a corner by applying a card onto it
     *
     * @param Cover The Card that covers the corner
     */
    public void setCovered(Card Cover) {
        this.coveredBy = Cover;
        isCovered = true;
    }

    /**
     * This method returns the card that covers the corner
     *
     * @return The card that covers the corner
     */
    public Card getCoveringCard() {
        return coveredBy;
    }

    //SETTER

    /**
     * Returns the resource
     *
     * @return the resource inside the corner
     */
    public ResourceType getResource() {
        return resource;
    }
}