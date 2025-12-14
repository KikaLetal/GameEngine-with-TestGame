package Engine.esc.builders;

import Engine.Systems.*;
import Engine.Systems.EventSystem;
import Engine.esc.Scene;

public class SceneBuilder {
    public static Scene createScene(){
        Scene scene = new Scene();
        scene.addSystem(new PhysicsSystem());
        scene.addSystem(ScriptSystem.get());
        scene.addSystem(new RenderSystem());
        scene.addSystem(EventSystem.get());
        scene.addSystem(new InputSystem());
        return scene;
    }
}
