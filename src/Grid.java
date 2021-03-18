import AlexsGameEnhancers.Clickable;
import AlexsGameEnhancers.Drawable;
import AlexsGameEnhancers.MouseMotionable;
import AlexsGameEnhancers.Vector;

import java.awt.Graphics;
import java.awt.Color;

import java.io.Serializable;
import java.util.ArrayList;

public class Grid implements Drawable, Clickable, MouseMotionable, Serializable {

    private static final long serialversionUID = 12129038120L;

    private Tile[][] tiles;

    private PaintBrush paintBrush;
    private PaintBrush eraser;

    private Color paintBrushColor;

    private GridConstraints gridConstraints;
    private Vector gridPosition;

    private PressedData mouseData;

    private int tileSize;

    public Grid() {
        setGridConstraints(new GridConstraints(20, 20));
        setTileSize(10);
        setPaintBrush(new Pen());
        eraser = new Pen();
        setGridPosition(new Vector(100, 100));
        setUpMouseValues();

        setUpTiles(gridConstraints);
    }

    private void setUpTiles(GridConstraints gridConstraints) {
        tiles = new Tile[gridConstraints.getRows()][gridConstraints.getColumns()];

        for(int row = 0; row < gridConstraints.getRows(); row++) {
            for(int column = 0; column < gridConstraints.getColumns(); column++) {
                Tile tile = new Tile();
                tile.setColor(new Color(255, 255, 255));

                tiles[row][column] = tile;
            }
        }
    }

    private void setUpMouseValues() {
        mouseData = new PressedData();
        mouseData.setSinglePressMax(true);
    }


    public void setGridPosition(Vector gridPosition) {
        this.gridPosition = gridPosition;
    }

    public void setGridConstraints(GridConstraints gridConstraints) {
        this.gridConstraints = gridConstraints;
        setUpTiles(gridConstraints);
    }

    public void setPaintBrushColor(Color paintBrushColor) {
        this.paintBrushColor = paintBrushColor;
    }

    public void setTileSize(int tileSize) {
        this.tileSize = tileSize;
    }

    public void setTileColor(GridConstraints gridPosition, Color color) {
        if(gridConstraints.compareTo(gridPosition) == 0) {
            Tile tileSelected = tiles[gridPosition.getRows()][gridPosition.getColumns()];
            tileSelected.setColor(color);
        }
    }

    public void setPaintBrush(PaintBrush paintBrush) {
        this.paintBrush = paintBrush;
    }

    public void clear() {
        for(Tile[] tileArr: tiles) {
            for(Tile tile: tileArr) {
                tile.setColor(Color.WHITE);
            }
        }
    }

    @Override
    public void drawSelf(Graphics g) {

        int xPosition = (int) gridPosition.getXDirection();
        int yPosition = (int) gridPosition.getYDirection();

        for(int row = 0; row < gridConstraints.getRows(); row++) {
            for(int column = 0; column < gridConstraints.getColumns(); column++) {
                Tile currentTile = tiles[row][column];

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

        if(mouseButton == 1) {
            paint(gridClickPosition, paintBrush, paintBrushColor);
            mouseData.press(1);
        }

        if(mouseButton == 3) {
            paint(gridClickPosition, eraser, Color.WHITE);
            mouseData.press(3);
        }


    }

    @Override
    public void mouseReleased(Vector position, int mouseButton) {
        if(mouseData.isPressed(1)) {
            mouseData.release(1);
        } else if(mouseData.isPressed(3)) {
            mouseData.release(3);
        }
    }

    @Override
    public void mouseMoved(Vector location) {
        if(mouseData.isPressed(1)) {
            paint(positionToGridConstraints(location), paintBrush, paintBrushColor);
        } else if(mouseData.isPressed(3)) {
            paint(positionToGridConstraints(location), eraser, Color.WHITE);
        }

    }

    private void paint(GridConstraints mouseClickPosition, PaintBrush currentPaintBrush, Color color) {
        int[][] paintPattern = paintBrush.paint();

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
