package Engine.Systems;

import Engine.Components.ScriptComponent;
import Engine.Systems.Events.Event;
import Engine.esc.System;
import Engine.esc.EntityManager;

import java.util.*;
import java.util.List;

public class EventSystem extends System {
    Queue<Event> eventQueue = new LinkedList<>();
    Map<Class<? extends Event>, List<ScriptComponent>> listeners = new HashMap<>();
    private static final EventSystem instance = new EventSystem();

    public static EventSystem get() {
        return instance;
    }

    public EventSystem(){
        setPriority(3);
    }

    public void emit(Event event) {
        eventQueue.add(event);
    }

    public void registerListener(Class<? extends Event> eventType, ScriptComponent script){
        listeners
                .computeIfAbsent(eventType, k -> new ArrayList<>())
                .add(script);
    }

    public void unregisterListener(ScriptComponent script) {
        for (List<ScriptComponent> list : listeners.values()) {
            list.remove(script);
        }
    }

    private void dispatch() {
        while (!eventQueue.isEmpty()) {
            Event e = eventQueue.poll();

            List<ScriptComponent> subs = listeners.get(Event.class);
            if(subs == null) continue;

            for(ScriptComponent sc : subs){
                sc.HandleEvent(e);
            }
        }
    }

    public void update(EntityManager entityManager, float deltaTime) {
        dispatch();
    }

    public void debugUpdate(EntityManager entityManager, float deltaTime) {
        dispatch();
    }
}
