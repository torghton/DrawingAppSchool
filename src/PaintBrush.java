public abstract class PaintBrush {

    protected int size = 1;

    abstract int[][] paint(GridConstraints gridPosition);

    public void setSize(int size) {
        this.size = size;
    }
}
