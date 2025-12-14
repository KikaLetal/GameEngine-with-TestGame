package Engine.Systems.Events;

import Engine.esc.Entity;

public class CollisionEnterEvent extends Event{
    private Entity A;
    private Entity B;

    public CollisionEnterEvent(Entity A, Entity B){
        this.A = A;
        this.B = B;
    }

    public Entity getA() {
        return A;
    }

    public Entity getB() {
        return B;
    }
}
