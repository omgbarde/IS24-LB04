package codex.lb04.View;

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

}
