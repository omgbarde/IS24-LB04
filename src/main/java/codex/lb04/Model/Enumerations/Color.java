package codex.lb04.Model.Enumerations;

import javafx.scene.paint.Paint;

/**
 * This enum contains all cards colours
 */
public enum Color {
    BLUE(javafx.scene.paint.Color.BLUE),
    GREEN(javafx.scene.paint.Color.GREEN),
    PURPLE(javafx.scene.paint.Color.PURPLE),
    RED(javafx.scene.paint.Color.RED),
    YELLOW(javafx.scene.paint.Color.YELLOW);

    private final Paint paint;

    Color(Paint paint) {
        this.paint = paint;
    }

    public Paint getPaint() {
        return this.paint;
    }
}
