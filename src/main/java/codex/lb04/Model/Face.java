package codex.lb04.Model;

import codex.lb04.Model.Enumerations.ResourceType;

import java.util.ArrayList;

public class Face {
    private final Corner UpperLeft;
    private final Corner UpperRight;
    private final Corner LowerLeft;
    private final Corner LowerRight;
    ArrayList<ResourceType> CentralResources= new ArrayList<ResourceType>();


    public Face(Corner Ur,Corner Ul,Corner Lr,Corner Ll,ResourceType resource){
        UpperRight = Ur;
        UpperLeft = Ul;
        LowerLeft = Ll;
        LowerRight = Lr;
        CentralResources.add(resource);
    }
    public Face(Corner Ur,Corner Ul,Corner Lr,Corner Ll,ResourceType resource, ResourceType resource2){
        UpperRight = Ur;
        UpperLeft = Ul;
        LowerLeft = Ll;
        LowerRight = Lr;
        CentralResources.add(resource);
        CentralResources.add(resource2);
    }
    public Face(Corner Ur,Corner Ul,Corner Lr,Corner Ll,ResourceType resource,ResourceType resource2, ResourceType resource3){
        UpperRight = Ur;
        UpperLeft = Ul;
        LowerLeft = Ll;
        LowerRight = Lr;
        CentralResources.add(resource);
        CentralResources.add(resource2);
        CentralResources.add(resource3);
    }

    public Corner getUpperLeft() {
        return UpperLeft;
    }

    public Corner getUpperRight() {
        return UpperRight;
    }

    public Corner getLowerLeft() {
        return LowerLeft;
    }

    public Corner getLowerRight() {
        return LowerRight;
    }

    public ArrayList<ResourceType> getCentralResources() {
        return CentralResources;
    }
}
