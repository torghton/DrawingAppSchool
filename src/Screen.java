import AlexsGameEnhancers.*;

import javax.swing.*;

import java.awt.*;

import java.awt.event.*;


public class Screen extends JPanel implements KeyListener, MouseListener, MouseMotionListener {

    private final Dimension SCREENSIZE;

    private Updater updater;

    private DrawableManager drawableManager;

    private ClickManager clickManager;

    private KeyInteractor keyInteractor;

    private MouseMotionManager mouseMotionManager;

    private Grid grid;
    private SideBar sideBar;
    private MenuBar menuBar;

    public Screen(Dimension SCREENSIZE, JFrame jframe) {
        this.SCREENSIZE = SCREENSIZE;
        setLayout(null);

        setUpJPanel();
        setBackground(Color.WHITE);
        initializeManagers();

        setupGrid();
        setupSideBar();
        setupMenuBar(jframe);

        Pen pen = new Pen();
        pen.setSize(1);
        grid.setPaintBrush(pen);
    }

    private void setupMenuBar(JFrame jframe) {
        menuBar = new MenuBar();
        menuBar.setUpMenus(jframe, grid);
        menuBar.addMenus(this);
        menuBar.setLoadedListener(new LoadedListener() {
            @Override
            public void loaded(Grid savedGrid) {
                removeManagers(grid);
                grid = null;

                grid = savedGrid;
                addManagers(grid);
            }

            @Override
            public void cleared() {
                grid.clear();
            }
        });
        addManagers(menuBar, 3);
    }

    private void setupGrid() {
        grid = new Grid();
        grid.setTileSize(35);
        grid.setGridPosition(new Vector(50, 200));
        grid.setGridConstraints(new GridConstraints(12, 12));
        addManagers(grid, 0);
    }

    private void setupSideBar() {
        sideBar = new SideBar();
        sideBar.setLocation(new Vector(550, 0));
        sideBar.setChangedEvent(new ChangedEvent() {
            @Override
            public void colorButtonPressedChanged(SideBar.ColorButton colorButton) {
                grid.setPaintBrushColor(colorButton.getColor());
            }

            @Override
            public void buttonPressed(Color color) {
                grid.setPaintBrushColor(color);
            }
        });
        addManagers(sideBar, 2);
    }

    private void initializeManagers() {
        updater = new Updater();
        drawableManager = new DrawableManager(5);
        clickManager = new ClickManager();
        keyInteractor = new KeyInteractor();
        mouseMotionManager = new MouseMotionManager();
    }

    private void setUpJPanel() {
        setFocusable(true);
        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
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

        if(obj instanceof MouseMotionable) {
            mouseMotionManager.addMouseMotionable((MouseMotionable) obj);
        }
    }

    private <T>void addManagers(T obj) {
        addManagers(obj, 0);
    }

    private <T> void removeManagers(T obj) {
        if(obj instanceof Updateable) {
            updater.removeUpdateable((Updateable) obj);
        }

        if(obj instanceof Drawable) {
            drawableManager.removeDrawable((Drawable) obj);
        }

        if(obj instanceof Clickable) {
            clickManager.removeClickable((Clickable) obj);
        }

        if(obj instanceof MouseMotionable) {
            mouseMotionManager.removeMouseMotionable((MouseMotionable) obj);
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

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseMotionManager.mouseMoved(new Vector(e.getX(), e.getY()));
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}