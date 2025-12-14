package Engine.Rendering;

import Engine.Components.BoxCollider;
import Engine.Components.Sprite;
import Engine.Components.Transform;
import Engine.Math.Vector2f;

import java.awt.*;

public class RenderCommand {
    private Sprite sprite;
    private Transform transform;
    private BoxCollider boxCollider;
    private int layer;
    private boolean debug;

    public void setTransform(Transform transform) {
        this.transform = transform;
    }

    public void setLayer(int layer) {
        this.layer = layer;
    }

    public void setSprite(Sprite sprite){
        this.sprite = sprite;
    }

    public void setBoxCollider(BoxCollider boxCollider) {
        this.boxCollider = boxCollider;
    }

    public void setDebug() {
        this.debug = true;
    }

    public int getLayer() {
        return layer;
    }

    public Texture getTexture() {
        return sprite.getTexture();
    }

    public Transform getTransform() {
        return transform;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public boolean isDebug() {
            return debug;
    }

    public BoxCollider getBoxCollider() {
        return boxCollider;
    }

    public Image getImage(){
        return sprite.getSpriteImage();
    }
}
