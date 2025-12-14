package Engine.Systems.Events;

public class KeyPressedEvent extends InputEvent{
    private final int key;
    public KeyPressedEvent(int key) { this.key = key; }

    public int getKey() {
        return key;
    }
}
