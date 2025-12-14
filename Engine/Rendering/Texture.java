package Engine.Rendering;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Texture {
    private Image image;
    private int width, height;
    private String path;

    public Texture(String path){
        System.out.print(path);
        this.path = path;
        loadTexture();
    }

    private void loadTexture(){
        try {
            BufferedImage loadedImg = ImageIO.read(new File(path));
            this.image = loadedImg;
            this.width = loadedImg.getWidth();
            this.height = loadedImg.getHeight();

        } catch (IOException e){
            System.err.println("Не удалось загрузить текстуру: " + path);
            e.printStackTrace();
            createPlaceholderTexture();
        }
    }

    private void createPlaceholderTexture(){
        BufferedImage placeholder = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = placeholder.createGraphics();

        g.setColor(Color.RED);
        g.fillRect(0, 0, 32, 32);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 16, 16);
        g.fillRect(16, 16, 16, 16);

        g.dispose();

        this.image = placeholder;
        this.width = 32;
        this.height = 32;

    }

    public Image getImage() { return image; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public String getPath() { return path; }
}
