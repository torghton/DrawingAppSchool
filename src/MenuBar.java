import AlexsGameEnhancers.Drawable;
import AlexsGameEnhancers.Vector;

import javax.swing.*;
import java.awt.*;
import java.io.File;

interface LoadedListener {
    void loaded(Grid grid);
    void cleared();
}

public class MenuBar implements Drawable {

    private JComboBox<String> fileButton;

    private Vector location;
    private Dimension size;

    private LoadedListener loadedListener;

    public MenuBar() {
        location = new Vector(0, 0);
        size = new Dimension(800, 20);
        loadedListener = new LoadedListener() {
            @Override
            public void loaded(Grid grid) {

            }

            @Override
            public void cleared() {

            }
        };
    }

    public void setUpMenus(JFrame jframe, Grid grid) {
        String[] fileChoices = {"save", "load", "clear"};
        fileButton = new JComboBox<>(fileChoices);
        fileButton.setBounds((int) location.getXDirection(), (int) location.getYDirection(), 60, (int) size.getHeight());
        fileButton.addActionListener((e) -> {
                String selected = (String) fileButton.getSelectedItem();
                System.out.println(selected);
                if(selected != null) {
                    if(selected.equals("save")) {
                        File file = FileSaver.promptForSaveTextFile(jframe);
                        if (file != null) {
                            FileSaver.saveObjectToFile(file.getAbsolutePath(), grid);
                        }
                    } else if(selected.equals("load")) {
                        File file = FileSaver.promptForLoadTextFile(jframe);
                        if (file != null) {
                            Grid savedGrid = (Grid) (FileSaver.loadObjectFromFile(file.getAbsolutePath()));
                            System.out.println(savedGrid);
                            loadedListener.loaded(savedGrid);
                        }
                    } else if(selected.equals("clear")) {
                        loadedListener.cleared();
                    }
                }

                fileButton.setSelectedIndex(-1);
        });

        fileButton.setSelectedIndex(-1);
    }

    public void setLoadedListener(LoadedListener loadedListener) {
        this.loadedListener = loadedListener;
    }

    public void addMenus(JPanel jPanel) {
        jPanel.add(fileButton);
    }

    public void setLocation(Vector location) {
        this.location = location;
    }

    public void setSize(Dimension size) {
        this.size = size;
    }

    @Override
    public void drawSelf(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect((int) location.getXDirection(), (int) location.getYDirection(), (int) size.getWidth(), (int) size.getHeight());
    }
}
