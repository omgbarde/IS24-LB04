package codex.lb04.View.Cli;

import codex.lb04.Model.Card;
import codex.lb04.Model.Corner;
import codex.lb04.Model.Enumerations.Color;
import codex.lb04.Model.Enumerations.ResourceType;
import codex.lb04.Model.GoldCard;

import java.util.ArrayList;
import java.util.List;

public class CardRenderer {

    private static final String resetColor = "\u001B[0m";

    private static String colorMap(Color color){
        switch (color){
            case RED:
                return "\u001B[31m";
            case GREEN:
                return "\u001B[32m";
            case BLUE:
                return "\u001B[36m";
            case PURPLE:
                return "\u001B[35m";
            default:
                return "";
        }
    }
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
                return null;
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

    public static String rederObjective(int ID) {
        switch (ID){
        case 87: return "2 points every 3 red cards in ascending diagonal";
        case 88: return "2 points every 3 green cards in descending diagonal";
        case 89: return "2 points every 3 blue cards in ascending diagonal";
        case 90: return  "2 points every 3 purple cards in descending diagonal";
        case 91: return "3 points every 'L' pattern:" +
                        " 2 red cards vertically and a green card diagonally right of the bottom card";
        case 92: return "3 points every 'L' pattern:" +
                        " 2 green cards vertically and a purple card diagonally left of the bottom card";
        case 93: return "3 points every 'L' pattern:" +
                        " 2 blue cards vertically and a red card diagonally right of the top card";
        case 94: return "3 points every 'L' pattern:" +
                        " 2 purple cards are vertically and a blue card diagonally left of the top card";
        case 95: return "2 points every 3 mushrooms visible";
        case 96: return "2 points every 3 leaves visible";
        case 97: return "2 points every 3 animals visible";
        case 98: return "2 points every 3 insects visible";
        case 99: return "3 points every set of inkwell-manuscript-quill visible";
        case 100: return "2 points every 2 manuscripts visible";
        case 101: return "2 points every 2 inkwells visible";
        case 102: return "2 points every 2 quills visible";
        default: return "";
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
        String colorCode = colorMap(card.getColor());
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

        stringBuilder.append(colorCode + "----" + resetColor);

        if (!upperRight.isCovered()){
            ResourceType resource = upperRight.getResource();
            if(resource!=null)stringBuilder.append(resourceMap(resource));
            else stringBuilder.append("⬜");
        }else stringBuilder.append("⬛");

        componentsArray[0] = stringBuilder.toString();
        
        stringBuilder = new StringBuilder();
        stringBuilder.append(colorCode + "|");
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
        stringBuilder.append("|" + resetColor);

        componentsArray[1] = stringBuilder.toString();

        stringBuilder = new StringBuilder();

        //lower part
        if (!lowerLeft.isCovered()){
            ResourceType resource = lowerLeft.getResource();
            if(resource!=null)stringBuilder.append(resourceMap(resource));
            else stringBuilder.append("⬜");
        }else stringBuilder.append("⬛");

        stringBuilder.append(colorCode + "----" + resetColor);

        if (!lowerRight.isCovered()){
            ResourceType resource = lowerRight.getResource();
            if(resource!=null)stringBuilder.append(resourceMap(resource));
            else stringBuilder.append("⬜");
        }else stringBuilder.append("⬛");

        componentsArray[2] = stringBuilder.toString();

        return componentsArray;
    }

    public static String[] placeHolder(String coordinates){
        String[]components = new String[3];

        components[0] = " ------- ";
        if(coordinates.isEmpty()) components[1] = "|       |";
        else if(coordinates.contains("-")) components[1] = "| "+coordinates+"|";
        else components[1] = "|  "+coordinates+"  |";
        components[2] = " ------- ";

        return components;
    }

    //non verrà usato
    public static String printInHand(Card card){
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
        String colorCode = colorMap(card.getColor());
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

        cardString.append(colorCode + "----------------" + resetColor);

        if (!upperRight.isCovered()){
            ResourceType r = upperRight.getResource();
            if(r!=null)cardString.append(resourceMap(r));
            else cardString.append("⬜");
        }else cardString.append("⬛");

        componentsArray[0] = cardString.toString();

        cardString = new StringBuilder();

        //get the points
        if(card.isShowingFront() && card.getPoints()!=0){
            cardString.append(colorCode + "|        "+points+multiplier +"       |" + resetColor);
        }
        else cardString.append(colorCode + "|                  |" + resetColor);

        componentsArray[1]=cardString.toString();

        //middle part
        cardString = new StringBuilder();
        cardString.append(colorCode+ "|"+ resetColor);
        switch(centralResources.size()){
            case 0:
                cardString.append("                  ");
                break;
            case 1:
                cardString.append("        ");
                cardString.append(resourceMap(centralResources.getFirst()));
                cardString.append("        ");
                break;
            case 2:
                cardString.append("       ");
                for(ResourceType r:centralResources){
                    cardString.append(resourceMap(r));
                }
                cardString.append("       ");
                break;
            case 3:
                cardString.append("     ");
                for(ResourceType r:centralResources){
                    cardString.append(resourceMap(r));
                }
                cardString.append("      ");
                break;
        }
        cardString.append(colorCode+ "|" + resetColor);
        componentsArray[2] = cardString.toString();

        cardString = new StringBuilder();

        //if the card is showing the front, draw the costs
        if(card.isShowingFront() && isGold) {
            switch(getNumberResourcesNeeded((GoldCard) card)){
                case 3:
                    cardString.append(colorCode+"|     "+resetColor);
                    for(int i = 0; i < 4; i++) {
                        for(int k = 0; k < resourcesNeededArray.get(i); k++) {
                            cardString.append(emojis.get(resourcesNeededArray.get(i)));
                        }
                    }
                    cardString.append(colorCode+"      |"+resetColor);
                    break;
                case 4:
                    cardString.append(colorCode+"|     "+resetColor);
                    for(int i = 0; i < 4; i++) {
                        for(int k = 0; k< resourcesNeededArray.get(i); k++) {
                            cardString.append(emojis.get(resourcesNeededArray.get(i)));
                        }
                    }
                    cardString.append(colorCode+"    |"+resetColor);
                    break;
                case 5:
                    cardString.append(colorCode+"|    "+resetColor);
                    for(int i = 0; i < 4; i++) {
                        for(int k = 0; k< resourcesNeededArray.get(i); k++) {
                            cardString.append(emojis.get(i));
                        }
                    }
                    cardString.append(colorCode+"   |"+resetColor);
                    break;
            }
        } else cardString.append(colorCode+"|                  |"+resetColor);
        componentsArray[3] = cardString.toString();

        cardString = new StringBuilder();
        //draw the corner
        if (!lowerLeft.isCovered()){
            ResourceType r = lowerLeft.getResource();
            if(r!=null)cardString.append(resourceMap(r));
            else cardString.append("⬜");
        }else cardString.append("⬛");

        cardString.append(colorCode + "----------------" + resetColor);

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

    private static Integer getNumberResourcesNeeded(GoldCard card){
        Integer cost=0;
        for (int i = 0; i < 4; i++) {
            cost += getResourcesNeeded(card).get(i);
        }
        return cost;
    }

}
