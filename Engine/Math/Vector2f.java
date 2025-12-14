package Engine.Math;

public class Vector2f {
    public float x, y;

    public Vector2f() { this(0, 0); }
    public Vector2f(float x, float y) {
        this.x = x;
        this.y = y;
    }
    public Vector2f(Vector2f newvetor) {
        this.x = newvetor.x;
        this.y = newvetor.y;
    }

    public Vector2f add(Vector2f other) {
        return new Vector2f(x + other.x, y + other.y);
    }

    public Vector2f subtract(Vector2f other) {
        return new Vector2f(x - other.x, y - other.y);
    }

    public Vector2f multiply(float scalar) {
        return new Vector2f(x * scalar, y * scalar);
    }

    public float length() {
        return (float) Math.sqrt(x * x + y * y);
    }

    public void normalize() {
        float len = length();
        if (len != 0) {
            x /= len;
            y /= len;
        }
    }

    static public Vector2f multiply(Vector2f A, float scalar){
        Vector2f newVector = new Vector2f(A.x * scalar, A.y * scalar);
        return newVector;
    }

    static public Vector2f add(Vector2f A, Vector2f B){
        Vector2f newVector = new Vector2f(A.x + B.x, A.y + B.y);
        return newVector;
    }

    static public Vector2f ZERO(){
        Vector2f zeroVector = new Vector2f(0, 0);
        return zeroVector;
    }

    public float distance(Vector2f other) {
        float dx = x - other.x;
        float dy = y - other.y;
        return (float) Math.sqrt(dx * dx + dy * dy);
    }

    public Vector2f copy() {
        return new Vector2f(x, y);
    }

    @Override
    public String toString() {
        return String.format("(%.2f, %.2f)", x, y);
    }
}
