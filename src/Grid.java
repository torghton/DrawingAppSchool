import AlexsGameEnhancers.Clickable;
import AlexsGameEnhancers.Drawable;
import AlexsGameEnhancers.Vector;

import java.awt.Graphics;
import java.awt.Color;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Grid implements Drawable, Clickable {

    private ArrayList<ArrayList<Tile>> tiles;

    private GridConstraints gridConstraints;

    private int tileSize;

    public Grid() {
        setGridConstraints(new GridConstraints(20, 20));
        setTileSize(10);

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

    public void setGridConstraints(GridConstraints gridConstraints) {
        this.gridConstraints = gridConstraints;
        setUpTiles(gridConstraints);
    }

    public void setTileSize(int tileSize) {
        this.tileSize = tileSize;
    }

    public void setTileColor(GridConstraints gridConstraints, Color color) {
        Tile tileSelected = tiles.get(gridConstraints.getRows()).get(gridConstraints.getColumns());
        tileSelected.setColor(color);
    }

    @Override
    public void drawSelf(Graphics g) {
        for(int row = 0; row < gridConstraints.getRows(); row++) {
            for(int column = 0; column < gridConstraints.getColumns(); column++) {
                Tile currentTile = tiles.get(row).get(column);

                g.setColor(currentTile.getColor());
                g.fillRect(column*tileSize,row*tileSize, tileSize, tileSize);

                g.setColor(Color.BLACK);
                g.drawRect(column*tileSize,row*tileSize, tileSize, tileSize);
            }
        }
    }

    @Override
    public void mouseClicked(Vector position, int mouseButton) {

    }

    @Override
    public void mousePressed(Vector position, int mouseButton) {
        setTileColor(positionToGridConstraints(position), new Color(0, 0, 0));
    }

    @Override
    public void mouseReleased(Vector position, int mouseButton) {

    }

    private GridConstraints positionToGridConstraints(Vector position) {
        int rowNum = (int) position.getYDirection()/tileSize;
        int colNum = (int) position.getXDirection()/tileSize;

        return new GridConstraints(rowNum, colNum);
    }
}
