package Engine.esc;

import Engine.Components.ScriptComponent;
import Engine.Systems.EventSystem;
import Engine.Systems.Events.Event;

import java.util.*;

public class Entity {
    private String id;
    private Map<Class<? extends Component>, Component> components = new HashMap<>();

    public Entity(String id){
        this.id = id;
    }

    public <T extends Component> void addComponentInternal(T component) {
        components.put(component.getClass(), component);

        if(component instanceof ScriptComponent script){
            script.setEntity(this);
            EventSystem.get().registerListener(Event.class, script);
        }
    }

    public <T extends Component> T removeComponentInternal(Class<T> componentClass) {
        return componentClass.cast(components.remove(componentClass));
    }

    public <T extends Component> boolean hasComponent(Class<T> componentClass) {
        return components.containsKey(componentClass);
    }

    public <T extends Component> T getComponent(Class<T> componentClass) {
        return componentClass.cast(components.get(componentClass));
    }

    public String getId() { return id; }

    public Collection<Component> getAllComponents() {
        return Collections.unmodifiableCollection(components.values());
    }

}
