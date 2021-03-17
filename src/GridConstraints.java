public class GridConstraints implements Comparable<GridConstraints> {

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

    @Override
    public int compareTo(GridConstraints o) {

        if(o.getRows() <= getRows()) {
            if(o.getColumns() <= getColumns()) {
                return 0;
            }
        }

        return -1;
    }
}
