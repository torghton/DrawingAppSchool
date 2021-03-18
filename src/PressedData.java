import java.io.Serializable;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class PressedData implements Serializable {

    private List<Integer> keysPressed;

    private boolean singlePressMax;

    public PressedData() {
        keysPressed = new CopyOnWriteArrayList<>();
    }

    public void setSinglePressMax(boolean singlePressMax) {
        this.singlePressMax = singlePressMax;
    }

    public void press(Integer keyCode) {
        if(singlePressMax) {
            keysPressed.clear();
        }
        keysPressed.add(keyCode);
    }

    public void release(Integer keyCode) {
        keysPressed.remove(keyCode);
    }

    public boolean isPressed(Integer keyCode) {
        for(Integer integer: keysPressed) {
            if(integer == keyCode) {
                return true;
            }
        }

        return false;
    }
}
