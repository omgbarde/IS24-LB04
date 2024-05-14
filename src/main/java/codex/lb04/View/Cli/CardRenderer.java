package codex.lb04.View.Cli;

import codex.lb04.Model.Card;
import codex.lb04.Model.Corner;
import codex.lb04.Model.Enumerations.Color;
import codex.lb04.Model.Enumerations.ResourceType;
import codex.lb04.Model.Face;
import codex.lb04.Model.ResourceCard;

public class CardRenderer {
    public static String cardToString(Card card) {
        StringBuilder cardString = new StringBuilder();
        ResourceType ul = card.getShownFace().getUpperLeft().getResource();
        ResourceType ur = card.getShownFace().getUpperRight().getResource();
        ResourceType lr = card.getShownFace().getLowerRight().getResource();
        ResourceType ll = card.getShownFace().getLowerLeft().getResource();
        if (ul !=null) {
            switch (ul) {
                case INSECT:
                    cardString.append("[\uD83E\uDD8B]");
                    break;
                case MUSHROOM:
                    cardString.append("[\uD83C\uDF44]");
                    break;
                case ANIMAL:
                    cardString.append("[\uD83E\uDD8A]");
                    break;
                case LEAF:
                    cardString.append("[\uD83C\uDF43]");
                    break;
                case QUILL:
                    cardString.append("[\uD83E\uDEB6]");
                    break;
                case INKWELL:
                    cardString.append("[\uD83E\uDED9]");
                    break;
                case MANUSCRIPT:
                    cardString.append("[\uD83D\uDCDC]");
                    break;
                default:
                   break;
            }
        }else  cardString.append("[X]");

        if(ur != null) {
            switch (ur) {
                case INSECT:
                    cardString.append("---[\uD83E\uDD8B]");
                    break;
                case MUSHROOM:
                    cardString.append("---[\uD83C\uDF44]");
                    break;
                case ANIMAL:
                    cardString.append("---[\uD83E\uDD8A]");
                    break;
                case LEAF:
                    cardString.append("---[\uD83C\uDF43]");
                    break;
                case QUILL:
                    cardString.append("---[\uD83E\uDEB6]");
                    break;
                case INKWELL:
                    cardString.append("---[\uD83E\uDED9]");
                    break;
                case MANUSCRIPT:
                    cardString.append("---[\uD83D\uDCDC]");
                    break;
                default:
                    break;
            }
        }
        else cardString.append("---[X]");

        cardString.append("\n");
        cardString.append("|      |\n");

        if(ll!=null) {
            switch (ll) {
                case INSECT:
                    cardString.append("[\uD83E\uDD8B]");
                    break;
                case MUSHROOM:
                    cardString.append("[\uD83C\uDF44]");
                    break;
                case ANIMAL:
                    cardString.append("[\uD83E\uDD8A]");
                    break;
                case LEAF:
                    cardString.append("[\uD83C\uDF43]");
                    break;
                case QUILL:
                    cardString.append("[\uD83E\uDEB6]");
                    break;
                case INKWELL:
                    cardString.append("[\uD83E\uDED9]");
                    break;
                case MANUSCRIPT:
                    cardString.append("[\uD83D\uDCDC]");
                    break;
                default:
                    break;
            }
        } else cardString.append("[X]");

        if (lr != null) {
            switch (lr) {
                case INSECT:
                    cardString.append("---[\uD83E\uDD8B]");
                    break;
                case MUSHROOM:
                    cardString.append("---[\uD83C\uDF44]");
                    break;
                case ANIMAL:
                    cardString.append("---[\uD83E\uDD8A]");
                    break;
                case LEAF:
                    cardString.append("---[\uD83C\uDF43]");
                    break;
                case QUILL:
                    cardString.append("---[\uD83E\uDEB6]");
                    break;
                case INKWELL:
                    cardString.append("---[\uD83E\uDED9]");
                    break;
                case MANUSCRIPT:
                    cardString.append("---[\uD83D\uDCDC]");
                    break;
                default:
                    break;
            }
        }else cardString.append("---[X]");

        return cardString.toString();
    }

    public static void main(String[] args) {
        Corner corner_Ur_rc6 = new Corner(ResourceType.MUSHROOM);
        Corner corner_Lr_rc6 = new Corner(ResourceType.ANIMAL);
        Corner corner_Ul_rc6 = new Corner(ResourceType.INKWELL);
        Corner corner_Ll_rc6 = new Corner(true);
        Corner cornerB_Ur_rc6 = new Corner(false);
        Corner cornerB_Ul_rc6 = new Corner(false);
        Corner cornerB_Lr_rc6 = new Corner(false);
        Corner cornerB_Ll_rc6 = new Corner(false);
        Face front_rc6 = new Face(corner_Ur_rc6, corner_Ul_rc6, corner_Lr_rc6, corner_Ll_rc6);
        Face back_rc6 = new Face(cornerB_Ur_rc6, cornerB_Ul_rc6, cornerB_Lr_rc6, cornerB_Ll_rc6, ResourceType.MUSHROOM);
        ResourceCard resource_card_6 = new ResourceCard(Color.RED, front_rc6, back_rc6, 0, 6);
        resource_card_6.setShowingFront(true);
        System.out.println(cardToString(resource_card_6));
    }
}
