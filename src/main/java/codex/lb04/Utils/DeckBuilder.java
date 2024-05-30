package codex.lb04.Utils;

import codex.lb04.Model.*;
import codex.lb04.Model.Enumerations.Color;
import codex.lb04.Model.Enumerations.ResourceType;
import com.google.gson.Gson;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

/**
 * this class builds the deck of cards and serializes it to the resources folder
 */
public class DeckBuilder implements Serializable {

    @Serial
    private static final long serialVersionUID = 604381147694715L;

    public ArrayList<ResourceCard> resourceCards;
    public ArrayList<GoldCard> goldCards;
    public ArrayList<ObjectiveCard> objectiveCards;
    public ArrayList<InitialCard> initialCards;

    public static void main(String[] args) {
        DeckBuilder toSerialize = new DeckBuilder();

        addCards(toSerialize);

        serialize(toSerialize);

    }

    /**
     * this method generates all the lists in the deck builder
     *
     * @param builder the deck builder you want to add cards to
     */
    private static void addCards(DeckBuilder builder) {
        //creates all cards
        builder.resourceCards = createResourceCards();
        builder.goldCards = createGoldCards();
        builder.objectiveCards = createObjectiveCards();
        builder.initialCards = createInitialCards();
    }

    /**
     * this method creates the resource cards of the deck and returns them as a list
     *
     * @return the deck of cards
     */
    private static ArrayList<ResourceCard> createResourceCards() {
        ArrayList<ResourceCard> resourceCards = new ArrayList<>();

        //red resource cards
        //red card 1
        Corner corner_Ur_rc1 = new Corner(false);
        Corner corner_Lr_rc1 = new Corner(true);
        Corner corner_Ul_rc1 = new Corner(ResourceType.MUSHROOM);
        Corner corner_Ll_rc1 = new Corner(ResourceType.MUSHROOM);
        Corner cornerB_Ur_rc1 = new Corner(false);
        Corner cornerB_Ul_rc1 = new Corner(false);
        Corner cornerB_Lr_rc1 = new Corner(false);
        Corner cornerB_Ll_rc1 = new Corner(false);
        Face front_rc1 = new Face(corner_Ur_rc1, corner_Ul_rc1, corner_Lr_rc1, corner_Ll_rc1);
        Face back_rc1 = new Face(cornerB_Ur_rc1, cornerB_Ul_rc1, cornerB_Lr_rc1, cornerB_Ll_rc1, ResourceType.MUSHROOM);
        ResourceCard resource_card_1 = new ResourceCard(Color.RED, front_rc1, back_rc1, 0, 1);
        resource_card_1.setShowingFront(false);
        resourceCards.add(resource_card_1);

        //red card 2
        Corner corner_Ur_rc2 = new Corner(ResourceType.MUSHROOM);
        Corner corner_Lr_rc2 = new Corner(false);
        Corner corner_Ul_rc2 = new Corner(ResourceType.MUSHROOM);
        Corner corner_Ll_rc2 = new Corner(true);
        Corner cornerB_Ur_rc2 = new Corner(false);
        Corner cornerB_Ul_rc2 = new Corner(false);
        Corner cornerB_Lr_rc2 = new Corner(false);
        Corner cornerB_Ll_rc2 = new Corner(false);
        Face front_rc2 = new Face(corner_Ur_rc2, corner_Ul_rc2, corner_Lr_rc2, corner_Ll_rc2);
        Face back_rc2 = new Face(cornerB_Ur_rc2, cornerB_Ul_rc2, cornerB_Lr_rc2, cornerB_Ll_rc2, ResourceType.MUSHROOM);
        ResourceCard resource_card_2 = new ResourceCard(Color.RED, front_rc2, back_rc2, 0, 2);
        resource_card_2.setShowingFront(false);
        resourceCards.add(resource_card_2);


        //red card 3
        Corner corner_Ur_rc3 = new Corner(true);
        Corner corner_Lr_rc3 = new Corner(ResourceType.MUSHROOM);
        Corner corner_Ul_rc3 = new Corner(false);
        Corner corner_Ll_rc3 = new Corner(ResourceType.MUSHROOM);
        Corner cornerB_Ur_rc3 = new Corner(false);
        Corner cornerB_Ul_rc3 = new Corner(false);
        Corner cornerB_Lr_rc3 = new Corner(false);
        Corner cornerB_Ll_rc3 = new Corner(false);
        Face front_rc3 = new Face(corner_Ur_rc3, corner_Ul_rc3, corner_Lr_rc3, corner_Ll_rc3);
        Face back_rc3 = new Face(cornerB_Ur_rc3, cornerB_Ul_rc3, cornerB_Lr_rc3, cornerB_Ll_rc3, ResourceType.MUSHROOM);
        ResourceCard resource_card_3 = new ResourceCard(Color.RED, front_rc3, back_rc3, 0, 3);
        resource_card_3.setShowingFront(false);
        resourceCards.add(resource_card_3);

        //red card 4
        Corner corner_Ur_rc4 = new Corner(ResourceType.MUSHROOM);
        Corner corner_Lr_rc4 = new Corner(ResourceType.MUSHROOM);
        Corner corner_Ul_rc4 = new Corner(true);
        Corner corner_Ll_rc4 = new Corner(false);
        Corner cornerB_Ur_rc4 = new Corner(false);
        Corner cornerB_Ul_rc4 = new Corner(false);
        Corner cornerB_Lr_rc4 = new Corner(false);
        Corner cornerB_Ll_rc4 = new Corner(false);
        Face front_rc4 = new Face(corner_Ur_rc4, corner_Ul_rc4, corner_Lr_rc4, corner_Ll_rc4);
        Face back_rc4 = new Face(cornerB_Ur_rc4, cornerB_Ul_rc4, cornerB_Lr_rc4, cornerB_Ll_rc4, ResourceType.MUSHROOM);
        ResourceCard resource_card_4 = new ResourceCard(Color.RED, front_rc4, back_rc4, 0, 4);
        resource_card_4.setShowingFront(false);
        resourceCards.add(resource_card_4);

        //red card 5
        Corner corner_Ur_rc5 = new Corner(ResourceType.QUILL);
        Corner corner_Lr_rc5 = new Corner(ResourceType.MUSHROOM);
        Corner corner_Ul_rc5 = new Corner(true);
        Corner corner_Ll_rc5 = new Corner(ResourceType.LEAF);
        Corner cornerB_Ur_rc5 = new Corner(false);
        Corner cornerB_Ul_rc5 = new Corner(false);
        Corner cornerB_Lr_rc5 = new Corner(false);
        Corner cornerB_Ll_rc5 = new Corner(false);
        Face front_rc5 = new Face(corner_Ur_rc5, corner_Ul_rc5, corner_Lr_rc5, corner_Ll_rc5);
        Face back_rc5 = new Face(cornerB_Ur_rc5, cornerB_Ul_rc5, cornerB_Lr_rc5, cornerB_Ll_rc5, ResourceType.MUSHROOM);
        ResourceCard resource_card_5 = new ResourceCard(Color.RED, front_rc5, back_rc5, 0, 5);
        resource_card_5.setShowingFront(false);
        resourceCards.add(resource_card_5);

        //red card 6
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
        resource_card_6.setShowingFront(false);
        resourceCards.add(resource_card_6);

        //red card 7
        Corner corner_Ur_rc7 = new Corner(ResourceType.INSECT);
        Corner corner_Lr_rc7 = new Corner(false);
        Corner corner_Ul_rc7 = new Corner(ResourceType.MUSHROOM);
        Corner corner_Ll_rc7 = new Corner(ResourceType.MANUSCRIPT);
        Corner cornerB_Ur_rc7 = new Corner(false);
        Corner cornerB_Ul_rc7 = new Corner(false);
        Corner cornerB_Lr_rc7 = new Corner(false);
        Corner cornerB_Ll_rc7 = new Corner(false);
        Face front_rc7 = new Face(corner_Ur_rc7, corner_Ul_rc7, corner_Lr_rc7, corner_Ll_rc7);
        Face back_rc7 = new Face(cornerB_Ur_rc7, cornerB_Ul_rc7, cornerB_Lr_rc7, cornerB_Ll_rc7, ResourceType.MUSHROOM);
        ResourceCard resource_card_7 = new ResourceCard(Color.RED, front_rc7, back_rc7, 0, 7);
        resource_card_7.setShowingFront(false);
        resourceCards.add(resource_card_7);

        //red card 8
        Corner corner_Ur_rc8 = new Corner(ResourceType.MUSHROOM);
        Corner corner_Lr_rc8 = new Corner(true);
        Corner corner_Ul_rc8 = new Corner(false);
        Corner corner_Ll_rc8 = new Corner(false);
        Corner cornerB_Ur_rc8 = new Corner(false);
        Corner cornerB_Ul_rc8 = new Corner(false);
        Corner cornerB_Lr_rc8 = new Corner(false);
        Corner cornerB_Ll_rc8 = new Corner(false);
        Face front_rc8 = new Face(corner_Ur_rc8, corner_Ul_rc8, corner_Lr_rc8, corner_Ll_rc8);
        Face back_rc8 = new Face(cornerB_Ur_rc8, cornerB_Ul_rc8, cornerB_Lr_rc8, cornerB_Ll_rc8, ResourceType.MUSHROOM);
        ResourceCard resource_card_8 = new ResourceCard(Color.RED, front_rc8, back_rc8, 1, 8);
        resource_card_8.setShowingFront(false);
        resourceCards.add(resource_card_8);

        //red card 9
        Corner corner_Ur_rc9 = new Corner(true);
        Corner corner_Lr_rc9 = new Corner(false);
        Corner corner_Ul_rc9 = new Corner(ResourceType.MUSHROOM);
        Corner corner_Ll_rc9 = new Corner(false);
        Corner cornerB_Ur_rc9 = new Corner(false);
        Corner cornerB_Ul_rc9 = new Corner(false);
        Corner cornerB_Lr_rc9 = new Corner(false);
        Corner cornerB_Ll_rc9 = new Corner(false);
        Face front_rc9 = new Face(corner_Ur_rc9, corner_Ul_rc9, corner_Lr_rc9, corner_Ll_rc9);
        Face back_rc9 = new Face(cornerB_Ur_rc9, cornerB_Ul_rc9, cornerB_Lr_rc9, cornerB_Ll_rc9, ResourceType.MUSHROOM);
        ResourceCard resource_card_9 = new ResourceCard(Color.RED, front_rc9, back_rc9, 1, 9);
        resource_card_9.setShowingFront(false);
        resourceCards.add(resource_card_9);

        //red card 10
        Corner corner_Ur_rc10 = new Corner(false);
        Corner corner_Lr_rc10 = new Corner(false);
        Corner corner_Ul_rc10 = new Corner(true);
        Corner corner_Ll_rc10 = new Corner(ResourceType.MUSHROOM);
        Corner cornerB_Ur_rc10 = new Corner(false);
        Corner cornerB_Ul_rc10 = new Corner(false);
        Corner cornerB_Lr_rc10 = new Corner(false);
        Corner cornerB_Ll_rc10 = new Corner(false);
        Face front_rc10 = new Face(corner_Ur_rc10, corner_Ul_rc10, corner_Lr_rc10, corner_Ll_rc10);
        Face back_rc10 = new Face(cornerB_Ur_rc10, cornerB_Ul_rc10, cornerB_Lr_rc10, cornerB_Ll_rc10, ResourceType.MUSHROOM);
        ResourceCard resource_card_10 = new ResourceCard(Color.RED, front_rc10, back_rc10, 1, 10);
        resource_card_10.setShowingFront(false);
        resourceCards.add(resource_card_10);


        //green cards
        //green card 11
        Corner corner_Ur_rc11 = new Corner(false);
        Corner corner_Lr_rc11 = new Corner(true);
        Corner corner_Ul_rc11 = new Corner(ResourceType.LEAF);
        Corner corner_Ll_rc11 = new Corner(ResourceType.LEAF);
        Corner cornerB_Ur_rc11 = new Corner(false);
        Corner cornerB_Ul_rc11 = new Corner(false);
        Corner cornerB_Lr_rc11 = new Corner(false);
        Corner cornerB_Ll_rc11 = new Corner(false);
        Face front_rc11 = new Face(corner_Ur_rc11, corner_Ul_rc11, corner_Lr_rc11, corner_Ll_rc11);
        Face back_rc11 = new Face(cornerB_Ur_rc11, cornerB_Ul_rc11, cornerB_Lr_rc11, cornerB_Ll_rc11, ResourceType.LEAF);
        ResourceCard resource_card_11 = new ResourceCard(Color.GREEN, front_rc11, back_rc11, 0, 11);
        resource_card_11.setShowingFront(false);
        resourceCards.add(resource_card_11);

        //green card 12
        Corner corner_Ur_rc12 = new Corner(ResourceType.LEAF);
        Corner corner_Lr_rc12 = new Corner(false);
        Corner corner_Ul_rc12 = new Corner(ResourceType.LEAF);
        Corner corner_Ll_rc12 = new Corner(true);
        Corner cornerB_Ur_rc12 = new Corner(false);
        Corner cornerB_Ul_rc12 = new Corner(false);
        Corner cornerB_Lr_rc12 = new Corner(false);
        Corner cornerB_Ll_rc12 = new Corner(false);
        Face front_rc12 = new Face(corner_Ur_rc12, corner_Ul_rc12, corner_Lr_rc12, corner_Ll_rc12);
        Face back_rc12 = new Face(cornerB_Ur_rc12, cornerB_Ul_rc12, cornerB_Lr_rc12, cornerB_Ll_rc12, ResourceType.LEAF);
        ResourceCard resource_card_12 = new ResourceCard(Color.GREEN, front_rc12, back_rc12, 0, 12);
        resource_card_12.setShowingFront(false);
        resourceCards.add(resource_card_12);

        // green card 13
        Corner corner_Ur_rc13 = new Corner(true);
        Corner corner_Lr_rc13 = new Corner(ResourceType.LEAF);
        Corner corner_Ul_rc13 = new Corner(false);
        Corner corner_Ll_rc13 = new Corner(ResourceType.LEAF);
        Corner cornerB_Ur_rc13 = new Corner(false);
        Corner cornerB_Ul_rc13 = new Corner(false);
        Corner cornerB_Lr_rc13 = new Corner(false);
        Corner cornerB_Ll_rc13 = new Corner(false);
        Face front_rc13 = new Face(corner_Ur_rc13, corner_Ul_rc13, corner_Lr_rc13, corner_Ll_rc13);
        Face back_rc13 = new Face(cornerB_Ur_rc13, cornerB_Ul_rc13, cornerB_Lr_rc13, cornerB_Ll_rc13, ResourceType.LEAF);
        ResourceCard resource_card_13 = new ResourceCard(Color.GREEN, front_rc13, back_rc13, 0, 13);
        resource_card_13.setShowingFront(false);
        resourceCards.add(resource_card_13);

        //green card 14
        Corner corner_Ur_rc14 = new Corner(ResourceType.LEAF);
        Corner corner_Lr_rc14 = new Corner(ResourceType.LEAF);
        Corner corner_Ul_rc14 = new Corner(true);
        Corner corner_Ll_rc14 = new Corner(false);
        Corner cornerB_Ur_rc14 = new Corner(false);
        Corner cornerB_Ul_rc14 = new Corner(false);
        Corner cornerB_Lr_rc14 = new Corner(false);
        Corner cornerB_Ll_rc14 = new Corner(false);
        Face front_rc14 = new Face(corner_Ur_rc14, corner_Ul_rc14, corner_Lr_rc14, corner_Ll_rc14);
        Face back_rc14 = new Face(cornerB_Ur_rc14, cornerB_Ul_rc14, cornerB_Lr_rc14, cornerB_Ll_rc14, ResourceType.LEAF);
        ResourceCard resource_card_14 = new ResourceCard(Color.GREEN, front_rc14, back_rc14, 0, 14);
        resource_card_14.setShowingFront(false);
        resourceCards.add(resource_card_14);

        // green card 15
        Corner corner_Ur_rc15 = new Corner(ResourceType.INSECT);
        Corner corner_Lr_rc15 = new Corner(ResourceType.LEAF);
        Corner corner_Ul_rc15 = new Corner(true);
        Corner corner_Ll_rc15 = new Corner(ResourceType.QUILL);
        Corner cornerB_Ur_rc15 = new Corner(false);
        Corner cornerB_Ul_rc15 = new Corner(false);
        Corner cornerB_Lr_rc15 = new Corner(false);
        Corner cornerB_Ll_rc15 = new Corner(false);
        Face front_rc15 = new Face(corner_Ur_rc15, corner_Ul_rc15, corner_Lr_rc15, corner_Ll_rc15);
        Face back_rc15 = new Face(cornerB_Ur_rc15, cornerB_Ul_rc15, cornerB_Lr_rc15, cornerB_Ll_rc15, ResourceType.LEAF);
        ResourceCard resource_card_15 = new ResourceCard(Color.GREEN, front_rc15, back_rc15, 0, 15);
        resource_card_15.setShowingFront(false);
        resourceCards.add(resource_card_15);

        //green card 16
        Corner corner_Ur_rc16 = new Corner(ResourceType.LEAF);
        Corner corner_Lr_rc16 = new Corner(ResourceType.INKWELL);
        Corner corner_Ul_rc16 = new Corner(ResourceType.MUSHROOM);
        Corner corner_Ll_rc16 = new Corner(true);
        Corner cornerB_Ur_rc16 = new Corner(false);
        Corner cornerB_Ul_rc16 = new Corner(false);
        Corner cornerB_Lr_rc16 = new Corner(false);
        Corner cornerB_Ll_rc16 = new Corner(false);
        Face front_rc16 = new Face(corner_Ur_rc16, corner_Ul_rc16, corner_Lr_rc16, corner_Ll_rc16);
        Face back_rc16 = new Face(cornerB_Ur_rc16, cornerB_Ul_rc16, cornerB_Lr_rc16, cornerB_Ll_rc16, ResourceType.LEAF);
        ResourceCard resource_card_16 = new ResourceCard(Color.GREEN, front_rc16, back_rc16, 0, 16);
        resource_card_16.setShowingFront(false);
        resourceCards.add(resource_card_16);

        //green card 17
        Corner corner_Ur_rc17 = new Corner(true);
        Corner corner_Lr_rc17 = new Corner(ResourceType.ANIMAL);
        Corner corner_Ul_rc17 = new Corner(ResourceType.MANUSCRIPT);
        Corner corner_Ll_rc17 = new Corner(ResourceType.LEAF);
        Corner cornerB_Ur_rc17 = new Corner(false);
        Corner cornerB_Ul_rc17 = new Corner(false);
        Corner cornerB_Lr_rc17 = new Corner(false);
        Corner cornerB_Ll_rc17 = new Corner(false);
        Face front_rc17 = new Face(corner_Ur_rc17, corner_Ul_rc17, corner_Lr_rc17, corner_Ll_rc17);
        Face back_rc17 = new Face(cornerB_Ur_rc17, cornerB_Ul_rc17, cornerB_Lr_rc17, cornerB_Ll_rc17, ResourceType.LEAF);
        ResourceCard resource_card_17 = new ResourceCard(Color.GREEN, front_rc17, back_rc17, 0, 17);
        resource_card_17.setShowingFront(false);
        resourceCards.add(resource_card_17);

        //green card 18
        Corner corner_Ur_rc18 = new Corner(false);
        Corner corner_Lr_rc18 = new Corner(true);
        Corner corner_Ul_rc18 = new Corner(false);
        Corner corner_Ll_rc18 = new Corner(ResourceType.LEAF);
        Corner cornerB_Ur_rc18 = new Corner(false);
        Corner cornerB_Ul_rc18 = new Corner(false);
        Corner cornerB_Lr_rc18 = new Corner(false);
        Corner cornerB_Ll_rc18 = new Corner(false);
        Face front_rc18 = new Face(corner_Ur_rc18, corner_Ul_rc18, corner_Lr_rc18, corner_Ll_rc18);
        Face back_rc18 = new Face(cornerB_Ur_rc18, cornerB_Ul_rc18, cornerB_Lr_rc18, cornerB_Ll_rc18, ResourceType.LEAF);
        ResourceCard resource_card_18 = new ResourceCard(Color.GREEN, front_rc18, back_rc18, 1, 18);
        resource_card_18.setShowingFront(false);
        resourceCards.add(resource_card_18);

        //green card 19
        Corner corner_Ur_rc19 = new Corner(false);
        Corner corner_Lr_rc19 = new Corner(ResourceType.LEAF);
        Corner corner_Ul_rc19 = new Corner(false);
        Corner corner_Ll_rc19 = new Corner(true);
        Corner cornerB_Ur_rc19 = new Corner(false);
        Corner cornerB_Ul_rc19 = new Corner(false);
        Corner cornerB_Lr_rc19 = new Corner(false);
        Corner cornerB_Ll_rc19 = new Corner(false);
        Face front_rc19 = new Face(corner_Ur_rc19, corner_Ul_rc19, corner_Lr_rc19, corner_Ll_rc19);
        Face back_rc19 = new Face(cornerB_Ur_rc19, cornerB_Ul_rc19, cornerB_Lr_rc19, cornerB_Ll_rc19, ResourceType.LEAF);
        ResourceCard resource_card_19 = new ResourceCard(Color.GREEN, front_rc19, back_rc19, 1, 19);
        resource_card_19.setShowingFront(false);
        resourceCards.add(resource_card_19);

        //green card 20
        Corner corner_Ur_rc20 = new Corner(ResourceType.LEAF);
        Corner corner_Lr_rc20 = new Corner(false);
        Corner corner_Ul_rc20 = new Corner(true);
        Corner corner_Ll_rc20 = new Corner(false);
        Corner cornerB_Ur_rc20 = new Corner(false);
        Corner cornerB_Ul_rc20 = new Corner(false);
        Corner cornerB_Lr_rc20 = new Corner(false);
        Corner cornerB_Ll_rc20 = new Corner(false);
        Face front_rc20 = new Face(corner_Ur_rc20, corner_Ul_rc20, corner_Lr_rc20, corner_Ll_rc20);
        Face back_rc20 = new Face(cornerB_Ur_rc20, cornerB_Ul_rc20, cornerB_Lr_rc20, cornerB_Ll_rc20, ResourceType.LEAF);
        ResourceCard resource_card_20 = new ResourceCard(Color.GREEN, front_rc20, back_rc20, 1, 20);
        resource_card_20.setShowingFront(false);
        resourceCards.add(resource_card_20);

        //blue cards
        //blue card 21
        Corner corner_Ur_rc21 = new Corner(ResourceType.ANIMAL);
        Corner corner_Lr_rc21 = new Corner(true);
        Corner corner_Ul_rc21 = new Corner(ResourceType.ANIMAL);
        Corner corner_Ll_rc21 = new Corner(false);
        Corner cornerB_Ur_rc21 = new Corner(false);
        Corner cornerB_Ul_rc21 = new Corner(false);
        Corner cornerB_Lr_rc21 = new Corner(false);
        Corner cornerB_Ll_rc21 = new Corner(false);
        Face front_rc21 = new Face(corner_Ur_rc21, corner_Ul_rc21, corner_Lr_rc21, corner_Ll_rc21);
        Face back_rc21 = new Face(cornerB_Ur_rc21, cornerB_Ul_rc21, cornerB_Lr_rc21, cornerB_Ll_rc21, ResourceType.ANIMAL);
        ResourceCard resource_card_21 = new ResourceCard(Color.BLUE, front_rc21, back_rc21, 0, 21);
        resource_card_21.setShowingFront(false);
        resourceCards.add(resource_card_21);

        //blue card 22
        Corner corner_Ur_rc22 = new Corner(false);
        Corner corner_Lr_rc22 = new Corner(ResourceType.ANIMAL);
        Corner corner_Ul_rc22 = new Corner(true);
        Corner corner_Ll_rc22 = new Corner(ResourceType.ANIMAL);
        Corner cornerB_Ur_rc22 = new Corner(false);
        Corner cornerB_Ul_rc22 = new Corner(false);
        Corner cornerB_Lr_rc22 = new Corner(false);
        Corner cornerB_Ll_rc22 = new Corner(false);
        Face front_rc22 = new Face(corner_Ur_rc22, corner_Ul_rc22, corner_Lr_rc22, corner_Ll_rc22);
        Face back_rc22 = new Face(cornerB_Ur_rc22, cornerB_Ul_rc22, cornerB_Lr_rc22, cornerB_Ll_rc22, ResourceType.ANIMAL);
        ResourceCard resource_card_22 = new ResourceCard(Color.BLUE, front_rc22, back_rc22, 0, 22);
        resource_card_22.setShowingFront(false);
        resourceCards.add(resource_card_22);

        //blue card 23
        Corner corner_Ur_rc23 = new Corner(true);
        Corner corner_Lr_rc23 = new Corner(false);
        Corner corner_Ul_rc23 = new Corner(ResourceType.ANIMAL);
        Corner corner_Ll_rc23 = new Corner(ResourceType.ANIMAL);
        Corner cornerB_Ur_rc23 = new Corner(false);
        Corner cornerB_Ul_rc23 = new Corner(false);
        Corner cornerB_Lr_rc23 = new Corner(false);
        Corner cornerB_Ll_rc23 = new Corner(false);
        Face front_rc23 = new Face(corner_Ur_rc23, corner_Ul_rc23, corner_Lr_rc23, corner_Ll_rc23);
        Face back_rc23 = new Face(cornerB_Ur_rc23, cornerB_Ul_rc23, cornerB_Lr_rc23, cornerB_Ll_rc23, ResourceType.ANIMAL);
        ResourceCard resource_card_23 = new ResourceCard(Color.BLUE, front_rc23, back_rc23, 0, 23);
        resource_card_23.setShowingFront(false);
        resourceCards.add(resource_card_23);

        //blue card 24
        Corner corner_Ur_rc24 = new Corner(ResourceType.ANIMAL);
        Corner corner_Lr_rc24 = new Corner(ResourceType.ANIMAL);
        Corner corner_Ul_rc24 = new Corner(false);
        Corner corner_Ll_rc24 = new Corner(true);
        Corner cornerB_Ur_rc24 = new Corner(false);
        Corner cornerB_Ul_rc24 = new Corner(false);
        Corner cornerB_Lr_rc24 = new Corner(false);
        Corner cornerB_Ll_rc24 = new Corner(false);
        Face front_rc24 = new Face(corner_Ur_rc24, corner_Ul_rc24, corner_Lr_rc24, corner_Ll_rc24);
        Face back_rc24 = new Face(cornerB_Ur_rc24, cornerB_Ul_rc24, cornerB_Lr_rc24, cornerB_Ll_rc24, ResourceType.ANIMAL);
        ResourceCard resource_card_24 = new ResourceCard(Color.BLUE, front_rc24, back_rc24, 0, 24);
        resource_card_24.setShowingFront(false);
        resourceCards.add(resource_card_24);

        //blue card 25
        Corner corner_Ur_rc25 = new Corner(ResourceType.INSECT);
        Corner corner_Lr_rc25 = new Corner(ResourceType.ANIMAL);
        Corner corner_Ul_rc25 = new Corner(true);
        Corner corner_Ll_rc25 = new Corner(ResourceType.INKWELL);
        Corner cornerB_Ur_rc25 = new Corner(false);
        Corner cornerB_Ul_rc25 = new Corner(false);
        Corner cornerB_Lr_rc25 = new Corner(false);
        Corner cornerB_Ll_rc25 = new Corner(false);
        Face front_rc25 = new Face(corner_Ur_rc25, corner_Ul_rc25, corner_Lr_rc25, corner_Ll_rc25);
        Face back_rc25 = new Face(cornerB_Ur_rc25, cornerB_Ul_rc25, cornerB_Lr_rc25, cornerB_Ll_rc25, ResourceType.ANIMAL);
        ResourceCard resource_card_25 = new ResourceCard(Color.BLUE, front_rc25, back_rc25, 0, 25);
        resource_card_25.setShowingFront(false);
        resourceCards.add(resource_card_25);

        //blue card 26
        Corner corner_Ur_rc26 = new Corner(ResourceType.ANIMAL);
        Corner corner_Lr_rc26 = new Corner(ResourceType.MANUSCRIPT);
        Corner corner_Ul_rc26 = new Corner(ResourceType.LEAF);
        Corner corner_Ll_rc26 = new Corner(true);
        Corner cornerB_Ur_rc26 = new Corner(false);
        Corner cornerB_Ul_rc26 = new Corner(false);
        Corner cornerB_Lr_rc26 = new Corner(false);
        Corner cornerB_Ll_rc26 = new Corner(false);
        Face front_rc26 = new Face(corner_Ur_rc26, corner_Ul_rc26, corner_Lr_rc26, corner_Ll_rc26);
        Face back_rc26 = new Face(cornerB_Ur_rc26, cornerB_Ul_rc26, cornerB_Lr_rc26, cornerB_Ll_rc26, ResourceType.ANIMAL);
        ResourceCard resource_card_26 = new ResourceCard(Color.BLUE, front_rc26, back_rc26, 0, 26);
        resource_card_26.setShowingFront(false);
        resourceCards.add(resource_card_26);

        //blue card 27
        Corner corner_Ur_rc27 = new Corner(true);
        Corner corner_Lr_rc27 = new Corner(ResourceType.MUSHROOM);
        Corner corner_Ul_rc27 = new Corner(ResourceType.QUILL);
        Corner corner_Ll_rc27 = new Corner(ResourceType.ANIMAL);
        Corner cornerB_Ur_rc27 = new Corner(false);
        Corner cornerB_Ul_rc27 = new Corner(false);
        Corner cornerB_Lr_rc27 = new Corner(false);
        Corner cornerB_Ll_rc27 = new Corner(false);
        Face front_rc27 = new Face(corner_Ur_rc27, corner_Ul_rc27, corner_Lr_rc27, corner_Ll_rc27);
        Face back_rc27 = new Face(cornerB_Ur_rc27, cornerB_Ul_rc27, cornerB_Lr_rc27, cornerB_Ll_rc27, ResourceType.ANIMAL);
        ResourceCard resource_card_27 = new ResourceCard(Color.BLUE, front_rc27, back_rc27, 0, 27);
        resource_card_27.setShowingFront(false);
        resourceCards.add(resource_card_27);

        //blue card 28
        Corner corner_Ur_rc28 = new Corner(false);
        Corner corner_Lr_rc28 = new Corner(false);
        Corner corner_Ul_rc28 = new Corner(true);
        Corner corner_Ll_rc28 = new Corner(ResourceType.ANIMAL);
        Corner cornerB_Ur_rc28 = new Corner(false);
        Corner cornerB_Ul_rc28 = new Corner(false);
        Corner cornerB_Lr_rc28 = new Corner(false);
        Corner cornerB_Ll_rc28 = new Corner(false);
        Face front_rc28 = new Face(corner_Ur_rc28, corner_Ul_rc28, corner_Lr_rc28, corner_Ll_rc28);
        Face back_rc28 = new Face(cornerB_Ur_rc28, cornerB_Ul_rc28, cornerB_Lr_rc28, cornerB_Ll_rc28, ResourceType.ANIMAL);
        ResourceCard resource_card_28 = new ResourceCard(Color.BLUE, front_rc28, back_rc28, 1, 28);
        resource_card_28.setShowingFront(false);
        resourceCards.add(resource_card_28);

        //blue card 29
        Corner corner_Ur_rc29 = new Corner(true);
        Corner corner_Lr_rc29 = new Corner(ResourceType.ANIMAL);
        Corner corner_Ul_rc29 = new Corner(false);
        Corner corner_Ll_rc29 = new Corner(false);
        Corner cornerB_Ur_rc29 = new Corner(false);
        Corner cornerB_Ul_rc29 = new Corner(false);
        Corner cornerB_Lr_rc29 = new Corner(false);
        Corner cornerB_Ll_rc29 = new Corner(false);
        Face front_rc29 = new Face(corner_Ur_rc29, corner_Ul_rc29, corner_Lr_rc29, corner_Ll_rc29);
        Face back_rc29 = new Face(cornerB_Ur_rc29, cornerB_Ul_rc29, cornerB_Lr_rc29, cornerB_Ll_rc29, ResourceType.ANIMAL);
        ResourceCard resource_card_29 = new ResourceCard(Color.BLUE, front_rc29, back_rc29, 1, 29);
        resource_card_29.setShowingFront(false);
        resourceCards.add(resource_card_29);

        //blue card 30
        Corner corner_Ur_rc30 = new Corner(ResourceType.ANIMAL);
        Corner corner_Lr_rc30 = new Corner(true);
        Corner corner_Ul_rc30 = new Corner(false);
        Corner corner_Ll_rc30 = new Corner(false);
        Corner cornerB_Ur_rc30 = new Corner(false);
        Corner cornerB_Ul_rc30 = new Corner(false);
        Corner cornerB_Lr_rc30 = new Corner(false);
        Corner cornerB_Ll_rc30 = new Corner(false);
        Face front_rc30 = new Face(corner_Ur_rc30, corner_Ul_rc30, corner_Lr_rc30, corner_Ll_rc30);
        Face back_rc30 = new Face(cornerB_Ur_rc30, cornerB_Ul_rc30, cornerB_Lr_rc30, cornerB_Ll_rc30, ResourceType.ANIMAL);
        ResourceCard resource_card_30 = new ResourceCard(Color.BLUE, front_rc30, back_rc30, 1, 30);
        resource_card_30.setShowingFront(false);
        resourceCards.add(resource_card_30);


        //purple cards

        //purple card 31
        Corner corner_Ur_rc31 = new Corner(ResourceType.INSECT);
        Corner corner_Lr_rc31 = new Corner(true);
        Corner corner_Ul_rc31 = new Corner(ResourceType.INSECT);
        Corner corner_Ll_rc31 = new Corner(false);
        Corner cornerB_Ur_rc31 = new Corner(false);
        Corner cornerB_Ul_rc31 = new Corner(false);
        Corner cornerB_Lr_rc31 = new Corner(false);
        Corner cornerB_Ll_rc31 = new Corner(false);
        Face front_rc31 = new Face(corner_Ur_rc31, corner_Ul_rc31, corner_Lr_rc31, corner_Ll_rc31);
        Face back_rc31 = new Face(cornerB_Ur_rc31, cornerB_Ul_rc31, cornerB_Lr_rc31, cornerB_Ll_rc31, ResourceType.INSECT);
        ResourceCard resource_card_31 = new ResourceCard(Color.PURPLE, front_rc31, back_rc31, 0, 31);
        resource_card_31.setShowingFront(false);
        resourceCards.add(resource_card_31);

        //purple card 32
        Corner corner_Ur_rc32 = new Corner(false);
        Corner corner_Lr_rc32 = new Corner(ResourceType.INSECT);
        Corner corner_Ul_rc32 = new Corner(true);
        Corner corner_Ll_rc32 = new Corner(ResourceType.INSECT);
        Corner cornerB_Ur_rc32 = new Corner(false);
        Corner cornerB_Ul_rc32 = new Corner(false);
        Corner cornerB_Lr_rc32 = new Corner(false);
        Corner cornerB_Ll_rc32 = new Corner(false);
        Face front_rc32 = new Face(corner_Ur_rc32, corner_Ul_rc32, corner_Lr_rc32, corner_Ll_rc32);
        Face back_rc32 = new Face(cornerB_Ur_rc32, cornerB_Ul_rc32, cornerB_Lr_rc32, cornerB_Ll_rc32, ResourceType.INSECT);
        ResourceCard resource_card_32 = new ResourceCard(Color.PURPLE, front_rc32, back_rc32, 0, 32);
        resource_card_32.setShowingFront(false);
        resourceCards.add(resource_card_32);

        //purple card 33
        Corner corner_Ur_rc33 = new Corner(true);
        Corner corner_Lr_rc33 = new Corner(false);
        Corner corner_Ul_rc33 = new Corner(ResourceType.INSECT);
        Corner corner_Ll_rc33 = new Corner(ResourceType.INSECT);
        Corner cornerB_Ur_rc33 = new Corner(false);
        Corner cornerB_Ul_rc33 = new Corner(false);
        Corner cornerB_Lr_rc33 = new Corner(false);
        Corner cornerB_Ll_rc33 = new Corner(false);
        Face front_rc33 = new Face(corner_Ur_rc33, corner_Ul_rc33, corner_Lr_rc33, corner_Ll_rc33);
        Face back_rc33 = new Face(cornerB_Ur_rc33, cornerB_Ul_rc33, cornerB_Lr_rc33, cornerB_Ll_rc33, ResourceType.INSECT);
        ResourceCard resource_card_33 = new ResourceCard(Color.PURPLE, front_rc33, back_rc33, 0, 33);
        resource_card_33.setShowingFront(false);
        resourceCards.add(resource_card_33);

        //purple card 34
        Corner corner_Ur_rc34 = new Corner(ResourceType.INSECT);
        Corner corner_Lr_rc34 = new Corner(ResourceType.INSECT);
        Corner corner_Ul_rc34 = new Corner(false);
        Corner corner_Ll_rc34 = new Corner(true);
        Corner cornerB_Ur_rc34 = new Corner(false);
        Corner cornerB_Ul_rc34 = new Corner(false);
        Corner cornerB_Lr_rc34 = new Corner(false);
        Corner cornerB_Ll_rc34 = new Corner(false);
        Face front_rc34 = new Face(corner_Ur_rc34, corner_Ul_rc34, corner_Lr_rc34, corner_Ll_rc34);
        Face back_rc34 = new Face(cornerB_Ur_rc34, cornerB_Ul_rc34, cornerB_Lr_rc34, cornerB_Ll_rc34, ResourceType.INSECT);
        ResourceCard resource_card_34 = new ResourceCard(Color.PURPLE, front_rc34, back_rc34, 0, 34);
        resource_card_34.setShowingFront(false);
        resourceCards.add(resource_card_34);

        //purple card 35
        Corner corner_Ur_rc35 = new Corner(ResourceType.QUILL);
        Corner corner_Lr_rc35 = new Corner(ResourceType.INSECT);
        Corner corner_Ul_rc35 = new Corner(true);
        Corner corner_Ll_rc35 = new Corner(ResourceType.ANIMAL);
        Corner cornerB_Ur_rc35 = new Corner(false);
        Corner cornerB_Ul_rc35 = new Corner(false);
        Corner cornerB_Lr_rc35 = new Corner(false);
        Corner cornerB_Ll_rc35 = new Corner(false);
        Face front_rc35 = new Face(corner_Ur_rc35, corner_Ul_rc35, corner_Lr_rc35, corner_Ll_rc35);
        Face back_rc35 = new Face(cornerB_Ur_rc35, cornerB_Ul_rc35, cornerB_Lr_rc35, cornerB_Ll_rc35, ResourceType.INSECT);
        ResourceCard resource_card_35 = new ResourceCard(Color.PURPLE, front_rc35, back_rc35, 0, 35);
        resource_card_35.setShowingFront(false);
        resourceCards.add(resource_card_35);

        //purple card 36
        Corner corner_Ur_rc36 = new Corner(ResourceType.INSECT);
        Corner corner_Lr_rc36 = new Corner(ResourceType.MUSHROOM);
        Corner corner_Ul_rc36 = new Corner(ResourceType.MANUSCRIPT);
        Corner corner_Ll_rc36 = new Corner(true);
        Corner cornerB_Ur_rc36 = new Corner(false);
        Corner cornerB_Ul_rc36 = new Corner(false);
        Corner cornerB_Lr_rc36 = new Corner(false);
        Corner cornerB_Ll_rc36 = new Corner(false);
        Face front_rc36 = new Face(corner_Ur_rc36, corner_Ul_rc36, corner_Lr_rc36, corner_Ll_rc36);
        Face back_rc36 = new Face(cornerB_Ur_rc36, cornerB_Ul_rc36, cornerB_Lr_rc36, cornerB_Ll_rc36, ResourceType.INSECT);
        ResourceCard resource_card_36 = new ResourceCard(Color.PURPLE, front_rc36, back_rc36, 0, 36);
        resource_card_36.setShowingFront(false);
        resourceCards.add(resource_card_36);

        //purple card 37
        Corner corner_Ur_rc37 = new Corner(ResourceType.LEAF);
        Corner corner_Lr_rc37 = new Corner(true);
        Corner corner_Ul_rc37 = new Corner(ResourceType.INSECT);
        Corner corner_Ll_rc37 = new Corner(ResourceType.INKWELL);
        Corner cornerB_Ur_rc37 = new Corner(false);
        Corner cornerB_Ul_rc37 = new Corner(false);
        Corner cornerB_Lr_rc37 = new Corner(false);
        Corner cornerB_Ll_rc37 = new Corner(false);
        Face front_rc37 = new Face(corner_Ur_rc37, corner_Ul_rc37, corner_Lr_rc37, corner_Ll_rc37);
        Face back_rc37 = new Face(cornerB_Ur_rc37, cornerB_Ul_rc37, cornerB_Lr_rc37, cornerB_Ll_rc37, ResourceType.INSECT);
        ResourceCard resource_card_37 = new ResourceCard(Color.PURPLE, front_rc37, back_rc37, 0, 37);
        resource_card_37.setShowingFront(false);
        resourceCards.add(resource_card_37);

        //purple card 38
        Corner corner_Ur_rc38 = new Corner(true);
        Corner corner_Lr_rc38 = new Corner(false);
        Corner corner_Ul_rc38 = new Corner(ResourceType.INSECT);
        Corner corner_Ll_rc38 = new Corner(false);
        Corner cornerB_Ur_rc38 = new Corner(false);
        Corner cornerB_Ul_rc38 = new Corner(false);
        Corner cornerB_Lr_rc38 = new Corner(false);
        Corner cornerB_Ll_rc38 = new Corner(false);
        Face front_rc38 = new Face(corner_Ur_rc38, corner_Ul_rc38, corner_Lr_rc38, corner_Ll_rc38);
        Face back_rc38 = new Face(cornerB_Ur_rc38, cornerB_Ul_rc38, cornerB_Lr_rc38, cornerB_Ll_rc38, ResourceType.INSECT);
        ResourceCard resource_card_38 = new ResourceCard(Color.PURPLE, front_rc38, back_rc38, 1, 38);
        resource_card_38.setShowingFront(false);
        resourceCards.add(resource_card_38);

        //purple card 39
        Corner corner_Ur_rc39 = new Corner(false);
        Corner corner_Lr_rc39 = new Corner(ResourceType.INSECT);
        Corner corner_Ul_rc39 = new Corner(false);
        Corner corner_Ll_rc39 = new Corner(true);
        Corner cornerB_Ur_rc39 = new Corner(false);
        Corner cornerB_Ul_rc39 = new Corner(false);
        Corner cornerB_Lr_rc39 = new Corner(false);
        Corner cornerB_Ll_rc39 = new Corner(false);
        Face front_rc39 = new Face(corner_Ur_rc39, corner_Ul_rc39, corner_Lr_rc39, corner_Ll_rc39);
        Face back_rc39 = new Face(cornerB_Ur_rc39, cornerB_Ul_rc39, cornerB_Lr_rc39, cornerB_Ll_rc39, ResourceType.INSECT);
        ResourceCard resource_card_39 = new ResourceCard(Color.PURPLE, front_rc39, back_rc39, 1, 39);
        resource_card_39.setShowingFront(false);
        resourceCards.add(resource_card_39);

        //purple card 40
        Corner corner_Ur_rc40 = new Corner(ResourceType.INSECT);
        Corner corner_Lr_rc40 = new Corner(false);
        Corner corner_Ul_rc40 = new Corner(true);
        Corner corner_Ll_rc40 = new Corner(false);
        Corner cornerB_Ur_rc40 = new Corner(false);
        Corner cornerB_Ul_rc40 = new Corner(false);
        Corner cornerB_Lr_rc40 = new Corner(false);
        Corner cornerB_Ll_rc40 = new Corner(false);
        Face front_rc40 = new Face(corner_Ur_rc40, corner_Ul_rc40, corner_Lr_rc40, corner_Ll_rc40);
        Face back_rc40 = new Face(cornerB_Ur_rc40, cornerB_Ul_rc40, cornerB_Lr_rc40, cornerB_Ll_rc40, ResourceType.INSECT);
        ResourceCard resource_card_40 = new ResourceCard(Color.PURPLE, front_rc40, back_rc40, 1, 40);
        resource_card_40.setShowingFront(false);
        resourceCards.add(resource_card_40);


        return resourceCards;
    }

    /**
     * this method creates all the gold cards and returns them in an array list
     *
     * @return an array list of all the gold cards
     */
    private static ArrayList<GoldCard> createGoldCards() {
        ArrayList<GoldCard> goldCards = new ArrayList<>();

        //red gold cards
        //red card 41
        Corner corner_Ur_gc41 = new Corner(false);
        Corner corner_Lr_gc41 = new Corner(ResourceType.QUILL);
        Corner corner_Ul_gc41 = new Corner(true);
        Corner corner_Ll_gc41 = new Corner(false);
        Corner cornerB_Ur_gc41 = new Corner(false);
        Corner cornerB_Ul_gc41 = new Corner(false);
        Corner cornerB_Lr_gc41 = new Corner(false);
        Corner cornerB_Ll_gc41 = new Corner(false);
        Face front_gc41 = new Face(corner_Ur_gc41, corner_Ul_gc41, corner_Lr_gc41, corner_Ll_gc41);
        Face back_gc41 = new Face(cornerB_Ur_gc41, cornerB_Ul_gc41, cornerB_Lr_gc41, cornerB_Ll_gc41, ResourceType.MUSHROOM);
        GoldCard gold_card_41 = new GoldCard(Color.RED, front_gc41, back_gc41, 1, 2, 1, 0, 0, 41);
        gold_card_41.setShowingFront(false);
        goldCards.add(gold_card_41);

        //red card 42
        Corner corner_Ur_gc42 = new Corner(ResourceType.INKWELL);
        Corner corner_Lr_gc42 = new Corner(false);
        Corner corner_Ul_gc42 = new Corner(false);
        Corner corner_Ll_gc42 = new Corner(true);
        Corner cornerB_Ur_gc42 = new Corner(false);
        Corner cornerB_Ul_gc42 = new Corner(false);
        Corner cornerB_Lr_gc42 = new Corner(false);
        Corner cornerB_Ll_gc42 = new Corner(false);
        Face front_gc42 = new Face(corner_Ur_gc42, corner_Ul_gc42, corner_Lr_gc42, corner_Ll_gc42);
        Face back_gc42 = new Face(cornerB_Ur_gc42, cornerB_Ul_gc42, cornerB_Lr_gc42, cornerB_Ll_gc42, ResourceType.MUSHROOM);
        GoldCard gold_card_42 = new GoldCard(Color.RED, front_gc42, back_gc42, 1, 2, 0, 1, 0, 42);
        gold_card_42.setShowingFront(false);
        goldCards.add(gold_card_42);

        //red card 43
        Corner corner_Ur_gc43 = new Corner(false);
        Corner corner_Lr_gc43 = new Corner(true);
        Corner corner_Ul_gc43 = new Corner(ResourceType.MANUSCRIPT);
        Corner corner_Ll_gc43 = new Corner(false);
        Corner cornerB_Ur_gc43 = new Corner(false);
        Corner cornerB_Ul_gc43 = new Corner(false);
        Corner cornerB_Lr_gc43 = new Corner(false);
        Corner cornerB_Ll_gc43 = new Corner(false);
        Face front_gc43 = new Face(corner_Ur_gc43, corner_Ul_gc43, corner_Lr_gc43, corner_Ll_gc43);
        Face back_gc43 = new Face(cornerB_Ur_gc43, cornerB_Ul_gc43, cornerB_Lr_gc43, cornerB_Ll_gc43, ResourceType.MUSHROOM);
        GoldCard gold_card_43 = new GoldCard(Color.RED, front_gc43, back_gc43, 1, 2, 0, 0, 1, 43);
        gold_card_43.setShowingFront(false);
        goldCards.add(gold_card_43);

        //red card 44
        Corner corner_Ur_gc44 = new Corner(false);
        Corner corner_Lr_gc44 = new Corner(false);
        Corner corner_Ul_gc44 = new Corner(false);
        Corner corner_Ll_gc44 = new Corner(true);
        Corner cornerB_Ur_gc44 = new Corner(false);
        Corner cornerB_Ul_gc44 = new Corner(false);
        Corner cornerB_Lr_gc44 = new Corner(false);
        Corner cornerB_Ll_gc44 = new Corner(false);
        Face front_gc44 = new Face(corner_Ur_gc44, corner_Ul_gc44, corner_Lr_gc44, corner_Ll_gc44);
        Face back_gc44 = new Face(cornerB_Ur_gc44, cornerB_Ul_gc44, cornerB_Lr_gc44, cornerB_Ll_gc44, ResourceType.MUSHROOM);
        GoldCard gold_card_44 = new GoldCard(Color.RED, front_gc44, back_gc44, 2, 3, 1, 0, 0, 44);
        gold_card_44.setShowingFront(false);
        goldCards.add(gold_card_44);

        //red card 45
        Corner corner_Ur_gc45 = new Corner(false);
        Corner corner_Lr_gc45 = new Corner(true);
        Corner corner_Ul_gc45 = new Corner(false);
        Corner corner_Ll_gc45 = new Corner(false);
        Corner cornerB_Ur_gc45 = new Corner(false);
        Corner cornerB_Ul_gc45 = new Corner(false);
        Corner cornerB_Lr_gc45 = new Corner(false);
        Corner cornerB_Ll_gc45 = new Corner(false);
        Face front_gc45 = new Face(corner_Ur_gc45, corner_Ul_gc45, corner_Lr_gc45, corner_Ll_gc45);
        Face back_gc45 = new Face(cornerB_Ur_gc45, cornerB_Ul_gc45, cornerB_Lr_gc45, cornerB_Ll_gc45, ResourceType.MUSHROOM);
        GoldCard gold_card_45 = new GoldCard(Color.RED, front_gc45, back_gc45, 2, 3, 0, 1, 0, 45);
        gold_card_45.setShowingFront(false);
        goldCards.add(gold_card_45);

        //red card 46
        Corner corner_Ur_gc46 = new Corner(true);
        Corner corner_Lr_gc46 = new Corner(false);
        Corner corner_Ul_gc46 = new Corner(false);
        Corner corner_Ll_gc46 = new Corner(false);
        Corner cornerB_Ur_gc46 = new Corner(false);
        Corner cornerB_Ul_gc46 = new Corner(false);
        Corner cornerB_Lr_gc46 = new Corner(false);
        Corner cornerB_Ll_gc46 = new Corner(false);
        Face front_gc46 = new Face(corner_Ur_gc46, corner_Ul_gc46, corner_Lr_gc46, corner_Ll_gc46);
        Face back_gc46 = new Face(cornerB_Ur_gc46, cornerB_Ul_gc46, cornerB_Lr_gc46, cornerB_Ll_gc46, ResourceType.MUSHROOM);
        GoldCard gold_card_46 = new GoldCard(Color.RED, front_gc46, back_gc46, 2, 3, 0, 0, 1, 46);
        gold_card_46.setShowingFront(false);
        goldCards.add(gold_card_46);

        //red card 47
        Corner corner_Ur_gc47 = new Corner(true);
        Corner corner_Lr_gc47 = new Corner(true);
        Corner corner_Ul_gc47 = new Corner(false);
        Corner corner_Ll_gc47 = new Corner(ResourceType.INKWELL);
        Corner cornerB_Ur_gc47 = new Corner(false);
        Corner cornerB_Ul_gc47 = new Corner(false);
        Corner cornerB_Lr_gc47 = new Corner(false);
        Corner cornerB_Ll_gc47 = new Corner(false);
        Face front_gc47 = new Face(corner_Ur_gc47, corner_Ul_gc47, corner_Lr_gc47, corner_Ll_gc47);
        Face back_gc47 = new Face(cornerB_Ur_gc47, cornerB_Ul_gc47, cornerB_Lr_gc47, cornerB_Ll_gc47, ResourceType.MUSHROOM);
        GoldCard gold_card_47 = new GoldCard(Color.RED, front_gc47, back_gc47, 3, 3, 0, 0, 0, 47);
        gold_card_47.setShowingFront(false);
        goldCards.add(gold_card_47);

        //red card 48
        Corner corner_Ur_gc48 = new Corner(false);
        Corner corner_Lr_gc48 = new Corner(true);
        Corner corner_Ul_gc48 = new Corner(ResourceType.QUILL);
        Corner corner_Ll_gc48 = new Corner(true);
        Corner cornerB_Ur_gc48 = new Corner(false);
        Corner cornerB_Ul_gc48 = new Corner(false);
        Corner cornerB_Lr_gc48 = new Corner(false);
        Corner cornerB_Ll_gc48 = new Corner(false);
        Face front_gc48 = new Face(corner_Ur_gc48, corner_Ul_gc48, corner_Lr_gc48, corner_Ll_gc48);
        Face back_gc48 = new Face(cornerB_Ur_gc48, cornerB_Ul_gc48, cornerB_Lr_gc48, cornerB_Ll_gc48, ResourceType.MUSHROOM);
        GoldCard gold_card_48 = new GoldCard(Color.RED, front_gc48, back_gc48, 3, 3, 0, 0, 0, 48);
        gold_card_48.setShowingFront(false);
        goldCards.add(gold_card_48);

        //red card 49
        Corner corner_Ur_gc49 = new Corner(ResourceType.MANUSCRIPT);
        Corner corner_Lr_gc49 = new Corner(false);
        Corner corner_Ul_gc49 = new Corner(true);
        Corner corner_Ll_gc49 = new Corner(true);
        Corner cornerB_Ur_gc49 = new Corner(false);
        Corner cornerB_Ul_gc49 = new Corner(false);
        Corner cornerB_Lr_gc49 = new Corner(false);
        Corner cornerB_Ll_gc49 = new Corner(false);
        Face front_gc49 = new Face(corner_Ur_gc49, corner_Ul_gc49, corner_Lr_gc49, corner_Ll_gc49);
        Face back_gc49 = new Face(cornerB_Ur_gc49, cornerB_Ul_gc49, cornerB_Lr_gc49, cornerB_Ll_gc49, ResourceType.MUSHROOM);
        GoldCard gold_card_49 = new GoldCard(Color.RED, front_gc49, back_gc49, 3, 3, 0, 0, 0, 49);
        gold_card_49.setShowingFront(false);
        goldCards.add(gold_card_49);

        //red card  50
        Corner corner_Ur_gc50 = new Corner(true);
        Corner corner_Lr_gc50 = new Corner(true);
        Corner corner_Ul_gc50 = new Corner(false);
        Corner corner_Ll_gc50 = new Corner(false);
        Corner cornerB_Ur_gc50 = new Corner(false);
        Corner cornerB_Ul_gc50 = new Corner(false);
        Corner cornerB_Lr_gc50 = new Corner(false);
        Corner cornerB_Ll_gc50 = new Corner(false);
        Face front_gc50 = new Face(corner_Ur_gc50, corner_Ul_gc50, corner_Lr_gc50, corner_Ll_gc50);
        Face back_gc50 = new Face(cornerB_Ur_gc50, cornerB_Ul_gc50, cornerB_Lr_gc50, cornerB_Ll_gc50, ResourceType.MUSHROOM);
        GoldCard gold_card_50 = new GoldCard(Color.RED, front_gc50, back_gc50, 5, 5, 0, 0, 0, 50);
        gold_card_50.setShowingFront(false);
        goldCards.add(gold_card_50);

        //green gold cards
        //green card 51
        Corner corner_Ur_gc51 = new Corner(false);
        Corner corner_Lr_gc51 = new Corner(true);
        Corner corner_Ul_gc51 = new Corner(ResourceType.QUILL);
        Corner corner_Ll_gc51 = new Corner(false);
        Corner cornerB_Ur_gc51 = new Corner(false);
        Corner cornerB_Ul_gc51 = new Corner(false);
        Corner cornerB_Lr_gc51 = new Corner(false);
        Corner cornerB_Ll_gc51 = new Corner(false);
        Face front_gc51 = new Face(corner_Ur_gc51, corner_Ul_gc51, corner_Lr_gc51, corner_Ll_gc51);
        Face back_gc51 = new Face(cornerB_Ur_gc51, cornerB_Ul_gc51, cornerB_Lr_gc51, cornerB_Ll_gc51, ResourceType.LEAF);
        GoldCard gold_card_51 = new GoldCard(Color.GREEN, front_gc51, back_gc51, 1, 0, 0, 2, 1, 51);
        gold_card_51.setShowingFront(false);
        goldCards.add(gold_card_51);

        //green card 52
        Corner corner_Ur_gc52 = new Corner(ResourceType.MANUSCRIPT);
        Corner corner_Lr_gc52 = new Corner(false);
        Corner corner_Ul_gc52 = new Corner(false);
        Corner corner_Ll_gc52 = new Corner(true);
        Corner cornerB_Ur_gc52 = new Corner(false);
        Corner cornerB_Ul_gc52 = new Corner(false);
        Corner cornerB_Lr_gc52 = new Corner(false);
        Corner cornerB_Ll_gc52 = new Corner(false);
        Face front_gc52 = new Face(corner_Ur_gc52, corner_Ul_gc52, corner_Lr_gc52, corner_Ll_gc52);
        Face back_gc52 = new Face(cornerB_Ur_gc52, cornerB_Ul_gc52, cornerB_Lr_gc52, cornerB_Ll_gc52, ResourceType.LEAF);
        GoldCard gold_card_52 = new GoldCard(Color.GREEN, front_gc52, back_gc52, 1, 1, 0, 2, 0, 52);
        gold_card_52.setShowingFront(false);
        goldCards.add(gold_card_52);

        //green card 53
        Corner corner_Ur_gc53 = new Corner(true);
        Corner corner_Lr_gc53 = new Corner(false);
        Corner corner_Ul_gc53 = new Corner(false);
        Corner corner_Ll_gc53 = new Corner(ResourceType.INKWELL);
        Corner cornerB_Ur_gc53 = new Corner(false);
        Corner cornerB_Ul_gc53 = new Corner(false);
        Corner cornerB_Lr_gc53 = new Corner(false);
        Corner cornerB_Ll_gc53 = new Corner(false);
        Face front_gc53 = new Face(corner_Ur_gc53, corner_Ul_gc53, corner_Lr_gc53, corner_Ll_gc53);
        Face back_gc53 = new Face(cornerB_Ur_gc53, cornerB_Ul_gc53, cornerB_Lr_gc53, cornerB_Ll_gc53, ResourceType.LEAF);
        GoldCard gold_card_53 = new GoldCard(Color.GREEN, front_gc53, back_gc53, 1, 0, 1, 2, 0, 53);
        gold_card_53.setShowingFront(false);
        goldCards.add(gold_card_53);

        //green card 54
        Corner corner_Ur_gc54 = new Corner(false);
        Corner corner_Lr_gc54 = new Corner(false);
        Corner corner_Ul_gc54 = new Corner(true);
        Corner corner_Ll_gc54 = new Corner(false);
        Corner cornerB_Ur_gc54 = new Corner(false);
        Corner cornerB_Ul_gc54 = new Corner(false);
        Corner cornerB_Lr_gc54 = new Corner(false);
        Corner cornerB_Ll_gc54 = new Corner(false);
        Face front_gc54 = new Face(corner_Ur_gc54, corner_Ul_gc54, corner_Lr_gc54, corner_Ll_gc54);
        Face back_gc54 = new Face(cornerB_Ur_gc54, cornerB_Ul_gc54, cornerB_Lr_gc54, cornerB_Ll_gc54, ResourceType.LEAF);
        GoldCard gold_card_54 = new GoldCard(Color.GREEN, front_gc54, back_gc54, 2, 0, 0, 3, 1, 54);
        gold_card_54.setShowingFront(false);
        goldCards.add(gold_card_54);

        //green card 55
        Corner corner_Ur_gc55 = new Corner(false);
        Corner corner_Lr_gc55 = new Corner(true);
        Corner corner_Ul_gc55 = new Corner(false);
        Corner corner_Ll_gc55 = new Corner(false);
        Corner cornerB_Ur_gc55 = new Corner(false);
        Corner cornerB_Ul_gc55 = new Corner(false);
        Corner cornerB_Lr_gc55 = new Corner(false);
        Corner cornerB_Ll_gc55 = new Corner(false);
        Face front_gc55 = new Face(corner_Ur_gc55, corner_Ul_gc55, corner_Lr_gc55, corner_Ll_gc55);
        Face back_gc55 = new Face(cornerB_Ur_gc55, cornerB_Ul_gc55, cornerB_Lr_gc55, cornerB_Ll_gc55, ResourceType.LEAF);
        GoldCard gold_card_55 = new GoldCard(Color.GREEN, front_gc55, back_gc55, 2, 0, 1, 3, 0, 55);
        gold_card_55.setShowingFront(false);
        goldCards.add(gold_card_55);

        //green card 56
        Corner corner_Ur_gc56 = new Corner(true);
        Corner corner_Lr_gc56 = new Corner(false);
        Corner corner_Ul_gc56 = new Corner(false);
        Corner corner_Ll_gc56 = new Corner(false);
        Corner cornerB_Ur_gc56 = new Corner(false);
        Corner cornerB_Ul_gc56 = new Corner(false);
        Corner cornerB_Lr_gc56 = new Corner(false);
        Corner cornerB_Ll_gc56 = new Corner(false);
        Face front_gc56 = new Face(corner_Ur_gc56, corner_Ul_gc56, corner_Lr_gc56, corner_Ll_gc56);
        Face back_gc56 = new Face(cornerB_Ur_gc56, cornerB_Ul_gc56, cornerB_Lr_gc56, cornerB_Ll_gc56, ResourceType.LEAF);
        GoldCard gold_card_56 = new GoldCard(Color.GREEN, front_gc56, back_gc56, 2, 1, 0, 3, 0, 56);
        gold_card_56.setShowingFront(false);
        goldCards.add(gold_card_56);

        //green card 57
        Corner corner_Ur_gc57 = new Corner(true);
        Corner corner_Lr_gc57 = new Corner(true);
        Corner corner_Ul_gc57 = new Corner(false);
        Corner corner_Ll_gc57 = new Corner(ResourceType.QUILL);
        Corner cornerB_Ur_gc57 = new Corner(false);
        Corner cornerB_Ul_gc57 = new Corner(false);
        Corner cornerB_Lr_gc57 = new Corner(false);
        Corner cornerB_Ll_gc57 = new Corner(false);
        Face front_gc57 = new Face(corner_Ur_gc57, corner_Ul_gc57, corner_Lr_gc57, corner_Ll_gc57);
        Face back_gc57 = new Face(cornerB_Ur_gc57, cornerB_Ul_gc57, cornerB_Lr_gc57, cornerB_Ll_gc57, ResourceType.LEAF);
        GoldCard gold_card_57 = new GoldCard(Color.GREEN, front_gc57, back_gc57, 3, 0, 0, 3, 0, 57);
        gold_card_57.setShowingFront(false);
        goldCards.add(gold_card_57);

        //green card 58
        Corner corner_Ur_gc58 = new Corner(false);
        Corner corner_Lr_gc58 = new Corner(true);
        Corner corner_Ul_gc58 = new Corner(ResourceType.MANUSCRIPT);
        Corner corner_Ll_gc58 = new Corner(true);
        Corner cornerB_Ur_gc58 = new Corner(false);
        Corner cornerB_Ul_gc58 = new Corner(false);
        Corner cornerB_Lr_gc58 = new Corner(false);
        Corner cornerB_Ll_gc58 = new Corner(false);
        Face front_gc58 = new Face(corner_Ur_gc58, corner_Ul_gc58, corner_Lr_gc58, corner_Ll_gc58);
        Face back_gc58 = new Face(cornerB_Ur_gc58, cornerB_Ul_gc58, cornerB_Lr_gc58, cornerB_Ll_gc58, ResourceType.LEAF);
        GoldCard gold_card_58 = new GoldCard(Color.GREEN, front_gc58, back_gc58, 3, 0, 0, 3, 0, 58);
        gold_card_58.setShowingFront(false);
        goldCards.add(gold_card_58);

        //green card 59
        Corner corner_Ur_gc59 = new Corner(ResourceType.INKWELL);
        Corner corner_Lr_gc59 = new Corner(false);
        Corner corner_Ul_gc59 = new Corner(true);
        Corner corner_Ll_gc59 = new Corner(true);
        Corner cornerB_Ur_gc59 = new Corner(false);
        Corner cornerB_Ul_gc59 = new Corner(false);
        Corner cornerB_Lr_gc59 = new Corner(false);
        Corner cornerB_Ll_gc59 = new Corner(false);
        Face front_gc59 = new Face(corner_Ur_gc59, corner_Ul_gc59, corner_Lr_gc59, corner_Ll_gc59);
        Face back_gc59 = new Face(cornerB_Ur_gc59, cornerB_Ul_gc59, cornerB_Lr_gc59, cornerB_Ll_gc59, ResourceType.LEAF);
        GoldCard gold_card_59 = new GoldCard(Color.GREEN, front_gc59, back_gc59, 3, 0, 0, 3, 0, 59);
        gold_card_59.setShowingFront(false);
        goldCards.add(gold_card_59);

        //green card 60
        Corner corner_Ur_gc60 = new Corner(false);
        Corner corner_Lr_gc60 = new Corner(true);
        Corner corner_Ul_gc60 = new Corner(false);
        Corner corner_Ll_gc60 = new Corner(true);
        Corner cornerB_Ur_gc60 = new Corner(false);
        Corner cornerB_Ul_gc60 = new Corner(false);
        Corner cornerB_Lr_gc60 = new Corner(false);
        Corner cornerB_Ll_gc60 = new Corner(false);
        Face front_gc60 = new Face(corner_Ur_gc60, corner_Ul_gc60, corner_Lr_gc60, corner_Ll_gc60);
        Face back_gc60 = new Face(cornerB_Ur_gc60, cornerB_Ul_gc60, cornerB_Lr_gc60, cornerB_Ll_gc60, ResourceType.LEAF);
        GoldCard gold_card_60 = new GoldCard(Color.GREEN, front_gc60, back_gc60, 5, 0, 0, 5, 0, 60);
        gold_card_60.setShowingFront(false);
        goldCards.add(gold_card_60);

        //blue gold cards
        //blue card 61
        Corner corner_Ur_gc61 = new Corner(false);
        Corner corner_Lr_gc61 = new Corner(true);
        Corner corner_Ul_gc61 = new Corner(ResourceType.INKWELL);
        Corner corner_Ll_gc61 = new Corner(false);
        Corner cornerB_Ur_gc61 = new Corner(false);
        Corner cornerB_Ul_gc61 = new Corner(false);
        Corner cornerB_Lr_gc61 = new Corner(false);
        Corner cornerB_Ll_gc61 = new Corner(false);
        Face front_gc61 = new Face(corner_Ur_gc61, corner_Ul_gc61, corner_Lr_gc61, corner_Ll_gc61);
        Face back_gc61 = new Face(cornerB_Ur_gc61, cornerB_Ul_gc61, cornerB_Lr_gc61, cornerB_Ll_gc61, ResourceType.ANIMAL);
        GoldCard gold_card_61 = new GoldCard(Color.BLUE, front_gc61, back_gc61, 1, 0, 2, 0, 1, 61);
        gold_card_61.setShowingFront(false);
        goldCards.add(gold_card_61);

        //blue card 62
        Corner corner_Ur_gc62 = new Corner(false);
        Corner corner_Lr_gc62 = new Corner(ResourceType.MANUSCRIPT);
        Corner corner_Ul_gc62 = new Corner(true);
        Corner corner_Ll_gc62 = new Corner(false);
        Corner cornerB_Ur_gc62 = new Corner(false);
        Corner cornerB_Ul_gc62 = new Corner(false);
        Corner cornerB_Lr_gc62 = new Corner(false);
        Corner cornerB_Ll_gc62 = new Corner(false);
        Face front_gc62 = new Face(corner_Ur_gc62, corner_Ul_gc62, corner_Lr_gc62, corner_Ll_gc62);
        Face back_gc62 = new Face(cornerB_Ur_gc62, cornerB_Ul_gc62, cornerB_Lr_gc62, cornerB_Ll_gc62, ResourceType.ANIMAL);
        GoldCard gold_card_62 = new GoldCard(Color.BLUE, front_gc62, back_gc62, 1, 0, 2, 1, 0, 62);
        gold_card_62.setShowingFront(false);
        goldCards.add(gold_card_62);

        //blue card 63
        Corner corner_Ur_gc63 = new Corner(true);
        Corner corner_Lr_gc63 = new Corner(false);
        Corner corner_Ul_gc63 = new Corner(false);
        Corner corner_Ll_gc63 = new Corner(ResourceType.QUILL);
        Corner cornerB_Ur_gc63 = new Corner(false);
        Corner cornerB_Ul_gc63 = new Corner(false);
        Corner cornerB_Lr_gc63 = new Corner(false);
        Corner cornerB_Ll_gc63 = new Corner(false);
        Face front_gc63 = new Face(corner_Ur_gc63, corner_Ul_gc63, corner_Lr_gc63, corner_Ll_gc63);
        Face back_gc63 = new Face(cornerB_Ur_gc63, cornerB_Ul_gc63, cornerB_Lr_gc63, cornerB_Ll_gc63, ResourceType.ANIMAL);
        GoldCard gold_card_63 = new GoldCard(Color.BLUE, front_gc63, back_gc63, 1, 1, 2, 0, 0, 63);
        gold_card_63.setShowingFront(false);
        goldCards.add(gold_card_63);

        //blue card 64
        Corner corner_Ur_gc64 = new Corner(false);
        Corner corner_Lr_gc64 = new Corner(false);
        Corner corner_Ul_gc64 = new Corner(false);
        Corner corner_Ll_gc64 = new Corner(true);
        Corner cornerB_Ur_gc64 = new Corner(false);
        Corner cornerB_Ul_gc64 = new Corner(false);
        Corner cornerB_Lr_gc64 = new Corner(false);
        Corner cornerB_Ll_gc64 = new Corner(false);
        Face front_gc64 = new Face(corner_Ur_gc64, corner_Ul_gc64, corner_Lr_gc64, corner_Ll_gc64);
        Face back_gc64 = new Face(cornerB_Ur_gc64, cornerB_Ul_gc64, cornerB_Lr_gc64, cornerB_Ll_gc64, ResourceType.ANIMAL);
        GoldCard gold_card_64 = new GoldCard(Color.BLUE, front_gc64, back_gc64, 2, 0, 3, 0, 1, 64);
        gold_card_64.setShowingFront(false);
        goldCards.add(gold_card_64);

        //blue card 65
        Corner corner_Ur_gc65 = new Corner(true);
        Corner corner_Lr_gc65 = new Corner(false);
        Corner corner_Ul_gc65 = new Corner(false);
        Corner corner_Ll_gc65 = new Corner(false);
        Corner cornerB_Ur_gc65 = new Corner(false);
        Corner cornerB_Ul_gc65 = new Corner(false);
        Corner cornerB_Lr_gc65 = new Corner(false);
        Corner cornerB_Ll_gc65 = new Corner(false);
        Face front_gc65 = new Face(corner_Ur_gc65, corner_Ul_gc65, corner_Lr_gc65, corner_Ll_gc65);
        Face back_gc65 = new Face(cornerB_Ur_gc65, cornerB_Ul_gc65, cornerB_Lr_gc65, cornerB_Ll_gc65, ResourceType.ANIMAL);
        GoldCard gold_card_65 = new GoldCard(Color.BLUE, front_gc65, back_gc65, 2, 1, 3, 0, 0, 65);
        gold_card_65.setShowingFront(false);
        goldCards.add(gold_card_65);

        //blue card 66
        Corner corner_Ur_gc66 = new Corner(false);
        Corner corner_Lr_gc66 = new Corner(false);
        Corner corner_Ul_gc66 = new Corner(true);
        Corner corner_Ll_gc66 = new Corner(false);
        Corner cornerB_Ur_gc66 = new Corner(false);
        Corner cornerB_Ul_gc66 = new Corner(false);
        Corner cornerB_Lr_gc66 = new Corner(false);
        Corner cornerB_Ll_gc66 = new Corner(false);
        Face front_gc66 = new Face(corner_Ur_gc66, corner_Ul_gc66, corner_Lr_gc66, corner_Ll_gc66);
        Face back_gc66 = new Face(cornerB_Ur_gc66, cornerB_Ul_gc66, cornerB_Lr_gc66, cornerB_Ll_gc66, ResourceType.ANIMAL);
        GoldCard gold_card_66 = new GoldCard(Color.BLUE, front_gc66, back_gc66, 2, 0, 3, 1, 0, 66);
        gold_card_66.setShowingFront(false);
        goldCards.add(gold_card_66);

        //blue card 67
        Corner corner_Ur_gc67 = new Corner(true);
        Corner corner_Lr_gc67 = new Corner(true);
        Corner corner_Ul_gc67 = new Corner(false);
        Corner corner_Ll_gc67 = new Corner(ResourceType.MANUSCRIPT);
        Corner cornerB_Ur_gc67 = new Corner(false);
        Corner cornerB_Ul_gc67 = new Corner(false);
        Corner cornerB_Lr_gc67 = new Corner(false);
        Corner cornerB_Ll_gc67 = new Corner(false);
        Face front_gc67 = new Face(corner_Ur_gc67, corner_Ul_gc67, corner_Lr_gc67, corner_Ll_gc67);
        Face back_gc67 = new Face(cornerB_Ur_gc67, cornerB_Ul_gc67, cornerB_Lr_gc67, cornerB_Ll_gc67, ResourceType.ANIMAL);
        GoldCard gold_card_67 = new GoldCard(Color.BLUE, front_gc67, back_gc67, 3, 0, 3, 0, 0, 67);
        gold_card_67.setShowingFront(false);
        goldCards.add(gold_card_67);

        //blue card 68
        Corner corner_Ur_gc68 = new Corner(ResourceType.INKWELL);
        Corner corner_Lr_gc68 = new Corner(true);
        Corner corner_Ul_gc68 = new Corner(false);
        Corner corner_Ll_gc68 = new Corner(true);
        Corner cornerB_Ur_gc68 = new Corner(false);
        Corner cornerB_Ul_gc68 = new Corner(false);
        Corner cornerB_Lr_gc68 = new Corner(false);
        Corner cornerB_Ll_gc68 = new Corner(false);
        Face front_gc68 = new Face(corner_Ur_gc68, corner_Ul_gc68, corner_Lr_gc68, corner_Ll_gc68);
        Face back_gc68 = new Face(cornerB_Ur_gc68, cornerB_Ul_gc68, cornerB_Lr_gc68, cornerB_Ll_gc68, ResourceType.ANIMAL);
        GoldCard gold_card_68 = new GoldCard(Color.BLUE, front_gc68, back_gc68, 3, 0, 3, 0, 0, 68);
        gold_card_68.setShowingFront(false);
        goldCards.add(gold_card_68);

        //blue card 69
        Corner corner_Ur_gc69 = new Corner(false);
        Corner corner_Lr_gc69 = new Corner(ResourceType.QUILL);
        Corner corner_Ul_gc69 = new Corner(true);
        Corner corner_Ll_gc69 = new Corner(true);
        Corner cornerB_Ur_gc69 = new Corner(false);
        Corner cornerB_Ul_gc69 = new Corner(false);
        Corner cornerB_Lr_gc69 = new Corner(false);
        Corner cornerB_Ll_gc69 = new Corner(false);
        Face front_gc69 = new Face(corner_Ur_gc69, corner_Ul_gc69, corner_Lr_gc69, corner_Ll_gc69);
        Face back_gc69 = new Face(cornerB_Ur_gc69, cornerB_Ul_gc69, cornerB_Lr_gc69, cornerB_Ll_gc69, ResourceType.ANIMAL);
        GoldCard gold_card_69 = new GoldCard(Color.BLUE, front_gc69, back_gc69, 3, 0, 3, 0, 0, 69);
        gold_card_69.setShowingFront(false);
        goldCards.add(gold_card_69);

        //blue card 70
        Corner corner_Ur_gc70 = new Corner(false);
        Corner corner_Lr_gc70 = new Corner(false);
        Corner corner_Ul_gc70 = new Corner(true);
        Corner corner_Ll_gc70 = new Corner(true);
        Corner cornerB_Ur_gc70 = new Corner(false);
        Corner cornerB_Ul_gc70 = new Corner(false);
        Corner cornerB_Lr_gc70 = new Corner(false);
        Corner cornerB_Ll_gc70 = new Corner(false);
        Face front_gc70 = new Face(corner_Ur_gc70, corner_Ul_gc70, corner_Lr_gc70, corner_Ll_gc70);
        Face back_gc70 = new Face(cornerB_Ur_gc70, cornerB_Ul_gc70, cornerB_Lr_gc70, cornerB_Ll_gc70, ResourceType.ANIMAL);
        GoldCard gold_card_70 = new GoldCard(Color.BLUE, front_gc70, back_gc70, 5, 0, 5, 0, 0, 70);
        gold_card_70.setShowingFront(false);
        goldCards.add(gold_card_70);

        //purple gold cards
        //purple card 71
        Corner corner_Ur_gc71 = new Corner(ResourceType.QUILL);
        Corner corner_Lr_gc71 = new Corner(false);
        Corner corner_Ul_gc71 = new Corner(false);
        Corner corner_Ll_gc71 = new Corner(true);
        Corner cornerB_Ur_gc71 = new Corner(false);
        Corner cornerB_Ul_gc71 = new Corner(false);
        Corner cornerB_Lr_gc71 = new Corner(false);
        Corner cornerB_Ll_gc71 = new Corner(false);
        Face front_gc71 = new Face(corner_Ur_gc71, corner_Ul_gc71, corner_Lr_gc71, corner_Ll_gc71);
        Face back_gc71 = new Face(cornerB_Ur_gc71, cornerB_Ul_gc71, cornerB_Lr_gc71, cornerB_Ll_gc71, ResourceType.INSECT);
        GoldCard gold_card_71 = new GoldCard(Color.PURPLE, front_gc71, back_gc71, 1, 0, 0, 1, 2, 71);
        gold_card_71.setShowingFront(false);
        goldCards.add(gold_card_71);

        //purple card 72
        Corner corner_Ur_gc72 = new Corner(true);
        Corner corner_Lr_gc72 = new Corner(false);
        Corner corner_Ul_gc72 = new Corner(false);
        Corner corner_Ll_gc72 = new Corner(ResourceType.MANUSCRIPT);
        Corner cornerB_Ur_gc72 = new Corner(false);
        Corner cornerB_Ul_gc72 = new Corner(false);
        Corner cornerB_Lr_gc72 = new Corner(false);
        Corner cornerB_Ll_gc72 = new Corner(false);
        Face front_gc72 = new Face(corner_Ur_gc72, corner_Ul_gc72, corner_Lr_gc72, corner_Ll_gc72);
        Face back_gc72 = new Face(cornerB_Ur_gc72, cornerB_Ul_gc72, cornerB_Lr_gc72, cornerB_Ll_gc72, ResourceType.INSECT);
        GoldCard gold_card_72 = new GoldCard(Color.PURPLE, front_gc72, back_gc72, 1, 0, 1, 0, 2, 72);
        gold_card_72.setShowingFront(false);
        goldCards.add(gold_card_72);

        //purple card 73
        Corner corner_Ur_gc73 = new Corner(false);
        Corner corner_Lr_gc73 = new Corner(ResourceType.INKWELL);
        Corner corner_Ul_gc73 = new Corner(true);
        Corner corner_Ll_gc73 = new Corner(false);
        Corner cornerB_Ur_gc73 = new Corner(false);
        Corner cornerB_Ul_gc73 = new Corner(false);
        Corner cornerB_Lr_gc73 = new Corner(false);
        Corner cornerB_Ll_gc73 = new Corner(false);
        Face front_gc73 = new Face(corner_Ur_gc73, corner_Ul_gc73, corner_Lr_gc73, corner_Ll_gc73);
        Face back_gc73 = new Face(cornerB_Ur_gc73, cornerB_Ul_gc73, cornerB_Lr_gc73, cornerB_Ll_gc73, ResourceType.INSECT);
        GoldCard gold_card_73 = new GoldCard(Color.PURPLE, front_gc73, back_gc73, 1, 1, 0, 0, 2, 73);
        gold_card_73.setShowingFront(false);
        goldCards.add(gold_card_73);

        //purple card 74
        Corner corner_Ur_gc74 = new Corner(false);
        Corner corner_Lr_gc74 = new Corner(false);
        Corner corner_Ul_gc74 = new Corner(false);
        Corner corner_Ll_gc74 = new Corner(true);
        Corner cornerB_Ur_gc74 = new Corner(false);
        Corner cornerB_Ul_gc74 = new Corner(false);
        Corner cornerB_Lr_gc74 = new Corner(false);
        Corner cornerB_Ll_gc74 = new Corner(false);
        Face front_gc74 = new Face(corner_Ur_gc74, corner_Ul_gc74, corner_Lr_gc74, corner_Ll_gc74);
        Face back_gc74 = new Face(cornerB_Ur_gc74, cornerB_Ul_gc74, cornerB_Lr_gc74, cornerB_Ll_gc74, ResourceType.INSECT);
        GoldCard gold_card_74 = new GoldCard(Color.PURPLE, front_gc74, back_gc74, 2, 0, 1, 0, 3, 74);
        gold_card_74.setShowingFront(false);
        goldCards.add(gold_card_74);

        //purple card 75
        Corner corner_Ur_gc75 = new Corner(false);
        Corner corner_Lr_gc75 = new Corner(true);
        Corner corner_Ul_gc75 = new Corner(false);
        Corner corner_Ll_gc75 = new Corner(false);
        Corner cornerB_Ur_gc75 = new Corner(false);
        Corner cornerB_Ul_gc75 = new Corner(false);
        Corner cornerB_Lr_gc75 = new Corner(false);
        Corner cornerB_Ll_gc75 = new Corner(false);
        Face front_gc75 = new Face(corner_Ur_gc75, corner_Ul_gc75, corner_Lr_gc75, corner_Ll_gc75);
        Face back_gc75 = new Face(cornerB_Ur_gc75, cornerB_Ul_gc75, cornerB_Lr_gc75, cornerB_Ll_gc75, ResourceType.INSECT);
        GoldCard gold_card_75 = new GoldCard(Color.PURPLE, front_gc75, back_gc75, 2, 0, 0, 1, 3, 75);
        gold_card_75.setShowingFront(false);
        goldCards.add(gold_card_75);

        //purple card 76
        Corner corner_Ur_gc76 = new Corner(true);
        Corner corner_Lr_gc76 = new Corner(false);
        Corner corner_Ul_gc76 = new Corner(false);
        Corner corner_Ll_gc76 = new Corner(false);
        Corner cornerB_Ur_gc76 = new Corner(false);
        Corner cornerB_Ul_gc76 = new Corner(false);
        Corner cornerB_Lr_gc76 = new Corner(false);
        Corner cornerB_Ll_gc76 = new Corner(false);
        Face front_gc76 = new Face(corner_Ur_gc76, corner_Ul_gc76, corner_Lr_gc76, corner_Ll_gc76);
        Face back_gc76 = new Face(cornerB_Ur_gc76, cornerB_Ul_gc76, cornerB_Lr_gc76, cornerB_Ll_gc76, ResourceType.INSECT);
        GoldCard gold_card_76 = new GoldCard(Color.PURPLE, front_gc76, back_gc76, 2, 1, 0, 0, 3, 76);
        gold_card_76.setShowingFront(false);
        goldCards.add(gold_card_76);

        //purple card 77
        Corner corner_Ur_gc77 = new Corner(true);
        Corner corner_Lr_gc77 = new Corner(true);
        Corner corner_Ul_gc77 = new Corner(ResourceType.INKWELL);
        Corner corner_Ll_gc77 = new Corner(false);
        Corner cornerB_Ur_gc77 = new Corner(false);
        Corner cornerB_Ul_gc77 = new Corner(false);
        Corner cornerB_Lr_gc77 = new Corner(false);
        Corner cornerB_Ll_gc77 = new Corner(false);
        Face front_gc77 = new Face(corner_Ur_gc77, corner_Ul_gc77, corner_Lr_gc77, corner_Ll_gc77);
        Face back_gc77 = new Face(cornerB_Ur_gc77, cornerB_Ul_gc77, cornerB_Lr_gc77, cornerB_Ll_gc77, ResourceType.INSECT);
        GoldCard gold_card_77 = new GoldCard(Color.PURPLE, front_gc77, back_gc77, 3, 0, 0, 0, 3, 77);
        gold_card_77.setShowingFront(false);
        goldCards.add(gold_card_77);

        //purple card 78
        Corner corner_Ur_gc78 = new Corner(ResourceType.MANUSCRIPT);
        Corner corner_Lr_gc78 = new Corner(true);
        Corner corner_Ul_gc78 = new Corner(false);
        Corner corner_Ll_gc78 = new Corner(true);
        Corner cornerB_Ur_gc78 = new Corner(false);
        Corner cornerB_Ul_gc78 = new Corner(false);
        Corner cornerB_Lr_gc78 = new Corner(false);
        Corner cornerB_Ll_gc78 = new Corner(false);
        Face front_gc78 = new Face(corner_Ur_gc78, corner_Ul_gc78, corner_Lr_gc78, corner_Ll_gc78);
        Face back_gc78 = new Face(cornerB_Ur_gc78, cornerB_Ul_gc78, cornerB_Lr_gc78, cornerB_Ll_gc78, ResourceType.INSECT);
        GoldCard gold_card_78 = new GoldCard(Color.PURPLE, front_gc78, back_gc78, 3, 0, 0, 0, 3, 78);
        gold_card_78.setShowingFront(false);
        goldCards.add(gold_card_78);

        //purple card 79
        Corner corner_Ur_gc79 = new Corner(true);
        Corner corner_Lr_gc79 = new Corner(false);
        Corner corner_Ul_gc79 = new Corner(true);
        Corner corner_Ll_gc79 = new Corner(ResourceType.QUILL);
        Corner cornerB_Ur_gc79 = new Corner(false);
        Corner cornerB_Ul_gc79 = new Corner(false);
        Corner cornerB_Lr_gc79 = new Corner(false);
        Corner cornerB_Ll_gc79 = new Corner(false);
        Face front_gc79 = new Face(corner_Ur_gc79, corner_Ul_gc79, corner_Lr_gc79, corner_Ll_gc79);
        Face back_gc79 = new Face(cornerB_Ur_gc79, cornerB_Ul_gc79, cornerB_Lr_gc79, cornerB_Ll_gc79, ResourceType.INSECT);
        GoldCard gold_card_79 = new GoldCard(Color.PURPLE, front_gc79, back_gc79, 3, 0, 0, 0, 3, 79);
        gold_card_79.setShowingFront(false);
        goldCards.add(gold_card_79);

        //purple card 80
        Corner corner_Ur_gc80 = new Corner(false);
        Corner corner_Lr_gc80 = new Corner(true);
        Corner corner_Ul_gc80 = new Corner(false);
        Corner corner_Ll_gc80 = new Corner(true);
        Corner cornerB_Ur_gc80 = new Corner(false);
        Corner cornerB_Ul_gc80 = new Corner(false);
        Corner cornerB_Lr_gc80 = new Corner(false);
        Corner cornerB_Ll_gc80 = new Corner(false);
        Face front_gc80 = new Face(corner_Ur_gc80, corner_Ul_gc80, corner_Lr_gc80, corner_Ll_gc80);
        Face back_gc80 = new Face(cornerB_Ur_gc80, cornerB_Ul_gc80, cornerB_Lr_gc80, cornerB_Ll_gc80, ResourceType.INSECT);
        GoldCard gold_card_80 = new GoldCard(Color.PURPLE, front_gc80, back_gc80, 5, 0, 0, 0, 5, 80);
        gold_card_80.setShowingFront(false);
        goldCards.add(gold_card_80);

        return goldCards;
    }

    /**
     * Creates the initial cards for the game
     *
     * @return an ArrayList of InitialCard objects
     */
    private static ArrayList<InitialCard> createInitialCards() {
        ArrayList<InitialCard> initialCards = new ArrayList<>();

        //starting cards
        //starting card 81
        Corner corner_Ur_sc81 = new Corner(ResourceType.LEAF);
        Corner corner_Lr_sc81 = new Corner(false);
        Corner corner_Ul_sc81 = new Corner(false);
        Corner corner_Ll_sc81 = new Corner(ResourceType.INSECT);
        Corner cornerB_Ur_sc81 = new Corner(ResourceType.LEAF);
        Corner cornerB_Lr_sc81 = new Corner(ResourceType.ANIMAL);
        Corner cornerB_Ul_sc81 = new Corner(ResourceType.MUSHROOM);
        Corner cornerB_Ll_sc81 = new Corner(ResourceType.INSECT);
        Face front_sc81 = new Face(corner_Ur_sc81, corner_Ul_sc81, corner_Lr_sc81, corner_Ll_sc81, ResourceType.INSECT);
        Face back_sc81 = new Face(cornerB_Ur_sc81, cornerB_Ul_sc81, cornerB_Lr_sc81, cornerB_Ll_sc81);
        InitialCard initial_card_81 = new InitialCard(front_sc81, back_sc81, 81);
        initial_card_81.setShowingFront(false);
        initialCards.add(initial_card_81);

        //starting card 82
        Corner corner_Ur_sc82 = new Corner(false);
        Corner corner_Lr_sc82 = new Corner(ResourceType.MUSHROOM);
        Corner corner_Ul_sc82 = new Corner(ResourceType.ANIMAL);
        Corner corner_Ll_sc82 = new Corner(false);
        Corner cornerB_Ur_sc82 = new Corner(ResourceType.ANIMAL);
        Corner cornerB_Lr_sc82 = new Corner(ResourceType.INSECT);
        Corner cornerB_Ul_sc82 = new Corner(ResourceType.LEAF);
        Corner cornerB_Ll_sc82 = new Corner(ResourceType.MUSHROOM);
        Face front_sc82 = new Face(corner_Ur_sc82, corner_Ul_sc82, corner_Lr_sc82, corner_Ll_sc82, ResourceType.MUSHROOM);
        Face back_sc82 = new Face(cornerB_Ur_sc82, cornerB_Ul_sc82, cornerB_Lr_sc82, cornerB_Ll_sc82);
        InitialCard initial_card_82 = new InitialCard(front_sc82, back_sc82, 82);
        initial_card_82.setShowingFront(false);
        initialCards.add(initial_card_82);

        //starting card 83
        Corner corner_Ur_sc83 = new Corner(false);
        Corner corner_Lr_sc83 = new Corner(false);
        Corner corner_Ul_sc83 = new Corner(false);
        Corner corner_Ll_sc83 = new Corner(false);
        Corner cornerB_Ur_sc83 = new Corner(ResourceType.ANIMAL);
        Corner cornerB_Lr_sc83 = new Corner(ResourceType.LEAF);
        Corner cornerB_Ul_sc83 = new Corner(ResourceType.INSECT);
        Corner cornerB_Ll_sc83 = new Corner(ResourceType.MUSHROOM);
        Face front_sc83 = new Face(corner_Ur_sc83, corner_Ul_sc83, corner_Lr_sc83, corner_Ll_sc83, ResourceType.MUSHROOM, ResourceType.LEAF);
        Face back_sc83 = new Face(cornerB_Ur_sc83, cornerB_Ul_sc83, cornerB_Lr_sc83, cornerB_Ll_sc83);
        InitialCard initial_card_83 = new InitialCard(front_sc83, back_sc83, 83);
        initial_card_83.setShowingFront(false);
        initialCards.add(initial_card_83);

        //starting card 84
        Corner corner_Ur_sc84 = new Corner(false);
        Corner corner_Lr_sc84 = new Corner(false);
        Corner corner_Ul_sc84 = new Corner(false);
        Corner corner_Ll_sc84 = new Corner(false);
        Corner cornerB_Ur_sc84 = new Corner(ResourceType.INSECT);
        Corner cornerB_Lr_sc84 = new Corner(ResourceType.MUSHROOM);
        Corner cornerB_Ul_sc84 = new Corner(ResourceType.LEAF);
        Corner cornerB_Ll_sc84 = new Corner(ResourceType.ANIMAL);
        Face front_sc84 = new Face(corner_Ur_sc84, corner_Ul_sc84, corner_Lr_sc84, corner_Ll_sc84, ResourceType.ANIMAL, ResourceType.INSECT);
        Face back_sc84 = new Face(cornerB_Ur_sc84, cornerB_Ul_sc84, cornerB_Lr_sc84, cornerB_Ll_sc84);
        InitialCard initial_card_84 = new InitialCard(front_sc84, back_sc84, 84);
        initial_card_84.setShowingFront(false);
        initialCards.add(initial_card_84);

        //starting card 85
        Corner corner_Ur_sc85 = new Corner(false);
        Corner corner_Lr_sc85 = new Corner(true);
        Corner corner_Ul_sc85 = new Corner(false);
        Corner corner_Ll_sc85 = new Corner(true);
        Corner cornerB_Ur_sc85 = new Corner(ResourceType.MUSHROOM);
        Corner cornerB_Lr_sc85 = new Corner(ResourceType.ANIMAL);
        Corner cornerB_Ul_sc85 = new Corner(ResourceType.INSECT);
        Corner cornerB_Ll_sc85 = new Corner(ResourceType.LEAF);
        Face front_sc85 = new Face(corner_Ur_sc85, corner_Ul_sc85, corner_Lr_sc85, corner_Ll_sc85, ResourceType.ANIMAL, ResourceType.INSECT, ResourceType.LEAF);
        Face back_sc85 = new Face(cornerB_Ur_sc85, cornerB_Ul_sc85, cornerB_Lr_sc85, cornerB_Ll_sc85);
        InitialCard initial_card_85 = new InitialCard(front_sc85, back_sc85, 85);
        initial_card_85.setShowingFront(false);
        initialCards.add(initial_card_85);

        //starting card 86
        Corner corner_Ur_sc86 = new Corner(false);
        Corner corner_Lr_sc86 = new Corner(true);
        Corner corner_Ul_sc86 = new Corner(false);
        Corner corner_Ll_sc86 = new Corner(true);
        Corner cornerB_Ur_sc86 = new Corner(ResourceType.ANIMAL);
        Corner cornerB_Lr_sc86 = new Corner(ResourceType.INSECT);
        Corner cornerB_Ul_sc86 = new Corner(ResourceType.MUSHROOM);
        Corner cornerB_Ll_sc86 = new Corner(ResourceType.LEAF);
        Face front_sc86 = new Face(corner_Ur_sc86, corner_Ul_sc86, corner_Lr_sc86, corner_Ll_sc86, ResourceType.ANIMAL, ResourceType.MUSHROOM, ResourceType.LEAF);
        Face back_sc86 = new Face(cornerB_Ur_sc86, cornerB_Ul_sc86, cornerB_Lr_sc86, cornerB_Ll_sc86);
        InitialCard initial_card_86 = new InitialCard(front_sc86, back_sc86, 86);
        initial_card_86.setShowingFront(false);
        initialCards.add(initial_card_86);

        return initialCards;
    }

    /**
     * Creates the objective cards for the game
     *
     * @return an ArrayList of ObjectiveCard objects
     */
    private static ArrayList<ObjectiveCard> createObjectiveCards() {
        ArrayList<ObjectiveCard> objectiveCards = new ArrayList<>();

        //objective card 87
        Corner Blank = new Corner(true);
        Face BlankFace = new Face(Blank, Blank, Blank, Blank);
        ObjectiveCard objective_card_87 = new ObjectiveCard(Color.RED, BlankFace, BlankFace, 2, 87);
        objectiveCards.add(objective_card_87);

        //objective card 88
        ObjectiveCard objective_card_88 = new ObjectiveCard(Color.GREEN, BlankFace, BlankFace, 2, 88);
        objectiveCards.add(objective_card_88);

        //objective card 89
        ObjectiveCard objective_card_89 = new ObjectiveCard(Color.BLUE, BlankFace, BlankFace, 2, 89);
        objectiveCards.add(objective_card_89);

        //objective card 90
        ObjectiveCard objective_card_90 = new ObjectiveCard(Color.PURPLE, BlankFace, BlankFace, 2, 90);
        objectiveCards.add(objective_card_90);

        //objective card 91
        ObjectiveCard objective_card_91 = new ObjectiveCard(Color.RED, BlankFace, BlankFace, 3, 91);
        objectiveCards.add(objective_card_91);

        //objective card 92
        ObjectiveCard objective_card_92 = new ObjectiveCard(Color.GREEN, BlankFace, BlankFace, 3, 92);
        objectiveCards.add(objective_card_92);

        //objective card 93
        ObjectiveCard objective_card_93 = new ObjectiveCard(Color.BLUE, BlankFace, BlankFace, 3, 93);
        objectiveCards.add(objective_card_93);

        //objective card 94
        ObjectiveCard objective_card_94 = new ObjectiveCard(Color.PURPLE, BlankFace, BlankFace, 3, 94);
        objectiveCards.add(objective_card_94);

        //objective card 95
        ObjectiveCard objective_card_95 = new ObjectiveCard(Color.RED, BlankFace, BlankFace, 2, 95);
        objectiveCards.add(objective_card_95);

        //objective card 96
        ObjectiveCard objective_card_96 = new ObjectiveCard(Color.GREEN, BlankFace, BlankFace, 2, 96);
        objectiveCards.add(objective_card_96);

        //objective card 97
        ObjectiveCard objective_card_97 = new ObjectiveCard(Color.BLUE, BlankFace, BlankFace, 2, 97);
        objectiveCards.add(objective_card_97);

        //objective card 98
        ObjectiveCard objective_card_98 = new ObjectiveCard(Color.PURPLE, BlankFace, BlankFace, 2, 98);
        objectiveCards.add(objective_card_98);

        //objective card 99
        ObjectiveCard objective_card_99 = new ObjectiveCard(Color.YELLOW, BlankFace, BlankFace, 3, 99);
        objectiveCards.add(objective_card_99);

        //objective card 100
        ObjectiveCard objective_card_100 = new ObjectiveCard(Color.YELLOW, BlankFace, BlankFace, 2, 100);
        objectiveCards.add(objective_card_100);

        //objective card 101
        ObjectiveCard objective_card_101 = new ObjectiveCard(Color.YELLOW, BlankFace, BlankFace, 2, 101);
        objectiveCards.add(objective_card_101);

        //objective card 102
        ObjectiveCard objective_card_102 = new ObjectiveCard(Color.YELLOW, BlankFace, BlankFace, 3, 102);
        objectiveCards.add(objective_card_102);

        return objectiveCards;

    }

    /**
     * this method serializes the deck builder to the resources folder (to use in case of changes to the deck structure)
     *
     * @param toSerialize the deck builder to serialize
     */
    private static void serialize(DeckBuilder toSerialize) {
        Gson gson = new Gson();
        String json = gson.toJson(toSerialize);

        try (FileWriter file = new FileWriter("src/main/resources/deck.json")) {
            file.write(json);
        } catch (IOException e) {
            System.out.println("error writing json file for the deck");
        }
    }

    /**
     * utility method to load json file, in case of failure it generates the deck manually
     *
     * @return the deck builder containing array lists of cards to use to build the game deck
     */
    public static DeckBuilder deserialize() {
        Gson gson = new Gson();

        DeckBuilder deck = new DeckBuilder();
        InputStreamReader reader = new InputStreamReader(Objects.requireNonNull(DeckBuilder.class.getResourceAsStream("/deck.json")));

        deck = gson.fromJson(reader, DeckBuilder.class);

        return deck;
    }

    //GETTER

    public ArrayList<ResourceCard> getResourceCards() {
        return this.resourceCards;
    }

    public ArrayList<GoldCard> getGoldCards() {
        return this.goldCards;
    }

    public ArrayList<ObjectiveCard> getObjectiveCards() {
        return this.objectiveCards;
    }

    public ArrayList<InitialCard> getInitialCards() {
        return this.initialCards;
    }

}
