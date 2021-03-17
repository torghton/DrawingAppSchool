import AlexsGameEnhancers.*;

import javax.swing.JPanel;

import java.awt.*;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class Screen extends JPanel implements KeyListener, MouseListener {

    private final Dimension SCREENSIZE;

    private ImageLoader imageLoader;

    private Updater updater;

    private DrawableManager drawableManager;

    private ClickManager clickManager;

    private KeyInteractor keyInteractor;

    private Grid grid;

    public Screen(Dimension SCREENSIZE) {
        this.SCREENSIZE = SCREENSIZE;

        setUpJPanel();
        setBackground(Color.WHITE);
        initializeManagers();

        setUpGrid();

        Pen pen = new Pen();
        pen.setSize(5);
        grid.setPaintBrush(pen);
    }

    private void setUpGrid() {
        grid = new Grid();
        grid.setTileSize(20);
        grid.setGridConstraints(new GridConstraints(20, 10));
        addManagers(grid, 0);
    }

    private void initializeManagers() {
        updater = new Updater();
        drawableManager = new DrawableManager(5);
        clickManager = new ClickManager();
        keyInteractor = new KeyInteractor();
    }

    private void setUpJPanel() {
        setFocusable(true);
        addKeyListener(this);
        addMouseListener(this);
    }

    private <T>void addManagers(T obj, int drawableLayer) {
        if(obj instanceof Updateable) {
            updater.addUpdateable((Updateable) obj);
        }

        if(obj instanceof Drawable) {
            drawableManager.addDrawable((Drawable) obj, drawableLayer);
        }

        if(obj instanceof KeyInteractable) {
            keyInteractor.addKeyInteractable((KeyInteractable) obj);
        }

        if(obj instanceof Clickable) {
            clickManager.addClickable((Clickable) obj);
        }
    }

    private <T>void addManagers(T obj) {
        if(obj instanceof Updateable) {
            updater.addUpdateable((Updateable) obj);
        }

        if(obj instanceof Drawable) {
            drawableManager.addDrawable((Drawable) obj, 0);
        }

        if(obj instanceof KeyInteractable) {
            keyInteractor.addKeyInteractable((KeyInteractable) obj);
        }

        if(obj instanceof Clickable) {
            clickManager.addClickable((Clickable) obj);
        }
    }


    @Override
    public Dimension getPreferredSize() {
        return new Dimension(SCREENSIZE);
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawableManager.drawAll(g);

        repaint();
    }

    public void gameLoop(int delay) {
        while(true) {
            try {
                Thread.sleep(delay);
            } catch(Exception e) {
                e.printStackTrace();
            }

            updater.updateAll();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        keyInteractor.keyPressed(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keyInteractor.keyReleased(e.getKeyCode());
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        clickManager.clickAll(new Vector(e.getX(), e.getY()), e.getButton());
    }

    @Override
    public void mousePressed(MouseEvent e) {
        clickManager.pressAll(new Vector(e.getX(), e.getY()), e.getButton());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        clickManager.releasesAll(new Vector(e.getX(), e.getY()), e.getButton());
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}