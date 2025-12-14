package Engine.Input;

import java.awt.event.*;

public class InputHandler implements KeyListener, MouseListener, MouseMotionListener{

    private static final InputHandler instance = new InputHandler();

    private boolean[] keys = new boolean[256];
    private boolean[] keysLast = new boolean[256];
    private boolean[] buttons = new boolean[5];
    private boolean[] buttonsLast = new boolean[5];
    private int mouseX, mouseY;

    private InputHandler() {}

    public static InputHandler get() {
        return instance;
    }

    public void update(){
        System.arraycopy(keys, 0, keysLast, 0, keys.length);
        System.arraycopy(buttons, 0, buttonsLast, 0, buttons.length);
    }

    public boolean isKeyDown(int keyCode) {
        return keys[keyCode];
    }

    public boolean isKeyPressed(int keyCode) {
        return keys[keyCode] && !keysLast[keyCode];
    }
    public boolean isKeyReleased(int keyCode) {return !keys[keyCode] && keysLast[keyCode];}


    public boolean isButtonDown(int button) {
        return buttons[button];
    }

    public boolean isButtonPressed(int button) {
        return buttons[button] && !buttonsLast[button];
    }

    public int getMouseX() { return mouseX; }
    public int getMouseY() { return mouseY; }

    public void keyPressed(KeyEvent e) { keys[e.getKeyCode()] = true; }
    public void keyReleased(KeyEvent e) { keys[e.getKeyCode()] = false; }
    public void keyTyped(KeyEvent e) {}

    public void mousePressed(MouseEvent e) { buttons[e.getButton() - 1] = true; }
    public void mouseReleased(MouseEvent e) { buttons[e.getButton() - 1] = false; }
    public void mouseClicked(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}

    public void mouseDragged(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    public boolean[] getButtons() {
        return buttons;
    }
    public boolean[] getButtonsLast() {
        return buttonsLast;
    }
    public boolean[] getKeys() {
        return keys;
    }
    public boolean[] getKeysLast() {
        return keysLast;
    }
}
