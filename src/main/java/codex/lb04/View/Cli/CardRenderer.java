package codex.lb04.View.Cli;

import codex.lb04.Model.Card;
import codex.lb04.Model.Corner;
import codex.lb04.Model.Enumerations.Color;
import codex.lb04.Model.Enumerations.ResourceType;
import codex.lb04.Model.GoldCard;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for rendering the cards with characters and emojis
 */
public class CardRenderer {
    //code characters to reset the color of the text
    private static final String resetCode = "\u001B[0m";

    /**
     * this method returns the color code to use based on the color of the card you want to render
     * @param color the color of the card
     * @return the color code
     */
    private static String colorMap(Color color){
        return switch (color) {
            case RED -> "\u001B[31m";
            case GREEN -> "\u001B[32m";
            case BLUE -> "\u001B[36m";
            case PURPLE -> "\u001B[35m";
            default -> "";
        };
    }

    /**
     * This method converts resource types to emojis
     * @param r the resource type to be converted
     * @return the emoji representation of the resource type
     */
    private static String resourceMap(ResourceType r) {
        return switch (r) {
            case INSECT -> "\uD83E\uDD8B";
            case MUSHROOM -> "\uD83C\uDF44";
            case ANIMAL -> "\uD83E\uDD8A";
            case LEAF -> "\uD83C\uDF43";
            case QUILL -> "\uD83E\uDEB6";
            case INKWELL -> "\uD83E\uDED9";
            case MANUSCRIPT -> "\uD83D\uDCDC";
        };
    }

    /**
     * This method converts resource types to emojis
     * @param ID the id of the card
     * @return the emoji representation of the multiplier
     */
    private static String multiplierMap(Integer ID) {
        return switch (ID) {
            case 41, 51, 63, 71 -> "\uD83E\uDEB6"; //quill
            case 42, 53, 61, 73 -> "\uD83E\uDED9"; //inkwell
            case 43, 52, 62, 72 -> "\uD83D\uDCDC"; //manuscript
            case 44, 45, 46, 54, 55, 56, 64, 65, 66, 74, 75, 76 -> "⬛"; //angle
            default -> "  ";
        };
    }

    /**
     * This method renders the objective of the card
     * @param ID the id of the card
     * @return the string representing the objective of the card
     */
    public static String renderObjective(int ID) {
        return switch (ID) {
            case 87 -> "2 points every 3 red cards in ascending diagonal";
            case 88 -> "2 points every 3 green cards in descending diagonal";
            case 89 -> "2 points every 3 blue cards in ascending diagonal";
            case 90 -> "2 points every 3 purple cards in descending diagonal";
            case 91 -> "3 points every 'L' pattern:" +
                    " 2 red cards vertically and a green card diagonally right of the bottom card";
            case 92 -> "3 points every 'L' pattern:" +
                    " 2 green cards vertically and a purple card diagonally left of the bottom card";
            case 93 -> "3 points every 'L' pattern:" +
                    " 2 blue cards vertically and a red card diagonally right of the top card";
            case 94 -> "3 points every 'L' pattern:" +
                    " 2 purple cards are vertically and a blue card diagonally left of the top card";
            case 95 -> "2 points every 3 mushrooms visible";
            case 96 -> "2 points every 3 leaves visible";
            case 97 -> "2 points every 3 animals visible";
            case 98 -> "2 points every 3 insects visible";
            case 99 -> "3 points every set of inkwell-manuscript-quill visible";
            case 100 -> "2 points every 2 manuscripts visible";
            case 101 -> "2 points every 2 inkwells visible";
            case 102 -> "2 points every 2 quills visible";
            default -> "";
        };
    }

    /**
     * method to print the card in board format (used only as a test method)
     * @param card the card to be printed
     * @return the string representing the card already formatted
     */
    public String printInGame(Card card){
        String[] components = renderInGame(card);
        StringBuilder stringBuilder = new StringBuilder();
        for(String s:components){
            stringBuilder.append(s);
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    /**
     * This method renders the card in the game
     * @param card the card to be rendered
     * @return the array of string components representing the card
     */
    public static String[] renderInGame(Card card){
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
        //render upper part
        renderEdgeInGame(stringBuilder, colorCode, upperLeft, upperRight);

        componentsArray[0] = stringBuilder.toString();
        
        stringBuilder = new StringBuilder();
        stringBuilder.append(colorCode).append("|");
        //middle part
        stringBuilder.append(" ".repeat(Math.max(0, padding)));

        for(ResourceType r:centralResources){
            stringBuilder.append(resourceMap(r));
        }
        stringBuilder.append(" ".repeat(Math.max(0, padding)));

        stringBuilder.append("|" + resetCode);

        componentsArray[1] = stringBuilder.toString();

        stringBuilder = new StringBuilder();

        //render lower part
        renderEdgeInGame(stringBuilder, colorCode, lowerLeft, lowerRight);

        componentsArray[2] = stringBuilder.toString();

        return componentsArray;
    }

    /**
     * This method renders the edge of the card (upper/lower)
     * @param stringBuilder the string builder to append the components
     * @param colorCode the color code of the card
     * @param corner1 one of the corners of the card
     * @param corner2 another corner of the card
     */
    private static void renderEdgeInGame(StringBuilder stringBuilder, String colorCode, Corner corner1, Corner corner2) {
        if (!corner1.isCovered()){
            ResourceType resource = corner1.getResource();
            if(resource!=null)stringBuilder.append(resourceMap(resource));
            else stringBuilder.append("⬜");
        }else stringBuilder.append("⬛");

        stringBuilder.append(colorCode).append("----").append(resetCode);

        if (!corner2.isCovered()){
            ResourceType resource = corner2.getResource();
            if(resource!=null)stringBuilder.append(resourceMap(resource));
            else stringBuilder.append("⬜");
        }else stringBuilder.append("⬛");
    }

    /**
     * This method renders the placeholder for the card in the board
     * @param coordinates the coordinates of the card
     * @return the array of string components representing the placeholder
     */
    public static String[] renderPlaceHolder(String coordinates){
        String[]components = new String[3];
        int padding = 7 - coordinates.length();
        components[0] = " ------- ";

        String middle = "|" + coordinates + " ".repeat(Math.max(0, padding)) + "|";

        components[1] = middle;
        components[2] = " ------- ";

        return components;
    }

    /**
     * method to print the card in hand format (to use as a test method or to draw the initial card)
     * @param card the card to be printed
     * @return the string representing the card already formatted
     */
    public static String printInHand(Card card){
        String[] components = renderInHand(card);
        StringBuilder stringBuilder = new StringBuilder();
        for(String s:components){
            stringBuilder.append(s);
            stringBuilder.append("  \n");
        }
        return stringBuilder.toString();
    }

    /**
     * This method renders the card in the hand
     * @param card the card to be rendered
     * @return the array of string components representing the card
     */
    public static String[] renderInHand(Card card){
        if (card == null) return new String[]{"              ","                ","               ","             ","                "};
        StringBuilder cardString = new StringBuilder();
        String colorCode = colorMap(card.getColor());
        ArrayList<Integer> resourcesNeededArray = new ArrayList<>();
        ArrayList<String> emojis = new ArrayList<>(List.of("\uD83C\uDF44", "\uD83E\uDD8A", "\uD83E\uDD8B", "\uD83C\uDF43"));
        String multiplier = multiplierMap(card.getID());
        //get the corners of the card
        Corner upperLeft = card.getShownFace().getUpperLeft();
        Corner upperRight = card.getShownFace().getUpperRight();
        Corner lowerRight = card.getShownFace().getLowerRight();
        Corner lowerLeft = card.getShownFace().getLowerLeft();

        ArrayList<ResourceType> centralResources = card.getShownFace().getCentralResources();
        String[] componentsArray = new String[5];

        //get the points of the card
        int points = card.getPoints();

        //get the array of costs for the card
        boolean isGold = card.getClass() == GoldCard.class;
        if(isGold){
            resourcesNeededArray = getResourcesNeeded((GoldCard) card);
        }

        //render upper part
        renderEdgeInHand(cardString, colorCode, upperLeft, upperRight);

        componentsArray[0] = cardString.toString();

        cardString = new StringBuilder();

        //get the points
        if(card.isShowingFront() && card.getPoints()!=0){
            cardString.append(colorCode).append("|        ").append(points).append(multiplier).append("       |").append(resetCode);
        }
        else cardString.append(colorCode).append("|                  |").append(resetCode);

        componentsArray[1]=cardString.toString();

        //middle part
        cardString = new StringBuilder();
        cardString.append(colorCode).append("|").append(resetCode);
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
        cardString.append(colorCode).append("|").append(resetCode);
        componentsArray[2] = cardString.toString();

        cardString = new StringBuilder();

        //if the card is showing the front, draw the costs
        if(card.isShowingFront() && isGold) {
            switch(getNumberResourcesNeeded((GoldCard) card)){
                case 3:
                    cardString.append(colorCode).append("|     ").append(resetCode);
                    for(int i = 0; i < 4; i++) {
                        cardString.append(String.valueOf(emojis.get(resourcesNeededArray.get(i))).repeat(Math.max(0, resourcesNeededArray.get(i))));
                    }
                    cardString.append(colorCode).append("      |").append(resetCode);
                    break;
                case 4:
                    cardString.append(colorCode).append("|     ").append(resetCode);
                    for(int i = 0; i < 4; i++) {
                        cardString.append(String.valueOf(emojis.get(resourcesNeededArray.get(i))).repeat(Math.max(0, resourcesNeededArray.get(i))));
                    }
                    cardString.append(colorCode).append("    |").append(resetCode);
                    break;
                case 5:
                    cardString.append(colorCode).append("|    ").append(resetCode);
                    for(int i = 0; i < 4; i++) {
                        cardString.append(String.valueOf(emojis.get(i)).repeat(Math.max(0, resourcesNeededArray.get(i))));
                    }
                    cardString.append(colorCode).append("   |").append(resetCode);
                    break;
            }
        } else cardString.append(colorCode).append("|                  |").append(resetCode);
        componentsArray[3] = cardString.toString();

        cardString = new StringBuilder();

        //render lower part
        renderEdgeInHand(cardString, colorCode, lowerLeft, lowerRight);

        componentsArray[4] = cardString.toString();

        return componentsArray;
    }

    /**
     * This method renders the edge of the card (upper/lower) in the hand
     * @param cardString the string builder to append the components
     * @param colorCode the color code of the card
     * @param corner1 one of the corners of the card
     * @param corner2 another corner of the card
     */
    private static void renderEdgeInHand(StringBuilder cardString, String colorCode, Corner corner1, Corner corner2) {
        if (!corner1.isCovered()){
            ResourceType r = corner1.getResource();
            if(r!=null)cardString.append(resourceMap(r));
            else cardString.append("⬜");
        }else cardString.append("⬛");

        cardString.append(colorCode).append("----------------").append(resetCode);

        if (!corner2.isCovered()){
            ResourceType r = corner2.getResource();
            if(r!=null)cardString.append(resourceMap(r));
            else cardString.append("⬜");
        }else cardString.append("⬛");
    }

    /**
     * This method returns the resources needed for a gold card
     * @param card the gold card
     * @return the array list of resources needed for the gold card
     */
    private static ArrayList<Integer> getResourcesNeeded(GoldCard card) {
        ArrayList<Integer> costArray = new ArrayList<>();
        costArray.add(card.getMushroom_needed());
        costArray.add(card.getAnimals_needed());
        costArray.add(card.getInsects_needed());
        costArray.add(card.getLeaf_needed());
        return costArray;
    }

    /**
     * This method returns the number of resources needed for a gold card
     * @param card the gold card
     * @return the number of resources needed for the gold card
     */
    private static Integer getNumberResourcesNeeded(GoldCard card){
        Integer cost=0;
        for (int i = 0; i < 4; i++) {
            cost += getResourcesNeeded(card).get(i);
        }
        return cost;
    }

}