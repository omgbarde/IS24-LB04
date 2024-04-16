package codex.lb04.Model;

import codex.lb04.Model.Enumerations.ResourceType;

import java.util.ArrayList;

/**
 * This class represents a face of a card.
 */
public class Face {
    private final Corner UpperLeft;
    private final Corner UpperRight;
    private final Corner LowerLeft;
    private final Corner LowerRight;
    private final ArrayList<ResourceType> CentralResources= new ArrayList<>();

    /**
     * Default constructor for a face with no central resources
     * @param Ur the upper left corner of the face
     * @param Ul the upper right corner of the face
     * @param Lr the lower right corner of the face
     * @param Ll the lower left corner of the face
     *
     */
    public Face(Corner Ur,Corner Ul,Corner Lr,Corner Ll){
        UpperRight = Ur;
        UpperLeft = Ul;
        LowerLeft = Ll;
        LowerRight = Lr;
    }
    /**
     * Default constructor
     * @param Ur the upper left corner of the face
     * @param Ul the upper right corner of the face
     * @param Lr the lower right corner of the face
     * @param Ll the lower left corner of the face
     * @param resource the resource the face has
     */
    public Face(Corner Ur,Corner Ul,Corner Lr,Corner Ll,ResourceType resource){
        UpperRight = Ur;
        UpperLeft = Ul;
        LowerLeft = Ll;
        LowerRight = Lr;
        CentralResources.add(resource);
    }

    /**
     * Secondary constructor for cards with two resources
     * @param Ur the upper left corner of the face
     * @param Ul the upper right corner of the face
     * @param Lr the lower right corner of the face
     * @param Ll the lower left corner of the face
     * @param resource the resource the face has
     * @param resource2 the second resource the face has
     */
    public Face(Corner Ur,Corner Ul,Corner Lr,Corner Ll,ResourceType resource, ResourceType resource2){
        UpperRight = Ur;
        UpperLeft = Ul;
        LowerLeft = Ll;
        LowerRight = Lr;
        CentralResources.add(resource);
        CentralResources.add(resource2);
    }

    /**
     * Secondary constructor for cards with three resources
     * @param Ur the upper left corner of the face
     * @param Ul the upper right upper
     * @param Lr the lower right corner of the face
     * @param Ll the lower left corner of the face
     * @param resource the resource the face has
     * @param resource2 the second resource the face has
     * @param resource3 the third resource the face has
     */
    public Face(Corner Ur,Corner Ul,Corner Lr,Corner Ll,ResourceType resource,ResourceType resource2, ResourceType resource3){
        UpperRight = Ur;
        UpperLeft = Ul;
        LowerLeft = Ll;
        LowerRight = Lr;
        CentralResources.add(resource);
        CentralResources.add(resource2);
        CentralResources.add(resource3);
    }

    /**
     * Returns the upper left corner of the face
     * @return the upper left corner of the face
     */
    public Corner getUpperLeft() {
        return UpperLeft;
    }

    /**
     * returns the upper right corner of the face
     * @return the upper right corner of the face
     */
    public Corner getUpperRight() {
        return UpperRight;
    }
    /**
     * returns the lower right corner of the face
     * @return the lower right corner of the face
     */
    public Corner getLowerLeft() {
        return LowerLeft;
    }
    /**
     * returns the lower left corner of the face
     * @return the lower left corner of the face
     */
    public Corner getLowerRight() {
        return LowerRight;
    }
    /**
     * returns the central resources of the face
     * @return the central resources of the face
     */
    public ArrayList<ResourceType> getCentralResources() {
        return CentralResources;
    }

    /**
     * return the corners as an arraylist
     * @return corners as an arraylist
     */
    public ArrayList<Corner> getCorners(){
        ArrayList<Corner> corners = new ArrayList<>();
        corners.add(UpperLeft);
        corners.add(UpperRight);
        corners.add(LowerLeft);
        corners.add(LowerRight);
        return corners;
    }
}
