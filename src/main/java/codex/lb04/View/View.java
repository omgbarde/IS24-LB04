package codex.lb04.View;

import java.util.ArrayList;

/**
 * Interface for the view
 */
public abstract class View {
    /**
     * abstract method to switch the scene to the one specified by the name
     *
     * @param sceneName is the name of the scene to load
     */
    public abstract void switchScene(String sceneName);

    public abstract void setTitle(String title);

    public abstract void setMode(String mode);

    public abstract void updateList(ArrayList<String> names);

    public abstract void updateListLater(ArrayList<String> names);
}
