package codex.lb04.Model;

import codex.lb04.Model.Enumerations.Color;
import codex.lb04.Model.Enumerations.ResourceType;

import java.util.*;

/**
 * this class represents the deck of cards
 */
public class Deck {
    private ArrayList<Card> ResourceCards;
    private ArrayList<Card> GoldCards;
    private static Deck instance;

    /**
     * Default constructor
     */
    private Deck() {
        ResourceCards = new ArrayList<>();
        GoldCards = new ArrayList<>();
        initializeDeck();
    }
    /**
     * returns the deck instance
     * @return the deck instance
     */
    public static Deck getInstance() {
        if (instance == null) {
            instance = new Deck();
        }
        return instance;
    }
    /**
     * this method returns the deck of resource cards
     * @return the deck of resource cards
     */
    public ArrayList<Card> getResourceCards() {
        return ResourceCards;
    }

    /**
     * this method returns the deck of gold cards
     * @return the deck of gold cards
     */
    public ArrayList<Card> getGoldCards() {
        return GoldCards;
    }
    /**
     * this method shuffles the deck of resources cards
     */
    public void shuffleResources() {
        Collections.shuffle(ResourceCards);
    }
    /**
     * this method shuffles the deck of gold cards
     *
     */
    public void shuffleGold() {
        Collections.shuffle(GoldCards);
    }
    /**
     * this method draws a card from the deck of resources
     * @return the card drawn
     */
    public Card drawResource() {
        if(ResourceCards.isEmpty()){
            throw new IllegalStateException("Deck is empty");
        }
        return ResourceCards.removeFirst();
    }
    /**
     * this method draws a card from the deck of gold
     * @return the card drawn
     */
    public Card drawGold() {
        if (GoldCards.isEmpty()) {
            throw new IllegalStateException("Deck is empty");
        }
        return GoldCards.removeFirst();
    }
    /**
     * this method creates the deck of cards and returns it
     */
    public void initializeDeck() {

    }

    public static void main(String[] args) {

        //red resource cards
        //red card 1
        Corner corner_Ur_rc1 = new Corner(true);
        Corner corner_Lr_rc1 = new Corner(false);
        Corner corner_Ul_rc1 = new Corner(ResourceType.MUSHROOM);
        Corner corner_Ll_rc1 = new Corner(ResourceType.MUSHROOM);
        Corner cornerB_Ur_rc1 = new Corner(true);
        Corner cornerB_Ul_rc1 = new Corner(true);
        Corner cornerB_Lr_rc1 = new Corner(true);
        Corner cornerB_Ll_rc1 = new Corner(true);
        Face front_rc1 = new Face(corner_Ur_rc1,corner_Ul_rc1,corner_Lr_rc1,corner_Ll_rc1);
        Face back_rc1 = new Face(cornerB_Ur_rc1,cornerB_Ul_rc1,cornerB_Lr_rc1,cornerB_Ll_rc1 ,ResourceType.MUSHROOM);
        ResourceCard resource_card_1 = new ResourceCard(Color.RED,front_rc1,back_rc1,0);

        //red card 2
        Corner corner_Ur_rc2 = new Corner(ResourceType.MUSHROOM);
        Corner corner_Lr_rc2 = new Corner(true);
        Corner corner_Ul_rc2 = new Corner(ResourceType.MUSHROOM);
        Corner corner_Ll_rc2 = new Corner(false);
        Corner cornerB_Ur_rc2 = new Corner(true);
        Corner cornerB_Ul_rc2 = new Corner(true);
        Corner cornerB_Lr_rc2 = new Corner(true);
        Corner cornerB_Ll_rc2 = new Corner(true);
        Face front_rc2 = new Face(corner_Ur_rc2,corner_Ul_rc2,corner_Lr_rc2,corner_Ll_rc2);
        Face back_rc2 = new Face(cornerB_Ur_rc2,cornerB_Ul_rc2,cornerB_Lr_rc2,cornerB_Ll_rc2 ,ResourceType.MUSHROOM);
        ResourceCard resource_card_2 = new ResourceCard(Color.RED,front_rc2,back_rc2,0);

        //red card 3
        Corner corner_Ur_rc3 = new Corner(false);
        Corner corner_Lr_rc3 = new Corner(ResourceType.MUSHROOM);
        Corner corner_Ul_rc3 = new Corner(true);
        Corner corner_Ll_rc3 = new Corner(ResourceType.MUSHROOM);
        Corner cornerB_Ur_rc3 = new Corner(true);
        Corner cornerB_Ul_rc3 = new Corner(true);
        Corner cornerB_Lr_rc3 = new Corner(true);
        Corner cornerB_Ll_rc3 = new Corner(true);
        Face front_rc3 = new Face(corner_Ur_rc3,corner_Ul_rc3,corner_Lr_rc3,corner_Ll_rc3);
        Face back_rc3 = new Face(cornerB_Ur_rc3,cornerB_Ul_rc3,cornerB_Lr_rc3,cornerB_Ll_rc3 ,ResourceType.MUSHROOM);
        ResourceCard resource_card_3 = new ResourceCard(Color.RED,front_rc3,back_rc3,0);

        //red card 4
        Corner corner_Ur_rc4 = new Corner(ResourceType.MUSHROOM);
        Corner corner_Lr_rc4 = new Corner(ResourceType.MUSHROOM);
        Corner corner_Ul_rc4 = new Corner(false);
        Corner corner_Ll_rc4 = new Corner(true);
        Corner cornerB_Ur_rc4 = new Corner(true);
        Corner cornerB_Ul_rc4 = new Corner(true);
        Corner cornerB_Lr_rc4 = new Corner(true);
        Corner cornerB_Ll_rc4 = new Corner(true);
        Face front_rc4 = new Face(corner_Ur_rc4,corner_Ul_rc4,corner_Lr_rc4,corner_Ll_rc4);
        Face back_rc4 = new Face(cornerB_Ur_rc4,cornerB_Ul_rc4,cornerB_Lr_rc4,cornerB_Ll_rc4 ,ResourceType.MUSHROOM);
        ResourceCard resource_card_4 = new ResourceCard(Color.RED,front_rc4,back_rc4,0);

        //red card 5
        Corner corner_Ur_rc5 = new Corner(ResourceType.QUILL);
        Corner corner_Lr_rc5 = new Corner(ResourceType.MUSHROOM);
        Corner corner_Ul_rc5 = new Corner(false);
        Corner corner_Ll_rc5 = new Corner(ResourceType.LEAF);
        Corner cornerB_Ur_rc5 = new Corner(true);
        Corner cornerB_Ul_rc5 = new Corner(true);
        Corner cornerB_Lr_rc5 = new Corner(true);
        Corner cornerB_Ll_rc5 = new Corner(true);
        Face front_rc5 = new Face(corner_Ur_rc5,corner_Ul_rc5,corner_Lr_rc5,corner_Ll_rc5);
        Face back_rc5 = new Face(cornerB_Ur_rc5,cornerB_Ul_rc5,cornerB_Lr_rc5,cornerB_Ll_rc5 ,ResourceType.MUSHROOM);
        ResourceCard resource_card_5 = new ResourceCard(Color.RED,front_rc5,back_rc5,0);

        //red card 6
        Corner corner_Ur_rc6 = new Corner(ResourceType.MUSHROOM);
        Corner corner_Lr_rc6 = new Corner(ResourceType.ANIMAL);
        Corner corner_Ul_rc6 = new Corner(ResourceType.INKWELL);
        Corner corner_Ll_rc6 = new Corner(false);
        Corner cornerB_Ur_rc6 = new Corner(true);
        Corner cornerB_Ul_rc6 = new Corner(true);
        Corner cornerB_Lr_rc6 = new Corner(true);
        Corner cornerB_Ll_rc6 = new Corner(true);
        Face front_rc6 = new Face(corner_Ur_rc6,corner_Ul_rc6,corner_Lr_rc6,corner_Ll_rc6);
        Face back_rc6 = new Face(cornerB_Ur_rc6,cornerB_Ul_rc6,cornerB_Lr_rc6,cornerB_Ll_rc6 ,ResourceType.MUSHROOM);
        ResourceCard resource_card_6 = new ResourceCard(Color.RED,front_rc6,back_rc6,0);

        //red card 7
        Corner corner_Ur_rc7 = new Corner(ResourceType.INSECT);
        Corner corner_Lr_rc7 = new Corner(true);
        Corner corner_Ul_rc7 = new Corner(ResourceType.MUSHROOM);
        Corner corner_Ll_rc7 = new Corner(ResourceType.MANUSCRIPT);
        Corner cornerB_Ur_rc7 = new Corner(true);
        Corner cornerB_Ul_rc7 = new Corner(true);
        Corner cornerB_Lr_rc7 = new Corner(true);
        Corner cornerB_Ll_rc7 = new Corner(true);
        Face front_rc7 = new Face(corner_Ur_rc7,corner_Ul_rc7,corner_Lr_rc7,corner_Ll_rc7);
        Face back_rc7 = new Face(cornerB_Ur_rc7,cornerB_Ul_rc7,cornerB_Lr_rc7,cornerB_Ll_rc7 ,ResourceType.MUSHROOM);
        ResourceCard resource_card_7 = new ResourceCard(Color.RED,front_rc7,back_rc7,0);

        //red card 8
        Corner corner_Ur_rc8 = new Corner(ResourceType.MUSHROOM);
        Corner corner_Lr_rc8 = new Corner(false);
        Corner corner_Ul_rc8 = new Corner(true);
        Corner corner_Ll_rc8 = new Corner(true);
        Corner cornerB_Ur_rc8 = new Corner(true);
        Corner cornerB_Ul_rc8 = new Corner(true);
        Corner cornerB_Lr_rc8 = new Corner(true);
        Corner cornerB_Ll_rc8 = new Corner(true);
        Face front_rc8 = new Face(corner_Ur_rc8,corner_Ul_rc8,corner_Lr_rc8,corner_Ll_rc8);
        Face back_rc8 = new Face(cornerB_Ur_rc8,cornerB_Ul_rc8,cornerB_Lr_rc8,cornerB_Ll_rc8 ,ResourceType.MUSHROOM);
        ResourceCard resource_card_8 = new ResourceCard(Color.RED,front_rc8,back_rc8,1);

        //red card 9
        Corner corner_Ur_rc9 = new Corner(false);
        Corner corner_Lr_rc9 = new Corner(true);
        Corner corner_Ul_rc9 = new Corner(ResourceType.MUSHROOM);
        Corner corner_Ll_rc9 = new Corner(true);
        Corner cornerB_Ur_rc9 = new Corner(true);
        Corner cornerB_Ul_rc9 = new Corner(true);
        Corner cornerB_Lr_rc9 = new Corner(true);
        Corner cornerB_Ll_rc9 = new Corner(true);
        Face front_rc9 = new Face(corner_Ur_rc9,corner_Ul_rc9,corner_Lr_rc9,corner_Ll_rc9);
        Face back_rc9 = new Face(cornerB_Ur_rc9,cornerB_Ul_rc9,cornerB_Lr_rc9,cornerB_Ll_rc9 ,ResourceType.MUSHROOM);
        ResourceCard resource_card_9 = new ResourceCard(Color.RED,front_rc9,back_rc9,1);

        //red card 10
        Corner corner_Ur_rc10 = new Corner(true);
        Corner corner_Lr_rc10 = new Corner(true);
        Corner corner_Ul_rc10 = new Corner(false);
        Corner corner_Ll_rc10 = new Corner(ResourceType.MUSHROOM);
        Corner cornerB_Ur_rc10 = new Corner(true);
        Corner cornerB_Ul_rc10 = new Corner(true);
        Corner cornerB_Lr_rc10 = new Corner(true);
        Corner cornerB_Ll_rc10 = new Corner(true);
        Face front_rc10 = new Face(corner_Ur_rc10,corner_Ul_rc10,corner_Lr_rc10,corner_Ll_rc10);
        Face back_rc10 = new Face(cornerB_Ur_rc10,cornerB_Ul_rc10,cornerB_Lr_rc10,cornerB_Ll_rc10 ,ResourceType.MUSHROOM);
        ResourceCard resource_card_10 = new ResourceCard(Color.RED,front_rc10,back_rc10,1);




        //green cards
        //green card 11
        Corner corner_Ur_rc11 = new Corner(true);
        Corner corner_Lr_rc11 = new Corner(false);
        Corner corner_Ul_rc11 = new Corner(ResourceType.LEAF);
        Corner corner_Ll_rc11 = new Corner(ResourceType.LEAF);
        Corner cornerB_Ur_rc11 = new Corner(true);
        Corner cornerB_Ul_rc11 = new Corner(true);
        Corner cornerB_Lr_rc11 = new Corner(true);
        Corner cornerB_Ll_rc11 = new Corner(true);
        Face front_rc11 = new Face(corner_Ur_rc11,corner_Ul_rc11,corner_Lr_rc11,corner_Ll_rc11);
        Face back_rc11 = new Face(cornerB_Ur_rc11,cornerB_Ul_rc11,cornerB_Lr_rc11,cornerB_Ll_rc11 ,ResourceType.LEAF);
        ResourceCard resource_card_11 = new ResourceCard(Color.GREEN,front_rc11,back_rc11,0);

        //green card 12
        Corner corner_Ur_rc12 = new Corner(ResourceType.LEAF);
        Corner corner_Lr_rc12 = new Corner(true);
        Corner corner_Ul_rc12 = new Corner(ResourceType.LEAF);
        Corner corner_Ll_rc12 = new Corner(false);
        Corner cornerB_Ur_rc12 = new Corner(true);
        Corner cornerB_Ul_rc12 = new Corner(true);
        Corner cornerB_Lr_rc12 = new Corner(true);
        Corner cornerB_Ll_rc12 = new Corner(true);
        Face front_rc12 = new Face(corner_Ur_rc12,corner_Ul_rc12,corner_Lr_rc12,corner_Ll_rc12);
        Face back_rc12 = new Face(cornerB_Ur_rc12,cornerB_Ul_rc12,cornerB_Lr_rc12,cornerB_Ll_rc12 ,ResourceType.LEAF);
        ResourceCard resource_card_12 = new ResourceCard(Color.GREEN,front_rc12,back_rc12,0);

        // green card 13
        Corner corner_Ur_rc13 = new Corner(false);
        Corner corner_Lr_rc13= new Corner(ResourceType.LEAF);
        Corner corner_Ul_rc13 = new Corner(true);
        Corner corner_Ll_rc13 = new Corner(ResourceType.LEAF);
        Corner cornerB_Ur_rc13 = new Corner(true);
        Corner cornerB_Ul_rc13 = new Corner(true);
        Corner cornerB_Lr_rc13 = new Corner(true);
        Corner cornerB_Ll_rc13 = new Corner(true);
        Face front_rc13 = new Face(corner_Ur_rc13,corner_Ul_rc13,corner_Lr_rc13,corner_Ll_rc13);
        Face back_rc13 = new Face(cornerB_Ur_rc13,cornerB_Ul_rc13,cornerB_Lr_rc13,cornerB_Ll_rc13 ,ResourceType.LEAF);
        ResourceCard resource_card_13 = new ResourceCard(Color.GREEN,front_rc13,back_rc13,0);

        //green card 14
        Corner corner_Ur_rc14 = new Corner(ResourceType.LEAF);
        Corner corner_Lr_rc14 = new Corner(ResourceType.LEAF);
        Corner corner_Ul_rc14 = new Corner(false);
        Corner corner_Ll_rc14 = new Corner(true);
        Corner cornerB_Ur_rc14 = new Corner(true);
        Corner cornerB_Ul_rc14 = new Corner(true);
        Corner cornerB_Lr_rc14 = new Corner(true);
        Corner cornerB_Ll_rc14 = new Corner(true);
        Face front_rc14 = new Face(corner_Ur_rc14,corner_Ul_rc14,corner_Lr_rc14,corner_Ll_rc14);
        Face back_rc14 = new Face(cornerB_Ur_rc14,cornerB_Ul_rc14,cornerB_Lr_rc14,cornerB_Ll_rc14 ,ResourceType.LEAF);
        ResourceCard resource_card_14 = new ResourceCard(Color.GREEN,front_rc14,back_rc14,0);

        // green card 15
        Corner corner_Ur_rc15 = new Corner(ResourceType.INSECT);
        Corner corner_Lr_rc15 = new Corner(ResourceType.LEAF);
        Corner corner_Ul_rc15 = new Corner(false);
        Corner corner_Ll_rc15 = new Corner(ResourceType.QUILL);
        Corner cornerB_Ur_rc15 = new Corner(true);
        Corner cornerB_Ul_rc15 = new Corner(true);
        Corner cornerB_Lr_rc15 = new Corner(true);
        Corner cornerB_Ll_rc15 = new Corner(true);
        Face front_rc15 = new Face(corner_Ur_rc15,corner_Ul_rc15,corner_Lr_rc15,corner_Ll_rc15);
        Face back_rc15 = new Face(cornerB_Ur_rc15,cornerB_Ul_rc15,cornerB_Lr_rc15,cornerB_Ll_rc15 ,ResourceType.LEAF);
        ResourceCard resource_card_15 = new ResourceCard(Color.GREEN,front_rc15,back_rc15,0);

        //green card 16
        Corner corner_Ur_rc16 = new Corner(ResourceType.LEAF);
        Corner corner_Lr_rc16 = new Corner(ResourceType.INKWELL);
        Corner corner_Ul_rc16 = new Corner(ResourceType.MUSHROOM);
        Corner corner_Ll_rc16 = new Corner(false);
        Corner cornerB_Ur_rc16 = new Corner(true);
        Corner cornerB_Ul_rc16 = new Corner(true);
        Corner cornerB_Lr_rc16 = new Corner(true);
        Corner cornerB_Ll_rc16 = new Corner(true);
        Face front_rc16 = new Face(corner_Ur_rc16,corner_Ul_rc16,corner_Lr_rc16,corner_Ll_rc16);
        Face back_rc16 = new Face(cornerB_Ur_rc16,cornerB_Ul_rc16,cornerB_Lr_rc16,cornerB_Ll_rc16 ,ResourceType.LEAF);
        ResourceCard resource_card_16 = new ResourceCard(Color.GREEN,front_rc16,back_rc16,0);

        //green card 17
        Corner corner_Ur_rc17 = new Corner(false);
        Corner corner_Lr_rc17 = new Corner(ResourceType.ANIMAL);
        Corner corner_Ul_rc17 = new Corner(ResourceType.MANUSCRIPT);
        Corner corner_Ll_rc17 = new Corner(ResourceType.LEAF);
        Corner cornerB_Ur_rc17 = new Corner(true);
        Corner cornerB_Ul_rc17 = new Corner(true);
        Corner cornerB_Lr_rc17 = new Corner(true);
        Corner cornerB_Ll_rc17 = new Corner(true);
        Face front_rc17 = new Face(corner_Ur_rc17,corner_Ul_rc17,corner_Lr_rc17,corner_Ll_rc17);
        Face back_rc17= new Face(cornerB_Ur_rc17,cornerB_Ul_rc17,cornerB_Lr_rc17,cornerB_Ll_rc17 ,ResourceType.LEAF);
        ResourceCard resource_card_17 = new ResourceCard(Color.GREEN,front_rc17,back_rc17,0);

        //green card 18
        Corner corner_Ur_rc18 = new Corner(true);
        Corner corner_Lr_rc18 = new Corner(false);
        Corner corner_Ul_rc18 = new Corner(true);
        Corner corner_Ll_rc18 = new Corner(ResourceType.LEAF);
        Corner cornerB_Ur_rc18 = new Corner(true);
        Corner cornerB_Ul_rc18 = new Corner(true);
        Corner cornerB_Lr_rc18 = new Corner(true);
        Corner cornerB_Ll_rc18= new Corner(true);
        Face front_rc18 = new Face(corner_Ur_rc18,corner_Ul_rc18,corner_Lr_rc18,corner_Ll_rc18);
        Face back_rc18 = new Face(cornerB_Ur_rc18,cornerB_Ul_rc18,cornerB_Lr_rc18,cornerB_Ll_rc18,ResourceType.LEAF);
        ResourceCard resource_card_18 = new ResourceCard(Color.GREEN,front_rc18,back_rc18,1);

        //green card 19
        Corner corner_Ur_rc19 = new Corner(true);
        Corner corner_Lr_rc19 = new Corner(ResourceType.LEAF);
        Corner corner_Ul_rc19 = new Corner(true);
        Corner corner_Ll_rc19 = new Corner(false);
        Corner cornerB_Ur_rc19 = new Corner(true);
        Corner cornerB_Ul_rc19 = new Corner(true);
        Corner cornerB_Lr_rc19 = new Corner(true);
        Corner cornerB_Ll_rc19 = new Corner(true);
        Face front_rc19 = new Face(corner_Ur_rc19,corner_Ul_rc19,corner_Lr_rc19,corner_Ll_rc19);
        Face back_rc19 = new Face(cornerB_Ur_rc19,cornerB_Ul_rc19,cornerB_Lr_rc19,cornerB_Ll_rc19 ,ResourceType.LEAF);
        ResourceCard resource_card_19 = new ResourceCard(Color.GREEN,front_rc19,back_rc19,1);
        
        //green card 20
        Corner corner_Ur_rc20 = new Corner(ResourceType.LEAF);
        Corner corner_Lr_rc20 = new Corner(true);
        Corner corner_Ul_rc20 = new Corner(false);
        Corner corner_Ll_rc20 = new Corner(true);
        Corner cornerB_Ur_rc20 = new Corner(true);
        Corner cornerB_Ul_rc20 = new Corner(true);
        Corner cornerB_Lr_rc20 = new Corner(true);
        Corner cornerB_Ll_rc20 = new Corner(true);
        Face front_rc20 = new Face(corner_Ur_rc20,corner_Ul_rc20,corner_Lr_rc20,corner_Ll_rc20);
        Face back_rc20 = new Face(cornerB_Ur_rc20,cornerB_Ul_rc20,cornerB_Lr_rc20,cornerB_Ll_rc20,ResourceType.LEAF);
        ResourceCard resource_card_20 = new ResourceCard(Color.GREEN,front_rc20,back_rc20,1);
        
        //blue cards
        //blue card 21
        Corner corner_Ur_rc21 = new Corner(ResourceType.ANIMAL);
        Corner corner_Lr_rc21 = new Corner(false);
        Corner corner_Ul_rc21 = new Corner(ResourceType.ANIMAL);
        Corner corner_Ll_rc21 = new Corner(true);
        Corner cornerB_Ur_rc21 = new Corner(true);
        Corner cornerB_Ul_rc21 = new Corner(true);
        Corner cornerB_Lr_rc21 = new Corner(true);
        Corner cornerB_Ll_rc21 = new Corner(true);
        Face front_rc21 = new Face(corner_Ur_rc21,corner_Ul_rc21,corner_Lr_rc21,corner_Ll_rc21);
        Face back_rc21 = new Face(cornerB_Ur_rc21,cornerB_Ul_rc21,cornerB_Lr_rc21,cornerB_Ll_rc21 ,ResourceType.ANIMAL);
        ResourceCard resource_card_21 = new ResourceCard(Color.BLUE,front_rc21,back_rc21,0);

        //blue card 22
        Corner corner_Ur_rc22 = new Corner(true);
        Corner corner_Lr_rc22 = new Corner(ResourceType.ANIMAL);
        Corner corner_Ul_rc22 = new Corner(false);
        Corner corner_Ll_rc22 = new Corner(ResourceType.ANIMAL);
        Corner cornerB_Ur_rc22 = new Corner(true);
        Corner cornerB_Ul_rc22 = new Corner(true);
        Corner cornerB_Lr_rc22 = new Corner(true);
        Corner cornerB_Ll_rc22 = new Corner(true);
        Face front_rc22 = new Face(corner_Ur_rc22,corner_Ul_rc22,corner_Lr_rc22,corner_Ll_rc22);
        Face back_rc22 = new Face(cornerB_Ur_rc22,cornerB_Ul_rc22,cornerB_Lr_rc22,cornerB_Ll_rc22 ,ResourceType.ANIMAL);
        ResourceCard resource_card_22 = new ResourceCard(Color.BLUE,front_rc22,back_rc22,0);

        //blue card 23
        Corner corner_Ur_rc23 = new Corner(false);
        Corner corner_Lr_rc23 = new Corner(true);
        Corner corner_Ul_rc23 = new Corner(ResourceType.ANIMAL);
        Corner corner_Ll_rc23 = new Corner(ResourceType.ANIMAL);
        Corner cornerB_Ur_rc23 = new Corner(true);
        Corner cornerB_Ul_rc23 = new Corner(true);
        Corner cornerB_Lr_rc23 = new Corner(true);
        Corner cornerB_Ll_rc23 = new Corner(true);
        Face front_rc23 = new Face(corner_Ur_rc23,corner_Ul_rc23,corner_Lr_rc23,corner_Ll_rc23);
        Face back_rc23 = new Face(cornerB_Ur_rc23,cornerB_Ul_rc23,cornerB_Lr_rc23,cornerB_Ll_rc23 ,ResourceType.ANIMAL);
        ResourceCard resource_card_23 = new ResourceCard(Color.BLUE,front_rc23,back_rc23,0);

        //blue card 24
        Corner corner_Ur_rc24 = new Corner(ResourceType.ANIMAL);
        Corner corner_Lr_rc24 = new Corner(ResourceType.ANIMAL);
        Corner corner_Ul_rc24 = new Corner(true);
        Corner corner_Ll_rc24 = new Corner(false);
        Corner cornerB_Ur_rc24 = new Corner(true);
        Corner cornerB_Ul_rc24 = new Corner(true);
        Corner cornerB_Lr_rc24 = new Corner(true);
        Corner cornerB_Ll_rc24 = new Corner(true);
        Face front_rc24 = new Face(corner_Ur_rc24,corner_Ul_rc24,corner_Lr_rc24,corner_Ll_rc24);
        Face back_rc24 = new Face(cornerB_Ur_rc24,cornerB_Ul_rc24,cornerB_Lr_rc24,cornerB_Ll_rc24 ,ResourceType.ANIMAL);
        ResourceCard resource_card_24 = new ResourceCard(Color.BLUE,front_rc24,back_rc24,0);

        //blue card 25
        Corner corner_Ur_rc25 = new Corner(ResourceType.INSECT);
        Corner corner_Lr_rc25 = new Corner(ResourceType.ANIMAL);
        Corner corner_Ul_rc25 = new Corner(false);
        Corner corner_Ll_rc25 = new Corner(ResourceType.INKWELL);
        Corner cornerB_Ur_rc25 = new Corner(true);
        Corner cornerB_Ul_rc25 = new Corner(true);
        Corner cornerB_Lr_rc25 = new Corner(true);
        Corner cornerB_Ll_rc25 = new Corner(true);
        Face front_rc25 = new Face(corner_Ur_rc25,corner_Ul_rc25,corner_Lr_rc25,corner_Ll_rc25);
        Face back_rc25 = new Face(cornerB_Ur_rc25,cornerB_Ul_rc25,cornerB_Lr_rc25,cornerB_Ll_rc25 ,ResourceType.ANIMAL);
        ResourceCard resource_card_25 = new ResourceCard(Color.BLUE,front_rc25,back_rc25,0);

        //blue card 26
        Corner corner_Ur_rc26 = new Corner(ResourceType.ANIMAL);
        Corner corner_Lr_rc26 = new Corner(ResourceType.MANUSCRIPT);
        Corner corner_Ul_rc26 = new Corner(ResourceType.LEAF);
        Corner corner_Ll_rc26 = new Corner(false);
        Corner cornerB_Ur_rc26 = new Corner(true);
        Corner cornerB_Ul_rc26 = new Corner(true);
        Corner cornerB_Lr_rc26 = new Corner(true);
        Corner cornerB_Ll_rc26 = new Corner(true);
        Face front_rc26 = new Face(corner_Ur_rc26,corner_Ul_rc26,corner_Lr_rc26,corner_Ll_rc26);
        Face back_rc26 = new Face(cornerB_Ur_rc26,cornerB_Ul_rc26,cornerB_Lr_rc26,cornerB_Ll_rc26 ,ResourceType.ANIMAL);
        ResourceCard resource_card_26 = new ResourceCard(Color.BLUE,front_rc26,back_rc26,0);

        //blue card 27
        Corner corner_Ur_rc27 = new Corner(false);
        Corner corner_Lr_rc27 = new Corner(ResourceType.MUSHROOM);
        Corner corner_Ul_rc27 = new Corner(ResourceType.QUILL);
        Corner corner_Ll_rc27 = new Corner(ResourceType.ANIMAL);
        Corner cornerB_Ur_rc27 = new Corner(true);
        Corner cornerB_Ul_rc27 = new Corner(true);
        Corner cornerB_Lr_rc27 = new Corner(true);
        Corner cornerB_Ll_rc27 = new Corner(true);
        Face front_rc27 = new Face(corner_Ur_rc27,corner_Ul_rc27,corner_Lr_rc27,corner_Ll_rc27);
        Face back_rc27 = new Face(cornerB_Ur_rc27,cornerB_Ul_rc27,cornerB_Lr_rc27,cornerB_Ll_rc27 ,ResourceType.ANIMAL);
        ResourceCard resource_card_27 = new ResourceCard(Color.BLUE,front_rc27,back_rc27,0);

        //blue card 28
        Corner corner_Ur_rc28 = new Corner(true);
        Corner corner_Lr_rc28 = new Corner(true);
        Corner corner_Ul_rc28 = new Corner(false);
        Corner corner_Ll_rc28 = new Corner(ResourceType.ANIMAL);
        Corner cornerB_Ur_rc28 = new Corner(true);
        Corner cornerB_Ul_rc28 = new Corner(true);
        Corner cornerB_Lr_rc28 = new Corner(true);
        Corner cornerB_Ll_rc28 = new Corner(true);
        Face front_rc28 = new Face(corner_Ur_rc28,corner_Ul_rc28,corner_Lr_rc28,corner_Ll_rc28);
        Face back_rc28 = new Face(cornerB_Ur_rc28,cornerB_Ul_rc28,cornerB_Lr_rc28,cornerB_Ll_rc28 ,ResourceType.ANIMAL);
        ResourceCard resource_card_28 = new ResourceCard(Color.BLUE,front_rc28,back_rc28,1);

        //blue card 29
        Corner corner_Ur_rc29 = new Corner(false);
        Corner corner_Lr_rc29 = new Corner(ResourceType.ANIMAL);
        Corner corner_Ul_rc29 = new Corner(true);
        Corner corner_Ll_rc29 = new Corner(true);
        Corner cornerB_Ur_rc29 = new Corner(true);
        Corner cornerB_Ul_rc29 = new Corner(true);
        Corner cornerB_Lr_rc29 = new Corner(true);
        Corner cornerB_Ll_rc29 = new Corner(true);
        Face front_rc29 = new Face(corner_Ur_rc29,corner_Ul_rc29,corner_Lr_rc29,corner_Ll_rc29);
        Face back_rc29 = new Face(cornerB_Ur_rc29,cornerB_Ul_rc29,cornerB_Lr_rc29,cornerB_Ll_rc29 ,ResourceType.ANIMAL);
        ResourceCard resource_card_29 = new ResourceCard(Color.BLUE,front_rc29,back_rc29,1);

        //blue card 30
        Corner corner_Ur_rc30 = new Corner(ResourceType.ANIMAL);
        Corner corner_Lr_rc30 = new Corner(false);
        Corner corner_Ul_rc30 = new Corner(true);
        Corner corner_Ll_rc30 = new Corner(true);
        Corner cornerB_Ur_rc30 = new Corner(true);
        Corner cornerB_Ul_rc30 = new Corner(true);
        Corner cornerB_Lr_rc30 = new Corner(true);
        Corner cornerB_Ll_rc30 = new Corner(true);
        Face front_rc30 = new Face(corner_Ur_rc30,corner_Ul_rc30,corner_Lr_rc30,corner_Ll_rc30);
        Face back_rc30 = new Face(cornerB_Ur_rc30,cornerB_Ul_rc30,cornerB_Lr_rc30,cornerB_Ll_rc30 ,ResourceType.ANIMAL);
        ResourceCard resource_card_30 = new ResourceCard(Color.BLUE,front_rc30,back_rc30,1);
        

        //purple cards
        
        //purple card 31
        Corner corner_Ur_rc31 = new Corner(ResourceType.INSECT);
        Corner corner_Lr_rc31 = new Corner(false);
        Corner corner_Ul_rc31 = new Corner(ResourceType.INSECT);
        Corner corner_Ll_rc31 = new Corner(true);
        Corner cornerB_Ur_rc31 = new Corner(true);
        Corner cornerB_Ul_rc31 = new Corner(true);
        Corner cornerB_Lr_rc31 = new Corner(true);
        Corner cornerB_Ll_rc31 = new Corner(true);
        Face front_rc31 = new Face(corner_Ur_rc31,corner_Ul_rc31,corner_Lr_rc31,corner_Ll_rc31);
        Face back_rc31 = new Face(cornerB_Ur_rc31,cornerB_Ul_rc31,cornerB_Lr_rc31,cornerB_Ll_rc31 ,ResourceType.INSECT);
        ResourceCard resource_card_31 = new ResourceCard(Color.PURPLE,front_rc31,back_rc31,0);
        
        //purple card 32
        Corner corner_Ur_rc32 = new Corner(true);
        Corner corner_Lr_rc32 = new Corner(ResourceType.INSECT);
        Corner corner_Ul_rc32 = new Corner(false);
        Corner corner_Ll_rc32 = new Corner(ResourceType.INSECT);
        Corner cornerB_Ur_rc32 = new Corner(true);
        Corner cornerB_Ul_rc32 = new Corner(true);
        Corner cornerB_Lr_rc32 = new Corner(true);
        Corner cornerB_Ll_rc32 = new Corner(true);
        Face front_rc32 = new Face(corner_Ur_rc32,corner_Ul_rc32,corner_Lr_rc32,corner_Ll_rc32);
        Face back_rc32 = new Face(cornerB_Ur_rc32,cornerB_Ul_rc32,cornerB_Lr_rc32,cornerB_Ll_rc32,ResourceType.INSECT);
        ResourceCard resource_card_32 = new ResourceCard(Color.PURPLE,front_rc32,back_rc32,0);
        
        //purple card 33
        Corner corner_Ur_rc33 = new Corner(false);
        Corner corner_Lr_rc33 = new Corner(true);
        Corner corner_Ul_rc33 = new Corner(ResourceType.INSECT);
        Corner corner_Ll_rc33 = new Corner(ResourceType.INSECT);
        Corner cornerB_Ur_rc33 = new Corner(true);
        Corner cornerB_Ul_rc33 = new Corner(true);
        Corner cornerB_Lr_rc33 = new Corner(true);
        Corner cornerB_Ll_rc33 = new Corner(true);
        Face front_rc33 = new Face(corner_Ur_rc33,corner_Ul_rc33,corner_Lr_rc33,corner_Ll_rc33);
        Face back_rc33 = new Face(cornerB_Ur_rc33,cornerB_Ul_rc33,cornerB_Lr_rc33,cornerB_Ll_rc33 ,ResourceType.INSECT);
        ResourceCard resource_card_33 = new ResourceCard(Color.PURPLE,front_rc33,back_rc33,0);
        
        //purple card 34
        Corner corner_Ur_rc34 = new Corner(ResourceType.INSECT);
        Corner corner_Lr_rc34 = new Corner(ResourceType.INSECT);
        Corner corner_Ul_rc34 = new Corner(true);
        Corner corner_Ll_rc34 = new Corner(false);
        Corner cornerB_Ur_rc34 = new Corner(true);
        Corner cornerB_Ul_rc34 = new Corner(true);
        Corner cornerB_Lr_rc34 = new Corner(true);
        Corner cornerB_Ll_rc34 = new Corner(true);
        Face front_rc34 = new Face(corner_Ur_rc34,corner_Ul_rc34,corner_Lr_rc34,corner_Ll_rc34);
        Face back_rc34 = new Face(cornerB_Ur_rc34,cornerB_Ul_rc34,cornerB_Lr_rc34,cornerB_Ll_rc34,ResourceType.INSECT);
        ResourceCard resource_card_34 = new ResourceCard(Color.PURPLE,front_rc34,back_rc34,0);

        //purple card 35
        Corner corner_Ur_rc35 = new Corner(ResourceType.QUILL);
        Corner corner_Lr_rc35 = new Corner(ResourceType.INSECT);
        Corner corner_Ul_rc35 = new Corner(false);
        Corner corner_Ll_rc35 = new Corner(ResourceType.ANIMAL);
        Corner cornerB_Ur_rc35 = new Corner(true);
        Corner cornerB_Ul_rc35 = new Corner(true);
        Corner cornerB_Lr_rc35 = new Corner(true);
        Corner cornerB_Ll_rc35 = new Corner(true);
        Face front_rc35 = new Face(corner_Ur_rc35,corner_Ul_rc35,corner_Lr_rc35,corner_Ll_rc35);
        Face back_rc35 = new Face(cornerB_Ur_rc35,cornerB_Ul_rc35,cornerB_Lr_rc35,cornerB_Ll_rc35,ResourceType.INSECT);
        ResourceCard resource_card_35 = new ResourceCard(Color.PURPLE,front_rc35,back_rc35,0);
        
        //purple card 36
        Corner corner_Ur_rc36 = new Corner(ResourceType.INSECT);
        Corner corner_Lr_rc36 = new Corner(ResourceType.MUSHROOM);
        Corner corner_Ul_rc36 = new Corner(ResourceType.INSECT);
        Corner corner_Ll_rc36 = new Corner(false);
        Corner cornerB_Ur_rc36 = new Corner(true);
        Corner cornerB_Ul_rc36 = new Corner(true);
        Corner cornerB_Lr_rc36 = new Corner(true);
        Corner cornerB_Ll_rc36 = new Corner(true);
        Face front_rc36 = new Face(corner_Ur_rc36,corner_Ul_rc36,corner_Lr_rc36,corner_Ll_rc36);
        Face back_rc36 = new Face(cornerB_Ur_rc36,cornerB_Ul_rc36,cornerB_Lr_rc36,cornerB_Ll_rc36 ,ResourceType.INSECT);
        ResourceCard resource_card_36 = new ResourceCard(Color.PURPLE,front_rc36,back_rc36,0);
        
        //purple card 37
        Corner corner_Ur_rc37 = new Corner(ResourceType.LEAF);
        Corner corner_Lr_rc37 = new Corner(false);
        Corner corner_Ul_rc37 = new Corner(ResourceType.INSECT);
        Corner corner_Ll_rc37 = new Corner(ResourceType.INKWELL);
        Corner cornerB_Ur_rc37 = new Corner(true);
        Corner cornerB_Ul_rc37 = new Corner(true);
        Corner cornerB_Lr_rc37 = new Corner(true);
        Corner cornerB_Ll_rc37 = new Corner(true);
        Face front_rc37 = new Face(corner_Ur_rc37,corner_Ul_rc37,corner_Lr_rc37,corner_Ll_rc37);
        Face back_rc37 = new Face(cornerB_Ur_rc37,cornerB_Ul_rc37,cornerB_Lr_rc37,cornerB_Ll_rc37,ResourceType.INSECT);
        ResourceCard resource_card_37 = new ResourceCard(Color.PURPLE,front_rc37,back_rc37,0);
        
        //purple card 38
        Corner corner_Ur_rc38 = new Corner(false);
        Corner corner_Lr_rc38 = new Corner(true);
        Corner corner_Ul_rc38 = new Corner(ResourceType.INSECT);
        Corner corner_Ll_rc38 = new Corner(true);
        Corner cornerB_Ur_rc38 = new Corner(true);
        Corner cornerB_Ul_rc38 = new Corner(true);
        Corner cornerB_Lr_rc38 = new Corner(true);
        Corner cornerB_Ll_rc38 = new Corner(true);
        Face front_rc38 = new Face(corner_Ur_rc38,corner_Ul_rc38,corner_Lr_rc38,corner_Ll_rc38);
        Face back_rc38 = new Face(cornerB_Ur_rc38,cornerB_Ul_rc38,cornerB_Lr_rc38,cornerB_Ll_rc38,ResourceType.INSECT);
        ResourceCard resource_card_38 = new ResourceCard(Color.PURPLE,front_rc38,back_rc38,1);

        //purple card 39
        Corner corner_Ur_rc39 = new Corner(true);
        Corner corner_Lr_rc39 = new Corner(ResourceType.INSECT);
        Corner corner_Ul_rc39 = new Corner(true);
        Corner corner_Ll_rc39 = new Corner(false);
        Corner cornerB_Ur_rc39 = new Corner(true);
        Corner cornerB_Ul_rc39 = new Corner(true);
        Corner cornerB_Lr_rc39 = new Corner(true);
        Corner cornerB_Ll_rc39 = new Corner(true);
        Face front_rc39 = new Face(corner_Ur_rc39,corner_Ul_rc39,corner_Lr_rc39,corner_Ll_rc39);
        Face back_rc39 = new Face(cornerB_Ur_rc39,cornerB_Ul_rc39,cornerB_Lr_rc39,cornerB_Ll_rc39,ResourceType.INSECT);
        ResourceCard resource_card_39 = new ResourceCard(Color.PURPLE,front_rc39,back_rc39,1);

        //purple card 40
        Corner corner_Ur_rc40 = new Corner(ResourceType.INSECT);
        Corner corner_Lr_rc40 = new Corner(true);
        Corner corner_Ul_rc40 = new Corner(false);
        Corner corner_Ll_rc40 = new Corner(true);
        Corner cornerB_Ur_rc40 = new Corner(true);
        Corner cornerB_Ul_rc40 = new Corner(true);
        Corner cornerB_Lr_rc40 = new Corner(true);
        Corner cornerB_Ll_rc40 = new Corner(true);
        Face front_rc40 = new Face(corner_Ur_rc40,corner_Ul_rc40,corner_Lr_rc40,corner_Ll_rc40);
        Face back_rc40 = new Face(cornerB_Ur_rc40,cornerB_Ul_rc40,cornerB_Lr_rc40,cornerB_Ll_rc40,ResourceType.INSECT);
        ResourceCard resource_card_40 = new ResourceCard(Color.PURPLE,front_rc40,back_rc40,1);

        //red gold cards
        //red card 41
        Corner corner_Ur_gc41 = new Corner(true);
        Corner corner_Lr_gc41 = new Corner(false);
        Corner corner_Ul_gc41 = new Corner(ResourceType.MUSHROOM);
        Corner corner_Ll_gc41 = new Corner(ResourceType.MUSHROOM);
        Corner cornerB_Ur_gc41 = new Corner(true);
        Corner cornerB_Ul_gc41 = new Corner(true);
        Corner cornerB_Lr_gc41 = new Corner(true);
        Corner cornerB_Ll_gc41 = new Corner(true);
        Face front_gc41 = new Face(corner_Ur_gc41,corner_Ul_gc41,corner_Lr_gc41,corner_Ll_gc41);
        Face back_gc41 = new Face(cornerB_Ur_gc41,cornerB_Ul_gc41,cornerB_Lr_gc41,cornerB_Ll_gc41,ResourceType.MUSHROOM);
//        GoldCard gold_card_41 = new GoldCard(Color.RED,front_gc41,back_gc41,0);

        //green gold cards
        //green card 51

        //blue gold cards
        //blue card 61


        //purple gold cards
        //blue card 71


        //starting cards
        //starting card 81
        Corner corner_Ur_sc81 = new Corner(ResourceType.LEAF);
        Corner corner_Lr_sc81 = new Corner(true);
        Corner corner_Ul_sc81 = new Corner(true);
        Corner corner_Ll_sc81 = new Corner(ResourceType.INSECT);
        Corner cornerB_Ur_sc81 = new Corner(ResourceType.LEAF);
        Corner cornerB_Lr_sc81 = new Corner(ResourceType.ANIMAL);
        Corner cornerB_Ul_sc81 = new Corner(ResourceType.MUSHROOM);
        Corner cornerB_Ll_sc81 = new Corner(ResourceType.INSECT);
        Face front_sc81 = new Face(corner_Ur_sc81,corner_Ul_sc81,corner_Lr_sc81,corner_Ll_sc81, ResourceType.INSECT);
        Face back_sc81 = new Face(cornerB_Ur_sc81,cornerB_Ul_sc81,cornerB_Lr_sc81,cornerB_Ll_sc81);
        InitialCard initial_card_81 = new InitialCard(front_sc81,back_sc81);
        

        //starting card 82
        Corner corner_Ur_sc82 = new Corner(true);
        Corner corner_Lr_sc82 = new Corner(ResourceType.MUSHROOM);
        Corner corner_Ul_sc82 = new Corner(ResourceType.ANIMAL);
        Corner corner_Ll_sc82 = new Corner(true);
        Corner cornerB_Ur_sc82 = new Corner(ResourceType.ANIMAL);
        Corner cornerB_Lr_sc82 = new Corner(ResourceType.INSECT);
        Corner cornerB_Ul_sc82 = new Corner(ResourceType.LEAF);
        Corner cornerB_Ll_sc82 = new Corner(ResourceType.MUSHROOM);
        Face front_sc82 = new Face(corner_Ur_sc82,corner_Ul_sc82,corner_Lr_sc82,corner_Ll_sc82, ResourceType.MUSHROOM);
        Face back_sc82 = new Face(cornerB_Ur_sc82,cornerB_Ul_sc82,cornerB_Lr_sc82,cornerB_Ll_sc82);
        InitialCard initial_card_82 = new InitialCard(front_sc82,back_sc82);

        //starting card 83
        Corner corner_Ur_sc83 = new Corner(true);
        Corner corner_Lr_sc83 = new Corner(true);
        Corner corner_Ul_sc83 = new Corner(true);
        Corner corner_Ll_sc83 = new Corner(true);
        Corner cornerB_Ur_sc83 = new Corner(ResourceType.ANIMAL);
        Corner cornerB_Lr_sc83 = new Corner(ResourceType.LEAF);
        Corner cornerB_Ul_sc83 = new Corner(ResourceType.INSECT);
        Corner cornerB_Ll_sc83 = new Corner(ResourceType.MUSHROOM);
        Face front_sc83 = new Face(corner_Ur_sc83,corner_Ul_sc83,corner_Lr_sc83,corner_Ll_sc83, ResourceType.MUSHROOM, ResourceType.LEAF);
        Face back_sc83 = new Face(cornerB_Ur_sc83,cornerB_Ul_sc83,cornerB_Lr_sc83,cornerB_Ll_sc83);
        InitialCard initial_card_83 = new InitialCard(front_sc83,back_sc83);

        //starting card 84
        Corner corner_Ur_sc84 = new Corner(true);
        Corner corner_Lr_sc84 = new Corner(true);
        Corner corner_Ul_sc84 = new Corner(true);
        Corner corner_Ll_sc84 = new Corner(true);
        Corner cornerB_Ur_sc84 = new Corner(ResourceType.INSECT);
        Corner cornerB_Lr_sc84 = new Corner(ResourceType.MUSHROOM);
        Corner cornerB_Ul_sc84 = new Corner(ResourceType.LEAF);
        Corner cornerB_Ll_sc84 = new Corner(ResourceType.ANIMAL);
        Face front_sc84 = new Face(corner_Ur_sc84,corner_Ul_sc84,corner_Lr_sc84,corner_Ll_sc84, ResourceType.ANIMAL, ResourceType.INSECT);
        Face back_sc84 = new Face(cornerB_Ur_sc84,cornerB_Ul_sc84,cornerB_Lr_sc84,cornerB_Ll_sc84);
        InitialCard initial_card_84 = new InitialCard(front_sc84,back_sc84);

        //starting card 85
        Corner corner_Ur_sc85 = new Corner(true);
        Corner corner_Lr_sc85 = new Corner(false);
        Corner corner_Ul_sc85 = new Corner(true);
        Corner corner_Ll_sc85 = new Corner(false);
        Corner cornerB_Ur_sc85 = new Corner(ResourceType.MUSHROOM);
        Corner cornerB_Lr_sc85 = new Corner(ResourceType.ANIMAL);
        Corner cornerB_Ul_sc85 = new Corner(ResourceType.INSECT);
        Corner cornerB_Ll_sc85 = new Corner(ResourceType.LEAF);
        Face front_sc85 = new Face(corner_Ur_sc85,corner_Ul_sc85,corner_Lr_sc85,corner_Ll_sc85, ResourceType.ANIMAL, ResourceType.INSECT, ResourceType.LEAF);
        Face back_sc85 = new Face(cornerB_Ur_sc85,cornerB_Ul_sc85,cornerB_Lr_sc85,cornerB_Ll_sc85);
        InitialCard initial_card_85 = new InitialCard(front_sc85,back_sc85);

        //starting card 86
        Corner corner_Ur_sc86 = new Corner(true);
        Corner corner_Lr_sc86 = new Corner(false);
        Corner corner_Ul_sc86 = new Corner(true);
        Corner corner_Ll_sc86 = new Corner(false);
        Corner cornerB_Ur_sc86 = new Corner(ResourceType.ANIMAL);
        Corner cornerB_Lr_sc86 = new Corner(ResourceType.INSECT);
        Corner cornerB_Ul_sc86 = new Corner(ResourceType.MUSHROOM);
        Corner cornerB_Ll_sc86 = new Corner(ResourceType.LEAF);
        Face front_sc86 = new Face(corner_Ur_sc86,corner_Ul_sc86,corner_Lr_sc86,corner_Ll_sc86, ResourceType.ANIMAL, ResourceType.MUSHROOM, ResourceType.LEAF);
        Face back_sc86 = new Face(cornerB_Ur_sc86,cornerB_Ul_sc86,cornerB_Lr_sc86,cornerB_Ll_sc86);
        InitialCard initial_card_86 = new InitialCard(front_sc86,back_sc86);






    }
}
