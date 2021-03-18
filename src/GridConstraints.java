import java.io.Serializable;

public class GridConstraints implements Comparable<GridConstraints>, Serializable {

    public int rows;

    public int columns;

    public GridConstraints(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }


    /**
     * Compares another grid in size
     *
     *
     * @param o the grid to compare to
     * @return returns 0 if the compared grid is smaller in both fields, if not it returns -1
     */
    @Override
    public int compareTo(GridConstraints o) {

        if(o.getRows() < getRows() && o.getRows() >= 0) {
            if(o.getColumns() < getColumns() && o.getColumns() >= 0) {
                return 0;
            }
        }

        return -1;
    }

    public static GridConstraints add(GridConstraints gridConstraints1, GridConstraints gridConstraints2) {
        int x1 = gridConstraints1.getRows();
        int y1 = gridConstraints1.getColumns();

        int x2 = gridConstraints2.getRows();
        int y2 = gridConstraints2.getColumns();

        return new GridConstraints(x1 + x2, y1 + y2);
    }
}
