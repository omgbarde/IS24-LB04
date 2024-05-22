package codex.lb04.View;

import codex.lb04.Model.Corner;
import codex.lb04.Model.Enumerations.Color;
import codex.lb04.Model.Enumerations.ResourceType;
import codex.lb04.Model.Face;
import codex.lb04.Model.GoldCard;
import codex.lb04.Model.ResourceCard;
import codex.lb04.View.Cli.CliBoardModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CliBoardModelTest {
    CliBoardModel model;
    @Before
    public void setUp() {
        model = new CliBoardModel();
    }

    @After
    public void tearDown(){
        model = null;
    }

    @Test
    public void testPrintGrid(){
        model.printGridMap();
    }

    @Test
    public void testPlacecard(){

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
        model.placeCard(0,0,resource_card_1);
        model.printGridMap();


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
        gold_card_43.setShowingFront(true);

        model.placeCard(1,1,gold_card_43);
        model.printGridMap();

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
        resource_card_2.setShowingFront(true);

        model.placeCard(-1,1,resource_card_2);
        model.printGridMap();
    }

}
