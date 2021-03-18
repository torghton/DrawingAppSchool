import AlexsGameEnhancers.Clickable;
import AlexsGameEnhancers.Drawable;
import AlexsGameEnhancers.Vector;

import java.awt.*;

interface ChangedEvent {

    void colorButtonPressedChanged(SideBar.ColorButton colorButton);

    void buttonPressed(Color color);
}

public class SideBar implements Drawable, Clickable {

    private Vector location;
    private Color sideBarColor = new Color(66, 164, 245);

    private ChangedEvent changedEvent;

    private ColorButton[] colorButtons;
    private PaintButton[] paintButtons;

    public SideBar() {
        this.location = new Vector(100, 100);

        changedEvent = new ChangedEvent() {
            @Override
            public void colorButtonPressedChanged(ColorButton colorButton) {

            }

            @Override
            public void buttonPressed(Color color) {

            }
        };

        colorButtons = new ColorButton[6];

        colorButtons[0] = new ColorButton(new Color(0, 0, 0));
        colorButtons[0].setLocation(new Vector(570, 600));

        colorButtons[1] = new ColorButton(new Color(255, 0, 0));
        colorButtons[1].setLocation(new Vector(645, 600));

        colorButtons[2] = new ColorButton(new Color(0, 255, 0));
        colorButtons[2].setLocation(new Vector(720, 600));

        colorButtons[3] = new ColorButton(new Color(0, 0, 255));
        colorButtons[3].setLocation(new Vector(570, 670));

        colorButtons[4] = new ColorButton(new Color(255, 255, 255));
        colorButtons[4].setLocation(new Vector(645, 670));

        colorButtons[5] = new ColorButton(new Color(123, 0, 255));
        colorButtons[5].setLocation(new Vector(720, 670));

        paintButtons = new PaintButton[2];

        paintButtons[0] = new PaintButton("Pen", Color.BLACK);
        paintButtons[0].setLocation(new Vector(610, 400));
        paintButtons[0].setSize(new Dimension(100, 50));

        paintButtons[1] = new PaintButton("Eraser", Color.WHITE);
        paintButtons[1].setLocation(new Vector(590, 250));
        paintButtons[1].setSize(new Dimension(160, 50));


    }

    public void setLocation(Vector location) {
        this.location = location;
    }

    public void setChangedEvent(ChangedEvent changedEvent) {
        this.changedEvent = changedEvent;
    }

    @Override
    public void drawSelf(Graphics g) {
        g.setColor(sideBarColor);
        g.fillRect((int) location.getXDirection(), (int) location.getYDirection(), 250, 800);

        for(ColorButton colorButton: colorButtons) {
            colorButton.drawSelf(g);
        }

        for(PaintButton paintButton: paintButtons) {
            paintButton.drawSelf(g);
        }
    }

    @Override
    public void mouseClicked(Vector position, int mouseButton) {

    }

    @Override
    public void mousePressed(Vector position, int mouseButton) {

    }

    @Override
    public void mouseReleased(Vector position, int mouseButton) {
        for(ColorButton colorButton: colorButtons) {
            colorButton.mouseReleased(position, mouseButton);
        }

        for(PaintButton paintButton: paintButtons) {
            paintButton.mouseReleased(position, mouseButton);
        }
    }


    public abstract class Button implements Clickable, Drawable {

        protected Vector location;
        protected Dimension size;

        public Button() {
            location = new Vector(100, 100);
            size = new Dimension(50, 50);
        }

        public void setLocation(Vector location) {
            this.location = location;
        }

        public void setSize(Dimension size) {
            this.size = size;
        }

        protected boolean isPointInBounds(Vector testLocation) {
            if(testLocation.getXDirection() > location.getXDirection() && testLocation.getXDirection() < location.getXDirection() + size.getWidth()) {
                if(testLocation.getYDirection() > location.getYDirection() && testLocation.getYDirection() < location.getYDirection() + size.getHeight()) {
                    return true;
                }
            }

            return false;
        }
    }

    public class ColorButton extends Button {

        private Color color;


        public ColorButton(Color color) {
            super();

            this.color = color;
        }

        public Color getColor() {
            return color;
        }

        @Override
        public void mouseClicked(Vector position, int mouseButton) {

        }

        @Override
        public void mousePressed(Vector position, int mouseButton) {

        }

        @Override
        public void mouseReleased(Vector position, int mouseButton) {
            if(isPointInBounds(position)) {
                changedEvent.colorButtonPressedChanged(this);
            }
        }

        @Override
        public void drawSelf(Graphics g) {
            g.setColor(getColor());
            g.fillRect((int) location.getXDirection(), (int) location.getYDirection(), (int) size.getWidth(), (int) size.getHeight());
        }
    }

    public class PaintButton extends Button {

        private Color color;
        private Font textFont;

        private Color colorToChangeTo;

        private String name;

        public PaintButton(String name, Color colorToChangeTo) {
            color = new Color(100, 100, 100);
            textFont = new Font("none", 1, 50);

            this.colorToChangeTo = colorToChangeTo;
            this.name = name;
        }

        @Override
        public void mouseClicked(Vector position, int mouseButton) {

        }

        @Override
        public void mousePressed(Vector position, int mouseButton) {

        }

        @Override
        public void mouseReleased(Vector position, int mouseButton) {
            if(isPointInBounds(position)) {
                changedEvent.buttonPressed(colorToChangeTo);
            }

        }

        @Override
        public void drawSelf(Graphics g) {
            g.setColor(color);
            g.fillRect((int) location.getXDirection(), (int) location.getYDirection(), (int) size.getWidth(), (int) size.getHeight());

            g.setColor(Color.BLACK);
            g.setFont(textFont);
            g.drawString(name, (int) location.getXDirection(), (int) location.getYDirection()+41);
        }
    }
}
