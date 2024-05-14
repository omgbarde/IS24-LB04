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
     * This method converts a card to a string representation to be printed in CLI mode
     * @param card the card to be converted
     * @return the string representation of the card
     */
    public static String cardToString(Card card) {
        StringBuilder cardString = new StringBuilder();
        ArrayList<Integer> resourcesNeededArray = new ArrayList<>();

        //get the corners of the card
        Corner upperLeft = card.getShownFace().getUpperLeft();
        Corner upperRight = card.getShownFace().getUpperRight();
        Corner lowerRight = card.getShownFace().getLowerRight();
        Corner lowerLeft = card.getShownFace().getLowerLeft();
        ArrayList<Corner> corners = new ArrayList<>();
        corners.addAll(List.of(upperLeft, upperRight, lowerRight, lowerLeft));
        ArrayList<ResourceType> centralResources = card.getShownFace().getCentralResources();

        //get the points of the card
        int points = card.getPoints();

        //get the array of costs for the card
        boolean isGold = card.getClass() == GoldCard.class;
        if(isGold){
            resourcesNeededArray = getResourcesNeeded((GoldCard) card);
        }

        //padding1 as padding
        int padding1 = 3 - centralResources.size();
        int padding2 = 4 - centralResources.size();

        for (int i=0; i<4;i++){
            //draw the corner
            if (!corners.get(i).isCovered()){
                ResourceType r = corners.get(i).getResource();
                if(r!=null)cardString.append(resourceMap(r));
                else cardString.append("⬜");
            }else cardString.append("⬛");

            //draw body of card
            if(i==1){
                //if the card is showing the front, draw the points
                if(card.isShowingFront()) cardString.append("\n|         "+points+"        |\n");
                else cardString.append("\n|                  |\n");
                cardString.append("|");

                //draw the central resources with padding
                for(int j = 0; j < padding1; j++ ){
                    cardString.append("   ");
                }
                for(ResourceType r:centralResources){
                    cardString.append("("+resourceMap(r)+")");
                }
                for(int j = 0; j < padding1; j++ ){
                    cardString.append("   ");
                }
                cardString.append("|\n");

                //if the card is showing the front, draw the costs
                if(card.isShowingFront() && isGold) {
                    cardString.append("| ");
                    for (int k = 0; k < resourcesNeededArray.size(); k++) {
                        cardString.append("(" + resourcesNeededArray.get(k) + ")");
                    }
                    cardString.append("  |\n");
                }
                else cardString.append("|                  |\n");
            }
            //draw the edge of the card
            if(i%2==0) cardString.append("----------------");
        }
        return cardString.toString();
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
        costArray.add(card.getAnimals_needed());
        costArray.add(card.getInsects_needed());
        costArray.add(card.getLeaf_needed());
        return costArray;
    }
}
