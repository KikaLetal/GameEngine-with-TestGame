package Engine.Systems;

import Engine.Rendering.RenderCommand;
import Engine.Rendering.Renderer;
import Engine.esc.*;
import Engine.esc.System;
import Engine.Components.*;
import Engine.esc.Entity;

import java.util.List;

public class RenderSystem extends System {
    public RenderSystem(){
        setPriority(1000);
    }

    @Override
    public void update(EntityManager entityManager, float deltaTime){
        Renderer.get().clearRenderQueue();
        List<Entity> entities = entityManager.getEntitiesWithComponents(Sprite.class, Transform.class);

        for(Entity e : entities){
            RenderCommand renCom = new RenderCommand();

            renCom.setSprite(e.getComponent(Sprite.class));
            renCom.setLayer(e.getComponent(Sprite.class).getLayer());
            renCom.setTransform(e.getComponent(Transform.class));

            Renderer.get().enqueueWorld(renCom);
        }

        Renderer.get().render();
    }

    @Override
    public void debugUpdate(EntityManager entityManager, float deltaTime){
        Renderer.get().clearRenderQueue();
        List<Entity> entities = entityManager.getEntitiesWithComponents(Sprite.class, Transform.class);

        for(Entity e : entities){
            RenderCommand renCom = new RenderCommand();

            renCom.setSprite(e.getComponent(Sprite.class));
            renCom.setLayer(e.getComponent(Sprite.class).getLayer());
            renCom.setTransform(e.getComponent(Transform.class));
            renCom.setDebug();

            BoxCollider bc = e.getComponent(BoxCollider.class);
            if(bc != null)renCom.setBoxCollider(bc);

            Renderer.get().enqueueWorld(renCom);
        }

        Renderer.get().render();
    }

}
