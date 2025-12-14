package Engine.esc;

import java.util.*;

public class Scene {
    private EntityManager entityManager = new EntityManager();
    private List<System> systems = new ArrayList<>();

    public void addSystem(System system) {
        systems.add(system);
        systems.sort(Comparator.comparingInt(System::getPriority));
    }

    public Entity addEntity(String id){
        return entityManager.createEntity(id);
    }

    public Entity addEntity(Entity ent, String id){
        return entityManager.createEntity(ent, id);
    }

    public void update(float deltaTime) {
        for (System s : systems) {
            s.update(entityManager, deltaTime);
        }
    }

    public void debugUpdate(float deltaTime) {
        for (System s : systems) {
            s.debugUpdate(entityManager, deltaTime);
        }
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }
}
