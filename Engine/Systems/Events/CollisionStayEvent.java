package Engine.Systems.Events;

import Engine.esc.Entity;

public class CollisionStayEvent extends Event{
    private Entity A;
    private Entity B;

    public CollisionStayEvent(Entity A, Entity B){
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
