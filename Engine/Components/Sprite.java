package Engine.Components;

import Engine.Math.Vector2f;
import Engine.Rendering.Texture;
import Engine.esc.Component;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Sprite extends Component {
    private Texture texture;
    private Color color;
    public int width, height;
    public boolean visible = true;
    int layer;

    public Sprite(Texture texture, int width, int height){
        this.texture = texture;
        this.width = width;
        this.height = height;
    }
    public Sprite(String texturePath, int width, int height){
        this.texture = new Texture("src/Resourses/" + texturePath);
        this.width = width;
        this.height = height;
    }

    public Sprite(Texture texture){
        this.texture = texture;
        this.width = texture.getWidth();
        this.height = texture.getHeight();
    }

    public Sprite(String texturePath){
        this.texture = new Texture("src/Resourses/" + texturePath);
        this.width = texture.getWidth();
        this.height = texture.getHeight();
    }

    public Sprite(int width, int height){
        this.color = Color.RED;
        this.width = width;
        this.height = height;
    }

    public Texture getTexture() { return texture; }

    public Image getSpriteImage() {
        if(hasTexture()) {
            BufferedImage source = (BufferedImage) texture.getImage();

            int w = Math.min(width, texture.getWidth());
            int h = Math.min(height, texture.getHeight());

            BufferedImage sub = source.getSubimage(0, 0, w, h);
            return sub;
        }

        BufferedImage colored = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = colored.createGraphics();
        g.setColor(color != null ? color : Color.WHITE);
        g.fillRect(0, 0, width, height);
        g.dispose();

        return colored;
    }

    public boolean hasTexture() { return texture != null; }

    public Vector2f getSize(){
        return new Vector2f(width, height);
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
        this.color = null;
    }

    public void setTexture(String texturePath) {
        this.texture = new Texture("src/Resourses/" + texturePath);
        this.color = null;
    }

    public void setSize(Vector2f size){
        this.width = (int) size.x;
        this.height = (int) size.y;
    }

    public int getLayer(){
        return layer;
    }

}
