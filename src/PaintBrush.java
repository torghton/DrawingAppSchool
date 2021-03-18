import java.io.Serializable;

public abstract class PaintBrush implements Serializable {

    protected int size = 1;

    abstract int[][] paint();

    public void setSize(int size) {
        this.size = size;
    }
}
