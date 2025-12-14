package Engine.Components;

import Engine.Math.Vector2f;
import Engine.esc.Component;

public class RigidBody extends Component {
    Vector2f velocity;
    float mass;
    boolean Iskinematic;
    float gravity;
    boolean onGround;

    public RigidBody(){
        this.velocity = new Vector2f(0, 0);
        this.mass = 1;
        Iskinematic = false;
        gravity = 1;
        onGround= false;
    }

    public RigidBody(float mass){
        this.velocity = new Vector2f(0, 0);
        this.mass = mass;
        Iskinematic = false;
        gravity = 1;
        onGround= false;
    }

    public RigidBody(float mass, float gravity){
        this.velocity = new Vector2f(0, 0);
        this.mass = mass;
        Iskinematic = false;
        this.gravity = gravity;
        onGround= false;
    }

    public RigidBody(float mass, boolean Iskinematic, float gravity){
        this.velocity = new Vector2f(0, 0);   
        this.mass = mass;
        this.Iskinematic = Iskinematic;
        this.gravity = gravity;
        onGround= false;
    }

    public RigidBody(float mass, boolean Iskinematic){
        this.velocity = new Vector2f(0, 0);
        this.mass = mass;
        this.Iskinematic = Iskinematic;
        this.gravity = 1;
        onGround= false;
    }

    public RigidBody(boolean Iskinematic){
        this.velocity = new Vector2f(0, 0);
        this.mass = 1;
        this.Iskinematic = Iskinematic;
        gravity = 1;
        onGround= false;
    }

    public void setIskinematic(boolean iskinematic) {
        Iskinematic = iskinematic;
    }

    public void setMass(float mass) {
        this.mass = mass;
    }

    public void setVelocity(Vector2f velocity) {
        this.velocity = velocity;
    }

    public void setGravity(float gravity) {
        this.gravity = gravity;
    }

    public void setKinematic(boolean Iskinematic){ this.Iskinematic = Iskinematic;}

    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }

    public boolean getIskinematic() {
        return Iskinematic;
    }

    public float getMass() {
        return mass;
    }

    public Vector2f getVelocity() {
        return velocity;
    }

    public float getGravity() {
        return gravity;
    }

    public boolean isOnGround() {
        return onGround;
    }
}
