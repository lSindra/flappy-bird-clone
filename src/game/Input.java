package game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Input implements KeyListener {


    private boolean spacePressed = false;
    private boolean spaceReleased = true;

    public boolean isSpacePressed() {
        boolean space = spacePressed;
        spacePressed = false;
        return space;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE && spaceReleased) {
            spacePressed = true;
            spaceReleased = false;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            spaceReleased = true;
        }
    }
}
