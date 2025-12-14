package Engine.Systems.Events;

public class KeyReleasedEvent extends InputEvent{
    private final int key;
    public KeyReleasedEvent(int key) { this.key = key; }

    public int getKey() {
        return key;
    }
}
