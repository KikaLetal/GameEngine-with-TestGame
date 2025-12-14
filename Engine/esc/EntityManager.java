package Engine.esc;

import java.util.*;

import Engine.Components.ScriptComponent;
import Engine.Systems.EventSystem;
import Engine.Systems.Events.Event;
import Engine.esc.Entity;
import Engine.esc.Component;

public class EntityManager {
    private Map<String, Entity> entitiesById = new LinkedHashMap<>();
    private Map<Class<? extends Component>, Map<String, Component>> componentMap = new HashMap<>();

    public Entity createEntity(String id){
        if (entitiesById.containsKey(id)) throw new IllegalArgumentException("Объект с идентификатором '"+id+"' уже существует");
        Entity entity = new Entity(id);
        entitiesById.put(id, entity);
        return entity;
    }

    public Entity createEntity(Entity ent, String id){
        if (entitiesById.containsKey(id)) throw new IllegalArgumentException("Объект с идентификатором '"+id+"' уже существует");
        entitiesById.put(id, ent);
        return ent;
    }

    public void removeEntity(String id){
        if (!entitiesById.containsKey(id)) throw new IllegalArgumentException("Объект с идентификатором '"+id+"' не найден");
        Entity entity = entitiesById.remove(id);
        for(Component comp : new ArrayList<>(entity.getAllComponents())){
            removeComponent(id, comp.getClass());
        };
    }

    public Entity getEntity(String id){
        return entitiesById.get(id);
    }

    public Collection<Entity> getAllEntities() {
        return Collections.unmodifiableCollection(entitiesById.values());
    }

    public <T extends Component> void addComponent(String entityID, T component){
        Entity entity = entitiesById.get(entityID);
        if(entity == null) throw new IllegalArgumentException("объект с таким '"+entityID+"' не существует");

        Class<? extends Component> compClass = component.getClass();

        Component  old = entity.removeComponentInternal(compClass);
        if(old != null){
            Map<String, Component> map = componentMap.get(compClass);
            if(map != null && !map.isEmpty()) map.remove(entityID);
        }

        entity.addComponentInternal(component);
        componentMap.computeIfAbsent(compClass, k -> new LinkedHashMap<>()).put(entityID, component);

        if (component instanceof ScriptComponent script) {
            script.setEntity(entity);
            script.onAttach();
        }
    }
    public <T extends Component> T removeComponent(String entityID, Class<T> compClass){
        Entity entity = entitiesById.get(entityID);
        if(entity == null) throw new IllegalArgumentException("объект с таким '"+entityID+"' не существует");

        T removed = entity.removeComponentInternal(compClass);
        Map<String, Component> map = componentMap.get(compClass);
        if(map != null) map.remove(entityID);

        if (removed instanceof ScriptComponent script) {
            script.onDetach();
        }

        return removed;
    }
    public <T extends Component> T getComponent(String entityID, Class<T> compClass){
        Entity entity = entitiesById.get(entityID);
        if(entity == null) throw new IllegalArgumentException("объект с таким '"+entityID+"' не существует");
        return entity.getComponent(compClass);
    }
    public Set<String> getEntityIDsWithComponent (Class<? extends Component> compClass){
        Map<String, Component> map = componentMap.get(compClass);
        if(map == null) return Collections.emptySet();

        return Set.copyOf(map.keySet());
    }
    public List<Entity> getEntitiesWithComponents(Class<? extends Component>... compClasses){
        if (compClasses == null || compClasses.length == 0) return new ArrayList<>(entitiesById.values());

        List<Set<String>> sets = new ArrayList<>();
        for(Class<? extends Component> cc : compClasses) {
            Map<String, Component> map = componentMap.get(cc);
            if (map == null || map.isEmpty()) return Collections.emptyList();
            sets.add(map.keySet());
        }

        sets.sort(Comparator.comparingInt(Set::size));
        Set<String> smallest = sets.get(0);

        List<Entity> result = new ArrayList<>();
        outer: for (String id : smallest){
            for(int i = 1; i < sets.size(); i++){
                if(!sets.get(i).contains(id)) continue outer;
            }
            Entity entity = entitiesById.get(id);
            if(entity != null) result.add(entity);
        }
        return result;
    }

}
