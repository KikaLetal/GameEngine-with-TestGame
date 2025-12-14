package purpurafingunt;

import Engine.Components.ScriptComponent;
import Engine.Components.RigidBody;
import Engine.Math.Vector2f;
import Engine.Systems.Events.Event;
import Engine.Systems.Events.*;
import Engine.esc.EntityManager;

import java.awt.event.KeyEvent;

public class PlayerControler extends ScriptComponent {
    private RigidBody rb;
    private float speed = 50f;
    private float jumpForce = -100f;

    @Override
    public void Start() {
        rb = entity.getComponent(RigidBody.class);
    }

    @Override
    public void update(float deltaTime){
        System.out.println(entity.getComponent(RigidBody.class).isOnGround());
    }

    @Override
    public void HandleEvent(Event e) {

        if (e instanceof KeyPressedEvent k) {

            switch (k.getKey()) {
                case KeyEvent.VK_A -> rb.setVelocity(
                        new Vector2f(-speed, rb.getVelocity().y)
                );
                case KeyEvent.VK_D -> rb.setVelocity(
                        new Vector2f(speed, rb.getVelocity().y)
                );
                case KeyEvent.VK_SPACE -> {
                    if (rb.isOnGround()) {
                        rb.setVelocity(
                                new Vector2f(rb.getVelocity().x, jumpForce)
                        );
                    }
                }
            }
        }

        if (e instanceof KeyReleasedEvent k) {

            if (k.getKey() == KeyEvent.VK_A ||
                k.getKey() == KeyEvent.VK_D) {

                rb.setVelocity(
                        new Vector2f(0, rb.getVelocity().y)
                );
            }
        }
    }
}

