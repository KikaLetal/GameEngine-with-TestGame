package Engine.Systems.Events;

import Engine.esc.Entity;

public class CollisionExitEvent extends Event{
    private Entity A;
    private Entity B;

    public CollisionExitEvent(Entity A, Entity B){
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
