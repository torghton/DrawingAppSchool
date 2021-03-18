package AlexsGameEnhancers;

import java.util.List;
import java.util.ArrayList;

public class MouseMotionManager {
    private List<MouseMotionable> mouseMotions;

    public MouseMotionManager() {
        mouseMotions = new ArrayList<>();
    }

    public void addMouseMotionable(MouseMotionable mouseMotionable) {
        mouseMotions.add(mouseMotionable);
    }


    public void mouseMoved(Vector location) {
        for(MouseMotionable mouseMotionable: mouseMotions) {
            mouseMotionable.mouseMoved(location);
        }
    }

    public void removeMouseMotionable(MouseMotionable mouseMotionable) {
        mouseMotions.remove(mouseMotionable);
    }
}