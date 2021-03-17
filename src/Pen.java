import java.util.Arrays;

public class Pen extends PaintBrush {

    public Pen() {}

    @Override
    int[][] paint(GridConstraints gridPosition) {
        int[][] points = new int[size][size];

        for(int[] row: points) {
            Arrays.fill(row, 1);
        }

        return points;
    }
}
