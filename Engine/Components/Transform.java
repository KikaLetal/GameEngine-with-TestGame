package Engine.Components;

import Engine.esc.Component;
import Engine.Math.Vector2f;

public class Transform extends Component {
    private Vector2f position;
    private Vector2f size;
    private float rotation;

    public Transform(Vector2f position){
        this.position = position;
        this.size = new Vector2f(1, 1);
        this.rotation = 0;
    }
    public Transform(Vector2f position, Vector2f scale){
        this.position = position;
        this.size = scale;
        this.rotation = 0;
    }

    public void setPosition(Vector2f position) {
        this.position = position;
    }

    public void Move(Vector2f position) {
        this.position.add(position);
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public void setSize(Vector2f size) {
        this.size = size;
    }

    public float getRotation() {
        return rotation;
    }

    public Vector2f getPosition() {
        return position;
    }

    public Vector2f getSize() {
        return size;
    }
}
