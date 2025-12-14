package Engine.Components;

import Engine.Systems.EventSystem;
import Engine.Systems.Events.Event;
import Engine.esc.Component;
import Engine.Systems.ScriptSystem;
import Engine.esc.Entity;

public class ScriptComponent extends Component {
    protected Entity entity;

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public void Awake() {}
    public void Start() {}
    public void update(float deltaTime) {}
    public void OnDestroy() {}
    public void HandleEvent(Event e) {}

    public void onAttach() {
        Awake();
        ScriptSystem.get().add(this);
        EventSystem.get().registerListener(Event.class, this);
        Start();
    }

    public void onDetach() {
        OnDestroy();
        EventSystem.get().unregisterListener(this);
        ScriptSystem.get().remove(this);
    }
}
