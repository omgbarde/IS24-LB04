package codex.lb04.View.Cli;

import codex.lb04.Model.Card;
import codex.lb04.Model.Corner;
import codex.lb04.Model.Enumerations.ResourceType;
import codex.lb04.Model.GoldCard;

import java.util.ArrayList;
import java.util.List;

public class CardRenderer {

    /**
     * This method converts resource types to emojis
     * @param r the resource type to be converted
     * @return the emoji representation of the resource type
     */
    private static String resourceMap(ResourceType r) {
        switch (r) {
            case INSECT:
                return "\uD83E\uDD8B";
            case MUSHROOM:
                return "\uD83C\uDF44";
            case ANIMAL:
                return "\uD83E\uDD8A";
            case LEAF:
                return "\uD83C\uDF43";
            case QUILL:
                return "\uD83E\uDEB6";
            case INKWELL:
                return "\uD83E\uDED9";
            case MANUSCRIPT:
                return "\uD83D\uDCDC";
            default:
                return "  ";
        }
    }

    /**
     * This method converts resource types to emojis
     * @param ID the id of the card
     * @return the emoji representation of the multiplier
     */
    private static String multiplierMap(Integer ID) {
        switch (ID) {
            case 41, 51, 63, 71:
                return "\uD83E\uDEB6"; //quill
            case 42, 53, 61, 73:
                return "\uD83E\uDED9"; //inkwell
            case 43, 52, 62, 72:
                return "\uD83D\uDCDC"; //manuscript
            case 44, 45, 46, 54, 55, 56, 64, 65, 66, 74, 75, 76:
                return "⬛"; //angle
            default:
                return "  ";
        }
    }

    //non verrà usato
    public String printInGame(Card card){
        String[] components = renderIngame(card);
        StringBuilder stringBuilder = new StringBuilder();
        for(String s:components){
            stringBuilder.append(s);
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public static String[] renderIngame(Card card){
        StringBuilder stringBuilder = new StringBuilder();
        //get the corners of the card
        Corner upperLeft = card.getShownFace().getUpperLeft();
        Corner upperRight = card.getShownFace().getUpperRight();
        Corner lowerRight = card.getShownFace().getLowerRight();
        Corner lowerLeft = card.getShownFace().getLowerLeft();
        ArrayList<ResourceType> centralResources = card.getShownFace().getCentralResources();

        int padding = 3 - centralResources.size();

        String[] componentsArray = new String[3];
        //upper part    
        if (!upperLeft.isCovered()){
            ResourceType resource = upperLeft.getResource();
            if(resource!=null)stringBuilder.append(resourceMap(resource));
            else stringBuilder.append("⬜");
        }else stringBuilder.append("⬛");

        stringBuilder.append("----");

        if (!upperRight.isCovered()){
            ResourceType resource = upperRight.getResource();
            if(resource!=null)stringBuilder.append(resourceMap(resource));
            else stringBuilder.append("⬜");
        }else stringBuilder.append("⬛");

        componentsArray[0] = stringBuilder.toString();
        
        stringBuilder = new StringBuilder();
        stringBuilder.append("|");
        //middle part
        for(int i = 0; i < padding; i++ ){
            stringBuilder.append(" ");
        }
        for(ResourceType r:centralResources){
            stringBuilder.append(resourceMap(r));
        }
        for(int i = 0; i < padding; i++ ){
            stringBuilder.append(" ");
        }
        stringBuilder.append("|");

        componentsArray[1] = stringBuilder.toString();

        stringBuilder = new StringBuilder();

        //lower part
        if (!lowerLeft.isCovered()){
            ResourceType resource = lowerLeft.getResource();
            if(resource!=null)stringBuilder.append(resourceMap(resource));
            else stringBuilder.append("⬜");
        }else stringBuilder.append("⬛");

        stringBuilder.append("----");

        if (!lowerRight.isCovered()){
            ResourceType resource = lowerRight.getResource();
            if(resource!=null)stringBuilder.append(resourceMap(resource));
            else stringBuilder.append("⬜");
        }else stringBuilder.append("⬛");

        componentsArray[2] = stringBuilder.toString();

        return componentsArray;
    }

    public static String[] placeHolder(){
        String[]components = new String[3];

        components[0] = " ------ ";
        components[1] = "|      |";
        components[2] = " ------ ";

        return components;
    }

    //non verrà usato
    public String printInHand(Card card){
        String[] components = renderInHand(card);
        StringBuilder stringBuilder = new StringBuilder();
        for(String s:components){
            stringBuilder.append(s);
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public static String[] renderInHand(Card card){
        StringBuilder cardString = new StringBuilder();
        ArrayList<Integer> resourcesNeededArray = new ArrayList<>();
        ArrayList<String> emojis = new ArrayList<>();
        emojis.addAll(List.of("\uD83C\uDF44","\uD83E\uDD8A","\uD83E\uDD8B","\uD83C\uDF43"));
        String multiplier = multiplierMap(card.getID());
        //get the corners of the card
        Corner upperLeft = card.getShownFace().getUpperLeft();
        Corner upperRight = card.getShownFace().getUpperRight();
        Corner lowerRight = card.getShownFace().getLowerRight();
        Corner lowerLeft = card.getShownFace().getLowerLeft();
        ArrayList<Corner> corners = new ArrayList<>();
        corners.addAll(List.of(upperLeft, upperRight, lowerRight, lowerLeft));
        ArrayList<ResourceType> centralResources = card.getShownFace().getCentralResources();
        String[] componentsArray = new String[5];

        //get the points of the card
        int points = card.getPoints();

        //get the array of costs for the card
        boolean isGold = card.getClass() == GoldCard.class;
        if(isGold){
            resourcesNeededArray = getResourcesNeeded((GoldCard) card);
        }


        //draw the corner
        if (!upperLeft.isCovered()){
            ResourceType r = upperLeft.getResource();
            if(r!=null)cardString.append(resourceMap(r));
            else cardString.append("⬜");
        }else cardString.append("⬛");

        cardString.append("----------------");

        if (!upperRight.isCovered()){
            ResourceType r = upperRight.getResource();
            if(r!=null)cardString.append(resourceMap(r));
            else cardString.append("⬜");
        }else cardString.append("⬛");

        componentsArray[0] = cardString.toString();

        cardString = new StringBuilder();

        //get the points
        if(card.isShowingFront() && card.getPoints()!=0){
            cardString.append("|        "+points+multiplier +"       |");
        }
        else cardString.append("|                  |");

        componentsArray[1]=cardString.toString();

        //middle part
        cardString = new StringBuilder();
        int padding1 = 3 - centralResources.size();
        cardString.append("|");
        for(int j = 0; j < padding1; j++ ){
            cardString.append("   ");
        }
        for(ResourceType r:centralResources){
            cardString.append(resourceMap(r));
        }
        for(int i = 0; i < padding1; i++ ){
            cardString.append("   ");
        }
        cardString.append("|");
        componentsArray[2] = cardString.toString();

        cardString = new StringBuilder();

        //if the card is showing the front, draw the costs
        if(card.isShowingFront() && isGold) {
            cardString.append("| ");
            for (int j = 0; j < resourcesNeededArray.size(); j++) {
                for (int k = 0; k<resourcesNeededArray.get(j); k++) {
                    cardString.append(emojis.get(j));
                }
            }
            cardString.append("  |");
        } else cardString.append("|                  |");
        componentsArray[3] = cardString.toString();

        cardString = new StringBuilder();
        //draw the corner
        if (!lowerLeft.isCovered()){
            ResourceType r = lowerLeft.getResource();
            if(r!=null)cardString.append(resourceMap(r));
            else cardString.append("⬜");
        }else cardString.append("⬛");

        cardString.append("----------------");

        if (!lowerRight.isCovered()){
            ResourceType r = lowerRight.getResource();
            if(r!=null)cardString.append(resourceMap(r));
            else cardString.append("⬜");
        }else cardString.append("⬛");

        componentsArray[4] = cardString.toString();

        return componentsArray;
    }

    /**
     * This method returns the resources needed for a gold card
     * @param card the gold card
     * @return the array of resources needed for the gold card
     */
    private static ArrayList<Integer> getResourcesNeeded(GoldCard card) {
        ArrayList<Integer> costArray = new ArrayList<>();
        costArray.add(card.getMushroom_needed());
        costArray.add(card.getAnimals_needed());
        costArray.add(card.getInsects_needed());
        costArray.add(card.getLeaf_needed());
        return costArray;
    }

}
