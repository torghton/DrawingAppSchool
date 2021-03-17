import java.awt.Color;

public class Tile {

    Color color;

    public Tile() {

        // default color
        color = new Color(255, 255,255);
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
