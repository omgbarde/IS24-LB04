package codex.lb04.View;

import codex.lb04.Model.Corner;
import codex.lb04.Model.Enumerations.Color;
import codex.lb04.Model.Enumerations.ResourceType;
import codex.lb04.Model.Face;
import codex.lb04.Model.GoldCard;
import codex.lb04.View.Cli.CardRenderer;
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

        String[][][] map = model.getGridMap();
        map[10][10] = CardRenderer.renderIngame(gold_card_43);
        model.setGridMap(map);
        model.printGridMap();
    }

}
