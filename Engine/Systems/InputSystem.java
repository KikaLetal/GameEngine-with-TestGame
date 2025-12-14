package Engine.Systems;

import Engine.Input.InputHandler;
import Engine.Systems.Events.KeyPressedEvent;
import Engine.Systems.Events.KeyReleasedEvent;
import Engine.esc.EntityManager;
import Engine.esc.System;

public class InputSystem extends System{
    InputHandler input = InputHandler.get();

    public InputSystem() {
        setPriority(-100);
    }

    @Override
    public void update(EntityManager em, float deltaTime) {
        for(int key = 0; key < 256; key++){
            if (input.isKeyPressed(key)) {
                EventSystem.get().emit(new KeyPressedEvent(key));
            }

            if (input.isKeyReleased(key)) {
                EventSystem.get().emit(new KeyReleasedEvent(key));
            }
        }

        input.update();
    }

    @Override
    public void debugUpdate(EntityManager em, float deltaTime) {
        update(em, deltaTime);
    }
}
