package Engine.esc;

public abstract class System {
    private int priority = 0;
    public int getPriority(){ return priority; }
    public void setPriority(int p){ priority = p; }
    public abstract void update(EntityManager entityManager, float deltaTime);
    public abstract void debugUpdate(EntityManager entityManager, float deltaTime);
}
