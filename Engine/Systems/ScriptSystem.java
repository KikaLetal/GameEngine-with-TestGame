package Engine.Systems;

import Engine.esc.System;
import Engine.esc.EntityManager;
import Engine.esc.Entity;
import Engine.esc.Component;
import Engine.Components.ScriptComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ScriptSystem extends System{
    private static final ScriptSystem instance = new ScriptSystem();

    private final List<ScriptComponent> scripts = new ArrayList<>();

    public static ScriptSystem get() {
        return instance;
    }

    public ScriptSystem(){
        setPriority(200);
    }

    public void add(ScriptComponent script) {
        scripts.add(script);
        script.Start();
    }

    public void remove(ScriptComponent script) {
        scripts.remove(script);
    }

    @Override
    public void update(EntityManager entityManager, float deltaTime){
        for (ScriptComponent sc : scripts) {
            sc.update(deltaTime);
        }
    }

    @Override
    public void debugUpdate(EntityManager entityManager, float deltaTime){
        for (ScriptComponent sc : scripts) {
            sc.update(deltaTime);
        }
    }
}
