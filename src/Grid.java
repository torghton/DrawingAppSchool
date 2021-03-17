import AlexsGameEnhancers.Clickable;
import AlexsGameEnhancers.Drawable;
import AlexsGameEnhancers.Vector;

import java.awt.Graphics;
import java.awt.Color;

import java.util.ArrayList;

public class Grid implements Drawable, Clickable {

    private ArrayList<ArrayList<Tile>> tiles;

    private GridConstraints gridConstraints;

    private PaintBrush paintBrush;

    private PaintBrush eraser;

    private Vector gridPosition;

    private int tileSize;

    public Grid() {
        setGridConstraints(new GridConstraints(20, 20));
        setTileSize(10);
        setPaintBrush(new Pen());
        setGridPosition(new Vector(100, 100));

        setUpTiles(gridConstraints);
    }

    private void setUpTiles(GridConstraints gridConstraints) {
        tiles = new ArrayList<>();

        for(int row = 0; row < gridConstraints.getRows(); row++) {
            tiles.add(new ArrayList<>());

            for(int column = 0; column < gridConstraints.getColumns(); column++) {
                Tile tile = new Tile();
                tile.setColor(new Color(100, 100, 100));

                tiles.get(row).add(tile);
            }
        }
    }

    public void setGridPosition(Vector gridPosition) {
        this.gridPosition = gridPosition;
    }

    public void setGridConstraints(GridConstraints gridConstraints) {
        this.gridConstraints = gridConstraints;
        setUpTiles(gridConstraints);
    }

    public void setTileSize(int tileSize) {
        this.tileSize = tileSize;
    }

    public void setTileColor(GridConstraints gridPosition, Color color) {
        if(gridConstraints.compareTo(gridPosition) == 0) {
            Tile tileSelected = tiles.get(gridPosition.getRows()).get(gridPosition.getColumns());
            tileSelected.setColor(color);
        }
    }

    public void setPaintBrush(PaintBrush paintBrush) {
        this.paintBrush = paintBrush;
    }

    @Override
    public void drawSelf(Graphics g) {

        int xPosition = (int) gridPosition.getXDirection();
        int yPosition = (int) gridPosition.getYDirection();

        for(int row = 0; row < gridConstraints.getRows(); row++) {
            for(int column = 0; column < gridConstraints.getColumns(); column++) {
                Tile currentTile = tiles.get(row).get(column);

                g.setColor(currentTile.getColor());
                g.fillRect(xPosition + column*tileSize,yPosition + row*tileSize, tileSize, tileSize);

                g.setColor(Color.BLACK);
                g.drawRect(xPosition + column*tileSize,yPosition + row*tileSize, tileSize, tileSize);
            }
        }
    }

    @Override
    public void mouseClicked(Vector position, int mouseButton) {

    }

    @Override
    public void mousePressed(Vector position, int mouseButton) {
        GridConstraints gridClickPosition = positionToGridConstraints(position);

        if(mouseButton == 1)
            paint(gridClickPosition, paintBrush, Color.BLACK);

        if(mouseButton == 3)
            paint(gridClickPosition, paintBrush, Color.WHITE);

    }

    @Override
    public void mouseReleased(Vector position, int mouseButton) {

    }

    private void paint(GridConstraints mouseClickPosition, PaintBrush currentPaintBrush, Color color) {
        int[][] paintPattern = paintBrush.paint(mouseClickPosition);

        for(int row = 0; row < paintPattern.length; row++) {
            for(int column = 0; column < paintPattern[row].length; column++) {
                if(paintPattern[row][column] == 1) {
                    GridConstraints currentIterationGridConstraints= new GridConstraints(row, column);
                    setTileColor(GridConstraints.add(mouseClickPosition, currentIterationGridConstraints), color);
                }
            }
        }
    }

    private GridConstraints positionToGridConstraints(Vector position) {
        int rowNum = (int) (position.getYDirection() - gridPosition.getYDirection())/tileSize;
        int colNum = (int) (position.getXDirection() - gridPosition.getXDirection())/tileSize;

        return new GridConstraints(rowNum, colNum);
    }
}
